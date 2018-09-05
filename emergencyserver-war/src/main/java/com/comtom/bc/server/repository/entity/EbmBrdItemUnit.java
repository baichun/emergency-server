package com.comtom.bc.server.repository.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * <b>bc_ebm_brd_item_unit[bc_ebm_brd_item_unit]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author Kidsoul Qu
 * @date 2016-12-26 21:11:22
 */
@Entity
@IdClass(ItemUnitPk.class)
@Table(name = "bc_ebm_brd_item_unit")
public class EbmBrdItemUnit {

	/**
	 * 播发记录ID
	 */
	@Id
	private String brdItemId;

	/**
	 * 部门ID
	 */
	@Id
	private String unitId;

	/**
	 * 部门名称
	 */
	private String unitName;

	/**
	 * 播放人员ID
	 */
	private String persionId;

	/**
	 * 播放人员名称
	 */
	private String persionName;

	/**
	 * 应急广播平台ID
	 */
	private String ebrpsId;

	/**
	 * 播发记录ID
	 * 
	 * @return brdItemId
	 */
	public String getBrdItemId() {
		return brdItemId;
	}

	/**
	 * 部门ID
	 * 
	 * @return unitId
	 */
	public String getUnitId() {
		return unitId;
	}

	/**
	 * 部门名称
	 * 
	 * @return unitName
	 */
	public String getUnitName() {
		return unitName;
	}

	/**
	 * 播放人员ID
	 * 
	 * @return persionId
	 */
	public String getPersionId() {
		return persionId;
	}

	/**
	 * 播放人员名称
	 * 
	 * @return persionName
	 */
	public String getPersionName() {
		return persionName;
	}

	/**
	 * 应急广播平台ID
	 * 
	 * @return ebrpsId
	 */
	public String getEbrpsId() {
		return ebrpsId;
	}

	/**
	 * 播发记录ID
	 * 
	 * @param brdItemId
	 */
	public void setBrdItemId(String brdItemId) {
		this.brdItemId = brdItemId;
	}

	/**
	 * 部门ID
	 * 
	 * @param unitId
	 */
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	/**
	 * 部门名称
	 * 
	 * @param unitName
	 */
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	/**
	 * 播放人员ID
	 * 
	 * @param persionId
	 */
	public void setPersionId(String persionId) {
		this.persionId = persionId;
	}

	/**
	 * 播放人员名称
	 * 
	 * @param persionName
	 */
	public void setPersionName(String persionName) {
		this.persionName = persionName;
	}

	/**
	 * 应急广播平台ID
	 * 
	 * @param ebrpsId
	 */
	public void setEbrpsId(String ebrpsId) {
		this.ebrpsId = ebrpsId;
	}

}