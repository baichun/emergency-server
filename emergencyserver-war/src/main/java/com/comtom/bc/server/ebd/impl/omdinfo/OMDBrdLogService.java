package com.comtom.bc.server.ebd.impl.omdinfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.comtom.bc.server.repository.entity.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.common.utils.RegionUtil;
import com.comtom.bc.exchange.commom.BroadcastStateEnum;
import com.comtom.bc.exchange.commom.EBDType;
import com.comtom.bc.exchange.commom.ODMRptType;
import com.comtom.bc.exchange.commom.SyncFlag;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.commom.Coverage;
import com.comtom.bc.exchange.model.ebd.commom.EBRAS;
import com.comtom.bc.exchange.model.ebd.commom.EBRBS;
import com.comtom.bc.exchange.model.ebd.commom.EBRPS;
import com.comtom.bc.exchange.model.ebd.commom.EBRST;
import com.comtom.bc.exchange.model.ebd.commom.Params;
import com.comtom.bc.exchange.model.ebd.details.other.EBM;
import com.comtom.bc.exchange.model.ebd.details.other.EBMBrdLog;
import com.comtom.bc.exchange.model.ebd.details.other.OMDRequest;
import com.comtom.bc.exchange.model.ebd.ebm.EBMBrdItem;
import com.comtom.bc.exchange.model.ebd.ebm.MsgBasicInfo;
import com.comtom.bc.exchange.model.ebd.ebm.MsgContent;
import com.comtom.bc.exchange.model.ebd.ebm.ResBrdInfo;
import com.comtom.bc.exchange.model.ebd.ebm.ResBrdItem;
import com.comtom.bc.exchange.model.ebd.ebm.Unit;
import com.comtom.bc.exchange.model.ebd.ebm.UnitInfo;
import com.comtom.bc.exchange.util.DateStyle;
import com.comtom.bc.exchange.util.DateTimeUtil;
import com.comtom.bc.exchange.util.EBDModelBuild;
import com.comtom.bc.server.ebd.service.AbstractOMDInfoService;
import com.comtom.bc.server.repository.dao.EbmBrdItemUnitDAO;
import com.comtom.bc.server.repository.dao.EbmBrdRecordDAO;
import com.comtom.bc.server.repository.dao.EbmDAO;
import com.comtom.bc.server.repository.dao.EbmResBsDAO;
import com.comtom.bc.server.repository.dao.EbmResDAO;
import com.comtom.bc.server.repository.dao.SchemeEbrDAO;

/**
 * @author nobody
 * 消息播发记录请求数据处理
 */
@Service
public class OMDBrdLogService extends AbstractOMDInfoService{
	
	@Autowired
	private EbmBrdItemUnitDAO ebmBrdItemUnitDAO;
	
	@Autowired
	private EbmResDAO ebmResDAO;
	
	@Autowired
	private EbmResBsDAO ebmResBsDAO;
	
	@Autowired
	private EbmBrdRecordDAO ebmBrdRecordDAO;
	
	@Autowired
	private EbmDAO ebmDAO;
	
	@Autowired
	private SchemeEbrDAO schemeEbrDAO;
	
	@Override
	public String OMDType() {
		return EBDType.EBMBrdLog.name();
	}
	
