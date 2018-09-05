package com.comtom.bc.server.req;


/**
 * 统计数据查询条件
 * 
 * @author kidsoul
 *
 */
public class StatsicListReq extends BaseReq {
	/**
	 * 统计类型， 1-日，2-周，3-月，4-季度，5-半年，6-年度
	 */
	private Integer statsicType;

	public Integer getStatsicType() {
		return statsicType;
	}

	public void setStatsicType(Integer statsicType) {
		this.statsicType = statsicType;
	}
}
