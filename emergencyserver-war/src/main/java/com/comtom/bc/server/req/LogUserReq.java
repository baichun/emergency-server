package com.comtom.bc.server.req;

import java.util.Date;

/**
 * 用户操作日志信息参数对象
 * 
 * @author zhucanhui
 *
 */
public class LogUserReq extends BaseReq {

	/**
	 * 用户
	 */
	private String account;

	/**
	 * 日志内容
	 */
	private String logContent;

	/**
	 * 记录开始日期
	 */
	private Date startDate;

	/**
	 * 记录结束日期
	 */
	private Date endDate;

	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * @return the logContent
	 */
	public String getLogContent() {
		return logContent;
	}

	/**
	 * @param logContent
	 *            the logContent to set
	 */
	public void setLogContent(String logContent) {
		this.logContent = logContent;
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

}
