package com.comtom.bc.server.repository.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * <b>bc_ebm[bc_ebm]数据持久化对象</b>
 * 
 * @author zhucanhui
 * @date 2016-12-20 17:03:37
 */
@Entity
@Table(name = "bc_ebm")
public class Ebm {

	/**
	 * 广播消息Id
	 */
	@Id
	private String ebmId;

	/**
	 * 广播消息协议版本号
	 */
	private String ebmVersion = "1.0";

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
	 * 区域名称
	 */
	@Transient
	private String areaName;

	/**
	 * 参考业务节目Id
	 */
	private Integer programNum;

	/**
	 * 关联会话Id
	 */
	private Long flowId;

	/**
	 * 消息状态
	 */
	private Integer ebmState;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 调度方案Id
	 */
	private Integer schemeId;

	/**
	 *方案信息
	 */
	@Transient
	private SchemeInfo schemeInfo;

	/**
	 * 发送标志（1：接收 2：发送）
	 */
	private Integer sendFlag;

	/**
	 * 调度分发记录
	 */
	@Transient
	private List<EbmDispatch> dispatchList;
	
	/**
	 * 调度分发文件记录
	 */
	@Transient
	private List<FileInfo> fileInfo;

	/**
	 * 用于标记该条消息是否超过开始时间，并且在结束时间内；则需要马上播发；
	 */
	private Integer timeOut;

	/**
	 * 关联的信息接入信息;
	 */
	@Transient
	private InfoAccess infoAccess;

	public SchemeInfo getSchemeInfo() {
		return schemeInfo;
	}

	public void setSchemeInfo(SchemeInfo schemeInfo) {
		this.schemeInfo = schemeInfo;
	}

	/**
	 * 广播消息Id
	 * 
	 * @return ebmId
	 */
	public String getEbmId() {
		return ebmId;
	}

	/**
	 * 广播消息协议版本号
	 * 
	 * @return ebmVersion
	 */
	public String getEbmVersion() {
		return ebmVersion;
	}

	/**
	 * 关联广播消息Id
	 * 
	 * @return relatedEbmId
	 */
	public String getRelatedEbmId() {
		return relatedEbmId;
	}

	/**
	 * 关联广播信息Id
	 * 
	 * @return relatedEbIId
	 */
	public String getRelatedEbIId() {
		return relatedEbIId;
	}

	/**
	 * 消息类型
	 * 
	 * @return msgType
	 */
	public Integer getMsgType() {
		return msgType;
	}

	/**
	 * 发布机构名称
	 * 
	 * @return sendName
	 */
	public String getSendName() {
		return sendName;
	}

	/**
	 * 发布机构编码
	 * 
	 * @return senderCode
	 */
	public String getSenderCode() {
		return senderCode;
	}

	/**
	 * 发布时间
	 * 
	 * @return sendTime
	 */
	public Date getSendTime() {
		return sendTime;
	}

	/**
	 * 事件类型编码
	 * 
	 * @return eventType
	 */
	public String getEventType() {
		return eventType;
	}

	/**
	 * 事件级别
	 * 
	 * @return severity
	 */
	public Integer getSeverity() {
		return severity;
	}

	/**
	 * 播发开始时间
	 * 
	 * @return startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * 播发结束时间
	 * 
	 * @return endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * 语种代码
	 * 
	 * @return msgLanguageCode
	 */
	public String getMsgLanguageCode() {
		return msgLanguageCode;
	}

	/**
	 * 消息标题文本
	 * 
	 * @return msgTitle
	 */
	public String getMsgTitle() {
		return msgTitle;
	}

	/**
	 * 消息内容文本
	 * 
	 * @return msgDesc
	 */
	public String getMsgDesc() {
		return msgDesc;
	}

	/**
	 * 覆盖区域编码
	 * 
	 * @return areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * 参考业务节目Id
	 * 
	 * @return programNum
	 */
	public Integer getProgramNum() {
		return programNum;
	}

	/**
	 * 关联会话Id
	 * 
	 * @return flowId
	 */
	public Long getFlowId() {
		return flowId;
	}

	/**
	 * 消息状态
	 * 
	 * @return ebmState
	 */
	public Integer getEbmState() {
		return ebmState;
	}

