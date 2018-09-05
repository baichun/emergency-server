package com.comtom.bc.server.ebd.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comtom.bc.exchange.commom.EBDType;
import com.comtom.bc.exchange.commom.SyncFlag;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.commom.EBRBS;
import com.comtom.bc.exchange.model.ebd.commom.Switch;
import com.comtom.bc.exchange.model.ebd.details.info.EBRBSInfo;
import com.comtom.bc.exchange.util.DateStyle;
import com.comtom.bc.exchange.util.DateTimeUtil;
import com.comtom.bc.server.ebd.model.ResourceModel;
import com.comtom.bc.server.repository.dao.EbrBsDAO;
import com.comtom.bc.server.repository.dao.EbrWorktimeDAO;
import com.comtom.bc.server.repository.dao.WorktimeDAO;
import com.comtom.bc.server.repository.entity.EbrBroadcast;
import com.comtom.bc.server.repository.entity.EbrWorktime;
import com.comtom.bc.server.repository.entity.Worktime;
import com.comtom.bc.server.service.ResIDGeneratorService;

/**
 * @author nobody
 * 播出系统信息上报
 */
@Service
public class EBRBSInfoService extends AbstractEMDService{
	@Autowired
	EbrBsDAO ebrBsDAO;
	
	@Autowired
	private EbrWorktimeDAO ebrWorktimeDAO;
	
	@Autowired
	private WorktimeDAO worktimeDAO;
	
    @Autowired
    ResIDGeneratorService residGenService;
	
	@Override
	public String serviceType() {
		return EBDType.EBRBSInfo.name();
	}

	//处理播出系统上报请求，获取播出系统上报数据并保存播出系统到数据库
	@Transactional
	public void service(EBD ebd, List<File> resourceFiles) {
		EBRBSInfo broadcastInfo = ebd.getEBRBSInfo();
		List<EBRBS> ebrBroadcastList = broadcastInfo.getDataList();
		if(null == ebrBroadcastList || ebrBroadcastList.size() < 1) {
			return;
		}
		List<String> bsIds = new ArrayList<String>();
		for(EBRBS ebrbs : ebrBroadcastList) {
			bsIds.add(ebrbs.getEBRID());
		}
		
		List<EbrBroadcast> broadcastsExist = ebrBsDAO.findAll(bsIds);
		List<EbrBroadcast> broadcasts2Save = new ArrayList<EbrBroadcast>();
		if(null == broadcastsExist) {
			broadcastsExist = new ArrayList<EbrBroadcast>();
		}
		for(EBRBS ebrbs : ebrBroadcastList) {
			EbrBroadcast broadcast = getByEbrId(ebrbs.getEBRID(), broadcastsExist);
			broadcasts2Save.add(newEbrBroadcast(broadcast, ebrbs));
		}
		List<EbrBroadcast> saveImg = ebrBsDAO.save(broadcasts2Save);
		//更新资源ID使用记录
		if(null == saveImg) {
			saveImg = new ArrayList<EbrBroadcast>(); 
		}
		List<String> svIds = new ArrayList<String>();
		for(EbrBroadcast svbs : saveImg) {
			svIds.add(svbs.getBsEbrId());
		}
		residGenService.updateResourceIdInfo(svIds.toArray(new String[svIds.size()]));
		
		if(svIds.size() < 1) {
			return;
		}
		
		//保存工作时间表及播出系统运行图
		for(EBRBS ebrbs : ebrBroadcastList) {
			if(null != ebrbs.getSchedule() && null != ebrbs.getSchedule().getDataList()) {
				List<Worktime> worktimes = new ArrayList<Worktime>();
				for(Switch swt : ebrbs.getSchedule().getDataList()) {
					Worktime wt = new Worktime();
					wt.setId(UUID.randomUUID().toString());
					wt.setWeekDay(swt.getWeekday());
					wt.setStartTime(swt.getStartTime());
					wt.setStopTime(swt.getEndTime());
					
					worktimes.add(wt);
				}
				
				saveEbrWorktime(ebrbs.getEBRID(), worktimes);
			}
		}
	}
	
