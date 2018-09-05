package com.comtom.bc.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Map;

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
import com.comtom.bc.server.repository.entity.DispatchFlow;
import com.comtom.bc.server.req.FlowQueryReq;
import com.comtom.bc.server.service.DispatchFlowService;

/**
 * 调度流程控制器实现
 * 
 * @author zhucanhui
 *
 */
@Api(value = "调度流程")
@RestController
@RequestMapping(value = "api/flow")
public class FlowController {

	@Autowired
	private DispatchFlowService dispatchFlowService;

	@ApiOperation(value = "调度流程", notes = "调度流程查询")
	@RequestMapping(value = "get", method = RequestMethod.POST)
	public ResponseEntity<String> get(@RequestBody FlowQueryReq req, @RequestParam String jsessionid) {

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		Page<DispatchFlow> flowPages = dispatchFlowService.getFlow(req);

		// 返回处理结果
		dataMap.put(ResultKey.FLOW_LIST_KEY, flowPages.getContent());
		dataMap.put(ResultKey.TOTAL_COUNT_KEY, flowPages.getSize());
		resultJson.setDataMap(dataMap);
		resultJson.setTotalCount(flowPages.getTotalElements());
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
	}

	@ApiOperation(value = "调度流程", notes = "调度流程统计查询")
	@RequestMapping(value = "stat", method = RequestMethod.POST)
	public ResponseEntity<String> stat(@RequestParam String jsessionid) {

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		Map<String, Long> flowStatMap = dispatchFlowService.statFlow();

		// 返回处理结果
		dataMap.put(ResultKey.FLOW_COUNT_MAP, flowStatMap);
		resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
	}
}
