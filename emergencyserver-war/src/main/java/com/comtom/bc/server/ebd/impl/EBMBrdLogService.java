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
import javax.transaction.Transactional;

import com.comtom.bc.exchange.commom.SyncFlag;
import com.comtom.bc.exchange.model.ebd.commom.*;
import com.comtom.bc.server.ebd.impl.omdinfo.OMDBrdLogService;
import com.comtom.bc.server.repository.entity.*;
import com.comtom.bc.server.req.ResourceLoadReq;
import com.comtom.bc.server.service.EbrAdaptorService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.comtom.bc.dispatch.FlowConstants;
import com.comtom.bc.exchange.commom.BroadcastStateEnum;
import com.comtom.bc.exchange.commom.EBDType;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.details.other.EBM;
import com.comtom.bc.exchange.model.ebd.details.other.EBMBrdLog;
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
import com.comtom.bc.server.repository.dao.EbmBrdItemUnitDAO;
import com.comtom.bc.server.repository.dao.EbmBrdRecordDAO;
import com.comtom.bc.server.repository.dao.EbmDAO;
import com.comtom.bc.server.repository.dao.EbmResBsDAO;
import com.comtom.bc.server.repository.dao.EbmResDAO;
import com.comtom.bc.server.repository.dao.SchemeEbrDAO;
import com.comtom.bc.server.service.DispatchFlowService;
import com.comtom.bc.server.service.EbmBrdLogService;

/**
 * @author nobody 接收应急广播消息播发记录处理
 */
@Service
public class EBMBrdLogService extends AbstractEMDService implements EbmBrdLogService {
	
	/**
	 * 日志记录器
	 */
	private static final Logger logger = LoggerFactory.getLogger(AbstractEMDService.class);

	@Autowired
	private DispatchFlowService dispatchFlowService;

	@Autowired
	private EbmBrdRecordDAO ebmBrdRecordDAO;

	@Autowired
	private EbmBrdItemUnitDAO ebmBrdItemUnitDAO;

	@Autowired
	private EbmResDAO ebmResDAO;

	@Autowired
	private EbmResBsDAO ebmResBsDAO;

	@Autowired
	private SchemeEbrDAO schemeEbrDAO;

	@Autowired
	private EbmDAO ebmDAO;

	@Autowired
	private OMDBrdLogService brdLogService;

	@Autowired
	private EbrAdaptorService ebrAdaptorService;

	@Override
	public String serviceType() {
		return EBDType.EBMBrdLog.name();
	}

