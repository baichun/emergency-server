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
import com.comtom.bc.exchange.model.ebd.commom.EBRST;
import com.comtom.bc.exchange.model.ebd.commom.Params;
import com.comtom.bc.exchange.model.ebd.commom.RelatedEBRPS;
import com.comtom.bc.exchange.model.ebd.details.info.EBRSTInfo;
import com.comtom.bc.exchange.model.ebd.details.other.OMDRequest;
import com.comtom.bc.exchange.util.DateStyle;
import com.comtom.bc.exchange.util.DateTimeUtil;
import com.comtom.bc.exchange.util.EBDModelBuild;
import com.comtom.bc.server.ebd.service.AbstractOMDInfoService;
import com.comtom.bc.server.repository.entity.EbrStation;
import com.comtom.bc.server.service.EbrStationService;

/**
 * @author nobody
 * 台站信息处理
 */
@Service
public class OMDSTInfoService extends AbstractOMDInfoService{
	@Autowired
	private EbrStationService stationService;

	@Override
	public String OMDType() {
		return EBDType.EBRSTInfo.name();
	}
	
	@Override
	public EBD service(String relatedEbdId,OMDRequest odmRequest){
		boolean incremental=false;
		Date rptStartTime=null;
		Date rptEndTime=null;
		Params params=odmRequest.getParams();
		if(params!=null){
			String rptType=params.getRptType();
			String rptStartTimeStr=params.getRptStartTime();
			String rptEndTimeStr=params.getRptEndTime();
			if(StringUtils.isEmpty(rptType)&&(ODMRptType.Incremental.name().equals(rptType))){
				incremental=true;
			}
			if(StringUtils.isEmpty(rptEndTimeStr)){
				rptStartTime=DateTimeUtil.stringToDate(rptStartTimeStr, DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
			}
			if(StringUtils.isEmpty(rptEndTimeStr)){
				rptEndTime=DateTimeUtil.stringToDate(rptEndTimeStr, DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
			}			
		}
		
		EBRSTInfo stationInfo = new EBRSTInfo();
		stationInfo.setParams(params);
		
		
		List<EbrStation> stationFound = stationService.listStation(incremental, rptStartTime, rptEndTime);
		List<EBRST> ebrStList = new ArrayList<EBRST>();
		if(null != stationFound) {
			for(EbrStation fnd : stationFound) {
				EBRST st = new EBRST();
				st.setRptTime(DateTimeUtil.dateToString(fnd.getUpdateTime(), DateStyle.YYYY_MM_DD_HH_MM_SS));
				st.setRptType(EBRInfoType.Sync.name());
				st.setEBRID(fnd.getStationEbrId());
				st.setEBRName(fnd.getStationName());
				st.setAddress(fnd.getStationAddress());
				st.setContact(fnd.getContact());
				st.setPhoneNumber(fnd.getPhoneNumber());
				st.setLongitude(fnd.getLongitude());
				st.setLatitude(fnd.getLatitude());
				
				String relatedPsId=fnd.getRelatedPsEbrId();
				if(StringUtils.isNotEmpty(relatedPsId)){
					RelatedEBRPS relatedEBRPS=new RelatedEBRPS();
					relatedEBRPS.setEBRID(relatedPsId);
					st.setRelatedEBRPS(relatedEBRPS);					
				}
				ebrStList.add(st);
			}
		}
		stationInfo.setDataList(ebrStList);
		
		String eBRID=getEbrPlatFormID();
		String srcURL=getPlatFormUrl();
		String ebdIndex=generateEbdIndex();
		EBD ebdResponse=EBDModelBuild.buildEBRSTInfo(eBRID, srcURL, ebdIndex, stationInfo,relatedEbdId);
		
		for (EbrStation object : stationFound) {
			object.setSyncFlag(SyncFlag.sync.getValue());
			stationService.saveOrUpdate(object);
		}
		
		return ebdResponse;
	}

}
