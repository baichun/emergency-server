package com.comtom.bc.server.req;

public class MenuReq extends BaseReq{

    private String id;

    private String name;

    private String url;

    private String parentId;

    private String status;

    private String parentName;

    private Integer portalType;

    private String userId;

    private String roleId;

    /**
     * 是否递归查询出子菜单
     */
    private boolean needChild;

    public boolean isNeedChild() {
        return needChild;
    }

    public void setNeedChild(boolean needChild) {
        this.needChild = needChild;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Integer getPortalType() {
        return portalType;
    }

    public void setPortalType(Integer portalType) {
        this.portalType = portalType;
    }
}
