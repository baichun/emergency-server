package com.comtom.bc.server.req;

import java.util.Date;

public class PlatformSaveReq extends BaseReq {
	private String psEbrId;
	private String psUrl;
	private String psEbrName;
	private Integer psState;
	private String areaCode;
	private String psType;
	private String parentPsEbrId;
	private String psAddress;
	private String contact;
	private String phoneNumber;
	private String longitude;
	private String latitude;
	private Integer platLevel;
	private Date createTime;
	private Date updateTime;
	private Integer syncFlag;
	
	public String getPsEbrId() {
		return psEbrId;
	}
	public void setPsEbrId(String psEbrId) {
		this.psEbrId = psEbrId;
	}
	public String getPsUrl() {
		return psUrl;
	}
	public void setPsUrl(String psUrl) {
		this.psUrl = psUrl;
	}
	public String getPsEbrName() {
		return psEbrName;
	}
	public void setPsEbrName(String psEbrName) {
		this.psEbrName = psEbrName;
	}
	public Integer getPsState() {
		return psState;
	}
	public void setPsState(Integer psState) {
		this.psState = psState;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
	public String getParentPsEbrId() {
		return parentPsEbrId;
	}
	public void setParentPsEbrId(String parentPsEbrId) {
		this.parentPsEbrId = parentPsEbrId;
	}
	public String getPsAddress() {
		return psAddress;
	}
	public void setPsAddress(String psAddress) {
		this.psAddress = psAddress;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
	public Integer getPlatLevel() {
		return platLevel;
	}
	public void setPlatLevel(Integer platLevel) {
		this.platLevel = platLevel;
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
	public String getPsType() {
		return psType;
	}
	public void setPsType(String psType) {
		this.psType = psType;
	}
}
