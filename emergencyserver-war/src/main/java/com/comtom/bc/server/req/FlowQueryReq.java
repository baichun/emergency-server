package com.comtom.bc.server.req;

/**
 * 创建节目请求参数对象
 * 
 * @author zhucanhui
 *
 */
public class FlowQueryReq extends BaseReq {

	/**
	 * 节目Id
	 */
	private Long programId;

	/**
	 * 数据包Id
	 */
	private String ebdId;

	/**
	 * @return the programId
	 */
	public Long getProgramId() {
		return programId;
	}

	/**
	 * @param programId
	 *            the programId to set
	 */
	public void setProgramId(Long programId) {
		this.programId = programId;
	}

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

}
