package com.comtom.bc.server.task.statsic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.exchange.commom.EventSeverityEnum;
import com.comtom.bc.server.repository.dao.EbdDAO;
import com.comtom.bc.server.repository.dao.EbmDAO;
import com.comtom.bc.server.repository.dao.EbrPlatformDAO;
import com.comtom.bc.server.repository.dao.RegionAreaDAO;
import com.comtom.bc.server.repository.dao.StatBroadcastAreaDAO;
import com.comtom.bc.server.repository.dao.StatBroadcastDAO;
import com.comtom.bc.server.repository.dao.SysParamDAO;
import com.comtom.bc.server.repository.entity.Ebd;
import com.comtom.bc.server.repository.entity.Ebm;
import com.comtom.bc.server.repository.entity.EbrPlatform;
import com.comtom.bc.server.repository.entity.RegionArea;
import com.comtom.bc.server.repository.entity.StatBroadcast;
import com.comtom.bc.server.repository.entity.StatBroadcastArea;

/**
 * 广播数目统计数据生成记录定时任务
 * 
 * @author kidsoul
 *
 */
//@Component
//@Configurable
//@EnableScheduling
public class BroadcastStatisticsDataTask {
	@Autowired
	private SysParamDAO sysParamDAO;
	
	@Autowired
	private EbmDAO ebmDAO;
	
	@Autowired
	private EbdDAO ebdDAO;
	
	@Autowired
	private StatBroadcastDAO statBrdDAO;
	
	@Autowired
	private StatBroadcastAreaDAO statBrdAreaDAO;
	
	@Autowired
	private EbrPlatformDAO ebrPlatformDAO;
	
	@Autowired
	private RegionAreaDAO regionAreaDAO;

	/**
	 * 日志记录器
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(BroadcastStatisticsDataTask.class);

	/**
	 * 执行广播数目统计数据生成任务
	 */
	@Scheduled(cron = "${task.statisitic-broadcast.cron}")
	public void broadcastStatistics() {
		Date currDt = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currDt);
		
		int currDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		int currMonth = calendar.get(Calendar.MONTH);

		Date endTime = getEndTime(currDt);

		if (currDayOfMonth == 1) {
			makeBrdStatsic(getStartTime(Constants.REPORT_MONTHLY, currDt),
					endTime, Constants.REPORT_MONTHLY,
					getStatsicDuation(Constants.REPORT_MONTHLY, currDt));

			if (currMonth == Calendar.JANUARY || currMonth == Calendar.APRIL
					|| currMonth == Calendar.JULY
					|| currMonth == Calendar.OCTOBER) {
				makeBrdStatsic(getStartTime(Constants.REPORT_SEASONLY, currDt),
						endTime, Constants.REPORT_SEASONLY,
						getStatsicDuation(Constants.REPORT_SEASONLY, currDt));
			}

			if (currMonth == Calendar.JANUARY || currMonth == Calendar.JULY) {
				makeBrdStatsic(
						getStartTime(Constants.REPORT_HALF_YEARLY, currDt),
						endTime, Constants.REPORT_HALF_YEARLY,
						getStatsicDuation(Constants.REPORT_HALF_YEARLY, currDt));
			}

			if (currMonth == Calendar.JANUARY) {
				makeBrdStatsic(getStartTime(Constants.REPORT_YEARLY, currDt),
						endTime, Constants.REPORT_YEARLY,
						getStatsicDuation(Constants.REPORT_YEARLY, currDt));
			}
		}
		
		logger.info("广播数目统计数据已经生成");
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

	    if (Constants.REPORT_MONTHLY == statsicType) {
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

        if (Constants.REPORT_MONTHLY == statsicType) {
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

	private void makeBrdStatsic(final Date startTime, final Date endTime,
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
				
				List<Integer> severityScope = new ArrayList<Integer>();
				severityScope.add(EventSeverityEnum.red.getCode());
				severityScope.add(EventSeverityEnum.orange.getCode());
				severityScope.add(EventSeverityEnum.yellow.getCode());
				severityScope.add(EventSeverityEnum.blue.getCode());
				severityScope.add(EventSeverityEnum.common.getCode());
				
				predicate.getExpressions().add(root.<Integer> get("severity").in(severityScope));

				return predicate;
			}

		});

