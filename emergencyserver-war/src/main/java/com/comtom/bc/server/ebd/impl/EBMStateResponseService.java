package com.comtom.bc.server.ebd.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.comtom.bc.exchange.commom.BroadcastStateEnum;
import com.comtom.bc.exchange.commom.EBDRespResultEnum;
import com.comtom.bc.exchange.commom.EBDType;
import com.comtom.bc.exchange.excepion.EbmException;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.commom.Coverage;
import com.comtom.bc.exchange.model.ebd.commom.EBRAS;
import com.comtom.bc.exchange.model.ebd.commom.EBRBS;
import com.comtom.bc.exchange.model.ebd.commom.EBRPS;
import com.comtom.bc.exchange.model.ebd.commom.EBRST;
import com.comtom.bc.exchange.model.ebd.details.other.EBMStateResponse;
import com.comtom.bc.exchange.model.ebd.ebm.ResBrdInfo;
import com.comtom.bc.exchange.model.ebd.ebm.ResBrdItem;
import com.comtom.bc.exchange.model.ebd.ebm.SRC;
import com.comtom.bc.exchange.util.DateStyle;
import com.comtom.bc.exchange.util.DateTimeUtil;
import com.comtom.bc.exchange.util.EBDModelBuild;
import com.comtom.bc.server.repository.dao.EbmBrdRecordDAO;
import com.comtom.bc.server.repository.dao.EbmDAO;
import com.comtom.bc.server.repository.dao.EbmResBsDAO;
import com.comtom.bc.server.repository.dao.EbmResDAO;
import com.comtom.bc.server.repository.dao.SchemeEbrDAO;
import com.comtom.bc.server.repository.entity.Ebm;
import com.comtom.bc.server.repository.entity.EbmBrdRecord;
import com.comtom.bc.server.repository.entity.EbmRes;
import com.comtom.bc.server.repository.entity.EbmResBs;
import com.comtom.bc.server.repository.entity.SchemeEbr;

/**
 * @author nobody
 * 播发状态反馈数据上报
 */
@Service
public class EBMStateResponseService extends AbstractEMDService{
	
//	@Autowired
//	private EbmStateResponseDAO ebmStateResponseDAO;

	@Autowired
	private SchemeEbrDAO schemeEbrDAO;
	
	@Autowired
	private EbmDAO ebmDAO;
	
	@Autowired
	private EbmResDAO ebmResDAO;
	
	@Autowired
	private EbmResBsDAO ebmResBsDAO;
	
	@Autowired
	private EbmBrdRecordDAO ebmBrdRecordDAO;
	
	@Override
	public String serviceType() {
		return EBDType.EBMStateResponse.name();
	}

	@Override
	public void service(EBD ebd,List<File> resourceFiles) {
		SRC src = ebd.getSRC();
		// 必选资源ID
		final String ebdSrcEbrId = src.getEBRID();
		final EBMStateResponse ebmStateResponse=ebd.getEBMStateResponse();
		
		EbmBrdRecord brdRecord=convertEbmBrdRecord(ebmStateResponse, ebdSrcEbrId);
		//更新播放记录状态
		ebmBrdRecordDAO.save(brdRecord);
		
		//播放记录关联资源对于的播出系统集合
		List<EbmResBs> ebmResBsList=new ArrayList<EbmResBs>();
		//必选
		ResBrdInfo brdInfo=ebmStateResponse.getResBrdInfo();
		//必选
		List<ResBrdItem> items=brdInfo.getDataList();
		//播放记录关联资源集合
		// TODO 播发记录中保存了关联信息，这里不做保存
//		List<EbmRes> resList=convertEbmResList(items, brdRecord.getBrdItemId(),ebmResBsList);
//
//		if(!CollectionUtils.isEmpty(resList)){
//			//保存播发记录关联资源信息
//			ebmResDAO.save(resList);
//		}
//		if(!CollectionUtils.isEmpty(ebmResBsList)){
//			//保存播发记录关联资源的播出系统信息
//			ebmResBsDAO.save(ebmResBsList);
//		}
		
		//将状态信息上报
		Executors.newSingleThreadExecutor().execute(new Runnable() {
			@Override
			public void run() {
				//统计当前系统的消息播发记录并上报上级系统
				reportBrdItem(ebmStateResponse);
			}
		});		
	}
	
