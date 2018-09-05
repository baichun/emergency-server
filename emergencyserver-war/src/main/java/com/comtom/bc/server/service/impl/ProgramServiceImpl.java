package com.comtom.bc.server.service.impl;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.common.utils.DateUtil;
import com.comtom.bc.dispatch.FlowConstants;
import com.comtom.bc.dispatch.common.ServiceFlowException;
import com.comtom.bc.server.repository.dao.*;
import com.comtom.bc.server.repository.entity.*;
import com.comtom.bc.server.req.ProgramAuditReq;
import com.comtom.bc.server.req.ProgramQueryReq;
import com.comtom.bc.server.req.ProgramSaveReq;
import com.comtom.bc.server.req.ProgramUpdateReq;
import com.comtom.bc.server.service.DispatchFlowService;
import com.comtom.bc.server.service.InfoAccessService;
import com.comtom.bc.server.service.ProgramService;
import com.comtom.bc.server.service.SchemeService;
import com.comtom.bc.server.service.base.BaseService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 节目信息-业务逻辑处理接口定义
 *
 * @author zhucanhui
 *
 */
@Service("ProgramService")
public class ProgramServiceImpl extends BaseService implements ProgramService {

	/**
	 * 日志记录器
	 */
	private static final Logger logger = LoggerFactory.getLogger(ProgramServiceImpl.class);

	@Autowired
	private SchemeService schemeService;

	@Autowired
	private DispatchFlowService dispatchFlowService;

	@Autowired
	private ProgramDAO programDAO;

	@Autowired
	private ProgramAreaDAO programAreaDAO;

	@Autowired
	private ProgramFilesDAO programFilesDAO;

	@Autowired
	private ProgramStrategyDAO programStrategyDAO;

	@Autowired
	private ProgramTimeDAO programTimeDAO;

	@Autowired
	private DispatchFlowDAO dispatchFlowDAO;

	@Autowired
	private RegionAreaDAO regionAreaDAO;

	@Autowired
	private FileInfoDAO fileInfoDAO;

	@Autowired
	private EbmEventDAO ebmEventDAO;

	@Autowired
	private EbmDAO ebmDAO;

	@Autowired
	private InfoAccessService infoAccessService;

	@Autowired
	private InfoAccessDAO infoAccessDAO;

