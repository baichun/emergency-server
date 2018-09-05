/**
 * 
 */
package com.comtom.bc.server.intercetor;

import io.swagger.annotations.ApiOperation;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.annotation.NoRecordLog;
import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.common.utils.DateUtil;
import com.comtom.bc.server.asset.WebCxt;
import com.comtom.bc.server.repository.entity.LogUser;
import com.comtom.bc.server.service.LogUserService;

/**
 * 用户操作日志拦截器<br>
 * 提供访问接口操作日志记录
 * 
 * @author zhucanhui
 *
 */
@Component
@Aspect
public class LogInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private LogUserService logUserService;

	/**
	 * 请求前拦截逻辑处理
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		return super.preHandle(request, response, handler);
	}

	/**
	 * 请求完成，记录用户请求操作日志
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView modelAndView) throws Exception {

		ApiOperation operation = null;
		NoRecordLog noRecordLog = null;

		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			operation = method.getAnnotation(ApiOperation.class);
			noRecordLog = method.getAnnotation(NoRecordLog.class);
		}

		// 需要记录日志
		if (noRecordLog == null) {
			if (operation != null) {

				// 获取API注解设置参数
				String value = operation.value();
				String notes = operation.notes();

				// 获取接口设置日志内容和请求参数
				String logContent = (String) request.getAttribute(Constants.USER_LOG);
				Object attribute = request.getAttribute(Constants.PORTAL_TYPE);
				Integer portalType=4;
				if(attribute!=null){
					portalType=(Integer)attribute;
				}

				String account = (String) request.getAttribute(Constants.ACCOUNT);

				LogUser logUser = new LogUser();
				logUser.setUserName(account);
				logUser.setModule(value);
				logUser.setOperation(notes);
				logUser.setLogTime(DateUtil.getDateTime(DateUtil.DATE_PATTERN.YYYY_MM_DD_HH_MM_SS));
				logUser.setContent(logContent);

				if (CommonUtil.isNotEmpty(portalType)) {
					logUser.setPortalType(Integer.valueOf(portalType));
				}

				logUser.setClientIp(WebCxt.getClientIpAddr(request));

				if (CommonUtil.isNotEmpty(logContent)) {
					// 记录用户操作日志
					logUserService.save(logUser);
				}
			}
		}

		super.postHandle(request, response, handler, modelAndView);
	}

	/**
	 * 请求完成，记录用户请求操作日志
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception ex) throws Exception {

		super.afterCompletion(request, response, handler, ex);
	}
}