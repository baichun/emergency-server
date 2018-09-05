package com.comtom.bc.server.rest;

import javax.servlet.http.HttpServletRequest;

import com.comtom.bc.common.Constants;

/**
 * rest接口基础功能
 * @author comtom
 *
 */
public class BaseRest {
	
	protected String getUserAccount(HttpServletRequest request) {
		return  (String)request.getAttribute(Constants.ACCESS_ACCOUNT);
	}
	
	protected String getToken(HttpServletRequest request) {
		return (String)request.getAttribute(Constants.ACCESS_TOKEN);
	}
	

}
