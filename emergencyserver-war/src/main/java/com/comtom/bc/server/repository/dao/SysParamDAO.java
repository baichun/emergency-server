package com.comtom.bc.server.repository.dao;

import com.comtom.bc.server.repository.entity.ParamInfo;
import com.comtom.bc.server.repository.entity.ProgramInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.comtom.bc.server.repository.entity.SysParam;
import com.comtom.bc.server.repository.support.CustomRepository;

/**
 * 键值参数-数据访问层接口定义
 * 
 * @author zhucanhui
 *
 */
public interface SysParamDAO extends CustomRepository<SysParam, String> {

	/**
	 * 根据键值参数Key获取对象
	 * 
	 * @param key
	 * @return String
	 */
	@Query("select value from SysParam where key=:key")
	public String findValueByKey(@Param(value = "key") String key);

	@Query("select s from SysParam s where key=:key")
	public SysParam findByKey(@Param(value = "key") String key);

	/**
	 * 根据多条件查询
	 */
	Page<SysParam> findAll(Specification<SysParam> spec, Pageable pageable);

}
