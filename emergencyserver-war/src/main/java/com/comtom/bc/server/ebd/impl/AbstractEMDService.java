package com.comtom.bc.server.ebd.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.comtom.bc.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.comtom.bc.exchange.commom.SendFlag;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.details.other.EBDResponse;
import com.comtom.bc.exchange.model.ebd.ebm.DEST;
import com.comtom.bc.exchange.model.ebd.ebm.RelatedEBD;
import com.comtom.bc.exchange.model.ebd.ebm.SRC;
import com.comtom.bc.exchange.model.signature.Signature;
import com.comtom.bc.exchange.service.EMDservice;
import com.comtom.bc.exchange.util.DateStyle;
import com.comtom.bc.exchange.util.DateTimeUtil;
import com.comtom.bc.exchange.util.EBDModelBuild;
import com.comtom.bc.exchange.util.FileNameUtil;
import com.comtom.bc.exchange.util.FileUtil;
import com.comtom.bc.exchange.util.HttpRequestUtil;
import com.comtom.bc.exchange.util.TarFileUtil;
import com.comtom.bc.server.ebd.model.ResourceModel;
import com.comtom.bc.server.repository.dao.EbdDAO;
import com.comtom.bc.server.repository.dao.EbdResponseDAO;
import com.comtom.bc.server.repository.entity.Ebd;
import com.comtom.bc.server.repository.entity.EbdResponse;
import com.comtom.bc.server.service.base.BaseService;

/**
 * @author nobody 处理接收数据包 保存数据库
 */
public abstract class AbstractEMDService extends BaseService implements EMDservice {

	/**
	 * 日志记录器
	 */
	private static final Logger logger = LoggerFactory.getLogger(AbstractEMDService.class);

	@Autowired
	private EbdDAO ebdDAO;

	@Autowired
	private EbdResponseDAO ebdResponseDAO;

	@Override
	public void preservice(EBD ebd, String ebdName, List<File> resourceFiles) {
		recordEbd(ebd, ebdName, SendFlag.receive, Constants.EBD_STATE_SEND_SUCCESS);
	}

	@Override
	public void afterservice(EBD ebd, String ebdName) {
		recordEbd(ebd, ebdName, SendFlag.send, Constants.EBD_STATE_SEND_SUCCESS);
		recordEbdResponse(ebd, ebd.getEBDResponse(), SendFlag.send);
	}

