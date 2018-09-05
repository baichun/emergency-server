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
import com.comtom.bc.server.req.ResourceLoadReq;
import com.comtom.bc.server.req.ResourceRelatedReq;
import com.comtom.bc.server.req.StationSaveReq;
import com.comtom.bc.server.service.EbrAdaptorService;
import com.comtom.bc.server.service.EbrBsService;
import com.comtom.bc.server.service.EbrPlatformService;
import com.comtom.bc.server.service.EbrStationService;
import com.comtom.bc.server.service.ResIDGeneratorService;

/**
 * 台站控制器接口
 * 
 * @author kidsoul
 *
 */
@Api(value = "台站资源")
@RestController
@RequestMapping(value = "api/station")
public class EbrStationController extends BaseController {

	/**
	 * 日志记录器
	 */
	private static final Logger logger = LoggerFactory.getLogger(EbrStationController.class);

	@Autowired
	private EbrStationService ebrStaService;
	
	@Autowired
	private EbrBsService ebrBsService;
	
	@Autowired
	private EbrPlatformService ebrPsService;
	
	@Autowired
	private EbrAdaptorService ebrAdaptorService;
	
	@Autowired
	private ResIDGeneratorService residGenService;

	@ApiOperation(value = "台站资源", notes = "查询台站列表")
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public ResponseEntity<String> list(HttpServletRequest request,
			@RequestBody ResourceLoadReq req, @RequestParam String jsessionid) {
		if (logger.isDebugEnabled()) {
			logger.info("EbrStationController.list get ebr sta list.");
		}

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 获取台站列表
		List<EbrStation> pageList = ebrStaService.search(req);
		if (null == pageList) {
			pageList = new ArrayList<EbrStation>();
		}
		dataMap.put(ResultKey.EBRSTA_LIST_KEY, pageList);

		// 返回处理结果
		resultJson.setTotalCount(pageList.size());
		resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}

	@ApiOperation(value = "台站资源", notes = "查询台站列表【分页】")
	@RequestMapping(value = "listPage", method = RequestMethod.POST)
	public ResponseEntity<String> listPage(HttpServletRequest request,
			@RequestBody ResourceLoadReq req, @RequestParam String jsessionid) {
		if (logger.isDebugEnabled()) {
			logger.info("EbrStationController.listPage get ebr sta list.");
		}

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 获取台站列表
		Page<EbrStation> pages = ebrStaService.searchPage(req);
		List<EbrStation> pageList = (null == pages.getContent()) ? new ArrayList<EbrStation>()
				: pages.getContent();
		dataMap.put(ResultKey.EBRSTA_LIST_KEY, pageList);
		dataMap.put(ResultKey.TOTAL_COUNT_KEY, pageList.size());

		// 返回处理结果
		resultJson.setTotalCount(pages.getTotalElements());
		resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}
	
	@ApiOperation(value = "台站资源", notes = "新增或修改台站")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public ResponseEntity<String> save(HttpServletRequest request,
			@RequestBody StationSaveReq req, @RequestParam String jsessionid) {
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);
		
		EbrStation ebrStation = new EbrStation();
		//if(null != req.getStationEbrId() && req.getStationEbrId().startsWith(Constants.EBR_TYPE_STATION)) {
		if(null != req.getStationEbrId()){
			ebrStation.setStationEbrId(req.getStationEbrId());
		}
		
		ebrStation.setStationName(req.getStationName());
		ebrStation.setStationAddress(req.getStationAddress());
		ebrStation.setStationType(req.getStationType());
		ebrStation.setContact(req.getContact());
		ebrStation.setPhoneNumber(req.getPhoneNumber());
		ebrStation.setLongitude(req.getLongitude());
		ebrStation.setLatitude(req.getLatitude());
		ebrStation.setAreaCode(req.getAreaCode());
		ebrStation.setRelatedPsEbrId(req.getRelatedPsEbrId());  
		ebrStation.setCreateTime(req.getCreateTime());
		ebrStation.setUpdateTime(req.getUpdateTime());
		ebrStation.setSyncFlag(req.getSyncFlag());
		
		EbrStation saved = ebrStaService.saveOrUpdate(ebrStation);
		

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
	
	@ApiOperation(value = "台站资源", notes = "查询台站关联的资源信息")
	@RequestMapping(value = "findRelatedResources", method = RequestMethod.POST)
	public ResponseEntity<String> findRelatedResources(HttpServletRequest request,
	@RequestBody ResourceRelatedReq req, @RequestParam String jsessionid) {
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);
		int totalCount = 0;
		
		EbrStation station = ebrStaService.findOne(req.getResourceId());
		if(null != station) {
			if(CommonUtil.isNotEmpty(station.getRelatedPsEbrId())) {
			    List<String> platformIds = new ArrayList<String>();
				platformIds.add(station.getRelatedPsEbrId());
				List<EbrPlatform> relatedPlatforms = ebrPsService.findPlatformListByIds(platformIds);
				if(null != relatedPlatforms && relatedPlatforms.size() > 0) {
					totalCount = totalCount + relatedPlatforms.size();
					dataMap.put("relatedPlatforms", relatedPlatforms);
				}	
			}
			
			List<EbrBroadcast> relatedBroadcasts = ebrBsService.findByRelatedRsEbrId(station.getStationEbrId());
			if(null != relatedBroadcasts && relatedBroadcasts.size() > 0) {
				totalCount = totalCount + relatedBroadcasts.size();
				dataMap.put("relatedBroadcasts", relatedBroadcasts);
			}
			
			List<EbrAdaptor> relatedAdaptors = ebrAdaptorService.findByRelatedRsEbrId(station.getStationEbrId());
            if(null != relatedAdaptors && relatedAdaptors.size() > 0) {
            	totalCount = totalCount + relatedAdaptors.size();
            	dataMap.put("relatedAdaptors", relatedAdaptors);
            }
		}
		
		resultJson.setTotalCount(totalCount);
		resultJson.setDataMap(dataMap);
		
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME), HttpStatus.OK);
	}
}
