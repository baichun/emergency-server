package com.comtom.bc.common.result;

/**
 * 返回结果码定义
 * 
 * @author zhucanhui
 *
 */
public final class ResultCode {

	/**
	 * 成功
	 */
	public static final int SUCCESSFUL = 0;

	/**
	 * 失败
	 */
	public static final int FAILURE = -1;
	
	/**
	 * 超时
	 */
	public static final int TIMEOUT = 1;
	
	/**
	 * 获取Session失败
	 */
	public static final int SESSION_FAILURE = 2;
	
	/**
	 * 没有接口调用权限
	 */
	public static final int AUTH_FAILURE = 3;
	
	/**
	 * 当前用户已登录
	 */
	public static final int USER_EXIST = 4;
}
