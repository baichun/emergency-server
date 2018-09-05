package com.comtom.bc.server.service.impl;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.common.utils.RegionUtil;
import com.comtom.bc.exchange.commom.SyncFlag;
import com.comtom.bc.server.repository.dao.EbrTerminalDAO;
import com.comtom.bc.server.repository.entity.EbrTerminal;
import com.comtom.bc.server.req.ResourceLoadReq;
import com.comtom.bc.server.req.TerminalStatReq;
import com.comtom.bc.server.service.EbrTerminalService;
import com.comtom.bc.server.service.ResIDGeneratorService;
import com.comtom.bc.server.service.base.BaseService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 资源-终端业务逻辑处理
 * 
 * @author kidsoul
 *
 */
@Service("EbrTerminalService")
public class EbrTerminalServiceImpl extends BaseService implements EbrTerminalService {

	@Autowired
	private EbrTerminalDAO ebrTerminalDAO;
	
    @Autowired
    private ResIDGeneratorService residGenService;

	@PersistenceContext
	private EntityManager em;

	private static final int TERM_SIZE=2000;


	/**
	 * 根据条件统计终端数
	 * 
	 * @param req
	 * @return long
	 */
	public long count(TerminalStatReq req) {
		return ebrTerminalDAO.count(getCountSpec(req));
	}

	/**
	 * 终端数量统计条件
	 * 
	 * @param req
	 * @return Specification<EbrTerminal>
	 */
	private Specification<EbrTerminal> getCountSpec(final TerminalStatReq req) {
		return new Specification<EbrTerminal>() {
			public Predicate toPredicate(Root<EbrTerminal> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (CommonUtil.isNotEmpty(req.getRelatedPsEbrId())) {
					predicate.getExpressions().add(
							cb.like(r.<String> get("relatedPsEbrId"),
									"%" + StringUtils.trim(req.getRelatedPsEbrId()) + "%"));
				}

				if (CommonUtil.isNotEmpty(req.getTerminalState())) {
					predicate.getExpressions().add(
							cb.equal(r.<Integer> get("terminalState"), req.getTerminalState()));
				}

				if (CommonUtil.isNotEmpty(req.getTerminalType())) {
					predicate.getExpressions().add(
							cb.equal(r.<Integer> get("terminalType"), req.getTerminalType()));
				}

				if (CommonUtil.isNotEmpty(req.getStartTime())) {
					predicate.getExpressions()
							.add(cb.greaterThanOrEqualTo(r.<Date> get("createTime"),
									req.getStartTime()));
				}

				if (CommonUtil.isNotEmpty(req.getEndTime())) {
					predicate.getExpressions().add(
							cb.lessThanOrEqualTo(r.<Date> get("createTime"), req.getEndTime()));
				}

				return predicate;
			}
		};
	}

