package com.comtom.bc.server.ebd.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comtom.bc.exchange.commom.EBDType;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.commom.EBRBS;
import com.comtom.bc.exchange.model.ebd.details.state.EBRBSState;
import com.comtom.bc.server.repository.dao.EbrBsDAO;
import com.comtom.bc.server.repository.entity.EbrBroadcast;

/**
 * @author nobody
 * 播出系统状态
 */
@Service
public class EBRBSStateService extends AbstractEMDService{
	@Autowired
	EbrBsDAO ebrBsDAO;
	
	@Override
	public String serviceType() {
		return EBDType.EBRBSState.name();
	}

	@Override
	public void service(EBD ebd,List<File> resourceFiles) {		
		EBRBSState broadcastState = ebd.getEBRBSState();
		List<EBRBS> bsStateList = broadcastState.getDataList();
		if(null == bsStateList || bsStateList.size() < 1) {
			return;
		}
		List<String> bsIds = new ArrayList<String>();
		for(EBRBS ebrbs : bsStateList) {
			bsIds.add(ebrbs.getEBRID());
		}
		
		List<EbrBroadcast> broadcastsExist = ebrBsDAO.findAll(bsIds);
		if(null == broadcastsExist || broadcastsExist.size() < 1) {
			return;
		}
		
		List<EbrBroadcast> broadcasts2Save = new ArrayList<EbrBroadcast>();
		for(EbrBroadcast broadcast : broadcastsExist) {
			EBRBS targetProps = getByEbrId(broadcast.getBsEbrId(), bsStateList);
			if(targetProps != null) {
				broadcast.setBsState(targetProps.getStateCode());
				broadcast.setUpdateTime(new Date());
				broadcasts2Save.add(broadcast);
			}
		}
		ebrBsDAO.save(broadcasts2Save);
	}
	
	private final EBRBS getByEbrId(String ebrBsId, List<EBRBS> bsStateList) {
		EBRBS found = null;
		for(EBRBS bs : bsStateList) {
			if(ebrBsId.equals(bs.getEBRID())) {
				found = bs;
				break;
			}
		}
		
		return found;
	}
}
