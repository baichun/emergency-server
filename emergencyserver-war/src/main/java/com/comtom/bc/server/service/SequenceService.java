package com.comtom.bc.server.service;

import com.comtom.bc.server.repository.entity.SysSequence;

/**
 * 序列号信息-业务逻辑处理接口定义
 * 
 * @author zhucanhui
 *
 */
public interface SequenceService {

	/**
	 * 根据序列号名称获取序列号信息
	 * 
	 * @param name
	 * @return SysSequence
	 */
	public SysSequence findByName(String name);

	/**
	 * 生成ID
	 * 
	 * @param idname
	 * @return String
	 */
	public String createId(String idname);
}