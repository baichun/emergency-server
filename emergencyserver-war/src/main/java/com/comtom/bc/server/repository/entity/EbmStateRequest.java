package com.comtom.bc.server.repository.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <b>bc_ebm_state_request[bc_ebm_state_request]数据持久化对象</b>
 * 
 * @author zhucanhui
 * @date 2016-12-20 17:03:38
 */
@Entity
@Table(name = "bc_ebm_state_request")
public class EbmStateRequest {

	/**
	 * 消息播发状态请求编号
	 */
	@Id
	private String ebmStateRequestId;

	/**
	 * 消息编号
	 */
	private String ebmId;

	/**
	 * 关联的资源编号
	 */
	private String relatedEbrId;

	/**
	 * 关联数据包编号
	 */
	private String relatedEbdId;

	/**
	 * 消息播发状态请求编号
	 * 
	 * @return ebmStateRequestId
	 */
	public String getEbmStateRequestId() {
		return ebmStateRequestId;
	}

	/**
	 * 消息编号
	 * 
	 * @return ebmId
	 */
	public String getEbmId() {
		return ebmId;
	}

	/**
	 * 关联的资源编号
	 * 
	 * @return relatedEbrId
	 */
	public String getRelatedEbrId() {
		return relatedEbrId;
	}

	/**
	 * 关联数据包编号
	 * 
	 * @return relatedEbdId
	 */
	public String getRelatedEbdId() {
		return relatedEbdId;
	}

	/**
	 * 消息播发状态请求编号
	 * 
	 * @param ebmStateRequestId
	 */
	public void setEbmStateRequestId(String ebmStateRequestId) {
		this.ebmStateRequestId = ebmStateRequestId;
	}

	/**
	 * 消息编号
	 * 
	 * @param ebmId
	 */
	public void setEbmId(String ebmId) {
		this.ebmId = ebmId;
	}

	/**
	 * 关联的资源编号
	 * 
	 * @param relatedEbrId
	 */
	public void setRelatedEbrId(String relatedEbrId) {
		this.relatedEbrId = relatedEbrId;
	}

	/**
	 * 关联数据包编号
	 * 
	 * @param relatedEbdId
	 */
	public void setRelatedEbdId(String relatedEbdId) {
		this.relatedEbdId = relatedEbdId;
	}

}