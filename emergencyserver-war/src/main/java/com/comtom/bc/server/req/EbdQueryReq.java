package com.comtom.bc.server.req;

/**
 * 创建节目请求参数对象
 * 
 * @author zhucanhui
 *
 */
public class EbdQueryReq extends BaseReq {

	/**
	 * 数据包Id
	 */
	private String ebdId;

	/**
	 * 数据包类型
	 */
	private String ebdType;

	/**
	 * @return the ebdId
	 */
	public String getEbdId() {
		return ebdId;
	}

	/**
	 * @param ebdId
	 *            the ebdId to set
	 */
	public void setEbdId(String ebdId) {
		this.ebdId = ebdId;
	}

	/**
	 * @return the ebdType
	 */
	public String getEbdType() {
		return ebdType;
	}

	/**
	 * @param ebdType
	 *            the ebdType to set
	 */
	public void setEbdType(String ebdType) {
		this.ebdType = ebdType;
	}
}
