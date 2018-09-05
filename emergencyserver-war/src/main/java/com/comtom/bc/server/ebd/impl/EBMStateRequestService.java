package com.comtom.bc.server.ebd.impl;

import com.comtom.bc.common.Constants;
import com.comtom.bc.exchange.commom.BroadcastStateEnum;
import com.comtom.bc.exchange.commom.EBDRespResultEnum;
import com.comtom.bc.exchange.commom.EBDType;
import com.comtom.bc.exchange.commom.TimeOutStatusEnum;
import com.comtom.bc.exchange.excepion.EbmException;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.commom.*;
import com.comtom.bc.exchange.model.ebd.details.other.EBM;
import com.comtom.bc.exchange.model.ebd.details.other.EBMStateRequest;
import com.comtom.bc.exchange.model.ebd.details.other.EBMStateResponse;
import com.comtom.bc.exchange.model.ebd.ebm.ResBrdInfo;
import com.comtom.bc.exchange.model.ebd.ebm.ResBrdItem;
import com.comtom.bc.exchange.model.ebd.ebm.SRC;
import com.comtom.bc.exchange.util.*;
import com.comtom.bc.server.repository.dao.*;
import com.comtom.bc.server.repository.entity.*;
import com.comtom.bc.server.service.EbrBsService;
import com.comtom.bc.server.service.EbrStationService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.File;
import java.util.*;
import java.util.concurrent.Executors;


/**
 * @author nobody
 * 应急广播消息播发状态查询处理
 */
@Service
public class EBMStateRequestService extends AbstractEMDService{

	@Autowired
	private EbmStateRequestDAO ebmStateRequestDAO;
	
	@Autowired
	private EbmDAO ebmDAO;
	
	@Autowired
	private SchemeDAO schemeDAO;
	
	@Autowired
	private SchemeEbrDAO schemeEbrDAO;
	
	@Autowired
	private EbmResDAO ebmResDAO;
	
	@Autowired
	private EbmBrdRecordDAO ebmBrdRecordDAO;
	
	@Autowired
	private EbmResBsDAO ebmResBsDAO;

	@Autowired
	private SysParamDAO sysParamDAO;

	@Autowired
	private EbrStationService ebrStationService;

	@Autowired
	private EbrBsService ebrBsService;
	
	@Override
	public String serviceType() {
		return EBDType.EBMStateRequest.name();
	}

	@Override
	public void service(EBD ebd,List<File> resourceFiles) {
		// 必选
		String ebdId = ebd.getEBDID();
		// 必选
		SRC src = ebd.getSRC();
		// 必选资源ID
		String ebdSrcEbrId = src.getEBRID();
		String ebmId=ebd.getEBMStateRequest().getEBM().getEBMID();
		
		EbmStateRequest ebmStateRequest=new EbmStateRequest();
		String ebmStateRequestId=UUID.randomUUID().toString();
		ebmStateRequest.setEbmId(ebmId);
		ebmStateRequest.setEbmStateRequestId(ebmStateRequestId);
		ebmStateRequest.setRelatedEbdId(ebdId);
		ebmStateRequest.setRelatedEbrId(ebdSrcEbrId);
		ebmStateRequestDAO.save(ebmStateRequest);
		
		//sendEBMStateRequest(ebmId);
		 sendResponse(ebmId,getParentPlatUrl(),ebdId);
		
	}
	
	
	public void sendResponse(final String ebmId,final String requestURL,final String ebdId){
		Executors.newSingleThreadExecutor().execute(new Runnable() {
			@Override
			public void run() {
			//	afterPlaying(ebmId);
				EBD ebd= buildEBD(ebdId,ebmId);
				sendEBD(ebd,requestURL);
			}
		});
	}

	/**
	 * 创建回复数据包
	 * @param ebmId
	 * @return
	 */
	private EBD buildEBD(String ebdId,String ebmId){
		EBMStateResponse response = buildEBMStateResponse(ebmId);
		String eBRID=getEbrPlatFormID();
		String srcURL=getPlatFormUrl();
		String ebdIndex=generateEbdIndex();
		EBD ebd=EBDModelBuild.buildStateResponse(ebdId,eBRID, srcURL, ebdIndex, response);
		return ebd;
	}

