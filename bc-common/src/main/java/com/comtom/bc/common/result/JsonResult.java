package com.comtom.bc.common.result;

import java.util.Map;

/**
 * 返回Json数据结果对象
 * 
 * @author zhucanhui
 *
 */
public class JsonResult<K, V> {

	/**
	 * 返回结果码
	 */
	private int resultCode;

	/**
	 * 返回结果描述
	 */
	private String resultMsg;

	/**
	 * jsessionid
	 */
	private String jsessionid;

	/**
	 * 总记录数
	 */
	private long totalCount;

	/**
	 * 当前页数
	 */
	private Integer currPage;

	/**
	 * 总页数
	 */
	private Integer totalPage;

	/**
	 * 返回关联的对象
	 */
	private Map<K, V> dataMap;

	/**
	 * @return the resultCode
	 */
	public int getResultCode() {
		return resultCode;
	}

	/**
	 * @param resultCode
	 *            the resultCode to set
	 */
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	/**
	 * @return the resultMsg
	 */
	public String getResultMsg() {
		return resultMsg;
	}

	/**
	 * @param resultMsg
	 *            the resultMsg to set
	 */
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	/**
	 * @return the jsessionid
	 */
	public String getJsessionid() {
		return jsessionid;
	}

	/**
	 * @param jsessionid
	 *            the jsessionid to set
	 */
	public void setJsessionid(String jsessionid) {
		this.jsessionid = jsessionid;
	}

	/**
	 * @return the dataMap
	 */
	public Map<K, V> getDataMap() {
		return dataMap;
	}

	/**
	 * @param dataMap
	 *            the dataMap to set
	 */
	public void setDataMap(Map<K, V> dataMap) {
		this.dataMap = dataMap;
	}

	/**
	 * @return the totalCount
	 */
	public long getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getCurrPage() {
		return currPage;
	}

	public void setCurrPage(Integer currPage) {
		this.currPage = currPage;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
}
