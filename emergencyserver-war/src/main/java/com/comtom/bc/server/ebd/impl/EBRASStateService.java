package com.comtom.bc.server.ebd.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comtom.bc.exchange.commom.EBDType;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.commom.EBRAS;
import com.comtom.bc.exchange.model.ebd.details.state.EBRASState;
import com.comtom.bc.server.repository.dao.EbrAdaptorDAO;
import com.comtom.bc.server.repository.entity.EbrAdaptor;

/**
 * @author nobody
 * 消息接收设备状态
 */
@Service
public class EBRASStateService extends AbstractEMDService{
    @Autowired
	EbrAdaptorDAO ebrAdaptorDAO;
	
	@Override
	public String serviceType() {
		return EBDType.EBRASState.name();
	}

	@Override
	public void service(EBD ebd,List<File> resourceFiles) {
		
		EBRASState adpatorState = ebd.getEBRASState();
		List<EBRAS> adaptorStateList = adpatorState.getDataList();
		if(null == adaptorStateList || adaptorStateList.size() < 1) {
			return;
		}
		List<String> ebrAdaptorIds = new ArrayList<String>();
		for(EBRAS as : adaptorStateList) {
			ebrAdaptorIds.add(as.getEBRID());
		}
		
		List<EbrAdaptor> adaptorsExist = ebrAdaptorDAO.findAll(ebrAdaptorIds);
		if(null == adaptorsExist || adaptorsExist.size() == 0) {
			return;
		}
		
		List<EbrAdaptor> adaptors2Save = new ArrayList<EbrAdaptor>();
		for(EbrAdaptor adaptor : adaptorsExist) {
			EBRAS targetProps = getByEbrId(adaptor.getAsEbrId(), adaptorStateList);
			if(targetProps != null) {
				adaptor.setAsState(targetProps.getStateCode());
				adaptor.setUpdateTime(new Date());
				adaptors2Save.add(adaptor);
			}
		}
		ebrAdaptorDAO.save(adaptors2Save);
	}
	
	private final EBRAS getByEbrId(String ebrDtId, List<EBRAS> adaptorStateList) {
		EBRAS found = null;
		for(EBRAS dt : adaptorStateList) {
			if(ebrDtId.equals(dt.getEBRID())) {
				found = dt;
				break;
			}
		}
		return found;
	}
}
