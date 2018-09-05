package com.comtom.bc.server.repository.dao;

import com.comtom.bc.server.repository.entity.SysModuleRole;
import com.comtom.bc.server.repository.entity.SysRole;
import com.comtom.bc.server.repository.support.CustomRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 系统角色信息-数据访问层接口定义
 * 
 * @author zhucanhui
 *
 */
public interface SysModuleRoleDAO extends CustomRepository<SysModuleRole, String> {

    @Modifying
    @Query("delete from SysModuleRole where roleId=:roleId")
    void deleteByRoleId(@Param(value = "roleId") String roleId);
}
