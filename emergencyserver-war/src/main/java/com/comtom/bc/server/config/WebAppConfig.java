package com.comtom.bc.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.comtom.bc.server.intercetor.UserSecurityInterceptor;

/**
 * Web配置类
 *
 * @author zhucanhui
 *
 */
// @Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

	/**
	 * 配置拦截器
	 *
	 * @param registry
	 */
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new UserSecurityInterceptor()).addPathPatterns("/api/**");
	}

	/**
	 * 提前初始化加载，防止无法注入
	 *
	 * @return HandlerInterceptor
	 */
	@Bean
	public HandlerInterceptor getUserSecurityInterceptor() {
		return new UserSecurityInterceptor();
	}

}