	private void saveEbrWorktime(String bsEbrId, List<Worktime> worktimes) {
		List<EbrWorktime> ebrWorktimes=new ArrayList<EbrWorktime>();
		if(!StringUtils.isEmpty(bsEbrId) && null != worktimes) {
			ebrWorktimeDAO.deleteByEbrId(bsEbrId);
			for (Worktime worktime : worktimes) {
				final String startTime=worktime.getStartTime();
				final String stopTime=worktime.getStopTime();
				final Integer weekDay=worktime.getWeekDay();
				if(StringUtils.isEmpty(stopTime)&&StringUtils.isEmpty(startTime)&&weekDay==null){
					continue ;
				}
				
				Specification<Worktime> spec=new Specification<Worktime>() {
					public Predicate toPredicate(Root<Worktime> root, CriteriaQuery<?> query,
							CriteriaBuilder cb) {
						Predicate predicate = cb.conjunction();
						if(StringUtils.isNotBlank(startTime)){
							predicate.getExpressions().add(cb.equal(root.<String> get("startTime"),startTime));							
						}
						if(StringUtils.isNotBlank(stopTime)){
							predicate.getExpressions().add(cb.equal(root.<String> get("stopTime"),stopTime));
						}
						if(weekDay!=null){
							predicate.getExpressions().add(cb.equal(root.<Integer> get("weekDay"),weekDay));							
						}
						return predicate;
					}
				};
				String workTimeId=null;
				Worktime worktime2=worktimeDAO.findOne(spec);
				if(worktime2==null){
					worktime.setId(UUID.randomUUID().toString());
					workTimeId=worktime.getId();
					worktimeDAO.save(worktime);
				}else{
					workTimeId=worktime2.getId();
				}
				
				EbrWorktime ebrWorktime=new EbrWorktime();
				ebrWorktime.setEbrId(bsEbrId);
				ebrWorktime.setWorktimeId(workTimeId);
				ebrWorktime.setId(UUID.randomUUID().toString());
				ebrWorktimes.add(ebrWorktime);
			}
			ebrWorktimeDAO.save(ebrWorktimes);
		}
	}

	private final EbrBroadcast getByEbrId(String ebrId, List<EbrBroadcast> broadcastsExist) {
		EbrBroadcast found = null;
		for(EbrBroadcast bs : broadcastsExist) {
			if(bs.getBsEbrId().equals(ebrId)) {
				found = bs;
				break;
			}
		}
		
		return found;
	}
	
	private EbrBroadcast newEbrBroadcast(EbrBroadcast based, EBRBS ebrbs) {
		EbrBroadcast broadcast = based;
		if(null == based) {
			broadcast = new EbrBroadcast();
		}
		broadcast.setBsEbrId(ebrbs.getEBRID());
		broadcast.setBsName(ebrbs.getEBRName());
		broadcast.setBsUrl(ebrbs.getURL());
		broadcast.setBsType(ebrbs.getEBRID().substring(2, 4));
		broadcast.setLongitude(ebrbs.getLongitude());
		broadcast.setLatitude(ebrbs.getLatitude());
		if(null != ebrbs.getSquare()){
			broadcast.setSquare(new BigDecimal(ebrbs.getSquare()));
		}
		broadcast.setAreaCode(ebrbs.getAreaCode());
		if(null != ebrbs.getPopulation()) {
			broadcast.setPopulation(new BigDecimal(ebrbs.getPopulation()));
		}
		broadcast.setLanguageCode(ebrbs.getLanguageCode());
		broadcast.setEquipRoom(ebrbs.getEquipRoom());
		if(null != ebrbs.getRadioParams()) {
			broadcast.setRadioChannelName(ebrbs.getRadioParams().getChannelName());
			if(ebrbs.getRadioParams().getFreq()!=null){
				broadcast.setRadioFreq(new BigDecimal(ebrbs.getRadioParams().getFreq()));
			}
			broadcast.setRadioPower(ebrbs.getRadioParams().getPower());
			broadcast.setBackup(ebrbs.getRadioParams().getBackup());
			broadcast.setAutoSwitch(ebrbs.getRadioParams().getAutoSwitch());
			broadcast.setRemoteControl(ebrbs.getRadioParams().getRemoteControl());
			broadcast.setExperiment(ebrbs.getRadioParams().getExperiment());
		}	
		if(null != ebrbs.gettVParams()) {
			broadcast.setTvChannelName(ebrbs.gettVParams().getChannelName());
			broadcast.setTvFreq(ebrbs.gettVParams().getFreq());
			broadcast.setProgramNum(ebrbs.gettVParams().getProgramNum());
			broadcast.setTvChannelNum(ebrbs.gettVParams().getChannelNum());
		}
		if(ebrbs.getRelatedEBRPS()!=null){
			broadcast.setRelatedPsEbrId(ebrbs.getRelatedEBRPS().getEBRID());			
		}
		if(ebrbs.getRelatedEBRST()!=null){
			broadcast.setRelatedRsEbrId(ebrbs.getRelatedEBRST().getEBRID());			
		}
		if(ebrbs.getRelatedEBRAS()!=null){
			broadcast.setRelatedAsEbrId(ebrbs.getRelatedEBRAS().getEBRID());			
		}
		if(null == broadcast.getCreateTime()) {
			String dateFormat = DateStyle.YYYY_MM_DD_HH_MM_SS.getValue();
			Date rptTime = DateTimeUtil.stringToDate(ebrbs.getRptTime(), dateFormat);
			broadcast.setCreateTime(rptTime);
		}
		
		broadcast.setUpdateTime(new Date());
		broadcast.setSyncFlag(SyncFlag.nosync.getValue());
		ResourceModel model=parseResourceId(broadcast.getBsEbrId());
		broadcast.setAreaCode(model.getAreaCode());
		broadcast.setBsType(model.getResourceType());
		return broadcast;
	}
}
