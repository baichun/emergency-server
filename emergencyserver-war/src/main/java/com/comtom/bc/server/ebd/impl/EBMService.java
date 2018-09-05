package com.comtom.bc.server.ebd.impl;

import com.comtom.bc.common.Constants;
import com.comtom.bc.dispatch.FlowConstants;
import com.comtom.bc.exchange.commom.EBDType;
import com.comtom.bc.exchange.commom.SendFlag;
import com.comtom.bc.exchange.commom.TimeOutStatusEnum;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.details.other.EBM;
import com.comtom.bc.exchange.model.ebd.ebm.Auxiliary;
import com.comtom.bc.exchange.model.ebd.ebm.MsgBasicInfo;
import com.comtom.bc.exchange.model.ebd.ebm.MsgContent;
import com.comtom.bc.exchange.model.ebd.ebm.RelatedInfo;
import com.comtom.bc.exchange.util.DateStyle;
import com.comtom.bc.exchange.util.DateTimeUtil;
import com.comtom.bc.server.repository.dao.EbdFileDAO;
import com.comtom.bc.server.repository.dao.EbmAuxiliaryDAO;
import com.comtom.bc.server.repository.dao.EbmDAO;
import com.comtom.bc.server.repository.dao.EbmDispatchDAO;
import com.comtom.bc.server.repository.entity.*;
import com.comtom.bc.server.service.DispatchFlowService;
import com.comtom.bc.server.service.InfoAccessService;
import com.comtom.bc.server.service.SchemeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author nobody 应急消息服务处理类
 */
@Service
public class EBMService extends AbstractEMDService {

	/**
	 * 日志记录器
	 */
	private static final Logger logger = LoggerFactory.getLogger(EBMService.class);

	@Autowired
	private DispatchFlowService dispatchFlowService;

	@Autowired
	private SchemeService schemeService;

	@Autowired
	private EbmDAO ebmDAO;

	@Autowired
	private EbdFileDAO ebdFileDAO;

	@Autowired
	private EbmDispatchDAO ebmDispatchDAO;

	@Autowired
	private EbmAuxiliaryDAO ebmAuxiliaryDAO;

	@Autowired
	private EBMStateRequestService eBMStateRequestService;

	@Autowired
	private InfoAccessService infoAccessService;

	@Override
	public String serviceType() {
		return EBDType.EBM.name();
	}