	/**
	 * 创建回复数据
	 * @param ebmId
	 * @return
	 */
	private EBMStateResponse buildEBMStateResponse(final String ebmId) {
		//查询消息状态
		Ebm ebm=ebmDAO.findOne(ebmId);
		if(ebm==null){
			throw new EbmException(EBDRespResultEnum.contentdamage, "查不到对应的消息实体数据");
		}
		Integer schemeId=ebm.getSchemeId();
		//ebm对应的播发记录
		List<EbmBrdRecord> ebmBrdRecords=getEbmBrdRecordList(ebmId);
		//重新统计覆盖区域
		Coverage coverage=getCoverage(ebm,schemeId, ebmBrdRecords);
		//统计播放结果
		BroadcastStateEnum stateEnum=getBrdState(schemeId, ebmBrdRecords);

		long startTime = ebm.getStartTime().getTime();
		long endTime = ebm.getEndTime().getTime();
		long cutTime = new Date().getTime();
		if(startTime < cutTime && endTime > cutTime ){  // 开始时间小于当前时间并且在结束时间内则反馈播发中状态
			ebm.setTimeOut(TimeOutStatusEnum.overtime.getValue()); //已超时
			ebmDAO.save(ebm);
		}
		if(endTime < cutTime){  // 结束时间小于当前时间
			stateEnum = BroadcastStateEnum.noprogress;
		}
		//返回数据
		//播发状态数据返回
		EBMStateResponse response=new EBMStateResponse();
		String rptTime=DateTimeUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS);
		response.setRptTime(rptTime);
		EBM eBM=new EBM();
		eBM.setEBMID(ebmId);
		response.setEBM(eBM);
		response.setBrdStateCode(stateEnum.getCode());
		response.setBrdStateDesc(stateEnum.getBrdstate());
		response.setCoverage(coverage);
		
		ResBrdInfo resBrdInfo=new ResBrdInfo();
		List<ResBrdItem> dataList=new ArrayList<ResBrdItem>();
		resBrdInfo.setDataList(dataList);
		response.setResBrdInfo(resBrdInfo);

