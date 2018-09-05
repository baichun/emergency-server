package com.comtom.bc.server.ebd.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.comtom.bc.exchange.commom.EBDType;
import com.comtom.bc.exchange.commom.SyncFlag;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.commom.EBRAS;
import com.comtom.bc.exchange.model.ebd.details.info.EBRASInfo;
import com.comtom.bc.exchange.util.DateStyle;
import com.comtom.bc.exchange.util.DateTimeUtil;
import com.comtom.bc.server.ebd.model.ResourceModel;
import com.comtom.bc.server.repository.dao.EbrAdaptorDAO;
import com.comtom.bc.server.repository.entity.EbrAdaptor;
import com.comtom.bc.server.service.ResIDGeneratorService;

/**
 * @author nobody
 * 消息接收设备信息上报
 */
@Service
public class EBRASInfoService extends AbstractEMDService{
    @Autowired
	EbrAdaptorDAO ebrAdaptorDAO;
    
    @Autowired
    ResIDGeneratorService residGenService;
    
	@Override
	public String serviceType() {
		return EBDType.EBRASInfo.name();
	}

	//处理消息接收设备上报请求，获取消息接收设备上报数据并保存消息接收设备到数据库
	@Transactional
	public void service(EBD ebd,List<File> resourceFiles) {
		EBRASInfo adpatorInfo = ebd.getEBRASInfo();
		List<EBRAS> ebrAdaptorList = adpatorInfo.getDataList();
		if(null == ebrAdaptorList || ebrAdaptorList.size() ==0) {
			return;
		}
		List<String> ebrAdaptorIds = new ArrayList<String>();
		for(EBRAS as : ebrAdaptorList) {
			ebrAdaptorIds.add(as.getEBRID());
		}
		
		//保存上报的消息接收设备数据， 其余的属性维持数据库原有状态
		List<EbrAdaptor> adaptorsExist = ebrAdaptorDAO.findAll(ebrAdaptorIds);
		List<EbrAdaptor> adaptors2Save = new ArrayList<EbrAdaptor>();
		if(null == adaptorsExist) {
			adaptorsExist = new ArrayList<EbrAdaptor>();
		}
		for(EBRAS as : ebrAdaptorList) {
			EbrAdaptor ebrAs = getByEbrId(as.getEBRID(), adaptorsExist);
			adaptors2Save.add(newEbrAdaptor(ebrAs, as));
		}
		List<EbrAdaptor> saveImg = ebrAdaptorDAO.save(adaptors2Save);
		//更新资源ID使用记录
		if(null == saveImg) {
			saveImg = new ArrayList<EbrAdaptor>();
		}
		
		List<String> svAsIds = new ArrayList<String>();
		for(EbrAdaptor svAs : saveImg) {
			svAsIds.add(svAs.getAsEbrId());
		}
		
		residGenService.updateResourceIdInfo(svAsIds.toArray(new String[svAsIds.size()]));
	}
	
	private final EbrAdaptor getByEbrId(String ebrId, List<EbrAdaptor> adaptorsExist) {
		EbrAdaptor found = null;
		for(EbrAdaptor as : adaptorsExist) {
			if(as.getAsEbrId().equals(ebrId)) {
				found = as;
				break;
			}
		}
		
		return found;
	}
	
	private EbrAdaptor newEbrAdaptor(EbrAdaptor ebrAs, EBRAS as) {
		EbrAdaptor adaptor = ebrAs;
		if(null == adaptor) {
			adaptor = new EbrAdaptor();
		}
		adaptor.setAsEbrId(as.getEBRID());       
		adaptor.setAsEbrName(as.getEBRName());     
		adaptor.setAsUrl(as.getURL());         
		adaptor.setAsType(as.getEBRID().substring(2, 4));    
		if(as.getRelatedEBRST()!=null){
			adaptor.setRelatedRsEbrId(as.getRelatedEBRST().getEBRID()); 			
		}
		if(as.getRelatedEBRPS()!=null){
			adaptor.setRelatedPsEbrId(as.getRelatedEBRPS().getEBRID());   			
		}
		adaptor.setLongitude(as.getLongitude());     
		adaptor.setLatitude(as.getLatitude());
		if(null == adaptor.getCreateTime()) {
			String dateFormat = DateStyle.YYYY_MM_DD_HH_MM_SS.getValue();
			Date rptTime = DateTimeUtil.stringToDate(as.getRptTime(), dateFormat);
			adaptor.setCreateTime(rptTime);    
		}
		adaptor.setUpdateTime(new Date());   
		List<?> params = as.getParams();
		if(!CollectionUtils.isEmpty(params)){
			if(params.size() > 0) {
				adaptor.setParam1(String.valueOf(params.get(0)));
			} 
			if(params.size() > 1) {
				adaptor.setParam1(String.valueOf(params.get(1)));
			}
		}
		adaptor.setSyncFlag(SyncFlag.nosync.getValue());
		ResourceModel model=parseResourceId(adaptor.getAsEbrId());
		adaptor.setAreaCode(model.getAreaCode());
		adaptor.setAsType(model.getResourceType());
		return adaptor;
	}
}
