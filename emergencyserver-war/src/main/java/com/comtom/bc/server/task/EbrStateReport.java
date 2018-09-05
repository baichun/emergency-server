package com.comtom.bc.server.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.comtom.bc.exchange.commom.RSStateEnum;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.commom.EBRAS;
import com.comtom.bc.exchange.model.ebd.commom.EBRBS;
import com.comtom.bc.exchange.model.ebd.commom.EBRDT;
import com.comtom.bc.exchange.model.ebd.commom.EBRPS;
import com.comtom.bc.exchange.model.ebd.details.state.EBRASState;
import com.comtom.bc.exchange.model.ebd.details.state.EBRBSState;
import com.comtom.bc.exchange.model.ebd.details.state.EBRDTState;
import com.comtom.bc.exchange.model.ebd.details.state.EBRPSState;
import com.comtom.bc.exchange.util.DateStyle;
import com.comtom.bc.exchange.util.DateTimeUtil;
import com.comtom.bc.exchange.util.EBDModelBuild;
import com.comtom.bc.server.repository.entity.EbrAdaptor;
import com.comtom.bc.server.repository.entity.EbrBroadcast;
import com.comtom.bc.server.repository.entity.EbrPlatform;
import com.comtom.bc.server.repository.entity.EbrTerminal;
import com.comtom.bc.server.service.EbrAdaptorService;
import com.comtom.bc.server.service.EbrBsService;
import com.comtom.bc.server.service.EbrPlatformService;
import com.comtom.bc.server.service.EbrTerminalService;

/**
 * 
 * 资源状态上报定时任务
 * @author kidsoul
 *
 */
//@Component
//@Configurable
//@EnableScheduling
public class EbrStateReport extends AbstractReport{ 
  
	@Autowired
	private EbrPlatformService platformService;
	
	@Autowired
	private EbrAdaptorService adaptorService;
	
	@Autowired
	private EbrBsService broadcastService;
	
	@Autowired
	private EbrTerminalService terminalService;
	
	/**
	 * 日志记录器
	 */
	private static final Logger logger = LoggerFactory.getLogger(EbrStateReport.class);
	
	@Scheduled(cron = "${task.ebr-state.cron}")
	@Override
	public void report() {
		//上级平台地址
		String parentUrl=getParentUrl();
		if(StringUtils.isEmpty(parentUrl)){
			return ;
		}	
		
		Executors.newSingleThreadExecutor().execute(new Runnable() {
			@Override
			public void run() {
				reportPlatState();
				reportAsState();
				reportBsState();
				// reportDtState();  //TODO : 暂时停止上报终端状态
			}
		});
	}

	/**
	 * 上报平台状态
	 */
	private void reportPlatState() {
		//平台地址
		String srcURL = getPlatUrl();
		//ebd序列号
		String ebdIndex = generateEbdInde();
		//平台ID
		String ebrId = getPlatFormId();
		
		List<EbrPlatform> platformFound = platformService.listPlatform(false, null, null);
		if(CollectionUtils.isEmpty(platformFound)){
			return ;
		}
		List<EBRPS> ebrPsList = new ArrayList<EBRPS>();
		for(EbrPlatform fnd : platformFound) {
			EBRPS ps = new EBRPS();
			ps.setRptTime(DateTimeUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS.getValue()));
			ps.setEBRID(fnd.getPsEbrId());
            ps.setStateCode(fnd.getPsState());
			ps.setStateDesc(RSStateEnum.getNameByCode(fnd.getPsState()));
			ebrPsList.add(ps);
		}
		