	/**
	 * 节目信息保存
	 *
	 * @param programReq
	 * @return ProgramInfo
	 */
	@Transactional
	public ProgramInfo saveProgram(ProgramSaveReq programSaveReq) throws RuntimeException {

		// 根据创建节目人员获取组织机构
		String createUser = programSaveReq.getCreateUser();
		SysUser sysUser = this.getSysUser(createUser);
		SysOrg sysOrg = this.getUserOrg(sysUser.getOrgCascadeId());

		// Step.1 保存节目基本信息
		ProgramInfo programInfo = new ProgramInfo();
		programInfo.setProgramType(programSaveReq.getProgramType());
		programInfo.setProgramLevel(programSaveReq.getProgramLevel());
		programInfo.setProgramName(programSaveReq.getProgramName());
		programInfo.setCreateTime(new Date());
		programInfo.setSrcType(2); // 节目来源
		programInfo.setLanguageCode(programSaveReq.getLanguageCode());
		programInfo.setProgramContent(programSaveReq.getProgramContent());
		programInfo.setCreateUser(programSaveReq.getCreateUser());
		programInfo.setAuditResult(Constants.AUDIT_INIT);
		programInfo.setReleaseOrgName(sysOrg.getName());
		programInfo.setReleaseOrgCode(sysOrg.getBizCode());

		if (programSaveReq.getSubmitFlag().equals(1)) {
			programInfo.setState(Constants.PROGRAM_STATE_SUBMIT);
		} else {
			programInfo.setState(Constants.PROGRAM_STATE_CREATE);
		}

		if(StringUtils.isNotEmpty(programSaveReq.getEbmEventType())){
			programInfo.setEbmEventType(programSaveReq.getEbmEventType());

			// 根据事件分类编码查询事件描述
			EbmEventType ebmEventType = ebmEventDAO.findOne(programSaveReq.getEbmEventType());

			if (ebmEventType != null) {
				programInfo.setEbmEventDesc(ebmEventType.getEventDesc());
			}
		}



		programInfo = programDAO.save(programInfo);

		if (programInfo == null) {
			return null;
		}

		if (programSaveReq.getSubmitFlag().equals(1)) {
			// 节目创建成功，创建调度流程信息
			dispatchFlowService.createFlow(programInfo.getProgramId(), null,
					FlowConstants.STAGE_STARTING, FlowConstants.STATE_INFO_AUDIT);
		} else {
			// 节目创建成功，创建调度流程信息
			dispatchFlowService.createFlow(programInfo.getProgramId(), null,
					FlowConstants.STAGE_STARTING, FlowConstants.STATE_INFO);
		}

		// 获取节目Id
		Long programId = programInfo.getProgramId();

		// Step.2 保存节目区域信息
		List<ProgramArea> areaList = programSaveReq.getAreaList();

		if (areaList == null || areaList.isEmpty()) {
			throw new RuntimeException("节目区域信息为空...");
		}

		for (ProgramArea programArea : areaList) {
			ProgramArea area = new ProgramArea();
			area.setProgramId(programId);
			area.setAreaCode(programArea.getAreaCode());

			area = programAreaDAO.save(area);

			if (area == null) {
				// 保存节目区域失败，回滚业务操作
				throw new RuntimeException();
			}
		}

		// Step.3 保存节目文件信息
		List<ProgramFiles> filesList = programSaveReq.getFilesList();

		if (filesList != null && !filesList.isEmpty()) {
			for (ProgramFiles programFiles : filesList) {
				ProgramFiles files = new ProgramFiles();
				files.setProgramId(programId);
				files.setFileId(programFiles.getFileId());

				files = programFilesDAO.save(files);

				if (files == null) {
					// 保存节目区域失败，回滚业务操作
					throw new RuntimeException();
				}
			}
		}

		// Step.4 保存节目策略信息
		ProgramStrategy programStrategy = programSaveReq.getProgramStrategy();
		List<ProgramTime> timeList = programStrategy.getTimeList();
		programStrategy.setProgramId(programId);
		programStrategy = programStrategyDAO.save(programStrategy);

		if (programStrategy == null) {
			// 保存节目策略失败，回滚业务操作
			throw new RuntimeException();
		}

		// Step.5 保存节目时间段
		for (ProgramTime programTime : timeList) {
			ProgramTime time = new ProgramTime();
			time.setStartTime(programTime.getStartTime());
			time.setOverTime(programTime.getOverTime());
			time.setDurationTime(programTime.getDurationTime());
			time.setStrategyId(programStrategy.getStrategyId());
			time.setHandleFlag(1);

			time = programTimeDAO.save(time);
		}

		return programInfo;
	}

