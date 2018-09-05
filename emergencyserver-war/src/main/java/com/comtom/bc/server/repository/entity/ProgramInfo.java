package com.comtom.bc.server.repository.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * <b>bc_program[bc_program]数据持久化对象</b>
 * 
 * @author zhucanhui
 * @date 2016-12-17 17:08:42
 */
@Entity
@Table(name = "bc_program")
public class ProgramInfo {

	/**
	 * 节目编号
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long programId;

	/**
	 * 节目类型（1:应急 2:日常 3:演练）
	 */
	private Integer programType;

	/**
	 * 节目名称
	 */
	private String programName;

	/**
	 * 节目级别（1,2,3,4,10）
	 */
	private Integer programLevel;

	/**
	 * 节目创建时间
	 */
	private Date createTime;

	/**
	 * 节目
	 */
	private String programContent;

	/**
	 * 节目更新时间
	 */
	private Date updateTime;

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
	 * 审核人
	 */
	private String auditUser;

	/**
	 * 节目来源（1:调控 2:制播）
	 */
	private Integer srcType;

	/**
	 * 语种代码
	 */
	private String languageCode;

	/**
	 * 创建人员
	 */
	private String createUser;

	/**
	 * 节目状态
	 */
	private Integer state;

	/**
	 * 事件分类
	 */
	private String ebmEventType;

	/**
	 * 事件描述
	 */
	private String ebmEventDesc;

	/**
	 * 发布机构名称
	 */
	private String releaseOrgName;

	/**
	 * 发布机构代码
	 */
	private String releaseOrgCode;

	/**
	 * 方案审核标识
	 */
	@Transient
	private Integer schemeAuditFlag;

	/**
	 * 区域列表
	 */
	@Transient
	private List<ProgramArea> areaList;

	/**
	 * 文件列表
	 */
	@Transient
	private List<ProgramFiles> filesList;

	/**
	 * 节目策略
	 */
	@Transient
	private ProgramStrategy programStrategy;

	/**
	 * 时间段列表
	 */
	@Transient
	private List<ProgramTime> timeList;

	/**
	 * 调度流程
	 */
	@Transient
	private DispatchFlow dispatchFlow;

	/**
	 * 贯通率
	 */
	@Transient
	private String cutThroughRate;

	/**
	 * 方案Id
	 */
	@Transient
	private Integer schemeId;

	/**
	 * 资源状态统计
	 */
	@Transient
	private Map<String, Integer> ebrStateMap;

	/**
	 * 关联信息Id
	 */
	@Transient
	private Long infoId;

	/**
	 * 节目编号
	 * 
	 * @return programId
	 */
	public Long getProgramId() {
		return programId;
	}

	/**
	 * 节目类型（1:应急 2:日常 3:演练）
	 * 
	 * @return programType
	 */
	public Integer getProgramType() {
		return programType;
	}

	/**
	 * 节目名称
	 * 
	 * @return programName
	 */
	public String getProgramName() {
		return programName;
	}

	/**
	 * 节目级别（1,2,3,4,10）
	 * 
	 * @return programLevel
	 */
	public Integer getProgramLevel() {
		return programLevel;
	}

	/**
	 * 节目创建时间
	 * 
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 节目
	 * 
	 * @return programContent
	 */
	public String getProgramContent() {
		return programContent;
	}

	/**
	 * 节目更新时间
	 * 
	 * @return updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
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
	 * 审核人
	 * 
	 * @return auditUser
	 */
	public String getAuditUser() {
		return auditUser;
	}

	/**
	 * 节目来源（1:调控 2:制播）
	 * 
	 * @return srcType
	 */
	public Integer getSrcType() {
		return srcType;
	}

	/**
	 * 节目编号
	 * 
	 * @param programId
	 */
	public void setProgramId(Long programId) {
		this.programId = programId;
	}

	/**
	 * 节目类型（1:应急 2:日常 3:演练）
	 * 
	 * @param programType
	 */
	public void setProgramType(Integer programType) {
		this.programType = programType;
	}

	/**
	 * 节目名称
	 * 
	 * @param programName
	 */
	public void setProgramName(String programName) {
		this.programName = programName;
	}

