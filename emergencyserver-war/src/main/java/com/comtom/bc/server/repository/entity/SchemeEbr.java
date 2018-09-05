package com.comtom.bc.server.repository.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * <b>bc_scheme_ebr[bc_scheme_ebr]数据持久化对象</b>
 * 
 * @author zhucanhui
 * @date 2016-12-20 16:04:23
 */
@Entity
@Table(name = "bc_scheme_ebr")
public class SchemeEbr {

	/**
	 * 方案资源关联编号
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 关联资源编号
	 */
	private String ebrId;

	/**
	 * 关联调度方案编号
	 */
	private Integer schemeId;

	/**
	 * 资源类型
	 */
	private String ebrType;

	/**
	 * 资源覆盖区域
	 */
	private String ebrArea;

	/**
	 * 应急广播平台资源
	 */
	@Transient
	private EbrPlatform ebrPlatform;

	/**
	 * 播出系统资源
	 */
	@Transient
	private EbrBroadcast ebrBroadcast;

	/**
	 * 方案资源关联编号
	 * 
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 关联资源编号
	 * 
	 * @return ebrId
	 */
	public String getEbrId() {
		return ebrId;
	}

	/**
	 * 关联调度方案编号
	 * 
	 * @return schemeId
	 */
	public Integer getSchemeId() {
		return schemeId;
	}

	/**
	 * 方案资源关联编号
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 关联资源编号
	 * 
	 * @param ebrId
	 */
	public void setEbrId(String ebrId) {
		this.ebrId = ebrId;
	}

	/**
	 * 关联调度方案编号
	 * 
	 * @param schemeId
	 */
	public void setSchemeId(Integer schemeId) {
		this.schemeId = schemeId;
	}

	/**
	 * @return the ebrType
	 */
	public String getEbrType() {
		return ebrType;
	}

	/**
	 * @param ebrType
	 *            the ebrType to set
	 */
	public void setEbrType(String ebrType) {
		this.ebrType = ebrType;
	}

	/**
	 * @return the ebrPlatform
	 */
	public EbrPlatform getEbrPlatform() {
		return ebrPlatform;
	}

	/**
	 * @param ebrPlatform
	 *            the ebrPlatform to set
	 */
	public void setEbrPlatform(EbrPlatform ebrPlatform) {
		this.ebrPlatform = ebrPlatform;
	}

	/**
	 * @return the ebrBroadcast
	 */
	public EbrBroadcast getEbrBroadcast() {
		return ebrBroadcast;
	}

	/**
	 * @param ebrBroadcast
	 *            the ebrBroadcast to set
	 */
	public void setEbrBroadcast(EbrBroadcast ebrBroadcast) {
		this.ebrBroadcast = ebrBroadcast;
	}

	/**
	 * @return the ebrArea
	 */
	public String getEbrArea() {
		return ebrArea;
	}

	/**
	 * @param ebrArea
	 *            the ebrArea to set
	 */
	public void setEbrArea(String ebrArea) {
		this.ebrArea = ebrArea;
	}

}