package com.comtom.bc.server.repository.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * <b>v_bc_ebr_platform_info数据视图对象</b>
 * 
 * @author zhucanhui
 */
@Entity
@Table(name = "v_bc_ebr_platform_info")
@NamedQuery(name = "VEbrPlatform.findAll", query = "SELECT t FROM VEbrPlatform t")
public class VEbrPlatform {

	/**
	 * 平台ID
	 */
	@Id
	@Column(name = "psEbrId")
	private String psEbrId;

	/**
	 * 平台网络地址
	 */
	private String psUrl;

	/**
	 * 平台名称
	 */
	private String psEbrName;

	/**
	 * 平台状态（1:运行2:停止3:故障4:故障恢复）
	 */
	private Integer psState;

	/**
	 * 平台覆盖区域编码
	 */
	private String areaCode;

	/**
	 * 平台覆盖区域名称
	 */
	private String areaName;

	/**
	 * 平台类型
	 */
	private String psType;

	/**
	 * 父平台资源编号
	 */
	private String parentPsEbrId;

	/**
	 * 平台地址
	 */
	private String psAddress;

	/**
	 * 联系人名称
	 */
	private String contact;

	/**
	 * 联系人电话
	 */
	private String phoneNumber;

	/**
	 * 平台经度
	 */
	private String longitude;

	/**
	 * 平台纬度
	 */
	private String latitude;

	/**
	 * 平台级别
	 */
	private Integer platLevel;

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
	 * @return the psEbrId
	 */
	public String getPsEbrId() {
		return psEbrId;
	}

	/**
	 * @param psEbrId
	 *            the psEbrId to set
	 */
	public void setPsEbrId(String psEbrId) {
		this.psEbrId = psEbrId;
	}

	/**
	 * @return the psUrl
	 */
	public String getPsUrl() {
		return psUrl;
	}

	/**
	 * @param psUrl
	 *            the psUrl to set
	 */
	public void setPsUrl(String psUrl) {
		this.psUrl = psUrl;
	}

	/**
	 * @return the psEbrName
	 */
	public String getPsEbrName() {
		return psEbrName;
	}

	/**
	 * @param psEbrName
	 *            the psEbrName to set
	 */
	public void setPsEbrName(String psEbrName) {
		this.psEbrName = psEbrName;
	}

	/**
	 * @return the psState
	 */
	public Integer getPsState() {
		return psState;
	}

	/**
	 * @param psState
	 *            the psState to set
	 */
	public void setPsState(Integer psState) {
		this.psState = psState;
	}

	/**
	 * @return the areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * @param areaCode
	 *            the areaCode to set
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * @return the areaName
	 */
	public String getAreaName() {
		return areaName;
	}

	/**
	 * @param areaName
	 *            the areaName to set
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	/**
	 * @return the psType
	 */
	public String getPsType() {
		return psType;
	}

	/**
	 * @param psType
	 *            the psType to set
	 */
	public void setPsType(String psType) {
		this.psType = psType;
	}

	/**
	 * @return the parentPsEbrId
	 */
	public String getParentPsEbrId() {
		return parentPsEbrId;
	}

	/**
	 * @param parentPsEbrId
	 *            the parentPsEbrId to set
	 */
	public void setParentPsEbrId(String parentPsEbrId) {
		this.parentPsEbrId = parentPsEbrId;
	}

	/**
	 * @return the psAddress
	 */
	public String getPsAddress() {
		return psAddress;
	}

	/**
	 * @param psAddress
	 *            the psAddress to set
	 */
	public void setPsAddress(String psAddress) {
		this.psAddress = psAddress;
	}

	/**
	 * @return the contact
	 */
	public String getContact() {
		return contact;
	}

	/**
	 * @param contact
	 *            the contact to set
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber
	 *            the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the platLevel
	 */
	public Integer getPlatLevel() {
		return platLevel;
	}

	/**
	 * @param platLevel
	 *            the platLevel to set
	 */
	public void setPlatLevel(Integer platLevel) {
		this.platLevel = platLevel;
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
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the syncFlag
	 */
	public Integer getSyncFlag() {
		return syncFlag;
	}

	/**
	 * @param syncFlag
	 *            the syncFlag to set
	 */
	public void setSyncFlag(Integer syncFlag) {
		this.syncFlag = syncFlag;
	}

}