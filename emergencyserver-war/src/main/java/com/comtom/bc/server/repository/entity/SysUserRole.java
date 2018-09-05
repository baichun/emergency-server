package com.comtom.bc.server.repository.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * <b>用户-角色关联表[aos_sys_user_role]数据持久化对象</b>
 * 
 * @author zhucanhui
 */
@Entity
@Table(name = "aos_sys_user_role")
public class SysUserRole {

	/**
	 * 流水号
	 */
	@Id
	@Column(name = "id_")
	@GeneratedValue(generator="system_uuid")
	@GenericGenerator(name="system_uuid",strategy="uuid")
	private String id;

	/**
	 * 用户流水号
	 */
	@Column(name = "user_id_")
	private String userId;

	/**
	 * 角色流水号
	 */
	@Column(name = "role_id_")
	private String roleId;

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
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
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