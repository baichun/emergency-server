package com.comtom.bc.server.task;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.comtom.bc.exchange.commom.EBDRespResultEnum;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.details.other.ConnectionCheck;
import com.comtom.bc.exchange.model.ebd.details.other.EBDResponse;
import com.comtom.bc.exchange.model.signature.Signature;
import com.comtom.bc.exchange.util.DateStyle;
import com.comtom.bc.exchange.util.DateTimeUtil;
import com.comtom.bc.exchange.util.EBDModelBuild;
import com.comtom.bc.exchange.util.FileUtil;
import com.comtom.bc.exchange.util.HttpRequestUtil;
import com.comtom.bc.exchange.util.TarFileUtil;
import com.comtom.bc.server.service.impl.BaseServiceImpl;

/**
 * 心跳上报定时任务
 *
 * @author kidsoul
 *
 */
//@Component
//@Configurable
//@EnableScheduling
public class HeatBeatTask {

	@Autowired
	private BaseServiceImpl serviceImpl;

	/**
	 * 日志记录器
	 */
	private static final Logger logger = LoggerFactory.getLogger(HeatBeatTask.class);

	/**
	 * 定时汇报心跳.
	 */
	@Scheduled(cron = "${task.heatbeat.cron}")
	public void heatbeat() {
		// 上级平台地址
		String parentUrl = serviceImpl.getParentPlatUrl();
		if (StringUtils.isEmpty(parentUrl)) {
			return;
		}
		// 平台地址
		String srcURL = serviceImpl.getPlatFormUrl();
		// ebd序列号
		String ebdIndex = "0000000000000000";
		// 平台ID
		String ebrId = serviceImpl.getEbrPlatFormID();

		// 接收文件保存路径
		String filePathReceive = serviceImpl.getReceiveEbdUrl();

		ConnectionCheck connectionCheck = new ConnectionCheck();
		Date date = new Date();
		String rptTime = DateTimeUtil.dateToString(date, DateStyle.YYYY_MM_DD_HH_MM_SS);
		connectionCheck.setRptTime(rptTime);

		EBD ebd = EBDModelBuild.buildConnectionCheck(ebrId, srcURL, ebdIndex, connectionCheck);

		// 发送文件保存路径（根据文件类型区分文件夹）
		String filePathSend = serviceImpl.getSendEbdUrl() + File.separator + ebd.getEBDType();

		File file = FileUtil.converFile(filePathSend, ebd);

		// 生成签名和签名文件
		Signature signature = EBDModelBuild.buildSignature(file, ebd.getEBDID());
		File signFile = FileUtil.converFile(filePathSend, signature);

		List<File> files = new ArrayList<File>();
		files.add(file);
		files.add(signFile);
		File tarfile = TarFileUtil.compressorsTar(ebd, files, filePathSend);
		EBD ebdReceive = HttpRequestUtil.sendFile(tarfile, parentUrl, filePathReceive);
		if (ebdReceive == null) {
			logger.error("发送心跳错误");
			return;
		}
		EBDResponse ebdResponse = ebdReceive.getEBDResponse();
		if (ebdResponse == null) {
			logger.error("发送心跳错误");
			return;
		}
		Integer resultCode = ebdResponse.getResultCode();

		if (!EBDRespResultEnum.receivevalid.getCode().equals(resultCode)) {
			logger.error("发送心跳错误");
			return;
		}
	}
}
