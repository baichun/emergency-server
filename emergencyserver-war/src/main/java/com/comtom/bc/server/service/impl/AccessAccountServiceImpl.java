package com.comtom.bc.server.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comtom.bc.server.repository.dao.AccessAccountDAO;
import com.comtom.bc.server.repository.entity.AccessAccount;
import com.comtom.bc.server.service.AccessAccountService;


@Service
@Transactional
public class AccessAccountServiceImpl implements AccessAccountService {
	
	@Autowired
	private AccessAccountDAO accessAccountDAO;
	
	private static final String UTF8 = "UTF-8";
	
	private static final String MD5 = "MD5";
	
	private static final String SHA256 = "SHA-256";

	@Override
	public AccessAccount save(AccessAccount entity) {
		
		entity.setSalt(genSalt());
		entity.setPassword(encryptPassword(entity.getAccount(), entity.getSalt(), entity.getPassword()));
		return accessAccountDAO.saveAndFlush(entity);
	}
	
	
	public  boolean validateExists(String  account){
		AccessAccount one = accessAccountDAO.findOne(account);
		if(one!=null){
			return true;
		}
		return false;
	}
	
	private String encryptPassword(String account,String salt,String password){
		try {
			MessageDigest sha=MessageDigest.getInstance(SHA256);
			sha.reset();
			sha.update(salt.getBytes(UTF8));
			sha.update(password.getBytes(UTF8));
			byte[] sha1 = sha.digest(account.getBytes(UTF8));
			sha.reset();
			byte[] sha2 = sha.digest(sha1);
			MessageDigest md5=MessageDigest.getInstance(MD5);
			md5.reset();
			byte[] digest = md5.digest(sha2);
			return Hex.encodeHexString(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	private String genSalt(){
		return  RandomStringUtils.randomAlphanumeric(32); 
	}

	@Override
	public boolean updatePassword(String account, String oldPassword, String newPassword) {
		if(checkPassword(account,oldPassword)){
			AccessAccount one = accessAccountDAO.findOne(account);
			one.setPassword(encryptPassword(one.getAccount(), one.getSalt(), newPassword));
			accessAccountDAO.saveAndFlush(one);
			return true;
		}
		return false;
	}

	@Override
	public boolean forceUpdatePassword(String account, String newPassword) {
		AccessAccount one = accessAccountDAO.findOne(account);
		if(one!=null){
			one.setPassword(encryptPassword(one.getAccount(), one.getSalt(), newPassword));
			accessAccountDAO.saveAndFlush(one);
			return true;
		}
		return false;
	}

	@Override
	public boolean checkPassword(String account, String password) {
		AccessAccount one = accessAccountDAO.findOne(account);
		if(one!=null){
			return one.getPassword().equals(encryptPassword(one.getAccount(), one.getSalt(), password));
		}
		return false;
	}
	

}