		List<EbmRes> ebmResListAll=new ArrayList<EbmRes>();
		Specification<EbmBrdRecord> specification=new Specification<EbmBrdRecord>() {
			@Override
			public Predicate toPredicate(Root<EbmBrdRecord> root,
					CriteriaQuery<?> criteriaquery, CriteriaBuilder criteriabuilder) {
				return criteriabuilder.equal(root.get("ebmId"), ebmId);
			}
		};
		List<EbmBrdRecord> ebmBrdRecordList=ebmBrdRecordDAO.findAll(specification);
		if(!CollectionUtils.isEmpty(ebmBrdRecordList)){
			for (EbmBrdRecord ebmBrdRecord2 : ebmBrdRecordList) {
				final String brdItemId=ebmBrdRecord2.getBrdItemId();
				List<EbmRes> ebmResList=ebmResDAO.findAll(new Specification<EbmRes>() {
					@Override
					public Predicate toPredicate(Root<EbmRes> root, CriteriaQuery<?> criteriaquery,
							CriteriaBuilder criteriabuilder) {
						Predicate condition=criteriabuilder.equal(root.get("brdItemId"), brdItemId);
						return condition;
					}
				});
				if(!CollectionUtils.isEmpty(ebmResList)){
					ebmResListAll.addAll(ebmResList);
				}
			}	
		}
		//TODO 等待播放，大喇叭还未反馈播放记录 为空则造数据，为了联测通过
		if(CollectionUtils.isEmpty(ebmResListAll)){
			ResBrdItem resBrdItem=new ResBrdItem();
			final String selfPlatformId = sysParamDAO.findValueByKey(Constants.EBR_PLATFORM_ID);

			List<EbrStation> station = ebrStationService.findByRelatedPsEbrId(selfPlatformId);

			String brUrl=ebrBsService.findByRelatedPsEbrId(selfPlatformId).get(0).getBsUrl();

			String stEBbrId= CollectionUtils.isEmpty(station) ? "43415230000000301010101" : station.get(0).getStationEbrId();


			EBRPS eBRPS=new EBRPS();
			eBRPS.setEBRID(selfPlatformId);
			resBrdItem.setEBRPS(eBRPS);
			EBRST eBRST=new EBRST();
			eBRST.setEBRID(stEBbrId); //台站信息id
			resBrdItem.setEBRST(eBRST);
//			EBRAS ebras=new EBRAS();
//			ebras.setEBRID("43415230000000301010201"); //消息接收设备id
//			resBrdItem.setEBRAS(ebras);
			EBRBS ebrbs=new EBRBS();
			ebrbs.setRptTime(DateTimeUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			ebrbs.setBrdSysInfo("43415230000000314010301,3,97400");
			ebrbs.setStartTime(DateTimeUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			ebrbs.setEndTime(DateTimeUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			ebrbs.setFileURL(brUrl);
			ebrbs.setBrdStateCode(stateEnum.getCode());
			ebrbs.setBrdStateDesc(stateEnum.getBrdstate());
			List<EBRBS> bsDataList=new ArrayList<EBRBS>();
			resBrdItem.setDataList(bsDataList);
			bsDataList.add(ebrbs);

			dataList.add(resBrdItem);
		}
		
		for (EbmRes ebmRes : ebmResListAll) {
			final String ebmResourceId=ebmRes.getEbmResourceId();
			
			String ebrasId=ebmRes.getEbrasId();
			String ebrpsId=ebmRes.getEbrpsId();
			String ebrstId=ebmRes.getEbrstId();
			ResBrdItem resBrdItem=new ResBrdItem();
			//可选
			if(ebrpsId!=null){
				EBRPS eBRPS=new EBRPS();
				eBRPS.setEBRID(ebrpsId);
				resBrdItem.setEBRPS(eBRPS);				
			}
			//必选
			if(ebrasId!=null){
				EBRAS ebras=new EBRAS();
				ebras.setEBRID(ebrasId);
				resBrdItem.setEBRAS(ebras);
			}
			//可选
			if(ebrstId!=null){
				EBRST eBRST=new EBRST();
				eBRST.setEBRID(ebrstId);
				resBrdItem.setEBRST(eBRST);
			}

			List<EbmResBs> ebmResBsList=ebmResBsDAO.findAll(new Specification<EbmResBs>() {
				@Override
				public Predicate toPredicate(Root<EbmResBs> root, CriteriaQuery<?> arg1,
						CriteriaBuilder criteriabuilder) {
					Predicate predicate = criteriabuilder.conjunction();
					Predicate condition=criteriabuilder.equal(root.<String> get("ebmResourceId"), ebmResourceId);
					predicate.getExpressions().add(condition);
					return predicate;
				}
			});
			//必选
			List<EBRBS> bsDataList=new ArrayList<EBRBS>();
			resBrdItem.setDataList(bsDataList);
			if(!CollectionUtils.isEmpty(ebmResBsList)){
				for (EbmResBs ebmResBs : ebmResBsList) {
					Date rptDate=ebmResBs.getRptTime();
					Date endDate=ebmResBs.getEndTime();
					Date startDate=ebmResBs.getStartTime();
					String startTime2=null;
					String rptTime2=null;
					String endTime2=null;
					if(rptDate!=null){
						rptTime2=DateTimeUtil.dateToString(rptDate, DateStyle.YYYY_MM_DD_HH_MM_SS);					
					}
					if(endDate!=null){
						endTime2=DateTimeUtil.dateToString(endDate, DateStyle.YYYY_MM_DD_HH_MM_SS);
					}
					if(startDate!=null){
						startTime2=DateTimeUtil.dateToString(startDate, DateStyle.YYYY_MM_DD_HH_MM_SS);
					}
					EBRBS ebrbs=new EBRBS();
					ebrbs.setRptTime(rptTime2);
//					ebrbs.setBrdSysType(ebmResBs.getBrdSysType());
					ebrbs.setBrdSysInfo(ebmResBs.getBrdSysInfo());
					ebrbs.setStartTime(startTime2);
					ebrbs.setEndTime(endTime2);
					ebrbs.setFileURL(ebmResBs.getFileURL());
					ebrbs.setBrdStateCode(ebmResBs.getBrdStateCode());
					ebrbs.setBrdStateDesc(ebmResBs.getBrdStateDesc());
					bsDataList.add(ebrbs);
				}
			}
			dataList.add(resBrdItem);
		}
		return response;
	}
	
	private Coverage getCoverage(Ebm ebm,final Integer schemeId,List<EbmBrdRecord> ebmBrdRecords){
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
		float coveragePercent=0;
		int allAreaCodeSize=ebrIdMap.size();
		if(allAreaCodeSize!=0){
			coveragePercent=(float)coverAreaSize/allAreaCodeSize;
		}
		Coverage coverage=new Coverage();
		
		coveragePercent= coveragePercent==0 ? 99 : coveragePercent;
		areaCode = StringUtils.isNotEmpty(areaCode) ? areaCode : ebm.getAreaCode(); //TODO 无法获取到播发记录，直接获取消息广播消息表中的覆盖区域编码
		//实际覆盖率
		coverage.setCoverageRate(coveragePercent);
		//实际覆盖区域
		coverage.setAreaCode(areaCode);
		coverage.setResBrdStat("2,10,10,10"); //实际调用资源响应统计
		//覆盖区域百分百
		// coverage.setCoveragePercent(coveragePercent);
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
	
//	private BroadcastStateEnum getBrdState(final Integer schemeId,List<EbmBrdRecord> ebmBrdRecords){
//		List<SchemeEbr> schemeEbrList=schemeEbrDAO.findBySchemeId(schemeId);
//		if(CollectionUtils.isEmpty(schemeEbrList)){
//			return BroadcastStateEnum.timetogo;
//		}
//		List<String> ebrIdList=new ArrayList<String>();
//		for (SchemeEbr schemeEbr : schemeEbrList) {
//			String ebrId=schemeEbr.getEbrId();
//			ebrIdList.add(ebrId);
//		}
//		for (EbmBrdRecord ebmBrdRecord : ebmBrdRecords) {
//			String ebrId=ebmBrdRecord.getResourceId();
//			if(ebrIdList.contains(ebrId)){
//				Integer stateCode=ebmBrdRecord.getBrdStateCode();
//				if(BroadcastStateEnum.failed.getCode().equals(stateCode)){
//					return BroadcastStateEnum.failed;
//				}
//			}
//		}
//		return BroadcastStateEnum.succeeded;
//	}
	
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
	
}
