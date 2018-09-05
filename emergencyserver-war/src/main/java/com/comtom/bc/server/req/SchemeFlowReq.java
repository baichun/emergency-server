package com.comtom.bc.server.req;

import java.util.Date;

/**
 * 调度方案流程请求参数对象
 * 
 * @author zhucanhui
 *
 */
public class SchemeFlowReq extends BaseReq {

	/**
	 * 调度方案Id
	 */
	private Integer schemeId;

	/**
	 * 方案标题
	 */
	private String schemeTitle;

	/**
	 * 发布开始日期
	 */
	private Date startDate;

	/**
	 * 发布结束日期
	 */
	private Date overDate;

	/**
	 * 流程阶段
	 */
	private Integer flowStage;

	/**
	 * 流程状态
	 */
	private Integer flowState;

	/**
	 * 来源单位
	 */
	private String sourceOrg;

	/**
	 * @return the schemeTitle
	 */
	public String getSchemeTitle() {
		return schemeTitle;
	}

	/**
	 * @param schemeTitle
	 *            the schemeTitle to set
	 */
	public void setSchemeTitle(String schemeTitle) {
		this.schemeTitle = schemeTitle;
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
	 * @return the overDate
	 */
	public Date getOverDate() {
		return overDate;
	}

	/**
	 * @param overDate
	 *            the overDate to set
	 */
	public void setOverDate(Date overDate) {
		this.overDate = overDate;
	}

	/**
	 * @return the flowStage
	 */
	public Integer getFlowStage() {
		return flowStage;
	}

	/**
	 * @param flowStage
	 *            the flowStage to set
	 */
	public void setFlowStage(Integer flowStage) {
		this.flowStage = flowStage;
	}

	/**
	 * @return the flowState
	 */
	public Integer getFlowState() {
		return flowState;
	}

	/**
	 * @param flowState
	 *            the flowState to set
	 */
	public void setFlowState(Integer flowState) {
		this.flowState = flowState;
	}

	/**
	 * @return the sourceOrg
	 */
	public String getSourceOrg() {
		return sourceOrg;
	}

	/**
	 * @param sourceOrg
	 *            the sourceOrg to set
	 */
	public void setSourceOrg(String sourceOrg) {
		this.sourceOrg = sourceOrg;
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

}
