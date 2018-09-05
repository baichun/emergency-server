package com.comtom.bc.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comtom.bc.common.result.JsonResult;
import com.comtom.bc.common.utils.JsonUtil;
import com.comtom.bc.server.asset.WebCxt;
import com.comtom.bc.server.repository.entity.SysDic;
import com.comtom.bc.server.service.DicService;

/**
 * 字典参数控制器实现
 * 
 * @author zhucanhui
 *
 */
@Api(value = "字典参数")
@RestController
@RequestMapping(value = "api/dic")
public class DicController {

	@Autowired
	private DicService dicService;

	@ApiOperation(value = "字典参数", notes = "字典参数查询")
	@RequestMapping(value = "get", method = RequestMethod.GET)
	public ResponseEntity<String> getCodeByKey(@RequestParam String key, @RequestParam String jsessionid) {

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		Set<SysDic> sysDicSet = dicService.findCodeByKey(key);

		for (SysDic sysDic : sysDicSet) {
			dataMap.put(sysDic.getCode(), sysDic.getDesc());
		}

		// 返回处理结果
		resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
	}
}
