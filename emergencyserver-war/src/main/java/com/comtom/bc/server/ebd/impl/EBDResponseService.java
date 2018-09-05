package com.comtom.bc.server.ebd.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comtom.bc.exchange.commom.EBDType;
import com.comtom.bc.exchange.commom.SendFlag;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.server.repository.dao.EbdResponseDAO;

/**
 * @author nobody
 * 通用结果反馈(不需要处理)
 */
@Service
public class EBDResponseService extends AbstractEMDService{

	@Autowired
	private EbdResponseDAO ebdResponseDAO;
	
	@Override
	public String serviceType() {
		return EBDType.EBDResponse.name();
	}

	@Override
	public void service(EBD ebd,List<File> resourceFiles) {
		recordEbdResponse(ebd, ebd.getEBDResponse(), SendFlag.receive);
	}
}
