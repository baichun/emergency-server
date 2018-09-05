package com.comtom.bc.server.repository.dao;

import com.comtom.bc.server.repository.entity.ProgramStrategy;
import com.comtom.bc.server.repository.support.CustomRepository;

/**
 * 节目策略-数据访问层接口定义<br>
 * 
 * @author zhucanhui
 *
 */
public interface ProgramStrategyDAO extends CustomRepository<ProgramStrategy, Long> {

	/**
	 * 根据节目Id查询节目策略
	 * 
	 * @param programId
	 */
	public ProgramStrategy findByProgramId(Long programId);
}
