package com.comtom.bc.server.task;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.comtom.bc.exchange.commom.EBRInfoType;
import com.comtom.bc.exchange.commom.SyncFlag;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.commom.EBRDT;
import com.comtom.bc.exchange.model.ebd.commom.RelatedEBRPS;
import com.comtom.bc.exchange.model.ebd.details.info.EBRDTInfo;
import com.comtom.bc.exchange.util.DateStyle;
import com.comtom.bc.exchange.util.DateTimeUtil;
import com.comtom.bc.exchange.util.EBDModelBuild;
import com.comtom.bc.server.repository.entity.EbrTerminal;
import com.comtom.bc.server.service.EbrTerminalService;

/**
 * 
 * 终端信息上报
 * @author kidsoul
 */
//@Component
//@Configurable
//@EnableScheduling
public class DTInfoReport extends AbstractReport{ 
  
	@Autowired
	private EbrTerminalService terminalService;
	
	/**
	 * 日志记录器
	 */
	private static final Logger logger = LoggerFactory.getLogger(DTInfoReport.class);
	
	@Scheduled(cron = "${task.ebr-dt.cron}")
	@Override
	public void report() {
		//上级平台地址
		String parentUrl=getParentUrl();
		if(StringUtils.isEmpty(parentUrl)){
			return ;
		}		
		//平台地址
		String srcURL = getPlatUrl();
		//ebd序列号
		String ebdIndex = generateEbdInde();
		//平台ID
		String ebrId = getPlatFormId();
		
		List<EbrTerminal> ebrTrmFound = terminalService.listTerminal(true, null, null);
		if(CollectionUtils.isEmpty(ebrTrmFound)){
			return ;
		}
		List<EBRDT> devTrmList = new ArrayList<EBRDT>();
		for(EbrTerminal fnd : ebrTrmFound) {
			EBRDT devTrm = new EBRDT();
			devTrm.setEBRID(fnd.getTerminalEbrId());
			devTrm.setEBRName(fnd.getTerminalEbrName());
			devTrm.setLatitude(fnd.getLatitude());
			devTrm.setLongitude(fnd.getLongitude());
			devTrm.setRptType(EBRInfoType.Sync.name());
			devTrm.setRptTime(DateTimeUtil.dateToString(fnd.getUpdateTime(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			
			String relatePsId=fnd.getRelatedPsEbrId();
			if(StringUtils.isNotEmpty(relatePsId)){
				RelatedEBRPS ebrPS = new RelatedEBRPS();
				ebrPS.setEBRID(relatePsId);
				devTrm.setRelatedEBRPS(ebrPS);
			}
			String param1=fnd.getParam1();
			String param2=fnd.getParam2();
			if(StringUtils.isNotEmpty(param1)||StringUtils.isNotEmpty(param2)){
				List<String> params2 = new ArrayList<String>();
				devTrm.setParams(params2);
				params2.add(param1);
				params2.add(param2);
			}
			devTrmList.add(devTrm);
		}
		EBRDTInfo devTrmInfo = new EBRDTInfo();
		devTrmInfo.setDataList(devTrmList);
		EBD ebd=EBDModelBuild.buildEBRDTInfo(ebrId, srcURL, ebdIndex, devTrmInfo,null);
		EBD response=sendEbd(ebd);
		boolean success=vilidataResult(response);
		logger.info(" DTInfoReport Is Success : "+ success);
		if(!success){
			StringBuffer errMsgBuf = new StringBuffer();
			errMsgBuf.append("上报终端信息错误, DTInfoReport : ");
			errMsgBuf.append(null == ebd ? "ebd为空" : "ebd.id=" + ebd.getEBDID());
			errMsgBuf.append(null == response ? ", ebd response为空" : ", ebd.response.id=" + response.getEBDID());
			logger.error(errMsgBuf.toString());
			return ;
		}

		for (EbrTerminal object : ebrTrmFound) {
			object.setSyncFlag(SyncFlag.sync.getValue());
			terminalService.saveOrUpdate(object);
		}
		
	}

}


