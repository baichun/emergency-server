package com.comtom.bc.server.repository.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <b>bc_ebm_brd_record[bc_ebm_brd_record]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author Kidsoul Qu
 * @date 2016-12-26 21:11:22
 */
@Entity
@Table(name = "bc_ebm_brd_record")
public class EbmBrdRecord  {

	/**
	 * 播发记录条目ID
	 */
	@Id
	private String brdItemId;
	
	/**
	 * 信息上报资源的ID
	 */
	private String resourceId;
	
	/**
	 * 消息ID
	 */
	private String ebmId;
	
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
	private String languageCode;
	
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
	 * 参考业务节目号
	 */
	private Integer programNum;
	
	/**
	 * 播发状态码
	 */
	private Integer brdStateCode;
	
	/**
	 * 播发状态描述
	 */
	private String brdStateDesc;
	
	/**
	 * 覆盖率
	 */
	private Double coveragePercent;
	
	/**
	 * coverageAreaCode
	 */
	private String coverageAreaCode;

	private Date updateTime;

	/**
	 *记录是否已同步标识（1：未同步 2：已同步）
	 */
	private Integer syncFlag;


	/**
	 * 播发记录条目ID
	 * 
	 * @return brdItemId
	 */
	public String getBrdItemId() {
		return brdItemId;
	}
	
	/**
	 * 信息上报资源的ID
	 * 
	 * @return resourceId
	 */
	public String getResourceId() {
		return resourceId;
	}
	
	/**
	 * 消息ID
	 * 
	 * @return ebmId
	 */
	public String getEbmId() {
		return ebmId;
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
	 * @return languageCode
	 */
	public String getLanguageCode() {
		return languageCode;
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
	 * 参考业务节目号
	 * 
	 * @return programNum
	 */
	public Integer getProgramNum() {
		return programNum;
	}
	
	/**
	 * 播发状态码
	 * 
	 * @return brdStateCode
	 */
	public Integer getBrdStateCode() {
		return brdStateCode;
	}
	
	/**
	 * 播发状态描述
	 * 
	 * @return brdStateDesc
	 */
	public String getBrdStateDesc() {
		return brdStateDesc;
	}
	
	/**
	 * 覆盖率
	 * 
	 * @return coveragePercent
	 */
	public Double getCoveragePercent() {
		return coveragePercent;
	}
	
	/**
	 * coverageAreaCode
	 * 
	 * @return coverageAreaCode
	 */
	public String getCoverageAreaCode() {
		return coverageAreaCode;
	}
	

	/**
	 * 播发记录条目ID
	 * 
	 * @param brdItemId
	 */
	public void setBrdItemId(String brdItemId) {
		this.brdItemId = brdItemId;
	}
	
	/**
	 * 信息上报资源的ID
	 * 
	 * @param resourceId
	 */
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
	/**
	 * 消息ID
	 * 
	 * @param ebmId
	 */
	public void setEbmId(String ebmId) {
		this.ebmId = ebmId;
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
	 * @param languageCode
	 */
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
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
	 * 参考业务节目号
	 * 
	 * @param programNum
	 */
	public void setProgramNum(Integer programNum) {
		this.programNum = programNum;
	}
	
	/**
	 * 播发状态码
	 * 
	 * @param brdStateCode
	 */
	public void setBrdStateCode(Integer brdStateCode) {
		this.brdStateCode = brdStateCode;
	}
	
	/**
	 * 播发状态描述
	 * 
	 * @param brdStateDesc
	 */
	public void setBrdStateDesc(String brdStateDesc) {
		this.brdStateDesc = brdStateDesc;
	}
	
	/**
	 * 覆盖率
	 * 
	 * @param coveragePercent
	 */
	public void setCoveragePercent(Double coveragePercent) {
		this.coveragePercent = coveragePercent;
	}
	
	/**
	 * coverageAreaCode
	 * 
	 * @param coverageAreaCode
	 */
	public void setCoverageAreaCode(String coverageAreaCode) {
		this.coverageAreaCode = coverageAreaCode;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getSyncFlag() {
		return syncFlag;
	}

	public void setSyncFlag(Integer syncFlag) {
		this.syncFlag = syncFlag;
	}
}