	/**
	 * 节目信息保存
	 *
	 * @param programReq
	 * @return ProgramInfo
	 */
	@Transactional
	public ProgramInfo saveProgram(ProgramInfo program) throws RuntimeException {

		// 根据创建节目人员获取组织机构
		String createUser = program.getCreateUser();
		SysUser sysUser = this.getSysUser(createUser);
		SysOrg sysOrg = this.getUserOrg(sysUser.getOrgCascadeId());

		// Step.1 保存节目基本信息
		ProgramInfo programInfo = new ProgramInfo();
		programInfo.setProgramType(program.getProgramType());
		programInfo.setProgramLevel(program.getProgramLevel());
		programInfo.setProgramName(program.getProgramName());
		programInfo.setCreateTime(new Date());
		programInfo.setSrcType(2); // 节目来源
		programInfo.setLanguageCode(program.getLanguageCode());
		programInfo.setProgramContent(program.getProgramContent());
		programInfo.setCreateUser(program.getCreateUser());
		programInfo.setAuditResult(Constants.AUDIT_INIT);
		programInfo.setReleaseOrgName(sysOrg.getName());
		programInfo.setReleaseOrgCode(sysOrg.getBizCode());

		programInfo.setState(program.getState());

		programInfo.setEbmEventType(program.getEbmEventType());
		programInfo.setEbmEventDesc(program.getEbmEventDesc());

		programInfo = programDAO.save(programInfo);

		if (programInfo == null) {
			return null;
		}

		/*if (programSaveReq.getSubmitFlag().equals(1)) {
			// 节目创建成功，创建调度流程信息
			dispatchFlowService.createFlow(programInfo.getProgramId(), null,
					FlowConstants.STAGE_STARTING, FlowConstants.STATE_INFO_AUDIT);
		} else {
			// 节目创建成功，创建调度流程信息
			dispatchFlowService.createFlow(programInfo.getProgramId(), null,
					FlowConstants.STAGE_STARTING, FlowConstants.STATE_INFO);
		}*/

		// 节目创建成功，创建调度流程信息
		dispatchFlowService.createFlow(programInfo.getProgramId(), null,
				FlowConstants.STAGE_STARTING, FlowConstants.STATE_INFO);

		// 获取节目Id
		Long programId = programInfo.getProgramId();

		// Step.2 保存节目区域信息
		List<ProgramArea> areaList = program.getAreaList();

		if (areaList == null || areaList.isEmpty()) {
			throw new RuntimeException("节目区域信息为空...");
		}

		for (ProgramArea programArea : areaList) {
			ProgramArea area = new ProgramArea();
			area.setProgramId(programId);
			area.setAreaCode(programArea.getAreaCode());

			area = programAreaDAO.save(area);

			if (area == null) {
				// 保存节目区域失败，回滚业务操作
				throw new RuntimeException();
			}
		}

		// Step.3 保存节目文件信息
		List<ProgramFiles> filesList = program.getFilesList();

		if (filesList != null && !filesList.isEmpty()) {
			for (ProgramFiles programFiles : filesList) {
				ProgramFiles files = new ProgramFiles();
				files.setProgramId(programId);
				files.setFileId(programFiles.getFileId());

				files = programFilesDAO.save(files);

				if (files == null) {
					// 保存节目区域失败，回滚业务操作
					throw new RuntimeException();
				}
			}
		}

		// Step.4 保存节目策略信息
		ProgramStrategy programStrategy = new ProgramStrategy();//program.getProgramStrategy();
		List<ProgramTime> timeList = program.getProgramStrategy().getTimeList();
		programStrategy.setProgramId(programId);
		programStrategy.setOverTime(program.getProgramStrategy().getOverTime());
		programStrategy.setPlayTime(program.getProgramStrategy().getPlayTime());
		programStrategy.setStartTime(program.getProgramStrategy().getStartTime());
		programStrategy.setWeekMask(program.getProgramStrategy().getWeekMask());
		programStrategy.setStrategyType(program.getProgramStrategy().getStrategyType());
		programStrategy = programStrategyDAO.save(programStrategy);

		if (programStrategy == null) {
			// 保存节目策略失败，回滚业务操作
			throw new RuntimeException();
		}

		// Step.5 保存节目时间段
		for (ProgramTime programTime : timeList) {
			ProgramTime time = new ProgramTime();
			time.setStartTime(programTime.getStartTime());
			time.setOverTime(programTime.getOverTime());
			time.setDurationTime(programTime.getDurationTime());
			time.setStrategyId(programStrategy.getStrategyId());
			time.setHandleFlag(1);

			time = programTimeDAO.save(time);
		}

		return programInfo;
	}

