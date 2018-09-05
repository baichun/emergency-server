package com.comtom.bc.server.repository.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 接入平台账号
 * @author comtom
 *
 */
@Entity
@Table(name = "bc_access_account")
public class AccessAccount {
	
	/**
	 * 账号
	 */
	@Id
	private String account;
	
	
	/**
	 * 盐
	 */
	private String salt;
	
	
	/**
	 * 密码
	 */
	private String password;


	public String getAccount() {
		return account;
	}


	public void setAccount(String account) {
		this.account = account;
	}


	public String getSalt() {
		return salt;
	}


	public void setSalt(String salt) {
		this.salt = salt;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	
	

}
