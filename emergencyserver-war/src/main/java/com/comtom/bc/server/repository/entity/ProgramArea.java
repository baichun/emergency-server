package com.comtom.bc.server.repository.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * <b>bc_program_area[bc_program_area]数据持久化对象</b>
 * 
 * @author zhucanhui
 * @date 2016-12-17 17:08:42
 */
@Entity
@Table(name = "bc_program_area")
public class ProgramArea {

	/**
	 * 标识id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 节目编号
	 */
	private Long programId;

	/**
	 * 关联区域编码
	 */
	private String areaCode;

	/**
	 * 区域名称
	 */
	@Transient
	private String areaName;

	/**
	 * 标识id
	 * 
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 节目编号
	 * 
	 * @return programId
	 */
	public Long getProgramId() {
		return programId;
	}

	/**
	 * 关联区域编码
	 * 
	 * @return areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * 标识id
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 节目编号
	 * 
	 * @param programId
	 */
	public void setProgramId(Long programId) {
		this.programId = programId;
	}

	/**
	 * 关联区域编码
	 * 
	 * @param areaCode
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
}