	/**
	 * 节目信息更新
	 *
	 * @param programReq
	 * @return ProgramInfo
	 */
	@Transactional
	public ProgramInfo updateProgram(ProgramUpdateReq programReq) throws RuntimeException {

		// Step.1 更新节目基本信息

		// 获取节目Id,根据节目Id更新节目信息
		Long programId = programReq.getProgramId();

		if (CommonUtil.isEmpty(programId)) {
			return null;
		}

		// 获取节目信息
		ProgramInfo programInfo = programDAO.findOne(programId.longValue());

		if (programInfo.getState().equals(Constants.PROGRAM_STATE_SUBMIT)) {
			return null;
		}

		// 设置修改属性
		if (CommonUtil.isNotEmpty(programReq.getProgramLevel())) {
			programInfo.setProgramLevel(programReq.getProgramLevel());
		}

		if (CommonUtil.isNotEmpty(programReq.getProgramName())) {
			programInfo.setProgramName(programReq.getProgramName());
		}

		if (CommonUtil.isNotEmpty(programReq.getLanguageCode())) {
			programInfo.setLanguageCode(programReq.getLanguageCode());
		}

		if (CommonUtil.isNotEmpty(programReq.getProgramContent())) {
			programInfo.setProgramContent(programReq.getProgramContent());
		}

		if (CommonUtil.isNotEmpty(programReq.getEbmEventType())) {
			programInfo.setEbmEventType(programReq.getEbmEventType());
		}

		if (programReq.isSubmit()) {
			programInfo.setState(Constants.PROGRAM_STATE_SUBMIT);
		}

		// 更新节目信息
		programInfo.setUpdateTime(new Date());
		programInfo = programDAO.save(programInfo);

		if (programInfo == null) {
			return null;
		}

		// Step.2 更新节目区域信息
		List<ProgramArea> areaList = programReq.getAreaList();

		if (areaList != null && !areaList.isEmpty()) {

			// 先删除关联区域
			programAreaDAO.deleteArea(programId);

			// 新增关联区域
			for (ProgramArea programArea : areaList) {
				ProgramArea area = new ProgramArea();
				area.setProgramId(programId);
				area.setAreaCode(programArea.getAreaCode());

				area = programAreaDAO.save(area);

				if (area == null) {
					// 保存节目区域失败，回滚业务操作
					throw new RuntimeException();
				}
			}
		}

		// Step.3 更新节目文件信息
		List<ProgramFiles> filesList = programReq.getFilesList();

		if (filesList != null && !filesList.isEmpty()) {

			// 先删除关联文件
			programFilesDAO.deleteFiles(programId);

			// 新增关联文件
			for (ProgramFiles programFiles : filesList) {
				ProgramFiles files = new ProgramFiles();
				files.setProgramId(programId);
				files.setFileId(programFiles.getFileId());

				files = programFilesDAO.save(files);

				if (files == null) {
					// 保存节目区域失败，回滚业务操作
					throw new RuntimeException();
				}
			}
		}

		// Step.4 更新节目策略信息
		ProgramStrategy strategy = programReq.getProgramStrategy();
		List<ProgramTime> timeList = strategy.getTimeList();

		if (strategy != null) {

			// 获取节目策略信息
			ProgramStrategy programStrategy = programStrategyDAO.findOne(strategy.getStrategyId());

			// 根据修改内容设置策略属性
			if (CommonUtil.isNotEmpty(strategy.getStartTime())) {
				programStrategy.setStartTime(strategy.getStartTime());
			}

			if (CommonUtil.isNotEmpty(strategy.getOverTime())) {
				programStrategy.setOverTime(strategy.getOverTime());
			}

			if (CommonUtil.isNotEmpty(strategy.getStrategyType())) {
				programStrategy.setStrategyType(strategy.getStrategyType());
			}

			if (CommonUtil.isNotEmpty(strategy.getPlayTime())) {
				programStrategy.setPlayTime(strategy.getPlayTime());
			}

			if (CommonUtil.isNotEmpty(strategy.getPlayTime())) {
				programStrategy.setWeekMask(strategy.getWeekMask());
			}

			programStrategy = programStrategyDAO.save(programStrategy);

			if (programStrategy == null) {
				// 保存节目策略失败，回滚业务操作
				throw new RuntimeException();
			}
		}

		// Step.5 更新节目时间段
		if (timeList != null && !timeList.isEmpty()) {

			// 先删除策略时段段信息
			programTimeDAO.deleteTime(strategy.getStrategyId());

			// 新增关联时间段
			for (ProgramTime programTime : timeList) {
				ProgramTime time = new ProgramTime();
				time.setStartTime(programTime.getStartTime());
				time.setOverTime(programTime.getOverTime());
				time.setDurationTime(programTime.getDurationTime());
				time.setStrategyId(strategy.getStrategyId());

				time = programTimeDAO.save(time);
			}
		}

		return programInfo;
	}

	/**
	 * 提交节目，更新节目状态为提交审核状态
	 *
	 * @param programId
	 * @return ProgramInfo
	 */
	@Transactional
	public ProgramInfo submitProgram(Long programId) {
		ProgramInfo programInfo = programDAO.findOne(programId);
		programInfo.setState(Constants.PROGRAM_STATE_SUBMIT);

		// 节目提交，更新调度流程信息
		dispatchFlowService.updateFlow(programInfo.getProgramId(), null,
				FlowConstants.STAGE_STARTING, FlowConstants.STATE_INFO_AUDIT);

		return programDAO.save(programInfo);
	}

	/**
	 * 节目总记录数统计
	 *
	 * @return long
	 */
	public long count() {
		return programDAO.count();
	}

