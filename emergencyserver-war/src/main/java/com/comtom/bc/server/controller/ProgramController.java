package com.comtom.bc.server.controller;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.annotation.NoRecordLog;
import com.comtom.bc.common.result.JsonResult;
import com.comtom.bc.common.result.ResultCode;
import com.comtom.bc.common.result.ResultKey;
import com.comtom.bc.common.result.ResultMsg;
import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.common.utils.JsonUtil;
import com.comtom.bc.dispatch.FlowConstants;
import com.comtom.bc.dispatch.common.ErrorEnum;
import com.comtom.bc.dispatch.common.ServiceFlowException;
import com.comtom.bc.server.asset.RedisUtil;
import com.comtom.bc.server.asset.WebCxt;
import com.comtom.bc.server.controller.base.BaseController;
import com.comtom.bc.server.repository.dao.EbmDAO;
import com.comtom.bc.server.repository.dao.EbmDispatchDAO;
import com.comtom.bc.server.repository.dao.InfoAccessDAO;
import com.comtom.bc.server.repository.entity.*;
import com.comtom.bc.server.req.ProgramAuditReq;
import com.comtom.bc.server.req.ProgramQueryReq;
import com.comtom.bc.server.req.ProgramSaveReq;
import com.comtom.bc.server.req.ProgramUpdateReq;
import com.comtom.bc.server.service.DispatchFlowService;
import com.comtom.bc.server.service.ProgramService;
import com.comtom.bc.server.service.SchemeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 节目制播控制器实现
 * 
 * @author zhucanhui
 *
 */
@Api(value = "节目制播")
@RestController
@RequestMapping(value = "api/program")
public class ProgramController extends BaseController {

	/**
	 * 日志记录器
	 */
	private static final Logger logger = LoggerFactory.getLogger(ProgramController.class);

	@Autowired
	private DispatchFlowService dispatchFlowService;

	@Autowired
	private ProgramService programService;

	@Autowired
	private SchemeService schemeService;

	@Autowired
	private EbmDAO ebmDAO;

	@Autowired
	private EbmDispatchDAO ebmDispatchDAO;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private InfoAccessDAO infoAccessDAO;

	/**
	 * 根据节目信息、节目区域、节目文件和节目策略创建节目
	 * 
	 * @param request
	 * @param req
	 *            创建节目请求参数
	 * @return ResponseEntity<String>
	 */
	@ApiOperation(value = "节目制播", notes = "节目创建")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public ResponseEntity<String> saveProgram(HttpServletRequest request,
			@RequestBody ProgramSaveReq req, @RequestParam String jsessionid) {

		SysUser sysUser = (SysUser) redisUtil.get(jsessionid);
		String account = sysUser.getAccount();

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		if (CommonUtil.isEmpty(req.getCreateUser())) {
			req.setCreateUser(account);
		}

		Long programId = req.getProgramId();

		// 业务逻辑处理
		ProgramInfo programInfo = null;

		// 节目Id不为空，表示节目已新建，只更新状态为提交即可
		if (programId != null) {
			programInfo = programService.submitProgram(programId);
		} else {

			programInfo = programService.saveProgram(req);

			// 用户操作日志
			request.setAttribute(Constants.ACCOUNT, account);
			request.setAttribute(Constants.PORTAL_TYPE, req.getPortalType());

			if (programInfo != null) {

				// 设置用户操作日志参数和内容
				String logContent = "用户【" + account + "】创建节目【" + programInfo.getProgramName()
						+ "】成功.";
				request.setAttribute(Constants.USER_LOG, logContent);
			} else {
				resultJson.setResultCode(ResultCode.FAILURE);
				resultJson.setResultMsg(ResultMsg.RESULT_FAILURE);
				resultJson.setDataMap(dataMap);

				return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
			}
		}

		// 返回处理结果
		dataMap.put(ResultKey.PROGRAM_INFO_KEY, programInfo);
		resultJson.setDataMap(dataMap);

		return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
	}

