package com.comtom.bc.server.controller;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.result.JsonResult;
import com.comtom.bc.common.result.ResultKey;
import com.comtom.bc.common.utils.JsonUtil;
import com.comtom.bc.server.asset.WebCxt;
import com.comtom.bc.server.ebd.impl.EBMBrdLogService;
import com.comtom.bc.server.ebd.impl.omdinfo.OMDBrdLogService;
import com.comtom.bc.server.repository.entity.Ebm;
import com.comtom.bc.server.repository.entity.VEbmDispatch;
import com.comtom.bc.server.req.EbmQueryReq;
import com.comtom.bc.server.service.EbmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * EBM播发记录
 * 
 *
 */
@Api(value = "广播消息")
@RestController
@RequestMapping(value = "/ebmBrLog")
public class EbmBrLogController {

	@Autowired
	private EbmService ebmService;

	@Autowired
    private EBMBrdLogService ebmBrdLogService;

	/**
	 * 上报播发记录
	 *
	 */
	@ApiOperation(value = "消息播发记录上报", notes = "上报指定的消息播发记录到上级平台")
	@RequestMapping(value = "reportEbmBrLog", method = RequestMethod.POST)
	public ResponseEntity<String> getDispatchEbm(@RequestParam String  ebmIds) {

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		ebmBrdLogService.reportEbmBrLog(ebmIds);

		// 返回处理结果
		resultJson.setDataMap(dataMap);
 		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}


}
