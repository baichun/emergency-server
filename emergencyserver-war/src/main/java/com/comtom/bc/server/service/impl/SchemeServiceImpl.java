package com.comtom.bc.server.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.enums.StrategyType;
import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.common.utils.DateUtil;
import com.comtom.bc.common.utils.MathUtil;
import com.comtom.bc.common.utils.RegionUtil;
import com.comtom.bc.dispatch.FlowConstants;
import com.comtom.bc.dispatch.common.ErrorEnum;
import com.comtom.bc.dispatch.common.ServiceFlowException;
import com.comtom.bc.exchange.commom.EBDType;
import com.comtom.bc.exchange.commom.EventSeverityEnum;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.details.other.EBM;
import com.comtom.bc.exchange.util.FileNameUtil;
import com.comtom.bc.server.asset.WebCxt;
import com.comtom.bc.server.repository.dao.DispatchFlowDAO;
import com.comtom.bc.server.repository.dao.EbdDAO;
import com.comtom.bc.server.repository.dao.EbdFileDAO;
import com.comtom.bc.server.repository.dao.EbmAuxiliaryDAO;
import com.comtom.bc.server.repository.dao.EbmDAO;
import com.comtom.bc.server.repository.dao.EbmDispatchDAO;
import com.comtom.bc.server.repository.dao.EbrBsDAO;
import com.comtom.bc.server.repository.dao.EbrPlatformDAO;
import com.comtom.bc.server.repository.dao.FileInfoDAO;
import com.comtom.bc.server.repository.dao.ProgramAreaDAO;
import com.comtom.bc.server.repository.dao.ProgramDAO;
import com.comtom.bc.server.repository.dao.ProgramFilesDAO;
import com.comtom.bc.server.repository.dao.ProgramStrategyDAO;
import com.comtom.bc.server.repository.dao.ProgramTimeDAO;
import com.comtom.bc.server.repository.dao.RegionAreaDAO;
import com.comtom.bc.server.repository.dao.SchemeDAO;
import com.comtom.bc.server.repository.dao.SchemeEbrDAO;
import com.comtom.bc.server.repository.dao.VSchemeFlowDAO;
import com.comtom.bc.server.repository.entity.DispatchFlow;
import com.comtom.bc.server.repository.entity.Ebd;
import com.comtom.bc.server.repository.entity.EbdFile;
import com.comtom.bc.server.repository.entity.Ebm;
import com.comtom.bc.server.repository.entity.EbmAuxiliary;
import com.comtom.bc.server.repository.entity.EbmDispatch;
import com.comtom.bc.server.repository.entity.EbrBroadcast;
import com.comtom.bc.server.repository.entity.EbrPlatform;
import com.comtom.bc.server.repository.entity.FileInfo;
import com.comtom.bc.server.repository.entity.LogUser;
import com.comtom.bc.server.repository.entity.ProgramArea;
import com.comtom.bc.server.repository.entity.ProgramFiles;
import com.comtom.bc.server.repository.entity.ProgramInfo;
import com.comtom.bc.server.repository.entity.ProgramStrategy;
import com.comtom.bc.server.repository.entity.ProgramTime;
import com.comtom.bc.server.repository.entity.RegionArea;
import com.comtom.bc.server.repository.entity.SchemeEbr;
import com.comtom.bc.server.repository.entity.SchemeInfo;
import com.comtom.bc.server.repository.entity.VSchemeFlow;
import com.comtom.bc.server.req.SchemeAuditReq;
import com.comtom.bc.server.req.SchemeFlowReq;
import com.comtom.bc.server.req.SchemeQueryReq;
import com.comtom.bc.server.req.SchemeUpdateReq;
import com.comtom.bc.server.service.DispatchFlowService;
import com.comtom.bc.server.service.EbdService;
import com.comtom.bc.server.service.LogUserService;
import com.comtom.bc.server.service.ParamService;
import com.comtom.bc.server.service.SchemeService;
import com.comtom.bc.server.service.base.BaseService;


/**
 * 调度方案-业务逻辑处理接口定义
 * 
 * @author zhucanhui
 *
 */
@Service("SchemeService")
public class SchemeServiceImpl extends BaseService implements SchemeService {

	/**
	 * 日志记录器
	 */
	private static final Logger logger = LoggerFactory.getLogger(SchemeServiceImpl.class);

	@Autowired
	private DispatchFlowService dispatchFlowService;

	@Autowired
	private EbdService ebdService;

	@Autowired
	private ParamService paramService;

	@Autowired
	private LogUserService logUserService;

	@Autowired
	private SchemeDAO schemeDAO;

	@Autowired
	private SchemeEbrDAO schemeEbrDAO;

	@Autowired
	private ProgramDAO programDAO;

	@Autowired
	private DispatchFlowDAO dispatchFlowDAO;

	@Autowired
	private ProgramAreaDAO programAreaDAO;

	@Autowired
	private ProgramStrategyDAO programStrategyDAO;

	@Autowired
	private ProgramFilesDAO programFilesDAO;

	@Autowired
	private ProgramTimeDAO programTimeDAO;

	@Autowired
	private RegionAreaDAO regionAreaDAO;

	@Autowired
	private EbrPlatformDAO ebrPlatformDAO;

	@Autowired
	private EbrBsDAO ebrBsDAO;

	@Autowired
	private EbdDAO ebdDAO;

	@Autowired
	private EbdFileDAO ebdFileDAO;

	@Autowired
	private EbmDAO ebmDAO;

	@Autowired
	private EbmAuxiliaryDAO ebmAuxiliaryDAO;

	@Autowired
	private EbmDispatchDAO ebmDispatchDAO;

	@Autowired
	private FileInfoDAO fileInfoDAO;

	@Autowired
	private VSchemeFlowDAO vSchemeFlowDAO;

	/**
	 * 根据条件查询调度方案列表
	 * 
	 * @param req
	 * @return Page<SchemeInfo>
	 */
	public Page<SchemeInfo> getScheme(SchemeQueryReq req) {

		return schemeDAO.findAll(getSchemeSpec(req),
				buildPageRequest(req.getPageNum(), req.getPageSize(), getSort()));
	}

