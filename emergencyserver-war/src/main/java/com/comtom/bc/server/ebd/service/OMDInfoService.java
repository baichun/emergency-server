package com.comtom.bc.server.ebd.service;

import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.details.other.OMDRequest;

public interface OMDInfoService {

	public String OMDType();
	
	public EBD service(String ebdId,OMDRequest odmRequest);
}
