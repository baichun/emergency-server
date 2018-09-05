package com.comtom.bc.server.controller;

import com.comtom.bc.server.repository.entity.*;
import com.comtom.bc.server.req.EbmReq;
import com.comtom.bc.server.req.SchemeQueryReq;
import com.comtom.bc.server.service.InfoAccessService;
import com.comtom.bc.server.service.SchemeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.result.JsonResult;
import com.comtom.bc.common.result.ResultKey;
import com.comtom.bc.common.utils.JsonUtil;
import com.comtom.bc.server.asset.WebCxt;
import com.comtom.bc.server.req.EbmQueryReq;
import com.comtom.bc.server.service.EbmService;

/**
 * EBM消息（包括预警消息和广播指令消息）控制器实现
 * 
 * @author zhucanhui
 *
 */
@Api(value = "广播消息")
@RestController
@RequestMapping(value = "api/ebm")
public class EbmInfoController {

	@Autowired
	private EbmService ebmService;
	@Autowired
	private SchemeService schemeService;
	@Autowired
	private InfoAccessService infoAccessService;

	/**
	 * 预警消息查询
	 * 
	 * @param req
	 * @param jsessionid
	 * @return ResponseEntity<String>
	 */
	@ApiOperation(value = "广播消息", notes = "预警消息查询")
	@RequestMapping(value = "getAlarm", method = RequestMethod.POST)
	public ResponseEntity<String> getAlarmEbm(@RequestBody EbmQueryReq req,
			@RequestParam String jsessionid) {

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		Page<Ebm> ebmPage = ebmService.getAlarmEbm(req);
		List<Ebm> ebmList = setSchemaInfo(ebmPage.getContent());
		dataMap.put(ResultKey.EBM_LIST_KEY, ebmList);

		// 返回处理结果
		resultJson.setDataMap(dataMap);
		resultJson.setCurrPage(ebmPage.getNumber()+1);
		resultJson.setTotalPage(ebmPage.getTotalPages());
		resultJson.setTotalCount(ebmPage.getTotalElements());
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}

	private List<Ebm> setSchemaInfo(List<Ebm> content) {
		List<Ebm> list = null;
		if (content !=null && !content.isEmpty()){
			list = new ArrayList<Ebm>();
			for (Ebm e : content) {
				Integer schemaId = e.getSchemeId();
				if (schemaId != null ) {
					SchemeInfo scheme = schemeService.findOne(schemaId);
					e.setSchemeInfo(scheme);
				}
				list.add(e);
			}
		}
		return list;
	}

	private List<Ebm> setSchemaInfoAndAccess(List<Ebm> content) {
		List<Ebm> list = null;
		if (content !=null && !content.isEmpty()){
			list = new ArrayList<Ebm>();
			for (Ebm e : content) {
				Integer schemaId = e.getSchemeId();
				if (schemaId != null ) {
					SchemeInfo scheme = schemeService.findOne(schemaId);
					InfoAccess infoAccess=infoAccessService.getInfo(scheme.getInfoId());
					e.setSchemeInfo(scheme);
					e.setInfoAccess(infoAccess);
				}
				list.add(e);
			}
		}
		return list;
	}

	/**
	 * 预警消息详情查询
	 * 
	 * @param req
	 * @param jsessionid
	 * @return ResponseEntity<String>
	 */
	@ApiOperation(value = "广播消息", notes = "预警消息详情查询")
	@RequestMapping(value = "getAlarmDetail", method = RequestMethod.POST)
	public ResponseEntity<String> getAlarmEbmDetail(@RequestBody EbmQueryReq req,
			@RequestParam String jsessionid) {

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		Ebm ebm = ebmService.getAlarmEbmDetail(req);
		dataMap.put(ResultKey.EBM_INFO_KEY, ebm);

		// 返回处理结果
		resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}

