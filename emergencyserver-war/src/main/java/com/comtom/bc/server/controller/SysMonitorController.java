package com.comtom.bc.server.controller;

import com.comtom.bc.common.result.JsonResult;
import com.comtom.bc.common.utils.ComputerMonitorUtil;
import com.comtom.bc.common.utils.JsonUtil;
import com.comtom.bc.server.asset.WebCxt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 系统监控控制器实现
 * 
 * @author zhucanhui
 *
 */
@Api(value = "系统监控")
@RestController
@RequestMapping(value = "api/sysMonitor")
public class SysMonitorController {


	@ApiOperation(value = "系统监控", notes = "系统监控")
	@RequestMapping(value = "get", method = RequestMethod.POST)
	public ResponseEntity<String> getMonitorData(HttpServletRequest request,
			@RequestParam String jsessionid) {

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理

		//当前系统的内存使用率
		double cpuUsage = ComputerMonitorUtil.getCpuUsage();
		//当前系统的硬盘使用率
		double memUsage = ComputerMonitorUtil.getMemUsage();

		// 返回处理结果
		dataMap.put("cpuUsage", cpuUsage);
		dataMap.put("memUsage", memUsage);
		resultJson.setDataMap(dataMap);

		return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
	}
}
