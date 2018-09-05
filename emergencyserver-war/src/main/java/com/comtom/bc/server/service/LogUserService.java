package com.comtom.bc.server.service;

import org.springframework.data.domain.Page;

import com.comtom.bc.server.repository.entity.LogUser;
import com.comtom.bc.server.req.LogUserReq;

/**
 * 键值参数-业务逻辑处理接口定义
 * 
 * @author zhucanhui
 *
 */
public interface LogUserService {

	/**
	 * 生成用户操作日志
	 * 
	 * @param logUser
	 * @return LogUser
	 */
	public LogUser save(LogUser logUser);

	/**
	 * 查询用户操作日志列表
	 * 
	 * @param logUser
	 * @return Page<LogUser>
	 */
	public Page<LogUser> getLogs(LogUserReq req);

	/**
	 * 根据客户端类型统计记录总数
	 * @param portalType
	 * @return long
	 */
	public long count(Integer portalType);

}