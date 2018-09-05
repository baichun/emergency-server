package com.comtom.bc.server.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <b>参数表[aos_sys_param]数据持久化对象</b>
 * 
 * @author zhucanhui
 */
@Entity
@Table(name = "aos_sys_param")
public class SysParam {

	/**
	 * 流水号
	 */
	@Id
	@Column(name = "id_")
	private String id;

	/**
	 * 参数键
	 */
	@Column(name = "key_")
	private String key;

	/**
	 * 参数值
	 */
	@Column(name = "value_")
	private String value;

	/**
	 * 所属分类流水号
	 */
	@Column(name = "catalog_id_")
	private String catalogId;

	/**
	 * 所属分类节点语义ID
	 */
	@Column(name = "catalog_cascade_id_")
	private String catalogCascadeId;

	/**
	 * 参数名称
	 */
	@Column(name = "name_")
	private String name;

	/**
	 * 是否可覆盖
	 */
	@Column(name = "is_overwrite_")
	private String isOverwrite;

	/**
	 * 覆盖来源字段
	 */
	@Column(name = "overwrite_field_")
	private String overwriteField;

	/**
	 * 备注
	 */
	@Column(name = "remark_")
	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getCatalogCascadeId() {
		return catalogCascadeId;
	}

	public void setCatalogCascadeId(String catalogCascadeId) {
		this.catalogCascadeId = catalogCascadeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsOverwrite() {
		return isOverwrite;
	}

	public void setIsOverwrite(String isOverwrite) {
		this.isOverwrite = isOverwrite;
	}

	public String getOverwriteField() {
		return overwriteField;
	}

	public void setOverwriteField(String overwriteField) {
		this.overwriteField = overwriteField;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}