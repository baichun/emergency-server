package com.comtom.bc.server.repository.dao;

import java.util.List;

import com.comtom.bc.server.repository.entity.EbmRes;
import com.comtom.bc.server.repository.support.CustomRepository;

/**
 * 播发记录关联资源-数据访问层接口定义
 * 
 * @author zhucanhui
 *
 */
public interface EbmResDAO extends CustomRepository<EbmRes, String> {

	/**
	 * 根据播发记录Id获取关联播发资源
	 * 
	 * @param brdItemId
	 * @return
	 */
	public List<EbmRes> findByBrdItemId(String brdItemId);
}
