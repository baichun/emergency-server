package com.comtom.bc.server.repository.dao;

import java.util.List;

import com.comtom.bc.server.repository.entity.EbmResBs;
import com.comtom.bc.server.repository.support.CustomRepository;

/**
 * 数据包-数据访问层接口定义
 * 
 * @author zhucanhui
 *
 */
public interface EbmResBsDAO extends CustomRepository<EbmResBs, Integer> {

	/**
	 * 根据播发记录Id获取关联播发资源
	 * 
	 * @param ebmResourceId
	 * @return List<EbmResBs>
	 */
	public List<EbmResBs> findByEbmResourceId(String ebmResourceId);
}
