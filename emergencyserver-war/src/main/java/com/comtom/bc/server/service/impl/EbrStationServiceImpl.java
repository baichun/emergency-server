package com.comtom.bc.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.exchange.commom.SyncFlag;
import com.comtom.bc.server.repository.dao.EbrStationDAO;
import com.comtom.bc.server.repository.dao.RegionAreaDAO;
import com.comtom.bc.server.repository.entity.EbrStation;
import com.comtom.bc.server.repository.entity.RegionArea;
import com.comtom.bc.server.req.ResourceLoadReq;
import com.comtom.bc.server.service.EbrStationService;
import com.comtom.bc.server.service.ResIDGeneratorService;
import com.comtom.bc.server.service.base.BaseService;

/**
 * 资源-台站业务逻辑处理
 * 
 * @author kidsoul
 *
 */
@Service("EbrStationService")
public class EbrStationServiceImpl extends BaseService implements EbrStationService {
	@Autowired
	private EbrStationDAO ebrStationDAO;

	@Autowired
	private RegionAreaDAO regionAreaDAO;
	
    @Autowired
    private ResIDGeneratorService residGenService;

	private Specification<EbrStation> getSearchWhereClause(final ResourceLoadReq searchReq) {
		return new Specification<EbrStation>() {
			public Predicate toPredicate(Root<EbrStation> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (CommonUtil.isNotEmpty(searchReq.getResourceName())) {
					predicate.getExpressions().add(
							cb.like(r.<String> get("stationName"),
									"%" + StringUtils.trim(searchReq.getResourceName()) + "%"));
				}

				if (CommonUtil.isNotEmpty(searchReq.getResourceId())) {
					predicate.getExpressions().add(
							cb.equal(r.<String> get("stationEbrId"), searchReq.getResourceId()));
				}

				if (CommonUtil.isNotEmpty(searchReq.getAreaCode())) {
					Predicate p1 = cb.like(r.<String> get("areaCode"),
							"%," + StringUtils.trim(searchReq.getAreaCode()) + "%");
					Predicate p2 = cb.like(r.<String> get("areaCode"),
							"% " + StringUtils.trim(searchReq.getAreaCode()) + "%");
					Predicate p3 = cb.like(r.<String> get("areaCode"),
							StringUtils.trim(searchReq.getAreaCode()) + "%");
					predicate.getExpressions().add(cb.or(p1, p2, p3));
				}

				if (CommonUtil.isNotEmpty(searchReq.getCreatTimeStart())) {
					predicate.getExpressions().add(
							cb.greaterThanOrEqualTo(r.<Date> get("createTime"),
									searchReq.getCreatTimeStart()));
				}

				if (CommonUtil.isNotEmpty(searchReq.getCreateTimeEnd())) {
					predicate.getExpressions().add(
							cb.lessThanOrEqualTo(r.<Date> get("createTime"),
									searchReq.getCreateTimeEnd()));
				}

				return predicate;
			}
		};
	}
	
	private Sort getDefaultSort() {
		return new Sort(Direction.DESC, "updateTime", "stationEbrId");
	}

	public Page<EbrStation> searchPage(ResourceLoadReq searchReq) {
		Page<EbrStation> result = ebrStationDAO.findAll(getSearchWhereClause(searchReq),
				buildPageRequest(searchReq.getPageNum(), searchReq.getPageSize(), getDefaultSort()));
		if (null != result) {
			assginConcernedValues(result.getContent());
		}

		return result;
	}

	public List<EbrStation> search(ResourceLoadReq searchReq) {
		List<EbrStation> result = ebrStationDAO.findAll(getSearchWhereClause(searchReq), getDefaultSort());
		assginConcernedValues(result);

		return result;
	}
	
	private Specification<EbrStation> getListStationWhereClause(final boolean Incremental,final Date rptStartTime, final Date rptEndTime) {
		return new Specification<EbrStation>() {
			public Predicate toPredicate(Root<EbrStation> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
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
	
	public List<EbrStation> listStation(boolean Incremental, Date rptStartTime, Date rptEndTime) {
        return ebrStationDAO.findAll(getListStationWhereClause(Incremental,rptStartTime, rptEndTime), getDefaultSort());
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
	
	private void assginConcernedValues(List<EbrStation> found) {
		if (null == found) {
			return;
		}
		for (EbrStation station : found) {
			if (null != station.getAreaCode()) {
				List<String> areaCds = new ArrayList<String>();
				for (String str : station.getAreaCode().split(",")) {
					areaCds.add(str.trim());
				}
				List<RegionArea> coveredAreas = regionAreaDAO.findAll(areaCds);
				station.setCoveredAreas(coveredAreas);
				station.setAreaName(areaNames(coveredAreas));
			}
		}
	}

	private void copyProperties(EbrStation target, EbrStation source) {
		if (CommonUtil.isNotEmpty(source.getStationName())) {
			target.setStationName(source.getStationName());
		}
		if (CommonUtil.isNotEmpty(source.getStationAddress())) {
			target.setStationAddress(source.getStationAddress());
		}
		if (CommonUtil.isNotEmpty(source.getStationType())) {
			target.setStationType(source.getStationType());
		}
		if (CommonUtil.isNotEmpty(source.getContact())) {
			target.setContact(source.getContact());
		}
		if (CommonUtil.isNotEmpty(source.getPhoneNumber())) {
			target.setPhoneNumber(source.getPhoneNumber());
		}
		if (CommonUtil.isNotEmpty(source.getLongitude())) {
			target.setLongitude(source.getLongitude());
		}
		if (CommonUtil.isNotEmpty(source.getLatitude())) {
			target.setLatitude(source.getLatitude());
		}
		if (CommonUtil.isNotEmpty(source.getAreaCode())) {
			target.setAreaCode(source.getAreaCode());
		}
		if (CommonUtil.isNotEmpty(source.getRelatedPsEbrId())) {
			target.setRelatedPsEbrId(source.getRelatedPsEbrId());
		}	
		if (null != source.getCreateTime()) {
			target.setCreateTime(source.getCreateTime());
		}
		if (null != source.getUpdateTime()) {
			target.setUpdateTime(source.getUpdateTime());
		}
		if (null != source.getSyncFlag()) {
			target.setSyncFlag(source.getSyncFlag());
		}		
	}

	@Transactional
	public EbrStation saveOrUpdate(EbrStation station) {
		EbrStation result = null;
		if (StringUtils.isNotBlank(station.getStationEbrId())) {
			EbrStation exist = ebrStationDAO.getOne(station.getStationEbrId());
			if (null != exist) {
				copyProperties(exist, station);
				result = ebrStationDAO.save(exist);
			}
		} else {
			String areaCD = station.getAreaCode().split(",")[0];
			String stationId = residGenService.generateResourceID(station.getStationType(),Constants.EBR_THIS_LEVEL,  areaCD);
			station.setStationEbrId(stationId);
			result = ebrStationDAO.save(station);
		}
		
		return result;
	}

	public List<EbrStation> findStationListByIds(List<String> ebrStationIds) {
		return ebrStationDAO.findAll(ebrStationIds);
	}
	
	public EbrStation findOne(String stationId) {
		return ebrStationDAO.findOne(stationId);
	}

	public List<EbrStation> findByRelatedPsEbrId(String relatedPlatformId) {
		return ebrStationDAO.findByRelatedPsEbrId(relatedPlatformId);
	}

}
