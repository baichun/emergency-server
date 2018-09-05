package com.comtom.bc.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.comtom.bc.common.result.JsonResult;
import com.comtom.bc.common.result.ResultCode;
import com.comtom.bc.common.result.ResultKey;
import com.comtom.bc.common.result.ResultMsg;
import com.comtom.bc.common.utils.JsonUtil;
import com.comtom.bc.server.asset.WebCxt;
import com.comtom.bc.server.controller.base.BaseController;
import com.comtom.bc.server.repository.entity.EbrTerminal;
import com.comtom.bc.server.req.ResourceLoadReq;
import com.comtom.bc.server.req.TerminalSaveReq;
import com.comtom.bc.server.req.TerminalStatReq;
import com.comtom.bc.server.service.EbrTerminalService;
import com.comtom.bc.server.service.ResIDGeneratorService;

/**
 * 终端控制器接口
 * 
 * @author kidsoul
 *
 */
@Api(value = "终端资源")
@RestController
@RequestMapping(value = "api/terminal")
public class EbrTerminalController extends BaseController {

	/**
	 * 日志记录器
	 */
	private static final Logger logger = LoggerFactory.getLogger(EbrTerminalController.class);

	@Autowired
	private EbrTerminalService ebrTrmService;
	
	@Autowired
	private ResIDGeneratorService residGenService;

	@ApiOperation(value = "终端资源", notes = "查询终端列表")
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public ResponseEntity<String> list(HttpServletRequest request,
			@RequestBody ResourceLoadReq req, @RequestParam String jsessionid) {
		if (logger.isDebugEnabled()) {
			logger.info("EbrTerminalController.list get ebr trm list.");
		}

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 获取终端列表
		List<EbrTerminal> pageList = ebrTrmService.search(req);
		if (null == pageList) {
			pageList = new ArrayList<EbrTerminal>();
		}
		dataMap.put(ResultKey.EBRTRM_LIST_KEY, pageList);

		// 返回处理结果
		resultJson.setTotalCount(pageList.size());
		resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}

	@ApiOperation(value = "终端资源", notes = "查询终端列表【分页】")
	@RequestMapping(value = "listPage", method = RequestMethod.POST)
	public ResponseEntity<String> listPage(HttpServletRequest request,
			@RequestBody ResourceLoadReq req, @RequestParam String jsessionid) {
		if (logger.isDebugEnabled()) {
			logger.info("EbrTerminalController.listPage get ebr trm list.");
		}

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 获取终端列表
		Page<EbrTerminal> pages = ebrTrmService.searchPage(req);
		List<EbrTerminal> pageList = (null == pages.getContent()) ? new ArrayList<EbrTerminal>()
				: pages.getContent();
		dataMap.put(ResultKey.EBRTRM_LIST_KEY, pageList);
		dataMap.put(ResultKey.TOTAL_COUNT_KEY, pageList.size());

		// 返回处理结果
		resultJson.setTotalCount(pages.getTotalElements());
		resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}

	/**
	 * 终端数量统计（根据条件）
	 * 
	 * @param request
	 * @param req
	 * @param jsessionid
	 * @return ResponseEntity<String>
	 */
	public ResponseEntity<String> count(HttpServletRequest request,
			@RequestBody TerminalStatReq req, @RequestParam String jsessionid) {

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		long terminalCount = ebrTrmService.count(req);
		dataMap.put(ResultKey.TERMINAL_TOTAL_KEY, terminalCount);
		resultJson.setDataMap(dataMap);

		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}
	
	@ApiOperation(value = "终端资源", notes = "新增或修改终端")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public ResponseEntity<String> add(HttpServletRequest request,
			@RequestBody TerminalSaveReq req, @RequestParam String jsessionid) {
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);
		
		EbrTerminal ebrTerminal = new EbrTerminal();
		if(null != req.getTerminalEbrId() && req.getTerminalEbrId().startsWith(Constants.EBR_TYPE_TERMINAL,Constants.SUBTYPE_TOFFSET)) {
			ebrTerminal.setTerminalEbrId(req.getTerminalEbrId());
		}
		
		ebrTerminal.setTerminalEbrName(req.getTerminalEbrName());
		ebrTerminal.setRelatedPsEbrId(req.getRelatedPsEbrId()); 
		ebrTerminal.setLongitude(req.getLongitude());      
		ebrTerminal.setLatitude(req.getLatitude());       
		ebrTerminal.setTerminalType(req.getTerminalType());   
		ebrTerminal.setTerminalState(req.getTerminalState());  
		ebrTerminal.setCreateTime(req.getCreateTime());     
		ebrTerminal.setUpdateTime(req.getUpdateTime());     
		ebrTerminal.setSyncFlag(req.getSyncFlag());       
		ebrTerminal.setParam1(req.getParam1());         
		ebrTerminal.setParam2(req.getParam2());
		
		EbrTerminal saved = ebrTrmService.saveOrUpdate(ebrTerminal);

		if(null == saved){
			resultJson.setResultCode(ResultCode.FAILURE);
			resultJson.setResultMsg(ResultMsg.RESULT_FAILURE);
		} else {
			resultJson.setResultCode(ResultCode.SUCCESSFUL);
			resultJson.setResultMsg(ResultMsg.RESULT_SUCCESS);
		}
		resultJson.setDataMap(dataMap);

		return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
	}


}