	/**
	 * 根据条件查询调度方案流程信息列表
	 * 
	 * @param req
	 * @return Page<VSchemeFlow>
	 */
	public Page<VSchemeFlow> getSchemeFlow(SchemeFlowReq req) {

		Page<VSchemeFlow> vFlowPages = vSchemeFlowDAO.findAll(getSchemeFlowSpec(req),
				buildPageRequest(req.getPageNum(), req.getPageSize(), getFlowSort()));

		for (VSchemeFlow vSchemeFlow : vFlowPages) {
			StringBuffer bufTime = new StringBuffer();
			StringBuffer bufArea = new StringBuffer();

			Long programId = vSchemeFlow.getProgramId();
			if (CommonUtil.isNotEmpty(programId)) {
				ProgramStrategy programStrategy = programStrategyDAO.findByProgramId(programId);
				if (CommonUtil.isNotEmpty(programStrategy)) {
					Long strategyId = programStrategy.getStrategyId();
					Date playTime = programStrategy.getPlayTime();
					Date startDate = programStrategy.getStartTime();

					// 播放日期为空，则取开始日期为播发日期
					if (CommonUtil.isEmpty(playTime) && CommonUtil.isNotEmpty(startDate)) {
						playTime = startDate;
					}

					if (CommonUtil.isNotEmpty(strategyId)) {
						List<ProgramTime> timeList = programTimeDAO.findByStrategyId(strategyId);
						bufTime.append(DateUtil.format(playTime, DateUtil.DATE_PATTERN.YYYY_MM_DD));
						bufTime.append(Constants.BLANK_SPLIT);
						for (int i = 0; i < timeList.size(); i++) {
							ProgramTime programTime = timeList.get(i);
							String startTime = programTime.getStartTime();
							bufTime.append(startTime);

							if (i < timeList.size() - 1) {
								bufTime.append(Constants.COMMA_SPLIT);
							}
						}
					}
				}

				List<ProgramArea> areaList = programAreaDAO.findByProgramId(programId);
				for (int i = 0; i < areaList.size(); i++) {
					ProgramArea programArea = areaList.get(i);
					RegionArea regionArea = regionAreaDAO.findOne(programArea.getAreaCode());
					bufArea.append(regionArea.getAreaName());

					if (i < areaList.size() - 1) {
						bufArea.append(Constants.COMMA_SPLIT);
					}
				}
			}

			if(StringUtils.isNotEmpty(vSchemeFlow.getEbmId())){  //上级平台下发的消息，获取覆盖区域，开始时间，发布机构
				Ebm ebm = ebmDAO.findOne(vSchemeFlow.getEbmId());
				vSchemeFlow.setStartTime(DateUtil.format(ebm.getStartTime(),DateUtil.DATE_PATTERN.YYYY_MM_DD_HH_MM_SS));
				bufArea = new StringBuffer();
				String[] areaCodes = vSchemeFlow.getAreaCode().split(Constants.COMMA_SPLIT+"");
				for (int i = 0; i < areaCodes.length; i++) {
					RegionArea regionArea = regionAreaDAO.findOne(areaCodes[i]);
					bufArea.append(regionArea.getAreaName());

					if (i < areaCodes.length - 1) {
						bufArea.append(Constants.COMMA_SPLIT);
					}
				}
				vSchemeFlow.setAreaName(bufArea.toString());
				vSchemeFlow.setReleaseOrgName(ebm.getSendName());
			}else{
				vSchemeFlow.setStartTime(bufTime.toString());
				vSchemeFlow.setAreaName(bufArea.toString());
			}
			SchemeInfo schemeInfo = schemeDAO.findOne(vSchemeFlow.getSchemeId());
			vSchemeFlow.setInfoId(schemeInfo.getInfoId()); //信息接入Id
		}

		return vFlowPages;

	}

	/**
	 * 根据调度方案Id,获取调度方案详情
	 */
	public SchemeInfo getSchemeDetail(SchemeQueryReq req) {

		// Step.1 获取方案信息
		Integer schemeId = req.getSchemeId();
		SchemeInfo schemeInfo = schemeDAO.findOne(schemeId);

		// Step.2 获取方案资源信息
		List<SchemeEbr> schemeEbrList = schemeEbrDAO.findBySchemeId(schemeId);
		for (SchemeEbr schemeEbr : schemeEbrList) {
			if (schemeEbr.getEbrType().equals(Constants.EBR_TYPE_PLATFORM)) {
				EbrPlatform ebrPlatform = ebrPlatformDAO.findOne(schemeEbr.getEbrId());
				String areaCode = ebrPlatform.getAreaCode();

				if (CommonUtil.isNotEmpty(areaCode)) {
					RegionArea regionArea = regionAreaDAO.findOne(areaCode);
					if (regionArea != null) {
						ebrPlatform.setAreaName(regionArea.getAreaName());
					}
				}

				schemeEbr.setEbrPlatform(ebrPlatform);
			} else {
				EbrBroadcast ebrBroadcast = ebrBsDAO.findOne(schemeEbr.getEbrId());
				String areaCode = ebrBroadcast.getAreaCode();

				if (CommonUtil.isNotEmpty(areaCode)) {
					RegionArea regionArea = regionAreaDAO.findOne(areaCode);
					if (regionArea != null) {
						ebrBroadcast.setAreaName(regionArea.getAreaName());
					}
				}
				schemeEbr.setEbrBroadcast(ebrBroadcast);
			}
		}

		// Step.3 获取调度流程详情
		SchemeFlowReq schemeFlowReq = new SchemeFlowReq();
		schemeFlowReq.setSchemeId(schemeId);
		Page<VSchemeFlow> flowPage = getSchemeFlow(schemeFlowReq);

		schemeInfo.setEbrList(schemeEbrList);
		schemeInfo.setvSchemeFlow(flowPage.getContent().get(0));

		return schemeInfo;
	}

	/**
	 * 生成调度方案、资源发现和EBM消息(本级制作节目处理)<br>
	 * 异步调度
	 * 
	 * @return Future<SchemeInfo>
	 */
	@Async
	@Transactional
	public Future<SchemeInfo> asynDispatch(ProgramInfo programInfo) {

		if (logger.isDebugEnabled()) {
			logger.info("SchemeServiceImpl.generateDispatch start generate dispatch by Program.");
		}

		// 节目Id
		Long programId = programInfo.getProgramId();

		// Step.1 生成方案
		SchemeInfo schemeInfo = generateScheme(programInfo);

		// Step.2 资源发现优化
		int ebrCount = 0;
		if (schemeInfo != null) {
			ebrCount = matchEbr(programId, schemeInfo.getSchemeId());
		}

		// Step.3 生成EBM消息和数据包信息
		// I级事件，方案无需审核，直接生成调度分发EBM和EBD
		Integer eventLevel = programInfo.getProgramLevel();

		// todo 可增加配置，控制是否直接生成
		if (eventLevel.equals(FlowConstants.SEVERITY_1)) {
			int ebmCount = 0;
			if (ebrCount > 0) {
				ebmCount = generateEbm(programInfo, schemeInfo);
			}

			if (logger.isDebugEnabled()) {
				logger.info(
						"SchemeServiceImpl.generateDispatch match ebr count={}, generate ebm count={}.",
						ebrCount, ebmCount);
			}
		}

		// 记录操作日志
		// 日志内容
		StringBuffer buf = new StringBuffer();
		buf.append("节目【");
		buf.append(programInfo.getProgramName());
		buf.append("】生成调度方案和消息成功.");

		LogUser logUser = new LogUser();
		logUser.setUserName(programInfo.getAuditUser());
		logUser.setModule("调度控制");
		logUser.setOperation("调度方案生成");
		logUser.setLogTime(DateUtil.getDateTime(DateUtil.DATE_PATTERN.YYYY_MM_DD_HH_MM_SS));
		logUser.setContent(buf.toString());
		logUser.setClientIp(WebCxt.getClientIpAddr());
		logUser.setPortalType(Constants.DISPATCH_CLIENT);

		// 记录用户操作日志
		logUserService.save(logUser);

		return new AsyncResult<SchemeInfo>(schemeInfo);
	}

	/**
	 * 生成调度方案、资源发现和EBM消息(本级制作节目处理)<br>
	 * 同步调度
	 * 
	 * @return SchemeInfo
	 */
	@Transactional
	public SchemeInfo synDispatch(ProgramInfo programInfo) throws RuntimeException {

		if (logger.isDebugEnabled()) {
			logger.info("SchemeServiceImpl.generateDispatch start generate dispatch by Program.");
		}

		// 节目Id
		Long programId = programInfo.getProgramId();

		// 获取配置方案审核标识
		String schemeAuditFlag = this.getSchemeAuditFlag();
		Integer auditFlag = 1;
		if (CommonUtil.isNotEmpty(schemeAuditFlag)) {
			auditFlag = Integer.valueOf(schemeAuditFlag);
		}

		programInfo.setSchemeAuditFlag(auditFlag);

		// Step.1 生成方案
		SchemeInfo schemeInfo = generateScheme(programInfo);

		// Step.2 资源发现优化
		int ebrCount = 0;
		if (schemeInfo != null) {
			ebrCount = matchEbr(programId, schemeInfo.getSchemeId());
		} else {
			throw new ServiceFlowException(ErrorEnum.scheme_add_error);
		}

		// Step.3 生成EBM消息和数据包信息
		if (ebrCount > 0) {

			// I级事件，方案无需审核，直接生成调度分发EBM和EBD
			Integer eventLevel = programInfo.getProgramLevel();

			// 如果配置不需要方案审核或I级应急事件，则直接播发消息
			if (auditFlag.equals(Constants.AUDIT_NO) || eventLevel.equals(FlowConstants.SEVERITY_1)) {
				int ebmCount = generateEbm(programInfo, schemeInfo);

				if (ebmCount > 0) {
					if (logger.isDebugEnabled()) {
						logger.info(
								"SchemeServiceImpl.generateDispatch match ebr count={}, generate ebm count={}.",
								ebrCount, ebmCount);
					}

					// 记录操作日志
					// 日志内容
					StringBuffer buf = new StringBuffer();
					buf.append("节目【");
					buf.append(programInfo.getProgramName());
					buf.append("】生成调度方案和消息成功.");

					LogUser logUser = new LogUser();
					logUser.setUserName(programInfo.getAuditUser());
					logUser.setModule("调度控制");
					logUser.setOperation("调度方案生成");
					logUser.setLogTime(DateUtil
							.getDateTime(DateUtil.DATE_PATTERN.YYYY_MM_DD_HH_MM_SS));
					logUser.setContent(buf.toString());
					logUser.setClientIp(WebCxt.getClientIpAddr());
					logUser.setPortalType(Constants.DISPATCH_CLIENT);

					// 记录用户操作日志
					logUserService.save(logUser);

				} else {
					throw new ServiceFlowException(ErrorEnum.ebm_add_error);
				}
			}
		} else {
			// 无资源可用
			throw new ServiceFlowException(ErrorEnum.ebr_match_error);
		}

		return schemeInfo;
	}