		if (null == foundEBMs || foundEBMs.size() < 1) {
			return;
		}

		//广播发起来源数目统计
		int selfMadeNum = 0;
		int parentPushNum = 0;
		int childApplyNum = 0;
		int otherAlertNum = 0;
        Integer selfSent = Integer.valueOf(Constants.SEND_FLAG);
        String parentPlatformId = getParentPlatformId();
        List<String> childPlatformIds = getChildPlatformIds();
        
		for (Ebm ebm : foundEBMs) {
             if(selfSent.equals(ebm.getSendFlag())) {           //本系统内发起（制播系统）
            	 selfMadeNum = selfMadeNum + 1;
             } else {
                 Ebd rcvEBD = findEBDByEbmId(ebm.getEbmId());
                 if(null != rcvEBD && null != rcvEBD.getEbdSrcEbrId()) {
                	 if(rcvEBD.getEbdSrcEbrId().equals(parentPlatformId)) {  //上级应急广播平台下发
                		 parentPushNum = parentPushNum + 1;
                	 } else if(childPlatformIds.contains(rcvEBD.getEbdSrcEbrId())){  //下级平台申请发起
                		 childApplyNum = childApplyNum + 1;
                	 } else {                                                //其他预警部门发起
                		 otherAlertNum = otherAlertNum + 1;
                	 }
                 }
             }
		}
		
		StatBroadcast statBrd = new StatBroadcast();
		statBrd.setStatsicTime(new Date());
		statBrd.setStatsicType(statsicType);
		statBrd.setStatsicDuation(statsicDuation);
		statBrd.setTotalNum(foundEBMs.size());
		statBrd.setSelfMadeNum(selfMadeNum);
		statBrd.setParentPushNum(parentPushNum);
		statBrd.setChildApplyNum(childApplyNum);
		statBrd.setOtherAlertNum(otherAlertNum);
		StatBroadcast svStat = statBrdDAO.save(statBrd);
        if(null == svStat) {
        	return;
        }
        
		List<RegionArea> broadcastAreas = regionAreaDAO.findAll(getBroadcastAreaCodes(foundEBMs));
		if(null == broadcastAreas) {
			broadcastAreas = new ArrayList<RegionArea>();
		}

		List<StatBroadcastArea> StatBrdArea2Save = new ArrayList<StatBroadcastArea>();
        for(RegionArea area : broadcastAreas) {
        	List<Ebm> hintEbms = hintEBM(area.getAreaCode(), foundEBMs);
        	StatBroadcastArea statBrdArea = fillStatBroadcastArea(svStat.getStatsicId(), area.getAreaCode(), area.getAreaName(), hintEbms);
        	StatBrdArea2Save.add(statBrdArea);
        }
        
    	StatBroadcastArea statBrdAreaOverall = fillStatBroadcastArea(svStat.getStatsicId(), "*", "所有区域", foundEBMs);
    	StatBrdArea2Save.add(statBrdAreaOverall);
    	
