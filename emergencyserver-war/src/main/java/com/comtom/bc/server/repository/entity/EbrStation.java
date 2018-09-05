package com.comtom.bc.server.repository.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * <b>bc_ebr_station[bc_ebr_station]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author Kidsoul Qu
 * @date 2016-12-13 14:50:14
 */
@Entity
@Table(name = "bc_ebr_station")
public class EbrStation {

	/**
	 * 台站ID
	 */
	@Id
	private String stationEbrId;
	
	/**
	 * 台站名称
	 */
	private String stationName;
	
	/**
	 * 台站地址
	 */
	private String stationAddress;
	
	/**
	 * 台站类型
	 */
	private String stationType;
	
	/**
	 * 台站联系人
	 */
	private String contact;
	
	/**
	 * 联系人电话
	 */
	private String phoneNumber;
	
	/**
	 * 台站经度
	 */
	private String longitude;
	
	/**
	 * 台站纬度
	 */
	private String latitude;
	
	/**
	 * 覆盖区域编码
	 */
	private String areaCode;
	
	/**
	 * 覆盖区域名称
	 */
	@Transient
	private String areaName;
	
	/**
	 * EWBS平台ID
	 */
	private String relatedPsEbrId;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;
	
	/**
	 * 是否已同步标识（1：未同步 2：已同步）
	 */
	private Integer syncFlag;
	
	/**
	 * 覆盖的区域详情列表
	 */
	@Transient
	private List<RegionArea> coveredAreas;
	
	/**
	 * 台站ID
	 * 
	 * @return stationEbrId
	 */
	public String getStationEbrId() {
		return stationEbrId;
	}
	
	/**
	 * 台站名称
	 * 
	 * @return stationName
	 */
	public String getStationName() {
		return stationName;
	}
	
	/**
	 * 台站地址
	 * 
	 * @return stationAddress
	 */
	public String getStationAddress() {
		return stationAddress;
	}
	
	/**
	 * 台站类型
	 * 
	 * @return stationType
	 */
	public String getStationType() {
		return stationType;
	}
	
	/**
	 * 台站联系人
	 * 
	 * @return contact
	 */
	public String getContact() {
		return contact;
	}
	
	/**
	 * 联系人电话
	 * 
	 * @return phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	/**
	 * 台站经度
	 * 
	 * @return longitude
	 */
	public String getLongitude() {
		return longitude;
	}
	
	/**
	 * 台站纬度
	 * 
	 * @return latitude
	 */
	public String getLatitude() {
		return latitude;
	}
	
	/**
	 * 覆盖区域编码
	 * 
	 * @return areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}
	
	/**
	 * EWBS平台ID
	 * 
	 * @return relatedPsEbrId
	 */
	public String getRelatedPsEbrId() {
		return relatedPsEbrId;
	}
	
	/**
	 * 创建时间
	 * 
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	
	/**
	 * 更新时间
	 * 
	 * @return updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	
	/**
	 * 是否已同步标识（1：未同步 2：已同步）
	 * 
	 * @return syncFlag
	 */
	public Integer getSyncFlag() {
		return syncFlag;
	}
	

	/**
	 * 台站ID
	 * 
	 * @param stationEbrId
	 */
	public void setStationEbrId(String stationEbrId) {
		this.stationEbrId = stationEbrId;
	}
	
	/**
	 * 台站名称
	 * 
	 * @param stationName
	 */
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	
	/**
	 * 台站地址
	 * 
	 * @param stationAddress
	 */
	public void setStationAddress(String stationAddress) {
		this.stationAddress = stationAddress;
	}
	
	/**
	 * 台站类型
	 * 
	 * @param stationType
	 */
	public void setStationType(String stationType) {
		this.stationType = stationType;
	}
	
	/**
	 * 台站联系人
	 * 
	 * @param contact
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	/**
	 * 联系人电话
	 * 
	 * @param phoneNumber
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	/**
	 * 台站经度
	 * 
	 * @param longitude
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	/**
	 * 台站纬度
	 * 
	 * @param latitude
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	/**
	 * 覆盖区域编码
	 * 
	 * @param areaCode
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
	/**
	 * EWBS平台ID
	 * 
	 * @param relatedPsEbrId
	 */
	public void setRelatedPsEbrId(String relatedPsEbrId) {
		this.relatedPsEbrId = relatedPsEbrId;
	}
	
	/**
	 * 创建时间
	 * 
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * 更新时间
	 * 
	 * @param updateTime
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	/**
	 * 是否已同步标识（1：未同步 2：已同步）
	 * 
	 * @param syncFlag
	 */
	public void setSyncFlag(Integer syncFlag) {
		this.syncFlag = syncFlag;
	}

	public List<RegionArea> getCoveredAreas() {
		return coveredAreas;
	}

	public void setCoveredAreas(List<RegionArea> coveredAreas) {
		this.coveredAreas = coveredAreas;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
}