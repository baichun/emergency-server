package com.comtom.bc.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.result.JsonResult;
import com.comtom.bc.common.result.ResultKey;
import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.common.utils.JsonUtil;
import com.comtom.bc.exchange.commom.EBDType;
import com.comtom.bc.exchange.commom.SendFlag;
import com.comtom.bc.server.asset.WebCxt;
import com.comtom.bc.server.repository.entity.Ebd;
import com.comtom.bc.server.req.EbdDownloadReq;
import com.comtom.bc.server.req.EbdQueryReq;
import com.comtom.bc.server.service.EbdService;

/**
 * EBD(预警信息)控制器实现
 * 
 * @author zhucanhui
 *
 */
@Api(value = "数据包信息")
@RestController
@RequestMapping(value = "api/ebd")
public class EbdInfoController {

	@Autowired
	private EbdService ebdService;

	/**
	 * 数据包信息查询
	 * 
	 * @param req
	 * @param jsessionid
	 * @return ResponseEntity<String>
	 */
	@ApiOperation(value = "数据包信息", notes = "数据包信息查询")
	@RequestMapping(value = "get", method = RequestMethod.POST)
	public ResponseEntity<String> getEbd(@RequestBody EbdQueryReq req,
			@RequestParam String jsessionid) {

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		Page<Ebd> ebdPage = ebdService.getEbd(req);
		dataMap.put(ResultKey.EBD_LIST_KEY, ebdPage.getContent());
		dataMap.put(ResultKey.TOTAL_COUNT_KEY, ebdPage.getSize());

		// 返回处理结果
		resultJson.setDataMap(dataMap);
		resultJson.setTotalCount(ebdPage.getTotalElements());
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}

	/**
	 * 预警信息详情查询
	 * 
	 * @param req
	 * @param jsessionid
	 * @return ResponseEntity<String>
	 */
	@ApiOperation(value = "数据包信息", notes = "数据包信息详情")
	@RequestMapping(value = "getDetail", method = RequestMethod.POST)
	public ResponseEntity<String> getEbdDetail(@RequestBody EbdQueryReq req,
			@RequestParam String jsessionid) {

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		Ebd ebd = ebdService.getEbdDetail(req.getEbdId());
		dataMap.put(ResultKey.EBD_INFO_KEY, ebd);

		// 返回处理结果
		resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
	}

	/**
	 * 数据包下载
	 * 
	 * @param id
	 * @return ResponseEntity<InputStreamResource>
	 * @throws IOException
	 */
	@ApiOperation(value = "数据包信息", notes = "数据包下载")
	@RequestMapping(value = "/download", method = RequestMethod.POST)
	public ResponseEntity<InputStreamResource> downloadFile(HttpServletRequest request,
			@RequestBody EbdDownloadReq req, @RequestParam String jsessionid) throws IOException {

		// 设置公用参数
		String account = (String) request.getAttribute(Constants.ACCOUNT);

		Ebd ebd = null;

		// EBMID
		String ebmId = req.getEbmId();

		// EBDID
		String ebdId = req.getEbdId();

		if (CommonUtil.isNotEmpty(ebdId)) {
			ebd = ebdService.findEbd(ebdId);
		} else if (CommonUtil.isNotEmpty(ebmId)) {

			// 根据ebmId获取ebd数据包名称
			List<Ebd> ebdList = ebdService.findByEbmId(ebmId);

			if (CommonUtil.isNotEmpty(ebdList)) {
				ebd = ebdList.get(0);
			}

		} else {
			return new ResponseEntity<InputStreamResource>(HttpStatus.NOT_FOUND);
		}

		String ebdName = ebd.getEbdName();
		String filePath = null;

		// 获取发送标识
		Integer sendFlag = ebd.getSendFlag();
		if (sendFlag.equals(SendFlag.receive.getValue())) {
			filePath = ebdService.getEbdFilePath(sendFlag, ebdName, null,null);
		} else if (sendFlag.equals(SendFlag.send.getValue())) {
			filePath = ebdService.getEbdFilePath(sendFlag, ebdName, EBDType.EBM.name(),ebd.getEbdDestEbrId());
		}

		FileSystemResource file = new FileSystemResource(filePath);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Content-Disposition",
				String.format("attachment; filename=\"%s\"", file.getFilename()));
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");

