package com.comtom.bc.server.repository.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * <b>bc_stat_broadcast[bc_stat_broadcast]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author Kidsoul Qu
 * @date 2017-01-15 17:14:46
 */
@Entity
@Table(name = "bc_stat_broadcast")
public class StatBroadcast {

	/**
	 * 广播数据统计ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer statsicId;
	
	/**
	 * 3-月，4-季度，5-半年，6-年度
	 */
	private Integer statsicType;
	
	/**
	 * x年x月，x年上半年/下半年，x年
	 */
	private String statsicDuation;
	
	/**
	 * 广播总次数
	 */
	private Integer totalNum;
	
	/**
	 * 本系统内发起（制播系统）次数
	 */
	private Integer selfMadeNum;
	
	/**
	 * 上级应急广播平台下发次数
	 */
	private Integer parentPushNum;
	
	/**
	 * 其他预警部门发起次数
	 */
	private Integer otherAlertNum;
	
	/**
	 * 下级平台申请发起
	 */
	private Integer childApplyNum;
	
	/**
	 * 统计时间戳
	 */
	private Date statsicTime;
	
	/**
	 * 广播次数按事件类别总体统计
	 */
	@Transient
	private StatBroadcastArea severityOverall;
	
	/**
	 * 广播次数按区域统计
	 */
	@Transient
	private List<StatBroadcastArea> areaBrdStatsics;

	/**
	 * 广播数据统计ID
	 * 
	 * @return statsicId
	 */
	public Integer getStatsicId() {
		return statsicId;
	}
	
	/**
	 * 3-月，4-季度，5-半年，6-年度
	 * 
	 * @return statsicType
	 */
	public Integer getStatsicType() {
		return statsicType;
	}
	
	/**
	 * x年x月，x年上半年/下半年，x年
	 * 
	 * @return statsicDuation
	 */
	public String getStatsicDuation() {
		return statsicDuation;
	}
	
	/**
	 * 广播总次数
	 * 
	 * @return totalNum
	 */
	public Integer getTotalNum() {
		return totalNum;
	}
	
	/**
	 * 本系统内发起（制播系统）次数
	 * 
	 * @return selfMadeNum
	 */
	public Integer getSelfMadeNum() {
		return selfMadeNum;
	}
	
	/**
	 * 上级应急广播平台下发次数
	 * 
	 * @return parentPushNum
	 */
	public Integer getParentPushNum() {
		return parentPushNum;
	}
	
	/**
	 * 其他预警部门发起次数
	 * 
	 * @return otherAlertNum
	 */
	public Integer getOtherAlertNum() {
		return otherAlertNum;
	}
	
	/**
	 * 下级平台申请发起
	 * 
	 * @return childApplyNum
	 */
	public Integer getChildApplyNum() {
		return childApplyNum;
	}
	

	/**
	 * 广播数据统计ID
	 * 
	 * @param statsicId
	 */
	public void setStatsicId(Integer statsicId) {
		this.statsicId = statsicId;
	}
	
	/**
	 * 3-月，4-季度，5-半年，6-年度
	 * 
	 * @param statsicType
	 */
	public void setStatsicType(Integer statsicType) {
		this.statsicType = statsicType;
	}
	
	/**
	 * x年x月，x年上半年/下半年，x年
	 * 
	 * @param statsicDuation
	 */
	public void setStatsicDuation(String statsicDuation) {
		this.statsicDuation = statsicDuation;
	}
	
	/**
	 * 广播总次数
	 * 
	 * @param totalNum
	 */
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	
	/**
	 * 本系统内发起（制播系统）次数
	 * 
	 * @param selfMadeNum
	 */
	public void setSelfMadeNum(Integer selfMadeNum) {
		this.selfMadeNum = selfMadeNum;
	}
	
	/**
	 * 上级应急广播平台下发次数
	 * 
	 * @param parentPushNum
	 */
	public void setParentPushNum(Integer parentPushNum) {
		this.parentPushNum = parentPushNum;
	}
	
	/**
	 * 其他预警部门发起次数
	 * 
	 * @param otherAlertNum
	 */
	public void setOtherAlertNum(Integer otherAlertNum) {
		this.otherAlertNum = otherAlertNum;
	}
	
	/**
	 * 下级平台申请发起
	 * 
	 * @param childApplyNum
	 */
	public void setChildApplyNum(Integer childApplyNum) {
		this.childApplyNum = childApplyNum;
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
	 * 统计时间戳
	 * 
	 * @param statsicTime
	 */
	public void setStatsicTime(Date statsicTime) {
		this.statsicTime = statsicTime;
	}

	public List<StatBroadcastArea> getAreaBrdStatsics() {
		return areaBrdStatsics;
	}

	public void setAreaBrdStatsics(List<StatBroadcastArea> areaBrdStatsics) {
		this.areaBrdStatsics = areaBrdStatsics;
	}

	public StatBroadcastArea getSeverityOverall() {
		return severityOverall;
	}

	public void setSeverityOverall(StatBroadcastArea severityOverall) {
		this.severityOverall = severityOverall;
	}
}