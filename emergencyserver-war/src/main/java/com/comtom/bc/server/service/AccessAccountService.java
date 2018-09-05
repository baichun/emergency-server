package com.comtom.bc.server.service;

import com.comtom.bc.server.repository.entity.AccessAccount;

public interface AccessAccountService {
	
	
	/** 账号是否存在
	 * @param account
	 * @return
	 */
	public  boolean validateExists(String  account);
	
	/** 保存账号信息
	 * @param account
	 * @return
	 */
	public AccessAccount save(AccessAccount account);
	
	
	/** 修改密码
	 * @param account
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	public boolean updatePassword(String account,String oldPassword,String newPassword);
	
	
	/** 验证用户密码
	 * @param account
	 * @param password
	 * @return
	 */
	public boolean checkPassword(String account,String password);
	
	/** 强制修改密码
	 * @param account
	 * @param newPassword
	 * @return
	 */
	public boolean forceUpdatePassword(String account,String newPassword);
	
}