		EBRPSState eBRPSState=new EBRPSState();
		eBRPSState.setDataList(ebrPsList);
		EBD ebd=EBDModelBuild.buildPSState(ebrId, srcURL, ebdIndex, eBRPSState,null);
		EBD response=sendEbd(ebd);
		boolean success=vilidataResult(response);
		if(!success){
			logger.error("上报平台状态错误");
			return ;
		}
	}
	
	/**
	 * 上报接收设备状态
	 */
	private void reportAsState() {
		//平台地址
		String srcURL = getPlatUrl();
		//ebd序列号
		String ebdIndex = generateEbdInde();
		//平台ID
		String ebrId = getPlatFormId();
		
		EBRASState adaptorState = new EBRASState();
		List<EbrAdaptor> adaptorFound = adaptorService.listAdaptor(false, null, null);
		if(CollectionUtils.isEmpty(adaptorFound)){
			return ;
		}
		List<EBRAS> ebrAsList = new ArrayList<EBRAS>();
		for(EbrAdaptor fnd : adaptorFound) {
			EBRAS as = new EBRAS();
            as.setRptTime(DateTimeUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS.getValue()));
            as.setEBRID(fnd.getAsEbrId());
            as.setStateCode(fnd.getAsState());
            as.setStateDesc(RSStateEnum.getNameByCode(fnd.getAsState()));
			ebrAsList.add(as);
		}
		adaptorState.setDataList(ebrAsList);
		EBD ebd=EBDModelBuild.buildASState(ebrId, srcURL, ebdIndex, adaptorState,null);
		EBD response=sendEbd(ebd);
		boolean success=vilidataResult(response);
		if(!success){
			logger.error("上报接收设备状态错误");
			return ;
		}
	}
	
	/**
	 * 上报接收播出系统状态
	 */
	private void reportBsState() {
		//平台地址
		String srcURL = getPlatUrl();
		//ebd序列号
		String ebdIndex = generateEbdInde();
		//平台ID
		String ebrId = getPlatFormId();
		
		EBRBSState broadcastState = new EBRBSState();
		List<EbrBroadcast> broadcastFound = broadcastService.listBroadcast(false, null, null);		
		if(CollectionUtils.isEmpty(broadcastFound)){
			return ;
		}
		List<EBRBS> ebrBsList = new ArrayList<EBRBS>();
		for(EbrBroadcast fnd : broadcastFound) {
			EBRBS bs = new EBRBS();
			bs.setRptTime(DateTimeUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS.getValue()));
			bs.setEBRID(fnd.getBsEbrId());
			bs.setStateCode(fnd.getBsState());
			bs.setStateDesc(RSStateEnum.getNameByCode(fnd.getBsState()));
            ebrBsList.add(bs);
		}
		broadcastState.setDataList(ebrBsList);
		EBD ebd=EBDModelBuild.buildBSState(ebrId, srcURL, ebdIndex, broadcastState,null);
		EBD response=sendEbd(ebd);
		boolean success=vilidataResult(response);
		if(!success){
			logger.error("上报播出系统状态错误");
			return ;
		}
	}

	/**
	 * 上报终端状态
	 */
	private void reportDtState() {
		//平台地址
		String srcURL = getPlatUrl();
		//ebd序列号
		String ebdIndex = generateEbdInde();
		//平台ID
		String ebrId = getPlatFormId();
		
		EBRDTState devTrmState = new EBRDTState();
		List<EbrTerminal> ebrTrmFound = terminalService.listTerminal(false, null, null);
		if(CollectionUtils.isEmpty(ebrTrmFound)){
			return ;
		}
		List<EBRDT> devTrmList = new ArrayList<EBRDT>();
		for(EbrTerminal fnd : ebrTrmFound) {
			EBRDT devTrm = new EBRDT();
			devTrm.setRptTime(DateTimeUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS.getValue()));
			devTrm.setEBRID(fnd.getTerminalEbrId());
			devTrm.setStateCode(fnd.getTerminalState());
			devTrm.setStateDesc(RSStateEnum.getNameByCode(fnd.getTerminalState()));
			devTrmList.add(devTrm);
		}
		devTrmState.setDataList(devTrmList);
		EBD ebd=EBDModelBuild.buildDTState(ebrId, srcURL, ebdIndex, devTrmState,null);
		EBD response=sendEbd(ebd);
		boolean success=vilidataResult(response);
		if(!success){
			logger.error("上报接终端状态错误");
			return ;
		}
	}
	
}