	/**
	 * 获取周期性节目待调度分发的节目列表
	 *
	 * @return List<ProgramInfo>
	 */
	public List<ProgramInfo> getDispatchProgram() {

		// Step.1 获取节目策略信息
		List<ProgramStrategy> strategyList = programStrategyDAO.findAll(getStrategySpec(),
				getStrategySort());

		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int weekDay = calendar.get(Calendar.DAY_OF_WEEK);

		List<ProgramInfo> programList = new ArrayList<ProgramInfo>();

		for (ProgramStrategy programStrategy : strategyList) {

			// 判断当天的播发记录是否已生成
			// Ebm ebm =
			// ebmDAO.findByProgramNum(programStrategy.getProgramId().intValue());

			List<Ebm> ebmList = ebmDAO.findAll(getDayEbmSpec(programStrategy.getProgramId()));

			if (ebmList != null && !ebmList.isEmpty()) {
				continue;
			}

			// 策略类型
			Integer strategyType = programStrategy.getStrategyType();

			// 天策略 or 周策略处理
			if (strategyType.equals(Constants.DAY_STRATEGY)) {
				ProgramInfo programInfo = programDAO.findOne(programStrategy.getProgramId());
				programList.add(programInfo);

			} else if (strategyType.equals(Constants.WEEK_STRATEGY)) {

				int weekMask = programStrategy.getWeekMask().intValue();

				// 如果周策略星期选择包含当前星期数，则可以调度
				int bitAnd = weekDay & weekMask;
				if (bitAnd == weekDay) {
					ProgramInfo programInfo = programDAO.findOne(programStrategy.getProgramId());
					programList.add(programInfo);
				}
			}

		}

		return programList;
	}

	@Override
	public List<ProgramFiles> findProgramFile(ProgramFiles programFile) {
		return programFilesDAO.findAll(getProgramFilesSpec(programFile.getFileId()));
	}

	/**
	 * 查询节目基本信息列表
	 */
	public Page<ProgramInfo> getProgramInfo(ProgramQueryReq programReq) {

		// 根据前端参数查询节目
		Page<ProgramInfo> programPage = programDAO.findAll(getSpec(programReq),
				buildPageRequest(programReq.getPageNum(), programReq.getPageSize(), getSort()));

		for (ProgramInfo programInfo : programPage) {
			List<ProgramArea> programAreaList = programAreaDAO.findByProgramId(programInfo
					.getProgramId());
			for (ProgramArea programArea : programAreaList) {
				RegionArea regionArea = regionAreaDAO.findOne(programArea.getAreaCode());
				programArea.setAreaName(regionArea.getAreaName());
			}

			programInfo.setAreaList(programAreaList);
		}

		return programPage;
	}

	/**
	 * 根据节目Id,获取详细信息
	 */
	public ProgramInfo getProgramDetail(Long programId) {

		// Step.1 获取节目信息
		ProgramInfo programInfo = programDAO.findOne(programId);

		// Step.2 获取区域信息
		List<ProgramArea> areaList = programAreaDAO.findByProgramId(programId);
		for (ProgramArea programArea : areaList) {
			RegionArea regionArea = regionAreaDAO.findOne(programArea.getAreaCode());
			programArea.setAreaName(regionArea.getAreaName());
		}

		// Step.3 获取文件信息
		List<ProgramFiles> filesList = programFilesDAO.findByProgramId(programId);
		for (ProgramFiles programFiles : filesList) {
			FileInfo fileInfo = fileInfoDAO.findOne(programFiles.getFileId());
			programFiles.setFileName(fileInfo.getOriginName());
		}

		// Step.4 获取策略信息
		ProgramStrategy strategy = programStrategyDAO.findByProgramId(programId);

		// Step.5 获取时间段信息
		List<ProgramTime> timeList = programTimeDAO.findByStrategyId(strategy.getStrategyId());

		// Step.6 获取流程信息
		DispatchFlow dispatchFlow = dispatchFlowDAO.findByRelatedProgramId(programId);

		// 处理返回节目详细信息
		strategy.setTimeList(timeList);
		programInfo.setAreaList(areaList);
		programInfo.setFilesList(filesList);
		programInfo.setProgramStrategy(strategy);
		programInfo.setDispatchFlow(dispatchFlow);

		return programInfo;
	}

	public ProgramInfo getProgramInfo(Long programId) {
		return  programDAO.findOne(programId);
	}

	/**
	 * 根据节目Id取消节目
	 *
	 * @param programId
	 * @return ProgramInfo
	 */
	@Transactional
	public ProgramInfo cancelProgram(Long programId) {

		// 获取节目信息
		ProgramInfo programInfo = programDAO.findOne(programId);

		programInfo.setState(Constants.PROGRAM_STATE_CANCEL);
		programDAO.save(programInfo);

		// 获取流程信息，根据流程信息处理取消流程
		DispatchFlow dispatchFlow = dispatchFlowDAO.findByRelatedProgramId(programId);
		Integer flowStage = dispatchFlow.getFlowStage();

		// 根据不同的阶段和状态，进行不同的业务流程处理
		SchemeInfo schemeInfo = schemeService.findByProgramId(programInfo.getProgramId());

		// 取消方案
		if (flowStage.equals(FlowConstants.STAGE_RESPONSE)) {
			schemeInfo = schemeService.cancelScheme(programId);
		} else if (flowStage.equals(FlowConstants.STAGE_PROCESS)) {

			boolean result = false;
			try {
				// 发送和记录取消消息
				result = schemeService.cancelEbdEbm(schemeInfo.getSchemeId());
			} catch (ServiceFlowException e) {
				throw e;
			}

			if (logger.isDebugEnabled()) {
				logger.info("ProgramService.cancelProgram cancel ebm and ebd result={}.", result);
			}
		}

		return programInfo;
	}

