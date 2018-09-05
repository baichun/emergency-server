package com.comtom.bc.server.req;

import com.comtom.bc.common.Constants;

/**
 * 参数对象基类
 * 
 * @author zhucanhui
 *
 */
public class BaseReq {

	/**
	 * 客户端类型
	 */
	protected Integer portalType;

	/**
	 * 用户帐号
	 */
	private String account;

	/**
	 * 当前页数
	 */
	protected Integer pageNum = Constants.DEFAULT_PAGE_NUM;

	/**
	 * 每页显示记录数
	 */
	protected Integer pageSize = Constants.DEFAULT_LIMIT;

	/**
	 * @return the portalType
	 */
	public Integer getPortalType() {
		return portalType;
	}

	/**
	 * @param portalType
	 *            the portalType to set
	 */
	public void setPortalType(Integer portalType) {
		this.portalType = portalType;
	}

	/**
	 * @return the pageNum
	 */
	public Integer getPageNum() {
		return pageNum;
	}

	/**
	 * @param pageNum
	 *            the pageNum to set
	 */
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	/**
	 * @return the pageSize
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}

}
