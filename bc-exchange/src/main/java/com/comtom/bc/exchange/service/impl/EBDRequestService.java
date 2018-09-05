package com.comtom.bc.exchange.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.comtom.bc.common.Constants;
import com.comtom.bc.exchange.commom.EBDRespResultEnum;
import com.comtom.bc.exchange.commom.EBDType;
import com.comtom.bc.exchange.excepion.EbmException;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.details.other.EBDResponse;
import com.comtom.bc.exchange.model.signature.Signature;
import com.comtom.bc.exchange.util.EBDModelBuild;
import com.comtom.bc.exchange.util.FileUtil;
import com.comtom.bc.exchange.util.TarFileUtil;
import com.comtom.bc.exchange.util.XmlUtil;

/**
 * @author nobody 处理应急广播请求数据解析
 */
@Service
public class EBDRequestService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private EBMServiceManager ebmServiceManager;

	@Autowired
	private SignValidateService signValidateService;

	/**
	 * 请求服务
	 * 
	 * @param request
	 * @param response
	 * @param receiveFilePath
	 *            保存接收文件路径
	 */
	public void service(HttpServletRequest request, HttpServletResponse response,
			String receiveFilePath, String sendFilePath, String ebrId, String srcURL,
			String ebdIndex) {
		// 解析请求
		EBDResponse ebdResponse = handleRequest(request, response, receiveFilePath);
		// 封装回复数据包模型
		EBD ebd = EBDModelBuild.buildResponse(ebrId, srcURL, ebdIndex, ebdResponse);
		// 通用反馈文件夹
		String ebdResponseFilePath = sendFilePath + File.separator + EBDType.EBDResponse.name();

		// 将对象转换为文件
		File file = FileUtil.converFile(ebdResponseFilePath, ebd);

		// 生成签名和签名文件
		Signature signature = EBDModelBuild.buildSignature(file, ebd.getEBDID());
		File signFile = FileUtil.converFile(ebdResponseFilePath, signature);

		// 将回复数据包打成tar包
		List<File> files = new ArrayList<File>();
		files.add(file);
		files.add(signFile);

		File tarFile = TarFileUtil.compressorsTar(ebd, files, ebdResponseFilePath);
		// 发送通用反馈
		sendResponse(tarFile, response);
		// 返回结果之后业务处理
		ebmServiceManager.dispatchAfterService(ebd, file.getName());
	}

	/**
	 * 处理请求
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	private EBDResponse handleRequest(HttpServletRequest request, HttpServletResponse response,
			String filePath) {
		// 获得输入流，实体，将获得的输入流写入文件
		MultipartHttpServletRequest mrequest = (MultipartHttpServletRequest) request;
		MultipartFile t_multipartFile = null;
		String orginalFileName = null;
		Collection<MultipartFile> partFiles = mrequest.getFileMap().values();
		for (MultipartFile multipartFile : partFiles) {
			orginalFileName = multipartFile.getOriginalFilename();
			if (orginalFileName.endsWith(".tar")) {
				t_multipartFile = multipartFile;
				break;
			}
		}
		if (t_multipartFile == null) {
			logger.error("接收文件为空");
			return buildEBDResponse(EBDRespResultEnum.parsefailure);
		}
		// 接收的tar文件保存路径
		String tarFilePath = filePath;
		File tarDir = new File(tarFilePath);
		if (!tarDir.exists()) {
			tarDir.mkdirs();
		}
		// 设定所接收的文件的名称
		File file = new File(tarDir.getAbsolutePath() + File.separator + orginalFileName);
		try {
			t_multipartFile.transferTo(file);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("转换文件异常:" + e.getMessage());
			return buildEBDResponse(EBDRespResultEnum.parsefailure);
		}

		// 解析文件
		List<File> resources = new ArrayList<File>();
		File EBDBFile = null;
		File EBDSFile = null;
		// 解压tar包到tar的文件夹路径下
		List<File> fileList = TarFileUtil.decompressorsTar(file);
		for (File file2 : fileList) {
			String name = file2.getName();
			if (name.startsWith("EBDB")) {
				EBDBFile = file2;
			} else if (name.startsWith("EBDS")) {
				EBDSFile = file2;
			} else {
				resources.add(file2);
			}
		}
		// 业务数据文件
		if (EBDBFile == null) {
			logger.error("获取业务数据文件为空");
			return buildEBDResponse(EBDRespResultEnum.parsefailure);
		}
		// 将文件转换xml
		String eBDxmlString = null;
		try {
			eBDxmlString = FileUtils.readFileToString(EBDBFile, "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("读取业务文件异常:" + e.getMessage());
		}
		if (StringUtils.isEmpty(eBDxmlString)) {
			return buildEBDResponse(EBDRespResultEnum.parsefailure);
		}
		// 转换对象
		EBD eBD = XmlUtil.fromXml(eBDxmlString, EBD.class);
		// 签名文件
		if (EBDSFile != null && Constants.VERIFY_SIGN) {
			// 签名验证
			boolean flag = signValidateService.analysis(EBDSFile, EBDBFile, eBD);
			if (!flag) {
				logger.error("签名验证失败");
				return buildEBDResponse(EBDRespResultEnum.wrongsignature);
			}
		}
		try {
			// 接收数据包业务处理
			ebmServiceManager.dispatchService(eBD, orginalFileName, resources);
		} catch (EbmException ebmException) {
			logger.error("业务异常:" + ebmException.getMessage());
			if (ebmException.getResultEnum() != null) {
				return buildEBDResponse(ebmException.getResultEnum());
			} else {
				return buildEBDResponse(EBDRespResultEnum.othererrors);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("业务异常:" + e.getMessage());
			return buildEBDResponse(EBDRespResultEnum.othererrors);
		}
		return buildEBDResponse(EBDRespResultEnum.receivevalid);
	}

	/**
	 * 发送tar包文件回复请求
	 * 
	 * @param tarfile
	 * @param response
	 */
	private void sendResponse(File tarfile, HttpServletResponse response) {
		synchronized(this) {
			// 以流的形式下载文件。
			response.setHeader("content-disposition", "attachment;filename=" + tarfile.getName());
			InputStream fis = null;
			OutputStream toClient = null;
			try {
				fis = new BufferedInputStream(new FileInputStream(tarfile));
				toClient = new BufferedOutputStream(response.getOutputStream());
				byte[] b = new byte[1024];
				int len = 0;
				while ((len = fis.read(b)) != -1) {
					toClient.write(b, 0, len);
				}
				toClient.flush();
			} catch (Exception e) {
				logger.error("回复请求异常:" + e.getMessage());
				e.printStackTrace();
			} finally {
				IOUtils.closeQuietly(fis);
				IOUtils.closeQuietly(toClient); //TODO  您的主机中的软件中止了一个已建立的连接。
			}
		}

	}

	private static EBDResponse buildEBDResponse(EBDRespResultEnum respResultEnum) {
		EBDResponse response = new EBDResponse();
		response.setResultCode(respResultEnum.getCode());
		response.setResultDesc(respResultEnum.getEbdResponseResult());
		return response;
	}

}
