package com.comtom.bc.server.repository.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * <b>bc_program_strategy[bc_program_strategy]数据持久化对象</b>
 * 
 * @author zhucanhui
 * @date 2016-12-17 17:08:43
 */
@Entity
@Table(name = "bc_program_strategy")
public class ProgramStrategy {

	/**
	 * strategyId
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long strategyId;

	/**
	 * 策略类型 (1:一次性 2:每天 3:每周)
	 */
	private Integer strategyType;

	/**
	 * 播发日期
	 */
	private Date playTime;

	/**
	 * 有效开始日期
	 */
	@Column(name = "vStartTime")
	private Date startTime;

	/**
	 * 有效结束日期
	 */
	@Column(name = "vOverTime")
	private Date overTime;

	/**
	 * 周掩码(位运算)
	 */
	private Integer weekMask;

	/**
	 * 关联节目Id
	 */
	private Long programId;

	/**
	 * 节目时间段
	 */
	@Transient
	private List<ProgramTime> timeList;

	/**
	 * strategyId
	 * 
	 * @return strategyId
	 */
	public Long getStrategyId() {
		return strategyId;
	}

	/**
	 * 策略类型 (1:一次性 2:每天 3:每周)
	 * 
	 * @return strategyType
	 */
	public Integer getStrategyType() {
		return strategyType;
	}

	/**
	 * 播发日期
	 * 
	 * @return playTime
	 */
	public Date getPlayTime() {
		return playTime;
	}

	/**
	 * 周掩码(位运算)
	 * 
	 * @return weekMask
	 */
	public Integer getWeekMask() {
		return weekMask;
	}

	/**
	 * 关联节目Id
	 * 
	 * @return programId
	 */
	public Long getProgramId() {
		return programId;
	}

	/**
	 * strategyId
	 * 
	 * @param strategyId
	 */
	public void setStrategyId(Long strategyId) {
		this.strategyId = strategyId;
	}

	/**
	 * 策略类型 (1:一次性 2:每天 3:每周)
	 * 
	 * @param strategyType
	 */
	public void setStrategyType(Integer strategyType) {
		this.strategyType = strategyType;
	}

	/**
	 * 播发日期
	 * 
	 * @param playTime
	 */
	public void setPlayTime(Date playTime) {
		this.playTime = playTime;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the overTime
	 */
	public Date getOverTime() {
		return overTime;
	}

	/**
	 * @param overTime
	 *            the overTime to set
	 */
	public void setOverTime(Date overTime) {
		this.overTime = overTime;
	}

	/**
	 * 周掩码(位运算)
	 * 
	 * @param weekMask
	 */
	public void setWeekMask(Integer weekMask) {
		this.weekMask = weekMask;
	}

	/**
	 * 关联节目Id
	 * 
	 * @param programId
	 */
	public void setProgramId(Long programId) {
		this.programId = programId;
	}

	/**
	 * @return the timeList
	 */
	public List<ProgramTime> getTimeList() {
		return timeList;
	}

	/**
	 * @param timeList
	 *            the timeList to set
	 */
	public void setTimeList(List<ProgramTime> timeList) {
		this.timeList = timeList;
	}

}