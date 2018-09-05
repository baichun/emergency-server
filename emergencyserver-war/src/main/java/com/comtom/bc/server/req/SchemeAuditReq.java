package com.comtom.bc.server.req;

/**
 * 方案审核请求参数对象
 * 
 * @author zhucanhui
 *
 */
public class SchemeAuditReq extends BaseReq {

	/**
	 * 调度方案Id
	 */
	private Integer schemeId;

	/**
	 * 审批结果
	 */
	private Integer auditResult;

	/**
	 * 审批意见
	 */
	private String auditOpinion;

	/**
	 * 审核人员
	 */
	private String auditUser;

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
	 * @return the auditResult
	 */
	public Integer getAuditResult() {
		return auditResult;
	}

	/**
	 * @param auditResult
	 *            the auditResult to set
	 */
	public void setAuditResult(Integer auditResult) {
		this.auditResult = auditResult;
	}

	/**
	 * @return the auditOpinion
	 */
	public String getAuditOpinion() {
		return auditOpinion;
	}

	/**
	 * @param auditOpinion
	 *            the auditOpinion to set
	 */
	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}

	/**
	 * @return the auditUser
	 */
	public String getAuditUser() {
		return auditUser;
	}

	/**
	 * @param auditUser
	 *            the auditUser to set
	 */
	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}

}
