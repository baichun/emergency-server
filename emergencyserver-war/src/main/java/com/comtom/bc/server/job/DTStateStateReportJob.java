package com.comtom.bc.server.job;

import com.comtom.bc.exchange.commom.RSStateEnum;
import com.comtom.bc.exchange.commom.SyncFlag;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.commom.EBRDT;
import com.comtom.bc.exchange.model.ebd.details.state.EBRDTState;
import com.comtom.bc.exchange.util.DateStyle;
import com.comtom.bc.exchange.util.DateTimeUtil;
import com.comtom.bc.exchange.util.EBDModelBuild;
import com.comtom.bc.server.repository.entity.EbrTerminal;
import com.comtom.bc.server.service.EbrTerminalService;
import org.apache.commons.lang.StringUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * 
 * 终端状态上报定时任务
 *
 */
@DisallowConcurrentExecution
public class DTStateStateReportJob extends AbstractJob implements Job{

	@Autowired
	private EbrTerminalService terminalService;
	
	/**
	 * 日志记录器
	 */
	private static final Logger logger = LoggerFactory.getLogger(DTStateStateReportJob.class);

	public void execute(JobExecutionContext context) throws JobExecutionException {
		//上级平台地址
		String parentUrl=getParentUrl();
		if(StringUtils.isEmpty(parentUrl)){
			return ;
		}	

		Executors.newSingleThreadExecutor().execute(new Runnable() {
			@Override
			public void run() {
				reportDtState();

			}
		});
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
		List<EbrTerminal> ebrTrmFound = terminalService.listTerminalStatus(true, null, null);
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
		for (EbrTerminal object : ebrTrmFound) {
			object.setStatusSyncFlag(SyncFlag.sync.getValue());
			//terminalService.saveOrUpdate(object);
		}
		terminalService.updateEbrTerminal(ebrTrmFound);
	}
	
}


