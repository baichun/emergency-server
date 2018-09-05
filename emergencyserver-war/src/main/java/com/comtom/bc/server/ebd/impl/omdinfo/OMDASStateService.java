package com.comtom.bc.server.ebd.impl.omdinfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comtom.bc.exchange.commom.EBDType;
import com.comtom.bc.exchange.commom.ODMRptType;
import com.comtom.bc.exchange.commom.RSStateEnum;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.commom.EBRAS;
import com.comtom.bc.exchange.model.ebd.commom.Params;
import com.comtom.bc.exchange.model.ebd.details.other.OMDRequest;
import com.comtom.bc.exchange.model.ebd.details.state.EBRASState;
import com.comtom.bc.exchange.util.DateStyle;
import com.comtom.bc.exchange.util.DateTimeUtil;
import com.comtom.bc.exchange.util.EBDModelBuild;
import com.comtom.bc.server.ebd.service.AbstractOMDInfoService;
import com.comtom.bc.server.repository.entity.EbrAdaptor;
import com.comtom.bc.server.service.EbrAdaptorService;

/**
 * @author nobody
 * 消息接收设备状态处理
 */
@Service
public class OMDASStateService extends AbstractOMDInfoService{
	
	@Autowired
	private EbrAdaptorService adaptorService;
	
	@Override
	public String OMDType() {
		return EBDType.EBRASState.name();
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
			if(StringUtils.isEmpty(rptType)&&(ODMRptType.Incremental.name().equals(rptType))){
				//查询状态不区分增量全量
//				incrementalRpt=true;
			}
			if(StringUtils.isEmpty(rptEndTimeStr)){
				rptStartTime=DateTimeUtil.stringToDate(rptStartTimeStr, DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
			}
			if(StringUtils.isEmpty(rptEndTimeStr)){
				rptEndTime=DateTimeUtil.stringToDate(rptEndTimeStr, DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
			}			
		}

		EBRASState adaptorState = new EBRASState();
		List<EbrAdaptor> adaptorFound = adaptorService.listAdaptor(incrementalRpt, rptStartTime, rptEndTime);
		List<EBRAS> ebrAsList = new ArrayList<EBRAS>();
		if(null != adaptorFound) {
			for(EbrAdaptor fnd : adaptorFound) {
				EBRAS as = new EBRAS();
                as.setRptTime(DateTimeUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS.getValue()));
                as.setEBRID(fnd.getAsEbrId());
                as.setStateCode(fnd.getAsState());
                as.setStateDesc(RSStateEnum.getNameByCode(fnd.getAsState()));
				ebrAsList.add(as);
			}
		}
		adaptorState.setDataList(ebrAsList);
		
		String eBRID=getEbrPlatFormID();
		String srcURL=getPlatFormUrl();
		String ebdIndex=generateEbdIndex();
		EBD ebdResponse=EBDModelBuild.buildASState(eBRID, srcURL, ebdIndex, adaptorState,relatedEbdId);
		return ebdResponse;
	}

}