	/**
	 * 节目审核，触发调度方案生成和调度消息
	 *
	 * @return ProgramInfo
	 */
	public ProgramInfo auditProgram(ProgramAuditReq req) {

		// 获取节目信息
		Integer auditResult = req.getAuditResult();
		ProgramInfo programInfo = programDAO.findOne(req.getProgramId());
		programInfo.setAuditUser(req.getAccount());

		// 节目审核通过，生成调度
		if (auditResult.equals(Constants.AUDIT_PASS)) {

			// 节目审核完成，更新流程状态：审核通过 或 审核不通过
			if (auditResult.equals(Constants.AUDIT_PASS)) {
				dispatchFlowService.updateFlow(programInfo.getProgramId(), null,
						FlowConstants.STAGE_STARTING, FlowConstants.STATE_INFO_AUDIT_YES);
			} else if (auditResult.equals(Constants.AUDIT_NO_PASS)) {
				dispatchFlowService.updateFlow(programInfo.getProgramId(), null,
						FlowConstants.STAGE_STARTING, FlowConstants.STATE_INFO_AUDIT_NO);
			}
			// 设置审核信息并更新
			programInfo.setAuditResult(req.getAuditResult());
			programInfo.setAuditOpinion(req.getAuditOpinion());
			programInfo.setAuditTime(new Date());
			programInfo.setAuditUser(req.getAccount());
			programInfo = programDAO.save(programInfo);
			InfoAccess infoAccess=null;
			try {
				Integer infoAuditFlag = Integer.parseInt(findValueByKey(Constants.INFO_ACCESS_AUDIT_));
				if(infoAuditFlag == Constants.AUDIT_YES || programInfo.getProgramType() >= Constants.PROGRAM_TYPE_DRILL){  //信息接入自动审核
					infoAccess = saveInfoAccess(programInfo, req.getAccount(), Constants.AUDIT_PASS, Constants.AUDIT_PASS_STR);
					programInfo.setInfoId(infoAccess.getInfoId());
					// 审批通过
					// 触发调度
					SchemeInfo schemeInfo = schemeService.synDispatch(programInfo);

					if (CommonUtil.isNotEmpty(schemeInfo)) {

						logger.info("ProgramController.audit generate dispatch successfully.");

					} else {
						return null;
					}
				}else{
					saveInfoAccess(programInfo);

				}



			} catch (ServiceFlowException e) {
				throw e;
			}

		} else {

		}
		// 设置审核信息并更新
		programInfo.setAuditResult(req.getAuditResult());
		programInfo.setAuditOpinion(req.getAuditOpinion());
		programInfo.setAuditTime(new Date());
		programInfo.setAuditUser(req.getAccount());
		programInfo = programDAO.save(programInfo);

		logger.info("ProgramController.audit generate dispatch successfully.");

		return programInfo;
	}

	private InfoAccess saveInfoAccess(ProgramInfo programInfo){
		return saveInfoAccess(programInfo,null,null,"");
	}

