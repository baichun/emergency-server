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
import com.comtom.bc.server.repository.dao.EbrPlatformDAO;
import com.comtom.bc.server.repository.dao.RegionAreaDAO;
import com.comtom.bc.server.repository.dao.VEbrPlatformDAO;
import com.comtom.bc.server.repository.entity.EbrPlatform;
import com.comtom.bc.server.repository.entity.RegionArea;
import com.comtom.bc.server.repository.entity.VEbrPlatform;
import com.comtom.bc.server.req.ResourceLoadReq;
import com.comtom.bc.server.service.EbrPlatformService;
import com.comtom.bc.server.service.ResIDGeneratorService;
import com.comtom.bc.server.service.base.BaseService;
import com.google.common.collect.Lists;

/**
 * 资源-应急广播平台业务逻辑处理
 * 
 * @author kidsoul
 *
 */
@Service("EbrPlatformService")
public class EbrPlatformServiceImpl extends BaseService implements EbrPlatformService {

	@Autowired
	private EbrPlatformDAO ebrPlatformDAO;

	@Autowired
	private RegionAreaDAO regionAreaDAO;

	@Autowired
	private VEbrPlatformDAO vEbrPlatformDAO;

	@Autowired
	private ResIDGeneratorService residGenService;

	/**
	 * 查询应急广播平台资源列表记录数
	 * 
	 * @return long
	 */
	public long count() {
		return ebrPlatformDAO.count();
	}

	/**
	 * 查询应急广播平台资源列表
	 */
	public List<EbrPlatform> list(EbrPlatform ebrAdaptor) {
		Iterable<EbrPlatform> iter = (Iterable<EbrPlatform>) ebrPlatformDAO.findAll();
		List<EbrPlatform> recordList = Lists.newArrayList(iter);
		return recordList;
	}

	/**
	 * 查询应急广播平台资源列表
	 */
	public Page<VEbrPlatform> findAll(ResourceLoadReq req) {
		VEbrPlatform vEbrPlatform = new VEbrPlatform();
		return vEbrPlatformDAO.findByAuto(vEbrPlatform,
				buildPageRequest(req.getPageNum(), req.getPageSize()));
	}

	/**
	 * 查询应急广播平台资源列表(分页查询)
	 */
	public Page<EbrPlatform> listPage(EbrPlatform ebrPlatform, int pageNumber, int pageSize) {
		return ebrPlatformDAO.findByAuto(ebrPlatform, buildPageRequest(pageNumber, pageSize));
	}

	/**
	 * 根据应急广播平台名称查询应急广播平台信息
	 */
	@Transactional
	public Page<EbrPlatform> findByPlatformName(String psEbrName) {
		EbrPlatform likeCondition = new EbrPlatform();
		likeCondition.setPsEbrName(psEbrName);
		return ebrPlatformDAO.findByAuto(likeCondition, null);
	}

	public EbrPlatform findByPsId(String platformId) {
		return ebrPlatformDAO.findOne(platformId);
	}