	private void reportBrdItem(EBMStateResponse ebmStateResponse){
		String parentPlatUrl=getParentPlatUrl();
		if(StringUtils.isEmpty(parentPlatUrl)){
			return ;
		}
		String ebmId=ebmStateResponse.getEBM().getEBMID();
		Ebm ebm=ebmDAO.findOne(ebmId);
		if(ebm==null){
			return ;
		}
		Integer schemeId=ebm.getSchemeId();
		//ebm对应的播发记录
		List<EbmBrdRecord> ebmBrdRecords=getEbmBrdRecordList(ebmId);
		//重新统计覆盖区域
		Coverage coverage=getCoverage(schemeId, ebmBrdRecords);
		//统计播放结果
		BroadcastStateEnum stateEnum=getBrdState(schemeId, ebmBrdRecords);
		
		ebmStateResponse.setBrdStateCode(stateEnum.getCode());
		ebmStateResponse.setBrdStateDesc(stateEnum.getBrdstate());
		ebmStateResponse.setCoverage(coverage);
		
		String ebrId=getEbrPlatFormID();
		String srcUrl=getPlatFormUrl();
		String ebdIndex=generateEbdIndex();
		EBD ebd=EBDModelBuild.buildStateResponse(ebrId, srcUrl, ebdIndex, ebmStateResponse);
		sendEBD(ebd, parentPlatUrl);
	}
	
	
	
