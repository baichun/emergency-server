package com.comtom.bc.server.repository.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * <b>bc_ebr_adaptor[bc_ebr_adaptor]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author Kidsoul Qu
 * @date 2016-12-06 16:57:07
 */
@Entity
@Table(name = "bc_ebr_adaptor")
public class EbrAdaptor {

	/**
	 * 接收设备ID
	 */
	@Id
	private String asEbrId;

	/**
	 * 接收设备名称
	 */
	private String asEbrName;

	/**
	 * 联动对接地址
	 */
	private String asUrl;

	/**
	 * 接收设备类型
	 */
	private String asType;

	/**
	 * 关联台站编号
	 */
	private String relatedRsEbrId;

	/**
	 * 关联平台编号
	 */
	private String relatedPsEbrId;

	/**
	 * 覆盖区域编码
	 */
	private String areaCode;
	
	/**
	 * 覆盖的区域名称
	 */
	@Transient
	private String areaName;

	/**
	 * 接收设备经度
	 */
	private String longitude;

	/**
	 * 接收设备纬度
	 */
	private String latitude;

	/**
	 * 适配器状态
	 */
	private Integer asState;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 同步标识
	 */
	private Integer syncFlag;

	/**
	 * 扩展字段
	 */
	private String param1;

	/**
	 * 扩展字段
	 */
	private String param2;

	/**
	 * 覆盖的区域详情列表
	 */
	@Transient
	private List<RegionArea> coveredAreas;

	/**
	 * @return the asEbrId
	 */
	public String getAsEbrId() {
		return asEbrId;
	}

	/**
	 * @param asEbrId
	 *            the asEbrId to set
	 */
	public void setAsEbrId(String asEbrId) {
		this.asEbrId = asEbrId;
	}

	/**
	 * @return the asEbrName
	 */
	public String getAsEbrName() {
		return asEbrName;
	}

	/**
	 * @param asEbrName
	 *            the asEbrName to set
	 */
	public void setAsEbrName(String asEbrName) {
		this.asEbrName = asEbrName;
	}

	/**
	 * @return the asUrl
	 */
	public String getAsUrl() {
		return asUrl;
	}

	/**
	 * @param asUrl
	 *            the asUrl to set
	 */
	public void setAsUrl(String asUrl) {
		this.asUrl = asUrl;
	}

	/**
	 * @return the asType
	 */
	public String getAsType() {
		return asType;
	}

	/**
	 * @param asType
	 *            the asType to set
	 */
	public void setAsType(String asType) {
		this.asType = asType;
	}

	/**
	 * @return the relatedRsEbrId
	 */
	public String getRelatedRsEbrId() {
		return relatedRsEbrId;
	}

	/**
	 * @param relatedRsEbrId
	 *            the relatedRsEbrId to set
	 */
	public void setRelatedRsEbrId(String relatedRsEbrId) {
		this.relatedRsEbrId = relatedRsEbrId;
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
	 * @return the asState
	 */
	public Integer getAsState() {
		return asState;
	}

	/**
	 * @param asState
	 *            the asState to set
	 */
	public void setAsState(Integer asState) {
		this.asState = asState;
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

	/**
	 * @return the param1
	 */
	public String getParam1() {
		return param1;
	}

	/**
	 * @param param1
	 *            the param1 to set
	 */
	public void setParam1(String param1) {
		this.param1 = param1;
	}

	/**
	 * @return the param2
	 */
	public String getParam2() {
		return param2;
	}

	/**
	 * @param param2
	 *            the param2 to set
	 */
	public void setParam2(String param2) {
		this.param2 = param2;
	}

	/**
	 * @return the coveredAreas
	 */
	public List<RegionArea> getCoveredAreas() {
		return coveredAreas;
	}

	/**
	 * @param coveredAreas
	 *            the coveredAreas to set
	 */
	public void setCoveredAreas(List<RegionArea> coveredAreas) {
		this.coveredAreas = coveredAreas;
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
	
}