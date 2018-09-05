package com.comtom.bc.server.service;

import java.util.List;

import com.comtom.bc.server.repository.entity.Region;
import com.comtom.bc.server.repository.entity.RegionArea;
import com.comtom.bc.server.req.RegionReq;

/**
 * 用户-用户管理业务逻辑处理接口定义
 * 
 * @author zhucanhui
 *
 */
public interface AreaService {

	/**
	 * 根据区域编码和区域级别查询区域信息列表
	 * 
	 * @return List<RegionArea>
	 */
	public List<RegionArea> findArea(String areaCode, Integer areaLevel);

	public List<Region> findAreaAll(RegionReq req, Integer areaLevel);

	public List<Region> findAreaAllChildren(RegionReq req, Integer areaLevel);

	public void insertAreaAllChildren(RegionReq req);

}