		// 用户操作日志
		request.setAttribute(Constants.ACCOUNT, account);
		request.setAttribute(Constants.PORTAL_TYPE, req.getPortalType());

		// 设置用户操作日志参数和内容
		String logContent = "用户【" + account + "】下载数据包文件【" + ebdName + "】成功.";
		request.setAttribute(Constants.USER_LOG, logContent);

		return ResponseEntity.ok().headers(headers).contentLength(file.contentLength())
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(new InputStreamResource(file.getInputStream()));
	}

	@ApiOperation(value = "查找数据包文件是否存在", notes = "查找数据包文件是否存在")
	@RequestMapping(value = "/getFile", method = RequestMethod.GET)
	public ResponseEntity<String> getFile(HttpServletRequest request,
															@RequestParam String ebmId, @RequestParam String jsessionid) throws IOException {
		// 设置公用参数
		String account = (String) request.getAttribute(Constants.ACCOUNT);

		Ebd ebd = null;

		if (CommonUtil.isNotEmpty(ebmId)) {

			// 根据ebmId获取ebd数据包名称
			List<Ebd> ebdList = ebdService.findByEbmId(ebmId);

			if (CommonUtil.isNotEmpty(ebdList)) {
				ebd = ebdList.get(0);
			}

		} else {
			return new ResponseEntity<String>("99", HttpStatus.NOT_FOUND);
		}

		String ebdName = ebd.getEbdName();
		String filePath = null;

		// 获取发送标识
		Integer sendFlag = ebd.getSendFlag();
		if (sendFlag.equals(SendFlag.receive.getValue())) {
			filePath = ebdService.getEbdFilePath(sendFlag, ebdName, null,null);
		} else if (sendFlag.equals(SendFlag.send.getValue())) {
			filePath = ebdService.getEbdFilePath(sendFlag, ebdName, EBDType.EBM.name(),ebd.getEbdDestEbrId());
		}

		FileSystemResource file = new FileSystemResource(filePath);
		if(!file.exists()){
			return new ResponseEntity<String>("99", HttpStatus.OK);
		}
		return new ResponseEntity<String>("0", HttpStatus.OK);

	}


	@ApiOperation(value = "数据包信息", notes = "web端数据包下载")
	@RequestMapping(value = "/downfile", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> downloadFile(HttpServletRequest request,
															@RequestParam String ebmId, @RequestParam String jsessionid) throws IOException {

		// 设置公用参数
		String account = (String) request.getAttribute(Constants.ACCOUNT);

		Ebd ebd = null;

		if (CommonUtil.isNotEmpty(ebmId)) {

			// 根据ebmId获取ebd数据包名称
			List<Ebd> ebdList = ebdService.findByEbmId(ebmId);

			if (CommonUtil.isNotEmpty(ebdList)) {
				ebd = ebdList.get(0);
			}

		} else {
			return new ResponseEntity<InputStreamResource>(HttpStatus.NOT_FOUND);
		}

		String ebdName = ebd.getEbdName();
		String filePath = null;

		// 获取发送标识
		Integer sendFlag = ebd.getSendFlag();
		if (sendFlag.equals(SendFlag.receive.getValue())) {
			filePath = ebdService.getEbdFilePath(sendFlag, ebdName, null,null);
		} else if (sendFlag.equals(SendFlag.send.getValue())) {
			filePath = ebdService.getEbdFilePath(sendFlag, ebdName, EBDType.EBM.name(),ebd.getEbdDestEbrId());
		}

		FileSystemResource file = new FileSystemResource(filePath);
		if(!file.exists()){
			return new ResponseEntity<InputStreamResource>(HttpStatus.NOT_FOUND);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Content-Disposition",
				String.format("attachment; filename=\"%s\"", file.getFilename()));
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");

		// 用户操作日志
		request.setAttribute(Constants.ACCOUNT, account);
		request.setAttribute(Constants.PORTAL_TYPE, 1);

		// 设置用户操作日志参数和内容
		String logContent = "用户【" + account + "】下载数据包文件【" + ebdName + "】成功.";
		request.setAttribute(Constants.USER_LOG, logContent);

		return ResponseEntity.ok().headers(headers).contentLength(file.contentLength())
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(new InputStreamResource(file.getInputStream()));
	}
}