	/**
	 * 生成调度方案、资源发现和EBM消息(解析上级数据包处理)
	 * 
	 * @return Future<SchemeInfo>
	 */
	@Async
	@Transactional
	public Future<SchemeInfo> asynDispatch(EBD ebd,Long flowId,Long infoId) throws RuntimeException {

		if (logger.isDebugEnabled()) {
			logger.info("SchemeServiceImpl.generateDispatch start generate dispatch by EBD.");
		}

		// Step.1 生成方案
		SchemeInfo schemeInfo = generateScheme(ebd,flowId,infoId);

		// Step.2 更新消息 关联调度方案Id
		String ebmId = ebd.getEBM().getEBMID();
		if (CommonUtil.isNotEmpty(ebmId)) {
			Ebm ebm = ebmDAO.findOne(ebmId);
			ebm.setSchemeId(schemeInfo.getSchemeId());
			ebm.setEbmState(Constants.EBM_STATE_CREATE);
			ebmDAO.save(ebm);
		}

		// Step.3 资源发现优化和生成Ebm调度资源记录
		if (schemeInfo != null) {
			matchEbr(ebd,flowId, schemeInfo.getSchemeId());
		}

		return new AsyncResult<SchemeInfo>(schemeInfo);
	}


	/**
	 * 消息接入审核通过后，生成调度方案、资源发现和EBM消息(解析上级数据包处理)
	 *
	 * @return Future<SchemeInfo>
	 */


	/**
	 * 调度方案生成<br>
	 * 根据数据包Id生成调度方案
	 * 
	 */
	public SchemeInfo generateScheme(EBD ebd,Long flowId,Long infoId) {

		String ebdId = ebd.getEBDID();
		String ebmId = ebd.getEBM().getEBMID();

		Integer severity = null;

		try {
			severity = ebd.getEBM().getMsgBasicInfo().getSeverity();
		} catch (RuntimeException e) {
			severity = EventSeverityEnum.common.getCode();
		}

		// Step.1 保存调度方案基本信息
		// 更新流程状态：方案生成
		DispatchFlow dispatchFlow = dispatchFlowService.updateFlow(flowId,
				FlowConstants.STAGE_RESPONSE, FlowConstants.STATE_SCHEME);

		// 获取调度方案名称
		String schemeName = FlowConstants.getSchemeName();

		// 根据节目Id获取节目区域信息
		BigDecimal square = new BigDecimal(0);
		BigDecimal population = new BigDecimal(0);

		EBM ebm = ebd.getEBM();
		if(ebm.getMsgContent()!=null && ebm.getMsgContent().getAreaCode()!=null) {
			String[] areaCodes = RegionUtil.areaLong2Short(ebm.getMsgContent().getAreaCode())
					.split(String.valueOf(Constants.COMMA_SPLIT));

			// 根据节目关联区域，计算区域覆盖面积、人口
			for (String areaCode : areaCodes) {
				// 获取区域详情
				RegionArea regionArea = regionAreaDAO.findOne(areaCode);
				if(regionArea == null){
					logger.info(areaCode + "区域在系统中不存在!");
				}else {
					square = square.add(regionArea.getAreaSquare());
					population = population.add(regionArea.getAreaPopulation());
				}
			}
		}
		SchemeInfo schemeInfo = new SchemeInfo();
		schemeInfo.setSchemeTitle(schemeName);
		schemeInfo.setCreateTime(new Date());
		schemeInfo.setFlowId(dispatchFlow.getFlowId());
		schemeInfo.setEbdId(ebdId);
		schemeInfo.setEbmId(ebmId);
		schemeInfo.setTotalArea(square);
		schemeInfo.setTotalPopu(population);
		schemeInfo.setInfoId(infoId);
		// 获取配置方案审核标识
		String schemeAuditFlag = this.getSchemeAuditFlag();
		Integer auditFlag = Constants.AUDIT_YES;
		if (CommonUtil.isNotEmpty(schemeAuditFlag)) {
			auditFlag = Integer.valueOf(schemeAuditFlag);
		}

		// 如果是应急I级事件或方案不需要审核，系统自动审核通过
		if (auditFlag.equals(Constants.AUDIT_NO)
				|| severity.equals(EventSeverityEnum.red.getCode())) {
			schemeInfo.setAuditResult(Constants.AUDIT_PASS);
			schemeInfo.setAuditOpinion(Constants.AUDIT_PASS_STR);
			schemeInfo.setAuditUser(Constants.AUDIT_USER);
			schemeInfo.setState(Constants.SCHEME_STATE_SUBMIT);
		} else {
			schemeInfo.setAuditResult(Constants.AUDIT_INIT);
			schemeInfo.setState(Constants.SCHEME_STATE_CREATE);
		}

		schemeInfo = schemeDAO.save(schemeInfo);
		return schemeInfo;
	}

	/**
	 * 调度方案生成<br>
	 * 根据节目Id生成调度方案
	 * 
	 * @return SchemeInfo
	 */
	@Transactional
	public SchemeInfo generateScheme(ProgramInfo programInfo) {

		Long programId = programInfo.getProgramId();

		// Step.1 保存调度方案基本信息
		// 更新流程状态：方案生成
		DispatchFlow dispatchFlow = dispatchFlowService.updateFlow(programId, null,
				FlowConstants.STAGE_RESPONSE, FlowConstants.STATE_SCHEME);

		// 获取调度方案名称
		String schemeName = FlowConstants.getSchemeName();

		// 根据节目Id获取节目区域信息
		BigDecimal square = new BigDecimal(0);
		BigDecimal population = new BigDecimal(0);
		List<ProgramArea> areaList = programAreaDAO.findByProgramId(programId);

		// 根据节目关联区域，计算区域覆盖面积、人口
		for (ProgramArea programArea : areaList) {

			// 获取区域详情
			RegionArea regionArea = regionAreaDAO.findOne(programArea.getAreaCode());

			BigDecimal areaSquare = regionArea.getAreaSquare();
			BigDecimal areaPopulation = regionArea.getAreaPopulation();

			if (areaSquare != null) {
				square = square.add(areaSquare);
			} else {
				square = new BigDecimal(1);
			}

			if (areaPopulation != null) {
				population = population.add(areaPopulation);
			} else {
				population = new BigDecimal(1);
			}
		}

		SchemeInfo schemeInfo = new SchemeInfo();
		schemeInfo.setSchemeTitle(schemeName);
		schemeInfo.setCreateTime(new Date());
		schemeInfo.setFlowId(dispatchFlow.getFlowId());
		schemeInfo.setProgramId(programId);
		schemeInfo.setTotalArea(square);
		schemeInfo.setTotalPopu(population);
		schemeInfo.setState(Constants.SCHEME_STATE_CREATE);
		schemeInfo.setInfoId(programInfo.getInfoId());
		// I级事件，方案无需审核，直接生成调度分发EBM和EBD
		Integer eventLevel = programInfo.getProgramLevel();

		// I级事件或方案不需要审核，或节目类型为演练 则直接设置审核通过参数
		if (programInfo.getSchemeAuditFlag().equals(Constants.AUDIT_NO)
				|| eventLevel.equals(FlowConstants.SEVERITY_1) || programInfo.getProgramType() >= Constants.PROGRAM_TYPE_DRILL) {
			schemeInfo.setAuditResult(Constants.AUDIT_PASS);
			schemeInfo.setAuditOpinion(Constants.AUDIT_PASS_STR);
			schemeInfo.setAuditTime(new Date());
			schemeInfo.setAuditUser("admin");
		} else {
			schemeInfo.setAuditResult(Constants.AUDIT_INIT);
		}
		schemeInfo = schemeDAO.save(schemeInfo);

		return schemeInfo;
	}

