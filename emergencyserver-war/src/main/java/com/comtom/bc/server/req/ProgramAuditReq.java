package com.comtom.bc.server.req;

/**
 * 审核节目请求参数对象
 * 
 * @author zhucanhui
 *
 */
public class ProgramAuditReq extends BaseReq {

	/**
	 * 节目Id
	 */
	private Long programId;

	/**
	 * 审批结果
	 */
	private Integer auditResult;

	/**
	 * 审批意见
	 */
	private String auditOpinion;

	/**
	 * 审批用户
	 */
	private String auditUser;

	/**
	 * @return the programId
	 */
	public Long getProgramId() {
		return programId;
	}

	/**
	 * @param programId
	 *            the programId to set
	 */
	public void setProgramId(Long programId) {
		this.programId = programId;
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
