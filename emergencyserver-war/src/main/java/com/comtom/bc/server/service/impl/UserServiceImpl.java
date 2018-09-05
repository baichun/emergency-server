package com.comtom.bc.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.*;

import com.comtom.bc.server.repository.dao.*;
import com.comtom.bc.server.repository.entity.*;
import com.comtom.bc.server.repository.mapper.UserMapper;
import com.comtom.bc.server.repository.vo.UserDetailVo;
import com.comtom.bc.server.req.UserFingerprintVerifyReq;
import com.comtom.bc.server.req.UserRoleGrantReq;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.utils.CodecUtil;
import com.comtom.bc.server.service.UserService;
import com.comtom.bc.server.service.base.BaseService;
import zk.jni.JavaToBiokey;

import javax.transaction.Transactional;

/**
 * 用户-用户处理业务逻辑处理
 * 
 * @author zhucanhui
 *
 */
@Service("UserService")
public class UserServiceImpl extends BaseService implements UserService {

	@Autowired
	private SysUserDAO sysUserDAO;

	@Autowired
	private SysUserExtDAO sysUserExtDAO;

	@Autowired
	private SysRoleDAO sysRoleDAO;

	@Autowired
	private UserRoleDAO userRoleDAO;

	@Autowired
	private SysModuleDAO sysModuleDAO;

	@Autowired
	private ModuleRoleDAO moduleRoleDAO;

	@Autowired
	private SysUserFingerprintDAO sysUserFingerprintDAO;


	@Autowired
	private UserMapper userMapper;


	@Value("${codekey}")
	private String codekey;

	/**
	 * 根据用户属性模糊查询
	 */
	public Page<SysUser> find(SysUser sysUser, Pageable pageable) {
		Map<String,Object> map=new HashMap<String,Object>();
/*		mybatis 调用示例
		System.out.println(userMapper.queryTotal());
		List<UserDetailVo> userDetailVos = userMapper.queryList(map);
		for (UserDetailVo u:userDetailVos) {
		}*/
		return sysUserDAO.findByAuto(sysUser, pageable);
	}

	/**
	 * 根据系统用户帐号获取用户信息
	 * 
	 * @return
	 */
	public SysUser findByAccount(String account) {
		return sysUserDAO.findByAccountAndDeleteFlag(account, Constants.USER_NORMAL_FLAG);
	}

	/**
	 * 加密登录密码
	 * 
	 * @param pwdString
	 * @return String
	 */
	public String encryptPwd(String pwdString) {
		String password = CodecUtil.encrypt(pwdString, codekey);
		return password;
	}

	/**
	 * 根据客户端类型和用户信息获取用户权限信息
	 * 
	 * @param sysUser
	 * @return SysUser
	 */
	public void setUserAuth(Integer portalType, SysUser sysUser) {

		// Step.1 根据用户Id获取角色Id(支持多角色)
		List<SysUserRole> userRoleList = userRoleDAO.findByUserId(sysUser.getId());

		List<SysModule> sysModuleList = new ArrayList<SysModule>();

		// Step.2 根据角色Id获取角色权限
		for (SysUserRole userRole : userRoleList) {

			List<SysModuleRole> moduleRoleList = moduleRoleDAO.findByRoleId(userRole.getRoleId());

			// Step.3 根据客户端类型过滤非所属客户端的模块
			for (SysModuleRole sysModuleRole : moduleRoleList) {
				SysModule sysModule = sysModuleDAO.findById(sysModuleRole.getModuleId());

				// 客户端类型匹配，匹配成功，则加入用户权限列表
				if (portalType.equals(sysModule.getPortalType())) {
					sysModuleList.add(sysModule);
				}
			}
		}

		sysUser.setModuleList(sysModuleList);
	}


	public  SysUser delUserInfo(SysUser sysUser){
		sysUser.setDeleteFlag("1");
		return sysUserDAO.save(sysUser);
	}

	@Override
	public SysUserResponse saveSysUser(SysUser sysUser,SysUserExt sysUserExt) {
		sysUser = sysUserDAO.save(sysUser);
		SysUserResponse response=new SysUserResponse();
		if(sysUserExt!=null){
			sysUserExt.setId(sysUser.getId());
			sysUserExt=sysUserExtDAO.save(sysUserExt);
			BeanUtils.copyProperties(sysUserExt,response);
		}
		BeanUtils.copyProperties(sysUser,response);
		return response;
	}

	@Override
	public SysUserResponse findDetailByAccount(String account) {
		SysUser sysUser = sysUserDAO.findByAccountAndDeleteFlag(account, Constants.USER_NORMAL_FLAG);
		SysUserExt sysUserExt = sysUserExtDAO.findOne(sysUser.getId());
		SysUserResponse response=new SysUserResponse();
		BeanUtils.copyProperties(sysUser,response);
		BeanUtils.copyProperties(sysUserExt,response);
		return response;
	}

	@Transactional
	@Override
	public void grant(UserRoleGrantReq req,String account) {
		//1.先删除当前用户绑定的角色 2.添加新的授权

		userRoleDAO.deleteByUserId(req.getUserId());

		List<String> roleIds = req.getRoleIds();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (String roleId:roleIds) {
			SysUserRole sysUserRole=new SysUserRole();
			sysUserRole.setUserId(req.getUserId());
			sysUserRole.setRoleId(roleId);
			sysUserRole.setOperateTime(sdf.format(new Date()));
			sysUserRole.setOperatorId(account);
			userRoleDAO.save(sysUserRole);
		}
	}

	@Override
	public SysUserFingerprint fingerprint(SysUserFingerprint sysUserFingerprint) {

		SysUserFingerprint one = sysUserFingerprintDAO.findOne(sysUserFingerprint.getId());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(one==null){
			sysUserFingerprint.setCreateTime(sdf.format(new Date()));
		}else{
			sysUserFingerprint.setCreateTime(one.getCreateTime());
			sysUserFingerprint.setUpdateTime(sdf.format(new Date()));
		}
		return sysUserFingerprintDAO.save(sysUserFingerprint);
	}


	@Override
	public SysUserFingerprint getfingerprint(String id) {
		return sysUserFingerprintDAO.findOne(id);
	}

	@Override
	public SysUser fingerprintVerify(UserFingerprintVerifyReq req) {

		//1.查出所有的指纹
		List<SysUserFingerprint> list=sysUserFingerprintDAO.findAll();
		//2.对所有的指纹进行遍历比对，匹配则返回.
		for (SysUserFingerprint s:list) {
			boolean isVerify=JavaToBiokey.NativeToProcess(s.getRegTemplate().trim(), req.getFpTemplate().trim());
			if(isVerify){
				return sysUserDAO.findOne(s.getId());
			}
		}
		return null;
	}
}