	/**
	 * 资源发现优化<br>
	 * 
	 * @param programId
	 * @return SchemeInfo
	 */
	public int matchEbr(Long programId, Integer schemeId) {

		// Step.1 资源发现、优化
		// 获取节目信息
		ProgramInfo programInfo = programDAO.findOne(programId);
		Integer eventLevel = programInfo.getProgramLevel();

		// 更新流程状态：方案优化
		DispatchFlow dispatchFlow = dispatchFlowDAO.findByRelatedProgramId(programId);
		dispatchFlow.setFlowStage(FlowConstants.STAGE_RESPONSE);
		dispatchFlow.setFlowState(FlowConstants.STATE_SCHEME_ADJUST);
		dispatchFlow.setUpdateTime(new Date());
		dispatchFlowDAO.save(dispatchFlow);

		// 根据节目Id获取节目区域信息
		List<ProgramArea> areaList = programAreaDAO.findByProgramId(programId);
		List<String> areaCodeList = new ArrayList<String>();
		for (ProgramArea programArea : areaList) {
			areaCodeList.add(programArea.getAreaCode().trim());
		}

		// 获取当前平台信息
		String platEbrId = getEbrPlatFormID();
		String platAreaCode = getEbrPlatFormArea();

		// 方案关联资源
		List<SchemeEbr> schemeEbrList = new ArrayList<SchemeEbr>();

		// 匹配应急广播平台
		List<EbrPlatform> psList = matchPsEbr(areaCodeList, eventLevel, platEbrId, platAreaCode);

		BigDecimal square = new BigDecimal(0);
		BigDecimal population = new BigDecimal(0);

		if (psList != null && !psList.isEmpty()) {
			for (EbrPlatform ebrPlatform : psList) {
				SchemeEbr schemeEbr = new SchemeEbr();
				schemeEbr.setSchemeId(schemeId);
				schemeEbr.setEbrId(ebrPlatform.getPsEbrId());
				schemeEbr.setEbrType(Constants.EBR_TYPE_PLATFORM);
				schemeEbr.setEbrArea(ebrPlatform.getAreaCode());

				BigDecimal psSquare = ebrPlatform.getSquare();
				BigDecimal psPopulation = ebrPlatform.getPopulation();

				if (CommonUtil.isNotEmpty(psSquare)) {
					square = square.add(psSquare);
				} else {
					square = square.add(new BigDecimal(1));
				}

				if (CommonUtil.isNotEmpty(psPopulation)) {
					population = population.add(psPopulation);
				} else {
					population = population.add(new BigDecimal(1));
				}

				schemeEbrList.add(schemeEbr);
			}
		}

		// 匹配播出系统
		List<EbrBroadcast> bsList = matchBsEbr(areaCodeList, eventLevel, platEbrId, platAreaCode);

		if (bsList != null && !bsList.isEmpty()) {
			for (EbrBroadcast ebrBroadcast : bsList) {
				SchemeEbr schemeEbr = new SchemeEbr();
				schemeEbr.setSchemeId(schemeId);
				schemeEbr.setEbrId(ebrBroadcast.getBsEbrId());
				schemeEbr.setEbrType(Constants.EBR_TYPE_EWBS_BS);
				schemeEbr.setEbrArea(ebrBroadcast.getAreaCode());

				BigDecimal bsSquare = ebrBroadcast.getSquare();
				BigDecimal bsPopulation = ebrBroadcast.getPopulation();

				if (CommonUtil.isNotEmpty(bsSquare)) {
					square = square.add(bsSquare);
				} else {
					square = square.add(new BigDecimal(1));
				}

				if (CommonUtil.isNotEmpty(bsPopulation)) {
					population = population.add(bsPopulation);
				} else {
					population = population.add(new BigDecimal(1));
				}

				schemeEbrList.add(schemeEbr);
			}
		}

		// 如果无资源可用
		if (schemeEbrList.isEmpty()) {
			return 0;
		}

		// 更新方案预评估效果
		SchemeInfo schemeInfo = schemeDAO.findOne(schemeId);
		BigDecimal precent = new BigDecimal(100);
		schemeInfo.setEbrArea(square);
		schemeInfo.setEbrPopu(population);
		schemeInfo.setAreaPercent(MathUtil.divide(square, schemeInfo.getTotalArea(), 2).multiply(
				precent));
		schemeInfo.setPopuPercent(MathUtil.divide(population, schemeInfo.getTotalPopu(), 2)
				.multiply(precent));
		schemeInfo = schemeDAO.save(schemeInfo);

		// 保存方案关联资源
		List<SchemeEbr> ebrList = schemeEbrDAO.save(schemeEbrList);

		if (ebrList != null && ebrList.size() > 0) {
			return ebrList.size();
		}

		return 0;
	}

