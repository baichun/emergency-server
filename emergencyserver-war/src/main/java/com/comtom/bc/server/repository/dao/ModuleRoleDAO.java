package com.comtom.bc.server.repository.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.comtom.bc.server.repository.entity.SysModuleRole;
import com.comtom.bc.server.repository.support.CustomRepository;

/**
 * 系统模块角色信息-数据访问层接口定义
 * 
 * @author zhucanhui
 *
 */
public interface ModuleRoleDAO extends CustomRepository<SysModuleRole, Long> {

	/**
	 * 根据角色Id获取角色权限列表
	 * 
	 * @param roleId
	 * @return List<SysModuleRole>
	 */
	public List<SysModuleRole> findByRoleId(String roleId);

	/**
	 * 根据角色Id获取角色权限列表
	 * 
	 * @param roleId
	 * @return List<SysModuleRole>
	 */
	@Query("select mr from SysModuleRole mr where roleId=:roleId order by moduleId")
	public List<SysModuleRole> findRoleId(@Param("roleId") String roleId);

}
