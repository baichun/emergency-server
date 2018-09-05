package com.comtom.bc.server.controller;

import com.comtom.bc.common.utils.ResUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.math.BigDecimal;
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
import com.comtom.bc.server.req.BroadcastSaveReq;
import com.comtom.bc.server.req.ResourceLoadReq;
import com.comtom.bc.server.req.ResourceRelatedReq;
import com.comtom.bc.server.service.EbrAdaptorService;
import com.comtom.bc.server.service.EbrBsService;
import com.comtom.bc.server.service.EbrPlatformService;
import com.comtom.bc.server.service.EbrStationService;

/**
 * 播出系统控制器接口
 * 
 * @author zhucanhui
 *
 */
@Api(value = "播出系统资源")
@RestController
@RequestMapping(value = "api/broadcast")
public class EbrBsController extends BaseController {

	/**
	 * 日志记录器
	 */
	private static final Logger logger = LoggerFactory.getLogger(EbrBsController.class);

	@Autowired
	private EbrBsService ebrBsService;
	
	@Autowired
	private EbrStationService ebrStaService;
	
	@Autowired
	private EbrPlatformService ebrPsService;
	
	@Autowired
	private EbrAdaptorService ebrAdaptorService;
	

	@ApiOperation(value = "播出系统资源", notes = "查询播出系统列表")
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public ResponseEntity<String> list(HttpServletRequest request,
			@RequestBody ResourceLoadReq req, @RequestParam String jsessionid) {
		if (logger.isDebugEnabled()) {
			logger.info("EbrBsController.list get ebr bs list.");
		}

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 获取消息接收设备列表
		List<EbrBroadcast> pageList = ebrBsService.search(req);
		dataMap.put(ResultKey.EBRBS_LIST_KEY, pageList);

		if (null == pageList) {
			pageList = new ArrayList<EbrBroadcast>();
		}

		resultJson.setTotalCount(pageList.size());
		resultJson.setDataMap(dataMap);

		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}

	@ApiOperation(value = "播出系统资源", notes = "查询播出系统列表【分页】")
	@RequestMapping(value = "listPage", method = RequestMethod.POST)
	public ResponseEntity<String> listPage(HttpServletRequest request,
			@RequestBody ResourceLoadReq req, @RequestParam String jsessionid) {
		if (logger.isDebugEnabled()) {
			logger.info("EbrBsController.listPage get ebr bs list.");
		}

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 获取消息接收设备列表
		Page<EbrBroadcast> pages = ebrBsService.searchPage(req);
		List<EbrBroadcast> pageList = (null == pages.getContent()) ? new ArrayList<EbrBroadcast>()
				: pages.getContent();
		dataMap.put(ResultKey.EBRBS_LIST_KEY, pageList);
		dataMap.put(ResultKey.TOTAL_COUNT_KEY, pageList.size());

		resultJson.setTotalCount(pages.getTotalElements());
		resultJson.setDataMap(dataMap);

		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}
	
	@ApiOperation(value = "播出系统资源", notes = "查询播出系统关联的资源信息")
	@RequestMapping(value = "findRelatedResources", method = RequestMethod.POST)
	public ResponseEntity<String> findRelatedResources(HttpServletRequest request,
	@RequestBody ResourceRelatedReq req, @RequestParam String jsessionid) {
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);
		int totalCount = 0;
		
