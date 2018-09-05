package com.comtom.bc.server.repository.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * <b>角色表[aos_sys_role]数据持久化对象</b>
 * 
 * @author zhucanhui
 */
@Entity
@Table(name = "aos_sys_role")
public class SysRole {

	/**
	 * 流水号
	 */
	@Id
	@Column(name = "id_")
	@GeneratedValue(generator="system_uuid")
	@GenericGenerator(name="system_uuid",strategy="uuid")
	private String id;

	/**
	 * 角色名称
	 */
	@Column(name = "name_")
	private String name;

	/**
	 * 当前状态
	 */
	@Column(name = "status_")
	private String status;

	/**
	 * 角色类型
	 */
	@Column(name = "type_")
	private String type;

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
	 * 创建人所属部门流水号
	 */
	@Column(name = "creater_org_id_")
	private String createrOrgId;

	/**
	 * 创建人所属部门节点语义ID
	 */
	@Column(name = "creater_org_cascade_id_")
	private String createrOrgCascadeId;

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
	 * @return the createrOrgId
	 */
	public String getCreaterOrgId() {
		return createrOrgId;
	}

	/**
	 * @param createrOrgId
	 *            the createrOrgId to set
	 */
	public void setCreaterOrgId(String createrOrgId) {
		this.createrOrgId = createrOrgId;
	}

	/**
	 * @return the createrOrgCascadeId
	 */
	public String getCreaterOrgCascadeId() {
		return createrOrgCascadeId;
	}

	/**
	 * @param createrOrgCascadeId
	 *            the createrOrgCascadeId to set
	 */
	public void setCreaterOrgCascadeId(String createrOrgCascadeId) {
		this.createrOrgCascadeId = createrOrgCascadeId;
	}

}