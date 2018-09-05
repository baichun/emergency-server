package com.comtom.bc.server.repository.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * <b>v_bc_ebm_dispatch_info数据视图对象</b>
 * 
 * @author zhucanhui
 */
@Entity
@Table(name = "v_bc_ebm_dispatch_info")
@NamedQuery(name = "VEbmDispatch.findAll", query = "SELECT t FROM VEbmDispatch t")
public class VEbmDispatch {

	/**
	 * 调度分发ID
	 */
	@Id
	@Column(name = "dispatchId")
	private Integer dispatchId;

	/**
	 * 消息Id
	 */
	private String ebmId;

	/**
	 * 发布机构代码
	 */
	private String senderCode;

	/**
	 * 发布机构名称
	 */
	private String sendName;

	/**
	 * 事件级别
	 */
	private Integer severity;

	/**
	 * 消息标题
	 */
	private String msgTitle;

	/**
	 * 消息内容
	 */
	private String msgDesc;

	/**
	 * 平台名称
	 */
	private String psEbrName;

	/**
	 * 播出系统名称
	 */
	private String bsName;

	/**
	 * 发布时间
	 */
	private Date createTime;

	/**
	 * 平台覆盖区域
	 */
	private String psAreaCode;

	/**
	 * 播出系统覆盖区域
	 */
	private String bsAreaCode;

	/**
	 * 区域名称
	 */
	@Transient
	private String areaName;

	/**
	 * 文件列表
	 */
	@Transient
	private List<FileInfo> fileList;

	/**
	 * @return the dispatchId
	 */
	public Integer getDispatchId() {
		return dispatchId;
	}

	/**
	 * @param dispatchId
	 *            the dispatchId to set
	 */
	public void setDispatchId(Integer dispatchId) {
		this.dispatchId = dispatchId;
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
	 * @return the senderCode
	 */
	public String getSenderCode() {
		return senderCode;
	}

	/**
	 * @param senderCode
	 *            the senderCode to set
	 */
	public void setSenderCode(String senderCode) {
		this.senderCode = senderCode;
	}

	/**
	 * @return the sendName
	 */
	public String getSendName() {
		return sendName;
	}

	/**
	 * @param sendName
	 *            the sendName to set
	 */
	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	/**
	 * @return the severity
	 */
	public Integer getSeverity() {
		return severity;
	}

	/**
	 * @param severity
	 *            the severity to set
	 */
	public void setSeverity(Integer severity) {
		this.severity = severity;
	}

	/**
	 * @return the msgTitle
	 */
	public String getMsgTitle() {
		return msgTitle;
	}

	/**
	 * @param msgTitle
	 *            the msgTitle to set
	 */
	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}

	/**
	 * @return the msgDesc
	 */
	public String getMsgDesc() {
		return msgDesc;
	}

	/**
	 * @param msgDesc
	 *            the msgDesc to set
	 */
	public void setMsgDesc(String msgDesc) {
		this.msgDesc = msgDesc;
	}

	/**
	 * @return the psEbrName
	 */
	public String getPsEbrName() {
		return psEbrName;
	}

	/**
	 * @param psEbrName
	 *            the psEbrName to set
	 */
	public void setPsEbrName(String psEbrName) {
		this.psEbrName = psEbrName;
	}

	/**
	 * @return the bsName
	 */
	public String getBsName() {
		return bsName;
	}

	/**
	 * @param bsName
	 *            the bsName to set
	 */
	public void setBsName(String bsName) {
		this.bsName = bsName;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the areaName
	 */
	public String getAreaName() {
		return areaName;
	}

	/**
	 * @param areaName
	 *            the areaName to set
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	/**
	 * @return the fileList
	 */
	public List<FileInfo> getFileList() {
		return fileList;
	}

	/**
	 * @param fileList
	 *            the fileList to set
	 */
	public void setFileList(List<FileInfo> fileList) {
		this.fileList = fileList;
	}

	/**
	 * @return the psAreaCode
	 */
	public String getPsAreaCode() {
		return psAreaCode;
	}

	/**
	 * @param psAreaCode
	 *            the psAreaCode to set
	 */
	public void setPsAreaCode(String psAreaCode) {
		this.psAreaCode = psAreaCode;
	}

	/**
	 * @return the bsAreaCode
	 */
	public String getBsAreaCode() {
		return bsAreaCode;
	}

	/**
	 * @param bsAreaCode
	 *            the bsAreaCode to set
	 */
	public void setBsAreaCode(String bsAreaCode) {
		this.bsAreaCode = bsAreaCode;
	}

}