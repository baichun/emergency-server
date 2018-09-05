package com.comtom.bc.exchange.service;

import java.io.File;
import java.util.List;

import com.comtom.bc.exchange.model.ebd.EBD;

/**
 * @author nobody
 * 应急消息报服务接口
 */
public interface EMDservice {
	
	public String serviceType();
	
	public void service(EBD ebd,List<File> resourceFiles);
	
	public void preservice(EBD ebd,String ebdName,List<File> resourceFiles);

	public void afterservice(EBD ebd,String ebdName);
	
}
