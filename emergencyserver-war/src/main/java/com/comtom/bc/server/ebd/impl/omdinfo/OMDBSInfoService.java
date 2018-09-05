package com.comtom.bc.server.ebd.impl.omdinfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comtom.bc.exchange.commom.EBDType;
import com.comtom.bc.exchange.commom.EBRInfoType;
import com.comtom.bc.exchange.commom.ODMRptType;
import com.comtom.bc.exchange.commom.SyncFlag;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.commom.Coverage;
import com.comtom.bc.exchange.model.ebd.commom.EBRBS;
import com.comtom.bc.exchange.model.ebd.commom.Params;
import com.comtom.bc.exchange.model.ebd.commom.RadioParams;
import com.comtom.bc.exchange.model.ebd.commom.RelatedEBRAS;
import com.comtom.bc.exchange.model.ebd.commom.RelatedEBRPS;
import com.comtom.bc.exchange.model.ebd.commom.RelatedEBRST;
import com.comtom.bc.exchange.model.ebd.commom.Schedule;
import com.comtom.bc.exchange.model.ebd.commom.Switch;
import com.comtom.bc.exchange.model.ebd.commom.TVParams;
import com.comtom.bc.exchange.model.ebd.details.info.EBRBSInfo;
import com.comtom.bc.exchange.model.ebd.details.other.OMDRequest;
import com.comtom.bc.exchange.util.DateStyle;
import com.comtom.bc.exchange.util.DateTimeUtil;
import com.comtom.bc.exchange.util.EBDModelBuild;
import com.comtom.bc.server.ebd.service.AbstractOMDInfoService;
import com.comtom.bc.server.repository.dao.EbrBsDAO;
import com.comtom.bc.server.repository.entity.EbrBroadcast;
import com.comtom.bc.server.repository.entity.Worktime;
import com.comtom.bc.server.service.EbrBsService;

/**
 * @author nobody
 * 播出系统信息处理
 */
@Service
public class OMDBSInfoService extends AbstractOMDInfoService{
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private EbrBsService broadcastService;
	
	@Autowired
	private EbrBsDAO ebrBsDAO;
	
	@Override
	public String OMDType() {
		return EBDType.EBRBSInfo.name();
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

		EBRBSInfo ebrbsInfo=new EBRBSInfo();
		ebrbsInfo.setParams(params);
		List<EBRBS> dataList=new ArrayList<EBRBS>();
		
		List<EbrBroadcast> broadcastFound = broadcastService.listBroadcast(incremental, rptStartTime, rptEndTime);
		
		if(null != broadcastFound) {
			for(EbrBroadcast fnd : broadcastFound) {
				EBRBS bs = new EBRBS();
				bs.setRptTime(DateTimeUtil.dateToString(fnd.getUpdateTime(), DateStyle.YYYY_MM_DD_HH_MM_SS.getValue()));
				bs.setRptType(EBRInfoType.Sync.name());
				
				String relatedPsId=fnd.getRelatedPsEbrId();
				String relatedStId=fnd.getRelatedRsEbrId();
				String relatedAsId=fnd.getRelatedAsEbrId();
				
				if(StringUtils.isNotEmpty(relatedPsId)){
					RelatedEBRPS ebrPS = new RelatedEBRPS();
					ebrPS.setEBRID(relatedPsId);
					bs.setRelatedEBRPS(ebrPS);
				}
				
				if(StringUtils.isNotEmpty(relatedStId)){
					RelatedEBRST ebrST = new RelatedEBRST();
					ebrST.setEBRID(relatedStId);
					bs.setRelatedEBRST(ebrST);
				}
				
				if(StringUtils.isNotEmpty(relatedAsId)){
					RelatedEBRAS ebrAS = new RelatedEBRAS();
					ebrAS.setEBRID(relatedAsId);
					bs.setRelatedEBRAS(ebrAS);
				}
				
				bs.setEBRID(fnd.getBsEbrId());
				
				bs.setEBRName(fnd.getBsName());
				bs.setURL(fnd.getBsUrl());
				bs.setLongitude(fnd.getLongitude());
				bs.setLatitude(fnd.getLatitude());
				bs.setSquare(String.valueOf(fnd.getSquare()));
//				Coverage cvg = new Coverage();
//				cvg.setAreaCode(fnd.getAreaCode());
				bs.setAreaCode(fnd.getAreaCode());
				if(fnd.getPopulation()!=null){
					bs.setPopulation(fnd.getPopulation().doubleValue());					
				}
				bs.setLanguageCode(fnd.getLanguageCode());
				bs.setEquipRoom(fnd.getEquipRoom());
				
                RadioParams rdPrms = new RadioParams();
                rdPrms.setChannelName(fnd.getRadioChannelName());
                rdPrms.setFreq(fnd.getRadioFreq() == null ? null : fnd.getRadioFreq().intValue());
                rdPrms.setPower(fnd.getRadioPower()); 
                rdPrms.setBackup(fnd.getBackup());
                rdPrms.setAutoSwitch(fnd.getAutoSwitch());
                rdPrms.setRemoteControl(fnd.getRemoteControl());
                rdPrms.setExperiment(fnd.getExperiment());
                if (StringUtils.isNotEmpty(rdPrms.getChannelName()) && rdPrms.getFreq()!=null && rdPrms.getPower()!=null && rdPrms.getBackup()!=null &&  rdPrms.getExperiment()!=null && rdPrms.getAutoSwitch()!=null && rdPrms.getRemoteControl()!=null){
                    bs.setRadioParams(rdPrms);
                }else{
                	logger.info("播出系统信息 RadioParams 模拟广播附加参数不完整...");
                }
                
                TVParams tvPrms = new TVParams();
                tvPrms.setChannelName(fnd.getTvChannelName());
                tvPrms.setFreq(fnd.getTvFreq());
                tvPrms.setChannelNum(fnd.getTvChannelNum());
                tvPrms.setProgramNum(fnd.getProgramNum());
                if(StringUtils.isNotEmpty(tvPrms.getChannelName()) && tvPrms.getFreq()!=null && StringUtils.isNotEmpty(tvPrms.getChannelNum()) &&  StringUtils.isNotEmpty(tvPrms.getProgramNum())){
                    bs.settVParams(tvPrms);
                }
                
                Schedule schedule=new Schedule();
                List<Switch> scheduleList=new ArrayList<Switch>();
                if(null != fnd.getWorkTimeSwitch()) {
                	for(Worktime wt : fnd.getWorkTimeSwitch()) {
            			Switch sw = new Switch();
            			sw.setWeekday(wt.getWeekDay());
            			sw.setStartTime(wt.getStartTime());
            			sw.setEndTime(wt.getStopTime());
            			scheduleList.add(sw);
                	}
                	if(scheduleList != null && !scheduleList.isEmpty()){
                    	schedule.setDataList(scheduleList);
                    	bs.setSchedule(schedule);
                	}
                }
                dataList.add(bs);
			}
		}
		ebrbsInfo.setDataList(dataList);
		
		String eBRID=getEbrPlatFormID();
		String srcURL=getPlatFormUrl();
		String ebdIndex=generateEbdIndex();
		EBD ebdResponse=EBDModelBuild.buildEBRBSInfo(eBRID, srcURL, ebdIndex, ebrbsInfo,relatedEbdId);
		
		for (EbrBroadcast ebrBroadcast : broadcastFound) {
			ebrBroadcast.setSyncFlag(SyncFlag.sync.getValue());
			ebrBsDAO.save(ebrBroadcast);
		}
		
		return ebdResponse;
	}

}
