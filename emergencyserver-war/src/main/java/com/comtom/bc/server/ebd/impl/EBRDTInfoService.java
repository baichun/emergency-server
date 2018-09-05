package com.comtom.bc.server.ebd.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comtom.bc.common.Constants;
import com.comtom.bc.exchange.commom.EBDType;
import com.comtom.bc.exchange.commom.SyncFlag;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.commom.EBRDT;
import com.comtom.bc.exchange.model.ebd.details.info.EBRDTInfo;
import com.comtom.bc.exchange.util.DateStyle;
import com.comtom.bc.exchange.util.DateTimeUtil;
import com.comtom.bc.server.ebd.model.ResourceModel;
import com.comtom.bc.server.repository.dao.EbrTerminalDAO;
import com.comtom.bc.server.repository.entity.EbrTerminal;
import com.comtom.bc.server.service.ResIDGeneratorService;

/**
 * @author nobody
 * 平台设备及终端信息
 */
@Service
public class EBRDTInfoService extends AbstractEMDService{
    @Autowired
	EbrTerminalDAO ebrTerminalDAO;
    
    @Autowired
    ResIDGeneratorService residGenService;
	
	@Override
	public String serviceType() {
		return EBDType.EBRDTInfo.name();
	}

	//处理终端上报请求，获取终端上报数据并保存终端到数据库
	@Transactional
	public void service(EBD ebd,List<File> resourceFiles) {
		
		EBRDTInfo devTrmInfo = ebd.getEBRDTInfo();
		List<EBRDT> ebrDevTrmList = devTrmInfo.getDataList();
		if(null == ebrDevTrmList || ebrDevTrmList.size() < 1) {
			return;
		}
		
		List<String> devTrmIds = new ArrayList<String>();
		for(EBRDT dt : ebrDevTrmList) {
			devTrmIds.add(dt.getEBRID());
		}
		
		//保存上报的终端数据， 其余的属性维持数据库原有状态
		List<EbrTerminal> devTrmsExist = ebrTerminalDAO.findAll(devTrmIds);
		List<EbrTerminal> devTrms2Save = new ArrayList<EbrTerminal>();
		if(null == devTrmsExist) {
			devTrmsExist = new ArrayList<EbrTerminal>();
		}
		for(EBRDT dt : ebrDevTrmList) {
			EbrTerminal devTrm = getByEbrId(dt.getEBRID(), devTrmsExist);
			devTrms2Save.add(newDevTrm(devTrm, dt));
		}
		List<EbrTerminal> saveImg = ebrTerminalDAO.save(devTrms2Save);
		
		//更新资源ID使用记录
		if(null == saveImg) {
			saveImg = new ArrayList<EbrTerminal>(); 
		}
		List<String> svIds = new ArrayList<String>();
		for(EbrTerminal svTm : saveImg) {
			svIds.add(svTm.getTerminalEbrId());
		}
		residGenService.updateResourceIdInfo(svIds.toArray(new String[svIds.size()]));
	}
	
	private final EbrTerminal getByEbrId(String ebrId, List<EbrTerminal> devTrmsExist) {
		EbrTerminal found = null;
		for(EbrTerminal dt : devTrmsExist) {
			if(dt.getTerminalEbrId().equals(ebrId)) {
				found = dt;
				break;
			}
		}
		
		return found;
	}
	
	private EbrTerminal newDevTrm(EbrTerminal devTrm, EBRDT dt) {
		EbrTerminal terminal = devTrm;
		if(null == terminal) {
			terminal = new EbrTerminal();
		}
		terminal.setTerminalEbrId(dt.getEBRID());
		terminal.setTerminalEbrName(dt.getEBRName());
		terminal.setTerminalType(dt.getEBRID().substring(13, 17));
		terminal.setLongitude(dt.getLongitude());
		terminal.setLatitude(dt.getLatitude());
		terminal.setTerminalState(Constants.EBR_STATE_RUNNING);
		
		if(null == terminal.getCreateTime()) {
			String dateFormat = DateStyle.YYYY_MM_DD_HH_MM_SS.getValue();
			Date rptTime = DateTimeUtil.stringToDate(dt.getRptTime(), dateFormat);
			terminal.setCreateTime(rptTime);
		}
		
		terminal.setUpdateTime(new Date());
		if(dt.getRelatedEBRPS()!=null){
			terminal.setRelatedPsEbrId(dt.getRelatedEBRPS().getEBRID());
		}
		
		terminal.setSyncFlag(SyncFlag.nosync.getValue());
//		ResourceModel model=parseResourceId(terminal.getTerminalEbrId());
//		terminal.setTerminalType(model.getResourceType());
		
		return terminal;
	}
}
