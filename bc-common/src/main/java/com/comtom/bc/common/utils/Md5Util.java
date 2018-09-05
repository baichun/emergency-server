package com.comtom.bc.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.AccessController;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivilegedAction;

/**
 * Md5处理工具类
 * 
 * @author zhucanhui
 *
 */
public class Md5Util {

	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
			'b', 'c', 'd', 'e', 'f' };

	protected static MessageDigest messagedigest = null;
	static {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
		}
	}

	/**
	 * 获取文件的MD5值
	 * 
	 * @param filePath
	 * @return String
	 */
	public static String getMd5ByFile(String filePath) {
		return getMd5ByFile(new File(filePath));
	}

	/**
	 * 获取文件的MD5值
	 * 
	 * @return String
	 */
	@SuppressWarnings("restriction")
	public static String getMd5ByFile(File f) {

		FileInputStream fis = null;

		try {
			fis = new FileInputStream(f);
			MappedByteBuffer byteBuffer = fis.getChannel().map(FileChannel.MapMode.READ_ONLY, 0,
					f.length());
			byte[] bytes = null;
			synchronized (messagedigest) {
				messagedigest.reset();
				messagedigest.update(byteBuffer);
				bytes = messagedigest.digest();
			}
			
			try {
				clean((sun.nio.ch.DirectBuffer)byteBuffer);
			} catch (Exception e) {
				e.printStackTrace();
			}
			fis.close();
			
			return bufferToHex(bytes, 0, bytes.length);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
		
	}
	
	

	/**
	 * @param bytes
	 * @param m 起始索引
	 * @param n 长度
	 * @return
	 */
	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];// 取字节中高 4 位的数字转换
		char c1 = hexDigits[bt & 0xf];// 取字节中低 4 位的数字转换
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}
	
	/** 获取md5值
	 * @param bytes
	 * @return
	 */
	public static byte[] getMd5Bytes(byte[] bytes) {
		synchronized (messagedigest) {
			messagedigest.reset();
			messagedigest.update(bytes);
			return messagedigest.digest();
		}
	}
	
	
	/** 获取Hex形式md5值
	 * @param bytes
	 * @return
	 */
	public static String getMd5Hex(byte[] bytes) {
		byte[] md5 = getMd5Bytes(bytes);
		return bufferToHex(md5, 0, md5.length);
	}
	
	/** 释放文件占用
	 * @param buffer
	 * @throws Exception
	 */
	public static void clean(@SuppressWarnings("restriction") final sun.nio.ch.DirectBuffer buffer) throws Exception {
		AccessController.doPrivileged(new PrivilegedAction<Buffer>() {
			@SuppressWarnings("restriction")
			public Buffer run() {
				try {
					Method getCleanerMethod = buffer.getClass().getMethod("cleaner", new Class[0]);
					getCleanerMethod.setAccessible(true);
					sun.misc.Cleaner cleaner = (sun.misc.Cleaner) getCleanerMethod.invoke(buffer, new Object[0]);
					cleaner.clean();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	 }

}
