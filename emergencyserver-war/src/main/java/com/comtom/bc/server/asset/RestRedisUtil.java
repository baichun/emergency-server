package com.comtom.bc.server.asset;

/**
 * rest模块在redis中工具类
 * @author comtom
 *
 */
public class RestRedisUtil {
	/**
	 * 平台接入账号_redis模板
	 */
	private static final String ACCESS_ACCOUNT_KEY_FORMAT = "accessAccount:%s";
	
	/**
	 * 平台接入令牌_redis模板
	 */
	private static final String ACCESS_TOKEN_KEY_FORMAT = "accessToken:%s";
	
	/** 获取账号在redis中的KEY
	 * @param account
	 * @return
	 */
	public static String getAccessAccountKey(String account){
		return String.format(ACCESS_ACCOUNT_KEY_FORMAT,account);
	}
	
	
	/** 获取令牌在redis中的KEY
	 * @param account
	 * @return
	 */
	public static String getAccessTokenKey(String token){
		return String.format(ACCESS_TOKEN_KEY_FORMAT,token);
	}
}
