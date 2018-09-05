package com.comtom.bc.server.rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.result.JsonResult;
import com.comtom.bc.common.result.ResultCode;
import com.comtom.bc.common.utils.JsonUtil;
import com.comtom.bc.common.utils.Md5Util;
import com.comtom.bc.dispatch.common.ServiceFlowException;
import com.comtom.bc.exchange.commom.EBDType;
import com.comtom.bc.exchange.commom.EBMType;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.details.other.EBM;
import com.comtom.bc.exchange.model.ebd.ebm.Auxiliary;
import com.comtom.bc.exchange.model.ebd.ebm.MsgBasicInfo;
import com.comtom.bc.exchange.model.ebd.ebm.MsgContent;
import com.comtom.bc.exchange.model.ebd.ebm.SRC;
import com.comtom.bc.exchange.service.impl.EBMServiceManager;
import com.comtom.bc.exchange.util.DateStyle;
import com.comtom.bc.exchange.util.TarFileUtil;
import com.comtom.bc.server.asset.WebCxt;
import com.comtom.bc.server.repository.entity.Ebm;
import com.comtom.bc.server.req.EbmQueryReq;
import com.comtom.bc.server.rest.dto.SingleBroadcastDTO;
import com.comtom.bc.server.rest.util.JaxbObjectAndXmlUtil;
import com.comtom.bc.server.rest.util.RestUtil;
import com.comtom.bc.server.service.AccessAccountService;
import com.comtom.bc.server.service.EbmService;
import com.comtom.bc.server.service.SchemeService;
import com.comtom.bc.server.service.impl.BaseServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(value = "广播接口")
@RestController
@RequestMapping("/rest/broadcast")
public class BroadcastRest extends BaseRest{
	
	private static final Logger logger=LoggerFactory.getLogger(BroadcastRest.class);
	
	@Autowired
	private BaseServiceImpl baseServiceImpl;
	
	@Autowired
	private EBMServiceManager ebmServiceManager;
	
	@Autowired
	private SchemeService schemeService;
	
	@Autowired
	private EbmService ebmService;
	
	@Autowired
	private AccessAccountService accessAccountService;

