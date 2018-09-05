package com.comtom.bc.server.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.result.JsonResult;
import com.comtom.bc.common.result.ResultCode;
import com.comtom.bc.common.utils.JsonUtil;
import com.comtom.bc.exchange.commom.BroadcastStateEnum;
import com.comtom.bc.server.asset.WebCxt;
import com.comtom.bc.server.repository.entity.Ebd;
import com.comtom.bc.server.repository.entity.Ebm;
import com.comtom.bc.server.repository.entity.EbrTerminal;
import com.comtom.bc.server.req.EbmQueryReq;
import com.comtom.bc.server.service.EbdService;
import com.comtom.bc.server.service.EbmBrdLogService;
import com.comtom.bc.server.service.EbmService;
import com.comtom.bc.server.service.EbrTerminalService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(value = "广播状态接口")
@RestController
@RequestMapping("/rest/broadcastState")
public class BroadcastStateRest extends BaseRest {
	
	@Autowired
	private EbdService ebdService;
	@Autowired
	private EbmService ebmService;
	@Resource
	private EbmBrdLogService ebmBrdLogService;
	@Resource
	private EbrTerminalService ebrTerminalService;

	/**
	 * 获取广播状态
	 * @param request
	 * @param taskId
	 * @return
	 */
	@RequestMapping(value = "state/{taskId}", method = RequestMethod.GET)
	@ApiOperation(value = "获取广播状态", notes = "获取广播状态")
	@ApiImplicitParam(name = "taskId", value = "任务唯一ID", dataType = "string", paramType = "path")
	public ResponseEntity<String> obtainState(HttpServletRequest request, @PathVariable("taskId") String taskId) {
		Ebd ebd = ebdService.findEbd(taskId);
		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();
		Map<String, Object> newJsonMap = WebCxt.newJsonMap(resultJson);
		if(ebd==null) {
			resultJson.setResultCode(ResultCode.FAILURE);
			resultJson.setResultMsg("没有找到此任务");
			resultJson.setTotalCount(0);
			return ResponseEntity.ok(JsonUtil.toJson(resultJson));
		}
		String ebmId = ebd.getEbmId();
		BroadcastStateEnum brdState = ebmBrdLogService.getBrdState(ebmId);
//		if(BroadcastStateEnum.succeeded.equals(brdState)) {
			resultJson.setTotalCount(1);
			newJsonMap.put("state", brdState.getCode());
			newJsonMap.put("reason", brdState.getBrdstate());
//		}
		return ResponseEntity.ok(JsonUtil.toJson(resultJson));
	}
	
	
	//effect效果
	
	@RequestMapping(value = "effect/{taskId}", method = RequestMethod.GET)
	@ApiOperation(value = "获取广播效果", notes = "获取广播效果")
	@ApiImplicitParam(name = "taskId", value = "任务唯一ID", dataType = "string", paramType = "path")
	public ResponseEntity<String> effect(HttpServletRequest request, @PathVariable("taskId") String taskId) {
		Ebd ebd = ebdService.findEbd(taskId);
		String ebmId = ebd.getEbmId();
		Ebm ebm = ebd.getEbm();
		if(ebm==null && ebmId!=null) {
			EbmQueryReq req = new EbmQueryReq();
			req.setEbmId(ebmId);
			ebm = ebmService.getAlarmEbmDetail(req);
		}
		if(ebm == null) {
			return ResponseEntity.badRequest().build();
		}
		//笨方法去重
		String[] areaCodes = ebm.getAreaCode().split(String.valueOf(Constants.COMMA_SPLIT));
		Set<String> terminalIds=new HashSet<String>();
		for(String code:areaCodes) {
			List<EbrTerminal> terminals = ebrTerminalService.findByAreaCode(code);
			for(EbrTerminal terminal :terminals) {
				terminalIds.add(terminal.getTerminalEbrId());
			}
		}
		
//		ResourceLoadReq resourceLoadReq = new ResourceLoadReq();
//		resourceLoadReq.setAreaCode(areaCode);
//		ebrTerminalService.search(resourceLoadReq);
		
		BroadcastStateEnum brdState = ebmBrdLogService.getBrdState(ebmId);
		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();
		Map<String, Object> newJsonMap = WebCxt.newJsonMap(resultJson);
//		if(BroadcastStateEnum.succeeded.equals(brdState)) {
			newJsonMap.put("state", brdState.getCode());
			newJsonMap.put("reason", brdState.getBrdstate());
			newJsonMap.put("terminalSize", terminalIds.size());
//		}
		return ResponseEntity.ok(JsonUtil.toJson(resultJson));
	}
}
