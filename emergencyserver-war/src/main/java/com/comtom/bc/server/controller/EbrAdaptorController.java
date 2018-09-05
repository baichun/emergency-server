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
import com.comtom.bc.server.req.AdaptorSaveReq;
import com.comtom.bc.server.req.ResourceLoadReq;
import com.comtom.bc.server.req.ResourceRelatedReq;
import com.comtom.bc.server.service.EbrAdaptorService;
import com.comtom.bc.server.service.EbrBsService;
import com.comtom.bc.server.service.EbrPlatformService;
import com.comtom.bc.server.service.EbrStationService;
import com.comtom.bc.server.service.ResIDGeneratorService;

/**
 * 适配器控制器接口
 * 
 * @author kidsoul
 *
 */
@Api(value = "适配器资源")
@RestController
@RequestMapping(value = "api/adaptor")
public class EbrAdaptorController extends BaseController {

	/**
	 * 日志记录器
	 */
	private static final Logger logger = LoggerFactory.getLogger(EbrAdaptorController.class);

	@Autowired
	private EbrAdaptorService ebrAdaptorService;

	@Autowired
	private EbrBsService ebrBsService;
	
	@Autowired
	private EbrStationService ebrStaService;
	
	@Autowired
	private EbrPlatformService ebrPsService;
	
	@Autowired
	private ResIDGeneratorService residGenService;

	@ApiOperation(value = "适配器资源", notes = "查询消息接收设备列表")
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public ResponseEntity<String> list(HttpServletRequest request,
			@RequestBody ResourceLoadReq req, @RequestParam String jsessionid) {
		if (logger.isDebugEnabled()) {
			logger.info("EbrAdaptorController.list get ebr as list.");
		}

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 获取消息接收设备列表
		List<EbrAdaptor> pageList = ebrAdaptorService.search(req);
		if(null == pageList) {
			pageList = new ArrayList<EbrAdaptor>();
		}
		dataMap.put(ResultKey.EBRAS_LIST_KEY, pageList);
		
		// 返回处理结果
		resultJson.setTotalCount(pageList.size());
		resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME), HttpStatus.OK);
	}
	
	@ApiOperation(value = "适配器资源", notes = "查询消息接收设备列表【分页】")
	@RequestMapping(value = "listPage", method = RequestMethod.POST)
	public ResponseEntity<String> listPage(HttpServletRequest request,
			@RequestBody ResourceLoadReq req, @RequestParam String jsessionid) {
		if (logger.isDebugEnabled()) {
			logger.info("EbrAdaptorController.listPage get ebr as list.");
		}

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 获取消息接收设备列表
		Page<EbrAdaptor> pages = ebrAdaptorService.searchPage(req);
		List<EbrAdaptor> pageList = (null == pages.getContent()) ? new ArrayList<EbrAdaptor>() : pages.getContent();
		dataMap.put(ResultKey.EBRAS_LIST_KEY, pageList);
		dataMap.put(ResultKey.TOTAL_COUNT_KEY, pageList.size());
		
		// 返回处理结果
		resultJson.setTotalCount(pages.getTotalElements());
		resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME), HttpStatus.OK);
	}
	
	@ApiOperation(value = "适配器资源", notes = "新增或修改适配器")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public ResponseEntity<String> add(HttpServletRequest request,
			@RequestBody AdaptorSaveReq req, @RequestParam String jsessionid) {
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);
		
		EbrAdaptor ebrAdaptor = new EbrAdaptor();
		if(null != req.getAsEbrId() && req.getAsEbrId().startsWith(Constants.EBR_TYPE_ADAPTOR,Constants.SUBTYPE_TOFFSET)) {
			ebrAdaptor.setAsEbrId(req.getAsEbrId());
		}
		
		ebrAdaptor.setAsEbrId(req.getAsEbrId());       
		ebrAdaptor.setAsEbrName(req.getAsEbrName());     
		ebrAdaptor.setAsUrl(req.getAsUrl());         
		ebrAdaptor.setAsType(req.getAsType());        
		ebrAdaptor.setRelatedRsEbrId(req.getRelatedRsEbrId());
		ebrAdaptor.setRelatedPsEbrId(req.getRelatedPsEbrId());
		ebrAdaptor.setAreaCode(req.getAreaCode());      
		ebrAdaptor.setLongitude(req.getLongitude());     
		ebrAdaptor.setLatitude(req.getLatitude());      
		ebrAdaptor.setAsState(req.getAsState());       
		ebrAdaptor.setCreateTime(req.getCreateTime());    
		ebrAdaptor.setUpdateTime(req.getUpdateTime());    
		ebrAdaptor.setSyncFlag(req.getSyncFlag());      
		ebrAdaptor.setParam1(req.getParam1());        
		ebrAdaptor.setParam2(req.getParam2());
		
		EbrAdaptor saved = ebrAdaptorService.saveOrUpdate(ebrAdaptor);

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
	
	@ApiOperation(value = "适配器资源", notes = "查询适配器关联的资源信息")
	@RequestMapping(value = "findRelatedResources", method = RequestMethod.POST)
	public ResponseEntity<String> findRelatedResources(HttpServletRequest request,
	@RequestBody ResourceRelatedReq req, @RequestParam String jsessionid) {
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);
		int totalCount = 0;
		
		EbrAdaptor adaptor = ebrAdaptorService.findOne(req.getResourceId());
		if(null != adaptor) {
			if(CommonUtil.isNotEmpty(adaptor.getRelatedPsEbrId())) {
			    List<String> platformIds = new ArrayList<String>();
				platformIds.add(adaptor.getRelatedPsEbrId());
				List<EbrPlatform> relatedPlatforms = ebrPsService.findPlatformListByIds(platformIds);
				if(null != relatedPlatforms && relatedPlatforms.size() > 0) {
					totalCount = totalCount + relatedPlatforms.size();
					dataMap.put("relatedPlatforms", relatedPlatforms);
				}
			}

			
			if(CommonUtil.isNotEmpty(adaptor.getRelatedRsEbrId())) {
				EbrStation relatedStation = ebrStaService.findOne(adaptor.getRelatedRsEbrId());
				if(null != relatedStation) {
					List<EbrStation> relatedStations = new ArrayList<EbrStation>();
					relatedStations.add(relatedStation);
					
					totalCount = totalCount + relatedStations.size();
					dataMap.put("relatedStations", relatedStations);
				}
			}
			
			List<EbrBroadcast> relatedBroadcasts = ebrBsService.findByRelatedAsEbrId(adaptor.getAsEbrId());
			if(null != relatedBroadcasts && relatedBroadcasts.size() > 0) {
				totalCount = totalCount + relatedBroadcasts.size();
				dataMap.put("relatedBroadcasts", relatedBroadcasts);
			}
		}
		
		resultJson.setTotalCount(totalCount);
		resultJson.setDataMap(dataMap);
		
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME), HttpStatus.OK);
	}
}