	/**
	 * 节目级别（1,2,3,4,10）
	 * 
	 * @param programLevel
	 */
	public void setProgramLevel(Integer programLevel) {
		this.programLevel = programLevel;
	}

	/**
	 * 节目创建时间
	 * 
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 节目
	 * 
	 * @param programContent
	 */
	public void setProgramContent(String programContent) {
		this.programContent = programContent;
	}

	/**
	 * 节目更新时间
	 * 
	 * @param updateTime
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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
	 * 审核人
	 * 
	 * @param auditUser
	 */
	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}

	/**
	 * 节目来源（1:调控 2:制播）
	 * 
	 * @param srcType
	 */
	public void setSrcType(Integer srcType) {
		this.srcType = srcType;
	}

	/**
	 * @return the languageCode
	 */
	public String getLanguageCode() {
		return languageCode;
	}

	/**
	 * @param languageCode
	 *            the languageCode to set
	 */
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
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
	 * @return the areaList
	 */
	public List<ProgramArea> getAreaList() {
		return areaList;
	}

	/**
	 * @param areaList
	 *            the areaList to set
	 */
	public void setAreaList(List<ProgramArea> areaList) {
		this.areaList = areaList;
	}

	/**
	 * @return the filesList
	 */
	public List<ProgramFiles> getFilesList() {
		return filesList;
	}

	/**
	 * @param filesList
	 *            the filesList to set
	 */
	public void setFilesList(List<ProgramFiles> filesList) {
		this.filesList = filesList;
	}

	/**
	 * @return the programStrategy
	 */
	public ProgramStrategy getProgramStrategy() {
		return programStrategy;
	}

	/**
	 * @param programStrategy
	 *            the programStrategy to set
	 */
	public void setProgramStrategy(ProgramStrategy programStrategy) {
		this.programStrategy = programStrategy;
	}

	/**
	 * @return the timeList
	 */
	public List<ProgramTime> getTimeList() {
		return timeList;
	}

	/**
	 * @param timeList
	 *            the timeList to set
	 */
	public void setTimeList(List<ProgramTime> timeList) {
		this.timeList = timeList;
	}

	/**
	 * @return the dispatchFlow
	 */
	public DispatchFlow getDispatchFlow() {
		return dispatchFlow;
	}

	/**
	 * @param dispatchFlow
	 *            the dispatchFlow to set
	 */
	public void setDispatchFlow(DispatchFlow dispatchFlow) {
		this.dispatchFlow = dispatchFlow;
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
	 * @return the releaseOrgCode
	 */
	public String getReleaseOrgCode() {
		return releaseOrgCode;
	}

	/**
	 * @param releaseOrgCode
	 *            the releaseOrgCode to set
	 */
	public void setReleaseOrgCode(String releaseOrgCode) {
		this.releaseOrgCode = releaseOrgCode;
	}

	/**
	 * @return the cutThroughRate
	 */
	public String getCutThroughRate() {
		return cutThroughRate;
	}

	/**
	 * @param cutThroughRate
	 *            the cutThroughRate to set
	 */
	public void setCutThroughRate(String cutThroughRate) {
		this.cutThroughRate = cutThroughRate;
	}

	/**
	 * @return the ebrStateMap
	 */
	public Map<String, Integer> getEbrStateMap() {
		return ebrStateMap;
	}

	/**
	 * @param ebrStateMap
	 *            the ebrStateMap to set
	 */
	public void setEbrStateMap(Map<String, Integer> ebrStateMap) {
		this.ebrStateMap = ebrStateMap;
	}

	/**
	 * @return the schemeAuditFlag
	 */
	public Integer getSchemeAuditFlag() {
		return schemeAuditFlag;
	}

	/**
	 * @param schemeAuditFlag
	 *            the schemeAuditFlag to set
	 */
	public void setSchemeAuditFlag(Integer schemeAuditFlag) {
		this.schemeAuditFlag = schemeAuditFlag;
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

	public Long getInfoId() {
		return infoId;
	}

	public void setInfoId(Long infoId) {
		this.infoId = infoId;
	}
}