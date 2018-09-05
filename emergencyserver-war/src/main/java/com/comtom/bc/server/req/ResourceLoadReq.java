package com.comtom.bc.server.req;

import java.util.Date;
import java.util.List;

/**
 * 资源加载请求参数对象
 * 
 * @author kidsoul
 *
 */
public class ResourceLoadReq extends BaseReq {
	/**
	 * 创建时间查找范围， 起始时间点
	 */
	private Date creatTimeStart;

	/**
	 * 创建时间查找范围，结束时间点
	 */
	private Date createTimeEnd;

	/**
	 * 资源ID
	 */
	private String resourceId;

	/**
	 * 资源名称
	 */
	private String resourceName;

	/**
	 * 覆盖区域
	 */
	private String areaCode;

	/**
	 * 资源状态
	 */
	private Integer state;

	/**
	 * 开关机时间
	 */
	private List<WorkTime> workTimeSwitch;

	/**
	 * 开关时间完全匹配
	 */
	private boolean workTimeFullyMatch;

	public Date getCreatTimeStart() {
		return creatTimeStart;
	}

	public void setCreatTimeStart(Date creatTimeStart) {
		this.creatTimeStart = creatTimeStart;
	}

	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public List<WorkTime> getWorkTimeSwitch() {
		return workTimeSwitch;
	}

	public void setWorkTimeSwitch(List<WorkTime> workTimeSwitch) {
		this.workTimeSwitch = workTimeSwitch;
	}

	public boolean isWorkTimeFullyMatch() {
		return workTimeFullyMatch;
	}

	public void setWorkTimeFullyMatch(boolean workTimeFullyMatch) {
		this.workTimeFullyMatch = workTimeFullyMatch;
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

}
