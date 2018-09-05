package com.comtom.bc.server.repository.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <b>bc_ebm_event_type[bc_ebm_event_type]数据持久化对象</b>
 * 
 * @author zhucanhui
 * @date 2016-12-20 17:03:37
 */
@Entity
@Table(name = "bc_ebm_event_type")
public class EbmEventType {

	/**
	 * 事件编码
	 */
	@Id
	private String eventCode;

	/**
	 * 事件描述
	 */
	private String eventDesc;

	/**
	 * 是否叶子节点（0不是1是）
	 */
	private String isLeaf;

	/**
	 * 父类编码
	 */
	private String parentCode;

	/**
	 * 事件编码
	 * 
	 * @return eventCode
	 */
	public String getEventCode() {
		return eventCode;
	}

	/**
	 * 事件描述
	 * 
	 * @return eventDesc
	 */
	public String getEventDesc() {
		return eventDesc;
	}

	/**
	 * 是否叶子节点（0不是1是）
	 * 
	 * @return isLeaf
	 */
	public String getIsLeaf() {
		return isLeaf;
	}

	/**
	 * 父类编码
	 * 
	 * @return parentCode
	 */
	public String getParentCode() {
		return parentCode;
	}

	/**
	 * 事件编码
	 * 
	 * @param eventCode
	 */
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	/**
	 * 事件描述
	 * 
	 * @param eventDesc
	 */
	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
	}

	/**
	 * 是否叶子节点（0不是1是）
	 * 
	 * @param isLeaf
	 */
	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}

	/**
	 * 父类编码
	 * 
	 * @param parentCode
	 */
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

}