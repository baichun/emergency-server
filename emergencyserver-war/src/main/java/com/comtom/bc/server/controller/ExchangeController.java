package com.comtom.bc.server.controller;

import com.comtom.bc.common.utils.ResUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.result.JsonResult;
import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.common.utils.JsonUtil;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.details.other.OMDRequest;
import com.comtom.bc.exchange.model.signature.Signature;
import com.comtom.bc.exchange.service.impl.EBDRequestService;
import com.comtom.bc.exchange.util.EBDModelBuild;
import com.comtom.bc.exchange.util.FileUtil;
import com.comtom.bc.exchange.util.HttpRequestUtil;
import com.comtom.bc.exchange.util.TarFileUtil;
import com.comtom.bc.server.asset.WebCxt;
import com.comtom.bc.server.repository.entity.EbrBroadcast;
import com.comtom.bc.server.repository.entity.EbrPlatform;
import com.comtom.bc.server.req.OmdReq;
import com.comtom.bc.server.service.EbrBsService;
import com.comtom.bc.server.service.EbrPlatformService;
import com.comtom.bc.server.service.impl.BaseServiceImpl;

/**
 * 联动接口，实现联动协议接口对接
 * 
 * @author zhucanhui
 */
@Api(value = "联动接口")
@RestController
public class ExchangeController {

	@Autowired
	private BaseServiceImpl baseServiceImpl;

	@Autowired
	private EBDRequestService eBDRequestService;

	@Autowired
	private EbrBsService ebrBsService;

	@Autowired
	private EbrPlatformService ebrPsService;

	@ApiOperation(value = "联动接口", notes = "数据包接收")
	@RequestMapping(value = "exchange", method = RequestMethod.POST)
	public void exchange(HttpServletRequest request, HttpServletResponse response) {
		String receiveFilePath = baseServiceImpl.getReceiveEbdUrl();
		String sendFilePath = baseServiceImpl.getSendEbdUrl();
		String ebrId = baseServiceImpl.getEbrPlatFormID();
		String srcURL = baseServiceImpl.getPlatFormUrl();
		String ebdIndex = baseServiceImpl.generateEbdIndex();
		eBDRequestService.service(request, response, receiveFilePath, sendFilePath, ebrId, srcURL,
				ebdIndex);
	}

	@ApiOperation(value = "联动接口", notes = "数据包请求发送")
	@RequestMapping(value = "sendOMDRequest", method = RequestMethod.POST)
	public ResponseEntity<String> sendOMDRequest(@RequestBody OmdReq req,
			@RequestParam String jsessionid) {

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();
		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		String omdType = req.getOmdType();
		String ebrId = req.getEbrId();
		String srcUrl = baseServiceImpl.getPlatFormUrl();
		String destUrl = null;

		// 根据资源编码获取资源网络地址URL
		if (ebrId.startsWith(Constants.EBR_TYPE_PLATFORM,Constants.RESTYPE_TOFFSET)) {
			EbrPlatform ebrPs = ebrPsService.findOne(ebrId);
			destUrl = ebrPs.getPsUrl();
		} else if (ResUtil.isEbrBS(ebrId)) {
			EbrBroadcast ebrBs = ebrBsService.findOne(ebrId);
			destUrl = ebrBs.getBsUrl();
		}

		OMDRequest omdRequest = new OMDRequest();
		omdRequest.setOMDType(omdType);
		String ebdIndex = baseServiceImpl.getEbdId();
		EBD ebd = EBDModelBuild.buildOMDRequest(ebrId, srcUrl, ebdIndex, omdRequest);

		String filePath = baseServiceImpl.getSendEbdUrl() + File.separator + ebd.getEBDType();
		List<File> fileList = new ArrayList<File>();

		// 根据EBD生成文件
		File xmlFile = FileUtil.converFile(filePath, ebd);

		// 生成签名和签名文件
		Signature signature = EBDModelBuild.buildSignature(xmlFile, ebd.getEBDID());
		File signFile = FileUtil.converFile(filePath, signature);
		fileList.add(xmlFile);
		fileList.add(signFile);

		// 生成联动TAR包
		File tarFile = TarFileUtil.compressorsTar(ebd, fileList, filePath);

		// 接收文件保存路径
		String filePathReceive = baseServiceImpl.getReceiveEbdUrl();

		// 上传文件至对应平台或播出系统资源
		EBD ebdPack = HttpRequestUtil.sendFile(tarFile, destUrl, filePathReceive);
		if (CommonUtil.isNotEmpty(ebdPack)) {
			resultJson.setDataMap(dataMap);
			return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.BAD_REQUEST);
		}

	}
}