    	statBrdAreaDAO.save(StatBrdArea2Save);
	}
	
	/**
	 * 根据EBM区域计算EBM对应类型统计数量
	 * @param statsicId
	 * @param areaCode
	 * @param areaName
	 * @param hintEBMs
	 * @return StatBroadcastArea
	 */
	private StatBroadcastArea fillStatBroadcastArea(Integer statsicId, String areaCode, String areaName, List<Ebm> hintEBMs) {
		StatBroadcastArea statBrdArea = new StatBroadcastArea();
		statBrdArea.setStatsicId(statsicId);
		statBrdArea.setAreaCode(areaCode);
		statBrdArea.setAreaName(areaName);
		statBrdArea.setTotalNum(hintEBMs.size());
		
		int commonNum = 0;
		int redNum = 0;
		int orangeNum = 0;
		int yellowNum = 0;
		int blueNum = 0;
		for(Ebm ebm : hintEBMs) {
			if(EventSeverityEnum.common.getCode().equals(ebm.getSeverity())) {
				commonNum = commonNum + 1;
			} else if(EventSeverityEnum.red.getCode().equals(ebm.getSeverity())) {
				redNum = redNum + 1;
			} else if(EventSeverityEnum.orange.getCode().equals(ebm.getSeverity())) {
				orangeNum = orangeNum + 1;
			} else if(EventSeverityEnum.yellow.getCode().equals(ebm.getSeverity())) {
				yellowNum = yellowNum + 1;
			} else if(EventSeverityEnum.blue.getCode().equals(ebm.getSeverity())) {
				blueNum = blueNum + 1;
			} 
		}
		
		statBrdArea.setCommonNum(commonNum);
		statBrdArea.setRedNum(redNum);
		statBrdArea.setOrangeNum(orangeNum);
		statBrdArea.setYellowNum(yellowNum);
		statBrdArea.setBlueNum(blueNum);
		
		return statBrdArea;
	}
	
	private List<Ebm> hintEBM(String areaCode, List<Ebm> foundEBMs) {
		List<Ebm> hintEbms = new ArrayList<Ebm>();
		
		for(Ebm ebm : foundEBMs) {
			if(null != ebm.getAreaCode() && ebm.getAreaCode().trim().length() > 0) {
				for(String acd : StringUtils.split(ebm.getAreaCode(), ",")) {
					if(null != acd && areaCode.equals(acd.trim())) {
						hintEbms.add(ebm);
					}
				}
			}
		}
		
		return hintEbms;
	}
	
	private Set<String> getBroadcastAreaCodes(List<Ebm> foundEBMs) {
		Set<String> areaCodes = new HashSet<String>();
		for(Ebm ebm : foundEBMs) {
			if(null != ebm.getAreaCode() && ebm.getAreaCode().trim().length() > 0) {
				for(String acd : StringUtils.split(ebm.getAreaCode(), ",")) {
					if(null != acd && acd.trim().length() > 0) {
						areaCodes.add(acd.trim());
					}
				}
			}
		}
		
		return areaCodes;
	}
	
	private List<String> getChildPlatformIds() {
        List<String> childPlatformIds = new ArrayList<String>();
        
		final String selfPlatformId = sysParamDAO.findValueByKey(Constants.EBR_PLATFORM_ID);
		if(null == selfPlatformId || selfPlatformId.length() < 1) {
			return childPlatformIds;
		}
        
		List<EbrPlatform> childPlatforms = ebrPlatformDAO.findAll(new Specification<EbrPlatform>(){
			public Predicate toPredicate(Root<EbrPlatform> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				predicate.getExpressions().add(cb.equal(root.<String> get("parentPsEbrId"), selfPlatformId));
				return null;
			}
		});
		
		if(null != childPlatforms) {
			for(EbrPlatform platform : childPlatforms) {
				childPlatformIds.add(platform.getPsEbrId());
			}
		}
        
		return childPlatformIds;
	}

	private Ebd findEBDByEbmId(String ebmId) {
	   	 List<Ebd> rcvEBDs = ebdDAO.findByEbmId(ebmId);
	   	 
	   	 Ebd result = null;
	   	 if(null != rcvEBDs && rcvEBDs.size() > 0) {
	   		result = rcvEBDs.get(rcvEBDs.size() - 1);
	   	 }
	   	 
	   	 return result;
	}
	
	
	private String getParentPlatformId() {
		String result = null;
		
		String selfPlatformId = sysParamDAO.findValueByKey(Constants.EBR_PLATFORM_ID);
		if(null == selfPlatformId || selfPlatformId.length() < 1) {
			return result;
		}
		
		EbrPlatform selfPlatform = ebrPlatformDAO.findOne(selfPlatformId);
		if(null != selfPlatform) {
			result = selfPlatform.getParentPsEbrId();
		}
		
		return result;
	}

}