	/**
	 * EBM消息查询
	 * 
	 * @param req
	 * @param jsessionid
	 * @return ResponseEntity<String>
	 */
	@ApiOperation(value = "广播消息", notes = "EBM消息查询")
	@RequestMapping(value = "getDispatch", method = RequestMethod.POST)
	public ResponseEntity<String> getDispatchEbm(@RequestBody EbmQueryReq req,
			@RequestParam String jsessionid) {

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		Page<Ebm> ebmPage = ebmService.getDispatchEbm(req);
		List<Ebm> ebmList = setSchemaInfo(ebmPage.getContent());
		dataMap.put(ResultKey.EBM_LIST_KEY, ebmList);

		// 返回处理结果
		resultJson.setDataMap(dataMap);
		resultJson.setCurrPage(ebmPage.getNumber()+1);
		resultJson.setTotalPage(ebmPage.getTotalPages());
		resultJson.setTotalCount(ebmPage.getTotalElements());
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}

	/**
	 * 广播指令消息查询
	 * 
	 * @param req
	 * @param jsessionid
	 * @return ResponseEntity<String>
	 */
	@ApiOperation(value = "广播消息", notes = "广播指令消息查询")
	@RequestMapping(value = "getEbmDispatch", method = RequestMethod.POST)
	public ResponseEntity<String> getEbmDispatch(@RequestBody EbmQueryReq req,
			@RequestParam String jsessionid) {

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		Page<VEbmDispatch> ebmPage = ebmService.getEbmDispatch(req);
		dataMap.put(ResultKey.EBM_LIST_KEY, ebmPage.getContent());

		// 返回处理结果
		resultJson.setDataMap(dataMap);
		resultJson.setCurrPage(ebmPage.getNumber()+1);
		resultJson.setTotalPage(ebmPage.getTotalPages());
		resultJson.setTotalCount(ebmPage.getTotalElements());
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}

	/**
	 * 广播指令消息详情查询
	 * 
	 * @param req
	 * @param jsessionid
	 * @return ResponseEntity<String>
	 */
	@ApiOperation(value = "广播消息", notes = "广播指令消息详情查询")
	@RequestMapping(value = "getEbmDispatchDetail", method = RequestMethod.POST)
	public ResponseEntity<String> getEbmDispatchDetail(@RequestBody EbmQueryReq req,
			@RequestParam String jsessionid) {

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		VEbmDispatch vEbmDispatch = ebmService.getEbmDispatchDetail(req);
		dataMap.put(ResultKey.EBM_INFO_KEY, vEbmDispatch);

		// 返回处理结果
		resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}


	@ApiOperation(value = "发送广播消息", notes = "发送广播消息")
	@RequestMapping(value = "sendEbm", method = RequestMethod.POST)
	public ResponseEntity<String> sendEbm(@RequestBody EbmReq req,
													   @RequestParam String jsessionid) {

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		 ebmService.sendEbm(req);

		// 返回处理结果
		resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}

	/**
	 * 预警消息查询
	 *
	 * @param req
	 * @param jsessionid
	 * @return ResponseEntity<String>
	 */
	@ApiOperation(value = "广播消息", notes = "应急广播消息查询")
	@RequestMapping(value = "getEbm", method = RequestMethod.POST)
	public ResponseEntity<String> getEbm(@RequestBody EbmQueryReq req,
											  @RequestParam String jsessionid) {

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		Page<Ebm> ebmPage = ebmService.getEbm(req);
		List<Ebm> ebmList = setSchemaInfoAndAccess(ebmPage.getContent());
		dataMap.put(ResultKey.EBM_LIST_KEY, ebmList);

		// 返回处理结果
		resultJson.setDataMap(dataMap);
		resultJson.setCurrPage(ebmPage.getNumber()+1);
		resultJson.setTotalPage(ebmPage.getTotalPages());
		resultJson.setTotalCount(ebmPage.getTotalElements());
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}


	/**
	 * 消息分发
	 *
	 * @param req
	 * @param jsessionid
	 * @return ResponseEntity<String>
	 */
	@ApiOperation(value = "消息分发查询", notes = " 消息分发查询")
	@RequestMapping(value = "getDispatchQuery", method = RequestMethod.POST)
	public ResponseEntity<String> getDispatchQuery( @RequestParam String ebmId,
										 @RequestParam String jsessionid) {

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		List<EbmDispatch> list = ebmService.getDispatch(ebmId);
 		dataMap.put(ResultKey.EBM_DISPATCH_KEY, list);

		// 返回处理结果
		resultJson.setDataMap(dataMap);
		//resultJson.setTotalCount(ebmPage.getTotalElements());
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}
}
