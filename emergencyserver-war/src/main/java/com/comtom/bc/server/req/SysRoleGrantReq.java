package com.comtom.bc.server.req;

import java.util.List;

public class SysRoleGrantReq extends  BaseReq{

    private List<String> menuIdList;

    private String roleId;

    public List<String> getMenuIdList() {
        return menuIdList;
    }

    public void setMenuIdList(List<String> menuIdList) {
        this.menuIdList = menuIdList;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
