package com.comtom.bc.server.req;

import java.util.Date;

/**
 * 终端统计请求参数对象
 * 
 * @author zhucanhui
 *
 */
public class TerminalStatReq extends BaseReq {

	/**
	 * 创建时间查找范围， 起始时间点
	 */
	private Date startTime;

	/**
	 * 创建时间查找范围，结束时间点
	 */
	private Date endTime;

	/**
	 * 资源状态
	 */
	private Integer terminalState;

	/**
	 * 资源类型
	 */
	private Integer terminalType;

	/**
	 * 所属应急广播平台
	 */
	private String relatedPsEbrId;

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the terminalState
	 */
	public Integer getTerminalState() {
		return terminalState;
	}

	/**
	 * @param terminalState
	 *            the terminalState to set
	 */
	public void setTerminalState(Integer terminalState) {
		this.terminalState = terminalState;
	}

	/**
	 * @return the terminalType
	 */
	public Integer getTerminalType() {
		return terminalType;
	}

	/**
	 * @param terminalType
	 *            the terminalType to set
	 */
	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}

	/**
	 * @return the relatedPsEbrId
	 */
	public String getRelatedPsEbrId() {
		return relatedPsEbrId;
	}

	/**
	 * @param relatedPsEbrId
	 *            the relatedPsEbrId to set
	 */
	public void setRelatedPsEbrId(String relatedPsEbrId) {
		this.relatedPsEbrId = relatedPsEbrId;
	}

}
