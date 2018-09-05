package com.comtom.bc.server.repository.dao;

import java.util.List;

import com.comtom.bc.server.repository.entity.SysUserRole;
import com.comtom.bc.server.repository.support.CustomRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 用户角色信息-数据访问层接口定义
 * 
 * @author zhucanhui
 *
 */
public interface UserRoleDAO extends CustomRepository<SysUserRole, Long> {

	/**
	 * 根据用户编号查询角色编号
	 * 
	 * @param userId
	 *            角色编号
	 * @return SysUserRole
	 */
	public List<SysUserRole> findByUserId(String userId);

	@Modifying
	@Query("delete from SysUserRole where userId=:userId")
    void deleteByUserId(@Param(value = "userId") String userId);
}
