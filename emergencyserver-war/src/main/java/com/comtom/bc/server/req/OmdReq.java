package com.comtom.bc.server.req;

/**
 * OMDRequest运维数据请求参数对象
 * 
 * @author zhucanhui
 *
 */
public class OmdReq {

	private String ebrId;

	private String omdType;

	/**
	 * @return the ebrId
	 */
	public String getEbrId() {
		return ebrId;
	}

	/**
	 * @param ebrId
	 *            the ebrId to set
	 */
	public void setEbrId(String ebrId) {
		this.ebrId = ebrId;
	}

	/**
	 * @return the omdType
	 */
	public String getOmdType() {
		return omdType;
	}

	/**
	 * @param omdType
	 *            the omdType to set
	 */
	public void setOmdType(String omdType) {
		this.omdType = omdType;
	}
}
