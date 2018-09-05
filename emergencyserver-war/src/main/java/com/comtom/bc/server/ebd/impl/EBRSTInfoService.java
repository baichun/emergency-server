package com.comtom.bc.server.ebd.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comtom.bc.exchange.commom.EBDType;
import com.comtom.bc.exchange.commom.SyncFlag;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.commom.EBRST;
import com.comtom.bc.exchange.model.ebd.details.info.EBRSTInfo;
import com.comtom.bc.exchange.util.DateStyle;
import com.comtom.bc.exchange.util.DateTimeUtil;
import com.comtom.bc.server.ebd.model.ResourceModel;
import com.comtom.bc.server.repository.dao.EbrStationDAO;
import com.comtom.bc.server.repository.entity.EbrStation;
import com.comtom.bc.server.service.ResIDGeneratorService;

/**
 * @author nobody
 * 台站信息上报
 */
@Service
public class EBRSTInfoService extends AbstractEMDService{

	@Autowired
	private EbrStationDAO ebrStationDAO;
	
    @Autowired
    ResIDGeneratorService residGenService;
	
	@Override
	public String serviceType() {
		return EBDType.EBRSTInfo.name();
	}

	//处理台站上报请求，获取台站上报数据并保存台站到数据库
	@Transactional
	public void service(EBD ebd,List<File> resourceFiles) {
		EBRSTInfo stationInfo = ebd.getEBRSTInfo();
		List<EBRST> ebrStationList = stationInfo.getDataList();
		if(null == ebrStationList || ebrStationList.size() < 1) {
			return;
		}
		
		List<String> stationIds = new ArrayList<String>();
		for(EBRST st : ebrStationList) {
			stationIds.add(st.getEBRID());
		}
		
		//保存上报的台站数据， 其余的属性维持数据库原有状态
		List<EbrStation> stationsExist = ebrStationDAO.findAll(stationIds);
		List<EbrStation> stations2Save = new ArrayList<EbrStation>();
		if(null == stationsExist) {
			stationsExist = new ArrayList<EbrStation>();
		}
		for(EBRST st : ebrStationList) {
           EbrStation ebrst = getByEbrId(st.getEBRID(), stationsExist);
           stations2Save.add(newEbrStation(ebrst, st));
		}		
		List<EbrStation> saveImg = ebrStationDAO.save(stations2Save);
		
		//更新资源ID使用记录
		if(null == saveImg) {
			saveImg = new ArrayList<EbrStation>(); 
		}
		List<String> svIds = new ArrayList<String>();
		for(EbrStation svSt : saveImg) {
			svIds.add(svSt.getStationEbrId());
		}
		residGenService.updateResourceIdInfo(svIds.toArray(new String[svIds.size()]));
	}
	
	private final EbrStation getByEbrId(String ebrId, List<EbrStation> stations) {
		EbrStation found = null;
	    for(EbrStation st : stations) {
	    	if(st.getStationEbrId().equals(ebrId)) {
	    		found = st;
	    		break;
	    	}
	    }
	    return found;
	}
	
	private EbrStation newEbrStation(EbrStation ebrst, EBRST st) {
		EbrStation station = ebrst;
		if(null == station) {
			station = new EbrStation();
		}
		station.setStationEbrId(st.getEBRID());
		station.setStationName(st.getEBRName());
		station.setStationAddress(st.getAddress());
		station.setStationType(st.getEBRID().substring(2, 4));
		station.setContact(st.getContact());
		station.setPhoneNumber(st.getPhoneNumber());
		station.setLongitude(st.getLongitude());
		station.setLatitude(st.getLatitude());
		if(st.getRelatedEBRPS()!=null){
			station.setRelatedPsEbrId(st.getRelatedEBRPS().getEBRID());			
		}
		if(null == station.getCreateTime()) {
			String dateFormat = DateStyle.YYYY_MM_DD_HH_MM_SS.getValue();
			Date rptTime = DateTimeUtil.stringToDate(st.getRptTime(), dateFormat);
			station.setCreateTime(rptTime);
		}
		station.setUpdateTime(new Date());
		station.setSyncFlag(SyncFlag.nosync.getValue());
		
		ResourceModel model=parseResourceId(station.getStationEbrId());
		station.setAreaCode(model.getAreaCode());
		station.setStationType(model.getResourceType());
		return station;
	}
}
