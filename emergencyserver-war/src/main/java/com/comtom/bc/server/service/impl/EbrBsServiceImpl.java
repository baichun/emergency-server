package com.comtom.bc.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.exchange.commom.SyncFlag;
import com.comtom.bc.server.repository.dao.EbrBsDAO;
import com.comtom.bc.server.repository.dao.EbrWorktimeDAO;
import com.comtom.bc.server.repository.dao.RegionAreaDAO;
import com.comtom.bc.server.repository.dao.WorktimeDAO;
import com.comtom.bc.server.repository.entity.EbrBroadcast;
import com.comtom.bc.server.repository.entity.EbrWorktime;
import com.comtom.bc.server.repository.entity.RegionArea;
import com.comtom.bc.server.repository.entity.Worktime;
import com.comtom.bc.server.req.ResourceLoadReq;
import com.comtom.bc.server.req.WorkTime;
import com.comtom.bc.server.service.EbrBsService;
import com.comtom.bc.server.service.ResIDGeneratorService;
import com.comtom.bc.server.service.base.BaseService;
import com.google.common.collect.Lists;

/**
 * 资源-播出系统业务逻辑处理
 * 
 * @author kidsoul
 *
 */
@Service("EbrBsService")
public class EbrBsServiceImpl extends BaseService implements EbrBsService {

	@Autowired
	private EbrBsDAO ebrBsDAO;
	
	@Autowired
	private EbrWorktimeDAO ebrWorktimeDAO;
	
	@Autowired
	private WorktimeDAO worktimeDAO;
	
	@Autowired
	private RegionAreaDAO regionAreaDAO;
	
    @Autowired
    private ResIDGeneratorService residGenService;

	/**
	 * 查询播出系统资源列表记录数
	 * 
	 * @return long
	 */
	public long count() {
		return ebrBsDAO.count();
	}

	/**
	 * 查询播出系统资源列表
	 */
	public List<EbrBroadcast> list(EbrBroadcast ebrBroadcast) {
		Iterable<EbrBroadcast> iter = (Iterable<EbrBroadcast>)ebrBsDAO.findAll();
		List<EbrBroadcast> recordList = Lists.newArrayList(iter);
		return recordList;
	}
	
	/**
	 * 查询播出系统资源列表(分页查询)
	 */
	public List<EbrBroadcast> listPage(int pageNumber, int pageSize) {
		Iterable<EbrBroadcast> iter = (Iterable<EbrBroadcast>)ebrBsDAO.findAll(buildPageRequest(pageNumber, pageSize));
		List<EbrBroadcast> recordList = Lists.newArrayList(iter);
		return recordList;
	}
	
	/**
	 * 根据播出系统名称查询播出系统信息
	 */
	@Transactional
	public EbrBroadcast findByBsName(String bsName){
		return ebrBsDAO.findByBsName(bsName);
	}

	private Specification<EbrBroadcast> getSearchWhereClause(final ResourceLoadReq searchReq, final List<String> resourceIds) {
		return new Specification<EbrBroadcast>() {
			public Predicate toPredicate(Root<EbrBroadcast> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (CommonUtil.isNotEmpty(searchReq.getResourceName())) {
					predicate.getExpressions().add(
							cb.like(r.<String> get("bsName"),
									"%" + StringUtils.trim(searchReq.getResourceName()) + "%"));
				}

				if (CommonUtil.isNotEmpty(searchReq.getResourceId())) {
					predicate.getExpressions().add(
							cb.equal(r.<String> get("bsEbrId"), searchReq.getResourceId()));
				}
				
				if(null != resourceIds && resourceIds.size() > 0) {
					predicate.getExpressions().add(r.<String> get("bsEbrId").in(resourceIds));
				}
				
				if(CommonUtil.isNotEmpty(searchReq.getAreaCode())) {	
					Predicate p1 = cb.like(r.<String> get("areaCode"), "%," + StringUtils.trim(searchReq.getAreaCode()) + "%");
					Predicate p2 = cb.like(r.<String> get("areaCode"), "% " + StringUtils.trim(searchReq.getAreaCode()) + "%");
					Predicate p3 = cb.like(r.<String> get("areaCode"), StringUtils.trim(searchReq.getAreaCode()) + "%");
					predicate.getExpressions().add(cb.or(p1, p2, p3));
				}

				if (CommonUtil.isNotEmpty(searchReq.getCreatTimeStart())) {
					predicate.getExpressions().add(
							cb.greaterThanOrEqualTo(r.<Date> get("createTime"), searchReq.getCreatTimeStart()));
				}

				if (CommonUtil.isNotEmpty(searchReq.getCreateTimeEnd())) {
					predicate.getExpressions().add(
							cb.lessThanOrEqualTo(r.<Date> get("createTime"), searchReq.getCreateTimeEnd()));
				}

				return predicate;
			}
		};
	}
	
