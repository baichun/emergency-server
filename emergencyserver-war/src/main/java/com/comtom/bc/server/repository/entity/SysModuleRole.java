package com.comtom.bc.server.repository.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * <b>功能模块-角色关联表[aos_sys_module_role]数据持久化对象</b>
 * 
 * @author zhucanhui
 */
@Entity
@Table(name = "aos_sys_module_role")
public class SysModuleRole {

	/**
	 * 流水号
	 */
	@Id
	@Column(name = "id_")
	@GeneratedValue(generator="system_uuid")
	@GenericGenerator(name="system_uuid",strategy="uuid")
	private String id;

	/**
	 * 角色流水号
	 */
	@Column(name = "role_id_")
	private String roleId;

	/**
	 * 功能模块流水号
	 */
	@Column(name = "module_id_")
	private String moduleId;

	/**
	 * 权限类型
	 */
	@Column(name = "grant_type_")
	private String grantType;

	/**
	 * 授权时间
	 */
	@Column(name = "operate_time_")
	private String operateTime;

	/**
	 * 授权人流水号
	 */
	@Column(name = "operator_id_")
	private String operatorId;

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
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId
	 *            the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the moduleId
	 */
	public String getModuleId() {
		return moduleId;
	}

	/**
	 * @param moduleId
	 *            the moduleId to set
	 */
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	/**
	 * @return the grantType
	 */
	public String getGrantType() {
		return grantType;
	}

	/**
	 * @param grantType
	 *            the grantType to set
	 */
	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	/**
	 * @return the operateTime
	 */
	public String getOperateTime() {
		return operateTime;
	}

	/**
	 * @param operateTime
	 *            the operateTime to set
	 */
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	/**
	 * @return the operatorId
	 */
	public String getOperatorId() {
		return operatorId;
	}

	/**
	 * @param operatorId
	 *            the operatorId to set
	 */
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

}