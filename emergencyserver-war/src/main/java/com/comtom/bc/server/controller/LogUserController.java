package com.comtom.bc.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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

import com.comtom.bc.common.result.JsonResult;
import com.comtom.bc.common.result.ResultKey;
import com.comtom.bc.common.utils.JsonUtil;
import com.comtom.bc.server.asset.WebCxt;
import com.comtom.bc.server.repository.entity.LogUser;
import com.comtom.bc.server.req.LogUserReq;
import com.comtom.bc.server.service.LogUserService;

/**
 * 字典参数控制器实现
 * 
 * @author zhucanhui
 *
 */
@Api(value = "用户日志")
@RestController
@RequestMapping(value = "api/log")
public class LogUserController {

	@Autowired
	private LogUserService logUserService;

	@ApiOperation(value = "用户日志", notes = "用户日志查询")
	@RequestMapping(value = "get", method = RequestMethod.POST)
	public ResponseEntity<String> getLog(HttpServletRequest request, @RequestBody LogUserReq req,
			@RequestParam String jsessionid) {

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		Page<LogUser> logs = logUserService.getLogs(req);

		// 返回处理结果
		resultJson.setTotalCount(logs.getTotalElements());
		dataMap.put(ResultKey.LOG_LIST_KEY, logs.getContent());
		dataMap.put(ResultKey.TOTAL_COUNT_KEY, logs.getSize());
		resultJson.setDataMap(dataMap);
		resultJson.setCurrPage(logs.getNumber()+1);
		resultJson.setTotalPage(logs.getTotalPages());
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
	}
}
