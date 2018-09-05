package com.comtom.bc.server.ebd.impl.omdinfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.comtom.bc.exchange.commom.SyncFlag;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comtom.bc.exchange.commom.EBDType;
import com.comtom.bc.exchange.commom.ODMRptType;
import com.comtom.bc.exchange.commom.RSStateEnum;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.commom.EBRPS;
import com.comtom.bc.exchange.model.ebd.commom.Params;
import com.comtom.bc.exchange.model.ebd.details.other.OMDRequest;
import com.comtom.bc.exchange.model.ebd.details.state.EBRPSState;
import com.comtom.bc.exchange.util.DateStyle;
import com.comtom.bc.exchange.util.DateTimeUtil;
import com.comtom.bc.exchange.util.EBDModelBuild;
import com.comtom.bc.server.ebd.service.AbstractOMDInfoService;
import com.comtom.bc.server.repository.entity.EbrPlatform;
import com.comtom.bc.server.service.EbrPlatformService;

/**
 * @author nobody
 * 应急广播平台状态处理
 */
@Service
public class OMDPSStateService extends AbstractOMDInfoService{
	
	@Autowired
	private EbrPlatformService platformService;
	
	@Override
	public String OMDType() {
		return EBDType.EBRPSState.name();
	}
	
	@Override
	public EBD service(String relatedEbdId,OMDRequest odmRequest){
		boolean incrementalRpt=false;
		Date rptStartTime=null;
		Date rptEndTime=null;
		Params params=odmRequest.getParams();
		if(params!=null){
			String rptType=params.getRptType();
			String rptStartTimeStr=params.getRptStartTime();
			String rptEndTimeStr=params.getRptEndTime();
			if(StringUtils.isNotEmpty(rptType)&&(ODMRptType.Incremental.name().equals(rptType))){
				//查询状态不区分增量全量
				incrementalRpt=true;
			}
			if(StringUtils.isNotEmpty(rptEndTimeStr)){
				rptStartTime=DateTimeUtil.stringToDate(rptStartTimeStr, DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
			}
			if(StringUtils.isNotEmpty(rptEndTimeStr)){
				rptEndTime=DateTimeUtil.stringToDate(rptEndTimeStr, DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
			}			
		}

		
		EBRPSState eBRPSState=new EBRPSState();
		List<EBRPS> ebrPsList = new ArrayList<EBRPS>();
		eBRPSState.setDataList(ebrPsList);
		List<EbrPlatform> platformFound = platformService.statusSyncPlatform(incrementalRpt, rptStartTime, rptEndTime);
		if(null != platformFound) {
			for(EbrPlatform fnd : platformFound) {
				EBRPS ps = new EBRPS();
				ps.setRptTime(DateTimeUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS.getValue()));
				ps.setEBRID(fnd.getPsEbrId());
                ps.setStateCode(fnd.getPsState());
				ps.setStateDesc(RSStateEnum.getNameByCode(fnd.getPsState()));
				ebrPsList.add(ps);
			}
		}
		
		String eBRID=getEbrPlatFormID();
		String srcURL=getPlatFormUrl();
		String ebdIndex=generateEbdIndex();
		EBD ebdResponse=EBDModelBuild.buildPSState(eBRID, srcURL, ebdIndex, eBRPSState,relatedEbdId);
		for (EbrPlatform object : platformFound) {
			object.setStatusSyncFlag(SyncFlag.sync.getValue());
			platformService.saveOrUpdate(object);
		}
		return ebdResponse;
	}

}
