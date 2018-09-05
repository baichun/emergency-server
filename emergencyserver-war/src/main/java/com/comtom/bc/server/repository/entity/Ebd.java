package com.comtom.bc.server.repository.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * <b>bc_ebd[bc_ebd]数据持久化对象</b>
 * 
 * @author zhucanhui
 * @date 2016-12-20 17:03:37
 */
@Entity
@Table(name = "bc_ebd")
public class Ebd {

	/**
	 * 业务数据包Id
	 */
	@Id
	private String ebdId;

	/**
	 * 数据包版本
	 */
	private String ebdVersion = "1.0";

	/**
	 * 业务数据类型
	 */
	private String ebdType;

	/**
	 * 数据包名称
	 */
	private String ebdName;

	/**
	 * 来源对象资源Id
	 */
	private String ebdSrcEbrId;

	/**
	 * 目标对象资源Id
	 */
	private String ebdDestEbrId;

	/**
	 * 生成时间
	 */
	private Date ebdTime;

	/**
	 * 关联业务数据包Id
	 */
	private String relateEbdId;

	/**
	 * 业务数据包来源对象URL
	 */
	private String ebdSrcUrl;

	/**
	 * 业务数据包标识（1：接收 2：发送）
	 */
	private Integer sendFlag;

	/**
	 * 业务数据包状态
	 */
	private Integer ebdState;

	/**
	 * 业务数据包接收时间
	 */
	private Date ebdRecvTime;

	/**
	 * 业务数据包发送时间
	 */
	private Date ebdSendTime;

	/**
	 * 关联会话Id
	 */
	private Long flowId;

	/**
	 * EBM消息Id
	 */
	private String ebmId;

	/**
	 * 关联EBM
	 */
	@Transient
	private Ebm ebm;

	/**
	 * 关联EBD文件列表
	 */
	@Transient
	private List<EbdFile> ebdFileList;

	/**
	 * 业务数据包Id
	 * 
	 * @return ebdId
	 */
	public String getEbdId() {
		return ebdId;
	}

	/**
	 * 数据包版本
	 * 
	 * @return ebdVersion
	 */
	public String getEbdVersion() {
		return ebdVersion;
	}

	/**
	 * 业务数据类型
	 * 
	 * @return ebdType
	 */
	public String getEbdType() {
		return ebdType;
	}

	/**
	 * 来源对象资源Id
	 * 
	 * @return ebdSrcEbrId
	 */
	public String getEbdSrcEbrId() {
		return ebdSrcEbrId;
	}

	/**
	 * 目标对象资源Id
	 * 
	 * @return ebdDestEbrId
	 */
	public String getEbdDestEbrId() {
		return ebdDestEbrId;
	}

	/**
	 * 生成时间
	 * 
	 * @return ebdTime
	 */
	public Date getEbdTime() {
		return ebdTime;
	}

	/**
	 * 关联业务数据包Id
	 * 
	 * @return relateEbdId
	 */
	public String getRelateEbdId() {
		return relateEbdId;
	}

	/**
	 * 业务数据包来源对象URL
	 * 
	 * @return ebdSrcUrl
	 */
	public String getEbdSrcUrl() {
		return ebdSrcUrl;
	}

	/**
	 * 业务数据包标识（1：接收 2：发送）
	 * 
	 * @return sendFlag
	 */
	public Integer getSendFlag() {
		return sendFlag;
	}

	/**
	 * 业务数据包状态
	 * 
	 * @return ebdState
	 */
	public Integer getEbdState() {
		return ebdState;
	}

	/**
	 * 业务数据包接收时间
	 * 
	 * @return ebdRecvTime
	 */
	public Date getEbdRecvTime() {
		return ebdRecvTime;
	}

	/**
	 * 业务数据包发送时间
	 * 
	 * @return ebdSendTime
	 */
	public Date getEbdSendTime() {
		return ebdSendTime;
	}

	/**
	 * 关联会话Id
	 * 
	 * @return flowId
	 */
	public Long getFlowId() {
		return flowId;
	}

	/**
	 * 业务数据包Id
	 * 
	 * @param ebdId
	 */
	public void setEbdId(String ebdId) {
		this.ebdId = ebdId;
	}

	/**
	 * 数据包版本
	 * 
	 * @param ebdVersion
	 */
	public void setEbdVersion(String ebdVersion) {
		this.ebdVersion = ebdVersion;
	}

	/**
	 * 业务数据类型
	 * 
	 * @param ebdType
	 */
	public void setEbdType(String ebdType) {
		this.ebdType = ebdType;
	}

	/**
	 * 来源对象资源Id
	 * 
	 * @param ebdSrcEbrId
	 */
	public void setEbdSrcEbrId(String ebdSrcEbrId) {
		this.ebdSrcEbrId = ebdSrcEbrId;
	}

	/**
	 * 目标对象资源Id
	 * 
	 * @param ebdDestEbrId
	 */
	public void setEbdDestEbrId(String ebdDestEbrId) {
		this.ebdDestEbrId = ebdDestEbrId;
	}

	/**
	 * 生成时间
	 * 
	 * @param ebdTime
	 */
	public void setEbdTime(Date ebdTime) {
		this.ebdTime = ebdTime;
	}

	/**
	 * 关联业务数据包Id
	 * 
	 * @param relateEbdId
	 */
	public void setRelateEbdId(String relateEbdId) {
		this.relateEbdId = relateEbdId;
	}

	/**
	 * 业务数据包来源对象URL
	 * 
	 * @param ebdSrcUrl
	 */
	public void setEbdSrcUrl(String ebdSrcUrl) {
		this.ebdSrcUrl = ebdSrcUrl;
	}

	/**
	 * 业务数据包标识（1：接收 2：发送）
	 * 
	 * @param sendFlag
	 */
	public void setSendFlag(Integer sendFlag) {
		this.sendFlag = sendFlag;
	}

	/**
	 * 业务数据包状态
	 * 
	 * @param ebdState
	 */
	public void setEbdState(Integer ebdState) {
		this.ebdState = ebdState;
	}

	/**
	 * 业务数据包接收时间
	 * 
	 * @param ebdRecvTime
	 */
	public void setEbdRecvTime(Date ebdRecvTime) {
		this.ebdRecvTime = ebdRecvTime;
	}

	/**
	 * 业务数据包发送时间
	 * 
	 * @param ebdSendTime
	 */
	public void setEbdSendTime(Date ebdSendTime) {
		this.ebdSendTime = ebdSendTime;
	}

	/**
	 * 关联会话Id
	 * 
	 * @param flowId
	 */
	public void setFlowId(Long flowId) {
		this.flowId = flowId;
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
	 * @return the ebm
	 */
	public Ebm getEbm() {
		return ebm;
	}

	/**
	 * @param ebm
	 *            the ebm to set
	 */
	public void setEbm(Ebm ebm) {
		this.ebm = ebm;
	}

	/**
	 * @return the ebdFileList
	 */
	public List<EbdFile> getEbdFileList() {
		return ebdFileList;
	}

	/**
	 * @param ebdFileList
	 *            the ebdFileList to set
	 */
	public void setEbdFileList(List<EbdFile> ebdFileList) {
		this.ebdFileList = ebdFileList;
	}

	/**
	 * @return the ebdName
	 */
	public String getEbdName() {
		return ebdName;
	}

	/**
	 * @param ebdName the ebdName to set
	 */
	public void setEbdName(String ebdName) {
		this.ebdName = ebdName;
	}
	
}