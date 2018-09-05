package com.comtom.bc.exchange.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.comtom.bc.exchange.commom.EBDRespResultEnum;
import com.comtom.bc.exchange.excepion.EbmException;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.signature.Signature;

/**
 * @author nobody 文件工具类
 */
public class FileUtil {

	/**
	 * EBD将对象转换为文件
	 * 
	 * @param filePath
	 * @param ebd
	 * @return
	 */
	public static File converFile(String filePath, EBD ebd) {
		if (ebd == null) {
			throw new EbmException(EBDRespResultEnum.notprocessed, "ebd对象为空");
		}
		File tarDir = new File(filePath);
		if (!tarDir.exists()) {
			tarDir.mkdirs();
		}
		String xml = XmlUtil.toXml(ebd);
		xml = XmlUtil.xmlPre + System.getProperty(("line.separator")) + xml;
		String fileName = FileNameUtil.createEBDBName(ebd.getEBDID());
		File flie = new File(filePath + File.separator + fileName);
		FileOutputStream out = null;
		if (!flie.exists()) {
			try {
				flie.createNewFile();
				out = new FileOutputStream(flie);
				IOUtils.write(xml, out, "utf-8");
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				IOUtils.closeQuietly(out);
			}
		}
		return flie;
	}

	/**
	 * Signature将对象转换为文件
	 * 
	 * @param filePath
	 * @param signature
	 * @return
	 */
	public static File converFile(String filePath, Signature signature) {
		if (signature == null) {
			throw new EbmException(EBDRespResultEnum.notprocessed, "signature对象为空");
		}
		File tarDir = new File(filePath);
		if (!tarDir.exists()) {
			tarDir.mkdirs();
		}
		String xml = XmlUtil.toSignXml(signature);
		xml = XmlUtil.xmlPre + System.getProperty(("line.separator")) + xml;
		String fileName = FileNameUtil.createEBDSName(signature.getRelatedEBD().getEBDID());
		File flie = new File(filePath + File.separator + fileName);
		FileOutputStream out = null;
		if (!flie.exists()) {
			try {
				flie.createNewFile();
				out = new FileOutputStream(flie);
				IOUtils.write(xml, out, "utf-8");
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				IOUtils.closeQuietly(out);
			}
		}
		return flie;
	}

	/**
	 * 将字符串保存文件
	 * 
	 * @param filePath
	 * @param fileName
	 * @param fileInfo
	 * @return
	 */
	public static File converFile(String filePath, String fileName, String fileInfo) {
		File tarDir = new File(filePath);
		if (!tarDir.exists()) {
			tarDir.mkdirs();
		}
		File flie = new File(filePath + File.separator + fileName);
		FileOutputStream out = null;
		if (!flie.exists()) {
			try {
				flie.createNewFile();
				out = new FileOutputStream(flie);
				IOUtils.write(fileInfo, out, "utf-8");
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				IOUtils.closeQuietly(out);
			}
		}
		return flie;
	}

	/**
	 * 将输入流保存
	 * 
	 * @param filePath
	 * @param fileName
	 * @param inputStream
	 * @return
	 */
	public static File converFile(String filePath, String fileName, InputStream inputStream) {
		File tarDir = new File(filePath);
		if (!tarDir.exists()) {
			tarDir.mkdirs();
		}
		File flie = new File(filePath + File.separator + fileName);
		FileOutputStream out = null;
		if (!flie.exists()) {
			try {
				flie.createNewFile();
				out = new FileOutputStream(flie);
				BufferedInputStream bin = new BufferedInputStream(inputStream);
				int size = 0;
				byte[] buf = new byte[1024];
				while ((size = bin.read(buf)) != -1) {
					out.write(buf, 0, size);
				}
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				IOUtils.closeQuietly(out);
			}
		}
		return flie;
	}

}
