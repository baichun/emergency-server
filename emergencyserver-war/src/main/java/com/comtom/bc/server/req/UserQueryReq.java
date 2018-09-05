package com.comtom.bc.server.req;

/**
 * 查询参数列表请求参数对象
 * 
 * @author zhucanhui
 *
 */
public class UserQueryReq extends BaseReq {
    
    private String account;

    private String name;

    private String deleteFlag;

    @Override
    public String getAccount() {
        return account;
    }

    @Override
    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
