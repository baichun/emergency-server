package com.comtom.bc.server.repository.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.comtom.bc.server.repository.entity.ProgramArea;
import com.comtom.bc.server.repository.support.CustomRepository;

/**
 * 节目区域-数据访问层接口定义<br>
 * 
 * @author zhucanhui
 *
 */
public interface ProgramAreaDAO extends CustomRepository<ProgramArea, Long> {

	/**
	 * 根据节目Id删除关联区域信息
	 */
	@Modifying
	@Query(value = "DELETE FROM bc_program_area WHERE programId=?1", nativeQuery = true)
	public void deleteArea(Long programId);

	/**
	 * 根据节目Id查询节目区域
	 * 
	 * @param programId
	 */
	public List<ProgramArea> findByProgramId(Long programId); 
}
