package com.comtom.bc.exchange.model.ebd.ebm;

import java.util.List;

/**
 * @author nobody
 * 调用资源播出信息
 */
public class ResBrdInfo {
	
	/**
	 * 调用资源播出条目集合一个或者多个
	 */
	private List<ResBrdItem> dataList;

	/**
	 * @return the 调用资源播出条目集合
	 */
	public List<ResBrdItem> getDataList() {
		return dataList;
	}

	/**
	 * @param 调用资源播出条目集合 the dataList to set
	 */
	public void setDataList(List<ResBrdItem> dataList) {
		this.dataList = dataList;
	}
	
}