	private Specification<EbrPlatform> getSearchWhereClause(final ResourceLoadReq searchReq) {
		return new Specification<EbrPlatform>() {
			public Predicate toPredicate(Root<EbrPlatform> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (CommonUtil.isNotEmpty(searchReq.getResourceName())) {
					predicate.getExpressions().add(
							cb.like(r.<String> get("psEbrName"),
									"%" + StringUtils.trim(searchReq.getResourceName()) + "%"));
				}

				if (CommonUtil.isNotEmpty(searchReq.getState())) {
					predicate.getExpressions().add(
							cb.equal(r.<String> get("psState"), searchReq.getState()));
				}

				if (CommonUtil.isNotEmpty(searchReq.getResourceId())) {
					predicate.getExpressions().add(
							cb.equal(r.<String> get("psEbrId"), searchReq.getResourceId()));
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
		return new Sort(Direction.DESC, "updateTime", "psEbrId");
	}

	public Page<EbrPlatform> searchPage(ResourceLoadReq searchReq) {
		Page<EbrPlatform> result = ebrPlatformDAO
				.findAll(
						getSearchWhereClause(searchReq),
						buildPageRequest(searchReq.getPageNum(), searchReq.getPageSize(),
								getDefaultSort()));

		if (null != result) {
			assginConcernedValues(result.getContent());
		}
		return result;
	}

	public List<EbrPlatform> search(ResourceLoadReq searchReq) {
		List<EbrPlatform> result = ebrPlatformDAO.findAll(getSearchWhereClause(searchReq),
				getDefaultSort());
		assginConcernedValues(result);
		return result;
	}

	private Specification<EbrPlatform> getListPlatformWhereClause(final boolean Incremental,
			final Date rptStartTime, final Date rptEndTime) {
		return new Specification<EbrPlatform>() {
			public Predicate toPredicate(Root<EbrPlatform> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (CommonUtil.isNotEmpty(rptStartTime)) {
					predicate.getExpressions().add(
							cb.greaterThanOrEqualTo(r.<Date> get("updateTime"), rptStartTime));
				}

				if (CommonUtil.isNotEmpty(rptEndTime)) {
					predicate.getExpressions().add(
							cb.lessThanOrEqualTo(r.<Date> get("updateTime"), rptEndTime));
				}
				// 增量 查询未同步的数据
				if (Incremental) {
					predicate.getExpressions().add(
							cb.equal(r.<Integer> get("syncFlag"), SyncFlag.nosync.getValue()));
				}
				return predicate;
			}
		};
	}

	private Specification<EbrPlatform> getSatusPlatformWhereClause(final boolean Incremental,
																  final Date rptStartTime, final Date rptEndTime) {
		return new Specification<EbrPlatform>() {
			public Predicate toPredicate(Root<EbrPlatform> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (CommonUtil.isNotEmpty(rptStartTime)) {
					predicate.getExpressions().add(
							cb.greaterThanOrEqualTo(r.<Date> get("updateTime"), rptStartTime));
				}

				if (CommonUtil.isNotEmpty(rptEndTime)) {
					predicate.getExpressions().add(
							cb.lessThanOrEqualTo(r.<Date> get("updateTime"), rptEndTime));
				}
				if (Incremental) {
					predicate.getExpressions().add(
							cb.equal(r.<Integer> get("statusSyncFlag"), SyncFlag.nosync.getValue()));
				}

				return predicate;
			}
		};
	}

	public List<EbrPlatform> listPlatform(boolean Incremental, Date rptStartTime, Date rptEndTime) {
		return ebrPlatformDAO
				.findAll(getListPlatformWhereClause(Incremental, rptStartTime, rptEndTime),
						getDefaultSort());
	}

	public List<EbrPlatform> statusSyncPlatform(boolean Incremental, Date rptStartTime, Date rptEndTime) {
		return ebrPlatformDAO
				.findAll(getSatusPlatformWhereClause(Incremental, rptStartTime, rptEndTime),
						getDefaultSort());
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
	
	private void assginConcernedValues(List<EbrPlatform> found) {
		if (null == found) {
			return;
		}

		for (EbrPlatform platform : found) {
			if (null != platform.getAreaCode()) {
				List<String> areaCds = new ArrayList<String>();
				for (String str : platform.getAreaCode().split(",")) {
					areaCds.add(str.trim());
				}
				List<RegionArea> coveredAreas = regionAreaDAO.findAll(areaCds);
				platform.setCoveredAreas(coveredAreas);
				platform.setAreaName(areaNames(coveredAreas));
			}
		}
	}

	private void copyProperties(EbrPlatform target, EbrPlatform source) {
		if (CommonUtil.isNotEmpty(source.getPsUrl())) {
			target.setPsUrl(source.getPsUrl());
		}
		if (CommonUtil.isNotEmpty(source.getPsEbrName())) {
			target.setPsEbrName(source.getPsEbrName());
		}
		if (null != source.getPsState()) {
			target.setPsState(source.getPsState());
		}
		if (CommonUtil.isNotEmpty(source.getAreaCode())) {
			target.setAreaCode(source.getAreaCode());
		}
		if (CommonUtil.isNotEmpty(source.getPsType())) {
			target.setPsType(source.getPsType());
		}
		if (CommonUtil.isNotEmpty(source.getParentPsEbrId())) {
			target.setParentPsEbrId(source.getParentPsEbrId());
		}
		if (CommonUtil.isNotEmpty(source.getPsAddress())) {
			target.setPsAddress(source.getPsAddress());
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
		if (null != source.getPlatLevel()) {
			target.setPlatLevel(source.getPlatLevel());
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
		if (null != source.getStatusSyncFlag()) {
			target.setStatusSyncFlag(source.getStatusSyncFlag());
		}
	}

	@Transactional
	public EbrPlatform saveOrUpdate(EbrPlatform platform) {
		EbrPlatform result = null;
		if (StringUtils.isNotBlank(platform.getPsEbrId())) {
			EbrPlatform exist = ebrPlatformDAO.getOne(platform.getPsEbrId());
			if (null != exist) {
				copyProperties(exist, platform);
				result = ebrPlatformDAO.save(exist);
			}
		} else {
			String areaCD = platform.getAreaCode().split(",")[0];
			String platformId = residGenService.generateResourceID(platform.getPsType(),
					Constants.EBR_THIS_LEVEL, areaCD);
			platform.setPsEbrId(platformId);
			result = ebrPlatformDAO.save(platform);
		}

		return result;
	}

	public EbrPlatform add(EbrPlatform platform) {
		return ebrPlatformDAO.save(platform);
	}

	public EbrPlatform update(EbrPlatform platform) {
		if (StringUtils.isNotBlank(platform.getPsEbrId())) {
			EbrPlatform exist = ebrPlatformDAO.getOne(platform.getPsEbrId());
			if (null != exist) {
				copyProperties(exist, platform);
				return ebrPlatformDAO.save(exist);
			}
		}
		return null;
	}

	public boolean batchDelete(List<String> platformIds) {
		boolean delResult = false;
		List<EbrPlatform> platformList = ebrPlatformDAO.findAll(platformIds);
		if (null != platformList && platformList.size() > 0) {
			ebrPlatformDAO.deleteInBatch(platformList);
			delResult = true;
		}
		return delResult;
	}

	public List<EbrPlatform> findPlatformListByIds(List<String> platformIds) {
		List<EbrPlatform> foundResult = new ArrayList<EbrPlatform>();
		if (null == platformIds || platformIds.size() < 1) {
			return foundResult;
		}
		return ebrPlatformDAO.findAll(platformIds);
	}

	public EbrPlatform findOne(String platformErbId) {
		return ebrPlatformDAO.findOne(platformErbId);
	}

}
