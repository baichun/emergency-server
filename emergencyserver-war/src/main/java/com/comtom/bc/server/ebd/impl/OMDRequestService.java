package com.comtom.bc.server.ebd.impl;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comtom.bc.exchange.commom.EBDType;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.commom.Params;
import com.comtom.bc.exchange.model.ebd.details.other.OMDRequest;
import com.comtom.bc.exchange.util.DateStyle;
import com.comtom.bc.exchange.util.DateTimeUtil;
import com.comtom.bc.server.ebd.impl.omdinfo.OMDInfoServiceManager;
import com.comtom.bc.server.repository.dao.OMDRequestDAO;
import com.comtom.bc.server.repository.dao.SysParamDAO;
import com.comtom.bc.server.repository.entity.OmdRequest;

/**
 * @author nobody
 * 运维数据请求处理类
 */
@Service
public class OMDRequestService extends AbstractEMDService{

	@Autowired
	private OMDInfoServiceManager oMDInfoServiceManager;
		
	@Autowired
	private OMDRequestDAO oMDRequestDAO;
	
	@Override
	public String serviceType() {
		return EBDType.OMDRequest.name();
	}

	@Override
	public void service(final EBD ebd,List<File> resourceFiles) {
		Executors.newSingleThreadExecutor().execute(new Runnable() {
			@Override
			public void run() {
				String relatedEbdId=ebd.getEBDID();
				String relatedEbrId=ebd.getSRC().getEBRID();
				OMDRequest omdRequestData=ebd.getOMDRequest();
				String omdType=omdRequestData.getOMDType();
				
				Date rptStartTime=null;
				Date rptEndTime=null;
				String rptType=null;
				
				Params params=omdRequestData.getParams();
				if(params!=null){
					String startTimeStr=params.getRptStartTime();
					String endTimeStr=params.getRptEndTime();
					rptType=omdRequestData.getParams().getRptType();
					if(StringUtils.isNotEmpty(startTimeStr)){
						rptStartTime=DateTimeUtil.stringToDate(startTimeStr, DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
					}
					if(StringUtils.isNotEmpty(endTimeStr)){
						rptEndTime=DateTimeUtil.stringToDate(endTimeStr, DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
					}					
				}
				OmdRequest omdRequest=new OmdRequest();
				omdRequest.setOmdRequestId(UUID.randomUUID().toString());
				omdRequest.setOmdType(omdType);
				omdRequest.setRelatedEbdId(relatedEbdId);
				omdRequest.setRelatedEbrId(relatedEbrId);
				omdRequest.setRptEndTime(rptEndTime);
				omdRequest.setRptStartTime(rptStartTime);
				omdRequest.setRptType(rptType);
				oMDRequestDAO.save(omdRequest);
				
				EBD ebdResponse=oMDInfoServiceManager.dispatchService(ebd);
				sendEBD(ebdResponse,getParentPlatUrl());
				// sendEBD(ebdResponse,ebd.getSRC().getURL());
			}
		});
	}
	
}
