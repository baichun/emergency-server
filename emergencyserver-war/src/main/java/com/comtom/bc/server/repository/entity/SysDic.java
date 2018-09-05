package com.comtom.bc.server.repository.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * <b>数据字典明细表[aos_sys_dic]数据持久化对象</b>
 * 
 * @author zhucanhui
 */
@Entity
@Table(name = "aos_sys_dic")
public class SysDic {

	/**
	 * 流水号
	 */
	@Id
	@Column(name = "id_")
	private String id;

	/**
	 * 字典对照码
	 */
	@Column(name = "code_")
	private String code;

	/**
	 * 字典对照值
	 */
	@Column(name = "desc_")
	private String desc;

	/**
	 * 热键
	 */
	@Column(name = "hotkey_")
	private String hotkey;

	/**
	 * 当前状态
	 */
	@Column(name = "status_")
	private String status;

	/**
	 * 备注
	 */
	@Column(name = "remark_")
	private String remark;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "dic_index_id_")
	private SysDicIndex sysDicIndex;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the hotkey
	 */
	public String getHotkey() {
		return hotkey;
	}

	/**
	 * @param hotkey
	 *            the hotkey to set
	 */
	public void setHotkey(String hotkey) {
		this.hotkey = hotkey;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the sysDicIndex
	 */
	public SysDicIndex getSysDicIndex() {
		return sysDicIndex;
	}

	/**
	 * @param sysDicIndex
	 *            the sysDicIndex to set
	 */
	public void setSysDicIndex(SysDicIndex sysDicIndex) {
		this.sysDicIndex = sysDicIndex;
	}

}