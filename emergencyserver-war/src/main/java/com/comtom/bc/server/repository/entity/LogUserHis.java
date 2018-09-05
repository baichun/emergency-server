package com.comtom.bc.server.repository.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <b>bc_log_user_his[bc_log_user_his]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author Kidsoul Qu
 * @date 2017-01-20 17:05:53
 */
@Entity
@Table(name = "bc_log_user_his")
public class LogUserHis {

	/**
	 * 历史日志编号
	 */
	@Id
	private Integer logId;
	
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
	 * 1-中控管理系统，2-制播管理系统，3-调度控制系统
	 */
	private Integer portalType;
	
	/**
	 * clientIp
	 */
	private String clientIp;
	

	/**
	 * 历史日志编号
	 * 
	 * @return logId
	 */
	public Integer getLogId() {
		return logId;
	}
	
	/**
	 * 用户名
	 * 
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * 模块
	 * 
	 * @return module
	 */
	public String getModule() {
		return module;
	}
	
	/**
	 * 操作
	 * 
	 * @return operation
	 */
	public String getOperation() {
		return operation;
	}
	
	/**
	 * 内容
	 * 
	 * @return content
	 */
	public String getContent() {
		return content;
	}
	
	/**
	 * 记录时间
	 * 
	 * @return logTime
	 */
	public String getLogTime() {
		return logTime;
	}
	
	/**
	 * 1-中控管理系统，2-制播管理系统，3-调度控制系统
	 * 
	 * @return portalType
	 */
	public Integer getPortalType() {
		return portalType;
	}
	
	/**
	 * clientIp
	 * 
	 * @return clientIp
	 */
	public String getClientIp() {
		return clientIp;
	}
	

	/**
	 * 历史日志编号
	 * 
	 * @param logId
	 */
	public void setLogId(Integer logId) {
		this.logId = logId;
	}
	
	/**
	 * 用户名
	 * 
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * 模块
	 * 
	 * @param module
	 */
	public void setModule(String module) {
		this.module = module;
	}
	
	/**
	 * 操作
	 * 
	 * @param operation
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	/**
	 * 内容
	 * 
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * 记录时间
	 * 
	 * @param logTime
	 */
	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}
	
	/**
	 * 1-中控管理系统，2-制播管理系统，3-调度控制系统
	 * 
	 * @param portalType
	 */
	public void setPortalType(Integer portalType) {
		this.portalType = portalType;
	}
	
	/**
	 * clientIp
	 * 
	 * @param clientIp
	 */
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	

}