	@Override
	@Transactional
	public void service(EBD ebd, List<File> resourceFiles) {
		// 数据来源的资源ID
		final String resourceId = ebd.getSRC().getEBRID();
		// 必选
		final EBMBrdLog ebmBrdLog = ebd.getEBMBrdLog();

		// 播放记录结果集
		List<EbmBrdRecord> ebmBrdRecords = new ArrayList<EbmBrdRecord>();
		// 播发记录关联人员集合
		List<EbmBrdItemUnit> ebmBrdItemUnits = new ArrayList<EbmBrdItemUnit>();
		// 播放记录关联资源集合
		List<EbmRes> ebmResList = new ArrayList<EbmRes>();
		// 播放记录关联资源对于的播出系统集合
		List<EbmResBs> ebmResBsList = new ArrayList<EbmResBs>();
		// 必选
		List<EBMBrdItem> ebmBrdItems = ebmBrdLog.getDataList();
		for (EBMBrdItem ebmBrdItem : ebmBrdItems) {

			EbmBrdRecord brdRecord = convert(ebmBrdItem, resourceId);
			ebmBrdRecords.add(brdRecord);
			String brdItemId = brdRecord.getBrdItemId();

			List<EbmBrdItemUnit> brdItemUnits = convertBrdItemUnits(ebmBrdItem, brdItemId);
			if (!CollectionUtils.isEmpty(brdItemUnits)) {
				ebmBrdItemUnits.addAll(brdItemUnits);
			}

			List<EbmRes> resList = convertEbmResList(ebmBrdItem, brdItemId, ebmResBsList);
			if (!CollectionUtils.isEmpty(resList)) {
				ebmResList.addAll(resList);
			}
		}
		if (!CollectionUtils.isEmpty(ebmBrdRecords)) {
			// 保存播发记录
			ebmBrdRecordDAO.save(ebmBrdRecords);
		}
		if (!CollectionUtils.isEmpty(ebmBrdItemUnits)) {
			// 保存播发记录关联人员信息
			ebmBrdItemUnitDAO.save(ebmBrdItemUnits);
		}
		if (!CollectionUtils.isEmpty(ebmResList)) {
			// 保存播发记录关联资源信息
			ebmResDAO.save(ebmResList);
		}
		if (!CollectionUtils.isEmpty(ebmResBsList)) {
			// 保存播发记录关联资源的播出系统信息
			ebmResBsDAO.save(ebmResBsList);
		}

		EBMBrdItem ebmBrdItem = ebmBrdLog.getDataList().get(0);
		String ebmId = ebmBrdItem.getEBM().getEBMID();

		// 获取调度流程编号
		Ebm ebm = ebmDAO.findOne(ebmId);
		Long flowId = ebm.getFlowId();
		Integer stateCode = ebmBrdItem.getBrdStateCode();

		// 上报播放记录，更新流程状态（预警发布 或 预警完成）
		if (stateCode.equals(BroadcastStateEnum.inprogress.getCode())) {
			// 更新流程
			dispatchFlowService.updateFlow(flowId, FlowConstants.STAGE_PROCESS,
					FlowConstants.STATE_MSG_RELEASE);
		} else if (stateCode.equals(BroadcastStateEnum.succeeded.getCode())) {
			// 更新流程
			dispatchFlowService.updateFlow(flowId, FlowConstants.STAGE_COMPLETE,
					FlowConstants.STATE_COMPLETE);
		}

		// 不主动上报播发记录
//		Executors.newSingleThreadExecutor().execute(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					// 统计当前系统的消息播发记录并上报上级系统
//					reportBrdItem(ebmBrdLog);
//				} catch (Exception e) {
//					logger.warn("上报播放状态出错", e);
//				}
//			}
//		});

	}

	private void reportBrdItem(EBMBrdLog ebmBrdLog) {
		String parentPlatUrl = getParentPlatUrl();
		if (StringUtils.isEmpty(parentPlatUrl)) {
			return;
		}
		List<EBMBrdItem> ebmBrdItems = ebmBrdLog.getDataList();
		if (CollectionUtils.isEmpty(ebmBrdItems)) {
			return;
		}
		// 重新统计播放状态和覆盖区域
		for (EBMBrdItem eBMBrdItem : ebmBrdItems) {
			String ebmId = eBMBrdItem.getEBM().getEBMID();
			Ebm ebm = ebmDAO.findOne(ebmId);
			if (ebm == null) {
				continue;
			}
			Integer schemeId = ebm.getSchemeId();
			// ebm对应的播发记录
			List<EbmBrdRecord> ebmBrdRecords = getEbmBrdRecordList(ebmId);
			// 重新统计覆盖区域
			Coverage coverage = getCoverage(schemeId, ebmBrdRecords);
			// 统计播放结果
			BroadcastStateEnum stateEnum = getBrdState(schemeId, ebmBrdRecords);
			eBMBrdItem.setBrdStateCode(stateEnum.getCode());  //TODO 播出状态
			eBMBrdItem.setBrdStateDesc(stateEnum.getBrdstate());
			eBMBrdItem.setCoverage(coverage);
		}
		String ebrId = getEbrPlatFormID();
		String srcUrl = getPlatFormUrl();
		String ebdIndex = generateEbdIndex();
		EBD ebd = EBDModelBuild.buildBrdLog(ebrId, srcUrl, ebdIndex, ebmBrdLog,null);
		sendEBD(ebd, parentPlatUrl);
	}

