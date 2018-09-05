package com.comtom.bc.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.annotation.NoRecordLog;
import com.comtom.bc.common.result.JsonResult;
import com.comtom.bc.common.result.ResultCode;
import com.comtom.bc.common.result.ResultKey;
import com.comtom.bc.common.result.ResultMsg;
import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.common.utils.JsonUtil;
import com.comtom.bc.dispatch.FlowConstants;
import com.comtom.bc.server.asset.RedisUtil;
import com.comtom.bc.server.asset.WebCxt;
import com.comtom.bc.server.controller.base.BaseController;
import com.comtom.bc.server.repository.entity.EbmResBs;
import com.comtom.bc.server.repository.entity.SchemeInfo;
import com.comtom.bc.server.repository.entity.SysUser;
import com.comtom.bc.server.repository.entity.VSchemeFlow;
import com.comtom.bc.server.req.ProgramQueryReq;
import com.comtom.bc.server.req.SchemeAuditReq;
import com.comtom.bc.server.req.SchemeFlowReq;
import com.comtom.bc.server.req.SchemeQueryReq;
import com.comtom.bc.server.req.SchemeUpdateReq;
import com.comtom.bc.server.service.DispatchFlowService;
import com.comtom.bc.server.service.ProgramService;
import com.comtom.bc.server.service.SchemeEbrService;
import com.comtom.bc.server.service.SchemeService;

/**
 * 调度方案控制器实现
 * 
 * @author zhucanhui
 *
 */
@Api(value = "调度方案")
@RestController
@RequestMapping(value = "api/scheme")
public class SchemeController extends BaseController {

	@Autowired
	private DispatchFlowService dispatchFlowService;

	@Autowired
	private SchemeService schemeService;

	@Autowired
	private ProgramService programService;

	@Autowired
	private SchemeEbrService schemeEbrService;

	@Autowired
	RedisUtil redisUtil;

	/**
	 * 查询调度方案列表
	 * 
	 * @param request
	 * @param req
	 *            查询方案请求参数
	 * @return ResponseEntity<String>
	 */
	@ApiOperation(value = "调度方案", notes = "调度方案查询")
	@RequestMapping(value = "get", method = RequestMethod.POST)
	public ResponseEntity<String> getScheme(HttpServletRequest request,
			@RequestBody SchemeQueryReq req, @RequestParam String jsessionid) {

		SysUser sysUser = (SysUser) redisUtil.get(jsessionid);
		String account = sysUser.getAccount();
		req.setAccount(account);

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		Page<SchemeInfo> pages = schemeService.getScheme(req);

		// 返回处理结果
		dataMap.put(ResultKey.SCHEME_LIST_KEY, pages.getContent());
		dataMap.put(ResultKey.TOTAL_COUNT_KEY, pages.getSize());
		resultJson.setDataMap(dataMap);
		resultJson.setTotalCount(pages.getTotalElements());
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}

	/**
	 * 查询调度方案列表
	 * 
	 * @param request
	 * @param req
	 *            查询方案请求参数
	 * @return ResponseEntity<String>
	 */
	@ApiOperation(value = "调度方案", notes = "调度方案流程查询")
	@RequestMapping(value = "getSchemeFlow", method = RequestMethod.POST)
	public ResponseEntity<String> getSchemeFlow(HttpServletRequest request,
			@RequestBody SchemeFlowReq req, @RequestParam String jsessionid) {

		SysUser sysUser = (SysUser) redisUtil.get(jsessionid);
		String account = sysUser.getAccount();
		req.setAccount(account);

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		Page<VSchemeFlow> pages = schemeService.getSchemeFlow(req);

		// 返回处理结果
		dataMap.put(ResultKey.SCHEME_LIST_KEY, pages.getContent());
		dataMap.put(ResultKey.TOTAL_COUNT_KEY, pages.getSize());
		resultJson.setDataMap(dataMap);
		resultJson.setCurrPage(pages.getNumber()+1);
		resultJson.setTotalPage(pages.getTotalPages());
		resultJson.setTotalCount(pages.getTotalElements());
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}

	/**
	 * 查询调度方案详情
	 * 
	 * @param request
	 * @param req
	 *            查询方案请求参数
	 * @return ResponseEntity<String>
	 */
	@NoRecordLog
	@ApiOperation(value = "调度方案", notes = "调度方案详情查看")
	@RequestMapping(value = "getDetail", method = RequestMethod.POST)
	public ResponseEntity<String> getSchemeDetail(HttpServletRequest request,
			@RequestBody SchemeQueryReq req, @RequestParam String jsessionid) {

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		SchemeInfo schemeInfo = schemeService.getSchemeDetail(req);

		// 返回处理结果
		dataMap.put(ResultKey.SCHEME_INFO_KEY, schemeInfo);
		resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}