	/**
	 * 保存信息接入信息
	 * @param info 节目对象
	 * @param auditor 审核人
	 * @param auditResult 审核结果
	 * @param auditOpinion 审核意见
	 */
	private InfoAccess saveInfoAccess(ProgramInfo info,String auditor,Integer auditResult,String auditOpinion){
		ProgramStrategy strategy=programStrategyDAO.findByProgramId(info.getProgramId());
		List<ProgramArea> areaList = programAreaDAO.findByProgramId(info.getProgramId());
		List<ProgramTime> timeList=programTimeDAO.findByStrategyId(strategy.getStrategyId());
		String startTime = null;
		String overTime = null;
		if (!CollectionUtils.isEmpty(timeList)) {
			startTime = timeList.get(0).getStartTime();
			overTime = timeList.get(0).getOverTime();
		}
		String playTime=DateUtil.format(strategy.getPlayTime(),DateUtil.DATE_PATTERN.YYYY_MM_DD);
		Date startDate = DateUtil.stringToDate(playTime + " " + startTime + ":00");
		Date endDate = DateUtil.stringToDate(playTime + " " + overTime + ":00");
		StringBuffer areaSb=new StringBuffer();
		String areaCode = null;
		for (ProgramArea area : areaList){
			areaSb.append(area.getAreaCode()).append(",");
		}
		if(areaSb.length()>0){
			areaCode=areaSb.deleteCharAt(areaSb.length() - 1).toString();
		}
		InfoAccess access=new InfoAccess();
		access.setRelatedEbmId(info.getProgramId()+"");
		access.setMsgType(1);
		access.setInfoType(info.getProgramType());
		access.setMsgSource(Constants.INFO_SOURCE_SELF);
		access.setSenderCode(info.getReleaseOrgCode());
		access.setSendName(info.getReleaseOrgName());
		access.setSendTime(info.getCreateTime());
		access.setEventType(info.getEbmEventType());
		access.setSeverity(info.getProgramLevel());
		access.setStartTime(startDate);
		access.setEndTime(endDate);
		access.setMsgLanguageCode(info.getLanguageCode());
		access.setMsgTitle(info.getProgramName());
		access.setMsgDesc(info.getProgramContent());
		access.setAreaCode(areaCode);
		access.setCreateTime(new Date());
		if(auditResult == null){
			access.setAuditResult(Constants.AUDIT_INIT);
		}else{
			access.setAuditResult(auditResult);
			access.setAuditTime(new Date());
		}
		if(StringUtils.isNotEmpty(auditor)){
			access.setAuditor(auditor);
		}
		if(StringUtils.isNotEmpty(auditOpinion)){
			access.setAuditOpinion(auditOpinion);
		}
		InfoAccess infoAccess=infoAccessDAO.save(access);
		if(infoAccess!=null){
			infoAccessService.infoRemind(infoAccess.getInfoId(),infoAccess.getMsgTitle());
		}
		return infoAccess;
	}

	/**
	 * 实现多条件模糊和带日期区间查询Specification
	 *
	 * @param req
	 * @return Specification<ProgramInfo>
	 */
	private Specification<ProgramInfo> getSpec(ProgramQueryReq req) {

		// 查询参数
		final String programName = req.getProgramName();
		final String programType = req.getProgramType();
		final String createUser = req.getCreateUser();
		final Date startDate = req.getStartDate();
		final Date overDate = req.getOverDate();
		final String ebmEventType = req.getEbmEventType();
		final String ebmEventDesc = req.getEbmEventDesc();
		final Integer state = req.getState();
		final Integer auditResult = req.getAuditResult();

		return new Specification<ProgramInfo>() {
			@Override
			public Predicate toPredicate(Root<ProgramInfo> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (CommonUtil.isNotEmpty(programName)) {
					predicate.getExpressions().add(
							cb.like(r.<String> get("programName"),
									"%" + StringUtils.trim(programName) + "%"));
				}

				if (CommonUtil.isNotEmpty(programType)) {
					/*predicate.getExpressions().add(
							cb.equal(r.<Integer> get("programType"), programType));*/
					List<Integer> values = new ArrayList<Integer> ();

					CriteriaBuilder.In<Object> in = cb.in(r.get("programType"));
					String[] types = programType.split(String.valueOf(Constants.COMMA_SPLIT));
					for (String type : types) {
						//values.add(Integer.parseInt(type));
						in.value(Integer.parseInt(type));
					}
					/*Expression<String> exp = r.<String>get("programType");
					exp.in(values);*/
					predicate.getExpressions().add(in);
				}

				if (CommonUtil.isNotEmpty(auditResult)) {
					predicate.getExpressions().add(
							cb.equal(r.<Integer> get("auditResult"), auditResult));
				}

				if (CommonUtil.isNotEmpty(createUser)) {
					predicate.getExpressions().add(
							cb.like(r.<String> get("createUser"),
									"%" + StringUtils.trim(createUser) + "%"));
				}

				if (CommonUtil.isNotEmpty(startDate)) {
					predicate.getExpressions().add(
							cb.greaterThanOrEqualTo(r.<Date> get("createTime"), startDate));
				}

				if (CommonUtil.isNotEmpty(overDate)) {

					Calendar cl = Calendar.getInstance();
					cl.setTime(overDate);
					cl.add(Calendar.DAY_OF_MONTH, +1);

					predicate.getExpressions().add(
							cb.lessThan(r.<Date> get("createTime"), cl.getTime()));
				}

				if (CommonUtil.isNotEmpty(ebmEventType)) {
					predicate.getExpressions().add(
							cb.like(r.<String> get("ebmEventType"),
									"%" + StringUtils.trim(ebmEventType) + "%"));
				}

				if (CommonUtil.isNotEmpty(ebmEventDesc)) {
					predicate.getExpressions().add(
							cb.like(r.<String> get("ebmEventDesc"),
									"%" + StringUtils.trim(ebmEventDesc) + "%"));
				}

				// 审核人员，查询提交审核和取消节目列表
				if (CommonUtil.isNotEmpty(state)) {
					predicate.getExpressions().add(
							cb.greaterThanOrEqualTo(r.<Integer> get("state"), state));
				}

				return predicate;
			}
		};
	}

