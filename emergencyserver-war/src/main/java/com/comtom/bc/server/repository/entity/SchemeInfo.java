package com.comtom.bc.server.repository.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * <b>bc_scheme[bc_scheme]数据持久化对象</b>
 * 
 * @author zhucanhui
 * @date 2016-12-20 16:04:22
 */
@Entity
@Table(name = "bc_scheme")
public class SchemeInfo {

	/**
	 * 调度方案编号
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer schemeId;

	/**
	 * 方案标题
	 */
	private String schemeTitle;

	/**
	 * 方案类型
	 */
	private Integer schemeType;

	/**
	 * 关联消息编号
	 */
	private String ebmId;

	/**
	 * 覆盖区域百分比
	 */
	private BigDecimal areaPercent;

	/**
	 * 覆盖人口百分比
	 */
	private BigDecimal popuPercent;

	/**
	 * 生成时间
	 */
	private Date createTime;

	/**
	 * 区域覆盖面积
	 */
	private BigDecimal totalArea;

	/**
	 * 区域覆盖人口
	 */
	private BigDecimal totalPopu;

	/**
	 * 资源覆盖面积
	 */
	private BigDecimal ebrArea;

	/**
	 * 资源覆盖人口
	 */
	private BigDecimal ebrPopu;

	/**
	 * 关联会话编号
	 */
	private Long flowId;

	/**
	 * 审核结果
	 */
	private Integer auditResult;

	/**
	 * 审核意见
	 */
	private String auditOpinion;

	/**
	 * 审核时间
	 */
	private Date auditTime;

	/**
	 * 审核人员
	 */
	private String auditUser;

	/**
	 * 方案状态(1:正常 2:取消)
	 */
	private Integer state;

	/**
	 * 关联节目Id
	 */
	private Long programId;

	/**
	 * 关联数据包Id
	 */
	private String ebdId;

	/**
	 * 关联的消息Id
	 */
	private Long infoId;


	/**
	 * 调度方案资源列表
	 */
	@Transient
	private List<SchemeEbr> ebrList;

	/**
	 * 方案流程详情
	 */
	@Transient
	private VSchemeFlow vSchemeFlow;
	
	/**
	 * 调度方案处理结果码
	 */
	@Transient
	private String resultCode;

	/**
	 * 调度方案编号
	 * 
	 * @return schemeId
	 */
	public Integer getSchemeId() {
		return schemeId;
	}

	/**
	 * 方案标题
	 * 
	 * @return schemeTitle
	 */
	public String getSchemeTitle() {
		return schemeTitle;
	}

	/**
	 * 方案类型
	 * 
	 * @return schemeType
	 */
	public Integer getSchemeType() {
		return schemeType;
	}

	/**
	 * 关联消息编号
	 * 
	 * @return ebmId
	 */
	public String getEbmId() {
		return ebmId;
	}

	/**
	 * 覆盖区域百分比
	 * 
	 * @return areaPercent
	 */
	public BigDecimal getAreaPercent() {
		return areaPercent;
	}

	/**
	 * 覆盖人口百分比
	 * 
	 * @return popuPercent
	 */
	public BigDecimal getPopuPercent() {
		return popuPercent;
	}

	/**
	 * 生成时间
	 * 
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 覆盖面积
	 * 
	 * @return totalArea
	 */
	public BigDecimal getTotalArea() {
		return totalArea;
	}

	/**
	 * 覆盖人口
	 * 
	 * @return totalPopu
	 */
	public BigDecimal getTotalPopu() {
		return totalPopu;
	}

	/**
	 * 关联会话编号
	 * 
	 * @return flowId
	 */
	public Long getFlowId() {
		return flowId;
	}

	/**
	 * 审核结果
	 * 
	 * @return auditResult
	 */
	public Integer getAuditResult() {
		return auditResult;
	}

	/**
	 * 审核意见
	 * 
	 * @return auditOpinion
	 */
	public String getAuditOpinion() {
		return auditOpinion;
	}

	/**
	 * 审核时间
	 * 
	 * @return auditTime
	 */
	public Date getAuditTime() {
		return auditTime;
	}

