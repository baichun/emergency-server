package com.comtom.bc.server.repository.dao;

import com.comtom.bc.server.repository.entity.Region;
import com.comtom.bc.server.repository.support.CustomRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统用户信息-数据访问层接口定义
 * 
 * @author zhucanhui
 *
 */
public interface RegionDAO extends CustomRepository<Region, String> {

	/**
	 * 根据区域码和区域级别查询对应的区域信息列表
	 *
	 * @param areaLevel
	 * @return List<RegionArea>
	 */
	@Query("select a from Region a where areaLevel<:areaLevel")
	public List<Region> findAreaDefault(@Param(value = "areaLevel") Integer areaLevel);

	@Modifying
	@Transactional
	@Query(value = "insert into bc_region_area select * from bc_region r1 where r1.areaCode like :areaCode% ",nativeQuery = true)
	public void insertAreaAllChildren(@Param(value = "areaCode") String areaCode);
}