	private List<EbmRes> convertEbmResList(List<ResBrdItem> items,final String brdItemId, List<EbmResBs> ebmResBsList) {
		List<EbmRes> result=new ArrayList<EbmRes>();
		if(CollectionUtils.isEmpty(items)){
			return result;
		}
		for (ResBrdItem resBrdItem : items) {
			//可选
			EBRPS ebrps=resBrdItem.getEBRPS();
			String ebrpsId=null;
			if(ebrps!=null){
				ebrpsId=ebrps.getEBRID();
			}
			//可选
			EBRST ebrst=resBrdItem.getEBRST();
			String ebrstId=null;
			if(ebrst!=null){
				ebrstId=ebrst.getEBRID();
			}					
			//可选
			EBRAS ebras=resBrdItem.getEBRAS();
			String ebrasId=null;
			if(ebras!=null){
				ebrasId=ebras.getEBRID();
			}
			final String tmpebrpsId=ebrpsId;
			Specification<EbmRes> specification=new Specification<EbmRes>() {
				@Override
				public Predicate toPredicate(Root<EbmRes> root,
						CriteriaQuery<?> criteriaquery, CriteriaBuilder criteriabuilder) {
					Predicate predicate =criteriabuilder.conjunction();
					Predicate condition1=criteriabuilder.equal(root.get("brdItemId"),brdItemId);
					predicate.getExpressions().add(condition1);
					if(tmpebrpsId!=null){
						Predicate condition2=criteriabuilder.equal(root.get("ebrpsId"),tmpebrpsId);
						predicate.getExpressions().add(condition2);
					}
					return predicate;
				}
			};
			
			EbmRes ebmRes=ebmResDAO.findOne(specification);			
			if(ebmRes==null){
				ebmRes=new EbmRes();
				ebmRes.setBrdItemId(brdItemId);
				ebmRes.setEbrasId(ebrasId);
				ebmRes.setEbrpsId(ebrpsId);
				ebmRes.setEbrstId(ebrstId);
				ebmRes.setEbmResourceId(UUID.randomUUID().toString());
				result.add(ebmRes);
			}
			
			List<EBRBS> ebrbsList= resBrdItem.getDataList();
			if(CollectionUtils.isEmpty(ebrbsList)){
				continue ;
			}
			final String ebmResourceId=ebmRes.getEbmResourceId();
			for (EBRBS ebrbs : ebrbsList) {
				String rptTimeStr2=ebrbs.getRptTime();
				Date rptTime2=DateTimeUtil.stringToDate(rptTimeStr2, DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
//				String brdSysType=ebrbs.getBrdSysType();
				String brdSysInfo=ebrbs.getBrdSysInfo();
				String startTimeStr=ebrbs.getStartTime();
				Date startTime=DateTimeUtil.stringToDate(startTimeStr, DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
				String endTimeStr=ebrbs.getEndTime();
				Date endTime=DateTimeUtil.stringToDate(endTimeStr, DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
				
				String fileURL=ebrbs.getFileURL();
				Integer brdStateCode2=ebrbs.getBrdStateCode();
				String brdStateDesc2=ebrbs.getBrdStateDesc();

				final Date rptTimeTemp=rptTime2;
				Specification<EbmResBs> specification2=new Specification<EbmResBs>() {
					@Override
					public Predicate toPredicate(Root<EbmResBs> root,
							CriteriaQuery<?> criteriaquery, CriteriaBuilder criteriabuilder) {
						Predicate predicate =criteriabuilder.conjunction();
						Predicate condition1=criteriabuilder.equal(root.get("ebmResourceId"),ebmResourceId);
						Predicate condition2=criteriabuilder.equal(root.get("rptTime"),rptTimeTemp);
						predicate.getExpressions().add(condition1);
						predicate.getExpressions().add(condition2);
						return predicate;
					}
				};
				EbmResBs ebmResBs=ebmResBsDAO.findOne(specification2);
				if(ebmResBs==null){
					ebmResBs=new EbmResBs();
					ebmResBs.setBrdStateCode(brdStateCode2);
					ebmResBs.setBrdStateDesc(brdStateDesc2);
					ebmResBs.setBrdSysInfo(brdSysInfo);
//					ebmResBs.setBrdSysType(brdSysType);
					ebmResBs.setEbmResourceId(ebmResourceId);
					ebmResBs.setEndTime(endTime);
					ebmResBs.setFileURL(fileURL);
					ebmResBs.setRptTime(rptTime2);
					ebmResBs.setStartTime(startTime);
					ebmResBsList.add(ebmResBs);
				}
			}
		}	
		return result;
	}

	private EbmBrdRecord convertEbmBrdRecord(EBMStateResponse ebmStateResponse,final String ebdSrcEbrId){
		final String ebmId=ebmStateResponse.getEBM().getEBMID();
		//根据来源ID和消息ID唯一区分
		Specification<EbmBrdRecord> arg0=new Specification<EbmBrdRecord>() {
			@Override
			public Predicate toPredicate(Root<EbmBrdRecord> arg0,
					CriteriaQuery<?> arg1, CriteriaBuilder arg2) {
				Predicate predicate =arg2.conjunction();
				Predicate condition1=arg2.equal(arg0.get("ebmId"),ebmId);
				Predicate condition2=arg2.equal(arg0.get("resourceId"),ebdSrcEbrId);
				predicate.getExpressions().add(condition1);
				predicate.getExpressions().add(condition2);
				return predicate;
			}
		};
		EbmBrdRecord brdRecord=ebmBrdRecordDAO.findOne(arg0);			
		if(brdRecord==null){
			//查询消息状态
			Ebm ebm=ebmDAO.findOne(ebmId);
			if(ebm==null){
				throw new EbmException(EBDRespResultEnum.contentdamage, "根据消息ID没有找到对应的消息");
			}
			String brdItemId=UUID.randomUUID().toString();		
			brdRecord=new EbmBrdRecord();
			brdRecord.setAreaCode(ebm.getAreaCode());
			brdRecord.setBrdItemId(brdItemId);
			brdRecord.setEbmId(ebmId);
			brdRecord.setEndTime(ebm.getEndTime());
			brdRecord.setEventType(ebm.getEventType());
			brdRecord.setLanguageCode(ebm.getMsgLanguageCode());
			brdRecord.setMsgDesc(ebm.getMsgDesc());
			brdRecord.setMsgTitle(ebm.getMsgTitle());
			brdRecord.setMsgType(ebm.getMsgType());
			brdRecord.setProgramNum(ebm.getProgramNum());
			brdRecord.setResourceId(ebdSrcEbrId);
			brdRecord.setSenderCode(ebm.getSenderCode());
			brdRecord.setSendName(ebm.getSendName());
			brdRecord.setSendTime(ebm.getSendTime());
			brdRecord.setSeverity(ebm.getSeverity());
			brdRecord.setStartTime(ebm.getStartTime());
		}
		//必选
		Integer brdStateCode=ebmStateResponse.getBrdStateCode();
		//必选
		String brdStateDesc=ebmStateResponse.getBrdStateDesc();
		//必选
		Coverage coverage=ebmStateResponse.getCoverage();
		//必选
		String coverageAreaCode=coverage.getAreaCode();
		//必选
		Double coveragePercent=coverage.getCoveragePercent();
		
		brdRecord.setCoverageAreaCode(coverageAreaCode);
		brdRecord.setCoveragePercent(coveragePercent);
		brdRecord.setBrdStateCode(brdStateCode);
		brdRecord.setBrdStateDesc(brdStateDesc);
		
		return brdRecord;
		
		
	}
	
	private List<EbmBrdRecord> getEbmBrdRecordList(final String ebmId){
		Specification<EbmBrdRecord> specification=new Specification<EbmBrdRecord>() {
			@Override
			public Predicate toPredicate(Root<EbmBrdRecord> root,
					CriteriaQuery<?> criteriaquery, CriteriaBuilder criteriabuilder) {
				return criteriabuilder.equal(root.get("ebmId"), ebmId);
			}
		};
		List<EbmBrdRecord> records=ebmBrdRecordDAO.findAll(specification);
		return records;
	}
	
	private Coverage getCoverage(final Integer schemeId,List<EbmBrdRecord> ebmBrdRecords){
		List<SchemeEbr> schemeEbrList=schemeEbrDAO.findBySchemeId(schemeId);
		Map<String,String> ebrIdMap=new HashMap<String,String>(); 
		if(!CollectionUtils.isEmpty(schemeEbrList)){
			for (SchemeEbr schemeEbr : schemeEbrList) {
				String areaCode=schemeEbr.getEbrArea();
				String ebrId=schemeEbr.getEbrId();
				ebrIdMap.put(ebrId, areaCode);
			}
		}
		int coverAreaSize=0;
		StringBuffer arecCodeBuffer=new StringBuffer(50);
		if(!CollectionUtils.isEmpty(ebmBrdRecords)){
			for (EbmBrdRecord ebmBrdRecord : ebmBrdRecords) {
				String ebrId=ebmBrdRecord.getResourceId();
				if(ebrIdMap.containsKey(ebrId)){
					arecCodeBuffer.append(ebrIdMap.get(ebrId)).append(",");
					coverAreaSize++;
				}
			}
		}
		String areaCode=arecCodeBuffer.toString();
		if(areaCode.endsWith(",")){
			areaCode=areaCode.substring(0,areaCode.length()-1);
		}
		Double coveragePercent=0.0;
		int allAreaCodeSize=ebrIdMap.size();
		if(allAreaCodeSize!=0){
			coveragePercent=(double)coverAreaSize/allAreaCodeSize;
		}
		Coverage coverage=new Coverage();
		//实际覆盖区域
		coverage.setAreaCode(areaCode);

		coverage.setCoverageRate(0.99f);

		coverage.setResBrdStat("2,10,10,10"); //实际调用资源响应统计

		//覆盖区域百分百
	//	coverage.setCoveragePercent(coveragePercent);
		return coverage;
	}

	private BroadcastStateEnum getBrdState(final Integer schemeId, List<EbmBrdRecord> ebmBrdRecords) {
		List<SchemeEbr> schemeEbrList = schemeEbrDAO.findBySchemeId(schemeId);
		if (CollectionUtils.isEmpty(schemeEbrList)) {
			return BroadcastStateEnum.noprogress;
		}
		if(ebmBrdRecords==null || ebmBrdRecords.isEmpty()) {
			return BroadcastStateEnum.timetogo;
		}
		List<String> ebrIdList = new ArrayList<String>();
		for (SchemeEbr schemeEbr : schemeEbrList) {
			String ebrId = schemeEbr.getEbrId();
			ebrIdList.add(ebrId);
		}
		for (EbmBrdRecord ebmBrdRecord : ebmBrdRecords) {
			String ebrId = ebmBrdRecord.getResourceId();
			if (ebrIdList.contains(ebrId)) {
				Integer stateCode = ebmBrdRecord.getBrdStateCode();
				if (BroadcastStateEnum.failed.getCode().equals(stateCode)) {
					return BroadcastStateEnum.failed;
				}else if(BroadcastStateEnum.inprogress.getCode().equals(stateCode)) {
					return BroadcastStateEnum.inprogress;
				}else if(BroadcastStateEnum.cancelled.getCode().equals(stateCode)) {
					return BroadcastStateEnum.cancelled;
				}
			}
		}
		return BroadcastStateEnum.succeeded;
	}
}
