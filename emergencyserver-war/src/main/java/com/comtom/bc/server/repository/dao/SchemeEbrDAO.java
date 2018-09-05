package com.comtom.bc.server.repository.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.comtom.bc.server.repository.entity.SchemeEbr;
import com.comtom.bc.server.repository.support.CustomRepository;

/**
 * 调度方案关联资源-数据访问层接口定义
 * 
 * @author zhucanhui
 *
 */
public interface SchemeEbrDAO extends CustomRepository<SchemeEbr, Integer> {

	/**
	 * 根据调度方案查询调度方案关联资源
	 * 
	 * @param schemeId
	 * @return List<SchemeEbr>
	 */
	public List<SchemeEbr> findBySchemeId(Integer schemeId);

	/**
	 * 根据调度方案Id删除关联的资源
	 * 
	 * @param schemeId
	 */
	@Query("DELETE FROM SchemeEbr WHERE schemeId=:schemeId")
	@Modifying
	@Transactional
	public void deleteSchemeEbr(@Param("schemeId") Integer schemeId);
}
