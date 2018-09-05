package com.comtom.bc.server.controller;

import com.comtom.bc.server.repository.entity.Region;
import com.comtom.bc.server.req.RegionReq;
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
import com.comtom.bc.common.annotation.NoRecordLog;
import com.comtom.bc.common.result.JsonResult;
import com.comtom.bc.common.result.ResultKey;
import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.common.utils.JsonUtil;
import com.comtom.bc.server.asset.WebCxt;
import com.comtom.bc.server.repository.entity.RegionArea;
import com.comtom.bc.server.req.RegionAreaReq;
import com.comtom.bc.server.service.AreaService;
import com.comtom.bc.server.service.ParamService;

/**
 * 字典参数控制器实现
 * 
 * @author zhucanhui
 *
 */
@Api(value = "行政区域")
@RestController
@RequestMapping(value = "api/area")
public class AreaController {

	@Autowired
	private AreaService areaService;

	@Autowired
	private ParamService paramService;

	@NoRecordLog
	@ApiOperation(value = "行政区域", notes = "行政区域查询")
	@RequestMapping(value = "findArea", method = RequestMethod.POST)
	public ResponseEntity<String> findArea(HttpServletRequest request,
			@RequestBody RegionAreaReq req, @RequestParam String jsessionid) {

		// 设置公用参数
		//request.setAttribute(Constants.PORTAL_TYPE, req.getPortalType());

		String areaCode = req.getAreaCode();
		String areaLevel = paramService.findValueByKey(Constants.DEFAULT_LOAD_LEVEL);

		// 防止键值参数修改导致异常
		if (CommonUtil.isEmpty(areaLevel)) {
			areaLevel = Constants.DEFAULT_LEVEL;
		}

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		List<RegionArea> areaList = areaService.findArea(areaCode, Integer.valueOf(areaLevel));
		dataMap.put(ResultKey.AREA_LIST_KEY, areaList);
		dataMap.put(ResultKey.TOTAL_COUNT_KEY, areaList.size());

		// 返回处理结果
		resultJson.setDataMap(dataMap);
		resultJson.setTotalCount(areaList.size());
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
	}

	@NoRecordLog
	@ApiOperation(value = "行政区域", notes = "全国所有行政区域查询")
	@RequestMapping(value = "findAreaAll", method = RequestMethod.POST)
	public ResponseEntity<String> findAreaAll(HttpServletRequest request,
											  @RequestBody RegionReq req, @RequestParam String jsessionid) {


		//String areaCode = req.getAreaCode();
		String areaLevel = paramService.findValueByKey(Constants.DEFAULT_LOAD_LEVEL);

		// 防止键值参数修改导致异常
		if (CommonUtil.isEmpty(areaLevel)) {
			areaLevel = Constants.DEFAULT_LEVEL;
		}

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		List<Region> areaList = areaService.findAreaAll(req, Integer.valueOf(areaLevel));
		dataMap.put(ResultKey.AREA_LIST_KEY, areaList);
		dataMap.put(ResultKey.TOTAL_COUNT_KEY, areaList.size());

		// 返回处理结果
		resultJson.setDataMap(dataMap);
		resultJson.setTotalCount(areaList.size());
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
	}

	@NoRecordLog
	@ApiOperation(value = "行政区域", notes = "保存初始化区域")
	@RequestMapping(value = "saveInitArea", method = RequestMethod.POST)
	public ResponseEntity<String> saveInitArea(HttpServletRequest request,
											  @RequestBody RegionReq req, @RequestParam String jsessionid) {
		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		areaService.insertAreaAllChildren(req);

		// 返回处理结果
		resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
	}
}
