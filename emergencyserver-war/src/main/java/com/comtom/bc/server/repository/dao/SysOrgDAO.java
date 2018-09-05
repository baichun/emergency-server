package com.comtom.bc.server.repository.dao;

import com.comtom.bc.server.repository.entity.SysOrg;
import com.comtom.bc.server.repository.support.CustomRepository;

/**
 * 系统组织机构信息-数据访问层接口定义
 * 
 * @author zhucanhui
 *
 */
public interface SysOrgDAO extends CustomRepository<SysOrg, String> {

	/**
	 * 根据组织编号获取组织信息
	 * 
	 * @param cascadeId
	 * @return SysOrg
	 */
	public SysOrg findByCascadeId(String cascadeId);
}
