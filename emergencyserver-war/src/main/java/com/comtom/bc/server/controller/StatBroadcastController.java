package com.comtom.bc.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.result.JsonResult;
import com.comtom.bc.common.utils.JsonUtil;
import com.comtom.bc.server.asset.WebCxt;
import com.comtom.bc.server.repository.entity.StatBroadcast;
import com.comtom.bc.server.req.StatsicListReq;
import com.comtom.bc.server.req.StatsicQueryReq;
import com.comtom.bc.server.service.StatBroadcastService;

/**
 * 广播次数统计接口，实现分区域/事件类别/来源统计
 * 
 * @author zhucanhui
 */
@Api(value = "广播次数统计")
@RestController
public class StatBroadcastController {

	@Autowired
	private StatBroadcastService statBroadcastService;

	@ApiOperation(value = "广播次数统计", notes = "广播次数统计记录")
	@RequestMapping(value = "listHistory", method = RequestMethod.POST)
	public ResponseEntity<String> listHistory(HttpServletRequest request, @RequestBody StatsicListReq req,
			@RequestParam String jsessionid) {

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();
		resultJson.setTotalCount(0);
		
		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		List<StatBroadcast> broadcastStats = statBroadcastService.search(req);
        if(null != broadcastStats && broadcastStats.size() > 0) {
    		dataMap.put("broadcastStats", broadcastStats);
    		resultJson.setTotalCount(broadcastStats.size());
        }
		
		// 返回处理结果
		resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME), HttpStatus.OK);
	}
	
	@ApiOperation(value = "广播次数统计", notes = "广播次数统计截止日期查询")
	@RequestMapping(value = "queryStatsic", method = RequestMethod.POST)
	public ResponseEntity<String> queryStatsic(HttpServletRequest request, @RequestBody StatsicQueryReq req,
			@RequestParam String jsessionid) {

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();
		resultJson.setTotalCount(0);

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		List<StatBroadcast> broadcastStats = statBroadcastService.queryStatsic(req);
        if(null != broadcastStats && broadcastStats.size() > 0) {
    		dataMap.put("broadcastStats", broadcastStats);
    		resultJson.setTotalCount(broadcastStats.size());
        }

		// 返回处理结果
		resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME), HttpStatus.OK);
	}
	
	@ApiOperation(value = "广播次数统计", notes = "统计记录优先于查询")
	@RequestMapping(value = "listOverQuery", method = RequestMethod.POST)
	public ResponseEntity<String> listOverQuery(HttpServletRequest request, @RequestBody StatsicListReq req,
			@RequestParam String jsessionid) {

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();
		resultJson.setTotalCount(0);
		
		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		List<StatBroadcast> broadcastStats = statBroadcastService.search(req);
        if(null == broadcastStats || broadcastStats.size() < 1) {
        	StatsicQueryReq queryReq = new StatsicQueryReq();
        	queryReq.setStatsicType(req.getStatsicType());
        	queryReq.setPortalType(req.getPortalType());
        	queryReq.setAccount(req.getAccount());
        	queryReq.setPageNum(req.getPageNum());
        	queryReq.setPageSize(req.getPageSize());
        	broadcastStats =statBroadcastService.queryStatsic(queryReq);
        }
        
        if(null != broadcastStats && broadcastStats.size() > 0) {
    		dataMap.put("broadcastStats", broadcastStats);
    		resultJson.setTotalCount(broadcastStats.size());
        } 

		// 返回处理结果
        resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME), HttpStatus.OK);
	}

}
