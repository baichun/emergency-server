package com.comtom.bc.server.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.result.JsonResult;
import com.comtom.bc.common.result.ResultCode;
import com.comtom.bc.common.result.ResultMsg;
import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.common.utils.GenUUId;
import com.comtom.bc.common.utils.JsonUtil;
import com.comtom.bc.server.asset.RedisUtil;
import com.comtom.bc.server.asset.RestRedisUtil;
import com.comtom.bc.server.asset.WebCxt;
import com.comtom.bc.server.repository.entity.AccessAccount;
import com.comtom.bc.server.rest.BaseRest;
import com.comtom.bc.server.service.AccessAccountService;
import com.comtom.bc.server.service.ParamService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "平台接入账号")
@RestController
@RequestMapping("")
public class AccessAccountController extends BaseRest{
	
	@Autowired
	private AccessAccountService accessAccountService;
	
	@Autowired
	ParamService paramService;

	@Autowired
	RedisUtil redisUtil;
	
	@RequestMapping(value = "/api/accessAccount", method = RequestMethod.POST)
	@ApiOperation(value = "保存平台接入账号", notes = "保存平台接入账号")
	public ResponseEntity<String> save(HttpServletRequest request,
			@RequestBody AccessAccount account,@RequestParam String jsessionid) {
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();
		if(account!=null){
			if(StringUtils.isBlank(account.getAccount())){
				resultJson.setResultCode(ResultCode.FAILURE);
				resultJson.setResultMsg("账号不能为空");
				return ResponseEntity.ok(JsonUtil.toJson(resultJson));
			}
			if(StringUtils.isBlank(account.getPassword())){
				resultJson.setResultCode(ResultCode.FAILURE);
				resultJson.setResultMsg("密码不能为空");
				return ResponseEntity.ok(JsonUtil.toJson(resultJson));
			}
			if(accessAccountService.validateExists(account.getAccount())){
				resultJson.setResultCode(ResultCode.FAILURE);
				resultJson.setResultMsg(ResultMsg.ACCOUNT_DUPLICATE);
				return ResponseEntity.ok(JsonUtil.toJson(resultJson));
			}
			account.setAccount(account.getAccount().trim());
			accessAccountService.save(account);
			Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);
			dataMap.put(Constants.ACCOUNT, account.getAccount());
			return ResponseEntity.ok(JsonUtil.toJson(resultJson));
		}else{
			resultJson.setResultCode(ResultCode.FAILURE);
			resultJson.setResultMsg("没有账号信息");
			return ResponseEntity.ok(JsonUtil.toJson(resultJson));
		}
	}
	
	@RequestMapping(value = "/accessAccount/login", method = RequestMethod.POST)
	@ApiOperation(value = "平台登录", notes = "平台登录",consumes="application/json, application/xml,application/x-www-form-urlencoded")
	@ApiImplicitParams({@ApiImplicitParam(name="account",paramType="form",dataType="string",value="账号"),
						@ApiImplicitParam(name="password",paramType="form",dataType="string",value="密码")})
	public ResponseEntity<String> login(HttpServletRequest request,
			String account, String password) {
		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();
		if(StringUtils.isBlank(account)){
			resultJson.setResultCode(ResultCode.FAILURE);
			resultJson.setResultMsg("账号不能为空");
			return ResponseEntity.ok(JsonUtil.toJson(resultJson));
		}
		if(StringUtils.isBlank(password)){
			resultJson.setResultCode(ResultCode.FAILURE);
			resultJson.setResultMsg("密码不能为空");
			return ResponseEntity.ok(JsonUtil.toJson(resultJson));
		}
		if(accessAccountService.checkPassword(account, password)){
			// 获取登录Session超时时间间隔键值参数
			String timeout = paramService.findValueByKey(Constants.JSESSIONID_TIMEOUT);
			String userLogin = paramService.findValueByKey(Constants.USER_LOGIN);
			String token = GenUUId.uuid();
			// 不允许用户重复登录，则保存当前用户信息至缓存
			if (!userLogin.equals(Constants.ALLOW_LOGIN)) {
				String accountKey = RestRedisUtil.getAccessAccountKey(account);
				// 获取当前用户登录会话
				Object _uuid = redisUtil.get(accountKey);
				if (CommonUtil.isNotEmpty(_uuid)) {
					token =(String) _uuid;
				}
				redisUtil.set(accountKey, token,
						Long.valueOf(timeout));
			}
			// Step.2 鉴权通过，设置session到缓存
			String tokenKey = RestRedisUtil.getAccessTokenKey(token);
			redisUtil.set(tokenKey, account, Long.valueOf(timeout));
			Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);
			dataMap.put(Constants.ACCESS_TOKEN, token);
			return ResponseEntity.ok(JsonUtil.toJson(resultJson));
		}else{
			resultJson.setResultCode(ResultCode.FAILURE);
			resultJson.setResultMsg("用户名或密码错误");
		}
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
	}
	
}
