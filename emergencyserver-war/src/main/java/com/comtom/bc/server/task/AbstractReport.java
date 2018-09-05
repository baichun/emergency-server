package com.comtom.bc.server.task;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.comtom.bc.common.Constants;
import com.comtom.bc.exchange.commom.SendFlag;
import com.comtom.bc.exchange.model.ebd.ebm.DEST;
import com.comtom.bc.exchange.model.ebd.ebm.RelatedEBD;
import com.comtom.bc.exchange.model.ebd.ebm.SRC;
import com.comtom.bc.exchange.util.*;
import com.comtom.bc.server.repository.dao.EbdDAO;
import com.comtom.bc.server.repository.entity.Ebd;
import org.springframework.beans.factory.annotation.Autowired;

import com.comtom.bc.exchange.commom.EBDRespResultEnum;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.details.other.EBDResponse;
import com.comtom.bc.exchange.model.signature.Signature;
import com.comtom.bc.server.repository.entity.EbrPlatform;
import com.comtom.bc.server.service.impl.BaseServiceImpl;

public abstract class AbstractReport {

	@Autowired
	private BaseServiceImpl serviceImpl;

	@Autowired
	private EbdDAO ebdDAO;


	/**
	 * 发送数据包
	 * 
	 * @param ebd
	 * @return EBD
	 */
	protected EBD sendEbd(EBD ebd) {
		// 上级平台地址
		String parentUrl = getParentUrl();
		// 发送文件保存路径(根据类型区分文件夹)
		String filePathSend = getSendFilePath() + File.separator + ebd.getEBDType();
		// 接收文件保存路径
		String filePathReceive = getReceiveFilePaht();

		File file = FileUtil.converFile(filePathSend, ebd);

		// 生成签名和签名文件
		Signature signature = EBDModelBuild.buildSignature(file, ebd.getEBDID());
		File signFile = FileUtil.converFile(filePathSend, signature);

		List<File> files = new ArrayList<File>();
		files.add(file);
		files.add(signFile);

		File tarfile = TarFileUtil.compressorsTar(ebd, files, filePathSend);
		EBD ebdReceive = HttpRequestUtil.sendFile(tarfile, parentUrl, filePathReceive);
		recordEbd(ebd, tarfile.getName(), SendFlag.send , ebdReceive == null ? Constants.EBD_STATE_SEND_FAILED : Constants.EBD_STATE_SEND_SUCCESS);
		if (ebdReceive == null) {
		//	throw new RuntimeException("发送文件错误.");
		}
		return ebdReceive;
	}


	/**
	 * 保存接收数发送据包记录
	 *
	 * @param ebd
	 * @param ebdName
	 */
	protected void recordEbd(EBD ebd, String ebdName, SendFlag sendFlag,Integer ebdState) {
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
	 * 验证数据包是否发送成功
	 * 
	 * @param ebd
	 * @return
	 */
	protected boolean vilidataResult(EBD ebdReceive) {
		if (ebdReceive == null) {
			return false;
		}
		EBDResponse ebdResponse = ebdReceive.getEBDResponse();
		if (ebdResponse == null) {
			return false;
		}
		Integer resultCode = ebdResponse.getResultCode();
		if (!EBDRespResultEnum.receivevalid.getCode().equals(resultCode)) {
			return false;
		}
		return true;
	}

	/**
	 * 接收文件保存路径
	 * 
	 * @return
	 */
	protected String getReceiveFilePaht() {
		String filePathReceive = serviceImpl.getReceiveEbdUrl();
		return filePathReceive;
	}

	/**
	 * 发送文件保存路径
	 * 
	 * @return
	 */
	protected String getSendFilePath() {
		String filePathSend = serviceImpl.getSendEbdUrl();
		return filePathSend;
	}

	/**
	 * 上级平台地址
	 * 
	 * @return
	 */
	protected String getParentUrl() {
		String parentUrl = serviceImpl.getParentPlatUrl();
		return parentUrl;
	}

	/**
	 * 平台地址
	 * 
	 * @return
	 */
	protected String getPlatUrl() {
		String srcURL = serviceImpl.getPlatFormUrl();
		return srcURL;
	}

	/**
	 * 获取平台ID
	 * 
	 * @return
	 */
	protected String getPlatFormId() {
		String ebrId = serviceImpl.getEbrPlatFormID();
		return ebrId;
	}

	/**
	 * 生成ebd序列
	 * 
	 * @return
	 */
	protected String generateEbdInde() {
		String ebdIndex = serviceImpl.generateEbdIndex();
		return ebdIndex;
	}

	protected EbrPlatform getEBRPSInfo() {
		return serviceImpl.getEBRPSInfo();
	}

	/**
	 * 上报信息
	 */
	public abstract void report();
}