	private EbmBrdRecord convert(EBMBrdItem ebmBrdItem, final String resourceId) {
		// 必选
		EBM ebm = ebmBrdItem.getEBM();
		// 必选消息ID
		final String ebmId = ebm.getEBMID();
		// 可选
		MsgBasicInfo msgBasicInfo = ebm.getMsgBasicInfo();
		Integer msgType = null;
		String sendName = null;
		String senderCode = null;

		Date sendTime = null;
		String eventType = null;
		Date startTime = null;
		Date endTime = null;
		Integer severity = null;
		if (msgBasicInfo != null) {
			msgType = msgBasicInfo.getMsgType();
			sendName = msgBasicInfo.getSenderName();
			senderCode = msgBasicInfo.getSenderCode();
			String sendTimeStr = msgBasicInfo.getSendTime();
			sendTime = DateTimeUtil.stringToDate(sendTimeStr,
					DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
			eventType = msgBasicInfo.getEventType();
			severity = msgBasicInfo.getSeverity();
			String startTimeStr = msgBasicInfo.getStartTime();
			startTime = DateTimeUtil.stringToDate(startTimeStr,
					DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
			String endTimeStr = msgBasicInfo.getEndTime();
			endTime = DateTimeUtil.stringToDate(endTimeStr,
					DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
		}
		// 必选
		MsgContent msgContent = ebm.getMsgContent();
		String areaCode = msgContent.getAreaCode();
		String languageCode = msgContent.getLanguageCode();
		String msgDesc = msgContent.getMsgDesc();
		String msgTitle = msgContent.getMsgTitle();
		// 可选
		Integer programNum = msgContent.getProgramNum();
		// 必选
		Integer brdStateCode = ebmBrdItem.getBrdStateCode();
		String brdStateDesc = ebmBrdItem.getBrdStateDesc();
		// 可选
		Coverage coverage = ebmBrdItem.getCoverage();
		String coverageAreaCode = null;
		Double coveragePercent = null;
		if (coverage != null) {
			// 必选
			coveragePercent = coverage.getCoveragePercent();
			// 必选
			coverageAreaCode = coverage.getAreaCode();
		}
		// 根据来源ID和消息ID唯一区分
		Specification<EbmBrdRecord> arg0 = new Specification<EbmBrdRecord>() {
			@Override
			public Predicate toPredicate(Root<EbmBrdRecord> arg0, CriteriaQuery<?> arg1,
					CriteriaBuilder arg2) {
				Predicate predicate = arg2.conjunction();
				Predicate condition1 = arg2.equal(arg0.get("ebmId"), ebmId);
				Predicate condition2 = arg2.equal(arg0.get("resourceId"), resourceId);
				predicate.getExpressions().add(condition1);
				predicate.getExpressions().add(condition2);
				return predicate;
			}
		};
		EbmBrdRecord brdRecord = ebmBrdRecordDAO.findOne(arg0);
		if (brdRecord == null) {
			// 新增
			brdRecord = new EbmBrdRecord();
			brdRecord.setBrdItemId(UUID.randomUUID().toString());
		}
		//TODO 大喇叭未对msgType消息类型,eventType(事件类型编码)severity(事件级别)等数据做保存，这里重新从ebm表中获取
		Ebm ebmDao=ebmDAO.findOne(ebmId);
		if(ebmDao!=null){
			msgType=ebmDao.getMsgType();
			eventType= ebmDao.getEventType();
			sendName = ebmDao.getSendName();
			senderCode = ebmDao.getSenderCode();
			severity = ebmDao.getSeverity();
		}
		// 修改
		brdRecord.setResourceId(resourceId);
		brdRecord.setMsgType(msgType);
		brdRecord.setEbmId(ebmId);
		brdRecord.setSendName(sendName);
		brdRecord.setSenderCode(senderCode);
		brdRecord.setSendTime(sendTime);
		brdRecord.setEventType(eventType);
		brdRecord.setSeverity(severity);
		brdRecord.setStartTime(startTime);
		brdRecord.setEndTime(endTime);
		brdRecord.setLanguageCode(languageCode);
		brdRecord.setMsgTitle(msgTitle);
		brdRecord.setMsgDesc(msgDesc);
		brdRecord.setAreaCode(areaCode);
		brdRecord.setProgramNum(programNum);
		brdRecord.setBrdStateCode(brdStateCode);
		brdRecord.setBrdStateDesc(brdStateDesc);
		brdRecord.setCoverageAreaCode(coverageAreaCode);
		brdRecord.setCoveragePercent(coveragePercent);
		brdRecord.setMsgTitle(msgTitle);
		brdRecord.setUpdateTime(new Date());
		brdRecord.setSyncFlag(SyncFlag.nosync.getValue());
		return brdRecord;
	}

	private List<EbmBrdItemUnit> convertBrdItemUnits(EBMBrdItem ebmBrdItem, final String brdItemId) {
		List<EbmBrdItemUnit> brdItemUnits = new ArrayList<EbmBrdItemUnit>();
		UnitInfo unitInfo = ebmBrdItem.getUnitInfo();
		if (unitInfo == null) {
			return brdItemUnits;
		}
		List<Unit> unitList = unitInfo.getDataList();
		if (CollectionUtils.isEmpty(unitList)) {
			return brdItemUnits;
		}
		for (Unit unit : unitList) {
			// 必选 播发部门ID
			final String unitId = unit.getUnitId();
			// 必选 播发部门名称
			String unitName = unit.getUnitName();
			// 必选 播发部门名称
			final String persionId = unit.getPersonID();
			// 必选 播发部门名称
			String persionName = unit.getPersonName();
			// 必选 应急广播平台ID
			String ebrpsId = unit.getEBRPS().getEBRID();

			Specification<EbmBrdItemUnit> specification = new Specification<EbmBrdItemUnit>() {
				@Override
				public Predicate toPredicate(Root<EbmBrdItemUnit> root,
						CriteriaQuery<?> criteriaquery, CriteriaBuilder criteriabuilder) {
					Predicate predicate = criteriabuilder.conjunction();
					Predicate condition1 = criteriabuilder.equal(root.get("brdItemId"), brdItemId);
					Predicate condition2 = criteriabuilder.equal(root.get("unitId"), unitId);
					predicate.getExpressions().add(condition1);
					predicate.getExpressions().add(condition2);
					return predicate;
				}
			};
			EbmBrdItemUnit ebmBrdItemUnit = ebmBrdItemUnitDAO.findOne(specification);
			if (ebmBrdItemUnit != null) {
				continue;
			}
			EbmBrdItemUnit brdItemUnit = new EbmBrdItemUnit();
			brdItemUnit.setBrdItemId(brdItemId);
			brdItemUnit.setEbrpsId(ebrpsId);
			brdItemUnit.setPersionId(persionId);
			brdItemUnit.setPersionName(persionName);
			brdItemUnit.setUnitId(unitId);
			brdItemUnit.setUnitName(unitName);
			brdItemUnits.add(brdItemUnit);
		}
		return brdItemUnits;
	}

	private List<EbmRes> convertEbmResList(EBMBrdItem ebmBrdItem, final String brdItemId,
			List<EbmResBs> ebmResBs) {
		List<EbmRes> ebmResList = new ArrayList<EbmRes>();
		// 可选 调用资源播出信息
		ResBrdInfo resBrdInfo = ebmBrdItem.getResBrdInfo();
		if (resBrdInfo == null) {
			return ebmResList;
		}
		List<ResBrdItem> brdItems = resBrdInfo.getDataList();
		if (CollectionUtils.isEmpty(brdItems)) {
			return ebmResList;
		}
		for (ResBrdItem resBrdItem : brdItems) {
			// 可选
			EBRPS ebrps = resBrdItem.getEBRPS();
			String ebrpsId = null;
			if (ebrps != null) {
				ebrpsId = ebrps.getEBRID();
			}
			// 可选
			EBRST ebrst = resBrdItem.getEBRST();
			String ebrstId = null;
			if (ebrst != null) {
				ebrstId = ebrst.getEBRID();
			}
			// 必选
			EBRAS ebras = resBrdItem.getEBRAS();
			String ebrasId = null;
			if (ebras != null) {
				ebrasId = ebras.getEBRID();
			}else{
				//TODO 目前没有接收设备，协议里面必填，只能造一条数据
				List<EbrAdaptor> pageList = ebrAdaptorService.search(new ResourceLoadReq());
				if(!CollectionUtils.isEmpty(pageList)){
					ebrasId = pageList.get(0).getAsEbrId();
				}else{
					ebrasId= "43415230000000301020201";
				}
			}
			final String tempEbrPsId = ebrpsId;
			Specification<EbmRes> specification = new Specification<EbmRes>() {
				@Override
				public Predicate toPredicate(Root<EbmRes> root, CriteriaQuery<?> criteriaquery,
						CriteriaBuilder criteriabuilder) {
					Predicate predicate = criteriabuilder.conjunction();
					Predicate condition1 = criteriabuilder.equal(root.get("brdItemId"), brdItemId);
					predicate.getExpressions().add(condition1);
					if (tempEbrPsId != null) {
						Predicate condition2 = criteriabuilder.equal(root.get("ebrpsId"),
								tempEbrPsId);
						predicate.getExpressions().add(condition2);
					}
					return predicate;
				}

			};
			EbmRes ebmRes = ebmResDAO.findOne(specification);
			if (ebmRes == null) {
				// 生成记录关联资源信息
				ebmRes = new EbmRes();
				ebmRes.setEbmResourceId(UUID.randomUUID().toString());
				ebmRes.setBrdItemId(brdItemId);

				ebmRes.setEbrasId(ebrasId);
				ebmRes.setEbrpsId(ebrpsId);
				ebmRes.setEbrstId(ebrstId);
				ebmResList.add(ebmRes);
			}
			final String ebmResourceId = ebmRes.getEbmResourceId();
			List<EbmResBs> resBs = convertEbmResBsList(resBrdItem, ebmResourceId);
			if (!CollectionUtils.isEmpty(resBs)) {
				ebmResBs.addAll(resBs);
			}
		}
		return ebmResList;
	}

	private List<EbmResBs> convertEbmResBsList(ResBrdItem resBrdItem, final String ebmResourceId) {
		List<EbmResBs> ebmResBsList = new ArrayList<EbmResBs>();
		// 播出系统详情 必选
		List<EBRBS> ebrbsList = resBrdItem.getDataList();
		if (CollectionUtils.isEmpty(ebrbsList)) {
			return ebmResBsList;
		}
		for (EBRBS ebrbs : ebrbsList) {
			String rptTimeStr = ebrbs.getRptTime();
			final Date rptTime = DateTimeUtil.stringToDate(rptTimeStr,
					DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
//			String brdSysType = ebrbs.getBrdSysType();
			final String brdSysInfo = ebrbs.getBrdSysInfo();
			String startTimeStr = ebrbs.getStartTime();
			Date startTime = DateTimeUtil.stringToDate(startTimeStr,
					DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
			String endTimeStr = ebrbs.getEndTime();
			Date endTime = DateTimeUtil.stringToDate(endTimeStr,
					DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());

			// 存放播音文件的网络地址
			String fileURL = ebrbs.getFileURL();
			Integer brdStateCode = ebrbs.getBrdStateCode();
			String brdStateDesc = ebrbs.getBrdStateDesc();

			Specification<EbmResBs> specification = new Specification<EbmResBs>() {
				@Override
				public Predicate toPredicate(Root<EbmResBs> root, CriteriaQuery<?> criteriaquery,
						CriteriaBuilder criteriabuilder) {
					Predicate predicate = criteriabuilder.conjunction();
					Predicate condition1 = criteriabuilder.equal(root.get("ebmResourceId"),
							ebmResourceId);
					Predicate condition2 = criteriabuilder
							.equal(root.get("brdSysInfo"), brdSysInfo);
					predicate.getExpressions().add(condition1);
					predicate.getExpressions().add(condition2);
					return predicate;
				}
			};
			EbmResBs ebmResBs = ebmResBsDAO.findOne(specification);
			if (ebmResBs == null) {
				ebmResBs = new EbmResBs();
				ebmResBs.setBrdStateCode(brdStateCode);
				ebmResBs.setBrdStateDesc(brdStateDesc);
				ebmResBs.setBrdSysInfo(brdSysInfo);
			//	ebmResBs.setBrdSysType("");
				ebmResBs.setEndTime(endTime);
				ebmResBs.setFileURL(fileURL);
				ebmResBs.setEbmResourceId(ebmResourceId);
				ebmResBs.setRptTime(rptTime);
				ebmResBs.setStartTime(startTime);
				ebmResBsList.add(ebmResBs);
			} else {
				ebmResBs.setBrdStateCode(brdStateCode);
				ebmResBs.setBrdStateDesc(brdStateDesc);
				ebmResBsList.add(ebmResBs);
			}
		}
		return ebmResBsList;
	}

	private Coverage getCoverage(final Integer schemeId, List<EbmBrdRecord> ebmBrdRecords) {
		List<SchemeEbr> schemeEbrList = schemeEbrDAO.findBySchemeId(schemeId);
		Map<String, String> ebrIdMap = new HashMap<String, String>();
		if (!CollectionUtils.isEmpty(schemeEbrList)) {
			for (SchemeEbr schemeEbr : schemeEbrList) {
				String areaCode = schemeEbr.getEbrArea();
				String ebrId = schemeEbr.getEbrId();
				ebrIdMap.put(ebrId, areaCode);
			}
		}
		int coverAreaSize = 0;
		StringBuffer arecCodeBuffer = new StringBuffer(50);
		if (!CollectionUtils.isEmpty(ebmBrdRecords)) {
			for (EbmBrdRecord ebmBrdRecord : ebmBrdRecords) {
				String ebrId = ebmBrdRecord.getResourceId();
				if (ebrIdMap.containsKey(ebrId)) {
					arecCodeBuffer.append(ebrIdMap.get(ebrId)).append(",");
					coverAreaSize++;
				}
			}
		}
		String areaCode = arecCodeBuffer.toString();
		if (areaCode.endsWith(",")) {
			areaCode = areaCode.substring(0, areaCode.length() - 1);
		}
		float coveragePercent = 0;
		int allAreaCodeSize = ebrIdMap.size();
		if (allAreaCodeSize != 0) {
			coveragePercent = (float) coverAreaSize / allAreaCodeSize;
		}
		Coverage coverage = new Coverage();
		// 实际覆盖区域  TODO 为了测试为空,则给个默认值
		coverage.setAreaCode(StringUtils.defaultString(areaCode, "000000000000"));
		// 覆盖区域百分百
		coverage.setCoverageRate(coveragePercent);

		coverage.setResBrdStat("2,10,10,10"); //实际调用资源响应统计

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

	private List<EbmBrdRecord> getEbmBrdRecordList(final String ebmId) {
		Specification<EbmBrdRecord> specification = new Specification<EbmBrdRecord>() {
			@Override
			public Predicate toPredicate(Root<EbmBrdRecord> root, CriteriaQuery<?> criteriaquery,
					CriteriaBuilder criteriabuilder) {
				return criteriabuilder.equal(root.get("ebmId"), ebmId);
			}
		};
		List<EbmBrdRecord> records = ebmBrdRecordDAO.findAll(specification);
		return records;
	}
	
	
	/** 供restApi使用
	 * @param ebmId
	 * @return
	 */
	public BroadcastStateEnum getBrdState(final String ebmId) {
		Ebm ebm = ebmDAO.findOne(ebmId);
		Integer schemeId = ebm.getSchemeId();
		List<EbmBrdRecord> ebmBrdRecords=getEbmBrdRecordList(ebmId);
		return getBrdState(schemeId, ebmBrdRecords);
	}


	/**
	 * 根据ebm消息id上报指定的播发记录
	 * @param embIds
	 */
	public void reportEbmBrLog(String embIds){
		final String parentPlatUrl = getParentPlatUrl();
		final EBD ebd=brdLogService.reportEbmBrLog(embIds);
		if(ebd == null ){
			logger.info("生成指定播发记录数据包失败 : "+ embIds);
			return;
		}
		Executors.newSingleThreadExecutor().execute(new Runnable() {
			@Override
			public void run() {
				try {
					sendEBD(ebd, parentPlatUrl);
				} catch (Exception e) {
					logger.warn("上报指定的播发记录出错", e.getMessage());
				}
			}
		});
	}


}
