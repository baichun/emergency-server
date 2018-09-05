package com.comtom.bc.server.req;

/**
 * 查询参数列表请求参数对象
 * 
 * @author zhucanhui
 *
 */
public class RoleQueryReq extends BaseReq {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
