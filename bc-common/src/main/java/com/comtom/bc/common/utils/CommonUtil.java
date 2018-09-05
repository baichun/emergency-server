package com.comtom.bc.common.utils;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * 通用工具类
 * 
 * @author zhucanhui
 *
 */
public class CommonUtil {

	/**
	 * 判断对象是否Empty(null或元素为0)<br>
	 * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
	 * 
	 * @param pObj
	 *            待检查对象
	 * @return boolean 返回的布尔值
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object pObj) {
		if (pObj == null)
			return true;
		if (pObj == "")
			return true;
		if (pObj instanceof String) {
			if (((String) pObj).length() == 0) {
				return true;
			}
		} else if (pObj instanceof Collection) {
			if (((Collection) pObj).size() == 0) {
				return true;
			}
		} else if (pObj instanceof Map) {
			if (((Map) pObj).size() == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断对象是否为NotEmpty(!null或元素>0)<br>
	 * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
	 * 
	 * @param pObj
	 *            待检查对象
	 * @return boolean 返回的布尔值
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNotEmpty(Object pObj) {
		if (pObj == null)
			return false;
		if (pObj == "")
			return false;
		if (pObj instanceof String) {
			if (((String) pObj).length() == 0) {
				return false;
			}
		} else if (pObj instanceof Collection) {
			if (((Collection) pObj).size() == 0) {
				return false;
			}
		} else if (pObj instanceof Map) {
			if (((Map) pObj).size() == 0) {
				return false;
			}
		}
		return true;
	}
	
    /**
     * 比较字符串形式的时间，仅限HH:mm:SS格式
     * 
     * @param time1
     * @param time2
     * 
     */
	public static int compareTime(String time1, String time2) {
		String[] time1Strs = time1.split(":");
		String[] time2Strs = time2.split(":");
		
		Integer t1 = (Integer.valueOf(time1Strs[0]) * 100 + Integer.valueOf(time1Strs[1])) * 100 + Integer.valueOf(time1Strs[2]);
		Integer t2 = (Integer.valueOf(time2Strs[0]) * 100 + Integer.valueOf(time2Strs[1])) * 100 + Integer.valueOf(time2Strs[2]);
		
		return t1 - t2;
		
	}
	
	
	/**
	 * 增加或减少数分钟
	 * @author 
	 * @description
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date addMinute(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, n);
        return cal.getTime();
    }
	
	
	/**
	 * 增加或减少数秒
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date addSecond(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, n);
        return cal.getTime();
    }
	

}
