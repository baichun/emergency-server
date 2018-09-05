package com.comtom.bc.server.repository.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <b>bc_program_time[bc_program_time]数据持久化对象</b>
 * 
 * @author zhucanhui
 * @date 2016-12-17 17:08:43
 */
@Entity
@Table(name = "bc_program_time")
public class ProgramTime {

	/**
	 * 时间段Id,自增主键定义为Long
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long timeId;

	/**
	 * 开始时间(HH:MI)
	 */
	private String startTime;

	/**
	 * 结束时间(HH:MI)
	 */
	private String overTime;

	/**
	 * 持续时间
	 */
	private Integer durationTime;

	/**
	 * 关联节目策略Id
	 */
	private Long strategyId;

	/**
	 * 处理标识(1:未处理 2:已处理)
	 */
	private Integer handleFlag;

	/**
	 * 时间段Id
	 * 
	 * @return timeId
	 */
	public Long getTimeId() {
		return timeId;
	}

	/**
	 * 开始时间(HH:MI)
	 * 
	 * @return startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * 结束时间(HH:MI)
	 * 
	 * @return overTime
	 */
	public String getOverTime() {
		return overTime;
	}

	/**
	 * 持续时间
	 * 
	 * @return durationTime
	 */
	public Integer getDurationTime() {
		return durationTime;
	}

	/**
	 * 关联节目策略Id
	 * 
	 * @return strategyId
	 */
	public Long getStrategyId() {
		return strategyId;
	}

	/**
	 * 处理标识(1:未处理 2:已处理)
	 * 
	 * @return handleFlag
	 */
	public Integer getHandleFlag() {
		return handleFlag;
	}

	/**
	 * 时间段Id
	 * 
	 * @param timeId
	 */
	public void setTimeId(Long timeId) {
		this.timeId = timeId;
	}

	/**
	 * 开始时间(HH:MI)
	 * 
	 * @param startTime
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * 结束时间(HH:MI)
	 * 
	 * @param overTime
	 */
	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}

	/**
	 * 持续时间
	 * 
	 * @param durationTime
	 */
	public void setDurationTime(Integer durationTime) {
		this.durationTime = durationTime;
	}

	/**
	 * 关联节目策略Id
	 * 
	 * @param strategyId
	 */
	public void setStrategyId(Long strategyId) {
		this.strategyId = strategyId;
	}

	/**
	 * 处理标识(1:未处理 2:已处理)
	 * 
	 * @param handleFlag
	 */
	public void setHandleFlag(Integer handleFlag) {
		this.handleFlag = handleFlag;
	}

}