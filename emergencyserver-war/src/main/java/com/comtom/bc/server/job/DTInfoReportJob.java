package com.comtom.bc.server.job;

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
import org.apache.commons.lang.StringUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *  终端信息上报
 *
 */
@DisallowConcurrentExecution
public class DTInfoReportJob extends AbstractJob implements Job{

	@Autowired
	private EbrTerminalService terminalService;

	/**
	 * 日志记录器
	 */
	private static final Logger logger = LoggerFactory.getLogger(DTInfoReportJob.class);

	public void execute(JobExecutionContext context) throws JobExecutionException {
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

		Page<EbrTerminal> page = terminalService.listTerminalPage();
		List<EbrTerminal> ebrTrmFound = page.getContent();
		if(CollectionUtils.isEmpty(page.getContent())){
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
		//	terminalService.saveOrUpdate(object);
		}
		terminalService.updateEbrTerminal(ebrTrmFound);
	}

}


