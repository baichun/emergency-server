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
import com.comtom.bc.exchange.model.ebd.commom.EBRPS;
import com.comtom.bc.exchange.model.ebd.details.info.EBRPSInfo;
import com.comtom.bc.exchange.util.DateStyle;
import com.comtom.bc.exchange.util.DateTimeUtil;
import com.comtom.bc.server.ebd.model.ResourceModel;
import com.comtom.bc.server.repository.dao.EbrBsDAO;
import com.comtom.bc.server.repository.dao.EbrPlatformDAO;
import com.comtom.bc.server.repository.entity.EbrPlatform;
import com.comtom.bc.server.service.ResIDGeneratorService;

/**
 * @author nobody
 * 应急广播平台信息上报
 */
@Service
public class EBRPSInfoService extends AbstractEMDService{
	@Autowired
	EbrPlatformDAO ebrPlatformDAO;
	
	@Autowired
	EbrBsDAO ebrBsDAO;
	
    @Autowired
    ResIDGeneratorService residGenService;
    
	@Override
	public String serviceType() {
		return EBDType.EBRPSInfo.name();
	}

	//处理应急广播平台上报请求，获取应急广播平台上报数据并保存应急广播平台到数据库
	@Transactional
	public void service(EBD ebd,List<File> resourceFiles) {		
		EBRPSInfo platformInfo = ebd.getEBRPSInfo();
		//List<EBRPS> ebrPlatformList = platformList(platformInfo.getDataList());
		List<EBRPS> ebrPlatformList = platformInfo.getDataList();
		if(null == ebrPlatformList || ebrPlatformList.size() < 1) {
			return;
		}
		
		List<String> platformIds = new ArrayList<String>();
		for(EBRPS ps : ebrPlatformList) {
			platformIds.add(ps.getEBRID());
		}
		
		//保存上报的应急广播平台数据， 其余的属性维持数据库原有状态
		List<EbrPlatform> platformsExist = ebrPlatformDAO.findAll(platformIds);
		List<EbrPlatform> platforms2Save = new ArrayList<EbrPlatform>();
		if(null == platformsExist) {
			platformsExist = new ArrayList<EbrPlatform>();
		}	
		for(EBRPS ps : ebrPlatformList) {
           EbrPlatform ebrps = getByEbrId(ps.getEBRID(), platformsExist);
           platforms2Save.add(newEbrPlatform(ebrps, ps));
		}
		List<EbrPlatform> saveImg = ebrPlatformDAO.save(platforms2Save);
		
		//更新资源ID使用记录
		if(null == saveImg) {
			saveImg = new ArrayList<EbrPlatform>(); 
		}
		List<String> svIds = new ArrayList<String>();
		for(EbrPlatform svPs : saveImg) {
			svIds.add(svPs.getPsEbrId());
		}
		residGenService.updateResourceIdInfo(svIds.toArray(new String[svIds.size()]));
	}
	
	private final EbrPlatform getByEbrId(String ebrId, List<EbrPlatform> platforms) {
		EbrPlatform found = null;
	    for(EbrPlatform ps : platforms) {
	    	if(ps.getPsEbrId().equals(ebrId)) {
	    		found = ps;
	    		break;
	    	}
	    }
	    return found;
	}
	
	private EbrPlatform newEbrPlatform(EbrPlatform ebrps, EBRPS ps) {
		EbrPlatform platform = ebrps;
		if(null == platform) {
			platform = new EbrPlatform();
		}
		platform.setPsEbrId(ps.getEBRID());
		platform.setPsUrl(ps.getURL());
		platform.setPsEbrName(ps.getEBRName());
		platform.setPsType(ps.getEBRID().substring(2,4));
		if(ps.getRelatedEBRPS()!=null){
			platform.setParentPsEbrId(ps.getRelatedEBRPS().getEBRID());			
		}else{
			//如果是直接的子平台则设置父平台为当前平台
			platform.setParentPsEbrId(getEbrPlatFormID());
		}
		platform.setPsAddress(ps.getAddress());
		platform.setContact(ps.getContact());
		platform.setPhoneNumber(ps.getPhoneNumber());
		platform.setLongitude(ps.getLongitude());
		platform.setLatitude(ps.getLatitude());
		
		if(null == platform.getCreateTime()) {
			String dateFormat = DateStyle.YYYY_MM_DD_HH_MM_SS.getValue();
			Date rptTime = DateTimeUtil.stringToDate(ps.getRptTime(), dateFormat);
			platform.setCreateTime(rptTime);
		}
		platform.setUpdateTime(new Date());
		platform.setSyncFlag(SyncFlag.nosync.getValue());
		
		ResourceModel model=parseResourceId(platform.getPsEbrId());
		platform.setAreaCode(model.getAreaCode());
		platform.setPlatLevel(model.getPlatLevel());
		platform.setPsType(model.getResourceType());
		
		return platform;
	}
		
}
