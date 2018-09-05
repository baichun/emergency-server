package com.comtom.bc.server.ebd.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comtom.bc.exchange.commom.EBDType;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.commom.EBRPS;
import com.comtom.bc.exchange.model.ebd.details.state.EBRPSState;
import com.comtom.bc.server.repository.dao.EbrPlatformDAO;
import com.comtom.bc.server.repository.entity.EbrPlatform;

/**
 * @author nobody
 * 平台状态上报
 */
@Service
public class EBRPSStateService extends AbstractEMDService{

	@Autowired
	EbrPlatformDAO ebrPlatformDAO;
	
	@Override
	public String serviceType() {
		return EBDType.EBRPSState.name();
	}

	@Override
	public void service(EBD ebd,List<File> resourceFiles) {		
		EBRPSState platformState = ebd.getEBRPSState();
		List<EBRPS> ebrPlatStateList = platformState.getDataList();
		if(null == ebrPlatStateList || ebrPlatStateList.size() < 1) {
			return;
		}
		
		List<String> platformIds = new ArrayList<String>();
		for(EBRPS ps : ebrPlatStateList) {
			platformIds.add(ps.getEBRID());
		}
		
		List<EbrPlatform> platformsExist = ebrPlatformDAO.findAll(platformIds);
		if(null == platformsExist || platformsExist.size() < 1) {
			return;
		}
		
		List<EbrPlatform> platforms2Save = new ArrayList<EbrPlatform>();
		for(EbrPlatform platform : platformsExist) {
			EBRPS targetProps = getByEbrId(platform.getPsEbrId(), ebrPlatStateList);
			if(targetProps != null) {
				platform.setPsState(targetProps.getStateCode());
				platform.setUpdateTime(new Date());
				platforms2Save.add(platform);
			}
		}
		ebrPlatformDAO.save(platforms2Save);
	}
	
	private final EBRPS getByEbrId(String ebrPlatId, List<EBRPS> ebrPlatStateList) {
		EBRPS found = null;
		for(EBRPS ps : ebrPlatStateList) {
			if(ebrPlatId.equals(ps.getEBRID())) {
				found = ps;
				break;
			}
		}
		return found;
	}
}
