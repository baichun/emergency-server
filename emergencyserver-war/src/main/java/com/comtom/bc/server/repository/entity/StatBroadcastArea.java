package com.comtom.bc.server.repository.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <b>bc_stat_broadcast_area[bc_stat_broadcast_area]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author Kidsoul Qu
 * @date 2017-01-15 17:14:46
 */
@Entity
@Table(name = "bc_stat_broadcast_area")
public class StatBroadcastArea {

	/**
	 * 广播数据分区域统计ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer statBdAreaId;
	
	/**
	 * 广播数据统计ID
	 */
	private Integer statsicId;
	
	/**
	 * 管辖区域代码
	 */
	private String areaCode;
	
	/**
	 * 管辖区域名称
	 */
	private String areaName;
	
	/**
	 * 广播总次数
	 */
	private Integer totalNum;
	
	/**
	 * 日常广播次数
	 */
	private Integer commonNum;
	
	/**
	 * Ⅰ级事件应急广播
	 */
	private Integer redNum;
	
	/**
	 * Ⅱ级事件应急广播
	 */
	private Integer orangeNum;
	
	/**
	 * Ⅲ级事件应急广播
	 */
	private Integer yellowNum;
	
	/**
	 * Ⅳ级事件应急广播
	 */
	private Integer blueNum;
	

	/**
	 * 广播数据分区域统计ID
	 * 
	 * @return statBdAreaId
	 */
	public Integer getStatBdAreaId() {
		return statBdAreaId;
	}
	
	/**
	 * 广播数据统计ID
	 * 
	 * @return statsicId
	 */
	public Integer getStatsicId() {
		return statsicId;
	}
	
	/**
	 * 管辖区域代码
	 * 
	 * @return areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}
	
	/**
	 * 管辖区域名称
	 * 
	 * @return areaName
	 */
	public String getAreaName() {
		return areaName;
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
	 * 日常广播次数
	 * 
	 * @return commonNum
	 */
	public Integer getCommonNum() {
		return commonNum;
	}
	
	/**
	 * Ⅰ级事件应急广播
	 * 
	 * @return redNum
	 */
	public Integer getRedNum() {
		return redNum;
	}
	
	/**
	 * Ⅱ级事件应急广播
	 * 
	 * @return orangeNum
	 */
	public Integer getOrangeNum() {
		return orangeNum;
	}
	
	/**
	 * Ⅲ级事件应急广播
	 * 
	 * @return yellowNum
	 */
	public Integer getYellowNum() {
		return yellowNum;
	}
	
	/**
	 * Ⅳ级事件应急广播
	 * 
	 * @return blueNum
	 */
	public Integer getBlueNum() {
		return blueNum;
	}
	

	/**
	 * 广播数据分区域统计ID
	 * 
	 * @param statBdAreaId
	 */
	public void setStatBdAreaId(Integer statBdAreaId) {
		this.statBdAreaId = statBdAreaId;
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
	 * 管辖区域代码
	 * 
	 * @param areaCode
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
	/**
	 * 管辖区域名称
	 * 
	 * @param areaName
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
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
	 * 日常广播次数
	 * 
	 * @param commonNum
	 */
	public void setCommonNum(Integer commonNum) {
		this.commonNum = commonNum;
	}
	
	/**
	 * Ⅰ级事件应急广播
	 * 
	 * @param redNum
	 */
	public void setRedNum(Integer redNum) {
		this.redNum = redNum;
	}
	
	/**
	 * Ⅱ级事件应急广播
	 * 
	 * @param orangeNum
	 */
	public void setOrangeNum(Integer orangeNum) {
		this.orangeNum = orangeNum;
	}
	
	/**
	 * Ⅲ级事件应急广播
	 * 
	 * @param yellowNum
	 */
	public void setYellowNum(Integer yellowNum) {
		this.yellowNum = yellowNum;
	}
	
	/**
	 * Ⅳ级事件应急广播
	 * 
	 * @param blueNum
	 */
	public void setBlueNum(Integer blueNum) {
		this.blueNum = blueNum;
	}
	

}