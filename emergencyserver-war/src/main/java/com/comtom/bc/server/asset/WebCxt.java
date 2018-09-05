package com.comtom.bc.server.asset;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.result.JsonResult;
import com.comtom.bc.common.result.ResultCode;
import com.comtom.bc.common.result.ResultMsg;
import com.comtom.bc.common.utils.CommonUtil;

/**
 * <b>Web上下文</b>
 * 
 * @author admin
 */
public class WebCxt {

	public static void initPage(int pageNum, int limit) {

		if (pageNum <= 0) {
			pageNum = Constants.DEFAULT_PAGE_NUM;
		}

		if (limit <= 0) {
			limit = Constants.DEFAULT_LIMIT;
		}
	}

	/**
	 * 初始化结果对象
	 * 
	 */
	public static Map<String, Object> newJsonMap(JsonResult<String, Object> result) {
		Map<String, Object> map = new HashMap<String, Object>();

		result.setResultCode(ResultCode.SUCCESSFUL);
		result.setResultMsg(ResultMsg.RESULT_SUCCESS);
		result.setDataMap(map);
		return map;
	}

	/**
	 * 获取登录用户IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getClientIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-real-ip");
		if (CommonUtil.isEmpty(ip)) {
			ip = request.getHeader("X-Forward-For");
		}
		if (CommonUtil.isEmpty(ip) || StringUtils.equalsIgnoreCase("unknown", ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (CommonUtil.isEmpty(ip) || StringUtils.equalsIgnoreCase("unknown", ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (CommonUtil.isEmpty(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 获取本地地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getClientIpAddr() {
		return "127.0.0.1";
	}

}
