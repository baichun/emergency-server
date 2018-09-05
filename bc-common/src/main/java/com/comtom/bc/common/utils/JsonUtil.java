package com.comtom.bc.common.utils;

import java.lang.reflect.Type;
import java.util.List;

import org.springframework.util.StringUtils;

import com.comtom.bc.common.Constants;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * <b>JSON格式处理类</b>
 * 
 * @author zhucanhui
 */
public class JsonUtil {

	private static Gson gson;

	static {
		GsonBuilder builder = new GsonBuilder();
		gson = builder.create();
	}

	/**
	 * 将Java对象进行JSON序列化
	 * 
	 * @param pObject
	 * @return
	 */
	public static final String toJson(Object pObject) {
		String jsonString = "";

		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		Gson gson = builder.create();
		jsonString = gson.toJson(pObject);
		return jsonString;
	}
	
	/**
	 * 将Java对象进行JSON序列化
	 * <p>
	 * 支持自定义日期时间类型格式
	 * <p>
	 * 
	 * @param pObject
	 * @param pDateFormat
	 *            日期时间类型格式字符串
	 * @return
	 */
	public static final String toJson(Object pObject, String pDateFormat) {
		String jsonString = "";
		if (StringUtils.isEmpty(pDateFormat)) {
			pDateFormat = Constants.DATATIME;
		}
		GsonBuilder builder = new GsonBuilder();
		
		// 暂不设定输出模式，直接输入
		builder.setPrettyPrinting();
		builder.setDateFormat(pDateFormat);
		Gson gson = builder.create();
		jsonString = gson.toJson(pObject);
		return jsonString;
	}
	

	/**
	 * 将Json字符串转换为Java对象
	 * 
	 * @param json
	 * @param type
	 * @return
	 */
	public static final <T> T fromJson(String json, Type type) {
		T list = gson.fromJson(json, type);
		return (T) list;
	}

	/**
	 * 将json转换为List<Object>集合对象的简便方法
	 * 
	 * @param json
	 * @return
	 */
	@SuppressWarnings("serial")
	public static final List<Object> fromJson(String json) {
		List<Object> list = fromJson(json, new TypeToken<List<Object>>() {
		}.getType());
		return list;
	}
}