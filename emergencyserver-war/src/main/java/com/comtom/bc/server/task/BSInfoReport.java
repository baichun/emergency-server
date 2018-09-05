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
import com.comtom.bc.exchange.model.ebd.commom.Coverage;
import com.comtom.bc.exchange.model.ebd.commom.EBRBS;
import com.comtom.bc.exchange.model.ebd.commom.RadioParams;
import com.comtom.bc.exchange.model.ebd.commom.RelatedEBRAS;
import com.comtom.bc.exchange.model.ebd.commom.RelatedEBRPS;
import com.comtom.bc.exchange.model.ebd.commom.RelatedEBRST;
import com.comtom.bc.exchange.model.ebd.commom.Schedule;
import com.comtom.bc.exchange.model.ebd.commom.Switch;
import com.comtom.bc.exchange.model.ebd.commom.TVParams;
import com.comtom.bc.exchange.model.ebd.details.info.EBRBSInfo;
import com.comtom.bc.exchange.util.DateStyle;
import com.comtom.bc.exchange.util.DateTimeUtil;
import com.comtom.bc.exchange.util.EBDModelBuild;
import com.comtom.bc.server.repository.dao.EbrBsDAO;
import com.comtom.bc.server.repository.entity.EbrBroadcast;
import com.comtom.bc.server.repository.entity.Worktime;
import com.comtom.bc.server.service.EbrBsService;

/**
 * 
 * 播出系统信息上报
 * @author kidsoul
 */
//@Component
//@Configurable
//@EnableScheduling
public class BSInfoReport extends AbstractReport{ 
  
	@Autowired
	private EbrBsService broadcastService;
	
	@Autowired
	private EbrBsDAO ebrBsDAO;
	
	/**
	 * 日志记录器
	 */
	private static final Logger logger = LoggerFactory.getLogger(BSInfoReport.class);
	
	@Scheduled(cron = "${task.ebr-bs.cron}")
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
		List<EbrBroadcast> broadcastFound = broadcastService.listBroadcast(true, null, null);
		if(CollectionUtils.isEmpty(broadcastFound)){
			return ;
		}
		List<EBRBS> dataList=new ArrayList<EBRBS>();
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
			if(fnd.getSquare()!=null){
				bs.setSquare(String.valueOf(fnd.getSquare()));
			}
//			Coverage cvg = new Coverage();
//			cvg.setAreaCode(fnd.getAreaCode());
			bs.setAreaCode(fnd.getAreaCode());
			if(fnd.getPopulation()!=null){
				bs.setPopulation(fnd.getPopulation().doubleValue());					
			}
			bs.setLanguageCode(fnd.getLanguageCode());
			bs.setEquipRoom(fnd.getEquipRoom());
			
            RadioParams rdPrms = new RadioParams();
            rdPrms.setChannelName(fnd.getRadioChannelName());
            if(fnd.getRadioFreq()!=null){
            	rdPrms.setFreq(fnd.getRadioFreq().intValue());
            }
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
		EBRBSInfo ebrbsInfo=new EBRBSInfo();
		ebrbsInfo.setDataList(dataList);
		EBD ebd=EBDModelBuild.buildEBRBSInfo(ebrId, srcURL, ebdIndex, ebrbsInfo,null);
		EBD response=sendEbd(ebd);
		boolean success=vilidataResult(response);
		if(!success){
			StringBuffer errMsgBuf = new StringBuffer();
			errMsgBuf.append("上报播出系统信息错误, BSInfoReport : ");
			errMsgBuf.append(null == ebd ? "ebd为空" : "ebd.id=" + ebd.getEBDID());
			errMsgBuf.append(null == response ? ", ebd response为空" : ", ebd.response.id=" + response.getEBDID());
			logger.error(errMsgBuf.toString());
			return ;
		}
		
		for (EbrBroadcast ebrBroadcast : broadcastFound) {
			ebrBroadcast.setSyncFlag(SyncFlag.sync.getValue());
			ebrBsDAO.save(ebrBroadcast);
		}
	}
}


