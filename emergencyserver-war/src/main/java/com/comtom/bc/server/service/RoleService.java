package com.comtom.bc.server.service;

import com.comtom.bc.server.repository.entity.SysModuleRole;
import com.comtom.bc.server.repository.entity.SysRole;
import com.comtom.bc.server.repository.entity.SysUserRole;
import com.comtom.bc.server.req.RoleQueryReq;
import com.comtom.bc.server.req.SysRoleGrantReq;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoleService {
    Page<SysRole> find(RoleQueryReq req);

    SysRole saveSysRole(SysRole sysRole);

    SysRole findById(String id);

    void delRoleInfo(SysRole sysRole);

    List<SysModuleRole> getResourceIdsByRoleId(SysModuleRole sysModuleRole);

    void grant(SysRoleGrantReq req,String account);

    List<SysUserRole> findListByUserId(String userId);
}
