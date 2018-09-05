package com.comtom.bc.server.repository.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * <b>bc_ebr_platform[bc_ebr_platform]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author Kidsoul Qu
 * @date 2016-12-06 16:57:08
 */
@Entity
@Table(name = "bc_ebr_platform")
public class EbrPlatform {

	/**
	 * 平台ID
	 */
	@Id
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
	@Transient
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
	 * 覆盖面积
	 */
	private BigDecimal square;

	/**
	 * 覆盖人口
	 */
	private BigDecimal population;

	/**
	 * 平台状态是否已同步标识（1：未同步 2：已同步）
	 */
	private Integer statusSyncFlag;

	/**
	 * 覆盖的区域详情列表
	 */
	@Transient
	private List<RegionArea> coveredAreas;

	/**
	 * 平台ID
	 * 
	 * @return psEbrId
	 */
	public String getPsEbrId() {
		return psEbrId;
	}

	/**
	 * 平台网络地址
	 * 
	 * @return psUrl
	 */
	public String getPsUrl() {
		return psUrl;
	}

	/**
	 * 平台名称
	 * 
	 * @return psEbrName
	 */
	public String getPsEbrName() {
		return psEbrName;
	}

	/**
	 * 平台状态（1:运行2:停止3:故障4:故障恢复）
	 * 
	 * @return psState
	 */
	public Integer getPsState() {
		return psState;
	}

	/**
	 * 平台覆盖区域编码
	 * 
	 * @return areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * 平台类型
	 * 
	 * @return psType
	 */
	public String getPsType() {
		return psType;
	}

	/**
	 * 父平台资源编号
	 * 
	 * @return parentPsEbrId
	 */
	public String getParentPsEbrId() {
		return parentPsEbrId;
	}

	/**
	 * 平台地址
	 * 
	 * @return psAddress
	 */
	public String getPsAddress() {
		return psAddress;
	}

	/**
	 * 联系人名称
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
	 * 平台经度
	 * 
	 * @return longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * 平台纬度
	 * 
	 * @return latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * 平台级别
	 * 
	 * @return platLevel
	 */
	public Integer getPlatLevel() {
		return platLevel;
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
	 * 平台ID
	 * 
	 * @param psEbrId
	 */
	public void setPsEbrId(String psEbrId) {
		this.psEbrId = psEbrId;
	}

	/**
	 * 平台网络地址
	 * 
	 * @param psUrl
	 */
	public void setPsUrl(String psUrl) {
		this.psUrl = psUrl;
	}

	/**
	 * 平台名称
	 * 
	 * @param psEbrName
	 */
	public void setPsEbrName(String psEbrName) {
		this.psEbrName = psEbrName;
	}

	/**
	 * 平台状态（1:运行2:停止3:故障4:故障恢复）
	 * 
	 * @param psState
	 */
	public void setPsState(Integer psState) {
		this.psState = psState;
	}

	/**
	 * 平台覆盖区域编码
	 * 
	 * @param areaCode
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * 平台类型
	 * 
	 * @param psType
	 */
	public void setPsType(String psType) {
		this.psType = psType;
	}

	/**
	 * 父平台资源编号
	 * 
	 * @param parentPsEbrId
	 */
	public void setParentPsEbrId(String parentPsEbrId) {
		this.parentPsEbrId = parentPsEbrId;
	}

	/**
	 * 平台地址
	 * 
	 * @param psAddress
	 */
	public void setPsAddress(String psAddress) {
		this.psAddress = psAddress;
	}

	/**
	 * 联系人名称
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
	 * 平台经度
	 * 
	 * @param longitude
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * 平台纬度
	 * 
	 * @param latitude
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * 平台级别
	 * 
	 * @param platLevel
	 */
	public void setPlatLevel(Integer platLevel) {
		this.platLevel = platLevel;
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

	/**
	 * @return the square
	 */
	public BigDecimal getSquare() {
		return square;
	}

	/**
	 * @param square
	 *            the square to set
	 */
	public void setSquare(BigDecimal square) {
		this.square = square;
	}

	/**
	 * @return the population
	 */
	public BigDecimal getPopulation() {
		return population;
	}

	/**
	 * @param population
	 *            the population to set
	 */
	public void setPopulation(BigDecimal population) {
		this.population = population;
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

	public Integer getStatusSyncFlag() {
		return statusSyncFlag;
	}

	public void setStatusSyncFlag(Integer statusSyncFlag) {
		this.statusSyncFlag = statusSyncFlag;
	}
}