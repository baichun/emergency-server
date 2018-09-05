package com.comtom.bc.server.repository.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <b>bc_omd_request[bc_omd_request]数据持久化对象</b>
 * 
 * @author zhucanhui
 * @date 2016-12-20 17:03:38
 */
@Entity
@Table(name = "bc_omd_request")
public class OmdRequest {

	/**
	 * 运维数据请求编号
	 */
	@Id
	private String omdRequestId;

	/**
	 * 运维数据类型
	 */
	private String omdType;

	/**
	 * 记录开始时间
	 */
	private Date rptStartTime;

	/**
	 * 记录结束时间
	 */
	private Date rptEndTime;

	/**
	 * 数据包操作类型
	 */
	private String rptType;

	/**
	 * 关联业务数据包编号（发送）
	 */
	private String relatedEbdId;

	/**
	 * 关联运维数据请求资源编号（发送）
	 */
	private String relatedEbrId;

	/**
	 * 运维数据请求编号
	 * 
	 * @return omdRequestId
	 */
	public String getOmdRequestId() {
		return omdRequestId;
	}

	/**
	 * 运维数据类型
	 * 
	 * @return omdType
	 */
	public String getOmdType() {
		return omdType;
	}

	/**
	 * 记录开始时间
	 * 
	 * @return rptStartTime
	 */
	public Date getRptStartTime() {
		return rptStartTime;
	}

	/**
	 * 记录结束时间
	 * 
	 * @return rptEndTime
	 */
	public Date getRptEndTime() {
		return rptEndTime;
	}

	/**
	 * 数据包操作类型
	 * 
	 * @return rptType
	 */
	public String getRptType() {
		return rptType;
	}

	/**
	 * 关联业务数据包编号（发送）
	 * 
	 * @return relatedEbdId
	 */
	public String getRelatedEbdId() {
		return relatedEbdId;
	}

	/**
	 * 关联运维数据请求资源编号（发送）
	 * 
	 * @return relatedEbrId
	 */
	public String getRelatedEbrId() {
		return relatedEbrId;
	}

	/**
	 * 运维数据请求编号
	 * 
	 * @param omdRequestId
	 */
	public void setOmdRequestId(String omdRequestId) {
		this.omdRequestId = omdRequestId;
	}

	/**
	 * 运维数据类型
	 * 
	 * @param omdType
	 */
	public void setOmdType(String omdType) {
		this.omdType = omdType;
	}

	/**
	 * 记录开始时间
	 * 
	 * @param rptStartTime
	 */
	public void setRptStartTime(Date rptStartTime) {
		this.rptStartTime = rptStartTime;
	}

	/**
	 * 记录结束时间
	 * 
	 * @param rptEndTime
	 */
	public void setRptEndTime(Date rptEndTime) {
		this.rptEndTime = rptEndTime;
	}

	/**
	 * 数据包操作类型
	 * 
	 * @param rptType
	 */
	public void setRptType(String rptType) {
		this.rptType = rptType;
	}

	/**
	 * 关联业务数据包编号（发送）
	 * 
	 * @param relatedEbdId
	 */
	public void setRelatedEbdId(String relatedEbdId) {
		this.relatedEbdId = relatedEbdId;
	}

	/**
	 * 关联运维数据请求资源编号（发送）
	 * 
	 * @param relatedEbrId
	 */
	public void setRelatedEbrId(String relatedEbrId) {
		this.relatedEbrId = relatedEbrId;
	}

}