	/**
	 * 查询节目列表
	 * 
	 * @param request
	 * @param req
	 *            查询节目请求参数
	 * @return ResponseEntity<String>
	 */
	@NoRecordLog
	@ApiOperation(value = "节目制播", notes = "节目列表查看")
	@RequestMapping(value = "get", method = RequestMethod.POST)
	public ResponseEntity<String> getProgram(HttpServletRequest request,
			@RequestBody ProgramQueryReq req, @RequestParam String jsessionid) {

		SysUser sysUser = (SysUser) redisUtil.get(jsessionid);
		String account = sysUser.getAccount();
		req.setAccount(account);

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		Page<ProgramInfo> page = programService.getProgramInfo(req);

		// 如果节目类型为演练，根据节目方案匹配资源，计算资源贯通率（根据资源通用反馈）
		for (ProgramInfo programInfo : page) {
			Integer programType = programInfo.getProgramType();
			if (programType.equals(3)) {
				Long programId = programInfo.getProgramId();
				Ebm ebm = ebmDAO.findByProgramNum(programId.intValue());
				if (CommonUtil.isNotEmpty(ebm)) {

					int successCount = 0;
					List<EbmDispatch> ebmDispatchList = ebmDispatchDAO.findByEbmId(ebm.getEbmId());
					int totalCount = ebmDispatchList.size();

					for (EbmDispatch ebmDispatch : ebmDispatchList) {
						// 获取Ebm调度状态，调度成功为1
						successCount = successCount + ebmDispatch.getState();
					}

					NumberFormat numberFormat = NumberFormat.getInstance();

					// 设置精确到小数点后2位
					numberFormat.setMaximumFractionDigits(2);
					String result = numberFormat.format((float) successCount / (float) totalCount
							* 100);
					programInfo.setCutThroughRate(result + "%");
				}
			}
		}


		dataMap.put(ResultKey.PROGRAM_LIST_KEY, page.getContent());
		resultJson.setCurrPage(page.getNumber()+1);
		resultJson.setTotalPage(page.getTotalPages());
		resultJson.setTotalCount(page.getTotalElements());

		// 返回处理结果
		resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}

	/**
	 * 查询节目详情
	 * 
	 * @param request
	 * @param req
	 *            查询节目请求参数
	 * @return ResponseEntity<String>
	 */
	@NoRecordLog
	@ApiOperation(value = "节目制播", notes = "节目详情查看")
	@RequestMapping(value = "getDetail", method = RequestMethod.POST)
	public ResponseEntity<String> getProgramDetail(HttpServletRequest request,
			@RequestBody ProgramQueryReq req, @RequestParam String jsessionid) {

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		ProgramInfo programInfo = programService.getProgramDetail(req.getProgramId());
		dataMap.put(ResultKey.PROGRAM_INFO_KEY, programInfo);

		// 返回处理结果
		resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}

	/**
	 * 克隆节目
	 *
	 * @param request
	 * @param req
	 *            查询节目请求参数
	 * @return ResponseEntity<String>
	 */
	@NoRecordLog
	@ApiOperation(value = "节目制播", notes = "节目克隆")
	@RequestMapping(value = "clone", method = RequestMethod.POST)
	public ResponseEntity<String> cloneProgram(HttpServletRequest request,
												   @RequestBody ProgramQueryReq req, @RequestParam String jsessionid) {
		SysUser sysUser = (SysUser) redisUtil.get(jsessionid);
		String account = sysUser.getAccount();
		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		ProgramInfo programInfo = programService.getProgramDetail(req.getProgramId());
		//dataMap.put(ResultKey.PROGRAM_INFO_KEY, programInfo);

		programInfo = programService.saveProgram(programInfo);

		// 用户操作日志
		request.setAttribute(Constants.ACCOUNT, account);
		request.setAttribute(Constants.PORTAL_TYPE, req.getPortalType());

		if (programInfo != null) {

			// 设置用户操作日志参数和内容
			String logContent = "用户【" + account + "】克隆节目【" + programInfo.getProgramName()
					+ "】成功.";
			request.setAttribute(Constants.USER_LOG, logContent);
		} else {
			resultJson.setResultCode(ResultCode.FAILURE);
			resultJson.setResultMsg(ResultMsg.RESULT_FAILURE);
			resultJson.setDataMap(dataMap);

			return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
		}

		// 返回处理结果
		resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
	}

