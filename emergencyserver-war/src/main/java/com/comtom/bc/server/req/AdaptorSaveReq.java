package com.comtom.bc.server.req;

import java.util.Date;

public class AdaptorSaveReq extends BaseReq {
	private String asEbrId;
	private String asEbrName;
	private String asUrl;
	private String asType;
	private String relatedRsEbrId;
	private String relatedPsEbrId;
	private String areaCode;
	private String longitude;
	private String latitude;
	private Integer asState;
	private Date createTime;
	private Date updateTime;
	private Integer syncFlag;
	private String param1;
	private String param2;

	public String getAsEbrId() {
		return asEbrId;
	}

	public void setAsEbrId(String asEbrId) {
		this.asEbrId = asEbrId;
	}

	public String getAsEbrName() {
		return asEbrName;
	}

	public void setAsEbrName(String asEbrName) {
		this.asEbrName = asEbrName;
	}

	public String getAsUrl() {
		return asUrl;
	}

	public void setAsUrl(String asUrl) {
		this.asUrl = asUrl;
	}

	public String getAsType() {
		return asType;
	}

	public void setAsType(String asType) {
		this.asType = asType;
	}

	public String getRelatedRsEbrId() {
		return relatedRsEbrId;
	}

	public void setRelatedRsEbrId(String relatedRsEbrId) {
		this.relatedRsEbrId = relatedRsEbrId;
	}

	public String getRelatedPsEbrId() {
		return relatedPsEbrId;
	}

	public void setRelatedPsEbrId(String relatedPsEbrId) {
		this.relatedPsEbrId = relatedPsEbrId;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
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

	public Integer getAsState() {
		return asState;
	}

	public void setAsState(Integer asState) {
		this.asState = asState;
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
}
