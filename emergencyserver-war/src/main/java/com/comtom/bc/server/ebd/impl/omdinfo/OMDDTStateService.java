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
import com.comtom.bc.exchange.model.ebd.commom.EBRDT;
import com.comtom.bc.exchange.model.ebd.commom.Params;
import com.comtom.bc.exchange.model.ebd.details.other.OMDRequest;
import com.comtom.bc.exchange.model.ebd.details.state.EBRDTState;
import com.comtom.bc.exchange.util.DateStyle;
import com.comtom.bc.exchange.util.DateTimeUtil;
import com.comtom.bc.exchange.util.EBDModelBuild;
import com.comtom.bc.server.ebd.service.AbstractOMDInfoService;
import com.comtom.bc.server.repository.entity.EbrTerminal;
import com.comtom.bc.server.service.EbrTerminalService;

/**
 * @author nobody
 * 设备及终端的状态处理
 */
@Service
public class OMDDTStateService extends AbstractOMDInfoService{
	
	@Autowired
	private EbrTerminalService terminalService;
	
	@Override
	public String OMDType() {
		return EBDType.EBRDTState.name();
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
				//查询状态不区分增量全量
				incremental=true;
			}
			if(StringUtils.isNotEmpty(rptEndTimeStr)){
				rptStartTime=DateTimeUtil.stringToDate(rptStartTimeStr, DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
			}
			if(StringUtils.isNotEmpty(rptEndTimeStr)){
				rptEndTime=DateTimeUtil.stringToDate(rptEndTimeStr, DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
			}			
		}

		EBRDTState devTrmState = new EBRDTState();
		List<EbrTerminal> ebrTrmFound = terminalService.listTerminalStatus(incremental, rptStartTime, rptEndTime);
		List<EBRDT> devTrmList = new ArrayList<EBRDT>();
		if(null != ebrTrmFound) {
			for(EbrTerminal fnd : ebrTrmFound) {
				EBRDT devTrm = new EBRDT();
				devTrm.setRptTime(DateTimeUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS.getValue()));
				devTrm.setEBRID(fnd.getTerminalEbrId());
				devTrm.setStateCode(fnd.getTerminalState());
				devTrm.setStateDesc(RSStateEnum.getNameByCode(fnd.getTerminalState()));
				devTrmList.add(devTrm);
			}
		}
		devTrmState.setDataList(devTrmList);
		
		String eBRID=getEbrPlatFormID();
		String srcURL=getPlatFormUrl();
		String ebdIndex=generateEbdIndex();
		EBD ebdResponse=EBDModelBuild.buildDTState(eBRID, srcURL, ebdIndex, devTrmState,relatedEbdId);
		return ebdResponse;
	}

}
