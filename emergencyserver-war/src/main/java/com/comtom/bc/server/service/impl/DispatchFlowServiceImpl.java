package com.comtom.bc.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.comtom.bc.common.result.ResultKey;
import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.dispatch.FlowConstants;
import com.comtom.bc.server.repository.dao.DispatchFlowDAO;
import com.comtom.bc.server.repository.entity.DispatchFlow;
import com.comtom.bc.server.req.FlowQueryReq;
import com.comtom.bc.server.service.DispatchFlowService;
import com.comtom.bc.server.service.base.BaseService;

/**
 * 调度流程（会话）-业务逻辑处理接口定义
 * 
 * @author zhucanhui
 *
 */
@Service("DispatchFlowService")
public class DispatchFlowServiceImpl extends BaseService implements DispatchFlowService {

	@Autowired
	private DispatchFlowDAO dispatchFlowDAO;

	/**
	 * 创建调度流程
	 * 
	 * @param dispatchFlow
	 * @return DispatchFlow
	 */
	public DispatchFlow save(DispatchFlow dispatchFlow) {
		return dispatchFlowDAO.save(dispatchFlow);
	}

	/**
	 * 根据节目Id获取流程信息
	 * 
	 * @param programId
	 * @return DispatchFlow
	 */
	public DispatchFlow findFlow(Long programId) {
		return dispatchFlowDAO.findByRelatedProgramId(programId);
	}

	/**
	 * 根据数据包Id获取流程信息
	 * 
	 * @param programId
	 * @return DispatchFlow
	 */
	public DispatchFlow findFlow(String ebdId) {
		return dispatchFlowDAO.findByRelatedEbdId(ebdId);
	}

	/**
	 * 根据条件参数获取调度流程信息列表
	 * 
	 * @param req
	 * @return Page<DispatchFlow>
	 */
	public Page<DispatchFlow> getFlow(FlowQueryReq req) {
		return dispatchFlowDAO.findAll(getFlowSpec(req),
				buildPageRequest(req.getPageNum(), req.getPageSize(), getFlowSort()));
	}

	/**
	 * 创建流程（根据节目或数据包）
	 * 
	 * @param programId
	 * @param flowStage
	 * @param flowState
	 * @return DispatchFlow
	 */
	public DispatchFlow createFlow(Long programId, String ebdId, Integer flowStage,
			Integer flowState) {

		// 节目创建成功，创建调度流程信息
		DispatchFlow dispatchFlow = new DispatchFlow();
		dispatchFlow.setCreateTime(new Date());
		dispatchFlow.setUpdateTime(new Date());
		dispatchFlow.setRelatedProgramId(programId);
		dispatchFlow.setRelatedEbdId(ebdId);
		dispatchFlow.setFlowStage(flowStage);
		dispatchFlow.setFlowState(flowState);
		return save(dispatchFlow);
	}

	/**
	 * 统计当前未完成调度流程信息
	 * 
	 * @return Map<String, Long>
	 */
	public Map<String, Long> statFlow() {

		Map<String, Long> statMap = new HashMap<String, Long>();
		Long stageResponse = dispatchFlowDAO.count(getStatSpec(FlowConstants.STAGE_RESPONSE, null));
		Long stageProcess = dispatchFlowDAO.count(getStatSpec(FlowConstants.STAGE_PROCESS, null));
		Long adjustScheme = dispatchFlowDAO.count(getStatSpec(null,
				FlowConstants.STATE_SCHEME_ADJUST));
		Long auditScheme = dispatchFlowDAO
				.count(getStatSpec(null, FlowConstants.STATE_SCHEME_AUDIT));

		statMap.put(ResultKey.STAGE_RESPONSE_COUNT, stageResponse);
		statMap.put(ResultKey.STAGE_PROCESS_COUNT, stageProcess);
		statMap.put(ResultKey.STATE_ADJUST_COUNT, adjustScheme);
		statMap.put(ResultKey.STATE_AUDIT_COUNT, auditScheme);

		return statMap;
	}

	/**
	 * 更新流程（根据节目或数据包）
	 * 
	 * @param programId
	 * @param ebdId
	 * @param flowStage
	 * @param flowState
	 * @return DispatchFlow
	 */
	public DispatchFlow updateFlow(Long programId, String ebdId, Integer flowStage,
			Integer flowState) {

		// 状态变化，更新调度流程信息
		DispatchFlow dispatchFlow = null;

		if (programId != null) {
			dispatchFlow = findFlow(programId);
		} else if (CommonUtil.isNotEmpty(ebdId)) {
			dispatchFlow = findFlow(ebdId);
		}

		dispatchFlow.setUpdateTime(new Date());
		dispatchFlow.setFlowStage(flowStage);
		dispatchFlow.setFlowState(flowState);

		return save(dispatchFlow);
	}
	
	/**
	 * 更新流程（根据流程编号）
	 * 
	 * @param flowId
	 * @param flowStage
	 * @param flowState
	 * @return DispatchFlow
	 */
	public DispatchFlow updateFlow(Long flowId, Integer flowStage,
			Integer flowState) {

		// 状态变化，更新调度流程信息
		DispatchFlow dispatchFlow = dispatchFlowDAO.findOne(flowId);

		dispatchFlow.setUpdateTime(new Date());
		dispatchFlow.setFlowStage(flowStage);
		dispatchFlow.setFlowState(flowState);

		return save(dispatchFlow);
	}

	/**
	 * 实现多条件模糊和带日期区间查询Specification
	 * 
	 * @param req
	 * @return Specification<DispatchFlow>
	 */
	private Specification<DispatchFlow> getFlowSpec(FlowQueryReq req) {

		// 查询参数
		final Long programId = req.getProgramId();
		final String ebdId = req.getEbdId();

		return new Specification<DispatchFlow>() {
			@Override
			public Predicate toPredicate(Root<DispatchFlow> r, CriteriaQuery<?> q,
					CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();

				if (CommonUtil.isNotEmpty(programId)) {
					predicate.getExpressions().add(cb.equal(r.<Long> get("programId"), programId));
				}

				if (CommonUtil.isNotEmpty(ebdId)) {
					predicate.getExpressions().add(cb.equal(r.<String> get("ebdId"), ebdId));
				}

				return predicate;
			}
		};
	}

	/**
	 * 实现多条件模糊和带日期区间查询Specification
	 * 
	 * @param req
	 * @return Specification<DispatchFlow>
	 */
	private Specification<DispatchFlow> getStatSpec(final Integer flowStage, final Integer flowState) {

		// 查询参数
		return new Specification<DispatchFlow>() {
			@Override
			public Predicate toPredicate(Root<DispatchFlow> r, CriteriaQuery<?> q,
					CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();

				if (CommonUtil.isNotEmpty(flowStage)) {
					predicate.getExpressions().add(
							cb.equal(r.<Integer> get("flowStage"), flowStage));
				}

				if (CommonUtil.isNotEmpty(flowState)) {
					predicate.getExpressions().add(
							cb.equal(r.<Integer> get("flowState"), flowState));
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
	private Sort getFlowSort() {
		Order orderTime = new Order(Direction.DESC, "createTime");
		List<Order> orders = new ArrayList<Order>();
		orders.add(orderTime);
		Sort sort = new Sort(orders);
		return sort;
	}

}