	@RequestMapping(value = "request", method = RequestMethod.POST)
	@ApiOperation(value = "创建单次广播", notes = "创建单次广播")
	public ResponseEntity<String> request(HttpServletRequest request,@RequestParam("tarFile") MultipartFile multipartFile) {
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();
		String receiveFilePath = baseServiceImpl.getReceiveEbdUrl();
		if (multipartFile != null) {
			String orginalFileName = multipartFile.getOriginalFilename();//原始文件名
			int lastDotPosition = orginalFileName.lastIndexOf(Constants.SUFFIX_SPLIT);
			String noExtensionsFileName=(lastDotPosition == -1)?orginalFileName:orginalFileName.substring(0, lastDotPosition);//无后缀文件名
			File dir = new File(receiveFilePath);//文件接收目录
			if(!dir.exists()) {
				dir.mkdirs();
			}
			File destFile = new File(dir.getAbsolutePath(),orginalFileName);//转存文件
			boolean isXml = true;//是否XML
			File xmlFile = null;//XML数据文件
			File targetDir = new File(receiveFilePath,noExtensionsFileName);//附件目录(Tar包解压目录)
			//转存
			try {
				multipartFile.transferTo(destFile);
			} catch (IllegalStateException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			/*
			 * 如果是tar包
			 */
			if (orginalFileName.endsWith(".tar")) {
				isXml = false;
				//解压到同名目录下
				TarFileUtil.decompressorsTar(destFile);
				String targetFileName=noExtensionsFileName+".xml";//XML文件名
				xmlFile=new File(targetDir,targetFileName);
			}else {
				//不是tar包则认为是XML
				xmlFile=destFile;
			}
			//XML内容字符串
			String xmlStr=readFile(xmlFile);
			logger.info("XML内容", xmlStr);
			//反序列化为DTO对象
			SingleBroadcastDTO dto = JaxbObjectAndXmlUtil.xml2Object(xmlStr, SingleBroadcastDTO.class);
			if(dto.getPlatformId()==null) {
				dto.setPlatformId(getUserAccount(request));
			}else {
				String account = dto.getPlatformId();
				boolean exists = accessAccountService.validateExists(account);
				if(!exists) {
					resultJson.setResultCode(ResultCode.FAILURE);
					resultJson.setResultMsg("平台ID错误");
					return ResponseEntity.ok(JsonUtil.toJson(resultJson));
				}
			}
			
			if(dto.getTitle() == null) {
				resultJson.setResultCode(ResultCode.FAILURE);
				resultJson.setResultMsg("广播标题不能为空");
				return ResponseEntity.ok(JsonUtil.toJson(resultJson));
			}
			
			if(dto.getRegions() == null||dto.getRegions().isEmpty()) {
				resultJson.setResultCode(ResultCode.FAILURE);
				resultJson.setResultMsg("广播区域不能为空");
				return ResponseEntity.ok(JsonUtil.toJson(resultJson));
			}
			
			if(dto.getPlayTime() == null) {
				resultJson.setResultCode(ResultCode.FAILURE);
				resultJson.setResultMsg("播放时间不能为空");
				return ResponseEntity.ok(JsonUtil.toJson(resultJson));
			}
			
			
			File auxiliaryFile = null;//附件
			if(!dto.getContentType().equals("text")){
				if(isXml) {
					targetDir.mkdirs();
				}
				String url=dto.getUrl();
				if(StringUtils.isBlank(url)) {
					//如果url为空，返回错误
					resultJson.setResultCode(ResultCode.FAILURE);
					resultJson.setResultMsg("url不能为空");
					return ResponseEntity.ok(JsonUtil.toJson(resultJson));
					
				}else {
					//url不为空，处理附件
					try {
						URI uri=new URI(url);
						if(uri.isAbsolute()) {
							//如果是完整URI,则下载网络附件
							String fileName=new File(uri.getPath()).getName();//附件名
							auxiliaryFile=new File(targetDir.getAbsolutePath(),fileName);//附件转存文件
							RestUtil.download(uri, auxiliaryFile.getPath());//下载
							dto.setUrl(fileName);
						}else {
							//否则认为是tar包中的附件
							auxiliaryFile=new File(targetDir.getAbsolutePath(),url);
							if(!auxiliaryFile.exists()) {
								//如果附件不存在，返回错误
								resultJson.setResultCode(ResultCode.FAILURE);
								resultJson.setResultMsg("url解析错误");
								return ResponseEntity.ok(JsonUtil.toJson(resultJson));
							}
						}
					} catch (URISyntaxException e) {
						//如果不符合URI语法,返回错误
						logger.warn("url解析错误", e);
						resultJson.setResultCode(ResultCode.FAILURE);
						resultJson.setResultMsg("url解析错误");
						return ResponseEntity.ok(JsonUtil.toJson(resultJson));
					}
				}
			}else {
				if(dto.getContent() == null || dto.getContent().trim() == "") {
					resultJson.setResultCode(ResultCode.FAILURE);
					resultJson.setResultMsg("文本播放内容不能为空");
					return ResponseEntity.ok(JsonUtil.toJson(resultJson));
				}
			}
			String ebdId=handle(xmlFile,auxiliaryFile,dto);
			WebCxt.newJsonMap(resultJson).put("taskId", ebdId);
			logger.debug(JsonUtil.toJson(dto));
		}
		return ResponseEntity.ok(JsonUtil.toJson(resultJson));
	}
	
	/**
	 * 构造EBD对象,并交给应急广播服务分发器处理
	 * @param xmlFile xml数据文件
	 * @param auxiliaryFile 附件
	 * @param dto DTO对象
	 */
	private String handle(File xmlFile,File auxiliaryFile, SingleBroadcastDTO dto) {
		List<File> resources = new ArrayList<File>();
		if(auxiliaryFile!=null) {
			resources.add(auxiliaryFile);
		}
		EBD ebd = new EBD();
		ebd.setEBDType(EBDType.EBM.name());
		String ebdId=Md5Util.getMd5ByFile(xmlFile);
		String ebmId = ebdId;
		EbmQueryReq req = new EbmQueryReq();
		req.setEbmId(ebmId);
		
		Ebm alarmEbmDetail = ebmService.getAlarmEbmDetail(req);
		//如果有重复的ebmId则不再做重复处理
		if(alarmEbmDetail!=null) {
			return ebdId;
		}
		ebd.setEBDID(ebdId);
		SRC src= new SRC();
		src.setEBRID(dto.getPlatformId());
		ebd.setSRC(src);
		ebd.setEBDTime(DateFormatUtils.format(Calendar.getInstance(),DateStyle.YYYY_MM_DD_HH_MM_SS.getValue()));
		
		EBM ebm = new EBM();
		ebd.setEBM(ebm);
		ebm.setEBMID(ebmId);
		ebm.setEBMVersion(Constants.EBD_VERSION);
		
		MsgBasicInfo msgBasicInfo = new MsgBasicInfo();
		msgBasicInfo.setMsgType(EBMType.request.getValue());//播发请求
		String senderCode = dto.getPlatformId();
		msgBasicInfo.setSenderCode(senderCode);
		msgBasicInfo.setSenderName(getSenderName(senderCode));
		msgBasicInfo.setEventType(dto.getEventCategory());
		msgBasicInfo.setSeverity(dto.getEventLevel());
		msgBasicInfo.setSendTime(DateFormatUtils.format(Calendar.getInstance(), DateStyle.YYYY_MM_DD_HH_MM_SS.getValue()));
		msgBasicInfo.setStartTime(DateFormatUtils.format(dto.getPlayTime(), DateStyle.YYYY_MM_DD_HH_MM_SS.getValue()));
		msgBasicInfo.setEndTime(DateFormatUtils.format(dto.getPlayTime(), DateStyle.YYYY_MM_DD_HH_MM_SS.getValue()));
		ebm.setMsgBasicInfo(msgBasicInfo);
		
		MsgContent msgContent = new MsgContent();
		msgContent.setLanguageCode(Constants.LANGUAGE_CODE_ZH);//中文
		msgContent.setMsgTitle(dto.getTitle());
		msgContent.setAreaCode(join(String.valueOf(Constants.COMMA_SPLIT), dto.getRegions()));
		msgContent.setMsgDesc(dto.getContent());
		List<Auxiliary> auxiliaryList = new ArrayList<>();
		for(File resource:resources) {
			Auxiliary auxiliary = new Auxiliary();
			Integer auxiliaryType = null;
			if(resource.getName().toLowerCase().endsWith(".mp3")) {
				auxiliaryType = Constants.EBM_AUXILIARY_MP3;
			}else if(resource.getName().toLowerCase().endsWith(".wav")) {
				auxiliaryType = Constants.EBM_AUXILIARY_WAV;
			}else if(resource.getName().toLowerCase().endsWith(".txt")) {
				String content=readFile(resource);
				//将文本文件内容写到MsgDesc中
				msgContent.setMsgDesc(content);
				continue;
			}else {
				continue;
			}
			auxiliary.setAuxiliaryType(auxiliaryType);
			auxiliary.setAuxiliaryDesc(dto.getUrl());
			auxiliaryList.add(auxiliary);
		}
		msgContent.setAuxiliaryList(auxiliaryList);
		
		ebm.setMsgContent(msgContent);
		String fileName = xmlFile.getName();
		//服务分发
		ebmServiceManager.dispatchService(ebd,fileName.substring(0, fileName.lastIndexOf(".")), resources);
		return ebdId;
	}
	
	private String join(CharSequence delimiter, Iterable<? extends CharSequence> elements) {
		StringBuilder sb= new StringBuilder();
		Iterator<? extends CharSequence> it=elements.iterator();
		boolean hasNext=it.hasNext();
		while(hasNext) {
			sb.append(it.next());
			hasNext=it.hasNext();
			if(hasNext) {
				sb.append(delimiter);
			}
		}
		return sb.toString();
		
	}
	
	
	/**
	 * 取消广播
	 * @param request
	 * @param taskId
	 * @return
	 */
	@RequestMapping(value = "cancel/{taskId}", method = RequestMethod.POST)
	@ApiOperation(value = "取消广播", notes = "取消广播")
	@ApiImplicitParam(name="taskId",value="任务唯一ID",dataType="string",paramType="path")
	public ResponseEntity<String> cancel(HttpServletRequest request,@PathVariable String taskId) {
		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();
		/*String platformId = (String)request.getAttribute(Constants.ACCESS_ACCOUNT);
		
		EBD ebd = new EBD();
		ebd.setEBDType(EBDType.EBM.name());
		//生成一个ebdId
		String ebdId=Md5Util.getMd5Hex(GenUUId.uuid().getBytes());
		ebd.setEBDID(ebdId);
		
		SRC src= new SRC();
		src.setEBRID(platformId);
		ebd.setSRC(src);
		ebd.setEBDTime(DateFormatUtils.format(Calendar.getInstance(),DateStyle.YYYY_MM_DD_HH_MM_SS.getValue()));
		
		EBM ebm = new EBM();
		ebd.setEBM(ebm);
		ebm.setEBMID(ebdId);
		ebm.setEBMVersion(Constants.EBD_VERSION);
		MsgBasicInfo msgBasicInfo = new MsgBasicInfo();
		msgBasicInfo.setMsgType(EBMType.cancel.getValue());//取消播发
		String senderCode = platformId;
		msgBasicInfo.setSenderCode(senderCode);
		msgBasicInfo.setSenderName(getSenderName(senderCode));
		msgBasicInfo.setSentTime(DateFormatUtils.format(Calendar.getInstance(), DateStyle.YYYY_MM_DD_HH_MM_SS.getValue()));
		ebm.setMsgBasicInfo(msgBasicInfo);
		
		MsgContent msgContent = new MsgContent();
		ebm.setMsgContent(msgContent);
		
		
		 * 设置关联信息
		 
		RelatedEBD relatedEBD = new RelatedEBD();
		relatedEBD.setEBDID(taskId);
		ebd.setRelatedEBD(relatedEBD);
		
		RelatedInfo relatedInfo = new RelatedInfo();
		relatedInfo.setEBMID(taskId);
		ebm.setRelatedInfo(relatedInfo);
		//服务分发
		ebmServiceManager.dispatchService(ebd,null, null);*/
		try {
			boolean canceled = cancel(taskId);
		
			if(canceled) {
				resultJson.setResultCode(ResultCode.SUCCESSFUL);
				resultJson.setResultMsg("取消成功");
			}else {
				resultJson.setResultCode(ResultCode.FAILURE);
				resultJson.setResultMsg("取消失败");
			}
		} catch (ServiceFlowException e) {
			e.printStackTrace();
			resultJson.setResultCode(e.getErrorCode());
			resultJson.setResultMsg(e.getErrorDesc());
		}
		
		return ResponseEntity.ok(JsonUtil.toJson(resultJson));
	}
	
	/**
	 * 取消播发
	 * @param ebmId
	 * @return
	 */
	private boolean cancel(String ebmId) {
		EbmQueryReq req = new EbmQueryReq();
		req.setEbmId(ebmId);
		Ebm alarmEbm = ebmService.getAlarmEbmDetail(req);
		if (alarmEbm == null) {
			return false;
		}
		Integer schemeId = alarmEbm.getSchemeId();
		if (schemeId == null) {
			return false;
		}
		return schemeService.cancelEbdEbm(schemeId);
	}
	
	private String getSenderName(String senderCode) {
		return "已注册平台";
	}
	
	/** 读取文件内容
	 * @param file
	 * @return
	 */
	private String readFile(File file) {
		BufferedReader br =null;
		StringBuilder sb =new StringBuilder();
		try {
			//读取XML内容
			br=new BufferedReader(new FileReader(file));
			while(true) {
				String line=br.readLine();
				if(line==null) {
					break;
				}
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(br!=null) {
				IOUtils.closeQuietly(br);
			}
		}
		return sb.toString();
	}
	
}
