package com.comtom.bc.server.req;

public class EbmReq extends BaseReq {


    private String requestUrl;

    /**
     * 广播消息Id
     */
    private String ebmId;

    /**
     * 广播消息协议版本号
     */
    private String ebmVersion = "1";

    /**
     * 关联广播消息Id
     */
    private String relatedEbmId;

    /**
     * 关联广播信息Id
     */
    private String relatedEbIId;

    /**
     * 消息类型
     */
    private Integer msgType;

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
    private String sendTime;

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
    private String startTime;

    /**
     * 播发结束时间
     */
    private String endTime;

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
     * 区域名称
     */
    private String areaName;


    public String getEbmId() {
        return ebmId;
    }

    public void setEbmId(String ebmId) {
        this.ebmId = ebmId;
    }

    public String getEbmVersion() {
        return ebmVersion;
    }

    public void setEbmVersion(String ebmVersion) {
        this.ebmVersion = ebmVersion;
    }

    public String getRelatedEbmId() {
        return relatedEbmId;
    }

    public void setRelatedEbmId(String relatedEbmId) {
        this.relatedEbmId = relatedEbmId;
    }

    public String getRelatedEbIId() {
        return relatedEbIId;
    }

    public void setRelatedEbIId(String relatedEbIId) {
        this.relatedEbIId = relatedEbIId;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
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

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
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

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }
}
