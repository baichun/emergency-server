package com.comtom.bc.server.repository.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * <b>v_bc_scheme_flow_info数据视图对象</b>
 * 
 * @author zhucanhui
 */
@Entity
@Table(name = "v_bc_scheme_flow_info")
@NamedQuery(name = "VSchemeFlow.findAll", query = "SELECT t FROM VSchemeFlow t")
public class VSchemeFlow {

	/**
	 * 平台ID
	 */
	@Id
	@Column(name = "schemeId")
	private Integer schemeId;

	/**
	 * 调度方案标题
	 */
	private String schemeTitle;

	/**
	 * 调度日期
	 */
	private Date createTime;

	/**
	 * 调度流程阶段
	 */
	private Integer flowStage;

	/**
	 * 调度流程状态
	 */
	private Integer flowState;

	/**
	 * 平台覆盖区域编码
	 */
	private String areaCode;

	/**
	 * 平台覆盖区域名称
	 */
	@Transient
	private String areaName;

	/**
	 * 节目级别
	 */
	private String programLevel;

	/**
	 * 事件描述
	 */
	private String ebmEventDesc;

	/**
	 * 事件级别
	 */
	private String severity;

	/**
	 * 发布机构名称
	 */
	private String sendName;

	/**
	 * 发布机构名称
	 */
	private String releaseOrgName;

	/**
	 * 节目Id
	 */
	private Long programId;

	/**
	 * 节目类型
	 */
	private Integer programType;

	/**
	 * 节目名称
	 */
	private String programName;

	/**
	 * 消息Id
	 */
	private String ebmId;

	/**
	 * 消息标题
	 */
	private String msgTitle;

	/**
	 * 广播时间
	 */
	@Transient
	private String startTime;

	@Transient
	private Long infoId;

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
	 * @return the areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * @param areaCode
	 *            the areaCode to set
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
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
	 * @return the programLevel
	 */
	public String getProgramLevel() {
		return programLevel;
	}

	/**
	 * @param programLevel
	 *            the programLevel to set
	 */
	public void setProgramLevel(String programLevel) {
		this.programLevel = programLevel;
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
	 * @return the severity
	 */
	public String getSeverity() {
		return severity;
	}

	/**
	 * @param severity
	 *            the severity to set
	 */
	public void setSeverity(String severity) {
		this.severity = severity;
	}

	/**
	 * @return the sendName
	 */
	public String getSendName() {
		return sendName;
	}

	/**
	 * @param sendName
	 *            the sendName to set
	 */
	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	/**
	 * @return the releaseOrgName
	 */
	public String getReleaseOrgName() {
		return releaseOrgName;
	}

	/**
	 * @param releaseOrgName
	 *            the releaseOrgName to set
	 */
	public void setReleaseOrgName(String releaseOrgName) {
		this.releaseOrgName = releaseOrgName;
	}

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
	public Integer getProgramType() {
		return programType;
	}

	/**
	 * @param programType
	 *            the programType to set
	 */
	public void setProgramType(Integer programType) {
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
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public Long getInfoId() {
		return infoId;
	}

	public void setInfoId(Long infoId) {
		this.infoId = infoId;
	}
}