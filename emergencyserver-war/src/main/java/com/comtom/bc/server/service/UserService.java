package com.comtom.bc.server.service;

import com.comtom.bc.server.repository.entity.SysUserExt;
import com.comtom.bc.server.repository.entity.SysUserFingerprint;
import com.comtom.bc.server.repository.entity.SysUserResponse;
import com.comtom.bc.server.req.UserFingerprintVerifyReq;
import com.comtom.bc.server.req.UserQueryReq;
import com.comtom.bc.server.req.UserRoleGrantReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.comtom.bc.server.repository.entity.SysUser;

/**
 * 用户-用户管理业务逻辑处理接口定义
 * 
 * @author zhucanhui
 *
 */
public interface UserService {

	/**
	 * 根据用户属性模糊查询
	 * 
	 * @return Page<SysUser>
	 */
	public Page<SysUser> find(SysUser sysUser, Pageable pageable);

	/**
	 * 根据系统用户帐号获取用户信息
	 * 
	 * @return SysUser
	 */
	public SysUser findByAccount(String account);

	/**
	 * 加密登录密码
	 * 
	 * @param pwdString
	 * @return String
	 */
	public String encryptPwd(String pwdString);

	/**
	 * 根据客户端类型和用户信息获取用户权限信息
	 * 
	 * @param sysUser
	 */
	public void setUserAuth(Integer portalType, SysUser sysUser);

	public  SysUser delUserInfo(SysUser sysUser);

	public SysUserResponse saveSysUser(SysUser sysUser, SysUserExt sysUserExt);

	public SysUserResponse findDetailByAccount(String account);

    void grant(UserRoleGrantReq req,String account);

	public SysUserFingerprint fingerprint(SysUserFingerprint sysUserFingerprint);


	SysUser fingerprintVerify(UserFingerprintVerifyReq req);

	public SysUserFingerprint getfingerprint(String id);
}