	/**
	 * 演练信息及贯通率效果统计详情查询
	 * 
	 * @param request
	 * @param req
	 *            查询节目请求参数
	 * @return ResponseEntity<String>
	 */
	@NoRecordLog
	@ApiOperation(value = "节目制播", notes = "节目统计详情查看")
	@RequestMapping(value = "getStatDetail", method = RequestMethod.POST)
	public ResponseEntity<String> getStatDetail(HttpServletRequest request,
			@RequestBody ProgramQueryReq req, @RequestParam String jsessionid) {

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		ProgramInfo programInfo = programService.getProgramDetail(req.getProgramId());
		SchemeInfo schemeInfo = schemeService.findByProgramId(req.getProgramId());

		Long programId = programInfo.getProgramId();
		Ebm ebm = ebmDAO.findByProgramNum(programId.intValue());
		if (CommonUtil.isNotEmpty(ebm)) {

			Map<String, Integer> ebrStateMap = new HashMap<String, Integer>();
			int successCount = 0;
			int bsEbrCount = 0;
			int bsEbrSuccessCount = 0;
			int psEbrCount = 0;
			int psEbrSuccessCount = 0;
			List<EbmDispatch> ebmDispatchList = ebmDispatchDAO.findByEbmId(ebm.getEbmId());
			int totalCount = ebmDispatchList.size();

			for (EbmDispatch ebmDispatch : ebmDispatchList) {

				String bsEbrId = ebmDispatch.getBsEbrId();
				if (CommonUtil.isNotEmpty(bsEbrId)) {
					bsEbrCount = bsEbrCount + 1;
					bsEbrSuccessCount = bsEbrSuccessCount + ebmDispatch.getState();
				} else {
					psEbrCount = psEbrCount + 1;
					psEbrSuccessCount = psEbrSuccessCount + ebmDispatch.getState();
				}

				// 获取Ebm调度状态，调度成功为1
				successCount = successCount + ebmDispatch.getState();
			}

			NumberFormat numberFormat = NumberFormat.getInstance();

			// 设置精确到小数点后2位
			numberFormat.setMaximumFractionDigits(2);
			String result = numberFormat.format((float) successCount / (float) totalCount * 100);
			programInfo.setCutThroughRate(result + "%");

			if (CommonUtil.isNotEmpty(schemeInfo)) {
				programInfo.setSchemeId(schemeInfo.getSchemeId());
			}

			ebrStateMap.put("EbrTotalCount", totalCount);
			ebrStateMap.put("EbrSuccessCount", successCount);
			ebrStateMap.put("BsEbrCount", bsEbrCount);
			ebrStateMap.put("BsEbrSuccessCount", bsEbrSuccessCount);
			ebrStateMap.put("PsEbrCount", psEbrCount);
			ebrStateMap.put("PsEbrSuccessCount", psEbrSuccessCount);

			programInfo.setEbrStateMap(ebrStateMap);
		}

		dataMap.put(ResultKey.PROGRAM_INFO_KEY, programInfo);

		// 返回处理结果
		resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}

