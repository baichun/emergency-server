package com.comtom.bc.server.repository.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <b>bc_ebd_response[bc_ebd_response]数据持久化对象</b>
 * 
 * @author zhucanhui
 * @date 2016-12-20 17:03:37
 */
@Entity
@Table(name = "bc_ebd_response")
public class EbdResponse {

	/**
	 * 业务数据包Id
	 */
	@Id
	private String ebdId;

	/**
	 * 业务数据类型
	 */
	private String ebdType;
	
	/**
	 * 业务数据包生成时间
	 */
	private Date ebdTime;
	
	/**
	 * 状态码
	 */
	private Integer resultCode;
	
	/**
	 * 状态描述
	 */
	private String resultDesc;
	
	/**
	 * 业务数据包标识（1：接收 2：发送）
	 */
	private Integer sendFlag;
	
	
	/**
	 * ebd的版本
	 */
	private String ebdVersion;
	
	/**
	 * ebd数据源的资源ID
	 */
	private String ebdSrcEbrId;
	
	/**
	 * 关联ebdID
	 */
	private String relatedEbdId;

	/**
	 * 业务数据包Id
	 * 
	 * @return ebdId
	 */
	public String getEbdId() {
		return ebdId;
	}
	
	/**
	 * 业务数据类型
	 * 
	 * @return ebdType
	 */
	public String getEbdType() {
		return ebdType;
	}
	
	/**
	 * 业务数据包生成时间
	 * 
	 * @return ebdTime
	 */
	public Date getEbdTime() {
		return ebdTime;
	}
	
	/**
	 * 状态码
	 * 
	 * @return resultCode
	 */
	public Integer getResultCode() {
		return resultCode;
	}
	
	/**
	 * 状态描述
	 * 
	 * @return resultDesc
	 */
	public String getResultDesc() {
		return resultDesc;
	}
	
	/**
	 * 业务数据包标识（1：接收 2：发送）
	 * 
	 * @return sendFlag
	 */
	public Integer getSendFlag() {
		return sendFlag;
	}
	

	/**
	 * 业务数据包Id
	 * 
	 * @param ebdId
	 */
	public void setEbdId(String ebdId) {
		this.ebdId = ebdId;
	}
	
	/**
	 * 业务数据类型
	 * 
	 * @param ebdType
	 */
	public void setEbdType(String ebdType) {
		this.ebdType = ebdType;
	}
	
	/**
	 * 业务数据包生成时间
	 * 
	 * @param ebdTime
	 */
	public void setEbdTime(Date ebdTime) {
		this.ebdTime = ebdTime;
	}
	
	/**
	 * 状态码
	 * 
	 * @param resultCode
	 */
	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}
	
	/**
	 * 状态描述
	 * 
	 * @param resultDesc
	 */
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	
	/**
	 * 业务数据包标识（1：接收 2：发送）
	 * 
	 * @param sendFlag
	 */
	public void setSendFlag(Integer sendFlag) {
		this.sendFlag = sendFlag;
	}

	/**
	 * @return the ebd的版本
	 */
	public String getEbdVersion() {
		return ebdVersion;
	}

	/**
	 * @param ebd的版本 the ebdVersion to set
	 */
	public void setEbdVersion(String ebdVersion) {
		this.ebdVersion = ebdVersion;
	}

	/**
	 * @return the ebd数据源的资源ID
	 */
	public String getEbdSrcEbrId() {
		return ebdSrcEbrId;
	}

	/**
	 * @param ebd数据源的资源ID the ebdSrcEbrId to set
	 */
	public void setEbdSrcEbrId(String ebdSrcEbrId) {
		this.ebdSrcEbrId = ebdSrcEbrId;
	}

	/**
	 * @return the 关联ebdID
	 */
	public String getRelatedEbdId() {
		return relatedEbdId;
	}

	/**
	 * @param 关联ebdID the relatedEbdId to set
	 */
	public void setRelatedEbdId(String relatedEbdId) {
		this.relatedEbdId = relatedEbdId;
	}

}