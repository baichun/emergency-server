package com.comtom.bc.server.req;

/**
 * 用户登录参数对象
 * 
 * @author zhucanhui
 *
 */
public class UserLoginReq extends BaseReq {
	
	/**
	 * 用户名
	 */
	private String account;
	
	/**
	 * 密码
	 */
	private String password;

	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
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
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
}