	@Override
	@Transactional
	public void service(EBD ebd, List<File> resourceFiles) {

		// 创建流程
		DispatchFlow dispatchFlow = dispatchFlowService.createFlow(null, ebd.getEBDID(), FlowConstants.STAGE_STARTING,
				FlowConstants.STATE_INFO_AUDIT_YES);

		// 带辅助数据（媒体）文件的，保存文件记录
		recordEbdFiles(ebd, resourceFiles);

		EBM ebm = ebd.getEBM();
		// 必选
		String ebmVersion = ebm.getEBMVersion();
		// 必选
		String ebmId = ebm.getEBMID();

		// 可选
		RelatedInfo relatedInfo = ebm.getRelatedInfo();
		String relatedEbIId = null;
		String relatedEbmId = null;
		if (relatedInfo != null) {
			// 可选
			relatedEbIId = relatedInfo.getEBIID();
			// 可选
			relatedEbmId = relatedInfo.getEBMID();
		}
		// 必选
		MsgBasicInfo msgBasicInfo = ebm.getMsgBasicInfo();

		Ebm ebmEntity = new Ebm();

		Integer msgType = null;
		String sendName = null;
		String senderCode = null;
		Date sendTime = null;
		String eventType = null;
		Date startTime = null;
		Date endTime = null;
		Integer severity = null;

		msgType = msgBasicInfo.getMsgType();
		sendName = msgBasicInfo.getSenderName();
		senderCode = msgBasicInfo.getSenderCode();
		String sendTimeStr = msgBasicInfo.getSendTime();
		sendTime = DateTimeUtil.stringToDate(sendTimeStr, DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
		eventType = msgBasicInfo.getEventType();
		severity = msgBasicInfo.getSeverity();
		String startTimeStr = msgBasicInfo.getStartTime();
		if (startTimeStr != null) {
			startTime = DateTimeUtil.stringToDate(startTimeStr, DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
		}

		String endTimeStr = msgBasicInfo.getEndTime();
		if (endTimeStr != null) {
			endTime = DateTimeUtil.stringToDate(endTimeStr, DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
		}

		ebmEntity.setEbmVersion(ebmVersion);
		ebmEntity.setEbmId(ebmId);
		ebmEntity.setRelatedEbIId(relatedEbIId);
		ebmEntity.setRelatedEbmId(relatedEbmId);
		ebmEntity.setMsgType(msgType);
		ebmEntity.setSendName(sendName);
		ebmEntity.setSenderCode(senderCode);
		ebmEntity.setSendTime(sendTime);
		ebmEntity.setEventType(eventType);
		ebmEntity.setSeverity(severity);
		ebmEntity.setStartTime(startTime);
		ebmEntity.setEndTime(endTime);

		// 可选
		MsgContent msgContent = ebm.getMsgContent();
		if (msgContent != null) {
			String areaCode = msgContent.getAreaCode();
			String msgLanguageCode = msgContent.getLanguageCode();
			String msgDesc = msgContent.getMsgDesc();
			String msgTitle = msgContent.getMsgTitle();
			// 可选
			Integer programNum = msgContent.getProgramNum();
			ebmEntity.setMsgLanguageCode(msgLanguageCode);
			ebmEntity.setMsgTitle(msgTitle);
			ebmEntity.setMsgDesc(msgDesc);
			ebmEntity.setAreaCode(areaCode);
			ebmEntity.setProgramNum(programNum);
		}

		ebmEntity.setSendFlag(SendFlag.receive.getValue());
		ebmEntity.setFlowId(dispatchFlow.getFlowId());
		ebmEntity.setEbmState(Constants.EBM_STATE_INIT);
		ebmEntity.setCreateTime(new Date());
		ebmEntity.setTimeOut(TimeOutStatusEnum.NoTimeout.getValue());
		ebmDAO.save(ebmEntity);
		InfoAccess infoAccess=infoAccessService.save(ebmEntity,Constants.AUDIT_USER,Constants.AUDIT_PASS,Constants.AUDIT_PASS_STR);
		List<EbmAuxiliary> ebmAuxiliarys = new ArrayList<EbmAuxiliary>();
		// 可选 辅助数据
		List<Auxiliary> auxiliarys = msgContent.getAuxiliaryList();

		if (auxiliarys != null && !auxiliarys.isEmpty()) {
			for (Auxiliary auxiliary : auxiliarys) {
				// 必选 辅助数据类型
				Integer auxiliaryType = auxiliary.getAuxiliaryType();
				// 必选 辅助数据描述 文件地址和名称
				String auxiliaryDesc = auxiliary.getAuxiliaryDesc();
				// 可选 签名
				String auxiliaryDigest = auxiliary.getDigest();
				// 可选 大小
				Integer auxiliarySize = auxiliary.getSize();

				EbmAuxiliary ebmAuxiliary = new EbmAuxiliary();
				ebmAuxiliary.setAuxiliaryDesc(auxiliaryDesc);
				ebmAuxiliary.setAuxiliaryDigest(auxiliaryDigest);
				ebmAuxiliary.setAuxiliarySize(auxiliarySize);
				ebmAuxiliary.setAuxiliaryType(auxiliaryType);
				ebmAuxiliary.setEbmId(ebmId);
				ebmAuxiliarys.add(ebmAuxiliary);

			}
			ebmAuxiliaryDAO.save(ebmAuxiliarys);
		}

		// 根据EBD触发调度方案
		Future<SchemeInfo> result = schemeService.asynDispatch(ebd, dispatchFlow.getFlowId(),infoAccess.getInfoId());

		while (true) {
			if (result.isDone()) {
				logger.info("EBMService.service generate dispatch scheme successfully.");
				break;
			} else {
				try {
					synchronized (Thread.currentThread()) {
						Thread.currentThread().wait(100L);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				logger.debug("EBMService.service generate dispatch scheme is running.");
			}
		}
		// 发送应急广播消息播发状态
		eBMStateRequestService.sendResponse(ebmId, getParentPlatUrl(),null);
	}

	/**
	 * 保存接收到EBD消息（EBM）媒体附件
	 * 
	 * @param resourceFiles
	 */
	protected void recordEbdFiles(EBD ebd, List<File> resourceFiles) {

		if (resourceFiles != null && !resourceFiles.isEmpty()) {
			for (File file : resourceFiles) {

				EbdFile ebdFile = new EbdFile();
				ebdFile.setEbdId(ebd.getEBDID());
				ebdFile.setFileUrl(file.getAbsolutePath());
				ebdFileDAO.save(ebdFile);
			}
		}
	}
}
