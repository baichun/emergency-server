package com.comtom.bc.server.repository.dao;

import com.comtom.bc.server.repository.entity.DispatchFlow;
import com.comtom.bc.server.repository.support.CustomRepository;

/**
 * 调度流程（会话）-数据访问层接口定义
 * 
 * @author zhucanhui
 *
 */
public interface DispatchFlowDAO extends CustomRepository<DispatchFlow, Long> {

	/**
	 * 根据节目Id查询调度会话流程
	 * 
	 * @param programId
	 */
	public DispatchFlow findByRelatedProgramId(Long relatedProgramId);

	/**
	 * 根据数据包Id查询调度会话流程
	 * 
	 * @param relatedEbdId
	 */
	public DispatchFlow findByRelatedEbdId(String relatedEbdId);
}
