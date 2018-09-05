package com.comtom.bc.server.ebd.impl;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comtom.bc.exchange.commom.EBDType;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.details.other.ConnectionCheck;
import com.comtom.bc.exchange.model.ebd.ebm.SRC;
import com.comtom.bc.exchange.util.DateStyle;
import com.comtom.bc.exchange.util.DateTimeUtil;
import com.comtom.bc.server.repository.dao.EbrCheckDAO;
import com.comtom.bc.server.repository.entity.EbrCheck;

/**
 * @author nobody
 * 心跳检测处理
 */
@Service
public class ConnectionCheckService extends AbstractEMDService{

	
	@Autowired
	private EbrCheckDAO ebrCheckDAO;
	
	@Override
	public String serviceType() {
		return EBDType.ConnectionCheck.name();
	}

	
	@Override
	public void preservice(EBD ebd, String ebdName, List<File> resourceFiles) {
		//心跳频繁,不记录接收数据包
	}
	
	@Override
	public void afterservice(EBD ebd, String ebdName) {
		//心跳频繁,不记录发送数据包
	}
	
	@Override
	public void service(EBD ebd,List<File> resourceFiles) {
		SRC src=ebd.getSRC();
		//必选资源ID
		String ebrId=src.getEBRID();
		//心跳处理
		ConnectionCheck check=ebd.getConnectionCheck();
		String rptTime=check.getRptTime();
		Date date=DateTimeUtil.stringToDate(rptTime, DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
		EbrCheck ebrCheck=ebrCheckDAO.findOne(ebrId);
		if(ebrCheck==null){
			ebrCheck=new EbrCheck();
			ebrCheck.setEbrId(ebrId);
		}
		ebrCheck.setRptTime(date);
		ebrCheckDAO.save(ebrCheck);
	}
}
