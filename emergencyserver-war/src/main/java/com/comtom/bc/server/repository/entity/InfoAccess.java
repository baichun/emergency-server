package com.comtom.bc.server.repository.entity;


import javax.persistence.*;
import java.util.Date;

@Entity

@Table(name = "bc_info_access")
public class InfoAccess {

    /**
     * ID
     */
    @Id
    @Column(name = "infoId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long infoId;

    /**
     * 关联的消息id or 节目id
     */
    private String relatedEbmId;

    /**
     * 消息类型
     */
    private Integer msgType;

    /**
     * 节目类型（1:应急 2:日常 3:演练）
     */
    private Integer infoType;

    /**
     * 消息来源(1:上级平台 2:自制作应急信息 3:其他第三方预警部门)
     */
    private Integer msgSource;

    /**
     * 发布机构名称
     */
    private String sendName;

    /**
     * 发布机构编码
     */
    private String senderCode;

    /**
     * 发布时间
     */
    private Date sendTime;

    /**
     * 事件类型编码
     */
    private String eventType;

    /**
     * 事件级别
     */
    private Integer severity;

    /**
     * 播发开始时间
     */
    private Date startTime;

    /**
     * 播发结束时间
     */
    private Date endTime;

    /**
     * 语种代码
     */
    private String msgLanguageCode;

    /**
     * 消息标题文本
     */
    private String msgTitle;

    /**
     * 消息内容文本
     */
    private String msgDesc;

    /**
     * 覆盖区域编码
     */
    private String areaCode;

    /**
     * 平台覆盖区域名称
     */
    @Transient
    private String areaName;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 审核结果(0:未审核 1:审核通过 2:审核不通过)
     */
    private Integer auditResult;

    /**
     * 审核意见
     */
    private String auditOpinion;

    /**
     * 审核类型(1:人工审核、2:自动审核)
     */
    private Integer auditType;

    /**
     * 审核时间
     */
    private Date auditTime;

    /**
     * 审核人
     */
    private String auditor;

    public Long getInfoId() {
        return infoId;
    }

    public void setInfoId(Long infoId) {
        this.infoId = infoId;
    }

    public String getRelatedEbmId() {
        return relatedEbmId;
    }

    public void setRelatedEbmId(String relatedEbmId) {
        this.relatedEbmId = relatedEbmId;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public Integer getMsgSource() {
        return msgSource;
    }

    public void setMsgSource(Integer msgSource) {
        this.msgSource = msgSource;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getSenderCode() {
        return senderCode;
    }

    public void setSenderCode(String senderCode) {
        this.senderCode = senderCode;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Integer getSeverity() {
        return severity;
    }

    public void setSeverity(Integer severity) {
        this.severity = severity;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getMsgLanguageCode() {
        return msgLanguageCode;
    }

    public void setMsgLanguageCode(String msgLanguageCode) {
        this.msgLanguageCode = msgLanguageCode;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgDesc() {
        return msgDesc;
    }

    public void setMsgDesc(String msgDesc) {
        this.msgDesc = msgDesc;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Integer getAuditType() {
        return auditType;
    }

    public void setAuditType(Integer auditType) {
        this.auditType = auditType;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public Integer getInfoType() {
        return infoType;
    }

    public void setInfoType(Integer infoType) {
        this.infoType = infoType;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}