	/**
	 * 根据节目信息、节目区域、节目文件和节目策略更新节目
	 * 
	 * @param request
	 * @param req
	 *            节目请求参数
	 * @return ResponseEntity<String>
	 */
	@ApiOperation(value = "节目制播", notes = "节目更新")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public ResponseEntity<String> updateProgram(HttpServletRequest request,
			@RequestBody ProgramUpdateReq req, @RequestParam String jsessionid) {

		SysUser sysUser = (SysUser) redisUtil.get(jsessionid);
		String account = sysUser.getAccount();

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		ProgramInfo programInfo = programService.updateProgram(req);

		// 用户操作日志
		request.setAttribute(Constants.ACCOUNT, account);
		request.setAttribute(Constants.PORTAL_TYPE, req.getPortalType());

		if (programInfo != null) {

			// 设置用户操作日志参数和内容
			String logContent = "用户【" + account + "】更新节目【" + programInfo.getProgramName() + "】成功.";
			request.setAttribute(Constants.USER_LOG, logContent);
		} else {
			resultJson.setResultCode(ResultCode.FAILURE);
			resultJson.setResultMsg(ResultMsg.RESULT_FAILURE);
			resultJson.setDataMap(dataMap);

			return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
		}

		// 返回处理结果
		dataMap.put(ResultKey.PROGRAM_INFO_KEY, programInfo);
		resultJson.setDataMap(dataMap);

		return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
	}

