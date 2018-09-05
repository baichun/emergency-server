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
import com.comtom.bc.exchange.model.ebd.commom.EBRPS;
import com.comtom.bc.exchange.model.ebd.commom.Params;
import com.comtom.bc.exchange.model.ebd.commom.RelatedEBRPS;
import com.comtom.bc.exchange.model.ebd.details.info.EBRPSInfo;
import com.comtom.bc.exchange.model.ebd.details.other.OMDRequest;
import com.comtom.bc.exchange.util.DateStyle;
import com.comtom.bc.exchange.util.DateTimeUtil;
import com.comtom.bc.exchange.util.EBDModelBuild;
import com.comtom.bc.server.ebd.service.AbstractOMDInfoService;
import com.comtom.bc.server.repository.entity.EbrPlatform;
import com.comtom.bc.server.service.EbrPlatformService;

/**
 * @author nobody
 * 应急广播平台信息处理
 */
@Service
public class OMDPSInfoService extends AbstractOMDInfoService{

	@Autowired
	private EbrPlatformService platformService;
	
	@Override
	public String OMDType() {
		return EBDType.EBRPSInfo.name();
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
			if(StringUtils.isNotEmpty(rptType)&&(ODMRptType.Incremental.name().equals(rptType))){
				incremental=true;
			}
			if(StringUtils.isNotEmpty(rptStartTimeStr)){
				rptStartTime=DateTimeUtil.stringToDate(rptStartTimeStr, DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
			}
			if(StringUtils.isNotEmpty(rptEndTimeStr)){
				rptEndTime=DateTimeUtil.stringToDate(rptEndTimeStr, DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
			}			
		}
		List<EbrPlatform> platformFound = platformService.listPlatform(incremental, rptStartTime, rptEndTime);
		List<EBRPS> ebrPsList = new ArrayList<EBRPS>();
		if(null != platformFound) {
			for(EbrPlatform fnd : platformFound) {
				EBRPS ps = new EBRPS();
				ps.setRptTime(DateTimeUtil.dateToString(fnd.getUpdateTime(), DateStyle.YYYY_MM_DD_HH_MM_SS));
				ps.setRptType(EBRInfoType.Sync.name());
				ps.setEBRID(fnd.getPsEbrId());
				ps.setEBRName(fnd.getPsEbrName());
				ps.setAddress(fnd.getPsAddress());
				ps.setContact(fnd.getContact());
				ps.setPhoneNumber(fnd.getPhoneNumber());
				ps.setLongitude(fnd.getLongitude());
				ps.setLatitude(fnd.getLatitude());
				ps.setURL(fnd.getPsUrl());
				
				String parentPsEbrId=fnd.getParentPsEbrId();
				if(StringUtils.isNotBlank(parentPsEbrId)){
					RelatedEBRPS relatedPs = new RelatedEBRPS();
					relatedPs.setEBRID(parentPsEbrId);
					ps.setRelatedEBRPS(relatedPs);
				}
				ebrPsList.add(ps);
			}
		}	
		
		EBRPSInfo ebrpsInfo=new EBRPSInfo();
		ebrpsInfo.setParams(params);
		ebrpsInfo.setDataList(ebrPsList);

		String eBRID=getEbrPlatFormID();
		String srcURL=getPlatFormUrl();
		String ebdIndex=generateEbdIndex();
		EBD ebdResponse=EBDModelBuild.buildEBRPSInfo(eBRID, srcURL, ebdIndex, ebrpsInfo,relatedEbdId);
		
		for (EbrPlatform object : platformFound) {
			object.setSyncFlag(SyncFlag.sync.getValue());
			platformService.saveOrUpdate(object);
		}
		
		return ebdResponse;
	}

}
