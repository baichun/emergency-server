package com.comtom.bc.server.repository.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <b>bc_stat_ebm[bc_stat_ebm]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author Kidsoul Qu
 * @date 2017-01-08 20:00:32
 */
@Entity
@Table(name="bc_stat_ebm")
public class StatEbm {
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
	 * 应急消息总数
	 */
	private Integer totalNum;
	
	/**
	 * 播发成功数目
	 */
	private Integer succeededNum;
	
	/**
	 * 播发失败数目
	 */
	private Integer failedNum;
	
	/**
	 * 未知状态数目
	 */
	private Integer unstatedNum;
	
	/**
	 * 未处理数目
	 */
	private Integer noprogressNum;
	
	/**
	 * 等待播发数目
	 */
	private Integer timetogoNum;
	
	/**
	 * 正在播发数目
	 */
	private Integer inprogressNum;
	
	/**
	 * 播发取消数目
	 */
	private Integer cancelledNum;
	
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
	 * 应急消息总数
	 * 
	 * @return totalNum
	 */
	public Integer getTotalNum() {
		return totalNum;
	}
	
	/**
	 * 播发成功数目
	 * 
	 * @return succeededNum
	 */
	public Integer getSucceededNum() {
		return succeededNum;
	}
	
	/**
	 * 播发失败数目
	 * 
	 * @return failedNum
	 */
	public Integer getFailedNum() {
		return failedNum;
	}
	
	/**
	 * 未知状态数目
	 * 
	 * @return unstatedNum
	 */
	public Integer getUnstatedNum() {
		return unstatedNum;
	}
	
	/**
	 * 未处理数目
	 * 
	 * @return noprogressNum
	 */
	public Integer getNoprogressNum() {
		return noprogressNum;
	}
	
	/**
	 * 等待播发数目
	 * 
	 * @return timetogoNum
	 */
	public Integer getTimetogoNum() {
		return timetogoNum;
	}
	
	/**
	 * 正在播发数目
	 * 
	 * @return inprogressNum
	 */
	public Integer getInprogressNum() {
		return inprogressNum;
	}
	
	/**
	 * 播发取消数目
	 * 
	 * @return cancelledNum
	 */
	public Integer getCancelledNum() {
		return cancelledNum;
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
	 * 应急消息总数
	 * 
	 * @param totalNum
	 */
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	
	/**
	 * 播发成功数目
	 * 
	 * @param succeededNum
	 */
	public void setSucceededNum(Integer succeededNum) {
		this.succeededNum = succeededNum;
	}
	
	/**
	 * 播发失败数目
	 * 
	 * @param failedNum
	 */
	public void setFailedNum(Integer failedNum) {
		this.failedNum = failedNum;
	}
	
	/**
	 * 未知状态数目
	 * 
	 * @param unstatedNum
	 */
	public void setUnstatedNum(Integer unstatedNum) {
		this.unstatedNum = unstatedNum;
	}
	
	/**
	 * 未处理数目
	 * 
	 * @param noprogressNum
	 */
	public void setNoprogressNum(Integer noprogressNum) {
		this.noprogressNum = noprogressNum;
	}
	
	/**
	 * 等待播发数目
	 * 
	 * @param timetogoNum
	 */
	public void setTimetogoNum(Integer timetogoNum) {
		this.timetogoNum = timetogoNum;
	}
	
	/**
	 * 正在播发数目
	 * 
	 * @param inprogressNum
	 */
	public void setInprogressNum(Integer inprogressNum) {
		this.inprogressNum = inprogressNum;
	}
	
	/**
	 * 播发取消数目
	 * 
	 * @param cancelledNum
	 */
	public void setCancelledNum(Integer cancelledNum) {
		this.cancelledNum = cancelledNum;
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