	/**
	 * 节目审核
	 * 
	 * @param request
	 * @param req
	 *            节目请求参数
	 * @return ResponseEntity<String>
	 */
	@ApiOperation(value = "节目制播", notes = "节目审核")
	@RequestMapping(value = "audit", method = RequestMethod.POST)
	public ResponseEntity<String> auditProgram(HttpServletRequest request,
			@RequestBody ProgramAuditReq req, @RequestParam String jsessionid) {

		SysUser sysUser = (SysUser) redisUtil.get(jsessionid);
		String account = sysUser.getAccount();
		req.setAccount(account);

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 用户操作日志
		request.setAttribute(Constants.ACCOUNT, account);
		request.setAttribute(Constants.PORTAL_TYPE, req.getPortalType());

		ProgramInfo programInfo = null;

		try {
			// 业务逻辑处理
			programInfo = programService.auditProgram(req);
		} catch (ServiceFlowException e) {
			Integer errorCode = e.getErrorCode();
			if (errorCode.equals(ErrorEnum.ebr_match_error.getErrorCode())) {
				resultJson.setResultCode(ErrorEnum.ebr_match_error.getErrorCode());
				resultJson.setResultMsg(ErrorEnum.ebr_match_error.getErrorMsg());
				resultJson.setDataMap(dataMap);

				return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
			} else {
				logger.error("Audit program generate dispatch failed, Please check program information.");
			}
		}

		if (programInfo != null) {
			// 设置用户操作日志参数和内容
			String logContent = "用户【" + account + "】审核节目【" + programInfo.getProgramName() + "】成功.";
			request.setAttribute(Constants.USER_LOG, logContent);
		} else {
			resultJson.setResultCode(ResultCode.FAILURE);
			resultJson.setResultMsg(ResultMsg.RESULT_FAILURE);
			resultJson.setDataMap(dataMap);

			return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
		}

		// 返回处理结果
		dataMap.put(ResultKey.PROGRAM_INFO_KEY, programInfo);
		resultJson.setDataMap(dataMap);


		return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);

	}






	/**
	 * 根据节目Id取消节目
	 * 
	 * @param request
	 * @param req
	 *            节目请求参数
	 * @return ResponseEntity<String>
	 */
	@ApiOperation(value = "节目制播", notes = "节目取消")
	@RequestMapping(value = "cancel", method = RequestMethod.POST)
	public ResponseEntity<String> cancelProgram(HttpServletRequest request,
			@RequestBody ProgramQueryReq req, @RequestParam String jsessionid) {

		SysUser sysUser = (SysUser) redisUtil.get(jsessionid);
		String account = sysUser.getAccount();

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		ProgramInfo programInfo = null;
		try {
			// 业务逻辑处理
			programInfo = programService.cancelProgram(req.getProgramId());
		} catch (ServiceFlowException e) {
			resultJson.setResultCode(ResultCode.FAILURE);
			resultJson.setResultMsg(ResultMsg.RESULT_FAILURE);
			resultJson.setDataMap(dataMap);
			return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
		}

		// 用户操作日志
		request.setAttribute(Constants.ACCOUNT, account);
		request.setAttribute(Constants.PORTAL_TYPE, req.getPortalType());

		if (programInfo != null) {

			dispatchFlowService.updateFlow(programInfo.getProgramId(), null,
					FlowConstants.STAGE_CANCEL, FlowConstants.STATE_CANCEL);

			// 设置用户操作日志参数和内容
			String logContent = "用户【" + account + "】取消节目【" + programInfo.getProgramName() + "】成功.";
			request.setAttribute(Constants.USER_LOG, logContent);
		} else {
			resultJson.setResultCode(ResultCode.FAILURE);
			resultJson.setResultMsg(ResultMsg.RESULT_FAILURE);
			resultJson.setDataMap(dataMap);

			return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
		}

		// 返回处理结果
		dataMap.put(ResultKey.PROGRAM_INFO_KEY, programInfo);
		resultJson.setDataMap(dataMap);

		if (logger.isDebugEnabled()) {
			logger.info("ProgramController.cancelProgram cancel program successfully.");
		}

		return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
	}


	/**
	 * 用于演练执行
	 * @param request
	 * @param programId
	 * @param jsessionid
	 * @return
	 */
	@ApiOperation(value = "执行演练", notes = "执行演练")
	@RequestMapping(value = "execDrill", method = RequestMethod.POST)
	public ResponseEntity<String> execDrill(HttpServletRequest request,
												@RequestParam Long programId, @RequestParam String jsessionid) {
		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		SysUser sysUser = (SysUser) redisUtil.get(jsessionid);
		String account = sysUser.getAccount();

		ProgramInfo programDetail = programService.getProgramInfo(programId);  //若节目已经创建，并审核通过，代表已经执行过一遍
		if (programDetail != null && programDetail.getProgramType() >= Constants.PROGRAM_TYPE_DRILL && programDetail.getAuditResult() == Constants.AUDIT_PASS) {
			Ebm ebm = ebmDAO.findByProgramNum(programId.intValue());
			if(ebm!=null){
				ebm.setEbmState(Constants.EBM_STATE_CREATE);
				ebmDAO.save(ebm);
				return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
			}
		}

		ProgramAuditReq req=new ProgramAuditReq();
		req.setAuditResult(Constants.AUDIT_PASS);
		req.setAuditOpinion(Constants.AUDIT_PASS_STR);
		req.setAuditUser(account);
		req.setProgramId(programId);
		req.setAccount(account);


		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 用户操作日志
		request.setAttribute(Constants.ACCOUNT, Constants.AUDIT_USER);
		request.setAttribute(Constants.PORTAL_TYPE, 1);

		ProgramInfo programInfo = null;

		try {
			// 业务逻辑处理
			programInfo = programService.auditProgram(req);
		} catch (ServiceFlowException e) {
			Integer errorCode = e.getErrorCode();
			if (errorCode.equals(ErrorEnum.ebr_match_error.getErrorCode())) {
				resultJson.setResultCode(ErrorEnum.ebr_match_error.getErrorCode());
				resultJson.setResultMsg(ErrorEnum.ebr_match_error.getErrorMsg());
				resultJson.setDataMap(dataMap);

				return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
			} else {
				logger.error("Audit program generate dispatch failed, Please check program information.");
			}
		}

		if (programInfo != null) {
			// 设置用户操作日志参数和内容
			String logContent = "用户【" + Constants.AUDIT_USER + "】审核节目【" + programInfo.getProgramName() + "】成功.";
			request.setAttribute(account, logContent);
		} else {
			resultJson.setResultCode(ResultCode.FAILURE);
			resultJson.setResultMsg(ResultMsg.RESULT_FAILURE);
			resultJson.setDataMap(dataMap);

			return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
		}

		// 返回处理结果
		dataMap.put(ResultKey.PROGRAM_INFO_KEY, programInfo);
		resultJson.setDataMap(dataMap);


		return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);

	}
}