	private Sort getDefaultSort() {
		return new Sort(Direction.DESC, "updateTime", "bsEbrId");
	}
	
	public Page<EbrBroadcast> searchPage(ResourceLoadReq searchReq) {
		List<String> resourceIds = filterResourceIds(searchReq.getWorkTimeSwitch(), searchReq.isWorkTimeFullyMatch());
		Page<EbrBroadcast> result = ebrBsDAO.findAll(getSearchWhereClause(searchReq, resourceIds), buildPageRequest(searchReq.getPageNum(), searchReq.getPageSize(), getDefaultSort()));
		if(null != result) {
			assginConcernedValues(result.getContent());
		}
		
		return result;
	}
	
	public List<EbrBroadcast> search(ResourceLoadReq searchReq) {
		List<String> resourceIds = filterResourceIds(searchReq.getWorkTimeSwitch(), searchReq.isWorkTimeFullyMatch());
		List<EbrBroadcast> result = ebrBsDAO.findAll(getSearchWhereClause(searchReq, resourceIds), getDefaultSort());
		assginConcernedValues(result);
		
		return result;
	}
	
	private String areaNames(List<RegionArea> coveredAreas) {
		StringBuffer nameBuf = new StringBuffer();
		if(null == coveredAreas || coveredAreas.size() < 1) {
			return nameBuf.toString();
		}
		
		for(RegionArea ar : coveredAreas) {
		    nameBuf.append(ar.getAreaName()).append(",");
		}
		nameBuf.deleteCharAt(nameBuf.lastIndexOf(","));
		
		return nameBuf.toString();
	}
	
	private void assginConcernedValues(List<EbrBroadcast> found) {
		if(null == found) {
			return;
		}
		
		for(EbrBroadcast bdcast : found) {
			if(null != bdcast.getAreaCode()) {
				List<String> areaCds = new ArrayList<String>();
				for(String str : bdcast.getAreaCode().split(",")) {
					areaCds.add(str.trim());
				}
				List<RegionArea> coveredAreas = regionAreaDAO.findAll(areaCds);
				bdcast.setCoveredAreas(coveredAreas);
				bdcast.setAreaName(areaNames(coveredAreas));
			}
			
			final String bdcastId = bdcast.getBsEbrId();
			List<EbrWorktime> ebrWtLst = ebrWorktimeDAO.findAll(new Specification<EbrWorktime>() {
				public Predicate toPredicate(Root<EbrWorktime> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					Predicate predicate = cb.conjunction();
					predicate.getExpressions().add(cb.equal(root.<String> get("ebrId"), bdcastId));
					return predicate;
				}
		
			});
			
			if(null != ebrWtLst) {
				List<String> wtIds = new ArrayList<String>();
				for(EbrWorktime ewt : ebrWtLst) {
					wtIds.add(ewt.getWorktimeId());
				}
				if(wtIds.size() < 1) {
					wtIds.add(Constants.INVALID_STRING_ID);
				}
				bdcast.setWorkTimeSwitch(worktimeDAO.findAll(wtIds));
			}
		}
	}
	
	
	private List<String> filterResourceIds(List<WorkTime> workTimeSwitch, boolean workTimeFullyMatch) {
		List<String> resourceIds = new ArrayList<String>();
		if(null != workTimeSwitch && workTimeSwitch.size() > 0) {
			List<String> ebrWorkTimeIds = matchWorkTimeIds(workTimeSwitch, workTimeFullyMatch);
			List<EbrWorktime> found = findEbrWorktime(ebrWorkTimeIds);
			if(null != found) {
				for(EbrWorktime ebrWt : found) {
					resourceIds.add(ebrWt.getEbrId());
				}
			}
			
			if(resourceIds.size() < 1) {
				resourceIds.add(Constants.INVALID_STRING_ID);
			}
		}
		
		return resourceIds;
	}
	