	@Override
	public EBD service(String relatedEbdId,OMDRequest odmRequest){
		boolean incrementalRpt=false;
		Date rptStartTime=null;
		Date rptEndTime=null;
		Params params=odmRequest.getParams();
		if(params!=null){
			//是否增量
			String rptType=params.getRptType();
			String rptStartTimeStr=params.getRptStartTime();
			String rptEndTimeStr=params.getRptEndTime();
			if(StringUtils.isNotEmpty(rptType)&&(ODMRptType.Incremental.name().equals(rptType))){
				incrementalRpt=true;
			}
			if(StringUtils.isNotEmpty(rptEndTimeStr)){
				rptStartTime=DateTimeUtil.stringToDate(rptStartTimeStr, DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
			}
			if(StringUtils.isNotEmpty(rptEndTimeStr)){
				rptEndTime=DateTimeUtil.stringToDate(rptEndTimeStr, DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
			}
			params = new Params(); //播出记录中不需要 RptType
			params.setRptStartTime(rptStartTimeStr);;
			params.setRptEndTime(rptEndTimeStr);
		}
		
		EBMBrdLog ebmBrdLog = new EBMBrdLog();
		ebmBrdLog.setParams(params);
		final Date rptStartDate=rptStartTime;
		final Date rptEndDate=rptEndTime;
		final boolean incremental=incrementalRpt;
		Specification<EbmBrdRecord> specification=new Specification<EbmBrdRecord>() {
			@Override
			public Predicate toPredicate(Root<EbmBrdRecord> root,
					CriteriaQuery<?> criteriaquery, CriteriaBuilder criteriabuilder) {
				Predicate predicate = criteriabuilder.conjunction();
				if (CommonUtil.isNotEmpty(rptStartDate)) {
					predicate.getExpressions().add(criteriabuilder.greaterThanOrEqualTo(root.<Date> get("updateTime"), rptStartDate));
				}
				if (CommonUtil.isNotEmpty(rptEndDate)) {
					predicate.getExpressions().add(criteriabuilder.lessThanOrEqualTo(root.<Date> get("updateTime"),rptEndDate));
				}
				//增量 查询未同步的数据
				if(incremental){
					predicate.getExpressions().add(criteriabuilder.equal(root.<Integer> get("syncFlag"), SyncFlag.nosync.getValue()));
				}
				return predicate;
			}
		};
		List<EBMBrdItem> ebmBrdList = new ArrayList<EBMBrdItem>();
		List<EbmBrdRecord> ebmList=ebmBrdRecordDAO.findAll(specification);
		if(!CollectionUtils.isEmpty(ebmList)){
			for (EbmBrdRecord ebmBrdRecord : ebmList) {
				EBMBrdItem brdItem=convert(ebmBrdRecord,rptStartDate,rptEndDate);
				ebmBrdList.add(brdItem);
			}
		}
		ebmBrdLog.setDataList(ebmBrdList);
		String ebrId=getEbrPlatFormID();
		String srcURL=getPlatFormUrl();
		String ebdIndex=generateEbdIndex();
		EBD ebdResponse=EBDModelBuild.buildBrdLog(ebrId, srcURL, ebdIndex, ebmBrdLog,relatedEbdId);
		for (EbmBrdRecord object : ebmList) {
			object.setSyncFlag(SyncFlag.sync.getValue());
			ebmBrdRecordDAO.save(object);
		}
		return ebdResponse;
	}

	
	private EBMBrdItem convert(EbmBrdRecord ebmBrdRecord, Date rptStartDate, Date rptEndDate){
		EBMBrdItem brdItm=new EBMBrdItem();
		Ebm ebm = ebmDAO.findOne(ebmBrdRecord.getEbmId());

		EBM ebmIdVO = convertEBM(ebmBrdRecord);
		//ebm对应的播发记录
		List<EbmBrdRecord> ebmBrdRecords=getEbmBrdRecordList(ebm.getEbmId());
		//播发记录对应的人员信息
		UnitInfo unitInfo=convertUnitList(ebmBrdRecord, ebmBrdRecords);
		//根据播发记录统计区域摆放比
		Coverage coverage=getCoverage(ebm.getSchemeId(), ebmBrdRecords);
		//根据播发记录获取播放资源信息
		ResBrdInfo resBrdInfo=converResBrdInfo(ebmBrdRecords, null, null);
		//统计播放结果
		BroadcastStateEnum stateEnum=getBrdState(ebm.getSchemeId(), ebmBrdRecords);
		
		brdItm.setEBM(ebmIdVO);
		brdItm.setBrdStateCode(stateEnum.getCode());
		brdItm.setBrdStateDesc(stateEnum.getBrdstate());
		brdItm.setUnitInfo(unitInfo);
		brdItm.setCoverage(coverage);
		brdItm.setResBrdInfo(resBrdInfo);
		return brdItm;
	}

	private EBM convertEBM(EbmBrdRecord ebmBrdRecord) {
		EBM ebmIdVO = new EBM();
		ebmIdVO.setEBMID(ebmBrdRecord.getEbmId());
		//消息基本信息
		MsgBasicInfo msgBasicInfo=new MsgBasicInfo();
		msgBasicInfo.setMsgType(ebmBrdRecord.getMsgType());
		msgBasicInfo.setSenderName(ebmBrdRecord.getSendName());
		msgBasicInfo.setSenderCode(ebmBrdRecord.getSenderCode());
		Date sendTime=ebmBrdRecord.getSendTime();
		if(sendTime!=null){
			String sendTimeStr=DateTimeUtil.dateToString(sendTime, DateStyle.YYYY_MM_DD_HH_MM_SS);
			msgBasicInfo.setSendTime(sendTimeStr);
		}
		msgBasicInfo.setEventType(ebmBrdRecord.getEventType());
		msgBasicInfo.setSeverity(ebmBrdRecord.getSeverity());
		Date startTime=ebmBrdRecord.getStartTime();
		if(startTime!=null){
			String startTimeStr=DateTimeUtil.dateToString(startTime, DateStyle.YYYY_MM_DD_HH_MM_SS);
			msgBasicInfo.setStartTime(startTimeStr);
		}
		
		Date endTime=ebmBrdRecord.getEndTime();
		if(endTime!=null){
			String endTimeStr=DateTimeUtil.dateToString(endTime, DateStyle.YYYY_MM_DD_HH_MM_SS);
			msgBasicInfo.setEndTime(endTimeStr);
		}
		//TODO  区域转换
		String areaCode=RegionUtil.areaShort2Long(ebmBrdRecord.getAreaCode());
		
		//消息内容
		MsgContent msgContent=new MsgContent();
		msgContent.setLanguageCode(ebmBrdRecord.getLanguageCode());
		msgContent.setMsgTitle(ebmBrdRecord.getMsgTitle());
		msgContent.setMsgDesc(ebmBrdRecord.getMsgDesc());
		msgContent.setAreaCode(areaCode);
		msgContent.setProgramNum(ebmBrdRecord.getProgramNum());
		
		ebmIdVO.setMsgBasicInfo(msgBasicInfo);
		ebmIdVO.setMsgContent(msgContent);
		return ebmIdVO;
	}
	
	private UnitInfo convertUnitList(EbmBrdRecord ebmRecord,List<EbmBrdRecord> ebmBrdRecords){
		UnitInfo unitInfo=new UnitInfo();
		List<Unit> dataList=new ArrayList<Unit>();
		unitInfo.setDataList(dataList);
		if(CollectionUtils.isEmpty(ebmBrdRecords)){
			return unitInfo;
		}
		for (EbmBrdRecord ebmBrdRecord : ebmBrdRecords) {
			//查询记录对应的人员部门信息
			final String brdItemId=ebmBrdRecord.getBrdItemId();
			List<EbmBrdItemUnit> unitList=ebmBrdItemUnitDAO.findAll(new Specification<EbmBrdItemUnit>() {
				@Override
				public Predicate toPredicate(Root<EbmBrdItemUnit> arg0,
						CriteriaQuery<?> arg1, CriteriaBuilder arg2) {
					Predicate condition=arg2.equal(arg0.<String> get("brdItemId"), brdItemId);
					return condition;
				}
			});
			if(CollectionUtils.isEmpty(unitList)){
				continue ;
			}
			for (EbmBrdItemUnit ebmBrdItemUnit : unitList) {
				Unit unit=new Unit();
				EBRPS eBRPS=new EBRPS();
				eBRPS.setEBRID(ebmBrdItemUnit.getEbrpsId());
				unit.setEBRPS(eBRPS);
				unit.setPersonID(ebmBrdItemUnit.getPersionId());
				unit.setPersonName(ebmBrdItemUnit.getPersionName());
				unit.setUnitId(ebmBrdItemUnit.getUnitId());
				unit.setUnitName(ebmBrdItemUnit.getUnitName());
				dataList.add(unit);
			}
		}
		return unitInfo;
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
		float coveragePercent=0;
		int allAreaCodeSize=ebrIdMap.size();
		if(allAreaCodeSize!=0){
			coveragePercent=(float)coverAreaSize/allAreaCodeSize;
		}
		Coverage coverage=new Coverage();
		
		//实际覆盖区域  TODO 为了测试为空则设置一个默认值
		coverage.setAreaCode(StringUtils.defaultIfEmpty(RegionUtil.areaShort2Long(areaCode), "341523000000"));
		//覆盖区域百分百
		//coverage.setCoveragePercent(coveragePercent);
		//  TODO 为了测试设个初始值
		//coveragePercent =  coveragePercent!= 0 ? coveragePercent : 9;
		coverage.setCoverageRate(coveragePercent);
		coverage.setResBrdStat("2,10,10,10"); //实际调用资源响应统计
		return coverage;
	}
	
	private List<ResBrdItem> convertResBrdItems(final String brdItemId,final Date rptStartTimeCon,final Date rptEndTimeCon){
		//调用资源播出数据
		List<ResBrdItem> resBrdItemList=new ArrayList<ResBrdItem>();
		List<EbmRes> ebmResList= ebmResDAO.findAll(new Specification<EbmRes>() {
			@Override
			public Predicate toPredicate(Root<EbmRes> root,
					CriteriaQuery<?> criteriaquery, CriteriaBuilder criteriabuilder) {
				return criteriabuilder.equal(root.get("brdItemId"), brdItemId);
			}
		});
		if(CollectionUtils.isEmpty(ebmResList)){
			return resBrdItemList;
		}
		
		for (EbmRes ebmRes : ebmResList) {
			ResBrdItem resBrdItem=new ResBrdItem();
			EBRAS eBRAS=new EBRAS();
			if(StringUtils.isNotBlank(ebmRes.getEbrasId())) {
				eBRAS.setEBRID(ebmRes.getEbrasId());
				resBrdItem.setEBRAS(eBRAS);
			}
			EBRPS eBRPS=new EBRPS();
			if(StringUtils.isNotBlank(ebmRes.getEbrpsId())) {
				eBRPS.setEBRID(ebmRes.getEbrpsId());
				resBrdItem.setEBRPS(eBRPS);
			}
			EBRST eBRST=new EBRST();
			if(StringUtils.isNotBlank(ebmRes.getEbrstId())){
				eBRST.setEBRID(ebmRes.getEbrstId());
				resBrdItem.setEBRST(eBRST);
			}

			final String ebmResourceId=ebmRes.getEbmResourceId();
			List<EbmResBs> ebmResBsList=ebmResBsDAO.findAll(new Specification<EbmResBs>() {
				@Override
				public Predicate toPredicate(Root<EbmResBs> root,
						CriteriaQuery<?> criteriaquery, CriteriaBuilder criteriabuilder) {
					Predicate predicate = criteriabuilder.conjunction();
					predicate.getExpressions().add(criteriabuilder.equal(root.get("ebmResourceId"), ebmResourceId));
					if (rptStartTimeCon!=null) {
						predicate.getExpressions().add(criteriabuilder.greaterThanOrEqualTo(root.<Date> get("rptTime"), rptStartTimeCon));
					}
					if (rptEndTimeCon!=null) {
						predicate.getExpressions().add(criteriabuilder.lessThanOrEqualTo(root.<Date> get("rptTime"),rptEndTimeCon));
					}
					return predicate;
				}
			});
			List<EBRBS> ebrbsList=new ArrayList<EBRBS>();
			if(!CollectionUtils.isEmpty(ebmResBsList)){
				for (EbmResBs ebmResBs : ebmResBsList) {
					EBRBS ebrbs=new EBRBS();
					Date rptTime=ebmResBs.getRptTime();
					if(rptTime!=null){
						ebrbs.setRptTime(DateTimeUtil.dateToString(rptTime, DateStyle.YYYY_MM_DD_HH_MM_SS));
					}
//					ebrbs.setBrdSysType(ebmResBs.getBrdSysType());
					ebrbs.setBrdSysInfo(ebmResBs.getBrdSysInfo());
					
					Date startTime2=ebmResBs.getStartTime();
					if(startTime2!=null){
						ebrbs.setStartTime(DateTimeUtil.dateToString(startTime2, DateStyle.YYYY_MM_DD_HH_MM_SS));
						
					}
					Date endTime2=ebmResBs.getEndTime();
					if(endTime2!=null){
						ebrbs.setEndTime(DateTimeUtil.dateToString(endTime2, DateStyle.YYYY_MM_DD_HH_MM_SS));
						
					}
					ebrbs.setFileURL(ebmResBs.getFileURL());
					ebrbs.setBrdStateCode(ebmResBs.getBrdStateCode());
					ebrbs.setBrdStateDesc(ebmResBs.getBrdStateDesc());
					ebrbsList.add(ebrbs);
				}
			}
			resBrdItem.setDataList(ebrbsList);
			resBrdItemList.add(resBrdItem);
		}
		return resBrdItemList;
	}

	private ResBrdInfo converResBrdInfo(List<EbmBrdRecord> ebmBrdRecords,final Date rptStartTimeCon,final Date rptEndTimeCon){
		ResBrdInfo resBrdInfo=new ResBrdInfo();
		List<ResBrdItem> dataListAll=new ArrayList<ResBrdItem>();
		resBrdInfo.setDataList(dataListAll);		
		for (EbmBrdRecord ebmBrdRecord : ebmBrdRecords) {
			String brdItemId=ebmBrdRecord.getBrdItemId();
			List<ResBrdItem> resBrdItems=convertResBrdItems(brdItemId,rptStartTimeCon,rptEndTimeCon);
			if(CollectionUtils.isEmpty(resBrdItems)){
				continue ;
			}
			dataListAll.addAll(resBrdItems);
		}
		return resBrdInfo;
	}

	
	private BroadcastStateEnum getBrdState(final Integer schemeId,List<EbmBrdRecord> ebmBrdRecords){
		List<SchemeEbr> schemeEbrList=schemeEbrDAO.findBySchemeId(schemeId);
		if(CollectionUtils.isEmpty(schemeEbrList)){
			return BroadcastStateEnum.timetogo;
		}
		List<String> ebrIdList=new ArrayList<String>();
		for (SchemeEbr schemeEbr : schemeEbrList) {
			String ebrId=schemeEbr.getEbrId();
			ebrIdList.add(ebrId);
		}
		for (EbmBrdRecord ebmBrdRecord : ebmBrdRecords) {
			String ebrId=ebmBrdRecord.getResourceId();
			if(ebrIdList.contains(ebrId)){
				Integer stateCode=ebmBrdRecord.getBrdStateCode();
				if(BroadcastStateEnum.failed.getCode().equals(stateCode)){
					return BroadcastStateEnum.failed;
				}
			}
		}
		return BroadcastStateEnum.succeeded;
	}


	/**
	 * 根据ebm消息id上报指定的播发记录
	 * @param embIds
	 * @return
	 */
	public EBD reportEbmBrLog(String embIds){
		EBMBrdLog ebmBrdLog = new EBMBrdLog();
//		Params params = new Params();
//		params.setRptStartTime(DateTimeUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
//		params.setRptEndTime(DateTimeUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
//		ebmBrdLog.setParams(params);
		List<EBMBrdItem> ebmBrdList = new ArrayList<EBMBrdItem>();
		List<String> embIdList = java.util.Arrays.asList(embIds.split(","));

		List<EbmBrdRecord> ebmList=ebmBrdRecordDAO.findEbmRecordByEmbIds(embIdList);
		if(CollectionUtils.isEmpty(ebmList)){
			return null;
		}
		for (EbmBrdRecord ebm : ebmList) {
			EBMBrdItem brdItem=convert(ebm,null,null);
			ebmBrdList.add(brdItem);
		}
		ebmBrdLog.setDataList(ebmBrdList);
		String eBRID=getEbrPlatFormID();
		String srcURL=getPlatFormUrl();
		String ebdIndex=generateEbdIndex();
		EBD ebdResponse=EBDModelBuild.buildBrdLog(eBRID, srcURL, ebdIndex, ebmBrdLog,null);
		return ebdResponse;
	}

	
	
}
