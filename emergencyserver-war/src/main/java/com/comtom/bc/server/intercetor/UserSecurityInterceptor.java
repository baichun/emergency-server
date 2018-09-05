package com.comtom.bc.server.intercetor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.result.JsonResult;
import com.comtom.bc.common.result.ResultCode;
import com.comtom.bc.common.result.ResultMsg;
import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.common.utils.JsonUtil;
import com.comtom.bc.server.asset.RedisUtil;
import com.comtom.bc.server.repository.entity.SysUser;
import com.comtom.bc.server.service.LogUserService;
import com.comtom.bc.server.service.ParamService;

/**
 * 提供用户安全验证拦截处理
 * 
 * @author zhucanhui
 *
 */
@Component
@Aspect
public class UserSecurityInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private ParamService paramService;

	@Autowired
	private LogUserService logUserService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {

		// 响应对象
		JsonResult<Object, Object> result = new JsonResult<Object, Object>();

		response.setCharacterEncoding(Constants.CHARACTER_ENCODING);
		response.setContentType(Constants.CONTENT_TYPE);

		String jsessionid = request.getParameter(Constants.JSESSIONID);

		// 获取登录Session超时时间间隔键值参数
		String timeout = paramService.findValueByKey(Constants.JSESSIONID_TIMEOUT);

		// 如果调用方没有传入鉴权session，提示没有权限
		if (CommonUtil.isEmpty(jsessionid)) {

			PrintWriter out = response.getWriter();

			result.setResultCode(ResultCode.AUTH_FAILURE);
			result.setResultMsg(ResultMsg.AUTH_FAILURE);
			out.append(JsonUtil.toJson(result));

			return false;
		}

		// 从请求参数中获取接口调用的jsessionid
		try {

			SysUser sysUser = (SysUser) redisUtil.get(jsessionid);

			if (sysUser == null) {
				PrintWriter out = response.getWriter();

				result.setResultCode(ResultCode.TIMEOUT);
				result.setResultMsg(ResultMsg.SESSION_TIMEOUT);
				out.append(JsonUtil.toJson(result));

				return false;
			} else {
				// 更新生存时间
				redisUtil.updateExpire(jsessionid, Long.valueOf(timeout));
				redisUtil.updateExpire(sysUser.getAccount(), Long.valueOf(timeout));
				request.setAttribute(Constants.ACCOUNT, sysUser.getAccount());
			}

		} catch (RuntimeException e) {
			e.printStackTrace();

			PrintWriter out = response.getWriter();

			result.setResultCode(ResultCode.SESSION_FAILURE);
			result.setResultMsg(ResultMsg.SESSION_FAILURE);
			out.append(JsonUtil.toJson(result));

			return false;
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception ex) throws Exception {
	}

}
