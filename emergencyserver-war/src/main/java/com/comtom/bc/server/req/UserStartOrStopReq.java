package com.comtom.bc.server.req;

/**
 * 查询参数列表请求参数对象
 * 
 * @author zhucanhui
 *
 */
public class UserStartOrStopReq extends BaseReq {
    
    private String account;

    private String status;

    @Override
    public String getAccount() {
        return account;
    }

    @Override
    public void setAccount(String account) {
        this.account = account;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
