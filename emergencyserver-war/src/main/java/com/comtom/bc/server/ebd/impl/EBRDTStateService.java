package com.comtom.bc.server.ebd.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.comtom.bc.exchange.commom.SyncFlag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comtom.bc.exchange.commom.EBDType;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.commom.EBRDT;
import com.comtom.bc.exchange.model.ebd.details.state.EBRDTState;
import com.comtom.bc.server.repository.dao.EbrTerminalDAO;
import com.comtom.bc.server.repository.entity.EbrTerminal;

/**
 * @author nobody
 * 平台设备及终端状态
 */
@Service
public class EBRDTStateService extends AbstractEMDService{
    @Autowired
	EbrTerminalDAO ebrTerminalDAO;
    
	@Override
	public String serviceType() {
		return EBDType.EBRDTState.name();
	}

	@Override
	public void service(EBD ebd,List<File> resourceFiles) {		
		EBRDTState devTrmState = ebd.getEBRDTState();
		List<EBRDT> ebrDevTrmList = devTrmState.getDataList();
		if(null == ebrDevTrmList || ebrDevTrmList.size() < 1) {
			return;
		}
		
		List<String> devTrmIds = new ArrayList<String>();
		for(EBRDT dt : ebrDevTrmList) {
			devTrmIds.add(dt.getEBRID());
		}
		
		List<EbrTerminal> terminalsExist = ebrTerminalDAO.findAll(devTrmIds);
		if(null == terminalsExist || terminalsExist.size() < 1) {
			return;
		}
		
		List<EbrTerminal> terminals2Save = new ArrayList<EbrTerminal>();
		for(EbrTerminal terminal : terminalsExist) {
			EBRDT targetProps = getByEbrId(terminal.getTerminalEbrId(), ebrDevTrmList);
			if(targetProps != null) {
				terminal.setTerminalState(targetProps.getStateCode());
				terminal.setUpdateTime(new Date());
				terminal.setStatusSyncFlag(SyncFlag.nosync.getValue());
				terminals2Save.add(terminal);
			}
		}
		ebrTerminalDAO.save(terminals2Save);
	}
	
	private final EBRDT getByEbrId(String ebrDtId, List<EBRDT> ebrDevTrmStateList) {
		EBRDT found = null;
		for(EBRDT dt : ebrDevTrmStateList) {
			if(ebrDtId.equals(dt.getEBRID())) {
				found = dt;
				break;
			}
		}
		return found;
	}
}