		EbrBroadcast broadcast = ebrBsService.findOne(req.getResourceId());
		if(null != broadcast) {
			if(CommonUtil.isNotEmpty(broadcast.getRelatedPsEbrId())) {
			    List<String> platformIds = new ArrayList<String>();
				platformIds.add(broadcast.getRelatedPsEbrId());
				List<EbrPlatform> relatedPlatforms = ebrPsService.findPlatformListByIds(platformIds);
				if(null != relatedPlatforms && relatedPlatforms.size() > 0) {
					totalCount = totalCount + relatedPlatforms.size();
					dataMap.put("relatedPlatforms", relatedPlatforms);
				}
			}

			if(CommonUtil.isNotEmpty(broadcast.getRelatedRsEbrId())) {
				EbrStation relatedStation = ebrStaService.findOne(broadcast.getRelatedRsEbrId());
				if(null != relatedStation) {
					List<EbrStation> relatedStations = new ArrayList<EbrStation>();
					relatedStations.add(relatedStation);
					
					totalCount = totalCount + relatedStations.size();
					dataMap.put("relatedStations", relatedStations);
				}
			}
			
			if(CommonUtil.isNotEmpty(broadcast.getRelatedAsEbrId())) {
				EbrAdaptor relatedAdaptor = ebrAdaptorService.findOne(broadcast.getRelatedAsEbrId());
	            if(null != relatedAdaptor) {
	            	List<EbrAdaptor> relatedAdaptors = new ArrayList<EbrAdaptor>();
	            	relatedAdaptors.add(relatedAdaptor);
	            	
	            	totalCount = totalCount + relatedAdaptors.size();
	            	dataMap.put("relatedAdaptors", relatedAdaptors);
	            }
			}
		}
		
		resultJson.setTotalCount(totalCount);
		resultJson.setDataMap(dataMap);
		
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME), HttpStatus.OK);
	}
	
	@ApiOperation(value = "播出系统资源", notes = "新增或修改播出系统")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public ResponseEntity<String> add(HttpServletRequest request,
			@RequestBody BroadcastSaveReq req, @RequestParam String jsessionid) {
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);
		
		EbrBroadcast ebrBroadcast = new EbrBroadcast();
		if(null != req.getBsEbrId() && ResUtil.isEbrBS(req.getBsEbrId())) {
			ebrBroadcast.setBsEbrId(req.getBsEbrId());
		}
		
		ebrBroadcast.setBsEbrId(req.getBsEbrId());         
		ebrBroadcast.setBsName(req.getBsName());          
		ebrBroadcast.setBsUrl(req.getBsUrl());           
		ebrBroadcast.setBsType(req.getBsType());          
		ebrBroadcast.setLongitude(req.getLongitude());       
		ebrBroadcast.setLatitude(req.getLatitude());        
		ebrBroadcast.setSquare(new BigDecimal(req.getSquare()));          
		ebrBroadcast.setAreaCode(req.getAreaCode());               
		ebrBroadcast.setPopulation(req.getPopulation());      
		ebrBroadcast.setLanguageCode(req.getLanguageCode());    
		ebrBroadcast.setEquipRoom(req.getEquipRoom());       
		ebrBroadcast.setRadioChannelName(req.getRadioChannelName());
		ebrBroadcast.setRadioFreq(req.getRadioFreq());       
		ebrBroadcast.setRadioPower(req.getRadioPower());      
		ebrBroadcast.setBackup(req.getBackup());          
		ebrBroadcast.setAutoSwitch(req.getAutoSwitch());      
		ebrBroadcast.setRemoteControl(req.getRemoteControl());   
		ebrBroadcast.setExperiment(req.getExperiment());      
		ebrBroadcast.setTvChannelName(req.getTvChannelName());   
		ebrBroadcast.setTvFreq(req.getTvFreq());          
		ebrBroadcast.setProgramNum(req.getProgramNum());      
		ebrBroadcast.setTvChannelNum(req.getTvChannelNum());    
		ebrBroadcast.setBsState(req.getBsState());         
		ebrBroadcast.setRelatedPsEbrId(req.getRelatedPsEbrId());  
		ebrBroadcast.setRelatedRsEbrId(req.getRelatedRsEbrId());  
		ebrBroadcast.setRelatedAsEbrId(req.getRelatedAsEbrId());  
		ebrBroadcast.setCreateTime(req.getCreateTime());      
		ebrBroadcast.setUpdateTime(req.getUpdateTime());      
		ebrBroadcast.setSyncFlag(req.getSyncFlag());
		ebrBroadcast.setWorkTimeSwitch(req.getWorkTimeSwitch());
		
		EbrBroadcast saved = ebrBsService.saveOrUpdate(ebrBroadcast);

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
