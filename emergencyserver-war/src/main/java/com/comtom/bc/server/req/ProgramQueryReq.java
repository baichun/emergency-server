package com.comtom.bc.server.req;

import java.util.Date;

/**
 * 创建节目请求参数对象
 *
 * @author zhucanhui
 *
 */
public class ProgramQueryReq extends BaseReq {

	/**
	 * 节目Id
	 */
	private Long programId;

	/**
	 * 节目类型（（1:应急 2:日常 3:演练））
	 */
	private String programType;

	/**
	 * 节目名称
	 */
	private String programName;

	/**
	 * 事件分类
	 */
	private String ebmEventType;

	/**
	 * 事件描述
	 */
	private String ebmEventDesc;

	/**
	 * 创建人员
	 */
	private String createUser;

	/**
	 * 开始日期
	 */
	private Date startDate;

	/**
	 * 结束日期
	 */
	private Date overDate;

	/**
	 * 节目状态
	 */
	private Integer state;

	/**
	 * 审批结果
	 */
	private Integer auditResult;

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
	 * @return the programType
	 */
	public String getProgramType() {
		return programType;
	}

	/**
	 * @param programType
	 *            the programType to set
	 */
	public void setProgramType(String programType) {
		this.programType = programType;
	}

	/**
	 * @return the programName
	 */
	public String getProgramName() {
		return programName;
	}

	/**
	 * @param programName
	 *            the programName to set
	 */
	public void setProgramName(String programName) {
		this.programName = programName;
	}

	/**
	 * @return the ebmEventType
	 */
	public String getEbmEventType() {
		return ebmEventType;
	}

	/**
	 * @param ebmEventType
	 *            the ebmEventType to set
	 */
	public void setEbmEventType(String ebmEventType) {
		this.ebmEventType = ebmEventType;
	}

	/**
	 * @return the createUser
	 */
	public String getCreateUser() {
		return createUser;
	}

	/**
	 * @param createUser
	 *            the createUser to set
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
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
	 * @return the state
	 */
	public Integer getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(Integer state) {
		this.state = state;
	}

	/**
	 * @return the ebmEventDesc
	 */
	public String getEbmEventDesc() {
		return ebmEventDesc;
	}

	/**
	 * @param ebmEventDesc
	 *            the ebmEventDesc to set
	 */
	public void setEbmEventDesc(String ebmEventDesc) {
		this.ebmEventDesc = ebmEventDesc;
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

}
