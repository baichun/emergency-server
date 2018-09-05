package com.comtom.bc.server.task;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.comtom.bc.exchange.commom.EBRInfoType;
import com.comtom.bc.exchange.commom.SyncFlag;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.commom.EBRPS;
import com.comtom.bc.exchange.model.ebd.commom.RelatedEBRPS;
import com.comtom.bc.exchange.model.ebd.details.info.EBRPSInfo;
import com.comtom.bc.exchange.util.DateStyle;
import com.comtom.bc.exchange.util.DateTimeUtil;
import com.comtom.bc.exchange.util.EBDModelBuild;
import com.comtom.bc.server.repository.entity.EbrPlatform;
import com.comtom.bc.server.service.EbrPlatformService;

/**
 *
 * 平台信息上报定时任务
 *
 * @author kidsoul
 *
 */
//@Component
//@Configurable
//@EnableScheduling
public class PsInfoReport extends AbstractReport {

	@Autowired
	private EbrPlatformService platformService;

	/**
	 * 日志记录器
	 */
	private static final Logger logger = LoggerFactory.getLogger(PsInfoReport.class);

	@Scheduled(cron = "${task.ebr-ps.cron}")
	@Override
	public void report() {
		// 上级平台地址
		String parentUrl = getParentUrl();
		if (StringUtils.isEmpty(parentUrl)) {
			return;
		}
		// 平台地址
		String srcURL = getPlatUrl();
		// ebd序列号
		String ebdIndex = generateEbdInde();
		// 平台ID
		String ebrId = getPlatFormId();

		// 查询未同步的数据
		List<EBRPS> ebrPsList = new ArrayList<EBRPS>();
		List<EbrPlatform> platformFound = platformService.listPlatform(true, null, null);
		if (CollectionUtils.isEmpty(platformFound)) {
			return;
		}
		for (EbrPlatform fnd : platformFound) {
			EBRPS ps = new EBRPS();
			ps.setRptTime(DateTimeUtil.dateToString(fnd.getUpdateTime(),
					DateStyle.YYYY_MM_DD_HH_MM_SS));
			ps.setRptType(EBRInfoType.Sync.name());
			ps.setEBRID(fnd.getPsEbrId());
			ps.setEBRName(fnd.getPsEbrName());
			ps.setAddress(fnd.getPsAddress());
			ps.setContact(fnd.getContact());
			ps.setPhoneNumber(fnd.getPhoneNumber());
			ps.setLongitude(fnd.getLongitude());
			ps.setLatitude(fnd.getLatitude());
			ps.setURL(fnd.getPsUrl());

			String parentPsEbrId = fnd.getParentPsEbrId();
			if (StringUtils.isNotBlank(parentPsEbrId)) {
				RelatedEBRPS relatedPs = new RelatedEBRPS();
				relatedPs.setEBRID(parentPsEbrId);
				ps.setRelatedEBRPS(relatedPs);
			}
			ebrPsList.add(ps);
		}
		EBRPSInfo ebrpsInfo = new EBRPSInfo();
		ebrpsInfo.setDataList(ebrPsList);
		EBD ebd = EBDModelBuild.buildEBRPSInfo(ebrId, srcURL, ebdIndex, ebrpsInfo,null);
		EBD response = sendEbd(ebd);
		boolean success = vilidataResult(response);
		if (!success) {
			StringBuffer errMsgBuf = new StringBuffer();
			errMsgBuf.append("上报平台信息错误, PsInfoReport : ");
			errMsgBuf.append(null == ebd ? "ebd为空" : "ebd.id=" + ebd.getEBDID());
			errMsgBuf.append(null == response ? ", ebd response为空" : ", ebd.response.id="
					+ response.getEBDID());
			logger.error(errMsgBuf.toString());
			return;
		}
		// 更新平台同步状态
		for (EbrPlatform object : platformFound) {
			object.setSyncFlag(SyncFlag.sync.getValue());
			platformService.saveOrUpdate(object);
		}
	}
}
