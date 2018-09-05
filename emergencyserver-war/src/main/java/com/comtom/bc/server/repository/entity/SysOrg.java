package com.comtom.bc.server.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <b>aos_sys_org组织表[aos_sys_org]数据持久化对象</b>
 * 
 * @author zhucanhui
 * @date 2015-12-22 22:10:31
 */
@Entity
@Table(name = "aos_sys_org")
public class SysOrg {

	/**
	 * 流水号
	 */
	@Id
	@Column(name = "id_")
	private String id;

	/**
	 * 节点语义ID
	 */
	@Column(name = "cascade_id_")
	private String cascadeId;

	/**
	 * 组织名称
	 */
	@Column(name = "name_")
	private String name;

	/**
	 * 热键
	 */
	@Column(name = "hotkey_")
	private String hotkey;

	/**
	 * 父节点流水号
	 */
	@Column(name = "parent_id_")
	private String parentId;

	/**
	 * 父节点名称
	 */
	@Column(name = "pareant_name_")
	private String pareantName;

	/**
	 * 是否叶子节点
	 */
	@Column(name = "is_leaf_")
	private String isLeaf;

	/**
	 * 是否自动展开
	 */
	@Column(name = "is_auto_expand_")
	private String isAutoExpand;

	/**
	 * 节点图标文件名称
	 */
	@Column(name = "icon_name_")
	private String iconName;

	/**
	 * 当前状态
	 */
	@Column(name = "status")
	private String status_;

	/**
	 * 组织类型
	 */
	@Column(name = "type_")
	private String type;

	/**
	 * 业务对照码
	 */
	@Column(name = "biz_code_")
	private String bizCode;

	/**
	 * 自定义扩展码
	 */
	@Column(name = "custom_code_")
	private String customCode;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time_")
	private String createTime;

	/**
	 * 创建人ID
	 */
	@Column(name = "creater_id_")
	private String createrId;

	/**
	 * 排序号
	 */
	@Column(name = "sort_no_")
	private Integer sortNo;

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
	 * @return the cascadeId
	 */
	public String getCascadeId() {
		return cascadeId;
	}

	/**
	 * @param cascadeId
	 *            the cascadeId to set
	 */
	public void setCascadeId(String cascadeId) {
		this.cascadeId = cascadeId;
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
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * @param parentId
	 *            the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the pareantName
	 */
	public String getPareantName() {
		return pareantName;
	}

	/**
	 * @param pareantName
	 *            the pareantName to set
	 */
	public void setPareantName(String pareantName) {
		this.pareantName = pareantName;
	}

	/**
	 * @return the isLeaf
	 */
	public String getIsLeaf() {
		return isLeaf;
	}

	/**
	 * @param isLeaf
	 *            the isLeaf to set
	 */
	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}

	/**
	 * @return the isAutoExpand
	 */
	public String getIsAutoExpand() {
		return isAutoExpand;
	}

	/**
	 * @param isAutoExpand
	 *            the isAutoExpand to set
	 */
	public void setIsAutoExpand(String isAutoExpand) {
		this.isAutoExpand = isAutoExpand;
	}

	/**
	 * @return the iconName
	 */
	public String getIconName() {
		return iconName;
	}

	/**
	 * @param iconName
	 *            the iconName to set
	 */
	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	/**
	 * @return the status_
	 */
	public String getStatus_() {
		return status_;
	}

	/**
	 * @param status_
	 *            the status_ to set
	 */
	public void setStatus_(String status_) {
		this.status_ = status_;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the bizCode
	 */
	public String getBizCode() {
		return bizCode;
	}

	/**
	 * @param bizCode
	 *            the bizCode to set
	 */
	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	/**
	 * @return the customCode
	 */
	public String getCustomCode() {
		return customCode;
	}

	/**
	 * @param customCode
	 *            the customCode to set
	 */
	public void setCustomCode(String customCode) {
		this.customCode = customCode;
	}

	/**
	 * @return the createTime
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the createrId
	 */
	public String getCreaterId() {
		return createrId;
	}

	/**
	 * @param createrId
	 *            the createrId to set
	 */
	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}

	/**
	 * @return the sortNo
	 */
	public Integer getSortNo() {
		return sortNo;
	}

	/**
	 * @param sortNo
	 *            the sortNo to set
	 */
	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

}