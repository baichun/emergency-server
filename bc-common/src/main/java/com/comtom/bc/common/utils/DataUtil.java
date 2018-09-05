package com.comtom.bc.common.utils;

/**
 * 数据格式处理工具
 * 
 * @author zhucanhui
 *
 */
public class DataUtil {

	/**
	 * 十六进制字符
	 */
	private static final String hexStr = "0123456789ABCDEF";

	/**
	 * 将byte[]转换为16进制字符串
	 * 
	 * @param b
	 * @return String
	 */
	public static String byteToHexString(byte[] b) {
		if (b == null)
			return null;
		StringBuffer sb = new StringBuffer(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(hexStr.charAt((b[i] & 0xf0) >> 4));
			sb.append(hexStr.charAt((b[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}

	/**
	 * 16进制字符串转换为byte[]
	 * 
	 * @param hex
	 * @return byte[]
	 */
	public static byte[] hexStringToByte(String hex) {
		if (hex == null)
			return null;
		int len = (hex.length() / 2);
		hex = hex.toUpperCase();
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}

	/**
	 * 转换为字节
	 * 
	 * @param c
	 * @return byte
	 */
	private static byte toByte(int c) {
		byte b = (byte) hexStr.indexOf(c);
		return b;
	}
}
