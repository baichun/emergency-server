package com.comtom.bc.server.repository.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Entity
@Table(name = "aos_sys_param")
public class ParamInfo {

	/**
	 * 流水号
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id_;

	/**
	 * 参数键
	 */
	private String key_;

	/**
	 * 参数值
	 */
	private String value_;

	/**
	 * 所属分类流水号
	 */
	private String catalog_id_;

	/**
	 * 所属分类节点语义ID
	 */
	private String catalog_cascade_id_;

	/**
	 * 参数名称
	 */
	private String name_;

	/**
	 * 是否可覆盖
	 */
	private String is_overwrite_;

	/**
	 * 覆盖来源字段
	 */
	private String overwrite_field_;

	/**
	 * 备注
	 */
	private String remark_;

	public String getId_() {
		return id_;
	}

	public void setId_(String id_) {
		this.id_ = id_;
	}

	public String getKey_() {
		return key_;
	}

	public void setKey_(String key_) {
		this.key_ = key_;
	}

	public String getValue_() {
		return value_;
	}

	public void setValue_(String value_) {
		this.value_ = value_;
	}

	public String getCatalog_id_() {
		return catalog_id_;
	}

	public void setCatalog_id_(String catalog_id_) {
		this.catalog_id_ = catalog_id_;
	}

	public String getCatalog_cascade_id_() {
		return catalog_cascade_id_;
	}

	public void setCatalog_cascade_id_(String catalog_cascade_id_) {
		this.catalog_cascade_id_ = catalog_cascade_id_;
	}

	public String getName_() {
		return name_;
	}

	public void setName_(String name_) {
		this.name_ = name_;
	}

	public String getIs_overwrite_() {
		return is_overwrite_;
	}

	public void setIs_overwrite_(String is_overwrite_) {
		this.is_overwrite_ = is_overwrite_;
	}

	public String getOverwrite_field_() {
		return overwrite_field_;
	}

	public void setOverwrite_field_(String overwrite_field_) {
		this.overwrite_field_ = overwrite_field_;
	}

	public String getRemark_() {
		return remark_;
	}

	public void setRemark_(String remark_) {
		this.remark_ = remark_;
	}
}