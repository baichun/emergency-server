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
import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.common.utils.JsonUtil;
import com.comtom.bc.server.asset.WebCxt;
import com.comtom.bc.server.controller.base.BaseController;
import com.comtom.bc.server.repository.entity.EbrAdaptor;
import com.comtom.bc.server.repository.entity.EbrBroadcast;
import com.comtom.bc.server.repository.entity.EbrPlatform;
import com.comtom.bc.server.repository.entity.EbrStation;
import com.comtom.bc.server.repository.entity.EbrTerminal;
import com.comtom.bc.server.repository.entity.VEbrPlatform;
import com.comtom.bc.server.req.PlatformSaveReq;
import com.comtom.bc.server.req.ResourceLoadReq;
import com.comtom.bc.server.req.ResourceRelatedReq;
import com.comtom.bc.server.service.EbrAdaptorService;
import com.comtom.bc.server.service.EbrBsService;
import com.comtom.bc.server.service.EbrPlatformService;
import com.comtom.bc.server.service.EbrStationService;
import com.comtom.bc.server.service.EbrTerminalService;
import com.comtom.bc.server.service.ResIDGeneratorService;

/**
 * 应急广播平台控制器接口
 * 
 * @author kidsoul
 *
 */
@Api(value = "平台资源")
@RestController
@RequestMapping(value = "api/platform")
public class EbrPlatformController extends BaseController {

	/**
	 * 日志记录器
	 */
	private static final Logger logger = LoggerFactory.getLogger(EbrPlatformController.class);

	@Autowired
	private EbrPlatformService ebrPsService;
	
	@Autowired
	private EbrAdaptorService ebrAdaptorService;

	@Autowired
	private EbrBsService ebrBsService;
	
	@Autowired
	private EbrStationService ebrStaService;
	
	@Autowired
	private EbrTerminalService ebrTrmService;
	
	@Autowired
	private ResIDGeneratorService residGenService;

	@ApiOperation(value = "平台资源", notes = "查询应急广播平台列表")
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public ResponseEntity<String> list(HttpServletRequest request,
			@RequestBody ResourceLoadReq req, @RequestParam String jsessionid) {
		if (logger.isDebugEnabled()) {
			logger.info("EbrPlatformController.list get ebr ps list.");
		}

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 获取应急广播平台列表
		List<EbrPlatform> pageList = ebrPsService.search(req);
		if (null == pageList) {
			pageList = new ArrayList<EbrPlatform>();
		}
		dataMap.put(ResultKey.EBRPS_LIST_KEY, pageList);

		// 返回处理结果
		resultJson.setTotalCount(pageList.size());
		resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}

	@ApiOperation(value = "平台资源", notes = "查询应急广播平台列表")
	@RequestMapping(value = "findAll", method = RequestMethod.POST)
	public ResponseEntity<String> findAll(HttpServletRequest request,
			@RequestBody ResourceLoadReq req, @RequestParam String jsessionid) {

		if (logger.isDebugEnabled()) {
			logger.info("EbrPlatformController.list get ebr ps list.");
		}

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 获取应急广播平台列表
		Page<VEbrPlatform> pageList = ebrPsService.findAll(req);
		dataMap.put(ResultKey.EBRPS_LIST_KEY, pageList.getContent());

		// 返回处理结果
		resultJson.setTotalCount(pageList.getTotalElements());
		resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}

	@ApiOperation(value = "平台资源", notes = "查询应急广播平台列表【分页】")
	@RequestMapping(value = "listPage", method = RequestMethod.POST)
	public ResponseEntity<String> listPage(HttpServletRequest request,
			@RequestBody ResourceLoadReq req, @RequestParam String jsessionid) {
		if (logger.isDebugEnabled()) {
			logger.info("EbrPlatformController.listPage get ebr ps list.");
		}

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);
		// WebCxt.initPage(req.getPageNum(), req.getPageSize());

		// 获取用户信息
		// SysUser user = getUser(request);

		// 获取应急广播平台列表
		Page<EbrPlatform> pages = ebrPsService.searchPage(req);
		List<EbrPlatform> pageList = (null == pages.getContent()) ? new ArrayList<EbrPlatform>()
				: pages.getContent();
		dataMap.put(ResultKey.EBRPS_LIST_KEY, pageList);
		dataMap.put(ResultKey.TOTAL_COUNT_KEY, pageList.size());

