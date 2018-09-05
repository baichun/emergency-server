package com.comtom.bc.server.repository.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.comtom.bc.server.repository.entity.ProgramTime;
import com.comtom.bc.server.repository.support.CustomRepository;
import org.springframework.data.repository.query.Param;

/**
 * 节目时间段-数据访问层接口定义<br>
 * 
 * @author zhucanhui
 *
 */
public interface ProgramTimeDAO extends CustomRepository<ProgramTime, Long> {

	/**
	 * 根据策略Id删除关联区域信息
	 */
	@Modifying
	//@Query(value = "DELETE FROM bc_program_strategy WHERE strategyId=?1", nativeQuery = true)
	@Query(value = "DELETE FROM ProgramTime WHERE strategyId=:strategyId")
	public void deleteTime(@Param(value = "strategyId") Long strategyId);

	/**
	 * 根据节目Id查询节目文件
	 * 
	 * @param strategyId
	 */
	public List<ProgramTime> findByStrategyId(Long strategyId);

}
