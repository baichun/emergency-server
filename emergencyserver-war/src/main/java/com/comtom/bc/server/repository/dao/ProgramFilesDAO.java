package com.comtom.bc.server.repository.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.comtom.bc.server.repository.entity.ProgramFiles;
import com.comtom.bc.server.repository.support.CustomRepository;

/**
 * 节目文件-数据访问层接口定义<br>
 * 
 * @author zhucanhui
 *
 */
public interface ProgramFilesDAO extends CustomRepository<ProgramFiles, Long> {

	/**
	 * 根据节目Id删除关联文件信息
	 */
	@Modifying
	@Query(value = "DELETE FROM bc_program_files WHERE programId=?1", nativeQuery = true)
	public void deleteFiles(Long programId);

	/**
	 * 根据节目Id查询节目文件
	 * 
	 * @param programId
	 */
	public List<ProgramFiles> findByProgramId(Long programId);
}
