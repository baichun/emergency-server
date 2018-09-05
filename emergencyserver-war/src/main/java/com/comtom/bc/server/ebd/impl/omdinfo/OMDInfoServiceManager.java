package com.comtom.bc.server.ebd.impl.omdinfo;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;

import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.server.ebd.service.OMDInfoService;

/**
 * @author nobody
 * 运维请求数据包服务分发管理类
 */
@Service
public class OMDInfoServiceManager implements BeanPostProcessor,DisposableBean,InitializingBean{
	
    private Logger logger = LoggerFactory.getLogger (getClass());
	
	private Map<String,OMDInfoService> serviceMap = new HashMap<String,OMDInfoService>();
	
	/**
	 * 分发服务
	 * @param ebd
	 * @param resourceFiles
	 */
	public EBD dispatchService(EBD ebd){
		String serviceType=ebd.getOMDRequest().getOMDType();
		return serviceMap.get(serviceType).service(ebd.getEBDID(),ebd.getOMDRequest());
	}

	
	@Override
	public Object postProcessAfterInitialization(Object arg0, String arg1)
			throws BeansException {
		return arg0;
	}

	@Override
	public Object postProcessBeforeInitialization(Object arg0, String arg1)
			throws BeansException {
		if(arg0 instanceof OMDInfoService){
			OMDInfoService omdInfoService=(OMDInfoService) arg0;
			serviceMap.put(omdInfoService.OMDType(), omdInfoService);
			logger.info("add service "+omdInfoService.OMDType());
		}
		return arg0;
	}

	@Override
	public void destroy() throws Exception {
		serviceMap.clear();
		serviceMap=null;
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("init serviceMap:="+serviceMap);
	}
	
}
