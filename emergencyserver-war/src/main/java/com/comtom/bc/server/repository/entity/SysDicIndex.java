package com.comtom.bc.server.repository.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * <b>数据字典索引表[aos_sys_dic_index]数据持久化对象</b>
 * 
 * @author zhucanhui
 */
@Entity
@Table(name = "aos_sys_dic_index")
public class SysDicIndex {

	/**
	 * 流水号
	 */
	@Id
	@Column(name = "id_")
	private String id;

	/**
	 * 字典标识
	 */
	@Column(name = "key_")
	private String key;

	/**
	 * 字典名称
	 */
	@Column(name = "name_")
	private String name;

	/**
	 * 热键
	 */
	@Column(name = "hotkey_")
	private String hotkey;

	/**
	 * 所属分类流水号
	 */
	@Column(name = "catalog_id_")
	private String catalogId;

	/**
	 * 所属分类流节点语义ID
	 */
	@Column(name = "catalog_cascade_id_")
	private String catalogCascadeId;

	/**
	 * 备注
	 */
	@Column(name = "remark_")
	private String remark;

	@OneToMany(mappedBy = "sysDicIndex", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<SysDic> sysDics = new HashSet<SysDic>();

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
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the catalogId
	 */
	public String getCatalogId() {
		return catalogId;
	}

	/**
	 * @param catalogId
	 *            the catalogId to set
	 */
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	/**
	 * @return the catalogCascadeId
	 */
	public String getCatalogCascadeId() {
		return catalogCascadeId;
	}

	/**
	 * @param catalogCascadeId
	 *            the catalogCascadeId to set
	 */
	public void setCatalogCascadeId(String catalogCascadeId) {
		this.catalogCascadeId = catalogCascadeId;
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
	 * @return the sysDics
	 */
	public Set<SysDic> getSysDics() {
		return sysDics;
	}

	/**
	 * @param sysDics the sysDics to set
	 */
	public void setSysDics(Set<SysDic> sysDics) {
		this.sysDics = sysDics;
	}
	
}