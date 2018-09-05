package com.comtom.bc.server.req;

import java.util.Date;

/**
 * 查询预警消息或广播指令请求参数对象
 * 
 * @author zhucanhui
 *
 */
public class EbmQueryReq extends BaseReq {
	
	/**
	 * 调度分发Id
	 */
	private Integer dispatchId;

	/**
	 * 消息Id
	 */
	private String ebmId;

	/**
	 * 消息标题/名称
	 */
	private String msgTitle;

	/**
	 * 消息内容
	 */
	private String msgDesc;

	/**
	 * 事件级别
	 */
	private Integer severity;

	/**
	 * 发布开始日期
	 */
	private Date startDate;

	/**
	 * 发布结束日期
	 */
	private Date endDate;

	/**
	 * @return the ebmId
	 */
	public String getEbmId() {
		return ebmId;
	}

	/**
	 * @param ebmId
	 *            the ebmId to set
	 */
	public void setEbmId(String ebmId) {
		this.ebmId = ebmId;
	}

	/**
	 * @return the msgTitle
	 */
	public String getMsgTitle() {
		return msgTitle;
	}

	/**
	 * @param msgTitle
	 *            the msgTitle to set
	 */
	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}

	/**
	 * @return the msgDesc
	 */
	public String getMsgDesc() {
		return msgDesc;
	}

	/**
	 * @param msgDesc
	 *            the msgDesc to set
	 */
	public void setMsgDesc(String msgDesc) {
		this.msgDesc = msgDesc;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the severity
	 */
	public Integer getSeverity() {
		return severity;
	}

	/**
	 * @param severity
	 *            the severity to set
	 */
	public void setSeverity(Integer severity) {
		this.severity = severity;
	}

	/**
	 * @return the dispatchId
	 */
	public Integer getDispatchId() {
		return dispatchId;
	}

	/**
	 * @param dispatchId the dispatchId to set
	 */
	public void setDispatchId(Integer dispatchId) {
		this.dispatchId = dispatchId;
	}
	
}
