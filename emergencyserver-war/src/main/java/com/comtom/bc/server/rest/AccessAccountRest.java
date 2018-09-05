package com.comtom.bc.server.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comtom.bc.common.result.JsonResult;
import com.comtom.bc.common.result.ResultCode;
import com.comtom.bc.common.utils.JsonUtil;
import com.comtom.bc.server.asset.RedisUtil;
import com.comtom.bc.server.asset.RestRedisUtil;
import com.comtom.bc.server.asset.WebCxt;
import com.comtom.bc.server.service.ParamService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(value = "平台接入账号")
@RestController
@RequestMapping("/rest/accessAccount")
public class AccessAccountRest extends BaseRest{
	
	@Autowired
	ParamService paramService;

	@Autowired
	RedisUtil redisUtil;
	
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ApiOperation(value = "平台注销", notes = "平台注销",consumes="application/x-www-form-urlencoded")
	@ApiImplicitParam(name="account",paramType="form",dataType="string",value="账号")
	public ResponseEntity<String> logout(HttpServletRequest request,
			 String account) {
		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();
		String userAccount = getUserAccount(request);
		if(userAccount.equals(account)){
			// 清除缓存用户登录信息
			redisUtil.remove(RestRedisUtil.getAccessAccountKey(account));
			String token = getToken(request);
			redisUtil.remove(RestRedisUtil.getAccessTokenKey(token));
			WebCxt.newJsonMap(resultJson);
		}else{
			resultJson.setResultCode(ResultCode.FAILURE);
			resultJson.setResultMsg("非当前账号，不能注销");
		}
		return ResponseEntity.ok(JsonUtil.toJson(resultJson));
	}
	
	
}