	/**
	 * 查询调度方案详情
	 * 
	 * @param request
	 * @param req
	 *            查询方案请求参数
	 * @return ResponseEntity<String>
	 */
	@NoRecordLog
	@ApiOperation(value = "调度方案", notes = "调度方案详情查看")
	@RequestMapping(value = "getDetailByProgram", method = RequestMethod.POST)
	public ResponseEntity<String> getSchemeDetailByProgram(HttpServletRequest request,
			@RequestBody ProgramQueryReq req, @RequestParam String jsessionid) {

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		Long programId = req.getProgramId();

		// 业务逻辑处理
		SchemeInfo scheme = schemeService.findByProgramId(programId);

		if (CommonUtil.isNotEmpty(scheme)) {
			SchemeQueryReq schemeQueryReq = new SchemeQueryReq();
			schemeQueryReq.setSchemeId(scheme.getSchemeId());
			SchemeInfo schemeInfo = schemeService.getSchemeDetail(schemeQueryReq);

			// 返回处理结果
			dataMap.put(ResultKey.SCHEME_INFO_KEY, schemeInfo);
		} else {
			// 返回处理结果
			dataMap.put(ResultKey.SCHEME_INFO_KEY, scheme);
		}

		resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}

	/**
	 * 统计当前方案关联数量（根据类型统计）
	 * 
	 * @param request
	 * @param req
	 *            查询方案请求参数
	 * @return ResponseEntity<String>
	 */
	@NoRecordLog
	@ApiOperation(value = "调度方案", notes = "调度资源统计")
	@RequestMapping(value = "calcEbr", method = RequestMethod.POST)
	public ResponseEntity<String> calcEbr(HttpServletRequest request,
			@RequestBody SchemeQueryReq req, @RequestParam String jsessionid) {

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		Integer schemeId = req.getSchemeId();
		Map<String, Long> clacEbrCount = schemeEbrService.calcEbr(schemeId);

		// 返回处理结果
		dataMap.put(ResultKey.EBR_COUNT_MAP, clacEbrCount);
		resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}

	/**
	 * 查询调度方案详情
	 * 
	 * @param request
	 * @param req
	 *            查询方案请求参数
	 * @return ResponseEntity<String>
	 */
	@NoRecordLog
	@ApiOperation(value = "调度方案", notes = "调度资源情况统计")
	@RequestMapping(value = "calcEbrDispatch", method = RequestMethod.POST)
	public ResponseEntity<String> calcEbrDispatch(HttpServletRequest request,
			@RequestBody SchemeQueryReq req, @RequestParam String jsessionid) {

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		Integer schemeId = req.getSchemeId();
		Map<String, Long> clacEbrDispatch = schemeEbrService.calcEbrDispatch(schemeId);

		// 返回处理结果
		dataMap.put(ResultKey.EBR_DISPATCH_COUNT_MAP, clacEbrDispatch);
		resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
	}

	/**
	 * 调度方案关联播发记录结果统计
	 * 
	 * @param request
	 * @param req
	 * @param jsessionid
	 * @return ResponseEntity<String>
	 */
	@NoRecordLog
	@ApiOperation(value = "调度方案", notes = "调度播发情况统计")
	@RequestMapping(value = "brdStat", method = RequestMethod.POST)
	public ResponseEntity<String> brdStatByScheme(HttpServletRequest request,
			@RequestBody SchemeQueryReq req, @RequestParam String jsessionid) {

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		Integer schemeId = req.getSchemeId();
		Map<String, List<EbmResBs>> calcBrdList = schemeEbrService.brdStatByScheme(schemeId);

		// 返回处理结果
		dataMap.put(ResultKey.BRD_RESULT_COUNT_LIST, calcBrdList);
		resultJson.setDataMap(dataMap);
		resultJson.setTotalCount(calcBrdList.size());
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}

