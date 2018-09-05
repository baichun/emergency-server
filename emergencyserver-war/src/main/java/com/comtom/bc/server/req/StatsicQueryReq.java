package com.comtom.bc.server.req;

import java.util.Date;


/**
 * 统计数据查询条件
 * 
 * @author kidsoul
 *
 */
public class StatsicQueryReq extends BaseReq {
	/**
	 * 统计类型， 3-月，4-季度，5-半年，6-年度
	 */
	private Integer statsicType;
	
	/**
	 * 统计查询结束时间
	 */
	private Date cutoffTime;

	public Integer getStatsicType() {
		return statsicType;
	}

	public void setStatsicType(Integer statsicType) {
		this.statsicType = statsicType;
	}

	public Date getCutoffTime() {
		return cutoffTime;
	}

	public void setCutoffTime(Date cutoffTime) {
		this.cutoffTime = cutoffTime;
	}
}
