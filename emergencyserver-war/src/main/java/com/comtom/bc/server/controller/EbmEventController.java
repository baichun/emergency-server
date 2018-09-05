package com.comtom.bc.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comtom.bc.common.annotation.NoRecordLog;
import com.comtom.bc.common.result.JsonResult;
import com.comtom.bc.common.result.ResultKey;
import com.comtom.bc.common.utils.JsonUtil;
import com.comtom.bc.server.asset.WebCxt;
import com.comtom.bc.server.repository.entity.EbmEventType;
import com.comtom.bc.server.service.EbmEventService;

/**
 * 字典参数控制器实现
 * 
 * @author zhucanhui
 *
 */
@Api(value = "事件分类")
@RestController
@RequestMapping(value = "api/ebm/event")
public class EbmEventController {

	@Autowired
	private EbmEventService ebmEventService;

	@NoRecordLog
	@ApiOperation(value = "EBM事件分类", notes = "EBM事件分类查询")
	@RequestMapping(value = "get", method = RequestMethod.POST)
	public ResponseEntity<String> getEbmEvent(HttpServletRequest request,
			@RequestParam String jsessionid) {

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		List<EbmEventType> ebmEventList = ebmEventService.findAll();

		// 返回处理结果
		dataMap.put(ResultKey.EVENT_LIST_KEY, ebmEventList);
		resultJson.setTotalCount(ebmEventList.size());
		resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
	}
}