	private Specification<EbrTerminal> getSearchWhereClause(final ResourceLoadReq searchReq) {
		return new Specification<EbrTerminal>() {
			public Predicate toPredicate(Root<EbrTerminal> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (CommonUtil.isNotEmpty(searchReq.getResourceName())) {
					predicate.getExpressions().add(
							cb.like(r.<String> get("terminalEbrName"),
									"%" + StringUtils.trim(searchReq.getResourceName()) + "%"));
				}

				if (CommonUtil.isNotEmpty(searchReq.getResourceId())) {
					predicate.getExpressions().add(
							cb.equal(r.<String> get("terminalEbrId"), searchReq.getResourceId()));
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
				if (CommonUtil.isNotEmpty(searchReq.getAreaCode())) {
					List<String> values = new ArrayList<String> ();
					String[] areaCodes = searchReq.getAreaCode().split(String.valueOf(Constants.COMMA_SPLIT));
					for (String areaCode : areaCodes) {
						values.add(areaCode);
					}
					Expression<String> exp = r.get("areaCode");
					exp.in(values);
					predicate.getExpressions().add(cb.in(exp));
				}

				return predicate;
			}
		};
	}

	private Sort getDefaultSort() {
		return new Sort(Direction.DESC, "updateTime", "terminalEbrId");
	}

	public Page<EbrTerminal> searchPage(ResourceLoadReq searchReq) {
		return ebrTerminalDAO
				.findAll(
						getSearchWhereClause(searchReq),
						buildPageRequest(searchReq.getPageNum(), searchReq.getPageSize(),
								getDefaultSort()));
	}

	public List<EbrTerminal> search(ResourceLoadReq searchReq) {
		return ebrTerminalDAO.findAll(getSearchWhereClause(searchReq), getDefaultSort());
	}

	private Specification<EbrTerminal> getListTerminalWhereClause(final boolean Incremental,final Date rptStartTime,
			final Date rptEndTime) {
		return new Specification<EbrTerminal>() {
			public Predicate toPredicate(Root<EbrTerminal> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (CommonUtil.isNotEmpty(rptStartTime)) {
					predicate.getExpressions().add(
							cb.greaterThanOrEqualTo(r.<Date> get("updateTime"), rptStartTime));
				}

				if (CommonUtil.isNotEmpty(rptEndTime)) {
					predicate.getExpressions().add(
							cb.lessThanOrEqualTo(r.<Date> get("updateTime"), rptEndTime));
				}
				//增量 查询未同步的数据
				if(Incremental){
					predicate.getExpressions().add(cb.equal(r.<Integer> get("syncFlag"), SyncFlag.nosync.getValue()));
				}
				return predicate;
			}
		};
	}


	private Specification<EbrTerminal> getTerminalStatusWhereClause(final boolean Incremental,final Date rptStartTime,
																  final Date rptEndTime) {
		return new Specification<EbrTerminal>() {
			public Predicate toPredicate(Root<EbrTerminal> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (CommonUtil.isNotEmpty(rptStartTime)) {
					predicate.getExpressions().add(
							cb.greaterThanOrEqualTo(r.<Date> get("updateTime"), rptStartTime));
				}

				if (CommonUtil.isNotEmpty(rptEndTime)) {
					predicate.getExpressions().add(
							cb.lessThanOrEqualTo(r.<Date> get("updateTime"), rptEndTime));
				}
				//增量 查询未同步的数据
				if(Incremental){
					predicate.getExpressions().add(cb.equal(r.<Integer> get("statusSyncFlag"), SyncFlag.nosync.getValue()));
				}
				return predicate;
			}
		};
	}

	public List<EbrTerminal> listTerminal(boolean Incremental, Date rptStartTime, Date rptEndTime) {
		return ebrTerminalDAO.findAll(getListTerminalWhereClause(Incremental,rptStartTime, rptEndTime),getDefaultSort());
	}

	public List<EbrTerminal> listTerminalStatus(boolean Incremental, Date rptStartTime, Date rptEndTime) {
		return ebrTerminalDAO.findAll(getTerminalStatusWhereClause(Incremental,rptStartTime, rptEndTime),getDefaultSort());
	}

	@Override
	public Page<EbrTerminal> listTerminalPage() {
		return ebrTerminalDAO
				.findAll(
						getListTerminalWhereClause(true,null,null),
						buildPageRequest(Constants.DEFAULT_PAGE_NUM, TERM_SIZE, getDefaultSort()));
	}

	@Override
	@Transactional
	public void updateEbrTerminal(List<EbrTerminal> ebrTerminals) {
		for(int i=0;i<ebrTerminals.size();i++) {
			em.merge(ebrTerminals.get(i));
			if (i % 100 == 0) {
				em.flush();
				em.clear();

			}
		}
	}

	private void copyProperties(EbrTerminal target, EbrTerminal source) {
		if (CommonUtil.isNotEmpty(source.getTerminalEbrName())) {
			target.setTerminalEbrName(source.getTerminalEbrName());
		}
		if (CommonUtil.isNotEmpty(source.getRelatedPsEbrId())) {
			target.setRelatedPsEbrId(source.getRelatedPsEbrId());
		}
		if (CommonUtil.isNotEmpty(source.getLongitude())) {
			target.setLongitude(source.getLongitude());
		}
		if (CommonUtil.isNotEmpty(source.getLatitude())) {
			target.setLatitude(source.getLatitude());
		}
		if (CommonUtil.isNotEmpty(source.getTerminalType())) {
			target.setTerminalType(source.getTerminalType());
		}
		if (null != source.getTerminalState()) {
			target.setTerminalState(source.getTerminalState());
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
		if (CommonUtil.isNotEmpty(source.getStatusSyncFlag())) {
			target.setStatusSyncFlag(source.getStatusSyncFlag());
		}
		if (CommonUtil.isNotEmpty(source.getParam1())) {
			target.setParam1(source.getParam1());
		}
		if (CommonUtil.isNotEmpty(source.getParam2())) {
			target.setParam2(source.getParam2());
		}
	}

	@Transactional
	public EbrTerminal saveOrUpdate(EbrTerminal terminal) {
		EbrTerminal result = null;
		if (StringUtils.isNotBlank(terminal.getTerminalEbrId())) {
			EbrTerminal exist = ebrTerminalDAO.getOne(terminal.getTerminalEbrId());
			if (null != exist) {
				copyProperties(exist, terminal);
				result = ebrTerminalDAO.save(exist);
			}
		} else {
			String areaCD = "00";
			String trmId = residGenService.generateResourceID(terminal.getTerminalType(),Constants.EBR_TYPE_TERMINAL,  areaCD);
			terminal.setTerminalEbrId(trmId);
			result = ebrTerminalDAO.save(terminal);
		}
		
		return result;
	}

	public List<EbrTerminal> findTerminalListByIds(List<String> terminalIds) {
		return ebrTerminalDAO.findAll(terminalIds);
	}

	public List<EbrTerminal> findByRelatedPsEbrId(String relatedPlatformId) {
		return ebrTerminalDAO.findByRelatedPsEbrId(relatedPlatformId);
	}

	@Override
	public EbrTerminal findByPK(String terminalId) {
		return ebrTerminalDAO.findOne(terminalId);
	}

	@Override
	public List<EbrTerminal> findByAreaCode(String areaCode) {
		final String shortAreaCode= RegionUtil.long2Short(areaCode);
		Specification<EbrTerminal> specification = new Specification<EbrTerminal>() {

			@Override
			public Predicate toPredicate(Root<EbrTerminal> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (CommonUtil.isNotEmpty(shortAreaCode)) {
					predicate.getExpressions().add(
							cb.like(root.<String> get("terminalEbrId"), "____"+shortAreaCode+"%"));
				}
				return predicate;
			}
			
		};
		return ebrTerminalDAO.findAll(specification);
	}

}
