package com.comtom.bc.server.job.statsic;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.exchange.commom.BroadcastStateEnum;
import com.comtom.bc.server.repository.dao.*;
import com.comtom.bc.server.repository.entity.*;
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
 * 应急消息播发统计数据生成记录定时任务
 * 
 */
public class EbmStatisticsDataJob implements Job {
	@Autowired
	private EbmBrdRecordDAO ebmBrdRecordDAO;

	@Autowired
	private EbmDAO ebmDAO;

	@Autowired
	private StatEbmDAO statEbmDAO;

	@Autowired
	private SchemeEbrDAO schemeEbrDAO;
	
	@Autowired
	private EbmResDAO ebmResDAO;
	
	@Autowired
	private EbmResBsDAO ebmResBsDAO;

	/**
	 * 日志记录器
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(EbmStatisticsDataJob.class);

	/**
	 * 执行应急消息播发统计数据生成任务
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Date currDt = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currDt);

		int currWeekDay = calendar.get(Calendar.DAY_OF_WEEK);
		int currDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		int currMonth = calendar.get(Calendar.MONTH);

		Date endTime = getEndTime(currDt);

		makeEbmStatsic(getStartTime(Constants.REPORT_DAILY, currDt), endTime,
				Constants.REPORT_DAILY,
				getStatsicDuation(Constants.REPORT_DAILY, currDt));
		if (currWeekDay == Calendar.MONDAY) {
			makeEbmStatsic(getStartTime(Constants.REPORT_WEEKLY, currDt),
					endTime, Constants.REPORT_WEEKLY,
					getStatsicDuation(Constants.REPORT_WEEKLY, currDt));
		}

		if (currDayOfMonth == 1) {
			makeEbmStatsic(getStartTime(Constants.REPORT_MONTHLY, currDt),
					endTime, Constants.REPORT_MONTHLY,
					getStatsicDuation(Constants.REPORT_MONTHLY, currDt));

			if (currMonth == Calendar.JANUARY || currMonth == Calendar.APRIL
					|| currMonth == Calendar.JULY
					|| currMonth == Calendar.OCTOBER) {
				makeEbmStatsic(getStartTime(Constants.REPORT_SEASONLY, currDt),
						endTime, Constants.REPORT_SEASONLY,
						getStatsicDuation(Constants.REPORT_SEASONLY, currDt));
			}

			if (currMonth == Calendar.JANUARY || currMonth == Calendar.JULY) {
				makeEbmStatsic(
						getStartTime(Constants.REPORT_HALF_YEARLY, currDt),
						endTime, Constants.REPORT_HALF_YEARLY,
						getStatsicDuation(Constants.REPORT_HALF_YEARLY, currDt));
			}

			if (currMonth == Calendar.JANUARY) {
				makeEbmStatsic(getStartTime(Constants.REPORT_YEARLY, currDt),
						endTime, Constants.REPORT_YEARLY,
						getStatsicDuation(Constants.REPORT_YEARLY, currDt));
			}
		}
		
		logger.info("应急消息播发统计数据已经生成");
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

	private void makeEbmStatsic(final Date startTime, final Date endTime,
			int statsicType, final String statsicDuation) {
		List<Ebm> foundEBMs = ebmDAO.findAll(new Specification<Ebm>() {
			public Predicate toPredicate(Root<Ebm> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (CommonUtil.isNotEmpty(startTime)) {
					predicate.getExpressions().add(
							cb.greaterThanOrEqualTo(
									root.<Date> get("createTime"), startTime));
				}

				if (CommonUtil.isNotEmpty(endTime)) {
					predicate.getExpressions().add(
							cb.lessThanOrEqualTo(root.<Date> get("createTime"),
									endTime));
				}

				return predicate;
			}

		});

		if (null == foundEBMs || foundEBMs.size() < 1) {
			return;
		}

		int noprogressNum = 0;
		int timetogoNum = 0;
		int inprogressNum = 0;
		int succeededNum = 0;
		int failedNum = 0;
		int cancelledNum = 0;
		int unstatedNum = 0;

		for (Ebm ebm : foundEBMs) {
			BroadcastStateEnum ebmBrdCd = findEBMBroadcastState(ebm);
			if (BroadcastStateEnum.noprogress.equals(ebmBrdCd)) {
				noprogressNum = noprogressNum + 1;
			} else if (BroadcastStateEnum.timetogo.equals(ebmBrdCd)) {
				timetogoNum = timetogoNum + 1;
			} else if (BroadcastStateEnum.inprogress.equals(ebmBrdCd)) {
				inprogressNum = inprogressNum + 1;
			} else if (BroadcastStateEnum.succeeded.equals(ebmBrdCd)) {
				succeededNum = succeededNum + 1;
			} else if (BroadcastStateEnum.failed.equals(ebmBrdCd)) {
				failedNum = failedNum + 1;
			} else if (BroadcastStateEnum.cancelled.equals(ebmBrdCd)) {
				cancelledNum = cancelledNum + 1;
			} else {
				unstatedNum = unstatedNum + 1;
			}
		}

		StatEbm stat = new StatEbm();
		stat.setStatsicTime(new Date());
		stat.setTotalNum(foundEBMs.size());
		stat.setSucceededNum(succeededNum);
		stat.setFailedNum(failedNum);
		stat.setUnstatedNum(unstatedNum);
		stat.setNoprogressNum(noprogressNum);
		stat.setTimetogoNum(timetogoNum);
		stat.setInprogressNum(inprogressNum);
		stat.setCancelledNum(cancelledNum);
		stat.setStatsicType(statsicType);
		stat.setStatsicDuation(statsicDuation);

		statEbmDAO.save(stat);

	}

	// 通过EBM找出方案的目标资源，应急广播子平台/本身具有播出系统，播出系统ID
	// select schemeId from bc_ebm where ebmId = :ebmId
	// select ebrId as psEbrId from bc_scheme_ebr where schemeId = :schemeId and
	// ebrType='01'
	// select ebrId as bsEbrId from bc_scheme_ebr where schemeId = :schemeId and
	// ebrType='05'

	// 找出EBM在应急广播平台中播发记录
	// select brdStateCode from bc_ebm_brd_record where resourceId = :psEbrId
	// and ebmId = :ebmId

	// 找出EBM在播出系统中播发记录
	// select brdStateCode from bc_ebm_brd_record rcd, bc_ebm_res rcdNBs, bc_ebm_res_bs bsRcd
	// where psRcd.brdItemId = bsRcd.brdItemId and ebmId = :ebmId
	private final BroadcastStateEnum findEBMBroadcastState(final Ebm ebm) {
		// 找出EBM对应的调度方案
		List<SchemeEbr> schemeEbrs = schemeEbrDAO
				.findAll(new Specification<SchemeEbr>() {
					public Predicate toPredicate(Root<SchemeEbr> root,
							CriteriaQuery<?> query, CriteriaBuilder cb) {
						Predicate predicate = cb.conjunction();
						if (CommonUtil.isNotEmpty(ebm.getSchemeId())) {
							predicate.getExpressions().add(
									cb.equal(root.<Integer> get("schemeId"),
											ebm.getSchemeId()));
						}

						return predicate;
					}
				});

		if (null == schemeEbrs || schemeEbrs.size() < 1) {
			return BroadcastStateEnum.noprogress;
		}

		// 分别找出目标应急广播平台和播出系统
		final List<String> psEbrIds = new ArrayList<String>();
		final List<String> bsEbrIds = new ArrayList<String>();
		for (SchemeEbr ebr : schemeEbrs) {
			if (Constants.EBR_TYPE_PLATFORM.equals(ebr.getEbrType())) {
				psEbrIds.add(ebr.getEbrId());
			} else if (Constants.EBR_TYPE_EWBS_BS.equals(ebr.getEbrType())) {
				bsEbrIds.add(ebr.getEbrId());
			}
		}

		//找出应急消息播发记录
		List<EbmBrdRecord> ebmBrdRecords = ebmBrdRecordDAO
				.findAll(new Specification<EbmBrdRecord>() {
					public Predicate toPredicate(Root<EbmBrdRecord> root,
							CriteriaQuery<?> query, CriteriaBuilder cb) {
						Predicate predicate = cb.conjunction();
						predicate.getExpressions().add(
								cb.equal(root.<Integer> get("ebmId"),
										ebm.getEbmId()));
						return predicate;
					}
				});
		
		// 查找应急广播平台消息播发结果代码
		List<Integer> psBrdCds = new ArrayList<Integer>();
		final List<String> bsBrdItemIds = new ArrayList<String>();
		if (null != ebmBrdRecords) {
			for (EbmBrdRecord rcd : ebmBrdRecords) {
				if(psEbrIds.contains(rcd.getResourceId())) {
					psBrdCds.add(rcd.getBrdStateCode());
				} else {
					bsBrdItemIds.add(rcd.getBrdItemId());
				}
			}
		}

		// 查找播出系统消息播发结果代码
		List<Integer> bsBrdCds = new ArrayList<Integer>();
		if(bsBrdItemIds.size() > 0) {
			 List<EbmRes> rcdNBs = ebmResDAO.findAll(new Specification<EbmRes>() {
				public Predicate toPredicate(Root<EbmRes> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					Predicate predicate = cb.conjunction();
					predicate.getExpressions().add(root.<String> get("brdItemId").in(bsBrdItemIds));
					return predicate;
				} 
			 });
			 
			 final List<String> ebmResourceIds = new ArrayList<String>();
			 if(null != rcdNBs) {
				 for(EbmRes ebmres : rcdNBs) {
					 ebmResourceIds.add(ebmres.getEbmResourceId());
				 }
			 }
			 
			List<EbmResBs> bsRcds = ebmResBsDAO.findAll(new Specification<EbmResBs>() {
				public Predicate toPredicate(Root<EbmResBs> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					Predicate predicate = cb.conjunction();
					predicate.getExpressions().add(root.<String> get("ebmResourceId").in(ebmResourceIds));
					return predicate;
				}
				
			});
			
			if(null != bsRcds) {
				for(EbmResBs bsRcd : bsRcds) {
					bsBrdCds.add(bsRcd.getBrdStateCode());
				}
			}
		}

		BroadcastStateEnum anEbmBrdResult = BroadcastStateEnum.failed;

		if(bsBrdItemIds.size() < 1) {                             //无播发记录
			if(ebm.getStartTime().after(new Date())) {
				anEbmBrdResult = BroadcastStateEnum.timetogo;
			} else {
				anEbmBrdResult = BroadcastStateEnum.noprogress;
			}
		} else if (psBrdCds.size() >= psEbrIds.size()                    // 播发结果代码数目大于等于目标资源数目，进一步分析播发结果代码
				&& bsBrdCds.size() >= bsEbrIds.size()) {
			List<Integer> brdCds = new ArrayList<Integer>();
			brdCds.addAll(psBrdCds);
			brdCds.addAll(bsBrdCds);

			anEbmBrdResult = conclusion(brdCds);
		}

		return anEbmBrdResult;
	}

	private final BroadcastStateEnum conclusion(List<Integer> brdStateCodes) {
		int noprogressNum = 0;
		int timetogoNum = 0;
		int inprogressNum = 0;
		int succeededNum = 0;
		int failedNum = 0;
		int cancelledNum = 0;

		for (Integer brdCd : brdStateCodes) {
			if (BroadcastStateEnum.noprogress.getCode().equals(brdCd)) {
				noprogressNum = noprogressNum + 1;
			} else if (BroadcastStateEnum.timetogo.getCode().equals(brdCd)) {
				timetogoNum = timetogoNum + 1;
			} else if (BroadcastStateEnum.inprogress.getCode().equals(brdCd)) {
				inprogressNum = inprogressNum + 1;
			} else if (BroadcastStateEnum.succeeded.getCode().equals(brdCd)) {
				succeededNum = succeededNum + 1;
			} else if (BroadcastStateEnum.failed.getCode().equals(brdCd)) {
				failedNum = failedNum + 1;
			} else if (BroadcastStateEnum.cancelled.getCode().equals(brdCd)) {
				cancelledNum = cancelledNum + 1;
			}
		}

		int brdRcdNum = brdStateCodes.size();
		BroadcastStateEnum result = null;
		if (inprogressNum > 0
				|| (failedNum == 0 && cancelledNum == 0 && succeededNum > 0 && succeededNum < brdRcdNum)) {
			result = BroadcastStateEnum.inprogress;
		} else if (succeededNum == brdRcdNum) {
			result = BroadcastStateEnum.succeeded;
		} else if (noprogressNum == brdRcdNum) {
			result = BroadcastStateEnum.noprogress;
		} else if (timetogoNum == brdRcdNum) {
			result = BroadcastStateEnum.timetogo;
		} else if (failedNum == 0 && cancelledNum > 0) {
			result = BroadcastStateEnum.cancelled;
		} else if (failedNum > 0) {
			result = BroadcastStateEnum.failed;
		}

		return result;
	}

}
