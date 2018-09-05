package com.comtom.bc.server.job;

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
import org.apache.commons.lang.StringUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * 台站信息上报
 * 
 */
@DisallowConcurrentExecution
public class STInfoReportJob extends AbstractJob implements Job {

	@Autowired
	private EbrStationService stationService;

	/**
	 * 日志记录器
	 */
	private static final Logger logger = LoggerFactory.getLogger(STInfoReportJob.class);

	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info(" 台站信息上报  Job 执行时间: " + new Date());
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
