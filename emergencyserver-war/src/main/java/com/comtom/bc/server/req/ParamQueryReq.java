package com.comtom.bc.server.req;

import java.util.Date;

/**
 * 查询参数列表请求参数对象
 * 
 * @author zhucanhui
 *
 */
public class ParamQueryReq extends BaseReq {
    
    private String key;

    private String catalogId;

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
