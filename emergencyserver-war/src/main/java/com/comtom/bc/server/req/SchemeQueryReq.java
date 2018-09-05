package com.comtom.bc.server.req;

import java.util.Date;

/**
 * 方案查询请求参数对象
 * 
 * @author zhucanhui
 *
 */
public class SchemeQueryReq extends BaseReq {

	/**
	 * 调度方案Id
	 */
	private Integer schemeId;

	/**
	 * 方案标题
	 */
	private String schemeTitle;

	/**
	 * 方案状态
	 */
	private Integer state;

	/**
	 * 查询开始日期
	 */
	private Date startDate;

	/**
	 * 查询结束日期
	 */
	private Date overDate;

	/**
	 * 审批结果
	 */
	private Integer auditResult;

	/**
	 * @return the schemeId
	 */
	public Integer getSchemeId() {
		return schemeId;
	}

	/**
	 * @param schemeId the schemeId to set
	 */
	public void setSchemeId(Integer schemeId) {
		this.schemeId = schemeId;
	}

	/**
	 * @return the schemeTitle
	 */
	public String getSchemeTitle() {
		return schemeTitle;
	}

	/**
	 * @param schemeTitle the schemeTitle to set
	 */
	public void setSchemeTitle(String schemeTitle) {
		this.schemeTitle = schemeTitle;
	}

	/**
	 * @return the state
	 */
	public Integer getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(Integer state) {
		this.state = state;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the overDate
	 */
	public Date getOverDate() {
		return overDate;
	}

	/**
	 * @param overDate the overDate to set
	 */
	public void setOverDate(Date overDate) {
		this.overDate = overDate;
	}

	/**
	 * @return the auditResult
	 */
	public Integer getAuditResult() {
		return auditResult;
	}

	/**
	 * @param auditResult the auditResult to set
	 */
	public void setAuditResult(Integer auditResult) {
		this.auditResult = auditResult;
	}
}