	/**
	 * 根据给出的时间表特征找出符合要求的工作时间表
	 * 
	 * @param searchVO 
	 * @param completeMatch 此参数为真，要求的时间段完全符合；否则可以交叉，不完全重叠
	 * @return
	 */
	private List<String> matchWorkTimeIds(List<WorkTime> searchVO, boolean completeMatch) {
		List<String> matchIds = new ArrayList<String>();
		if(null == searchVO || searchVO.size() < 1) {
			return matchIds;
		}
		List<Worktime> found = worktimeDAO.findAll();
		if(null == found || found.size() < 1) {
			return matchIds;
		}
		
		for(Worktime wt : found) {
			for(WorkTime sch : searchVO) {
				if(wt.getWeekDay() == sch.getWeekDay()) {
					if(CommonUtil.compareTime(wt.getStartTime(), sch.getStartTime()) <= 0) {
						if(CommonUtil.compareTime(wt.getStopTime(), sch.getStopTime()) >= 0) {
							matchIds.add(wt.getId());
						}
					}
				}
			}
		}
		
		if(completeMatch == false) {
			for(Worktime wt : found) {
				for(WorkTime sch : searchVO) {
					if(wt.getWeekDay() == sch.getWeekDay()) {
						if(CommonUtil.compareTime(wt.getStartTime(), sch.getStartTime()) <= 0 && CommonUtil.compareTime(wt.getStopTime(), sch.getStartTime()) >= 0) {
							if(!matchIds.contains(wt.getId())) {
								matchIds.add(wt.getId());
							}
						}
						
						if(CommonUtil.compareTime(wt.getStartTime(), sch.getStopTime()) <= 0 && CommonUtil.compareTime(wt.getStopTime(), sch.getStopTime()) >= 0 ) {
							if(!matchIds.contains(wt.getId())) {
								matchIds.add(wt.getId());
							}
						}
					}
				}
			}
		}
		
		return matchIds;
	}

	/**
	 * 根据 工作时间表ID列表 找出符合要求的播出系统资源ID列表
	 * 
	 * @param timeIds 工作时间表ID列表
	 * @return 资源ID与工作时间表ID对应关系
	 */
	private List<EbrWorktime> findEbrWorktime(final List<String> timeIds) {
		if(null == timeIds || timeIds.size() < 1) {
			return new ArrayList<EbrWorktime>();
		}
		return ebrWorktimeDAO.findAll(new Specification<EbrWorktime>() {
			public Predicate toPredicate(Root<EbrWorktime> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(null != timeIds && timeIds.size() > 0) {
					predicate.getExpressions().add(root.<String> get("worktimeId").in(timeIds));
				}
				return predicate;
			}
			
		});
	}
	
	private Specification<EbrBroadcast> getListBroadcastWhereClause(final boolean Incremental,final Date rptStartTime, final Date rptEndTime) {
		return new Specification<EbrBroadcast>() {
			public Predicate toPredicate(Root<EbrBroadcast> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
					if (CommonUtil.isNotEmpty(rptStartTime)) {
						predicate.getExpressions().add(
								cb.greaterThanOrEqualTo(r.<Date> get("updateTime"), 
										rptStartTime));
					}

					if (CommonUtil.isNotEmpty(rptEndTime)) {
						predicate.getExpressions().add(
								cb.lessThanOrEqualTo(r.<Date> get("updateTime"),
										rptEndTime));
					}
					//增量 查询未同步的数据
					if(Incremental){
						predicate.getExpressions().add(cb.equal(r.<Integer> get("syncFlag"), SyncFlag.nosync.getValue()));
					}
					return predicate;
				}
		};
	}


	private Specification<EbrBroadcast> getBroadcastStatusWhereClause(final boolean Incremental,final Date rptStartTime, final Date rptEndTime) {
		return new Specification<EbrBroadcast>() {
			public Predicate toPredicate(Root<EbrBroadcast> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (CommonUtil.isNotEmpty(rptStartTime)) {
					predicate.getExpressions().add(
							cb.greaterThanOrEqualTo(r.<Date> get("updateTime"),
									rptStartTime));
				}

				if (CommonUtil.isNotEmpty(rptEndTime)) {
					predicate.getExpressions().add(
							cb.lessThanOrEqualTo(r.<Date> get("updateTime"),
									rptEndTime));
				}
				//增量 查询未同步的数据
				if(Incremental){
					predicate.getExpressions().add(cb.equal(r.<Integer> get("statusSyncFlag"), SyncFlag.nosync.getValue()));
				}
				return predicate;
			}
		};
	}
	
