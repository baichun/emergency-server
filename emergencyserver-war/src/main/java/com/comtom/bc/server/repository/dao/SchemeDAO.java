package com.comtom.bc.server.repository.dao;

import com.comtom.bc.server.repository.entity.SchemeInfo;
import com.comtom.bc.server.repository.support.CustomRepository;

/**
 * 调度方案-数据访问层接口定义
 * 
 * @author zhucanhui
 *
 */
public interface SchemeDAO extends CustomRepository<SchemeInfo, Integer> {

	/**
	 * 根据节目Id获取对应调度方案信息
	 * @param programId
	 * @return SchemeInfo
	 */
	public SchemeInfo findByProgramId(Long programId);
}
