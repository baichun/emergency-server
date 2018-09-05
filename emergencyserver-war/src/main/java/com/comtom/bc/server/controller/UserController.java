package com.comtom.bc.server.controller;

import antlr.StringUtils;
import com.comtom.bc.server.repository.entity.*;
import com.comtom.bc.server.req.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.User;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.result.JsonResult;
import com.comtom.bc.common.result.ResultCode;
import com.comtom.bc.common.result.ResultKey;
import com.comtom.bc.common.result.ResultMsg;
import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.common.utils.GenUUId;
import com.comtom.bc.common.utils.JsonUtil;
import com.comtom.bc.exchange.util.DateTimeUtil;
import com.comtom.bc.server.asset.RedisUtil;
import com.comtom.bc.server.asset.WebCxt;
import com.comtom.bc.server.service.ParamService;
import com.comtom.bc.server.service.UserService;

/**
 * 提供前端登陆鉴权，登陆通过后，返回用户权限信息和jsessionid前端
 * 
 * @author zhucanhui
 *
 */
@Api(value = "用户管理")
@RestController
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	ParamService paramService;

	@Autowired
	RedisUtil redisUtil;

	/**
	 * 提供客户端登录鉴权
	 *
	 * @param portalType
	 * @param account
	 * @param password
	 * @return ResponseEntity<String>
	 */
	@ApiOperation(value = "用户管理", notes = "用户登录")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<String> login(HttpServletRequest request, @RequestBody UserLoginReq req) {

		String account = req.getAccount();
		String password = req.getPassword();
		Integer portalType = req.getPortalType();

		// 设置公用参数
		request.setAttribute(Constants.ACCOUNT, account);
		request.setAttribute(Constants.PORTAL_TYPE, portalType);

		// Step.1 登陆鉴权，根据用户名密码匹配用户信息
		SysUser sysUser = userService.findByAccount(account);

		// 获取登录Session超时时间间隔键值参数
		String timeout = paramService.findValueByKey(Constants.JSESSIONID_TIMEOUT);
		String userLogin = paramService.findValueByKey(Constants.USER_LOGIN);

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 用户信息存在，并且密码匹配正确
		if (sysUser != null && sysUser.getPassword().equals(userService.encryptPwd(password))) {

			// 初始化业务数据Map对象
			Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);
			try {

				// 不允许用户重复登录，则保存当前用户信息至缓存
				if (!userLogin.equals(Constants.ALLOW_LOGIN)) {
					// 获取当前用户登录会话
					Object dateStr = redisUtil.get(account);

					if (CommonUtil.isNotEmpty(dateStr)) {
						resultJson.setResultCode(ResultCode.USER_EXIST);
						resultJson.setResultMsg(ResultMsg.USER_EXIST);
						return new ResponseEntity<String>(JsonUtil.toJson(resultJson),
								HttpStatus.OK);
					} else {
						redisUtil.set(account, DateTimeUtil.dateToString(new Date()),
								Long.valueOf(timeout));
					}
				}
				// Step.2 鉴权通过，设置session到缓存
				String uuid = GenUUId.uuid();
				// 缓存用户登录信息
				redisUtil.set(uuid, sysUser, Long.valueOf(timeout));

				resultJson.setJsessionid(uuid);
				// 根据用户和客户端类型设置权限清单
				userService.setUserAuth(portalType, sysUser);

			} catch (RuntimeException e) {
				e.printStackTrace();
			}

			// 设置用户登录用户信息
			dataMap.put(ResultKey.USER_INFO_KEY, sysUser);

			// 返回登录结果信息和session
			resultJson.setDataMap(dataMap);

			// 设置用户操作日志参数和内容
			String logContent = "用户【" + account + "】登录成功.";
			request.setAttribute(Constants.USER_LOG, logContent);

			return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
					HttpStatus.OK);

		} else {

			// 设置用户操作日志内容
			String logContent = "用户【" + account + "】登录失败.";
			request.setAttribute(Constants.USER_LOG, logContent);

			resultJson.setResultCode(ResultCode.FAILURE);
			resultJson.setResultMsg(ResultMsg.RESULT_FAILURE);

			return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
		}
	}

	/**
	 * 提供客户端登出
	 *
	 * @param portalType
	 * @param account
	 * @param password
	 * @return ResponseEntity<String>
	 */
	@ApiOperation(value = "用户管理", notes = "用户注销")
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ResponseEntity<String> logout(HttpServletRequest request,
										 @RequestBody UserLoginReq userLoginVo, @RequestParam String jsessionid) {

		// 获取请求参数
		String account = userLoginVo.getAccount();

		if (CommonUtil.isEmpty(account)) {
			SysUser cursysUser = (SysUser) redisUtil.get(jsessionid);
			account = cursysUser.getAccount();
		}


		// 设置公用参数
		request.setAttribute(Constants.ACCOUNT, account);
		request.setAttribute(Constants.PORTAL_TYPE, userLoginVo.getPortalType());

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		if (CommonUtil.isEmpty(jsessionid)) {
			resultJson.setResultCode(ResultCode.FAILURE);
			resultJson.setResultMsg(ResultMsg.RESULT_FAILURE);

			// 设置用户操作日志内容
			String logContent = "用户【" + account + "】注销失败.";
			request.setAttribute(Constants.USER_LOG, logContent);

			return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);

		} else {
			// 清除缓存用户登录信息
			redisUtil.remove(jsessionid);

			Object dateStr = redisUtil.get(account);

			if (CommonUtil.isNotEmpty(dateStr)) {
				redisUtil.remove(account);
			}

			// 初始化业务数据Map对象
			Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);
			resultJson.setDataMap(dataMap);

			// 设置用户操作日志内容
			String logContent = "用户【" + account + "】注销成功.";
			request.setAttribute(Constants.USER_LOG, logContent);

			return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
		}
	}


	@ApiOperation(value = "查询用户列表", notes = "用户分页列表")
	@RequestMapping(value = "api/user/list", method = RequestMethod.GET)
	public ResponseEntity<String> list(HttpServletRequest request,
									   @ModelAttribute UserQueryReq req, @RequestParam String jsessionid) {

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);


		SysUser sysUser = new SysUser();
		sysUser.setDeleteFlag("0");
		if (CommonUtil.isNotEmpty(req.getAccount())) {
			sysUser.setAccount(req.getAccount());
		}
		if (CommonUtil.isNotEmpty(req.getName())) {
			sysUser.setName(req.getName());
		}
		Pageable pageable = new PageRequest(req.getPageNum() - 1, req.getPageSize());

		// 业务逻辑处理
		Page<SysUser> page = userService.find(sysUser, pageable);

		dataMap.put(ResultKey.USER_LIST_KEY, page.getContent());
		resultJson.setCurrPage(page.getNumber() + 1);
		resultJson.setTotalPage(page.getTotalPages());
		resultJson.setTotalCount(page.getTotalElements());

		// 返回处理结果
		resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}


	@ApiOperation(value = "用户删除", notes = "用户删除")
	@RequestMapping(value = "api/user/delete/{account}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUserInfo(HttpServletRequest request,
												 @PathVariable("account") String account, @RequestParam String jsessionid, @RequestParam Integer portalType) {

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();
		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);
		resultJson.setDataMap(dataMap);

		String operAccount = (String) request.getAttribute(Constants.ACCOUNT);

		SysUser sysUser = userService.findByAccount(account);


		if (sysUser != null) {
			userService.delUserInfo(sysUser);
			// 用户操作日志
			request.setAttribute(Constants.ACCOUNT, account);
			request.setAttribute(Constants.PORTAL_TYPE, portalType);

			// 设置用户操作日志参数和内容
			String logContent = "用户【" + operAccount + "】删除用户信息【" + sysUser.getName() + "】成功.";
			request.setAttribute(Constants.USER_LOG, logContent);

		} else {
			resultJson.setResultCode(ResultCode.FAILURE);
			resultJson.setResultMsg(ResultMsg.RESULT_FAILURE);
		}

		return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
	}


	@ApiOperation(value = "获取当前用户", notes = "获取当前用户")
	@RequestMapping(value = "api/user/curUser", method = RequestMethod.GET)
	public ResponseEntity<String> curUser(HttpServletRequest request,
												  @RequestParam String jsessionid) {
		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();
		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		SysUser cursysUser = (SysUser) redisUtil.get(jsessionid);

		dataMap.put(ResultKey.USER_INFO_KEY, cursysUser);
		resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
	}


	@ApiOperation(value = "用户启用或停用", notes = "用户启用或停用")
	@RequestMapping(value = "api/user/startOrStop", method = RequestMethod.POST)
	public ResponseEntity<String> startOrStop(HttpServletRequest request,
											  @RequestBody UserStartOrStopReq req, @RequestParam String jsessionid, @RequestParam Integer portalType) {

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();
		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);
		resultJson.setDataMap(dataMap);

		String operAccount = (String) request.getAttribute(Constants.ACCOUNT);

		SysUser sysUser = userService.findByAccount(req.getAccount());


		if (sysUser != null) {

			sysUser.setStatus(req.getStatus());
			userService.saveSysUser(sysUser, null);
			// 用户操作日志
			request.setAttribute(Constants.ACCOUNT, operAccount);
			request.setAttribute(Constants.PORTAL_TYPE, portalType);

			// 设置用户操作日志参数和内容
			String logContent = "用户【" + operAccount + "】删除用户信息【" + sysUser.getName() + "】成功.";
			request.setAttribute(Constants.USER_LOG, logContent);

		} else {
			resultJson.setResultCode(ResultCode.FAILURE);
			resultJson.setResultMsg(ResultMsg.RESULT_FAILURE);
		}

		return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
	}


	@ApiOperation(value = "新增用户", notes = "新增用户")
	@RequestMapping(value = "api/user/save", method = RequestMethod.POST)
	public ResponseEntity<String> saveSysUser(HttpServletRequest request,
											  @RequestBody SysUserSaveReq req, @RequestParam String jsessionid) {

		SysUser cursysUser = (SysUser) redisUtil.get(jsessionid);
		String account = cursysUser.getAccount();

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		SysUser sysUser = userService.findByAccount(req.getAccount());


		if (sysUser != null) {
			//直接返回，提示当前账号已经被注册
			resultJson.setResultCode(ResultCode.FAILURE);
			resultJson.setResultMsg("当前账号已经被注册");
			resultJson.setDataMap(dataMap);
			return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
		} else {
			sysUser = new SysUser();
			BeanUtils.copyProperties(req, sysUser);

			sysUser.setCreaterId(cursysUser.getId());
			sysUser.setDeleteFlag("0");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sysUser.setCreateTime(sdf.format(new Date()));
			sysUser.setOrgid("63cf387a243d4d9799367d773b853346");
			sysUser.setOrgCascadeId("0");
			sysUser.setType("1");
			sysUser.setStatus("1");
			sysUser.setPassword(userService.encryptPwd(sysUser.getPassword()));


			SysUserExt sysUserExt = new SysUserExt();
			BeanUtils.copyProperties(req, sysUserExt);


			SysUserResponse response = userService.saveSysUser(sysUser, sysUserExt);


			// 用户操作日志
			request.setAttribute(Constants.ACCOUNT, account);
			request.setAttribute(Constants.PORTAL_TYPE, req.getPortalType());

			if (sysUser != null) {

				// 设置用户操作日志参数和内容
				String logContent = "用户【" + account + "】创建用户账号：【" + sysUser.getAccount()
						+ "】成功.";
				request.setAttribute(Constants.USER_LOG, logContent);
				dataMap.put(ResultKey.USER_INFO_KEY, response);
				resultJson.setDataMap(dataMap);
			} else {
				resultJson.setResultCode(ResultCode.FAILURE);
				resultJson.setResultMsg(ResultMsg.RESULT_FAILURE);
				resultJson.setDataMap(dataMap);
				return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
			}
		}
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
	}

	@ApiOperation(value = "用户查找", notes = "根据账号找用户")
	@RequestMapping(value = "api/user/get/{account}", method = RequestMethod.GET)
	public ResponseEntity<String> get(HttpServletRequest request,
									  @PathVariable("account") String account, @RequestParam String jsessionid, @RequestParam Integer portalType) {

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();
		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);
		resultJson.setDataMap(dataMap);

		SysUserResponse sysUser = userService.findDetailByAccount(account);
		if (sysUser != null) {
			request.setAttribute(Constants.ACCOUNT, account);
			request.setAttribute(Constants.PORTAL_TYPE, portalType);
			dataMap.put(ResultKey.USER_INFO_KEY, sysUser);
			resultJson.setDataMap(dataMap);
		} else {
			resultJson.setResultCode(ResultCode.FAILURE);
			resultJson.setResultMsg(ResultMsg.RESULT_FAILURE);
		}
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
	}

	@ApiOperation(value = "更新用户", notes = "更新用户")
	@RequestMapping(value = "api/user/update", method = RequestMethod.POST)
	public ResponseEntity<String> updateSysUser(HttpServletRequest request,
												@RequestBody SysUserSaveReq req, @RequestParam String jsessionid) {

		SysUser cursysUser = (SysUser) redisUtil.get(jsessionid);
		String account = cursysUser.getAccount();

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);


		SysUserResponse sysUserResponse = userService.findDetailByAccount(req.getAccount());


		if (sysUserResponse == null) {
			//直接返回，提示当前账号已经被注册
			resultJson.setResultCode(ResultCode.FAILURE);
			resultJson.setResultMsg("当前账号不存在");
			resultJson.setDataMap(dataMap);
			return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
		} else {
			SysUser sysUser = new SysUser();
			SysUserExt sysUserExt = new SysUserExt();
			BeanUtils.copyProperties(sysUserResponse, sysUser);
			BeanUtils.copyProperties(sysUserResponse, sysUserExt);

			sysUser.setSex(req.getSex());
			sysUser.setName(req.getName());
			sysUser.setStatus(req.getStatus());


			sysUserExt.setAddress(req.getAddress());
			sysUserExt.setEmail(req.getEmail());
			sysUserExt.setIdno(req.getIdno());
			sysUserExt.setRemark(req.getRemark());
			sysUserExt.setMobilePhone(req.getMobilePhone());

			SysUserResponse response = userService.saveSysUser(sysUser, sysUserExt);

			// 用户操作日志
			request.setAttribute(Constants.ACCOUNT, account);
			request.setAttribute(Constants.PORTAL_TYPE, req.getPortalType());

			if (sysUser != null) {

				// 设置用户操作日志参数和内容
				String logContent = "用户【" + account + "】修改用户账号：【" + sysUser.getAccount()
						+ "】成功.";
				request.setAttribute(Constants.USER_LOG, logContent);
				dataMap.put(ResultKey.USER_INFO_KEY, response);
				resultJson.setDataMap(dataMap);
			} else {
				resultJson.setResultCode(ResultCode.FAILURE);
				resultJson.setResultMsg(ResultMsg.RESULT_FAILURE);
				resultJson.setDataMap(dataMap);
				return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
			}
		}
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
	}

	@ApiOperation(value = "用户授权", notes = "用户授权")
	@RequestMapping(value = "api/user/grant", method = RequestMethod.POST)
	public ResponseEntity<String> grant(HttpServletRequest request,
										@RequestBody UserRoleGrantReq req, @RequestParam String jsessionid) {

		SysUser cursysUser = (SysUser) redisUtil.get(jsessionid);
		String account = cursysUser.getAccount();

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		try {
			userService.grant(req, cursysUser.getId());
			request.setAttribute(Constants.ACCOUNT, account);
			request.setAttribute(Constants.PORTAL_TYPE, req.getPortalType());
			String logContent = "用户【" + account + "】修改用户授权信息：【" + req.getUserId()
					+ "】成功.";
			request.setAttribute(Constants.USER_LOG, logContent);
			resultJson.setDataMap(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			resultJson.setResultCode(ResultCode.FAILURE);
			resultJson.setResultMsg(ResultMsg.RESULT_FAILURE);
			resultJson.setDataMap(dataMap);
			return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
		}
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
	}


	@ApiOperation(value = "指纹登记", notes = "指纹登记")
	@RequestMapping(value = "api/user/fingerprint", method = RequestMethod.POST)
	public ResponseEntity<String> fingerprint(HttpServletRequest request,
											  @RequestParam String jsessionid, @RequestBody UserFingerprintReq req) {
		SysUser cursysUser = (SysUser) redisUtil.get(jsessionid);
		String account = cursysUser.getAccount();

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		UserFingerprintVerifyReq verifyReq=new UserFingerprintVerifyReq();
		verifyReq.setFpTemplate(req.getRegTemplate());

		SysUser sysUser = userService.fingerprintVerify(verifyReq);
		if (sysUser != null&&!sysUser.getId().equals(cursysUser.getId())) {
			resultJson.setResultCode(ResultCode.FAILURE);
			resultJson.setResultMsg("当前指纹已经绑定账号："+sysUser.getAccount()+"，指纹登记失败");
			resultJson.setDataMap(dataMap);
			return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
		}

		SysUserFingerprint sysUserFingerprint = new SysUserFingerprint();
		sysUserFingerprint.setId(cursysUser.getId());
		sysUserFingerprint.setRegTemplate(req.getRegTemplate());
		sysUserFingerprint.setFingerPosition(req.getFingerPosition());
		sysUserFingerprint = userService.fingerprint(sysUserFingerprint);

		if (sysUserFingerprint == null) {
			resultJson.setResultCode(ResultCode.FAILURE);
			resultJson.setResultMsg("指纹登记失败");
			resultJson.setDataMap(dataMap);
			return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
		} else {
			request.setAttribute(Constants.ACCOUNT, account);
			request.setAttribute(Constants.PORTAL_TYPE, req.getPortalType());
			String logContent = "用户【" + account + "】指纹登记成功.";
			request.setAttribute(Constants.USER_LOG, logContent);
			dataMap.put(ResultKey.USER_INFO_KEY, sysUserFingerprint);
			resultJson.setDataMap(dataMap);
		}
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
	}



	@ApiOperation(value = "获取指纹信息", notes = "获取当前人的指纹信息")
	@RequestMapping(value = "api/user/getfingerprint", method = RequestMethod.POST)
	public ResponseEntity<String> getfingerprint(HttpServletRequest request,
											  @RequestParam String jsessionid) {
		SysUser cursysUser = (SysUser) redisUtil.get(jsessionid);
		String account = cursysUser.getAccount();

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		SysUserFingerprint sysUserFingerprint = userService.getfingerprint(cursysUser.getId());

		if (sysUserFingerprint != null) {
			dataMap.put(ResultKey.SYS_USER_FINGERPRINT_KEY, sysUserFingerprint);
			resultJson.setDataMap(dataMap);
		}
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
	}



	@ApiOperation(value = "指纹比对", notes = "指纹比对")
	@RequestMapping(value = "login/user/fingerprint/verify", method = RequestMethod.POST)
	public ResponseEntity<String> verify(HttpServletRequest request,
										 @RequestBody UserFingerprintVerifyReq req) {


		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		SysUser sysUser = userService.fingerprintVerify(req);

		// 获取登录Session超时时间间隔键值参数
		String timeout = paramService.findValueByKey(Constants.JSESSIONID_TIMEOUT);
		String userLogin = paramService.findValueByKey(Constants.USER_LOGIN);

		// 初始化返回结果对象JsonResult

		if (sysUser != null) {

			try {
				// 不允许用户重复登录，则保存当前用户信息至缓存
				if (!userLogin.equals(Constants.ALLOW_LOGIN)) {
					// 获取当前用户登录会话
					Object dateStr = redisUtil.get(sysUser.getAccount());

					if (CommonUtil.isNotEmpty(dateStr)) {
						resultJson.setResultCode(ResultCode.USER_EXIST);
						resultJson.setResultMsg(ResultMsg.USER_EXIST);
						return new ResponseEntity<String>(JsonUtil.toJson(resultJson),
								HttpStatus.OK);
					} else {
						redisUtil.set(sysUser.getAccount(), DateTimeUtil.dateToString(new Date()),
								Long.valueOf(timeout));
					}
				}
				// Step.2 鉴权通过，设置session到缓存
				String uuid = GenUUId.uuid();
				// 缓存用户登录信息
				redisUtil.set(uuid, sysUser, Long.valueOf(timeout));

				resultJson.setJsessionid(uuid);
				// 根据用户和客户端类型设置权限清单
				userService.setUserAuth(req.getPortalType(), sysUser);

			} catch (RuntimeException e) {
				e.printStackTrace();
			}

			// 设置用户登录用户信息
			dataMap.put(ResultKey.USER_INFO_KEY, sysUser);

			// 返回登录结果信息和session
			resultJson.setDataMap(dataMap);

			// 设置用户操作日志参数和内容
			String logContent = "用户【" + sysUser.getAccount() + "】指纹登录成功.";
			request.setAttribute(Constants.USER_LOG, logContent);

			return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
					HttpStatus.OK);

		} else {

			// 设置用户操作日志内容
			String logContent = "指纹【" + req.getFpTemplate() + "】登录失败.";
			request.setAttribute(Constants.USER_LOG, logContent);

			resultJson.setResultCode(ResultCode.FAILURE);
			resultJson.setResultMsg(ResultMsg.RESULT_FAILURE);

			return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
		}

	}
}