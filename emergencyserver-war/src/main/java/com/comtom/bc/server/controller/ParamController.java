package com.comtom.bc.server.controller;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.result.ResultCode;
import com.comtom.bc.common.result.ResultKey;
import com.comtom.bc.common.result.ResultMsg;
import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.server.asset.RedisUtil;
import com.comtom.bc.server.repository.entity.*;
import com.comtom.bc.server.req.ParamQueryReq;
import com.comtom.bc.server.req.ParamUpdateReq;
import com.comtom.bc.server.req.ProgramQueryReq;
import com.comtom.bc.server.req.ProgramUpdateReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.comtom.bc.common.result.JsonResult;
import com.comtom.bc.common.utils.JsonUtil;
import com.comtom.bc.server.asset.WebCxt;
import com.comtom.bc.server.service.ParamService;

import javax.servlet.http.HttpServletRequest;

/**
 * 字典参数控制器实现
 * 
 * @author zhucanhui
 *
 */
@Api(value = "键值参数")
@RestController
@RequestMapping(value = "api/param")
public class ParamController {

	@Autowired
	private ParamService paramService;

	@Autowired
	private RedisUtil redisUtil;

	@ApiOperation(value = "键值参数", notes = "查询键值参数")
	@RequestMapping(value = "get", method = RequestMethod.GET)
	public ResponseEntity<String> getParamByKey(@RequestParam String key,
			@RequestParam String jsessionid) {

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		String value = paramService.findValueByKey(key);
		dataMap.put(key, value);

		// 返回处理结果
		resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
	}


	@ApiOperation(value = "查询参数列表", notes = "业务参数列表")
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public ResponseEntity<String> list(HttpServletRequest request,
											 @ModelAttribute ParamQueryReq req, @RequestParam String jsessionid) {

		SysUser sysUser = (SysUser) redisUtil.get(jsessionid);
		String account = sysUser.getAccount();
		req.setAccount(account);

		req.setCatalogId("4ee1d6e48b31487b849a72cd03d2512a");


		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		Page<SysParam> page = paramService.list(req);


		dataMap.put(ResultKey.PARAM_LIST_KEY, page.getContent());
		resultJson.setCurrPage(page.getNumber()+1);
		resultJson.setTotalPage(page.getTotalPages());
		resultJson.setTotalCount(page.getTotalElements());

		// 返回处理结果
		resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}


	@ApiOperation(value = "更新参数", notes = "参数更新")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public ResponseEntity<String> updateProgram(HttpServletRequest request,
												@RequestBody ParamUpdateReq req, @RequestParam String jsessionid) {

		SysUser sysUser = (SysUser) redisUtil.get(jsessionid);
		String account = sysUser.getAccount();

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		SysParam paramInfo = paramService.updateProgram(req);

		// 用户操作日志
		request.setAttribute(Constants.ACCOUNT, account);
		request.setAttribute(Constants.PORTAL_TYPE, req.getPortalType());

		if (paramInfo != null) {
			// 设置用户操作日志参数和内容
			String logContent = "用户【" + account + "】更新参数【" + paramInfo.getKey() + "】成功.";
			request.setAttribute(Constants.USER_LOG, logContent);
		} else {
			resultJson.setResultCode(ResultCode.FAILURE);
			resultJson.setResultMsg(ResultMsg.RESULT_FAILURE);
			resultJson.setDataMap(dataMap);
			return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
		}

		// 返回处理结果
		dataMap.put(ResultKey.PROGRAM_INFO_KEY, paramInfo);
		resultJson.setDataMap(dataMap);

		return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
	}


}
