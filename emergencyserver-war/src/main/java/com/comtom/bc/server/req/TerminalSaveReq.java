package com.comtom.bc.server.req;

import java.util.Date;

public class TerminalSaveReq extends BaseReq {
	private String terminalEbrId;
	private String terminalEbrName;
	private String relatedPsEbrId;
	private String longitude;
	private String latitude;
	private String terminalType;
	private Integer terminalState;
	private Date createTime;
	private Date updateTime;
	private Integer syncFlag;
	private String param1;
	private String param2;
	
	public String getTerminalEbrId() {
		return terminalEbrId;
	}
	public void setTerminalEbrId(String terminalEbrId) {
		this.terminalEbrId = terminalEbrId;
	}
	public String getTerminalEbrName() {
		return terminalEbrName;
	}
	public void setTerminalEbrName(String terminalEbrName) {
		this.terminalEbrName = terminalEbrName;
	}
	public String getRelatedPsEbrId() {
		return relatedPsEbrId;
	}
	public void setRelatedPsEbrId(String relatedPsEbrId) {
		this.relatedPsEbrId = relatedPsEbrId;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getSyncFlag() {
		return syncFlag;
	}
	public void setSyncFlag(Integer syncFlag) {
		this.syncFlag = syncFlag;
	}
	public String getParam1() {
		return param1;
	}
	public void setParam1(String param1) {
		this.param1 = param1;
	}
	public String getParam2() {
		return param2;
	}
	public void setParam2(String param2) {
		this.param2 = param2;
	}
	public String getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}
	public Integer getTerminalState() {
		return terminalState;
	}
	public void setTerminalState(Integer terminalState) {
		this.terminalState = terminalState;
	}
}
