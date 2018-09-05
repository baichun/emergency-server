package com.comtom.bc.server.req;

import java.util.Date;
import java.util.List;

import com.comtom.bc.server.repository.entity.ProgramArea;
import com.comtom.bc.server.repository.entity.ProgramFiles;
import com.comtom.bc.server.repository.entity.ProgramStrategy;

/**
 * 创建节目请求参数对象
 * 
 * @author zhucanhui
 *
 */
public class ProgramSaveReq extends BaseReq {

	/**
	 * 节目Id
	 */
	private Long programId;

	/**
	 * 节目类型（（1:应急 2:日常 3:演练））
	 */
	private Integer programType;

	/**
	 * 节目名称
	 */
	private String programName;

	/**
	 * 节目级别
	 */
	private Integer programLevel;

	/**
	 * 语种代码
	 */
	private String languageCode;

	/**
	 * 节目内容（文本）
	 */
	private String programContent;

	/**
	 * 事件分类
	 */
	private String ebmEventType;

	/**
	 * 创建人员
	 */
	private String createUser;

	/**
	 * 节目文件
	 */
	private List<ProgramFiles> filesList;

	/**
	 * 节目区域
	 */
	private List<ProgramArea> areaList;

	/**
	 * 节目策略信息
	 */
	private ProgramStrategy programStrategy;

	/**
	 * 开始日期
	 */
	private Date startDate;

	/**
	 * 结束日期
	 */
	private Date overDate;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 节目来源
	 */
	private Integer srcType;

	/**
	 * 提交标识
	 */
	private Integer submitFlag;

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
	 * @return the programLevel
	 */
	public Integer getProgramLevel() {
		return programLevel;
	}

	/**
	 * @param programLevel
	 *            the programLevel to set
	 */
	public void setProgramLevel(Integer programLevel) {
		this.programLevel = programLevel;
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
	 * @return the programContent
	 */
	public String getProgramContent() {
		return programContent;
	}

	/**
	 * @param programContent
	 *            the programContent to set
	 */
	public void setProgramContent(String programContent) {
		this.programContent = programContent;
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
	 * @return the srcType
	 */
	public Integer getSrcType() {
		return srcType;
	}

	/**
	 * @param srcType
	 *            the srcType to set
	 */
	public void setSrcType(Integer srcType) {
		this.srcType = srcType;
	}

	/**
	 * @return the submitFlag
	 */
	public Integer getSubmitFlag() {
		return submitFlag;
	}

	/**
	 * @param submitFlag
	 *            the submitFlag to set
	 */
	public void setSubmitFlag(Integer submitFlag) {
		this.submitFlag = submitFlag;
	}
}
