package com.comtom.bc.server.repository.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <b>bc_worktime[bc_worktime]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author Kidsoul Qu
 * @date 2016-12-06 16:57:08
 */
@Entity
@Table(name = "bc_worktime")
public class Worktime {
	/**
	 * id
	 */
	@Id
	private String id;
	
	/**
	 * 1-星期一，2-星期二，3-星期三，4-星期四， 5-星期五， 6-星期六， 7-星期日
	 */
	private Integer weekDay;
	
	/**
	 * 开始时间，固定格式HH:mm:ss
	 */
	private String startTime;
	
	/**
	 * 结束时间，固定格式HH:mm:ss
	 */
	private String stopTime;
	
	/**
	 * 描述
	 */
	private String remark;
	

	/**
	 * id
	 * 
	 * @return id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * 1-星期一，2-星期二，3-星期三，4-星期四， 5-星期五， 6-星期六， 7-星期日
	 * 
	 * @return weekDay
	 */
	public Integer getWeekDay() {
		return weekDay;
	}
	
	/**
	 * 开始时间，固定格式HH:mm:ss
	 * 
	 * @return startTime
	 */
	public String getStartTime() {
		return startTime;
	}
	
	/**
	 * 结束时间，固定格式HH:mm:ss
	 * 
	 * @return stopTime
	 */
	public String getStopTime() {
		return stopTime;
	}
	
	/**
	 * 描述
	 * 
	 * @return remark
	 */
	public String getRemark() {
		return remark;
	}
	

	/**
	 * id
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 1-星期一，2-星期二，3-星期三，4-星期四， 5-星期五， 6-星期六， 7-星期日
	 * 
	 * @param weekDay
	 */
	public void setWeekDay(Integer weekDay) {
		this.weekDay = weekDay;
	}
	
	/**
	 * 开始时间，固定格式HH:mm:ss
	 * 
	 * @param startTime
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	/**
	 * 结束时间，固定格式HH:mm:ss
	 * 
	 * @param stopTime
	 */
	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}
	
	/**
	 * 描述
	 * 
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	

}