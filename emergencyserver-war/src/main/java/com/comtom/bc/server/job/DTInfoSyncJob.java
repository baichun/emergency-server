package com.comtom.bc.server.job;

import com.comtom.bc.exchange.commom.EBDType;
import com.comtom.bc.exchange.commom.EBRInfoType;
import com.comtom.bc.exchange.commom.SyncFlag;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.commom.EBRBS;
import com.comtom.bc.exchange.model.ebd.commom.EBRDT;
import com.comtom.bc.exchange.model.ebd.commom.RelatedEBRPS;
import com.comtom.bc.exchange.model.ebd.details.info.EBRDTInfo;
import com.comtom.bc.exchange.model.ebd.details.other.OMDRequest;
import com.comtom.bc.exchange.util.*;
import com.comtom.bc.server.repository.entity.EbrBroadcast;
import com.comtom.bc.server.repository.entity.EbrTerminal;
import com.comtom.bc.server.service.EbrBsService;
import com.comtom.bc.server.service.EbrTerminalService;
import com.comtom.bc.server.service.impl.BaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *  发出同步终端信息请求到大喇叭系统
 *
 */
public class DTInfoSyncJob extends AbstractJob implements Job{


	@Autowired
	private BaseServiceImpl baseServiceImpl;

	@Autowired
	private EbrBsService broadcastService;
	
	/**
	 * 日志记录器
	 */
	private static final Logger logger = LoggerFactory.getLogger(DTInfoSyncJob.class);

	public void execute(JobExecutionContext context) throws JobExecutionException {
//		List<EbrBroadcast> broadcastFound = broadcastService.listBroadcast(true, null, null);
//		if(CollectionUtils.isEmpty(broadcastFound)){
//			return ;
//		}
//		boolean isSuccess=sendEbd(broadcastFound.get(0),EBDType.EBRDTInfo.name());
//		if(!isSuccess){
//			return;
//		}
//		try {
//			Thread.sleep(30000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		// 终端状态同步请求；
//		sendEbd(broadcastFound.get(0),EBDType.EBRDTState.name());


	}

	private boolean sendEbd(EbrBroadcast fnd,String omdType){
		String srcUrl = baseServiceImpl.getPlatFormUrl();
		EBRBS bs = new EBRBS();
		bs.setRptTime(DateTimeUtil.dateToString(fnd.getUpdateTime(), DateStyle.YYYY_MM_DD_HH_MM_SS.getValue()));
		bs.setRptType(EBRInfoType.Sync.name());

		String destUrl = fnd.getBsUrl();
		OMDRequest omdRequest = new OMDRequest();
		omdRequest.setOMDType(omdType);
		String ebdIndex = baseServiceImpl.getEbdId();
		EBD ebd = EBDModelBuild.buildOMDRequest(fnd.getBsEbrId(), srcUrl, ebdIndex, omdRequest);
		String filePath = baseServiceImpl.getSendEbdUrl() + File.separator + ebd.getEBDType();
		List<File> fileList = new ArrayList<File>();

		// 根据EBD生成文件
		File xmlFile = FileUtil.converFile(filePath, ebd);
		fileList.add(xmlFile);
		// 生成联动TAR包
		File tarFile = TarFileUtil.compressorsTar(ebd, fileList, filePath);
		// 接收文件保存路径
		String filePathReceive = baseServiceImpl.getReceiveEbdUrl();

		// 上传文件至对应播出系统
		EBD ebdPack = HttpRequestUtil.sendFile(tarFile, destUrl, filePathReceive);

		boolean success=vilidataResult(ebdPack);

		return success;

	}

}


