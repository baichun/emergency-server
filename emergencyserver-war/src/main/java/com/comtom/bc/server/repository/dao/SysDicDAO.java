package com.comtom.bc.server.repository.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.comtom.bc.server.repository.entity.SysDic;

/**
 * 字典参数-数据访问层接口定义
 * 
 * @author zhucanhui
 *
 */
public interface SysDicDAO extends CrudRepository<SysDic, Long> {

	/**
	 * 根据字典索引KEY获取对应的字典列表值<br>
	 * 关联查询字典索引表和字典表
	 * 
	 * @param key
	 * @return List<SysDic>
	 */
	@Query("SELECT d FROM SysDic d JOIN d.sysDicIndex i WHERE i.key = :key ")
	public Set<SysDic> findCodeByKey(@Param(value = "key") String key);
}
