package com.comtom.bc.server.req;

public class InfoAccessUpdateReq {

    /**
     * 信息id
     */
    private Long infoId;

    /**
     * 审核结果
     */
    private Integer auditResult;

    /**
     * 审核意见
     */
    private String auditOpinion;

    public Long getInfoId() {
        return infoId;
    }

    public void setInfoId(Long infoId) {
        this.infoId = infoId;
    }

    public Integer getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(Integer auditResult) {
        this.auditResult = auditResult;
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion;
    }
}
