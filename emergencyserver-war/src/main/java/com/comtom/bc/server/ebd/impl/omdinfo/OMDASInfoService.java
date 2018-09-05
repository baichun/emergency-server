package com.comtom.bc.server.ebd.impl.omdinfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comtom.bc.exchange.commom.EBDType;
import com.comtom.bc.exchange.commom.EBRInfoType;
import com.comtom.bc.exchange.commom.ODMRptType;
import com.comtom.bc.exchange.commom.SyncFlag;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.commom.EBRAS;
import com.comtom.bc.exchange.model.ebd.commom.Params;
import com.comtom.bc.exchange.model.ebd.commom.RelatedEBRPS;
import com.comtom.bc.exchange.model.ebd.commom.RelatedEBRST;
import com.comtom.bc.exchange.model.ebd.details.info.EBRASInfo;
import com.comtom.bc.exchange.model.ebd.details.other.OMDRequest;
import com.comtom.bc.exchange.util.DateStyle;
import com.comtom.bc.exchange.util.DateTimeUtil;
import com.comtom.bc.exchange.util.EBDModelBuild;
import com.comtom.bc.server.ebd.service.AbstractOMDInfoService;
import com.comtom.bc.server.repository.entity.EbrAdaptor;
import com.comtom.bc.server.service.EbrAdaptorService;

/**
 * @author nobody
 * 消息接收设备信息处理
 */
@Service
public class OMDASInfoService extends AbstractOMDInfoService{

	@Autowired
	private EbrAdaptorService adaptorService;
	
	@Override
	public String OMDType() {
		return EBDType.EBRASInfo.name();
	}
	
	@Override
	public EBD service(String relatedEbdId,OMDRequest odmRequest){
		boolean incrementalRpt=false;
		Date rptStartTime=null;
		Date rptEndTime=null;
		Params params=odmRequest.getParams();
		if(params!=null){
			//是否增量
			String rptType=params.getRptType();
			String rptStartTimeStr=params.getRptStartTime();
			String rptEndTimeStr=params.getRptEndTime();
			if(StringUtils.isEmpty(rptType)&&(ODMRptType.Incremental.name().equals(rptType))){
				incrementalRpt=true;
			}
			if(StringUtils.isEmpty(rptEndTimeStr)){
				rptStartTime=DateTimeUtil.stringToDate(rptStartTimeStr, DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
			}
			if(StringUtils.isEmpty(rptEndTimeStr)){
				rptEndTime=DateTimeUtil.stringToDate(rptEndTimeStr, DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
			}			
		}

		EBRASInfo adaptorInfo = new EBRASInfo();
		adaptorInfo.setParams(params);
		List<EbrAdaptor> adaptorFound = adaptorService.listAdaptor(incrementalRpt, rptStartTime, rptEndTime);
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
		adaptorInfo.setDataList(ebrAsList);
		String eBRID=getEbrPlatFormID();
		String srcURL=getPlatFormUrl();
		String ebdIndex=generateEbdIndex();
		EBD ebdResponse=EBDModelBuild.buildEBRASInfo(eBRID, srcURL, ebdIndex, adaptorInfo,relatedEbdId);
		
		for (EbrAdaptor ebrAdaptor : adaptorFound) {
			ebrAdaptor.setSyncFlag(SyncFlag.sync.getValue());
			adaptorService.saveOrUpdate(ebrAdaptor);
		}
		
		return ebdResponse;
	}

}