	/**
	 * 方案状态(1:正常 2:取消)
	 * 
	 * @return state
	 */
	public Integer getState() {
		return state;
	}

	/**
	 * 调度方案编号
	 * 
	 * @param schemeId
	 */
	public void setSchemeId(Integer schemeId) {
		this.schemeId = schemeId;
	}

	/**
	 * 方案标题
	 * 
	 * @param schemeTitle
	 */
	public void setSchemeTitle(String schemeTitle) {
		this.schemeTitle = schemeTitle;
	}

	/**
	 * 方案类型
	 * 
	 * @param schemeType
	 */
	public void setSchemeType(Integer schemeType) {
		this.schemeType = schemeType;
	}

	/**
	 * 关联消息编号
	 * 
	 * @param ebmId
	 */
	public void setEbmId(String ebmId) {
		this.ebmId = ebmId;
	}

	/**
	 * 覆盖区域百分比
	 * 
	 * @param areaPercent
	 */
	public void setAreaPercent(BigDecimal areaPercent) {
		this.areaPercent = areaPercent;
	}

	/**
	 * 覆盖人口百分比
	 * 
	 * @param popuPercent
	 */
	public void setPopuPercent(BigDecimal popuPercent) {
		this.popuPercent = popuPercent;
	}

	/**
	 * 生成时间
	 * 
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 覆盖面积
	 * 
	 * @param totalArea
	 */
	public void setTotalArea(BigDecimal totalArea) {
		this.totalArea = totalArea;
	}

	/**
	 * 覆盖人口
	 * 
	 * @param totalPopu
	 */
	public void setTotalPopu(BigDecimal totalPopu) {
		this.totalPopu = totalPopu;
	}

	/**
	 * 关联会话编号
	 * 
	 * @param flowId
	 */
	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}

	/**
	 * 审核结果
	 * 
	 * @param auditResult
	 */
	public void setAuditResult(Integer auditResult) {
		this.auditResult = auditResult;
	}

	/**
	 * 审核意见
	 * 
	 * @param auditOpinion
	 */
	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}

	/**
	 * 审核时间
	 * 
	 * @param auditTime
	 */
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	/**
	 * 方案状态(1:正常 2:取消)
	 * 
	 * @param state
	 */
	public void setState(Integer state) {
		this.state = state;
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
	 * @return the ebdId
	 */
	public String getEbdId() {
		return ebdId;
	}

	/**
	 * @param ebdId
	 *            the ebdId to set
	 */
	public void setEbdId(String ebdId) {
		this.ebdId = ebdId;
	}

	/**
	 * @return the ebrList
	 */
	public List<SchemeEbr> getEbrList() {
		return ebrList;
	}

	/**
	 * @param ebrList
	 *            the ebrList to set
	 */
	public void setEbrList(List<SchemeEbr> ebrList) {
		this.ebrList = ebrList;
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

	/**
	 * @return the vSchemeFlow
	 */
	public VSchemeFlow getvSchemeFlow() {
		return vSchemeFlow;
	}

	/**
	 * @param vSchemeFlow
	 *            the vSchemeFlow to set
	 */
	public void setvSchemeFlow(VSchemeFlow vSchemeFlow) {
		this.vSchemeFlow = vSchemeFlow;
	}

	/**
	 * @return the ebrArea
	 */
	public BigDecimal getEbrArea() {
		return ebrArea;
	}

	/**
	 * @param ebrArea
	 *            the ebrArea to set
	 */
	public void setEbrArea(BigDecimal ebrArea) {
		this.ebrArea = ebrArea;
	}

	/**
	 * @return the ebrPopu
	 */
	public BigDecimal getEbrPopu() {
		return ebrPopu;
	}

	/**
	 * @param ebrPopu
	 *            the ebrPopu to set
	 */
	public void setEbrPopu(BigDecimal ebrPopu) {
		this.ebrPopu = ebrPopu;
	}

	/**
	 * @return the resultCode
	 */
	public String getResultCode() {
		return resultCode;
	}

	/**
	 * @param resultCode the resultCode to set
	 */
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public Long getInfoId() {
		return infoId;
	}

	public void setInfoId(Long infoId) {
		this.infoId = infoId;
	}
}