package com.comtom.bc.server.job.statsic;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.exchange.commom.EBDType;
import com.comtom.bc.server.repository.dao.EbdDAO;
import com.comtom.bc.server.repository.dao.StatEbdDAO;
import com.comtom.bc.server.repository.entity.Ebd;
import com.comtom.bc.server.repository.entity.StatEbd;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 业务数据包统计数据生成记录定时任务
 * 
 *
 */
public class EbdStatisticsDataJob implements Job {

	@Autowired
	private EbdDAO ebdDAO;

	@Autowired
	private StatEbdDAO statEbdDAO;

	/**
	 * 日志记录器
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(EbdStatisticsDataJob.class);

	public void execute(JobExecutionContext context) throws JobExecutionException {
		Date currDt = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currDt);

		int currWeekDay = calendar.get(Calendar.DAY_OF_WEEK);
		int currDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		int currMonth = calendar.get(Calendar.MONTH);

		Date endTime = getEndTime(currDt);

		makeEbdStatsic(getStartTime(Constants.REPORT_DAILY, currDt), endTime,
				Constants.REPORT_DAILY,
				getStatsicDuation(Constants.REPORT_DAILY, currDt));
		
		
		if (currWeekDay == Calendar.MONDAY) {
			makeEbdStatsic(getStartTime(Constants.REPORT_WEEKLY, currDt),
					endTime, Constants.REPORT_WEEKLY,
					getStatsicDuation(Constants.REPORT_WEEKLY, currDt));
		}

		if (currDayOfMonth == 1) {
			makeEbdStatsic(getStartTime(Constants.REPORT_MONTHLY, currDt),
					endTime, Constants.REPORT_MONTHLY,
					getStatsicDuation(Constants.REPORT_MONTHLY, currDt));

			if (currMonth == Calendar.JANUARY || currMonth == Calendar.APRIL
					|| currMonth == Calendar.JULY
					|| currMonth == Calendar.OCTOBER) {
				makeEbdStatsic(getStartTime(Constants.REPORT_SEASONLY, currDt),
						endTime, Constants.REPORT_SEASONLY,
						getStatsicDuation(Constants.REPORT_SEASONLY, currDt));
			}

			if (currMonth == Calendar.JANUARY || currMonth == Calendar.JULY) {
				makeEbdStatsic(getStartTime(Constants.REPORT_HALF_YEARLY, currDt),
						endTime, Constants.REPORT_HALF_YEARLY,
						getStatsicDuation(Constants.REPORT_HALF_YEARLY, currDt));
			}

			if (currMonth == Calendar.JANUARY) {
				makeEbdStatsic(getStartTime(Constants.REPORT_YEARLY, currDt),
						endTime, Constants.REPORT_YEARLY,
						getStatsicDuation(Constants.REPORT_YEARLY, currDt));
			}
		}

		logger.info("业务数据包统计数据已经生成");
	}

	private Date getEndTime(Date currDt) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currDt);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	private Date getStartTime(int statsicType, Date currDt) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currDt);

		if (Constants.REPORT_DAILY == statsicType) {
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		} else if (Constants.REPORT_WEEKLY == statsicType) {
			calendar.add(Calendar.DAY_OF_MONTH, -7);
		} else if (Constants.REPORT_MONTHLY == statsicType) {
			calendar.add(Calendar.MONTH, -1);
		} else if (Constants.REPORT_SEASONLY == statsicType) {
			calendar.add(Calendar.MONTH, -3);
		} else if (Constants.REPORT_HALF_YEARLY == statsicType) {
			calendar.add(Calendar.MONTH, -6);
		} else if (Constants.REPORT_YEARLY == statsicType) {
			calendar.add(Calendar.MONTH, -12);
		}

		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 1);

		return calendar.getTime();
	}

	private String getStatsicDuation(int statsicType, Date currDt) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currDt);

		String duation = null;

		if (Constants.REPORT_DAILY == statsicType) {
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			duation = calendar.get(Calendar.YEAR) + "年"
					+ (calendar.get(Calendar.MONTH) + 1) + "月"
					+ calendar.get(Calendar.DAY_OF_MONTH) + "日";
		} else if (Constants.REPORT_WEEKLY == statsicType) {
			calendar.add(Calendar.DAY_OF_MONTH, -7);
			duation = calendar.get(Calendar.YEAR) + "年"
					+ calendar.get(Calendar.WEEK_OF_YEAR) + "周";
		} else if (Constants.REPORT_MONTHLY == statsicType) {
			calendar.add(Calendar.MONTH, -1);
			duation = calendar.get(Calendar.YEAR) + "年"
					+ (calendar.get(Calendar.MONTH) + 1) + "月";
		} else if (Constants.REPORT_SEASONLY == statsicType) {
			calendar.add(Calendar.MONTH, -3);
			duation = calendar.get(Calendar.YEAR) + "年"
					+ (calendar.get(Calendar.MONTH) / 3 + 1) + "季度";
		} else if (Constants.REPORT_HALF_YEARLY == statsicType) {
			calendar.add(Calendar.MONTH, -6);
			duation = calendar.get(Calendar.YEAR)
					+ "年"
					+ (calendar.get(Calendar.MONTH) == Calendar.JANUARY ? "上半年"
							: "下半年");
		} else if (Constants.REPORT_YEARLY == statsicType) {
			calendar.add(Calendar.MONTH, -12);
			duation = calendar.get(Calendar.YEAR) + "年";
		}

		return duation;
	}

	private void makeEbdStatsic(final Date startTime, final Date endTime,
			int statsicType, final String statsicDuation) {
		List<Ebd> meanEBDs = ebdDAO.findAll(new Specification<Ebd>() {
			public Predicate toPredicate(Root<Ebd> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (CommonUtil.isNotEmpty(startTime)) {
					predicate.getExpressions().add(
							cb.greaterThanOrEqualTo(root.<Date> get("ebdTime"),
									startTime));
				}

				if (CommonUtil.isNotEmpty(endTime)) {
					predicate.getExpressions().add(
							cb.lessThanOrEqualTo(root.<Date> get("ebdTime"),
									endTime));
				}

				List<String> meanEBDTypes = new ArrayList<String>();
				meanEBDTypes.add(EBDType.EBM.name());
				meanEBDTypes.add(EBDType.EBMStateRequest.name());
				meanEBDTypes.add(EBDType.EBMStateResponse.name());
				meanEBDTypes.add(EBDType.OMDRequest.name());
				meanEBDTypes.add(EBDType.EBRPSInfo.name());
				meanEBDTypes.add(EBDType.EBRSTInfo.name());
				meanEBDTypes.add(EBDType.EBRASInfo.name());
				meanEBDTypes.add(EBDType.EBRBSInfo.name());
				meanEBDTypes.add(EBDType.EBRDTInfo.name());
				meanEBDTypes.add(EBDType.EBMBrdLog.name());
				meanEBDTypes.add(EBDType.EBRPSState.name());
				meanEBDTypes.add(EBDType.EBRASState.name());
				meanEBDTypes.add(EBDType.EBRBSState.name());
				meanEBDTypes.add(EBDType.EBRDTState.name());

				predicate.getExpressions().add(
						root.<String> get("ebdType").in(meanEBDTypes));

				return predicate;
			}

		});

		if (null == meanEBDs || meanEBDs.size() < 1) {
			return;
		}

		Integer sntflg = Integer.valueOf(Constants.SEND_FLAG);
		List<Ebd> sendMeanEBDs = new ArrayList<Ebd>();
		List<Ebd> receiveMeanEBDs = new ArrayList<Ebd>();

		for (Ebd ebd : meanEBDs) {
			if (sntflg.equals(ebd.getSendFlag())) {
				sendMeanEBDs.add(ebd);
			} else {
				receiveMeanEBDs.add(ebd);
			}
		}

		List<StatEbd> ebdStat2Save = new ArrayList<StatEbd>();
		StatEbd sntStat = fillStatEBD(sendMeanEBDs, Constants.SEND_FLAG, statsicType, statsicDuation);
		if (null != sntStat) {
			ebdStat2Save.add(sntStat);
		}
		StatEbd rcvStat = fillStatEBD(receiveMeanEBDs, Constants.RECEIVE_FLAG, statsicType, statsicDuation);
		if (null != rcvStat) {
			ebdStat2Save.add(rcvStat);
		}

		if (ebdStat2Save.size() > 0) {
			statEbdDAO.save(ebdStat2Save);
		}

	}

	private StatEbd fillStatEBD(List<Ebd> meanEBDs, int sendFlag, int statsicType,
			String statsicDuation) {
		if (null == meanEBDs || meanEBDs.size() < 1) {
			return null;
		}

		int ebmNum = 0;
		int ebmStatReqNum = 0;
		int ebmStatResNum = 0;
		int omdReqNum = 0;
		int ebrPSInfoNum = 0;
		int ebrSTInfoNum = 0;
		int ebrASInfoNum = 0;
		int ebrBSInfoNum = 0;
		int ebrDTInfoNum = 0;
		int ebmBrdLogNum = 0;
		int ebrPSStateNum = 0;
		int ebrASStateNum = 0;
		int ebrBSStateNum = 0;
		int ebrDTStateNum = 0;

		for (Ebd ebd : meanEBDs) {
			if (EBDType.EBM.name().equals(ebd.getEbdType())) {
				ebmNum = ebmNum + 1;
			} else if (EBDType.EBMStateRequest.name().equals(ebd.getEbdType())) {
				ebmStatReqNum = ebmStatReqNum + 1;
			} else if (EBDType.EBMStateResponse.name().equals(ebd.getEbdType())) {
				ebmStatResNum = ebmStatResNum + 1;
			} else if (EBDType.OMDRequest.name().equals(ebd.getEbdType())) {
				omdReqNum = omdReqNum + 1;
			} else if (EBDType.EBRPSInfo.name().equals(ebd.getEbdType())) {
				ebrPSInfoNum = ebrPSInfoNum + 1;
			} else if (EBDType.EBRSTInfo.name().equals(ebd.getEbdType())) {
				ebrSTInfoNum = ebrSTInfoNum + 1;
			} else if (EBDType.EBRASInfo.name().equals(ebd.getEbdType())) {
				ebrASInfoNum = ebrASInfoNum + 1;
			} else if (EBDType.EBRBSInfo.name().equals(ebd.getEbdType())) {
				ebrBSInfoNum = ebrBSInfoNum + 1;
			} else if (EBDType.EBRDTInfo.name().equals(ebd.getEbdType())) {
				ebrDTInfoNum = ebrDTInfoNum + 1;
			} else if (EBDType.EBMBrdLog.name().equals(ebd.getEbdType())) {
				ebmBrdLogNum = ebmBrdLogNum + 1;
			} else if (EBDType.EBRPSState.name().equals(ebd.getEbdType())) {
				ebrPSStateNum = ebrPSStateNum + 1;
			} else if (EBDType.EBRASState.name().equals(ebd.getEbdType())) {
				ebrASStateNum = ebrASStateNum + 1;
			} else if (EBDType.EBRBSState.name().equals(ebd.getEbdType())) {
				ebrBSStateNum = ebrBSStateNum + 1;
			} else if (EBDType.EBRDTState.name().equals(ebd.getEbdType())) {
				ebrDTStateNum = ebrDTStateNum + 1;
			}
		}

		StatEbd statEBD = new StatEbd();
		statEBD.setTotalNum(meanEBDs.size());
		statEBD.setEbmNum(ebmNum);
		statEBD.setEbmStatReqNum(ebmStatReqNum);
		statEBD.setEbmStatResNum(ebmStatResNum);
		statEBD.setOmdReqNum(omdReqNum);
		statEBD.setEbrPSInfoNum(ebrPSInfoNum);
		statEBD.setEbrSTInfoNum(ebrSTInfoNum);
		statEBD.setEbrASInfoNum(ebrASInfoNum);
		statEBD.setEbrBSInfoNum(ebrBSInfoNum);
		statEBD.setEbrDTInfoNum(ebrDTInfoNum);
		statEBD.setEbmBrdLogNum(ebmBrdLogNum);
		statEBD.setEbrPSStateNum(ebrPSStateNum);
		statEBD.setEbrASStateNum(ebrASStateNum);
		statEBD.setEbrBSStateNum(ebrBSStateNum);
		statEBD.setEbrDTStateNum(ebrDTStateNum);
		statEBD.setSendFlag(sendFlag);
		statEBD.setStatsicType(statsicType);
		statEBD.setStatsicDuation(statsicDuation);
		statEBD.setStatsicTime(new Date());

		return statEBD;
	}

}
