package com.comtom.bc.server.repository.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <b>bc_stat_ebd[bc_stat_ebd]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author Kidsoul Qu
 * @date 2017-01-12 17:21:07
 */
@Entity
@Table(name = "bc_stat_ebd")
public class StatEbd {

	/**
	 * statsicId
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer statsicId;
	
	/**
	 * 统计时间戳
	 */
	private Date statsicTime;
	
	/**
	 * 业务数据包标识（1：接收 2：发送）
	 */
	private Integer sendFlag;
	
	/**
	 * 业务数据包总数
	 */
	private Integer totalNum;
	
	/**
	 * 应急广播消息数目
	 */
	private Integer ebmNum;
	
	/**
	 * 应急广播消息播发状态查询数目
	 */
	private Integer ebmStatReqNum;
	
	/**
	 * 应急广播消息播发状态反馈数目
	 */
	private Integer ebmStatResNum;
	
	/**
	 * 运维数据请求数目
	 */
	private Integer omdReqNum;
	
	/**
	 * 应急广播平台信息数目
	 */
	private Integer ebrPSInfoNum;
	
	/**
	 * 台站前端信息数目
	 */
	private Integer ebrSTInfoNum;
	
	/**
	 * 消息接收设备信息数目
	 */
	private Integer ebrASInfoNum;
	
	/**
	 * 播出系统信息数目
	 */
	private Integer ebrBSInfoNum;
	
	/**
	 * 平台设备及终端信息数目
	 */
	private Integer ebrDTInfoNum;
	
	/**
	 * 播发记录数目
	 */
	private Integer ebmBrdLogNum;
	
	/**
	 * 应急广播平台状态数目
	 */
	private Integer ebrPSStateNum;
	
	/**
	 * 消息接收设备状态数目
	 */
	private Integer ebrASStateNum;
	
	/**
	 * 播出系统状态数目
	 */
	private Integer ebrBSStateNum;
	
	/**
	 * 平台设备及终端状态数目
	 */
	private Integer ebrDTStateNum;
	
	/**
	 * 1-日，2-周，3-月，4-季度，5-半年，6-年度
	 */
	private Integer statsicType;
	
	/**
	 * x年x月x日，x年x周，x年x月，x年上半年/下半年，x年
	 */
	private String statsicDuation;
	

	/**
	 * statsicId
	 * 
	 * @return statsicId
	 */
	public Integer getStatsicId() {
		return statsicId;
	}
	
