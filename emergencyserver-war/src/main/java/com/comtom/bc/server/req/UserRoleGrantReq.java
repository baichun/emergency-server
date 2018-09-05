package com.comtom.bc.server.req;

import java.util.List;

public class UserRoleGrantReq extends  BaseReq{

    private List<String> roleIds;

    private String userId;

    public List<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
