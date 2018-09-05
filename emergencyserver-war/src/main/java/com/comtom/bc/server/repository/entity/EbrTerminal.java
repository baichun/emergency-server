package com.comtom.bc.server.repository.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <b>bc_ebr_terminal[bc_ebr_terminal]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author Kidsoul Qu
 * @date 2016-12-06 16:57:08
 */
@Entity
@Table(name = "bc_ebr_terminal")
public class EbrTerminal {

	/**
	 * 终端资源编号
	 */
	@Id
	private String terminalEbrId;
	
	/**
	 * 终端资源名称
	 */
	private String terminalEbrName;
	
	/**
	 * 关联平台编号
	 */
	private String relatedPsEbrId;
	
	/**
	 * 经度
	 */
	private String longitude;
	
	/**
	 * 纬度
	 */
	private String latitude;
	
	/**
	 * 终端类型
	 */
	private String terminalType;
	
	/**
	 * 终端状态
	 */
	private Integer terminalState;
	
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
	 * 扩展参数
	 */
	private String param1;
	
	/**
	 * 扩展参数
	 */
	private String param2;

	/**
	 * 终端状态同步标识
	 */
	private Integer statusSyncFlag;
	

	/**
	 * 终端资源编号
	 * 
	 * @return terminalEbrId
	 */
	public String getTerminalEbrId() {
		return terminalEbrId;
	}
	
	/**
	 * 终端资源名称
	 * 
	 * @return terminalEbrName
	 */
	public String getTerminalEbrName() {
		return terminalEbrName;
	}
	
	/**
	 * 关联平台编号
	 * 
	 * @return relatedPsEbrId
	 */
	public String getRelatedPsEbrId() {
		return relatedPsEbrId;
	}
	
	/**
	 * 经度
	 * 
	 * @return longitude
	 */
	public String getLongitude() {
		return longitude;
	}
	
	/**
	 * 纬度
	 * 
	 * @return latitude
	 */
	public String getLatitude() {
		return latitude;
	}
	
	/**
	 * 终端类型
	 * 
	 * @return terminalType
	 */
	public String getTerminalType() {
		return terminalType;
	}
	
	/**
	 * 终端状态
	 * 
	 * @return terminalSate
	 */
	public Integer getTerminalState() {
		return terminalState;
	}
	
	/**
	 * 创建时间
	 * 
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	
	/**
	 * 更新时间
	 * 
	 * @return updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	
	/**
	 * 同步标识
	 * 
	 * @return syncFlag
	 */
	public Integer getSyncFlag() {
		return syncFlag;
	}
	
	/**
	 * 扩展参数
	 * 
	 * @return param1
	 */
	public String getParam1() {
		return param1;
	}
	
	/**
	 * 扩展参数
	 * 
	 * @return param2
	 */
	public String getParam2() {
		return param2;
	}
	

	/**
	 * 终端资源编号
	 * 
	 * @param terminalEbrId
	 */
	public void setTerminalEbrId(String terminalEbrId) {
		this.terminalEbrId = terminalEbrId;
	}
	
	/**
	 * 终端资源名称
	 * 
	 * @param terminalEbrName
	 */
	public void setTerminalEbrName(String terminalEbrName) {
		this.terminalEbrName = terminalEbrName;
	}
	
	/**
	 * 关联平台编号
	 * 
	 * @param relatedPsEbrId
	 */
	public void setRelatedPsEbrId(String relatedPsEbrId) {
		this.relatedPsEbrId = relatedPsEbrId;
	}
	
	/**
	 * 经度
	 * 
	 * @param longitude
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	/**
	 * 纬度
	 * 
	 * @param latitude
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	/**
	 * 终端类型
	 * 
	 * @param terminalType
	 */
	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}
	
	/**
	 * 终端状态
	 * 
	 * @param terminalSate
	 */
	public void setTerminalState(Integer terminalSate) {
		this.terminalState = terminalSate;
	}
	
	/**
	 * 创建时间
	 * 
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * 更新时间
	 * 
	 * @param updateTime
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	/**
	 * 同步标识
	 * 
	 * @param syncFlag
	 */
	public void setSyncFlag(Integer syncFlag) {
		this.syncFlag = syncFlag;
	}
	
	/**
	 * 扩展参数
	 * 
	 * @param param1
	 */
	public void setParam1(String param1) {
		this.param1 = param1;
	}
	
	/**
	 * 扩展参数
	 * 
	 * @param param2
	 */
	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public Integer getStatusSyncFlag() {
		return statusSyncFlag;
	}

	public void setStatusSyncFlag(Integer statusSyncFlag) {
		this.statusSyncFlag = statusSyncFlag;
	}
}