	/**
	 * 统计时间戳
	 * 
	 * @return statsicTime
	 */
	public Date getStatsicTime() {
		return statsicTime;
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
	 * 业务数据包总数
	 * 
	 * @return totalNum
	 */
	public Integer getTotalNum() {
		return totalNum;
	}
	
	/**
	 * 应急广播消息数目
	 * 
	 * @return ebmNum
	 */
	public Integer getEbmNum() {
		return ebmNum;
	}
	
	/**
	 * 应急广播消息播发状态查询数目
	 * 
	 * @return ebmStatReqNum
	 */
	public Integer getEbmStatReqNum() {
		return ebmStatReqNum;
	}
	
	/**
	 * 应急广播消息播发状态反馈数目
	 * 
	 * @return ebmStatResNum
	 */
	public Integer getEbmStatResNum() {
		return ebmStatResNum;
	}
	
	/**
	 * 运维数据请求数目
	 * 
	 * @return omdReqNum
	 */
	public Integer getOmdReqNum() {
		return omdReqNum;
	}
	
	/**
	 * 应急广播平台信息数目
	 * 
	 * @return ebrPSInfoNum
	 */
	public Integer getEbrPSInfoNum() {
		return ebrPSInfoNum;
	}
	
	/**
	 * 台站前端信息数目
	 * 
	 * @return ebrSTInfoNum
	 */
	public Integer getEbrSTInfoNum() {
		return ebrSTInfoNum;
	}
	
	/**
	 * 消息接收设备信息数目
	 * 
	 * @return ebrASInfoNum
	 */
	public Integer getEbrASInfoNum() {
		return ebrASInfoNum;
	}
	
	/**
	 * 播出系统信息数目
	 * 
	 * @return ebrBSInfoNum
	 */
	public Integer getEbrBSInfoNum() {
		return ebrBSInfoNum;
	}
	
	/**
	 * 平台设备及终端信息数目
	 * 
	 * @return ebrDTInfoNum
	 */
	public Integer getEbrDTInfoNum() {
		return ebrDTInfoNum;
	}
	
	/**
	 * 播发记录数目
	 * 
	 * @return ebmBrdLogNum
	 */
	public Integer getEbmBrdLogNum() {
		return ebmBrdLogNum;
	}
	
	/**
	 * 应急广播平台状态数目
	 * 
	 * @return ebrPSStateNum
	 */
	public Integer getEbrPSStateNum() {
		return ebrPSStateNum;
	}
	
	/**
	 * 消息接收设备状态数目
	 * 
	 * @return ebrASStateNum
	 */
	public Integer getEbrASStateNum() {
		return ebrASStateNum;
	}
	
	/**
	 * 播出系统状态数目
	 * 
	 * @return ebrBSStateNum
	 */
	public Integer getEbrBSStateNum() {
		return ebrBSStateNum;
	}
	
	/**
	 * 平台设备及终端状态数目
	 * 
	 * @return ebrDTStateNum
	 */
	public Integer getEbrDTStateNum() {
		return ebrDTStateNum;
	}
	
	/**
	 * 1-日，2-周，3-月，4-季度，5-半年，6-年度
	 * 
	 * @return statsicType
	 */
	public Integer getStatsicType() {
		return statsicType;
	}
	
	/**
	 * x年x月x日，x年x周，x年x月，x年上半年/下半年，x年
	 * 
	 * @return statsicDuation
	 */
	public String getStatsicDuation() {
		return statsicDuation;
	}
	

	/**
	 * statsicId
	 * 
	 * @param statsicId
	 */
	public void setStatsicId(Integer statsicId) {
		this.statsicId = statsicId;
	}
	
	/**
	 * 统计时间戳
	 * 
	 * @param statsicTime
	 */
	public void setStatsicTime(Date statsicTime) {
		this.statsicTime = statsicTime;
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
	 * 业务数据包总数
	 * 
	 * @param totalNum
	 */
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	
	/**
	 * 应急广播消息数目
	 * 
	 * @param ebmNum
	 */
	public void setEbmNum(Integer ebmNum) {
		this.ebmNum = ebmNum;
	}
	
	/**
	 * 应急广播消息播发状态查询数目
	 * 
	 * @param ebmStatReqNum
	 */
	public void setEbmStatReqNum(Integer ebmStatReqNum) {
		this.ebmStatReqNum = ebmStatReqNum;
	}
	
	/**
	 * 应急广播消息播发状态反馈数目
	 * 
	 * @param ebmStatResNum
	 */
	public void setEbmStatResNum(Integer ebmStatResNum) {
		this.ebmStatResNum = ebmStatResNum;
	}
	
	/**
	 * 运维数据请求数目
	 * 
	 * @param omdReqNum
	 */
	public void setOmdReqNum(Integer omdReqNum) {
		this.omdReqNum = omdReqNum;
	}
	
	/**
	 * 应急广播平台信息数目
	 * 
	 * @param ebrPSInfoNum
	 */
	public void setEbrPSInfoNum(Integer ebrPSInfoNum) {
		this.ebrPSInfoNum = ebrPSInfoNum;
	}
	
	/**
	 * 台站前端信息数目
	 * 
	 * @param ebrSTInfoNum
	 */
	public void setEbrSTInfoNum(Integer ebrSTInfoNum) {
		this.ebrSTInfoNum = ebrSTInfoNum;
	}
	
	/**
	 * 消息接收设备信息数目
	 * 
	 * @param ebrASInfoNum
	 */
	public void setEbrASInfoNum(Integer ebrASInfoNum) {
		this.ebrASInfoNum = ebrASInfoNum;
	}
	
	/**
	 * 播出系统信息数目
	 * 
	 * @param ebrBSInfoNum
	 */
	public void setEbrBSInfoNum(Integer ebrBSInfoNum) {
		this.ebrBSInfoNum = ebrBSInfoNum;
	}
	
	/**
	 * 平台设备及终端信息数目
	 * 
	 * @param ebrDTInfoNum
	 */
	public void setEbrDTInfoNum(Integer ebrDTInfoNum) {
		this.ebrDTInfoNum = ebrDTInfoNum;
	}
	
	/**
	 * 播发记录数目
	 * 
	 * @param ebmBrdLogNum
	 */
	public void setEbmBrdLogNum(Integer ebmBrdLogNum) {
		this.ebmBrdLogNum = ebmBrdLogNum;
	}
	
	/**
	 * 应急广播平台状态数目
	 * 
	 * @param ebrPSStateNum
	 */
	public void setEbrPSStateNum(Integer ebrPSStateNum) {
		this.ebrPSStateNum = ebrPSStateNum;
	}
	
	/**
	 * 消息接收设备状态数目
	 * 
	 * @param ebrASStateNum
	 */
	public void setEbrASStateNum(Integer ebrASStateNum) {
		this.ebrASStateNum = ebrASStateNum;
	}
	
	/**
	 * 播出系统状态数目
	 * 
	 * @param ebrBSStateNum
	 */
	public void setEbrBSStateNum(Integer ebrBSStateNum) {
		this.ebrBSStateNum = ebrBSStateNum;
	}
	
	/**
	 * 平台设备及终端状态数目
	 * 
	 * @param ebrDTStateNum
	 */
	public void setEbrDTStateNum(Integer ebrDTStateNum) {
		this.ebrDTStateNum = ebrDTStateNum;
	}
	
	/**
	 * 1-日，2-周，3-月，4-季度，5-半年，6-年度
	 * 
	 * @param statsicType
	 */
	public void setStatsicType(Integer statsicType) {
		this.statsicType = statsicType;
	}
	
	/**
	 * x年x月x日，x年x周，x年x月，x年上半年/下半年，x年
	 * 
	 * @param statsicDuation
	 */
	public void setStatsicDuation(String statsicDuation) {
		this.statsicDuation = statsicDuation;
	}
	

}