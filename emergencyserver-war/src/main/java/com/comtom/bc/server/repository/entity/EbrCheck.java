package com.comtom.bc.server.repository.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <b>bc_ebr_check[bc_ebr_check]数据持久化对象</b>
 * 
 */
@Entity
@Table(name = "bc_ebr_check")
public class EbrCheck {
	
	@Id
	private String ebrId;
	
	private Date rptTime;

	/**
	 * @return the ebrId
	 */
	public String getEbrId() {
		return ebrId;
	}

	/**
	 * @param ebrId the ebrId to set
	 */
	public void setEbrId(String ebrId) {
		this.ebrId = ebrId;
	}

	/**
	 * @return the rptTime
	 */
	public Date getRptTime() {
		return rptTime;
	}

	/**
	 * @param rptTime the rptTime to set
	 */
	public void setRptTime(Date rptTime) {
		this.rptTime = rptTime;
	}

}