package com.comtom.bc.server.repository.dao;

import com.comtom.bc.server.repository.entity.SysModule;
import com.comtom.bc.server.repository.support.CustomRepository;

/**
 * 系统模块信息-数据访问层接口定义
 * 
 * @author zhucanhui
 *
 */
public interface SysModuleDAO extends CustomRepository<SysModule, String> {
	
	/**
	 * 根据模块Id获取对应的模块信息
	 * @param id
	 * @return SysModule
	 */
	public SysModule findById(String id);

}
