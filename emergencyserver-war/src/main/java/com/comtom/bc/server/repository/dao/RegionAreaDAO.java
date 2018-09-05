package com.comtom.bc.server.repository.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.comtom.bc.server.repository.entity.RegionArea;

/**
 * 系统用户信息-数据访问层接口定义
 * 
 * @author zhucanhui
 *
 */
public interface RegionAreaDAO extends JpaRepository<RegionArea, String> {

	/**
	 * 根据区域码和区域级别查询对应的区域信息列表
	 * 
	 * @param areaLevel
	 * @return List<RegionArea>
	 */
	@Query("select a from RegionArea a where areaLevel<:areaLevel")
	public List<RegionArea> findAreaDefault(@Param(value = "areaLevel") Integer areaLevel);

	/**
	 * 根据区域码和区域级别查询对应的区域信息列表
	 * 
	 * @param areaCode
	 * @return List<RegionArea>
	 */
	@Query("select a from RegionArea a where parentAreaCode=:areaCode")
	public List<RegionArea> findSubArea(@Param(value = "areaCode") String areaCode);

	@Query("select a from RegionArea a where areaCode=:areaCode")
	public RegionArea findSubAreaByCode(@Param(value = "areaCode") String areaCode);

}
