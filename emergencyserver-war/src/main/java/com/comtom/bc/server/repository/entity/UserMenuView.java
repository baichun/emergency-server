package com.comtom.bc.server.repository.entity;

import javax.persistence.*;
import java.util.List;


/**
 * <b>功能模块表[aos_sys_module]数据持久化对象</b>
 * 
 * @author zhucanhui
 */
@Entity
@Table(name = "v_ewbs_module_role")
public class UserMenuView {

	/**
	 * 功能模块流水号
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
	 * 功能模块名称
	 */
	@Column(name = "name_")
	private String name;
	
	/**
	 * 主页面URL
	 */
	@Column(name = "url_")
	private String url;
	
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
	@Column(name = "status_")
	private String status;
	
	/**
	 * 父节点名称
	 */
	@Column(name = "parent_name_")
	private String parentName;
	
	/**
	 * 矢量图标
	 */
	@Column(name = "vector_")
	private String vector;
	
	/**
	 * 排序号
	 */
	@Column(name = "sort_no_")
	private Integer sortNo;
	
	@Column(name = "portal_type_")
	private Integer portalType;

	@Column(name = "user_id_")
	private String userId;

	@Column(name = "user_name_")
	private String userName;

/*	@Column(name = "role_id_")
	private String roleId;

	@Column(name = "role_name_")
	private String roleName;*/

	@Transient
	private List<UserMenuView> children;

	public List<UserMenuView> getChildren() {
		return children;
	}

	public void setChildren(List<UserMenuView> children) {
		this.children = children;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
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
	 * @param cascadeId the cascadeId to set
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
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the hotkey
	 */
	public String getHotkey() {
		return hotkey;
	}

	/**
	 * @param hotkey the hotkey to set
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
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the isLeaf
	 */
	public String getIsLeaf() {
		return isLeaf;
	}

	/**
	 * @param isLeaf the isLeaf to set
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
	 * @param isAutoExpand the isAutoExpand to set
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
	 * @param iconName the iconName to set
	 */
	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the parentName
	 */
	public String getParentName() {
		return parentName;
	}

	/**
	 * @param parentName the parentName to set
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	/**
	 * @return the vector
	 */
	public String getVector() {
		return vector;
	}

	/**
	 * @param vector the vector to set
	 */
	public void setVector(String vector) {
		this.vector = vector;
	}

	/**
	 * @return the sortNo
	 */
	public Integer getSortNo() {
		return sortNo;
	}

	/**
	 * @param sortNo the sortNo to set
	 */
	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	/**
	 * @return the portalType
	 */
	public Integer getPortalType() {
		return portalType;
	}

	/**
	 * @param portalType the portalType to set
	 */
	public void setPortalType(Integer portalType) {
		this.portalType = portalType;
	}
	
}