	/**
	 * 资源发现优化<br>
	 * 
	 * @param ebd
	 * @param
	 * @return int
	 */
	public int matchEbr(EBD ebd,Long flowId, Integer schemeId) {

		// Step.1 资源发现、优化
		// 获取EBM信息
		EBM ebm = ebd.getEBM();
		Integer eventLevel = ebm.getMsgBasicInfo().getSeverity();

		// 更新流程状态：方案优化
		dispatchFlowService.updateFlow(flowId, FlowConstants.STAGE_RESPONSE,
				FlowConstants.STATE_SCHEME_ADJUST);

		// 获取区域信息
		List<String> areaCodeList = new ArrayList<String>();
		String[] areaCodes = RegionUtil.areaLong2Short(ebm.getMsgContent().getAreaCode())
				.split(String.valueOf(Constants.COMMA_SPLIT));

		for (String areaCode : areaCodes) {
			RegionArea regionArea = regionAreaDAO.findOne(areaCode.trim());
			if(regionArea == null) {
				continue;
			}
			areaCodeList.add(areaCode.trim());
		}

		// 获取当前平台的级别
		String localPsAreaCode = this.getEbrPlatFormArea();
		String localPsEbrId = this.getEbrPlatFormID();

		// 方案关联资源
		List<SchemeEbr> schemeEbrList = new ArrayList<SchemeEbr>();
		// EBM调度资源
		List<EbmDispatch> ebmDispatchList = new ArrayList<EbmDispatch>();

		// 匹配应急广播平台
		List<EbrPlatform> psList = matchPsEbr(areaCodeList, eventLevel, localPsEbrId,
				localPsAreaCode);

		BigDecimal square = new BigDecimal(0);
		BigDecimal population = new BigDecimal(0);

		if (psList != null && !psList.isEmpty()) {
			for (EbrPlatform ebrPlatform : psList) {

				String psEbrId = ebrPlatform.getPsEbrId();

				// 方案资源记录
				SchemeEbr schemeEbr = new SchemeEbr();
				schemeEbr.setSchemeId(schemeId);
				schemeEbr.setEbrId(psEbrId);
				schemeEbr.setEbrType(Constants.EBR_TYPE_PLATFORM);
				schemeEbr.setEbrArea(ebrPlatform.getAreaCode());

				square = square.add(ebrPlatform.getSquare());
				population = population.add(ebrPlatform.getPopulation());

				schemeEbrList.add(schemeEbr);

				// EBM调度资源记录
				EbmDispatch ebmDispatch = new EbmDispatch();
				ebmDispatch.setPsEbrId(psEbrId);
				ebmDispatch.setEbmId(ebm.getEBMID());
				ebmDispatch.setState(0);
				ebmDispatch.setEbdId(ebd.getEBDID());

				ebmDispatchList.add(ebmDispatch);
			}
		}

		// 匹配播出系统
		List<EbrBroadcast> bsList = matchBsEbr(areaCodeList, eventLevel, localPsEbrId, localPsAreaCode);

		if (bsList != null && !bsList.isEmpty()) {
			for (EbrBroadcast ebrBroadcast : bsList) {
				SchemeEbr schemeEbr = new SchemeEbr();
				schemeEbr.setSchemeId(schemeId);
				schemeEbr.setEbrId(ebrBroadcast.getBsEbrId());
				schemeEbr.setEbrType(Constants.EBR_TYPE_EWBS_BS);
				schemeEbr.setEbrArea(ebrBroadcast.getAreaCode());

				square = square.add(ebrBroadcast.getSquare());
				population = population.add(ebrBroadcast.getPopulation());

				schemeEbrList.add(schemeEbr);

				// EBM调度资源记录
				EbmDispatch ebmDispatch = new EbmDispatch();
				ebmDispatch.setPsEbrId(localPsEbrId);
				ebmDispatch.setBsEbrId(ebrBroadcast.getBsEbrId());
				ebmDispatch.setEbmId(ebm.getEBMID());
				ebmDispatch.setState(0);
				ebmDispatch.setEbdId(ebd.getEBDID());

				ebmDispatchList.add(ebmDispatch);
			}
		}

		// 如果无资源可用
		if (schemeEbrList.isEmpty()) {
			return 0;
		}

		// 更新方案预评估效果
		SchemeInfo schemeInfo = schemeDAO.findOne(schemeId);
		schemeInfo.setAreaPercent(MathUtil.divide(square, schemeInfo.getTotalArea(), 2));
		schemeInfo.setPopuPercent(MathUtil.divide(population, schemeInfo.getTotalPopu(), 2));
		schemeInfo = schemeDAO.save(schemeInfo);

		// 保存方案关联资源
		List<SchemeEbr> ebrList = schemeEbrDAO.save(schemeEbrList);

		// 保存EBM调度资源
		ebmDispatchDAO.save(ebmDispatchList);

		if (ebrList != null && ebrList.size() > 0) {
			return ebrList.size();
		}

		return 0;
	}

	/**
	 * 匹配平台资源
	 * 
	 * @param areaList
	 * @param eventLevel
	 * @return List<EbrPlatform>
	 */
	public List<EbrPlatform> matchPsEbr(List<String> areaList, Integer eventLevel,
			String platEbrId, String platAreaCode) {

		// 区域匹配
		for (String areaCode : areaList) {
			RegionArea regionArea = regionAreaDAO.findOne(areaCode);
			if(regionArea == null) {
				continue;
			}
			String targetAreaCode = regionArea.getAreaCode();
			// Integer areaLevel = regionArea.getAreaLevel();

			// 目标不区域等于当前平台区域，则为全区域播发
			if (targetAreaCode.equals(platAreaCode)) {
				// 全区域播发，匹配当前平台下所有下级平台
				return ebrPlatformDAO.findAll(getSubPsSpec(targetAreaCode));
			} else if (platAreaCode.length() == 2) {
				// 省级平台，直接匹配市级平台
				return ebrPlatformDAO.findAll(getPsSpec(targetAreaCode.substring(0, 4), platEbrId));
			} else if (platAreaCode.length() == 4) {
				// 市级，直接匹配县级
				return ebrPlatformDAO.findAll(getPsSpec(targetAreaCode.substring(0, 6), platEbrId));
			}
			// 县级，无需匹配平台

			// 增加运行图匹配，1级事件以外，需要匹配运行图

		}

		return null;
	}

	/**
	 * 匹配播出系统资源
	 * 
	 * @param areaList
	 * @param eventLevel
	 * @param platAreaCode
	 * @return List<EbrBroadcast>
	 */
	public List<EbrBroadcast> matchBsEbr(List<String> areaList, Integer eventLevel,
			String platEbrId, String platAreaCode) {
		List<EbrBroadcast> ebrBsList=new ArrayList<EbrBroadcast>();
		// 如果播发区域包括本级平台区域编码，则匹配本平台播出系统
		// 获取本级平台符合条件的播出系统
		Set<String> areaSet=new HashSet<String>();
		// 如果需要本级播发
		areaSet.add(platAreaCode);
		for (String areaCode : areaList) {
			if(areaCode.length() > 6){
				areaCode = areaCode.substring(0, 6);
			}
			areaSet.add(areaCode);
		}
		/*// 如果需要本级播发，匹配本级播出系统
		if (platAreaCode.equals(areaCode)) {
			return ebrBsDAO.findAll(getBsSpec(platAreaCode, platEbrId));
		} else {
			// 如果播发区域是区县以下级别，直接取区县码
			if (areaCode.length() > 6) {
				areaCode = areaCode.substring(0, 6);
			}
			
			return ebrBsDAO.findAll(getBsSpec(areaCode, platEbrId));
		}*/
		for(Iterator<String> it=areaSet.iterator();it.hasNext();){
			String areaCode=it.next();
			List<EbrBroadcast> _ebrBsList=ebrBsDAO.findAll(getBsSpec(areaCode, platEbrId));
			if(_ebrBsList!=null&&!_ebrBsList.isEmpty()){
				ebrBsList.addAll(_ebrBsList);
			}
		}

		return ebrBsList;
	}

	/**
	 * 根据EBD生成和方案生成Ebd记录
	 * 
	 * @param schemeInfo
	 * @return int
	 */
	public int generateEbd(EBD ebdPack, SchemeInfo schemeInfo) {

		EBM ebm = ebdPack.getEBM();
		String ebmId = ebm.getEBMID();

		// 消息调度资源数据
		List<SchemeEbr> schemeEbrList = schemeEbrDAO.findBySchemeId(schemeInfo.getSchemeId());
		for (SchemeEbr schemeEbr : schemeEbrList) {
			String ebrType = schemeEbr.getEbrType();

			// 如果是应急广播平台资源
			if (ebrType.equals(Constants.EBR_TYPE_PLATFORM)) {
				EbmDispatch ebmDispatch = new EbmDispatch();
				ebmDispatch.setPsEbrId(schemeEbr.getEbrId());
				ebmDispatch.setEbmId(ebmId);
				ebmDispatch = ebmDispatchDAO.save(ebmDispatch);
			}
			// 根据方案资源，生成调度EBD数据包
			Ebd ebd = new Ebd();
			String ebdId = this.getEbdId();
			ebd.setEbdVersion(Constants.EBD_VERSION);
			ebd.setEbdId(ebdId);
			ebd.setEbdType(EBDType.EBM.name());
			ebd.setEbdSrcEbrId(this.findValueByKey(Constants.EBR_PLATFORM_ID));
			ebd.setEbdDestEbrId(schemeEbr.getEbrId());
			ebd.setEbdName(ebdId);
			ebd.setEbdSrcUrl(getPlatFormUrl());
			ebd.setSendFlag(Constants.SEND_FLAG);
			ebd.setEbdTime(new Date());
			ebd.setEbdState(Constants.EBD_STATE_CREATE);
			ebd.setFlowId(schemeInfo.getFlowId());
			ebd.setEbmId(ebmId);

			// 创建EBD
			ebd = ebdDAO.save(ebd);

			return 1;
		}

		return 0;
	}

