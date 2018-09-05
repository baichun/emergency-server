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
import com.comtom.bc.exchange.model.ebd.commom.EBRST;
import com.comtom.bc.exchange.model.ebd.commom.RelatedEBRPS;
import com.comtom.bc.exchange.model.ebd.details.info.EBRSTInfo;
import com.comtom.bc.exchange.util.DateStyle;
import com.comtom.bc.exchange.util.DateTimeUtil;
import com.comtom.bc.exchange.util.EBDModelBuild;
import com.comtom.bc.server.repository.entity.EbrStation;
import com.comtom.bc.server.service.EbrStationService;

/**
 * 
 * 台站信息上报
 * 
 * @author kidsoul
 */
//@Component
//@Configurable
//@EnableScheduling
public class STInfoReport extends AbstractReport {

	@Autowired
	private EbrStationService stationService;

	/**
	 * 日志记录器
	 */
	private static final Logger logger = LoggerFactory.getLogger(STInfoReport.class);

	@Scheduled(cron = "${task.ebr-st.cron}")
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
		List<EbrStation> stationFound = stationService.listStation(true, null, null);
		if (CollectionUtils.isEmpty(stationFound)) {
			return;
		}
		List<EBRST> ebrStList = new ArrayList<EBRST>();
		for (EbrStation fnd : stationFound) {
			EBRST st = new EBRST();
			st.setRptTime(DateTimeUtil.dateToString(fnd.getUpdateTime(),
					DateStyle.YYYY_MM_DD_HH_MM_SS));
			st.setRptType(EBRInfoType.Sync.name());
			st.setEBRID(fnd.getStationEbrId());
			st.setEBRName(fnd.getStationName());
			st.setAddress(fnd.getStationAddress());
			st.setContact(fnd.getContact());
			st.setPhoneNumber(fnd.getPhoneNumber());
			st.setLongitude(fnd.getLongitude());
			st.setLatitude(fnd.getLatitude());

			String relatedPsId = fnd.getRelatedPsEbrId();
			if (StringUtils.isNotEmpty(relatedPsId)) {
				RelatedEBRPS relatedEBRPS = new RelatedEBRPS();
				relatedEBRPS.setEBRID(relatedPsId);
				st.setRelatedEBRPS(relatedEBRPS);
			}
			ebrStList.add(st);
		}
		EBRSTInfo stationInfo = new EBRSTInfo();
		stationInfo.setDataList(ebrStList);
		EBD ebd = EBDModelBuild.buildEBRSTInfo(ebrId, srcURL, ebdIndex, stationInfo,null);
		EBD response = sendEbd(ebd);
		boolean success = vilidataResult(response);
		if (!success) {
			StringBuffer errMsgBuf = new StringBuffer();
			errMsgBuf.append("上报台站信息错误, STInfoReport : ");
			errMsgBuf.append(null == ebd ? "ebd为空" : "ebd.id=" + ebd.getEBDID());
			errMsgBuf.append(null == response ? ", ebd response为空" : ", ebd.response.id="
					+ response.getEBDID());
			logger.error(errMsgBuf.toString());
			return;
		}

		for (EbrStation object : stationFound) {
			object.setSyncFlag(SyncFlag.sync.getValue());
			stationService.saveOrUpdate(object);
		}

	}

}
