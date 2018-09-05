package com.comtom.bc.server.service;

import java.util.Map;

import org.springframework.data.domain.Page;

import com.comtom.bc.server.repository.entity.DispatchFlow;
import com.comtom.bc.server.req.FlowQueryReq;

/**
 * 调度流程-业务逻辑处理接口定义
 * 
 * @author zhucanhui
 *
 */
public interface DispatchFlowService {

	/**
	 * 创建调度流程
	 * 
	 * @param dispatchFlow
	 * @return DispatchFlow
	 */
	public DispatchFlow save(DispatchFlow dispatchFlow);

	/**
	 * 根据节目Id获取流程信息
	 * 
	 * @param programId
	 * @return DispatchFlow
	 */
	public DispatchFlow findFlow(Long programId);

	/**
	 * 根据数据包Id获取流程信息
	 * 
	 * @param ebdId
	 * @return DispatchFlow
	 */
	public DispatchFlow findFlow(String ebdId);

	/**
	 * 根据条件参数获取调度流程信息列表
	 * 
	 * @param req
	 * @return Page<DispatchFlow>
	 */
	public Page<DispatchFlow> getFlow(FlowQueryReq req);

	/**
	 * 创建流程（根据节目或数据包）
	 * 
	 * @param programId
	 * @param flowStage
	 * @param flowState
	 * @return DispatchFlow
	 */
	public DispatchFlow createFlow(Long programId, String ebdId, Integer flowStage,
			Integer flowState);

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
			Integer flowState);

	/**
	 * 更新流程（根据流程编号）
	 * 
	 * @param flowId
	 * @param flowStage
	 * @param flowState
	 * @return DispatchFlow
	 */
	public DispatchFlow updateFlow(Long flowId, Integer flowStage, Integer flowState);

	/**
	 * 统计当前未完成调度流程信息
	 * 
	 * @return Map<String, Long>
	 */
	public Map<String, Long> statFlow();

}