package com.comtom.bc.server.req;

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
public class ProgramUpdateReq extends BaseReq {

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
	 * 是否为提交 true 提交 false 保存
	 */
	private boolean isSubmit;

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
	 * @return the isSubmit
	 */
	public boolean isSubmit() {
		return isSubmit;
	}

	/**
	 * @param isSubmit
	 *            the isSubmit to set
	 */
	public void setSubmit(boolean isSubmit) {
		this.isSubmit = isSubmit;
	}

}