		// 返回处理结果
		resultJson.setTotalCount(pages.getTotalElements());
		resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}
	
	@ApiOperation(value = "平台资源", notes = "新增或修改应急广播平台")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public ResponseEntity<String> add(HttpServletRequest request, @RequestBody PlatformSaveReq req,
			@RequestParam String jsessionid) {
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);
		
		EbrPlatform ebrPlatform = new EbrPlatform();
		if(null != req.getPsEbrId() && req.getPsEbrId().startsWith(Constants.EBR_TYPE_PLATFORM,Constants.RESTYPE_TOFFSET)) {
			ebrPlatform.setPsEbrId(req.getPsEbrId());
		}
		
		ebrPlatform.setPsUrl(req.getPsUrl());
		ebrPlatform.setPsEbrName(req.getPsEbrName());
		ebrPlatform.setPsState(req.getPsState());
		ebrPlatform.setAreaCode(req.getAreaCode());
		ebrPlatform.setPsType(req.getPsType());
		ebrPlatform.setParentPsEbrId(req.getParentPsEbrId());
		ebrPlatform.setPsAddress(req.getPsAddress());
		ebrPlatform.setContact(req.getContact());
		ebrPlatform.setPhoneNumber(req.getPhoneNumber());
		ebrPlatform.setLongitude(req.getLongitude());
		ebrPlatform.setLatitude(req.getLatitude());
		ebrPlatform.setCreateTime(req.getCreateTime());
		ebrPlatform.setUpdateTime(req.getUpdateTime());
		ebrPlatform.setSyncFlag(req.getSyncFlag());
		
		EbrPlatform saved = ebrPsService.saveOrUpdate(ebrPlatform);

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
	
	@ApiOperation(value = "平台资源", notes = "查询平台关联的资源信息")
	@RequestMapping(value = "findRelatedResources", method = RequestMethod.POST)
	public ResponseEntity<String> findRelatedResources(HttpServletRequest request,
	@RequestBody ResourceRelatedReq req, @RequestParam String jsessionid) {
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);
		int totalCount = 0;
		
		EbrPlatform platform = ebrPsService.findOne(req.getResourceId());
		if(null != platform) {
			if(CommonUtil.isNotEmpty(platform.getParentPsEbrId())) {
				EbrPlatform parentPlatform = ebrPsService.findOne(platform.getParentPsEbrId());
				
				totalCount = totalCount + 1;
				dataMap.put("parentPlatform", parentPlatform);
			}

            List<EbrStation> relatedStations = ebrStaService.findByRelatedPsEbrId(platform.getPsEbrId());
			if(null != relatedStations && relatedStations.size() > 0) {
				totalCount = totalCount + relatedStations.size();
				dataMap.put("relatedStations", relatedStations);
			}
			List<EbrAdaptor> relatedAdaptors = ebrAdaptorService.findByRelatedPsEbrId(platform.getPsEbrId());
			if(null != relatedAdaptors && relatedAdaptors.size() > 0) {
				totalCount = totalCount + relatedAdaptors.size();
				dataMap.put("relatedAdaptors", relatedAdaptors);
			}
			List<EbrBroadcast> relatedBroadcasts = ebrBsService.findByRelatedPsEbrId(platform.getPsEbrId());
			if(null != relatedBroadcasts && relatedBroadcasts.size() > 0) {
				totalCount = totalCount + relatedBroadcasts.size();
				dataMap.put("relatedBroadcasts", relatedBroadcasts);
			}
			List<EbrTerminal> relatedTerminals = ebrTrmService.findByRelatedPsEbrId(platform.getPsEbrId());
			if(null != relatedTerminals && relatedTerminals.size() > 0) {
				totalCount = totalCount + relatedTerminals.size();
				dataMap.put("relatedTerminals", relatedTerminals);
			}
		}
		
		resultJson.setTotalCount(totalCount);
		resultJson.setDataMap(dataMap);
		
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME), HttpStatus.OK);
	}
}
