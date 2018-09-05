package com.comtom.bc.server.repository.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * <b>bc_ebm_dispatch[bc_ebm_dispatch]数据持久化对象</b>
 * 
 * @author zhucanhui
 * @date 2016-12-22 15:24:40
 */
@Entity
@Table(name = "bc_ebm_dispatch")
public class EbmDispatch {

	/**
	 * 调用资源数据Id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dispatchId;

	/**
	 * 语种代码
	 */
	private String languageCode;

	/**
	 * 应急广播平台Id
	 */
	private String psEbrId;

	/**
	 * 应急广播平台名称
	 */
	@Transient
	private String psEbrName;

	/**
	 * 播出系统Id
	 */
	private String bsEbrId;

	/**
	 * 播出系统名称
	 */
	@Transient
	private String bsEbrName;

	/**
	 * 播出系统类型
	 */
	private String brdSysType;

	/**
	 * 播出系统信息
	 */
	private String brdSysInfo;

	/**
	 * 关联数据包Id
	 */
	private String ebdId;

	/**
	 * 关联EBM
	 */
	private String ebmId;

	/**
	 * 调度状态 0:待调度 1:已调度
	 */
	private Integer state;

	/**
	 * 调度分发时间
	 */
	private Date dispatchTime;

	/**
	 * 区域名称
	 */
	@Transient
	private String areaName;

	/**
	 * 调用资源数据Id
	 * 
	 * @return dispatchId
	 */
	public Integer getDispatchId() {
		return dispatchId;
	}

	/**
	 * 语种代码
	 * 
	 * @return languageCode
	 */
	public String getLanguageCode() {
		return languageCode;
	}

	/**
	 * 应急广播平台Id
	 * 
	 * @return psEbrId
	 */
	public String getPsEbrId() {
		return psEbrId;
	}

	/**
	 * 播出系统Id
	 * 
	 * @return bsEbrId
	 */
	public String getBsEbrId() {
		return bsEbrId;
	}

	/**
	 * 播出系统类型
	 * 
	 * @return brdSysType
	 */
	public String getBrdSysType() {
		return brdSysType;
	}

	/**
	 * 播出系统信息
	 * 
	 * @return brdSysInfo
	 */
	public String getBrdSysInfo() {
		return brdSysInfo;
	}

	/**
	 * 关联EBM
	 * 
	 * @return ebmId
	 */
	public String getEbmId() {
		return ebmId;
	}

	/**
	 * 调用资源数据Id
	 * 
	 * @param dispatchId
	 */
	public void setDispatchId(Integer dispatchId) {
		this.dispatchId = dispatchId;
	}

	/**
	 * 语种代码
	 * 
	 * @param languageCode
	 */
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	/**
	 * 应急广播平台Id
	 * 
	 * @param psEbrId
	 */
	public void setPsEbrId(String psEbrId) {
		this.psEbrId = psEbrId;
	}

	/**
	 * 播出系统Id
	 * 
	 * @param bsEbrId
	 */
	public void setBsEbrId(String bsEbrId) {
		this.bsEbrId = bsEbrId;
	}

	/**
	 * 播出系统类型
	 * 
	 * @param brdSysType
	 */
	public void setBrdSysType(String brdSysType) {
		this.brdSysType = brdSysType;
	}

	/**
	 * 播出系统信息
	 * 
	 * @param brdSysInfo
	 */
	public void setBrdSysInfo(String brdSysInfo) {
		this.brdSysInfo = brdSysInfo;
	}

	/**
	 * 关联EBM
	 * 
	 * @param ebmId
	 */
	public void setEbmId(String ebmId) {
		this.ebmId = ebmId;
	}

	/**
	 * @return the state
	 */
	public Integer getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(Integer state) {
		this.state = state;
	}

	/**
	 * @return the ebdId
	 */
	public String getEbdId() {
		return ebdId;
	}

	/**
	 * @param ebdId
	 *            the ebdId to set
	 */
	public void setEbdId(String ebdId) {
		this.ebdId = ebdId;
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
	 * @return the bsEbrName
	 */
	public String getBsEbrName() {
		return bsEbrName;
	}

	/**
	 * @param bsEbrName
	 *            the bsEbrName to set
	 */
	public void setBsEbrName(String bsEbrName) {
		this.bsEbrName = bsEbrName;
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
	 * @return the dispatchTime
	 */
	public Date getDispatchTime() {
		return dispatchTime;
	}

	/**
	 * @param dispatchTime
	 *            the dispatchTime to set
	 */
	public void setDispatchTime(Date dispatchTime) {
		this.dispatchTime = dispatchTime;
	}

}