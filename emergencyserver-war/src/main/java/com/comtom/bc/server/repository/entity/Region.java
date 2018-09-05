package com.comtom.bc.server.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * <b>bc_region_area[bc_region_area]数据持久化对象</b>
 * 
 * @author zhucanhui
 */
@Entity
@Table(name = "bc_region")
public class Region {

	/**
	 * 区域编码
	 */
	@Id
	@Column(name = "areaCode")
	private String areaCode;

	/**
	 * 区域名称
	 */
	private String areaName;

	/**
	 * 上级区域
	 */
	private String parentAreaCode;

	/**
	 * 区域级别(省、市、县...）
	 */
	private Integer areaLevel;

	/**
	 * 区域面积， 单位平方公里
	 */
	private BigDecimal areaSquare;

	/**
	 * 区域人口， 单位万
	 */
	private BigDecimal areaPopulation;

	/**
	 * 应急中心经度
	 */
	private String centerLongitude;

	/**
	 * 应急中心纬度
	 */
	private String centerLatitude;

	/**
	 * 区域编码
	 * 
	 * @return areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * 区域名称
	 * 
	 * @return areaName
	 */
	public String getAreaName() {
		return areaName;
	}

	/**
	 * 上级区域
	 * 
	 * @return parentAreaCode
	 */
	public String getParentAreaCode() {
		return parentAreaCode;
	}

	/**
	 * 区域级别(省、市、县...）
	 * 
	 * @return areaLevel
	 */
	public Integer getAreaLevel() {
		return areaLevel;
	}

	/**
	 * 区域面积， 单位平方公里
	 * 
	 * @return areaSquare
	 */
	public BigDecimal getAreaSquare() {
		return areaSquare;
	}

	/**
	 * 区域人口， 单位万
	 * 
	 * @return areaPopulation
	 */
	public BigDecimal getAreaPopulation() {
		return areaPopulation;
	}

	/**
	 * 应急中心经度
	 * 
	 * @return centerLongitude
	 */
	public String getCenterLongitude() {
		return centerLongitude;
	}

	/**
	 * 应急中心纬度
	 * 
	 * @return centerLatitude
	 */
	public String getCenterLatitude() {
		return centerLatitude;
	}

	/**
	 * 区域编码
	 * 
	 * @param areaCode
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * 区域名称
	 * 
	 * @param areaName
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	/**
	 * 上级区域
	 * 
	 * @param parentAreaCode
	 */
	public void setParentAreaCode(String parentAreaCode) {
		this.parentAreaCode = parentAreaCode;
	}

	/**
	 * 区域级别(省、市、县...）
	 * 
	 * @param areaLevel
	 */
	public void setAreaLevel(Integer areaLevel) {
		this.areaLevel = areaLevel;
	}

	/**
	 * 区域面积， 单位平方公里
	 * 
	 * @param areaSquare
	 */
	public void setAreaSquare(BigDecimal areaSquare) {
		this.areaSquare = areaSquare;
	}

	/**
	 * 区域人口， 单位万
	 * 
	 * @param areaPopulation
	 */
	public void setAreaPopulation(BigDecimal areaPopulation) {
		this.areaPopulation = areaPopulation;
	}

	/**
	 * 应急中心经度
	 * 
	 * @param centerLongitude
	 */
	public void setCenterLongitude(String centerLongitude) {
		this.centerLongitude = centerLongitude;
	}

	/**
	 * 应急中心纬度
	 * 
	 * @param centerLatitude
	 */
	public void setCenterLatitude(String centerLatitude) {
		this.centerLatitude = centerLatitude;
	}

}