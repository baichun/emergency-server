package com.comtom.bc.server.service;

import com.comtom.bc.server.repository.entity.UserMenuView;
import com.comtom.bc.server.repository.entity.SysModule;
import com.comtom.bc.server.req.MenuReq;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 菜单管理
 */
public interface MenuService {

    public Page<SysModule> page(MenuReq req);

    public SysModule save(SysModule menu);

    public SysModule findById(String id);

    public List<SysModule> list(MenuReq req);

    public List<SysModule> treeList(MenuReq req);

    public List<UserMenuView> grantedList(MenuReq req);
}
