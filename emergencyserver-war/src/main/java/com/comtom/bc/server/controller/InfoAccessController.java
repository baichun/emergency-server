package com.comtom.bc.server.controller;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.annotation.NoRecordLog;
import com.comtom.bc.common.result.JsonResult;
import com.comtom.bc.common.result.ResultCode;
import com.comtom.bc.common.result.ResultKey;
import com.comtom.bc.common.result.ResultMsg;
import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.common.utils.JsonUtil;
import com.comtom.bc.server.asset.RedisUtil;
import com.comtom.bc.server.asset.WebCxt;
import com.comtom.bc.server.controller.base.BaseController;
import com.comtom.bc.server.repository.entity.*;
import com.comtom.bc.server.req.InfoAccessQueryReq;
import com.comtom.bc.server.req.InfoAccessUpdateReq;
import com.comtom.bc.server.req.ProgramUpdateReq;
import com.comtom.bc.server.req.RegionAreaReq;
import com.comtom.bc.server.service.AreaService;
import com.comtom.bc.server.service.InfoAccessService;
import com.comtom.bc.server.service.ParamService;
import com.comtom.bc.server.service.SchemeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 信息接入控制器实现
 * 
 *
 */
@Api(value = "信息接入")
@RestController
@RequestMapping(value = "api/infoAccess")
public class InfoAccessController extends BaseController {

	@Autowired
	private InfoAccessService infoAccessService;

	@Autowired
	private RedisUtil redisUtil;



	@NoRecordLog
	@ApiOperation(value = "信息接入查询", notes = "信息接入查询")
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public ResponseEntity<String> findArea(HttpServletRequest request,
										   @RequestBody InfoAccessQueryReq req, @RequestParam String jsessionid) {


		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		Page<InfoAccess> page = infoAccessService.list(req);

		dataMap.put(ResultKey.INFOACCESS_LIST_KEY, page.getContent());
		resultJson.setTotalCount(page.getTotalElements());

		// 返回处理结果
		resultJson.setDataMap(dataMap);
		resultJson.setCurrPage(page.getNumber()+1);
		resultJson.setTotalPage(page.getTotalPages());
		resultJson.setTotalCount(page.getTotalElements());
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}

	@ApiOperation(value = "修改审核结果", notes = "修改审核结果")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public ResponseEntity<String> updateProgram(HttpServletRequest request,
												@RequestBody InfoAccessUpdateReq req, @RequestParam String jsessionid) {
		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();
		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);
		SysUser sysUser = (SysUser) redisUtil.get(jsessionid);
		String account = sysUser.getAccount();
		InfoAccess infoAccess=new InfoAccess();
		infoAccess.setAuditor(account);
		infoAccess.setAuditOpinion(req.getAuditOpinion());
		infoAccess.setAuditResult(req.getAuditResult());
		infoAccess.setInfoId(req.getInfoId());
		InfoAccess info = infoAccessService.update(infoAccess);
		if (info == null) {
			resultJson.setResultCode(ResultCode.FAILURE);
			resultJson.setResultMsg(ResultMsg.RESULT_FAILURE);
			resultJson.setDataMap(dataMap);
			return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
		}
		// 返回处理结果
		dataMap.put(ResultKey.INFO_ACCESS, info);
		resultJson.setDataMap(dataMap);

		return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
	}

	@ApiOperation(value="获取消息接入详情" , notes = "获取消息接入详情")
	@RequestMapping(value = "/info/{id}",method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> infoById(@PathVariable("id") Integer id, @RequestParam String jsessionid) {
		InfoAccess info = infoAccessService.getInfo(id);
		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);
		if (info == null) {
			resultJson.setResultCode(ResultCode.FAILURE);
			resultJson.setResultMsg(ResultMsg.RESULT_FAILURE);
			resultJson.setDataMap(dataMap);
			return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
		}
		dataMap.put(ResultKey.INFO_ACCESS, info);
		resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}
}