	/**
	 * 获取排序对象Sort
	 *
	 * @return Sort
	 */
	private Sort getSort() {
		Order orderState = new Order(Direction.ASC, "state");
		Order orderTime = new Order(Direction.DESC, "createTime");
		List<Order> orders = new ArrayList<Order>();
		orders.add(0, orderState);
		orders.add(1, orderTime);
		Sort sort = new Sort(orders);
		return sort;
	}

	/**
	 * 实现多条件模糊和带日期区间查询Specification
	 *
	 * @param req
	 * @return Specification<ProgramInfo>
	 */
	private Specification<ProgramStrategy> getStrategySpec() {

		// 查询参数
		return new Specification<ProgramStrategy>() {
			@Override
			public Predicate toPredicate(Root<ProgramStrategy> r, CriteriaQuery<?> q,
										 CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();

				String date = null;
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				Calendar calendar = Calendar.getInstance();
				date = sf.format(calendar.getTime());

				// 周期策略
				predicate.getExpressions().add(
						cb.greaterThanOrEqualTo(r.<Integer> get("strategyType"), 1));

				// 策略有效期内
				try {
					predicate.getExpressions().add(
							cb.lessThanOrEqualTo(r.<Date> get("startTime"), sf.parse(date)));
				} catch (ParseException e) {
					e.printStackTrace();
				}

				try {
					predicate.getExpressions().add(
							cb.greaterThanOrEqualTo(r.<Date> get("overTime"), sf.parse(date)));
				} catch (ParseException e) {
					e.printStackTrace();
				}

				return predicate;
			}
		};
	}

	/**
	 * 查询当天周期消息是否已生成<br>
	 * 实现多条件模糊和带日期区间查询Specification
	 *
	 * @param req
	 * @return Specification<ProgramInfo>
	 */
	private Specification<Ebm> getDayEbmSpec(final Long programNum) {

		// 查询参数
		return new Specification<Ebm>() {
			@Override
			public Predicate toPredicate(Root<Ebm> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();

				String startDate = null;
				SimpleDateFormat sf = new SimpleDateFormat(DateUtil.DATE_PATTERN.YYYY_MM_DD);
				Calendar calendar = Calendar.getInstance();
				startDate = sf.format(calendar.getTime());

				calendar.add(Calendar.DAY_OF_MONTH, 1);
				String overDate = sf.format(calendar.getTime());

				if (CommonUtil.isNotEmpty(programNum)) {
					predicate.getExpressions()
							.add(cb.equal(r.<Long> get("programNum"), programNum));
				}

				// 当天已生成EBM消息
				try {
					predicate.getExpressions()
							.add(cb.greaterThanOrEqualTo(r.<Date> get("createTime"),
									sf.parse(startDate)));
				} catch (ParseException e) {
					e.printStackTrace();
				}

				try {
					predicate.getExpressions().add(
							cb.lessThan(r.<Date> get("createTime"), sf.parse(overDate)));
				} catch (ParseException e) {
					e.printStackTrace();
				}

				return predicate;
			}
		};
	}

	private Specification<ProgramFiles> getProgramFilesSpec(final Long fileId) {

		// 查询参数
		return new Specification<ProgramFiles>() {
			@Override
			public Predicate toPredicate(Root<ProgramFiles> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (CommonUtil.isNotEmpty(fileId)) {
					predicate.getExpressions()
							.add(cb.equal(r.<Long> get("fileId"), fileId));
				}

				return predicate;
			}
		};
	}
	/**
	 * 获取排序对象Sort
	 *
	 * @return Sort
	 */
	private Sort getStrategySort() {
		Order strategyId = new Order(Direction.ASC, "strategyId");
		List<Order> orders = new ArrayList<Order>();
		orders.add(strategyId);
		Sort sort = new Sort(orders);
		return sort;
	}

}