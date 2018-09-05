package com.comtom.bc.server.repository.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <b>bc_log_user[bc_log_user]数据持久化对象</b>
 * 
 * @author zhucanhui
 */
@Entity
@Table(name = "bc_log_user")
public class LogUser {

	/**
	 * 日志编号
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long logId;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 模块
	 */
	private String module;

	/**
	 * 操作
	 */
	private String operation;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 记录时间
	 */
	private String logTime;

	/**
	 * 客户端类型
	 */
	private Integer portalType;

	/**
	 * 客户端IP
	 */
	private String clientIp;

	/**
	 * @return the logId
	 */
	public Long getLogId() {
		return logId;
	}

	/**
	 * @param logId
	 *            the logId to set
	 */
	public void setLogId(Long logId) {
		this.logId = logId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the module
	 */
	public String getModule() {
		return module;
	}

	/**
	 * @param module
	 *            the module to set
	 */
	public void setModule(String module) {
		this.module = module;
	}

	/**
	 * @return the operation
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * @param operation
	 *            the operation to set
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the logTime
	 */
	public String getLogTime() {
		return logTime;
	}

	/**
	 * @param logTime
	 *            the logTime to set
	 */
	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}

	/**
	 * @return the portalType
	 */
	public Integer getPortalType() {
		return portalType;
	}

	/**
	 * @param portalType
	 *            the portalType to set
	 */
	public void setPortalType(Integer portalType) {
		this.portalType = portalType;
	}

	/**
	 * @return the clientIp
	 */
	public String getClientIp() {
		return clientIp;
	}

	/**
	 * @param clientIp
	 *            the clientIp to set
	 */
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

}