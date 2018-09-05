package com.comtom.bc.server.req;

/**
 * 数据包下载请求参数对象
 * 
 * @author zhucanhui
 *
 */
public class EbdDownloadReq extends BaseReq {

	/**
	 * 数据包Id
	 */
	private String ebdId;

	/**
	 * 数据包Id
	 */
	private String ebmId;

	/**
	 * 节目Id
	 */
	private Integer programId;

	/**
	 * 方案Id
	 */
	private Integer schemeId;

	/**
	 * 发送标识(1:接收 2:发送)
	 */
	private Integer sendFlag;

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
	 * @return the ebmId
	 */
	public String getEbmId() {
		return ebmId;
	}

	/**
	 * @param ebmId
	 *            the ebmId to set
	 */
	public void setEbmId(String ebmId) {
		this.ebmId = ebmId;
	}

	/**
	 * @return the programId
	 */
	public Integer getProgramId() {
		return programId;
	}

	/**
	 * @param programId
	 *            the programId to set
	 */
	public void setProgramId(Integer programId) {
		this.programId = programId;
	}

	/**
	 * @return the schemeId
	 */
	public Integer getSchemeId() {
		return schemeId;
	}

	/**
	 * @param schemeId
	 *            the schemeId to set
	 */
	public void setSchemeId(Integer schemeId) {
		this.schemeId = schemeId;
	}

	/**
	 * @return the sendFlag
	 */
	public Integer getSendFlag() {
		return sendFlag;
	}

	/**
	 * @param sendFlag
	 *            the sendFlag to set
	 */
	public void setSendFlag(Integer sendFlag) {
		this.sendFlag = sendFlag;
	}

}