	/**
	 * 调度方案调整
	 * 
	 * @param request
	 * @param req
	 * @param jsessionid
	 * @return ResponseEntity<String>
	 */
	@ApiOperation(value = "调度方案", notes = "调度方案调整")
	@RequestMapping(value = "adjust", method = RequestMethod.POST)
	public ResponseEntity<String> adjustScheme(HttpServletRequest request,
			@RequestBody SchemeUpdateReq req, @RequestParam String jsessionid) {

		SysUser sysUser = (SysUser) redisUtil.get(jsessionid);
		String account = sysUser.getAccount();
		req.setAccount(account);

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		SchemeInfo schemeInfo = schemeService.adjustScheme(req);

		// 用户操作日志
		request.setAttribute(Constants.ACCOUNT, account);
		request.setAttribute(Constants.PORTAL_TYPE, req.getPortalType());

		// 设置用户操作日志参数和内容
		String logContent = "用户【" + account + "】调整调度方案【" + schemeInfo.getSchemeTitle() + "】成功.";
		request.setAttribute(Constants.USER_LOG, logContent);

		// 返回处理结果
		dataMap.put(ResultKey.PROGRAM_INFO_KEY, schemeInfo);
		resultJson.setDataMap(dataMap);

		return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
	}

	/**
	 * 调度方案提交
	 * 
	 * @param request
	 * @param req
	 * @param jsessionid
	 * @return ResponseEntity<String>
	 */
	@ApiOperation(value = "调度方案", notes = "调度方案提交")
	@RequestMapping(value = "submit", method = RequestMethod.POST)
	public ResponseEntity<String> submitScheme(HttpServletRequest request,
			@RequestBody SchemeQueryReq req, @RequestParam String jsessionid) {

		SysUser sysUser = (SysUser) redisUtil.get(jsessionid);
		String account = sysUser.getAccount();
		req.setAccount(account);

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		SchemeInfo schemeInfo = schemeService.submitScheme(req);

		// 用户操作日志
		request.setAttribute(Constants.ACCOUNT, account);
		request.setAttribute(Constants.PORTAL_TYPE, req.getPortalType());

		// 设置用户操作日志参数和内容
		String logContent = "用户【" + account + "】提交调度方案【" + schemeInfo.getSchemeTitle() + "】成功.";
		request.setAttribute(Constants.USER_LOG, logContent);

		// 返回处理结果
		dataMap.put(ResultKey.PROGRAM_INFO_KEY, schemeInfo);
		resultJson.setDataMap(dataMap);

		return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
	}

	/**
	 * 调度方案审核
	 * 
	 * @param request
	 * @param req
	 *            调度方案审核请求参数
	 * @return ResponseEntity<String>
	 */
	@ApiOperation(value = "调度方案", notes = "调度方案审核")
	@RequestMapping(value = "audit", method = RequestMethod.POST)
	public ResponseEntity<String> auditScheme(HttpServletRequest request,
			@RequestBody SchemeAuditReq req, @RequestParam String jsessionid) {

		SysUser sysUser = (SysUser) redisUtil.get(jsessionid);
		String account = sysUser.getAccount();
		req.setAuditUser(account);

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 用户操作日志
		request.setAttribute(Constants.ACCOUNT, account);
		request.setAttribute(Constants.PORTAL_TYPE, req.getPortalType());

		// 业务逻辑处理
		SchemeInfo schemeInfo = schemeService.auditScheme(req);

		if (schemeInfo != null) {

			// 设置用户操作日志参数和内容
			String logContent = "用户【" + account + "】审核调度方案【" + schemeInfo.getSchemeTitle() + "】成功.";
			request.setAttribute(Constants.USER_LOG, logContent);

			// 节目审核完成，更新流程状态：审核通过 或 审核不通过
			Integer auditRuslt = schemeInfo.getAuditResult();

			if (auditRuslt.equals(Constants.AUDIT_PASS)) {
				dispatchFlowService.updateFlow(schemeInfo.getProgramId(), null,
						FlowConstants.STAGE_RESPONSE, FlowConstants.STATE_SCHEME_AUDIT_YES);
			} else if (auditRuslt.equals(Constants.AUDIT_NO_PASS)) {
				dispatchFlowService.updateFlow(schemeInfo.getProgramId(), null,
						FlowConstants.STAGE_RESPONSE, FlowConstants.STATE_SCHEME_AUDIT_NO);
			}

		} else {
			resultJson.setResultCode(ResultCode.FAILURE);
			resultJson.setResultMsg(ResultMsg.RESULT_FAILURE);
			resultJson.setDataMap(dataMap);

			return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
		}

		// 返回处理结果
		dataMap.put(ResultKey.PROGRAM_INFO_KEY, schemeInfo);
		resultJson.setDataMap(dataMap);

		return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);

	}
}
