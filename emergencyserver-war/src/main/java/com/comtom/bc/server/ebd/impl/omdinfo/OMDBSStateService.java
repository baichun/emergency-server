package com.comtom.bc.server.ebd.impl.omdinfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.comtom.bc.exchange.commom.SyncFlag;
import com.comtom.bc.server.repository.dao.EbrBsDAO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comtom.bc.exchange.commom.EBDType;
import com.comtom.bc.exchange.commom.ODMRptType;
import com.comtom.bc.exchange.commom.RSStateEnum;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.commom.EBRBS;
import com.comtom.bc.exchange.model.ebd.commom.Params;
import com.comtom.bc.exchange.model.ebd.details.other.OMDRequest;
import com.comtom.bc.exchange.model.ebd.details.state.EBRBSState;
import com.comtom.bc.exchange.util.DateStyle;
import com.comtom.bc.exchange.util.DateTimeUtil;
import com.comtom.bc.exchange.util.EBDModelBuild;
import com.comtom.bc.server.ebd.service.AbstractOMDInfoService;
import com.comtom.bc.server.repository.entity.EbrBroadcast;
import com.comtom.bc.server.service.EbrBsService;

/**
 * @author nobody
 * 播出系统状态处理
 */
@Service
public class OMDBSStateService extends AbstractOMDInfoService{

	@Autowired
	private EbrBsService broadcastService;

	@Autowired
	private EbrBsDAO ebrBsDAO;
	
	@Override
	public String OMDType() {
		return EBDType.EBRBSState.name();
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

		EBRBSState broadcastState = new EBRBSState();
		List<EbrBroadcast> broadcastFound = broadcastService.broadcastStatus(incrementalRpt, rptStartTime, rptEndTime);
		List<EBRBS> ebrBsList = new ArrayList<EBRBS>();
		if(null != broadcastFound) {
			for(EbrBroadcast fnd : broadcastFound) {
				EBRBS bs = new EBRBS();
				bs.setRptTime(DateTimeUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS.getValue()));
				bs.setEBRID(fnd.getBsEbrId());
				bs.setStateCode(fnd.getBsState());
				bs.setStateDesc(RSStateEnum.getNameByCode(fnd.getBsState()));
                ebrBsList.add(bs);
			}
		}
		broadcastState.setDataList(ebrBsList);
		
		String eBRID=getEbrPlatFormID();
		String srcURL=getPlatFormUrl();
		String ebdIndex=generateEbdIndex();
		EBD ebdResponse=EBDModelBuild.buildBSState(eBRID, srcURL, ebdIndex, broadcastState,relatedEbdId);
		for (EbrBroadcast ebrBroadcast : broadcastFound) {
			ebrBroadcast.setStatusSyncFlag(SyncFlag.sync.getValue());
			ebrBsDAO.save(ebrBroadcast);
		}
		return ebdResponse;
	}

}
