package com.comtom.bc.server.repository.entity;

import java.io.Serializable;

/**
 * 播发记录联合主键
 * 
 * @author zhucanhui
 *
 */
public class ItemUnitPk implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3037981622165072954L;

	/**
	 * 播发记录ID
	 */
	private String brdItemId;

	/**
	 * 部门ID
	 */
	private String unitId;

	/**
	 * @return the brdItemId
	 */
	public String getBrdItemId() {
		return brdItemId;
	}

	/**
	 * @param brdItemId
	 *            the brdItemId to set
	 */
	public void setBrdItemId(String brdItemId) {
		this.brdItemId = brdItemId;
	}

	/**
	 * @return the unitId
	 */
	public String getUnitId() {
		return unitId;
	}

	/**
	 * @param unitId
	 *            the unitId to set
	 */
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

}
