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
import com.comtom.bc.exchange.model.ebd.commom.EBRAS;
import com.comtom.bc.exchange.model.ebd.commom.RelatedEBRPS;
import com.comtom.bc.exchange.model.ebd.commom.RelatedEBRST;
import com.comtom.bc.exchange.model.ebd.details.info.EBRASInfo;
import com.comtom.bc.exchange.util.DateStyle;
import com.comtom.bc.exchange.util.DateTimeUtil;
import com.comtom.bc.exchange.util.EBDModelBuild;
import com.comtom.bc.server.repository.entity.EbrAdaptor;
import com.comtom.bc.server.service.EbrAdaptorService;

/**
 * 
 * 接收设备信息上报
 * @author kidsoul
 */
//@Component
//@Configurable
//@EnableScheduling
public class ASInfoReport extends AbstractReport{ 
  
	@Autowired
	private EbrAdaptorService adaptorService;
	
	/**
	 * 日志记录器
	 */
	private static final Logger logger = LoggerFactory.getLogger(ASInfoReport.class);
	
	/**
	 * 
	 */
	@Scheduled(cron = "${task.ebr-as.cron}")
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
		
		//查询未同步的数据
		List<EbrAdaptor> adaptorFound = adaptorService.listAdaptor(true,null, null);
		if(CollectionUtils.isEmpty(adaptorFound)){
			return ;
		}
		List<EBRAS> ebrAsList = new ArrayList<EBRAS>();
		if(null != adaptorFound) {
			for(EbrAdaptor fnd : adaptorFound) {
				EBRAS as = new EBRAS();
				as.setRptTime(DateTimeUtil.dateToString(fnd.getUpdateTime(),  DateStyle.YYYY_MM_DD_HH_MM_SS));
				as.setRptType(EBRInfoType.Sync.name());  
				as.setEBRID(fnd.getAsEbrId());
				as.setEBRName(fnd.getAsEbrName());
				as.setLongitude(fnd.getLongitude());
				as.setLatitude(fnd.getLatitude());
				as.setURL(fnd.getAsUrl());
				String relatedPsId=fnd.getRelatedPsEbrId();
				if(StringUtils.isNotEmpty(relatedPsId)){
					RelatedEBRPS relatedEBRPS=new RelatedEBRPS();
					relatedEBRPS.setEBRID(relatedPsId);
					as.setRelatedEBRPS(relatedEBRPS);					
				}
				String relatedStId=fnd.getRelatedRsEbrId();
				if(StringUtils.isNotEmpty(relatedStId)){
					RelatedEBRST relatedEBRST=new RelatedEBRST();
					relatedEBRST.setEBRID(relatedStId);
					as.setRelatedEBRST(relatedEBRST);
				}				
				ebrAsList.add(as);
			}
		}
		EBRASInfo adaptorInfo = new EBRASInfo();
		adaptorInfo.setDataList(ebrAsList);
		EBD ebd=EBDModelBuild.buildEBRASInfo(ebrId, srcURL, ebdIndex, adaptorInfo,null);
		EBD response=sendEbd(ebd);
		boolean success=vilidataResult(response);
		if(!success){
			StringBuffer errMsgBuf = new StringBuffer();
			errMsgBuf.append("上报消息接收设备信息错误, ASInfoReport : ");
			errMsgBuf.append(null == ebd ? "ebd为空" : "ebd.id=" + ebd.getEBDID());
			errMsgBuf.append(null == response ? ", ebd response为空" : ", ebd.response.id=" + response.getEBDID());
			logger.error(errMsgBuf.toString());
			return ;
		}
		
		//更新同步状态
		for (EbrAdaptor ebrAdaptor : adaptorFound) {
			ebrAdaptor.setSyncFlag(SyncFlag.sync.getValue());
			adaptorService.saveOrUpdate(ebrAdaptor);
		}		
		
	}

}