	/**
	 * 生成EBM消息(播发)
	 * 
	 * @return int
	 */
	public int generateEbm(ProgramInfo programInfo, SchemeInfo schemeInfo) {

		// 定义EBM消息列表
		List<Ebm> ebmList = new ArrayList<Ebm>();

		// 获取消息基本信息
		String lanCode = programInfo.getLanguageCode();
		String content = programInfo.getProgramContent();
		String eventType = programInfo.getEbmEventType();
		String eventDesc = programInfo.getEbmEventDesc();
		Integer severity = programInfo.getProgramLevel();
		Long programId = programInfo.getProgramId();

		// 获取消息播发区域
		List<ProgramArea> areas = programAreaDAO.findByProgramId(programId);
		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < areas.size(); i++) {
			ProgramArea programArea = areas.get(i);
			buf.append(programArea.getAreaCode());

			if (i < areas.size() - 1) {
				buf.append(Constants.COMMA_SPLIT);
			}
		}

		ProgramStrategy strategy = programStrategyDAO.findByProgramId(programId);
		Integer strategyType = strategy.getStrategyType();
		String startTime = null;

		if (strategyType.equals(StrategyType.onetime.getValue())) {
			startTime = DateUtil.format(strategy.getPlayTime(), DateUtil.DATE_PATTERN.YYYY_MM_DD);
		} else {
			Date vStartTime = strategy.getStartTime();
			Date currDate = null;

			SimpleDateFormat sf = new SimpleDateFormat(DateUtil.DATE_PATTERN.YYYY_MM_DD);
			Calendar calendar = Calendar.getInstance();
			String currDateStr = sf.format(calendar.getTime());

			try {
				currDate = sf.parse(currDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			// 有效期开始大于等于当前日期，则取开始时期，否则取当前日期
			if (vStartTime.getTime() >= currDate.getTime()) {
				startTime = DateUtil.format(vStartTime, DateUtil.DATE_PATTERN.YYYY_MM_DD);
			} else {
				startTime = DateUtil.format(currDate, DateUtil.DATE_PATTERN.YYYY_MM_DD);
			}

		}

		List<ProgramTime> timeList = programTimeDAO.findByStrategyId(strategy.getStrategyId());
		for (ProgramTime programTime : timeList) {

			String embId = this.getEbmId();

			// 根据节目策略时间段
			Ebm ebm = new Ebm();
			ebm.setEbmId(embId); // 根据平台Id生成
			ebm.setEventType(eventType);
			ebm.setMsgTitle(programInfo.getProgramName());
			ebm.setMsgDesc(content);
			ebm.setMsgLanguageCode(lanCode);
			ebm.setSeverity(severity);
			ebm.setAreaCode(buf.toString());
			ebm.setProgramNum(programId.intValue());
			ebm.setStartTime(DateUtil.stringToDate(startTime + Constants.BLANK_SPLIT
					+ programTime.getStartTime()));
			ebm.setEndTime(DateUtil.stringToDate(startTime + Constants.BLANK_SPLIT
					+ programTime.getOverTime()));
			ebm.setMsgType(1); // 请求播发
			ebm.setSendName(programInfo.getReleaseOrgName());
			ebm.setSenderCode(programInfo.getReleaseOrgCode());
			ebm.setCreateTime(new Date());
			ebm.setFlowId(schemeInfo.getFlowId());
			ebm.setEbmState(Constants.EBM_STATE_CREATE); // 新建
			ebm.setSchemeId(schemeInfo.getSchemeId());
			ebm.setSendFlag(Constants.SEND_FLAG);

			// 消息辅助数据
			List<ProgramFiles> filesList = programFilesDAO.findByProgramId(programId);
			List<FileInfo> fileInfoList = new ArrayList<FileInfo>();

			// 文本内容为空，节目文件列表不为空
			if (CommonUtil.isEmpty(content) && filesList != null && !filesList.isEmpty()) {
				for (ProgramFiles programFiles : filesList) {
					FileInfo fileInfo = fileInfoDAO.findOne(programFiles.getFileId());
					fileInfoList.add(fileInfo);

					EbmAuxiliary ebmAuxiliary = new EbmAuxiliary();
					ebmAuxiliary.setAuxiliaryType(Constants.EBM_AUXILIARY_MP3);
					ebmAuxiliary.setAuxiliaryDesc(fileInfo.getFileDesc());
					ebmAuxiliary.setAuxiliarySize(fileInfo.getByteSize());
					ebmAuxiliary.setAuxiliaryDigest(fileInfo.getMd5Code());
					ebmAuxiliary.setEbmId(embId);

					ebmAuxiliary = ebmAuxiliaryDAO.save(ebmAuxiliary);
				}
			}

			// 消息调度资源数据
			List<SchemeEbr> schemeEbrList = schemeEbrDAO.findBySchemeId(schemeInfo.getSchemeId());
			for (SchemeEbr schemeEbr : schemeEbrList) {

				// 根据方案资源，生成调度EBD数据包
				Ebd ebd = new Ebd();
				String ebdId = this.getEbdId();
				ebd.setEbdVersion(Constants.EBD_VERSION);
				ebd.setEbdId(ebdId);
				ebd.setEbdType(EBDType.EBM.name());
				ebd.setEbdSrcEbrId(this.findValueByKey(Constants.EBR_PLATFORM_ID));
				ebd.setEbdDestEbrId(schemeEbr.getEbrId());
				ebd.setEbdName(FileNameUtil.generateTarFileName(ebdId));
				ebd.setEbdSrcUrl(getPlatFormUrl());
				ebd.setSendFlag(Constants.SEND_FLAG);
				ebd.setEbdTime(new Date());
				ebd.setEbdState(Constants.EBD_STATE_CREATE);
				ebd.setFlowId(schemeInfo.getFlowId());
				ebd.setEbmId(ebm.getEbmId());

				// 创建EBD
				ebd = ebdDAO.save(ebd);

				// 资源类型
				String ebrType = schemeEbr.getEbrType();

				// 如果是应急广播平台资源
				if (ebrType.equals(Constants.EBR_TYPE_PLATFORM)) {
					EbmDispatch ebmDispatch = new EbmDispatch();
					ebmDispatch.setPsEbrId(schemeEbr.getEbrId());
					ebmDispatch.setEbmId(ebm.getEbmId());
					ebmDispatch.setEbdId(ebdId);
					ebmDispatch.setState(Constants.DISPATCH_STATE_INIT);

					ebmDispatch = ebmDispatchDAO.save(ebmDispatch);
				} else if (ebrType.equals(Constants.EBR_TYPE_EWBS_BS)) {
					EbmDispatch ebmDispatch = new EbmDispatch();

					// 获取本级平台Id
					String ebrPsId = this.getEbrPlatFormID();

					// 获取本级平台EBRID
					ebmDispatch.setPsEbrId(ebrPsId);
					ebmDispatch.setBsEbrId(schemeEbr.getEbrId());
					ebmDispatch.setEbmId(ebm.getEbmId());
					ebmDispatch.setEbdId(ebdId);
					ebmDispatch.setState(Constants.DISPATCH_STATE_INIT);

					ebmDispatch = ebmDispatchDAO.save(ebmDispatch);
				}

				// TODO
				// 如果是播出系统，需要判断播出系统是具备接收消息能力
				// 播出系统没有消息接收能力，则获取对应的适配器URL

				// 保存EBD关联文件信息
				for (FileInfo fileInfo : fileInfoList) {
					String filePath = paramService.getFilePath(fileInfo);
					EbdFile ebdFile = new EbdFile();
					ebdFile.setEbdId(ebd.getEbdId());
					ebdFile.setFileUrl(filePath);
					ebdFileDAO.save(ebdFile);
				}
			}

			ebmList.add(ebm);
		}

		List<Ebm> ebms = ebmDAO.save(ebmList);
		if (ebms != null && !ebms.isEmpty()) {
			// 开始调度
			// ebdService.dispatchEbdPack(Constants.EBM_STATE_CREATE);
			return ebms.size();
		} else {
			return 0;
		}
	}

	/**
	 * 调度方案调整
	 * 
	 * @param req
	 * @return SchemeInfo
	 */
	@Transactional
	public SchemeInfo adjustScheme(SchemeUpdateReq req) {

		Integer schemeId = req.getSchemeId();

		// 获取调度方案信息
		SchemeInfo schemeInfo = schemeDAO.findOne(schemeId);

		// 先删除关联的资源再新增关联的资源信息
		schemeEbrDAO.deleteSchemeEbr(schemeId);
		schemeEbrDAO.save(req.getSchemeEbrList());

		Long programId = schemeInfo.getProgramId();
		String ebdId = schemeInfo.getEbdId();

		// 更新流程状态：方案调整
		dispatchFlowService.updateFlow(programId, ebdId, FlowConstants.STAGE_RESPONSE,
				FlowConstants.STATE_SCHEME_ADJUST);

		return schemeInfo;
	}

	/**
	 * 调度方案审核
	 * 
	 * @param req
	 * @return SchemeInfo
	 */
	@Transactional
	public SchemeInfo auditScheme(SchemeAuditReq req) {

		Integer schemeId = req.getSchemeId();

		// 获取调度方案信息
		SchemeInfo schemeInfo = schemeDAO.findOne(schemeId);

		// 设置审核信息并更新
		schemeInfo.setAuditResult(req.getAuditResult());
		schemeInfo.setAuditOpinion(req.getAuditOpinion());
		schemeInfo.setAuditTime(new Date());
		schemeInfo.setAuditUser(req.getAuditUser());
		schemeInfo = schemeDAO.save(schemeInfo);

		if (schemeInfo != null) {

			// 根据方案Id获取EBM，如果已存在，则不需要生成
			List<Ebm> ebmList = ebmDAO.findBySchemeId(schemeId);

			if (ebmList == null || ebmList.isEmpty()) {
				// 根据方案获取节目信息
				Long programId = schemeInfo.getProgramId();
				ProgramInfo programInfo = null;

				if (programId != null) {
					programInfo = programDAO.findOne(programId);
				}

				if (programInfo != null) {
					int ebmCount = generateEbm(programInfo, schemeInfo);

					if (logger.isDebugEnabled()) {
						logger.info("SchemeServiceImpl.generateDispatch generate ebm count={}.",
								ebmCount);
					}
				}
			}

			Long programId = schemeInfo.getProgramId();
			String ebdId = schemeInfo.getEbdId();

			if (schemeInfo.getAuditResult().equals(Constants.AUDIT_PASS)) {
				// 更新流程状态：方案审核通过
				dispatchFlowService.updateFlow(programId, ebdId, FlowConstants.STAGE_RESPONSE,
						FlowConstants.STATE_SCHEME_AUDIT_YES);
			} else if (schemeInfo.getAuditResult().equals(Constants.AUDIT_NO_PASS)) {
				// 更新流程状态：方案审核不通过
				dispatchFlowService.updateFlow(programId, ebdId, FlowConstants.STAGE_RESPONSE,
						FlowConstants.STATE_SCHEME_AUDIT_NO);
			}
		}

		return schemeInfo;
	}

	/**
	 * 取消调度方案
	 * 
	 * @param req
	 * @return SchemeInfo
	 */
	@Transactional
	public SchemeInfo cancelScheme(Long programId) {

		// 获取调度方案信息
		SchemeInfo schemeInfo = schemeDAO.findByProgramId(programId);
		schemeInfo.setState(Constants.SCHEME_STATE_CANCEL);

		return schemeDAO.save(schemeInfo);
	}

	/**
	 * 取消EBD和EBM
	 * 
	 * @param schemeId
	 * @return Ebd
	 */
	public boolean cancelEbdEbm(Integer schemeId) {

		// 获取EBM
		List<Ebm> ebmList = ebmDAO.findBySchemeId(schemeId);
		List<Ebd> ebdList = null;

		try {
			for (Ebm ebm : ebmList) {

				// 设置EBM状态为取消
				ebm.setEbmState(Constants.EBM_STATE_CANCEL);
				ebm = ebmDAO.save(ebm);

				// 创建取消EBM
				Ebm newEbm = newEbm(ebm);

				// 获取EBD
				ebdList = ebdDAO.findByEbmId(ebm.getEbmId());

				// 如果EBD数据包已发送，则需要生成取消播发EBM和EBD
				for (Ebd ebd : ebdList) {

					// 设置EBD状态为取消
					ebd.setEbdState(Constants.EBD_STATE_CANCEL);
					ebd = ebdDAO.save(ebd);

					// 创建取消EBD
					newEbd(ebd, newEbm);
				}

			}
		} catch (Exception e) {
			throw new ServiceFlowException(ErrorEnum.ebm_cancel_error);
		}

		return true;
	}

	/**
	 * 新建EBD
	 * 
	 * @param ebdList
	 */
	private Ebd newEbd(Ebd ebd, Ebm ebm) {

		Ebd newEbd = new Ebd();
		String ebdId = this.getEbdId();

		List<EbmDispatch> ebmDispatchList = ebmDispatchDAO.findByEbmId(ebm.getEbmId());

		// 新建EBD
		newEbd.setEbdId(ebdId);
		newEbd.setEbdVersion(ebd.getEbdVersion());
		newEbd.setEbdType(ebd.getEbdType());
		newEbd.setEbdName(FileNameUtil.generateTarFileName(ebdId));
		newEbd.setEbdSrcEbrId(ebd.getEbdSrcEbrId());
		newEbd.setEbdDestEbrId(ebd.getEbdDestEbrId());
		newEbd.setEbdTime(new Date());
		newEbd.setEbdSrcUrl(ebd.getEbdSrcUrl());
		newEbd.setSendFlag(ebd.getSendFlag());
		newEbd.setEbdState(Constants.EBD_STATE_CREATE);
		newEbd.setFlowId(ebd.getFlowId());
		newEbd.setEbmId(ebm.getEbmId());

		for (EbmDispatch ebmDispatch : ebmDispatchList) {
			ebmDispatch.setEbdId(ebdId);
			ebmDispatchDAO.save(ebmDispatch);
		}

		return ebdDAO.save(newEbd);
	}

	/**
	 * 新建EBM
	 * 
	 * @param
	 */
	private Ebm newEbm(Ebm ebm) {
		List<EbmDispatch> ebmDispatchList = ebmDispatchDAO.findByEbmId(ebm.getEbmId());

		Ebm newEbm = new Ebm();
		String ebmId = this.getEbmId();

		// 新建EBM
		newEbm.setEbmId(ebmId);
		newEbm.setEbmVersion(ebm.getEbmVersion());
		newEbm.setMsgType(Constants.CANCEL_MSG);
		newEbm.setSendName(ebm.getSendName());
		newEbm.setSenderCode(ebm.getSenderCode());
		newEbm.setEventType(ebm.getEventType());
		newEbm.setSeverity(ebm.getSeverity());
		newEbm.setStartTime(ebm.getStartTime());
		newEbm.setEndTime(ebm.getEndTime());
		newEbm.setMsgLanguageCode(ebm.getMsgLanguageCode());
		newEbm.setMsgTitle(ebm.getMsgTitle());
		newEbm.setMsgDesc(ebm.getMsgDesc());
		newEbm.setAreaCode(ebm.getAreaCode());
		newEbm.setAreaName(ebm.getAreaName());
		newEbm.setProgramNum(ebm.getProgramNum());
		newEbm.setFlowId(ebm.getFlowId());
		newEbm.setEbmState(Constants.EBM_STATE_CREATE);
		newEbm.setCreateTime(new Date());
		newEbm.setSchemeId(ebm.getSchemeId());
		newEbm.setSendFlag(ebm.getSendFlag());
		newEbm.setRelatedEbmId(ebm.getEbmId());
		newEbm = ebmDAO.save(newEbm);

		// EBM调度信息
		for (EbmDispatch ebmDispatch : ebmDispatchList) {
			EbmDispatch newEbmDispatch = new EbmDispatch();
			newEbmDispatch.setPsEbrId(ebmDispatch.getPsEbrId());
			newEbmDispatch.setBsEbrId(ebmDispatch.getBsEbrId());
			newEbmDispatch.setEbmId(ebmId);
			newEbmDispatch.setState(Constants.DISPATCH_STATE_INIT);
			ebmDispatchDAO.save(newEbmDispatch);
		}

		return newEbm;
	}

	/**
	 * 根据方案Id获取方案信息
	 * 
	 * @return
	 */
	public SchemeInfo findOne(Integer schemeId) {
		return schemeDAO.findOne(schemeId);
	}

	/**
	 * 调度方案提交
	 * 
	 * @return SchemeInfo
	 */
	public SchemeInfo submitScheme(SchemeQueryReq req) {

		Integer schemeId = req.getSchemeId();
		SchemeInfo schemeInfo = schemeDAO.findOne(schemeId);
		schemeInfo.setState(Constants.SCHEME_STATE_SUBMIT);

		Long programId = schemeInfo.getProgramId();
		String ebdId = schemeInfo.getEbdId();

		// 更新流程状态：方案审核
		dispatchFlowService.updateFlow(programId, ebdId, FlowConstants.STAGE_RESPONSE,
				FlowConstants.STATE_SCHEME_AUDIT);

		return schemeDAO.save(schemeInfo);
	}

	/**
	 * 根据节目Id获取对应调度方案信息
	 * 
	 * @param programId
	 * @return SchemeInfo
	 */
	public SchemeInfo findByProgramId(Long programId) {
		return schemeDAO.findByProgramId(programId);
	}

	/**
	 * 平台资源查询Specification
	 * 
	 * @param areaCode
	 * @return Specification<EbrPlatform>
	 */
	private Specification<EbrPlatform> getPsSpec(final String areaCode, final String platEbrId) {

		return new Specification<EbrPlatform>() {
			public Predicate toPredicate(Root<EbrPlatform> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();

				predicate.getExpressions().add(
						cb.equal(r.<String> get("psState"), Constants.EBR_STATE_RUNNING));

				// 根据区域编码匹配
				if (CommonUtil.isNotEmpty(areaCode)) {
					predicate.getExpressions().add(
							cb.like(r.<String> get("areaCode"), "%" + StringUtils.trim(areaCode)
									+ "%"));
				}

				// 排除本级平台资源
				if (CommonUtil.isNotEmpty(platEbrId)) {
					predicate.getExpressions().add(
							cb.notEqual(r.<String> get("psEbrId"), platEbrId));
				}

				return predicate;
			}
		};
	}

	/**
	 * 平台资源查询Specification
	 * 
	 * @param areaCode
	 * @return Specification<EbrPlatform>
	 */
	private Specification<EbrPlatform> getSubPsSpec(final String areaCode) {

		return new Specification<EbrPlatform>() {
			public Predicate toPredicate(Root<EbrPlatform> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();

				predicate.getExpressions().add(
						cb.equal(r.<String> get("psState"), Constants.EBR_STATE_RUNNING));

				// 根据区域编码匹配,匹配当前平台下级平台
				if (CommonUtil.isNotEmpty(areaCode)) {
					predicate.getExpressions().add(
							cb.like(r.<String> get("areaCode"), StringUtils.trim(areaCode) + "__"));
				}

				return predicate;
			}
		};
	}

	/**
	 * 平台资源查询Specification
	 * 
	 * @param areaCode
	 * @return Specification<EbrPlatform>
	 */
	private Specification<EbrBroadcast> getBsSpec(final String areaCode, final String relatedPsEbrId) {

		return new Specification<EbrBroadcast>() {
			public Predicate toPredicate(Root<EbrBroadcast> r, CriteriaQuery<?> q,
					CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();

				predicate.getExpressions().add(
						cb.equal(r.<String> get("bsState"), Constants.EBR_STATE_RUNNING));

				if (CommonUtil.isNotEmpty(areaCode)) {
					Predicate p = cb.like(r.<String> get("areaCode"), StringUtils.trim(areaCode)
							+ "%");
					predicate.getExpressions().add(p);
				}

				if (CommonUtil.isNotEmpty(relatedPsEbrId)) {
					Predicate p = cb.equal(r.<String> get("relatedPsEbrId"), relatedPsEbrId);
					predicate.getExpressions().add(p);
				}

				return predicate;
			}
		};
	}

	/**
	 * 实现多条件模糊和带日期区间查询Specification
	 * 
	 * @param req
	 * @return Specification<SchemeInfo>
	 */
	private Specification<SchemeInfo> getSchemeSpec(SchemeQueryReq req) {

		// 查询参数
		final String schemeTitle = req.getSchemeTitle();
		final Date startDate = req.getStartDate();
		final Date overDate = req.getOverDate();
		final Integer state = req.getState();
		final Integer auditResult = req.getAuditResult();

		return new Specification<SchemeInfo>() {
			@Override
			public Predicate toPredicate(Root<SchemeInfo> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (CommonUtil.isNotEmpty(schemeTitle)) {
					predicate.getExpressions().add(
							cb.like(r.<String> get("schemeTitle"),
									"%" + StringUtils.trim(schemeTitle) + "%"));
				}

				if (CommonUtil.isNotEmpty(auditResult)) {
					predicate.getExpressions().add(
							cb.equal(r.<Integer> get("auditResult"), auditResult));
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
	 * 实现多条件模糊和带日期区间查询Specification
	 * 
	 * @param req
	 * @return Specification<VSchemeFlow>
	 */
	private Specification<VSchemeFlow> getSchemeFlowSpec(SchemeFlowReq req) {

		// 查询参数
		final Integer schemeId = req.getSchemeId();
		final String schemeTitle = req.getSchemeTitle();
		final Date startDate = req.getStartDate();
		final Date overDate = req.getOverDate();
		final String sourceOrg = req.getSourceOrg();
		final Integer flowStage = req.getFlowStage();
		final Integer flowState = req.getFlowState();

		return new Specification<VSchemeFlow>() {
			@Override
			public Predicate toPredicate(Root<VSchemeFlow> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (CommonUtil.isNotEmpty(schemeTitle)) {
					predicate.getExpressions().add(
							cb.like(r.<String> get("schemeTitle"),
									"%" + StringUtils.trim(schemeTitle) + "%"));
				}

				if (CommonUtil.isNotEmpty(sourceOrg)) {
					predicate.getExpressions().add(
							cb.like(r.<String> get("sourceOrg"), "%" + StringUtils.trim(sourceOrg)
									+ "%"));
				}

				if (CommonUtil.isNotEmpty(schemeId)) {
					predicate.getExpressions().add(cb.equal(r.<Integer> get("schemeId"), schemeId));
				}

				if (CommonUtil.isNotEmpty(flowStage)) {
					predicate.getExpressions().add(
							cb.equal(r.<Integer> get("flowStage"), flowStage));
				}

				if (CommonUtil.isNotEmpty(flowState)) {
					predicate.getExpressions().add(
							cb.equal(r.<Integer> get("flowState"), flowState));
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
	 * 获取排序对象Sort
	 * 
	 * @return Sort
	 */
	private Sort getFlowSort() {
		Order orderTime = new Order(Direction.DESC, "createTime");
		List<Order> orders = new ArrayList<Order>();
		orders.add(0, orderTime);
		Sort sort = new Sort(orders);
		return sort;
	}
}