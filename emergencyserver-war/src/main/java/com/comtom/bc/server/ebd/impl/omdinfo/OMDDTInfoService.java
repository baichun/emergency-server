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
import com.comtom.bc.exchange.model.ebd.commom.EBRDT;
import com.comtom.bc.exchange.model.ebd.commom.Params;
import com.comtom.bc.exchange.model.ebd.commom.RelatedEBRPS;
import com.comtom.bc.exchange.model.ebd.details.info.EBRDTInfo;
import com.comtom.bc.exchange.model.ebd.details.other.OMDRequest;
import com.comtom.bc.exchange.util.DateStyle;
import com.comtom.bc.exchange.util.DateTimeUtil;
import com.comtom.bc.exchange.util.EBDModelBuild;
import com.comtom.bc.server.ebd.service.AbstractOMDInfoService;
import com.comtom.bc.server.repository.entity.EbrTerminal;
import com.comtom.bc.server.service.EbrTerminalService;

/**
 * @author nobody
 * 设备及终端信息处理
 */
@Service
public class OMDDTInfoService extends AbstractOMDInfoService{
	@Autowired
	private EbrTerminalService terminalService;
	
	@Override
	public String OMDType() {
		return EBDType.EBRDTInfo.name();
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
		
		EBRDTInfo devTrmInfo = new EBRDTInfo();
		devTrmInfo.setParams(params);
		
		List<EbrTerminal> ebrTrmFound = terminalService.listTerminal(incremental, rptStartTime, rptEndTime);
		List<EBRDT> devTrmList = new ArrayList<EBRDT>();
		if(null != ebrTrmFound) {
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
		}
		
		devTrmInfo.setDataList(devTrmList);

		String eBRID=getEbrPlatFormID();
		String srcURL=getPlatFormUrl();
		String ebdIndex=generateEbdIndex();
		EBD ebdResponse=EBDModelBuild.buildEBRDTInfo(eBRID, srcURL, ebdIndex, devTrmInfo,relatedEbdId);
		
		for (EbrTerminal object : ebrTrmFound) {
			object.setSyncFlag(SyncFlag.sync.getValue());
			terminalService.saveOrUpdate(object);
		}
		return ebdResponse;
	}

}