	/**
	 * 广播消息Id
	 * 
	 * @param ebmId
	 */
	public void setEbmId(String ebmId) {
		this.ebmId = ebmId;
	}

	/**
	 * 广播消息协议版本号
	 * 
	 * @param ebmVersion
	 */
	public void setEbmVersion(String ebmVersion) {
		this.ebmVersion = ebmVersion;
	}

	/**
	 * 关联广播消息Id
	 * 
	 * @param relatedEbmId
	 */
	public void setRelatedEbmId(String relatedEbmId) {
		this.relatedEbmId = relatedEbmId;
	}

	/**
	 * 关联广播信息Id
	 * 
	 * @param relatedEbIId
	 */
	public void setRelatedEbIId(String relatedEbIId) {
		this.relatedEbIId = relatedEbIId;
	}

	/**
	 * 消息类型
	 * 
	 * @param msgType
	 */
	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	/**
	 * 发布机构名称
	 * 
	 * @param sendName
	 */
	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	/**
	 * 发布机构编码
	 * 
	 * @param senderCode
	 */
	public void setSenderCode(String senderCode) {
		this.senderCode = senderCode;
	}

	/**
	 * 发布时间
	 * 
	 * @param sendTime
	 */
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	/**
	 * 事件类型编码
	 * 
	 * @param eventType
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	/**
	 * 事件级别
	 * 
	 * @param severity
	 */
	public void setSeverity(Integer severity) {
		this.severity = severity;
	}

	/**
	 * 播发开始时间
	 * 
	 * @param startTime
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * 播发结束时间
	 * 
	 * @param endTime
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * 语种代码
	 * 
	 * @param msgLanguageCode
	 */
	public void setMsgLanguageCode(String msgLanguageCode) {
		this.msgLanguageCode = msgLanguageCode;
	}

	/**
	 * 消息标题文本
	 * 
	 * @param msgTitle
	 */
	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}

	/**
	 * 消息内容文本
	 * 
	 * @param msgDesc
	 */
	public void setMsgDesc(String msgDesc) {
		this.msgDesc = msgDesc;
	}

	/**
	 * 覆盖区域编码
	 * 
	 * @param areaCode
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * 参考业务节目Id
	 * 
	 * @param programNum
	 */
	public void setProgramNum(Integer programNum) {
		this.programNum = programNum;
	}

	/**
	 * 关联会话Id
	 * 
	 * @param flowId
	 */
	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}

	/**
	 * 消息状态
	 * 
	 * @param ebmState
	 */
	public void setEbmState(Integer ebmState) {
		this.ebmState = ebmState;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the schemeId
	 */
	public Integer getSchemeId() {
		return schemeId;
	}

	/**
	 * @param schemeId
	 *            the schemeId to set
	 */
	public void setSchemeId(Integer schemeId) {
		this.schemeId = schemeId;
	}

	/**
	 * @return the 发送标志（1：接收2：发送）
	 */
	public Integer getSendFlag() {
		return sendFlag;
	}

	/**
	 * @param 发送标志
	 *            （1：接收2：发送） the sendFlag to set
	 */
	public void setSendFlag(Integer sendFlag) {
		this.sendFlag = sendFlag;
	}

	/**
	 * @return the dispatchList
	 */
	public List<EbmDispatch> getDispatchList() {
		return dispatchList;
	}

	/**
	 * @param dispatchList
	 *            the dispatchList to set
	 */
	public void setDispatchList(List<EbmDispatch> dispatchList) {
		this.dispatchList = dispatchList;
	}

	/**
	 * @return the areaName
	 */
	public String getAreaName() {
		return areaName;
	}

	/**
	 * @param areaName
	 *            the areaName to set
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	/**
	 * @return the fileInfo
	 */
	public List<FileInfo> getFileInfo() {
		return fileInfo;
	}

	/**
	 * @param fileInfo the fileInfo to set
	 */
	public void setFileInfo(List<FileInfo> fileInfo) {
		this.fileInfo = fileInfo;
	}

	public Integer getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(Integer timeOut) {
		this.timeOut = timeOut;
	}

	public InfoAccess getInfoAccess() {
		return infoAccess;
	}

	public void setInfoAccess(InfoAccess infoAccess) {
		this.infoAccess = infoAccess;
	}
}