	public List<EbrBroadcast> listBroadcast(boolean Incremental, Date rptStartTime, Date rptEndTime) {
        List<EbrBroadcast> result = ebrBsDAO.findAll(getListBroadcastWhereClause(Incremental,rptStartTime, rptEndTime), getDefaultSort());
        assginConcernedValues(result);
        return result;
	}

	public List<EbrBroadcast> broadcastStatus(boolean Incremental, Date rptStartTime, Date rptEndTime) {
		List<EbrBroadcast> result = ebrBsDAO.findAll(getBroadcastStatusWhereClause(Incremental,rptStartTime, rptEndTime), getDefaultSort());
		assginConcernedValues(result);
		return result;
	}

	public List<EbrBroadcast> findBroadcastListByIds(List<String> broadcastIds) {
		return ebrBsDAO.findAll(broadcastIds);
	}

	public List<EbrBroadcast> findByRelatedRsEbrId(String relatedStationId) {
		return ebrBsDAO.findByRelatedRsEbrId(relatedStationId);
	}

	public EbrBroadcast findOne(String broadcastErbId) {
		return ebrBsDAO.findOne(broadcastErbId);
	}

	public List<EbrBroadcast> findByRelatedPsEbrId(String relatedPlatformId) {
		return ebrBsDAO.findByRelatedPsEbrId(relatedPlatformId);
	}

	public List<EbrBroadcast> findByRelatedAsEbrId(String relatedAdaptorId) {
		return ebrBsDAO.findByRelatedAsEbrId(relatedAdaptorId);
	}

	@Transactional
	public EbrBroadcast saveOrUpdate(EbrBroadcast broadcast) {
		EbrBroadcast result = null;
		String bsEbrId=broadcast.getBsEbrId();
		if(StringUtils.isEmpty(bsEbrId)){
			//新增
			String areaCD = broadcast.getAreaCode().split(",")[0];
			bsEbrId = residGenService.generateResourceID(broadcast.getBsType(),Constants.EBR_TYPE_BROADCAST, areaCD);
			broadcast.setBsEbrId(bsEbrId);
			result = ebrBsDAO.save(broadcast);
		}else{
			//修改
			EbrBroadcast exist = ebrBsDAO.getOne(broadcast.getBsEbrId());
			if(exist==null){
				throw new RuntimeException("ebrId:"+broadcast.getBsEbrId()+"数据不存在.");
			}else{
				copyProperties(exist, broadcast);
				result = ebrBsDAO.save(exist);				
			}
		}
		
		List<EbrWorktime> ebrWorktimes=new ArrayList<EbrWorktime>();
		List<Worktime> worktimes = broadcast.getWorkTimeSwitch();
		if(null != result && null != worktimes) {
			ebrWorktimeDAO.deleteByEbrId(bsEbrId);
			for (Worktime worktime : worktimes) {
				final String startTime=worktime.getStartTime();
				final String stopTime=worktime.getStopTime();
				final Integer weekDay=worktime.getWeekDay();
				if(StringUtils.isEmpty(stopTime)&&StringUtils.isEmpty(startTime)&&weekDay==null){
					continue ;
				}
				Specification<Worktime> spec=new Specification<Worktime>() {
					@Override
					public Predicate toPredicate(Root<Worktime> root, CriteriaQuery<?> query,
							CriteriaBuilder cb) {
						Predicate predicate = cb.conjunction();
						if(StringUtils.isNotBlank(startTime)){
							predicate.getExpressions().add(cb.equal(root.<String> get("startTime"),startTime));							
						}
						if(StringUtils.isNotBlank(stopTime)){
							predicate.getExpressions().add(cb.equal(root.<String> get("stopTime"),stopTime));
						}
						if(weekDay!=null){
							predicate.getExpressions().add(cb.equal(root.<Integer> get("weekDay"),weekDay));							
						}
						return predicate;
					}
				};
				String workTimeId=null;
				Worktime worktime2=worktimeDAO.findOne(spec);
				if(worktime2==null){
					worktime.setId(UUID.randomUUID().toString());
					workTimeId=worktime.getId();
					worktimeDAO.save(worktime);
				}else{
					workTimeId=worktime2.getId();
				}
				
				EbrWorktime ebrWorktime=new EbrWorktime();
				ebrWorktime.setEbrId(broadcast.getBsEbrId());
				ebrWorktime.setWorktimeId(workTimeId);
				ebrWorktime.setId(UUID.randomUUID().toString());
				ebrWorktimes.add(ebrWorktime);
			}
			ebrWorktimeDAO.save(ebrWorktimes);
		}
		return result;
	}
	
