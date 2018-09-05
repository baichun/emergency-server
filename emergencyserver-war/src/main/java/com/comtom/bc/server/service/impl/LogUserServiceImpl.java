package com.comtom.bc.server.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.server.repository.dao.LogUserDAO;
import com.comtom.bc.server.repository.entity.LogUser;
import com.comtom.bc.server.req.LogUserReq;
import com.comtom.bc.server.service.LogUserService;
import com.comtom.bc.server.service.base.BaseService;

/**
 * 字典参数-业务逻辑处理接口定义
 * 
 * @author zhucanhui
 *
 */
@Service("LogUserService")
public class LogUserServiceImpl extends BaseService implements LogUserService {

	@Autowired
	private LogUserDAO logUserDAO;

	/**
	 * 保存用户操作日志信息
	 * 
	 * @param logUser
	 * @return
	 */
	public LogUser save(LogUser logUser) {
		return logUserDAO.save(logUser);
	}

	/**
	 * 查询用户操作日志列表
	 * 
	 * @param logUser
	 * @return Page<LogUser>
	 */
	public Page<LogUser> getLogs(LogUserReq req) {
		return logUserDAO.findAll(getSpec(req),
				buildPageRequest(req.getPageNum(), req.getPageSize(), getSort()));
	}

	/**
	 * 日志总记录数统计
	 * 
	 * @return long
	 */
	public long count(Integer portalType) {
		return logUserDAO.count(getCountSpec(portalType));
	}

	private Specification<LogUser> getCountSpec(final Integer portalType) {
		return new Specification<LogUser>() {
			@Override
			public Predicate toPredicate(Root<LogUser> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();

				if (CommonUtil.isNotEmpty(portalType)) {
					predicate.getExpressions().add(
							cb.equal(r.<Integer> get("portalType"), portalType));
				}

				return predicate;
			}
		};
	}

	/**
	 * 实现多条件模糊和带日期区间查询Specification
	 * 
	 * @param portalType
	 * @param account
	 * @param logContent
	 * @param startDate
	 * @param endDate
	 * @return Specification<LogUser>
	 */
	private Specification<LogUser> getSpec(LogUserReq req) {

		final String account = req.getAccount();
		final String logContent = req.getLogContent();
		final Date startDate = req.getStartDate();
		final Date endDate = req.getEndDate();
		final Integer portalType = req.getPortalType();

		return new Specification<LogUser>() {
			@Override
			public Predicate toPredicate(Root<LogUser> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (CommonUtil.isNotEmpty(account)) {
					predicate.getExpressions().add(
							cb.like(r.<String> get("userName"), "%" + StringUtils.trim(account)
									+ "%"));
				}

				if (CommonUtil.isNotEmpty(portalType)) {
					predicate.getExpressions().add(
							cb.equal(r.<Integer> get("portalType"), portalType));
				}

				if (CommonUtil.isNotEmpty(logContent)) {
					predicate.getExpressions().add(
							cb.like(r.<String> get("content"), "%" + StringUtils.trim(logContent)
									+ "%"));
				}

				if (CommonUtil.isNotEmpty(startDate)) {
					predicate.getExpressions().add(
							cb.greaterThanOrEqualTo(r.<Date> get("logTime"), startDate));
				}

				if (CommonUtil.isNotEmpty(endDate)) {

					Calendar cl = Calendar.getInstance();
					cl.setTime(endDate);
					cl.add(Calendar.DAY_OF_MONTH, +1);

					predicate.getExpressions().add(
							cb.lessThan(r.<Date> get("logTime"), cl.getTime()));
				}

				return predicate;
			}
		};
	}

	/**
	 * 获取排序对象Sort
	 * 
	 * @return Sort
	 */
	private Sort getSort() {
		Order orderTime = new Order(Direction.DESC, "logTime");
		List<Order> orders = new ArrayList<Order>();
		orders.add(orderTime);
		Sort sort = new Sort(orders);
		return sort;
	}
}