	/**
	 * 保存接收数发送据包记录
	 * 
	 * @param ebd
	 * @param ebdName
	 */
	protected void recordEbd(EBD ebd, String ebdName, SendFlag sendFlag, Integer ebdState) {
		// 必选
		String ebdVersion = ebd.getEBDVersion();
		// 必选
		String ebdId = ebd.getEBDID();
		// 必选
		String ebdType = ebd.getEBDType();
		// 必选
		String ebdTimeString = ebd.getEBDTime();
		Date ebdTime = DateTimeUtil.stringToDate(ebdTimeString,
				DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
		// 必选
		SRC src = ebd.getSRC();
		// 必选资源ID
		String ebdSrcEbrId = src.getEBRID();
		// 可选
		String ebdSrcUrl = src.getURL();
		// 可选
		DEST dest = ebd.getDEST();
		String ebdDestEbrId = null;
		if (dest != null) {
			// 目标对象的资源ID 必选
			ebdDestEbrId = dest.getEBRID();
		}
		// 可选
		RelatedEBD relatedEBD = ebd.getRelatedEBD();
		String relateEbdId = null;
		if (relatedEBD != null) {
			// 必选
			relateEbdId = relatedEBD.getEBDID();
		}

		Ebd arg0 = new Ebd();
		arg0.setEbdId(ebdId);
		arg0.setEbdVersion(ebdVersion);
		arg0.setEbdType(ebdType);
		arg0.setEbdName(FileNameUtil.generateTarFileName(ebdId));
		arg0.setEbdSrcEbrId(ebdSrcEbrId);
		arg0.setEbdDestEbrId(ebdDestEbrId);
		arg0.setEbdTime(ebdTime);
		arg0.setRelateEbdId(relateEbdId);
		arg0.setEbdSrcUrl(ebdSrcUrl);

		arg0.setSendFlag(sendFlag.getValue());
		arg0.setEbdState(ebdState);

		if (SendFlag.receive.equals(sendFlag)) {
			arg0.setEbdRecvTime(new Date());
		} else if (SendFlag.send.equals(sendFlag)) {
			arg0.setEbdSendTime(new Date());
		}
		arg0.setFlowId(null);

		// 如果是EBM消息，设置EBD关联的EBM消息Id
		if (ebd.getEBM() != null) {
			arg0.setEbmId(ebd.getEBM().getEBMID());
		}
		ebdDAO.save(arg0);
	}

	/**
	 * 保存接收发送数据包响应数据
	 * 
	 * @param ebd
	 * @param
	 */
	protected void recordEbdResponse(EBD ebd, EBDResponse eBDResponse, SendFlag sendFlag) {

		String ebdVersion = ebd.getEBDVersion();
		String ebdSrcEbrId = ebd.getSRC().getEBRID();
		String relatedEbdId = null;
		if (ebd.getRelatedEBD() != null) {
			relatedEbdId = ebd.getRelatedEBD().getEBDID();
		}
		// 必选
		String ebdId = ebd.getEBDID();
		// 必选
		String ebdType = ebd.getEBDType();
		// 必选
		String ebdTimeString = ebd.getEBDTime();
		Date ebdTime = DateTimeUtil.stringToDate(ebdTimeString,
				DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
		Integer resultCode = eBDResponse.getResultCode();
		String resultDesc = eBDResponse.getResultDesc();

		EbdResponse ebdResponse = new EbdResponse();
		ebdResponse.setEbdId(ebdId);
		ebdResponse.setEbdTime(ebdTime);
		ebdResponse.setEbdType(ebdType);
		ebdResponse.setResultCode(resultCode);
		ebdResponse.setResultDesc(resultDesc);
		ebdResponse.setSendFlag(sendFlag.getValue());
		ebdResponse.setEbdVersion(ebdVersion);
		ebdResponse.setRelatedEbdId(relatedEbdId);
		ebdResponse.setEbdSrcEbrId(ebdSrcEbrId);
		ebdResponseDAO.save(ebdResponse);
	}

	/**
	 * 发送数据包
	 * 
	 * @param ebd
	 * @return
	 */
	protected void sendEBD(EBD ebd, String requestURL) {
		// 发送文件的路径（根据文件类型区分文件夹）
		String sendFilePath = getSendEbdUrl() + File.separator + ebd.getEBDType();
		
		// 对象转换文件
		File file = FileUtil.converFile(sendFilePath, ebd);

		// 生成签名和签名文件
		Signature signature = EBDModelBuild.buildSignature(file, ebd.getEBDID());
		File signFile = FileUtil.converFile(sendFilePath, signature);
		
		List<File> files = new ArrayList<File>();
		files.add(file);
		files.add(signFile);
		
		// 文件压缩
		File tar = TarFileUtil.compressorsTar(ebd, files, sendFilePath);
		// 接收文件保存路径
		String receviceFilePath = getReceiveEbdUrl();
		// 文件发送
		EBD ebdResponse = HttpRequestUtil.sendFile(tar, requestURL, receviceFilePath);
		// 保存数据包记录
		recordEbd(ebd, file.getName(), SendFlag.send,ebdResponse == null ? Constants.EBD_STATE_SEND_FAILED : Constants.EBD_STATE_SEND_SUCCESS);
		if (ebdResponse == null) {
			logger.error("发送数据失败.");
			return;
		}
		// 接收数据包成功
		EBDResponse response = ebdResponse.getEBDResponse();
		if (response == null) {
			logger.error("发送数据异常.");
			return;
		}
		recordEbdResponse(ebd, response, SendFlag.receive);
	}

	/**
	 * 
	 * @param resouceId
	 * @return
	 */
	protected ResourceModel parseResourceId(String resouceId) {
		ResourceModel resourceModel = new ResourceModel();
		Integer platLevel = null;
		String areaCode = null;
		String psType = resouceId.substring(2, 4);
		String areaCodeAll = resouceId.substring(4, 16);
		if (areaCodeAll.endsWith("0000000000")) {
			platLevel = 1;
			areaCode = areaCodeAll.substring(0, 2);
		} else if (areaCodeAll.endsWith("00000000")) {
			platLevel = 2;
			areaCode = areaCodeAll.substring(0, 4);
		} else if (areaCodeAll.endsWith("000000")) {
			platLevel = 3;
			areaCode = areaCodeAll.substring(0, 6);
		} else if (areaCodeAll.endsWith("000")) {
			platLevel = 4;
			areaCode = areaCodeAll.substring(0, 9);
		} else {
			platLevel = 5;
			areaCode = areaCodeAll;
		}
		resourceModel.setAreaCode(areaCode);
		resourceModel.setPlatLevel(platLevel);
		resourceModel.setResourceType(psType);
		return resourceModel;
	}

}