	private void copyProperties(EbrBroadcast target, EbrBroadcast source) {
		if(CommonUtil.isNotEmpty(source.getBsName())) {        
			target.setBsName(source.getBsName());  
		}
		if(CommonUtil.isNotEmpty(source.getBsUrl())) {
			target.setBsUrl(source.getBsUrl());   
		}
		if(CommonUtil.isNotEmpty(source.getBsType())) {
			target.setBsType(source.getBsType());   
		}
		if(CommonUtil.isNotEmpty(source.getLongitude())) {
			target.setLongitude(source.getLongitude()); 
		}
		if(CommonUtil.isNotEmpty(source.getLatitude())) {
			target.setLatitude(source.getLatitude());   
		}
		if(CommonUtil.isNotEmpty(source.getSquare())) {
			target.setSquare(source.getSquare());    
		}
		if(CommonUtil.isNotEmpty(source.getAreaCode())) {
			target.setAreaCode(source.getAreaCode());     
		}
		if(CommonUtil.isNotEmpty(source.getPopulation())) {
			target.setPopulation(source.getPopulation());  
		}
		if(CommonUtil.isNotEmpty(source.getLanguageCode())) {
			target.setLanguageCode(source.getLanguageCode()); 
		}	       
		if(CommonUtil.isNotEmpty(source.getEquipRoom())) {
			target.setEquipRoom(source.getEquipRoom());    
		}
		if(CommonUtil.isNotEmpty(source.getRadioChannelName())) {
			target.setRadioChannelName(source.getRadioChannelName());   
		}
		if(CommonUtil.isNotEmpty(source.getRadioFreq())) {
			target.setRadioFreq(source.getRadioFreq()); 
		}
		if(CommonUtil.isNotEmpty(source.getRadioPower())) {
			target.setRadioPower(source.getRadioPower()); 
		}	       
		if(CommonUtil.isNotEmpty(source.getBackup())) {
			target.setBackup(source.getBackup());    
		}
		if(CommonUtil.isNotEmpty(source.getAutoSwitch())) {
			target.setAutoSwitch(source.getAutoSwitch());      
		}
		if(CommonUtil.isNotEmpty(source.getRemoteControl())) {
			target.setRemoteControl(source.getRemoteControl()); 
		}
		if(CommonUtil.isNotEmpty(source.getExperiment())) {
			target.setExperiment(source.getExperiment()); 
		}		      
		if(CommonUtil.isNotEmpty(source.getTvChannelName())) {
			target.setTvChannelName(source.getTvChannelName()); 
		}	       
		if(CommonUtil.isNotEmpty(source.getTvFreq())) {
			target.setTvFreq(source.getTvFreq());     
		}
		if(CommonUtil.isNotEmpty(source.getProgramNum())) {
			target.setProgramNum(source.getProgramNum());       
		}
		if(CommonUtil.isNotEmpty(source.getTvChannelNum())) {
			target.setTvChannelNum(source.getTvChannelNum());  
		}
		if(CommonUtil.isNotEmpty(source.getBsState())) {
			target.setBsState(source.getBsState());
		}		         		   
		if(CommonUtil.isNotEmpty(source.getRelatedPsEbrId())) {
			target.setRelatedPsEbrId(source.getRelatedPsEbrId()); 
		}	       
		if(CommonUtil.isNotEmpty(source.getRelatedRsEbrId())) {
			target.setRelatedRsEbrId(source.getRelatedRsEbrId());    
		}
		if(CommonUtil.isNotEmpty(source.getRelatedAsEbrId())) {
			target.setRelatedAsEbrId(source.getRelatedAsEbrId());       
		}
		if(CommonUtil.isNotEmpty(source.getCreateTime())) {
			target.setCreateTime(source.getCreateTime());   
		}
		if(CommonUtil.isNotEmpty(source.getUpdateTime())) {
			target.setUpdateTime(source.getUpdateTime()); 
		}		                  
		if(CommonUtil.isNotEmpty(source.getSyncFlag())) {
			target.setSyncFlag(source.getSyncFlag()); 
		}		 
	}
}
