package com.comtom.bc.server.repository.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.comtom.bc.server.repository.entity.ProgramInfo;
import com.comtom.bc.server.repository.support.CustomRepository;

/**
 * 节目信息管理-数据访问层接口定义<br>
 * 日常节目、应急节目和应急演练
 * 
 * @author zhucanhui
 *
 */
public interface ProgramDAO extends CustomRepository<ProgramInfo, Long> {

	/**
	 * 根据多条件查询
	 */
	Page<ProgramInfo> findAll(Specification<ProgramInfo> spec, Pageable pageable);
}
