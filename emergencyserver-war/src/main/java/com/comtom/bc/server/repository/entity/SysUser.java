package com.comtom.bc.server.repository.entity;

import org.hibernate.annotations.GenericGenerator;

import java.util.List;

import javax.persistence.*;

/**
 * <b>用户基本信息表[aos_sys_user]数据持久化对象</b>
 * 
 * @author zhucanhui
 */
@Entity
@Table(name = "aos_sys_user")
public class SysUser {

	/**
	 * 流水号
	 */
	@Id
	@Column(name = "id_")
	@GeneratedValue(generator="system_uuid")
	@GenericGenerator(name="system_uuid",strategy="uuid")
	private String id;

	/**
	 * 用户登录帐号
	 */
	@Column(name = "account_")
	private String account;

	/**
	 * 密码
	 */
	@Column(name = "password_")
	private String password;

	/**
	 * 用户姓名
	 */
	@Column(name = "name_")
	private String name;

	/**
	 * 性别
	 */
	@Column(name = "sex_")
	private String sex;

	/**
	 * 所属主部门流水号
	 */
	@Column(name = "org_id_")
	private String orgid;

	/**
	 * 用户状态
	 */
	@Column(name = "status_")
	private String status;

	/**
	 * 用户类型
	 */
	@Column(name = "type_")
	private String type;

	/**
	 * 业务对照码
	 */
	@Column(name = "biz_code")
	private String bizCode;

	/**
	 * 经办时间
	 */
	@Column(name = "create_time_")
	private String createTime;

	/**
	 * 经办人流水号
	 */
	@Column(name = "creater_id_")
	private String createrId;

	/**
	 * 组织编号
	 */
	@Column(name = "org_cascade_id_")
	private String orgCascadeId;

	/**
	 * 物理删除标识
	 */
	@Column(name = "delete_flag_")
	private String deleteFlag;

	/**
	 * 用户关联角色
	 */
	@Transient
	private SysRole sysRole;

	/**
	 * 用户关联权限
	 */
	@Transient
	private List<SysModule> moduleList;

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
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
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
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex
	 *            the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return the orgid
	 */
	public String getOrgid() {
		return orgid;
	}

	/**
	 * @param orgid
	 *            the orgid to set
	 */
	public void setOrgid(String orgid) {
		this.orgid = orgid;
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
	 * @return the orgCascadeId
	 */
	public String getOrgCascadeId() {
		return orgCascadeId;
	}

	/**
	 * @param orgCascadeId
	 *            the orgCascadeId to set
	 */
	public void setOrgCascadeId(String orgCascadeId) {
		this.orgCascadeId = orgCascadeId;
	}

	/**
	 * @return the deleteFlag
	 */
	public String getDeleteFlag() {
		return deleteFlag;
	}

	/**
	 * @param deleteFlag
	 *            the deleteFlag to set
	 */
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	/**
	 * @return the sysRole
	 */
	public SysRole getSysRole() {
		return sysRole;
	}

	/**
	 * @param sysRole
	 *            the sysRole to set
	 */
	public void setSysRole(SysRole sysRole) {
		this.sysRole = sysRole;
	}

	/**
	 * @return the moduleList
	 */
	public List<SysModule> getModuleList() {
		return moduleList;
	}

	/**
	 * @param moduleList
	 *            the moduleList to set
	 */
	public void setModuleList(List<SysModule> moduleList) {
		this.moduleList = moduleList;
	}

}