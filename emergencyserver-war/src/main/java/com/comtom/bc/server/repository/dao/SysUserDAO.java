package com.comtom.bc.server.repository.dao;

import com.comtom.bc.server.repository.entity.SysUser;
import com.comtom.bc.server.repository.support.CustomRepository;

/**
 * 系统用户信息-数据访问层接口定义
 * 
 * @author zhucanhui
 *
 */
public interface SysUserDAO extends CustomRepository<SysUser, String> {

	public SysUser findByAccountAndDeleteFlag(String account, String deleteFlag);

}
