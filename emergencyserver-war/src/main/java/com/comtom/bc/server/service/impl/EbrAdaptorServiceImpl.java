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
import com.comtom.bc.server.repository.dao.EbrAdaptorDAO;
import com.comtom.bc.server.repository.dao.RegionAreaDAO;
import com.comtom.bc.server.repository.entity.EbrAdaptor;
import com.comtom.bc.server.repository.entity.RegionArea;
import com.comtom.bc.server.req.ResourceLoadReq;
import com.comtom.bc.server.service.EbrAdaptorService;
import com.comtom.bc.server.service.ResIDGeneratorService;
import com.comtom.bc.server.service.base.BaseService;

/**
 * 资源-消息接收设备业务逻辑处理
 * 
 * @author kidsoul
 *
 */
@Service("EbrAdaptorService")
public class EbrAdaptorServiceImpl extends BaseService implements EbrAdaptorService {
	@Autowired
	private EbrAdaptorDAO ebrAdaptorDAO;

	@Autowired
	private RegionAreaDAO regionAreaDAO;
	
    @Autowired
    private ResIDGeneratorService residGenService;

	private Specification<EbrAdaptor> getSearchWhereClause(final ResourceLoadReq searchReq) {
		return new Specification<EbrAdaptor>() {
			public Predicate toPredicate(Root<EbrAdaptor> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (CommonUtil.isNotEmpty(searchReq.getResourceName())) {
					predicate.getExpressions().add(
							cb.like(r.<String> get("asEbrName"),
									"%" + StringUtils.trim(searchReq.getResourceName()) + "%"));
				}

				if (CommonUtil.isNotEmpty(searchReq.getResourceId())) {
					predicate.getExpressions().add(
							cb.equal(r.<String> get("asEbrId"), searchReq.getResourceId()));
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
		return new Sort(Direction.DESC, "updateTime", "asEbrId");
	}

	public Page<EbrAdaptor> searchPage(ResourceLoadReq searchReq) {
		Page<EbrAdaptor> result = ebrAdaptorDAO.findAll(getSearchWhereClause(searchReq),
				buildPageRequest(searchReq.getPageNum(), searchReq.getPageSize(), getDefaultSort()));
		if (null != result) {
			assginConcernedValues(result.getContent());
		}

		return result;
	}

	public List<EbrAdaptor> search(ResourceLoadReq searchReq) {
		List<EbrAdaptor> result = ebrAdaptorDAO.findAll(getSearchWhereClause(searchReq), getDefaultSort());
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
	
	private void assginConcernedValues(List<EbrAdaptor> found) {
		if (null == found) {
			return;
		}

		for (EbrAdaptor adaptor : found) {
			if (null != adaptor.getAreaCode()) {
				List<String> areaCds = new ArrayList<String>();
				for (String str : adaptor.getAreaCode().split(",")) {
					areaCds.add(str.trim());
				}
				List<RegionArea> coveredAreas = regionAreaDAO.findAll(areaCds);
				adaptor.setCoveredAreas(coveredAreas);
				adaptor.setAreaName(areaNames(coveredAreas));
			}
		}
	}

	private Specification<EbrAdaptor> getListAdaptorWhereClause(final boolean Incremental, final Date rptStartTime, final Date rptEndTime) {
		return new Specification<EbrAdaptor>() {
			public Predicate toPredicate(Root<EbrAdaptor> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
					if (CommonUtil.isNotEmpty(rptStartTime)) {
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(r.<Date> get("updateTime"), rptStartTime));
					}
					if (CommonUtil.isNotEmpty(rptEndTime)) {
						predicate.getExpressions().add(cb.lessThanOrEqualTo(r.<Date> get("updateTime"),rptEndTime));
					}
					//增量 查询未同步的数据
					if(Incremental){
						predicate.getExpressions().add(cb.equal(r.<Integer> get("syncFlag"), SyncFlag.nosync.getValue()));
					}
					return predicate;
				}
		};
	}
	
	public List<EbrAdaptor> listAdaptor(boolean Incremental, Date rptStartTime, Date rptEndTime) {                
        return ebrAdaptorDAO.findAll(getListAdaptorWhereClause(Incremental,rptStartTime, rptEndTime), getDefaultSort());
	}

	private void copyProperties(EbrAdaptor target, EbrAdaptor source) {
		if (CommonUtil.isNotEmpty(source.getAsEbrName())) {
			target.setAsEbrName(source.getAsEbrName());  
		}
		if (CommonUtil.isNotEmpty(source.getAsUrl())) {
			target.setAsUrl(source.getAsUrl());
		}
		if (CommonUtil.isNotEmpty(source.getAsType())) {
			target.setAsType(source.getAsType());
		}
		if (CommonUtil.isNotEmpty(source.getRelatedRsEbrId())) {
			target.setRelatedRsEbrId(source.getRelatedRsEbrId());
		}
		if (CommonUtil.isNotEmpty(source.getRelatedPsEbrId())) {
			target.setRelatedPsEbrId(source.getRelatedPsEbrId());
		}
		if (CommonUtil.isNotEmpty(source.getAreaCode())) {
			target.setAreaCode(source.getAreaCode()); 
		}
		if (CommonUtil.isNotEmpty(source.getLongitude())) {
			target.setLongitude(source.getLongitude());
		}
		if (CommonUtil.isNotEmpty(source.getLatitude())) {
			target.setLatitude(source.getLatitude());
		}
		if (null != source.getAsState()) {
			target.setAsState(source.getAsState());
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
		if (CommonUtil.isNotEmpty(source.getParam1())) {
			target.setParam1(source.getParam1()); 
		}
		if (CommonUtil.isNotEmpty(source.getParam2())) {
			target.setParam2(source.getParam2()); 
		}
	}
	
	@Transactional
	public EbrAdaptor saveOrUpdate(EbrAdaptor adaptor) {
		EbrAdaptor result = null;
		if(StringUtils.isNotBlank(adaptor.getAsEbrId())) {
			EbrAdaptor exist = ebrAdaptorDAO.getOne(adaptor.getAsEbrId());
			if (null != exist) {
				copyProperties(exist, adaptor);
				result = ebrAdaptorDAO.save(exist);
			}
		} else {
			String areaCD = adaptor.getAreaCode().split(",")[0];
			String adaptorId = residGenService.generateResourceID( adaptor.getAsType(),Constants.EBR_TYPE_ADAPTOR, areaCD);
			adaptor.setAsEbrId(adaptorId);
			result = ebrAdaptorDAO.save(adaptor);
		}
		
		return result;
	}

	public List<EbrAdaptor> findAdaptorListByIds(List<String> adaptorIds) {
		return ebrAdaptorDAO.findAll(adaptorIds);
	}

	public List<EbrAdaptor> findByRelatedRsEbrId(String relatedStationId) {
		return ebrAdaptorDAO.findByRelatedRsEbrId(relatedStationId);
	}

	public EbrAdaptor findOne(String relatedAsEbrId) {
		return ebrAdaptorDAO.findOne(relatedAsEbrId);
	}

	public List<EbrAdaptor> findByRelatedPsEbrId(String relatedPlatformId) {
		return ebrAdaptorDAO.findByRelatedPsEbrId(relatedPlatformId);
	}
}
