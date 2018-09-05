package com.comtom.bc.server.repository.dao;

import com.comtom.bc.server.repository.entity.SysSequence;
import com.comtom.bc.server.repository.support.CustomRepository;

/**
 * 序列号-数据访问层接口定义
 * 
 * @author zhucanhui
 *
 */
public interface SysSequenceDAO extends CustomRepository<SysSequence, String> {

	/**
	 * 根据序列号名称获取序列号信息
	 * 
	 * @param name
	 * @return SysSequence
	 */
	public SysSequence findByName(String name);
}
