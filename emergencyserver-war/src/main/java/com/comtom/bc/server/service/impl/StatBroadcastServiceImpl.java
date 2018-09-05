package com.comtom.bc.server.service.impl;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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
import com.comtom.bc.server.req.StatsicListReq;
import com.comtom.bc.server.req.StatsicQueryReq;
import com.comtom.bc.server.service.StatBroadcastService;

/**
 * 广播数据统计-业务逻辑处理接口定义
 * 
 * @author zhucanhui
 *
 */
@Service("StatBroadcastService")
public class StatBroadcastServiceImpl implements StatBroadcastService {

	@Autowired
	private StatBroadcastDAO statBrdDAO;
	
	@Autowired
	private StatBroadcastAreaDAO statBrdAreaDAO;

	@Autowired
	private SysParamDAO sysParamDAO;
	
	@Autowired
	private EbmDAO ebmDAO;
	
	@Autowired
	private EbdDAO ebdDAO;
	
	@Autowired
	private EbrPlatformDAO ebrPlatformDAO;
	
	@Autowired
	private RegionAreaDAO regionAreaDAO;
	
	/**
	 * 根据统计类型找出广播次数统计列表
	 * 
	 * @param searchReq
	 * @return
	 */
	public List<StatBroadcast> search(final StatsicListReq searchReq) {
		List<StatBroadcast> result = statBrdDAO.findAll(new Specification<StatBroadcast>() {
			public Predicate toPredicate(Root<StatBroadcast> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (CommonUtil.isNotEmpty(searchReq.getStatsicType())) {
					predicate.getExpressions().add(
							cb.equal(root.<Integer> get("statsicType"), searchReq.getStatsicType()));
				}
				
				return predicate;
			}
			
		}, new Sort(Direction.DESC, "statsicTime"));
		
		assginConcernedValues(result);
		
		return result;
	}

	private void assginConcernedValues(List<StatBroadcast> result) {
		if (null == result) {
			return;
		}
		
		final List<Integer> brdStatsicIds = new ArrayList<Integer>();
		for(StatBroadcast brd : result) {
			brdStatsicIds.add(brd.getStatsicId());
		}
		
		List<StatBroadcastArea> brdStatAreaList = statBrdAreaDAO.findAll(new Specification<StatBroadcastArea>(){
			public Predicate toPredicate(Root<StatBroadcastArea> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(null != brdStatsicIds && brdStatsicIds.size() > 0) {
					predicate.getExpressions().add(root.<Integer> get("statsicId").in(brdStatsicIds));
				}
				return predicate;
			}
			
		});
		
		for(StatBroadcast brd : result) {
			brd.setAreaBrdStatsics(areaBrdStats(brd.getStatsicId(), brdStatAreaList));
			brd.setSeverityOverall(severityOverall(brd.getStatsicId(), brdStatAreaList));
		}
	}
	
	private List<StatBroadcastArea> areaBrdStats(Integer brdStatsicId, List<StatBroadcastArea> brdStatAreaList) {
		List<StatBroadcastArea> statBrdAreas = new ArrayList<StatBroadcastArea>();
		if(null == brdStatAreaList || brdStatAreaList.size() < 1) {
			return statBrdAreas;
		}
		
		final String overallAreaCode = "*";
		
		for(StatBroadcastArea stat : brdStatAreaList) {
			if(brdStatsicId.equals(stat.getStatsicId()) && !overallAreaCode.equals(stat.getAreaCode())) {
				statBrdAreas.add(stat);
			}
		}
		
		return statBrdAreas;
	}
	
	private StatBroadcastArea severityOverall(Integer brdStatsicId, List<StatBroadcastArea> brdStatAreaList) {
		if(null == brdStatAreaList || brdStatAreaList.size() < 1) {
			return null;
		}
		
		final String overallAreaCode = "*";
		StatBroadcastArea overall = null;
		for(StatBroadcastArea stat : brdStatAreaList) {
			if(brdStatsicId.equals(stat.getStatsicId()) && overallAreaCode.equals(stat.getAreaCode())) {
				overall = stat;
				break;
			}
		}
		
		return overall;
	}

	public List<StatBroadcast> queryStatsic(StatsicQueryReq req) {
		Date cutoffTime = req.getCutoffTime();
		if(null == cutoffTime) {
			cutoffTime = new Date();
		}
		
		Integer statsicType = req.getStatsicType();
		if(null == statsicType) {
			statsicType = Constants.REPORT_MONTHLY;
		}
		
		if(Constants.REPORT_MONTHLY != statsicType && Constants.REPORT_SEASONLY != statsicType 
				&& Constants.REPORT_HALF_YEARLY != statsicType && Constants.REPORT_YEARLY != statsicType) {
			return null;
		}
		
		Date startTime = getStartTime(statsicType, cutoffTime);
		
		//3-月，4-季度，5-半年，6-年度
		StatBroadcast statBrd = fillBrdStatsic(startTime, cutoffTime, statsicType, getStatsicDuation(statsicType, cutoffTime));
		List<StatBroadcast> statBrds = new ArrayList<StatBroadcast>();
		if(null != statBrd) {
			statBrds.add(statBrd);
		}
		
		return statBrds;
	}

	private Date getStartTime(int statsicType, Date cutoffTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(cutoffTime);

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

	private String getStatsicDuation(int statsicType, Date cutoffTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(cutoffTime);

		String duation = null;

        if (Constants.REPORT_MONTHLY == statsicType) {
			duation = calendar.get(Calendar.YEAR) + "年"
					+ (calendar.get(Calendar.MONTH) + 1) + "月";
		} else if (Constants.REPORT_SEASONLY == statsicType) {
			duation = calendar.get(Calendar.YEAR) + "年"
					+ (calendar.get(Calendar.MONTH) / 3 + 1) + "季度";
		} else if (Constants.REPORT_HALF_YEARLY == statsicType) {
			duation = calendar.get(Calendar.YEAR)
					+ "年"
					+ (calendar.get(Calendar.MONTH) == Calendar.JANUARY ? "上半年"
							: "下半年");
		} else if (Constants.REPORT_YEARLY == statsicType) {
			duation = calendar.get(Calendar.YEAR) + "年";
		}

		return duation;
	}

	private StatBroadcast fillBrdStatsic(final Date startTime, final Date endTime,
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
			return null;
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
		statBrd.setStatsicId(-1);
        
		List<RegionArea> broadcastAreas = regionAreaDAO.findAll(getBroadcastAreaCodes(foundEBMs));
		if(null == broadcastAreas) {
			broadcastAreas = new ArrayList<RegionArea>();
		}

		List<StatBroadcastArea> StatBrdArea2Display = new ArrayList<StatBroadcastArea>();
        for(RegionArea area : broadcastAreas) {
        	List<Ebm> hintEbms = hintEBM(area.getAreaCode(), foundEBMs);
        	StatBroadcastArea statBrdArea = fillStatBroadcastArea(statBrd.getStatsicId(), area.getAreaCode(), area.getAreaName(), hintEbms);
        	StatBrdArea2Display.add(statBrdArea);
        }
        statBrd.setAreaBrdStatsics(StatBrdArea2Display);
    	StatBroadcastArea statBrdAreaOverall = fillStatBroadcastArea(statBrd.getStatsicId(), "*", "所有区域", foundEBMs);
    	statBrd.setSeverityOverall(statBrdAreaOverall);
    	
    	return statBrd;
	}
	
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