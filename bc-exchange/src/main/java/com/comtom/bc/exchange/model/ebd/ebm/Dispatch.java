package com.comtom.bc.exchange.model.ebd.ebm;

import com.comtom.bc.exchange.model.ebd.commom.EBRAS;
import com.comtom.bc.exchange.model.ebd.commom.EBRPS;
import com.comtom.bc.exchange.model.ebd.commom.EBRBS;

/**
 * @author nobody
 * 调用资源数据
 */
public class Dispatch {
	
	/**
	 * 语种代码
	 */
	private String languageCode;
	
	/**
	 * 应急广播平台(只需要传递ID)
	 */
	private EBRPS EBRPS;
	
	/**
	 * 消息接收设备(只需要传递ID)
	 */
	private EBRAS EBRAS;
	
	/**
	 * 调用播出系统信息(可选)
	 * 只需要设置brdSysType和brdSysInfo信息
	 */
	private EBRBS EBRBS;
	
	/**
	 * @return the languageCode
	 */
	public String getLanguageCode() {
		return languageCode;
	}

	/**
	 * @param languageCode the languageCode to set
	 */
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	/**
	 * @return the eBRPS (只需要传递ID)
	 */
	public EBRPS getEBRPS() {
		return EBRPS;
	}

	/**
	 * @param eBRPS(只需要传递ID) the eBRPS to set
	 */
	public void setEBRPS(EBRPS eBRPS) {
		EBRPS = eBRPS;
	}

	/**
	 * @return the eBRAS(只需要传递ID)
	 */
	public EBRAS getEBRAS() {
		return EBRAS;
	}

	/**
	 * @param eBRAS(只需要传递ID) the eBRAS to set
	 */
	public void setEBRAS(EBRAS eBRAS) {
		EBRAS = eBRAS;
	}

	/**
	 * @return the eBRBS
	 * 只需要设置brdSysType和brdSysInfo信息
	 */
	public EBRBS getEBRBS() {
		return EBRBS;
	}

	/**
	 * @param eBRBS the eBRBS to set
	 * 只需要设置brdSysType和brdSysInfo信息
	 */
	public void setEBRBS(EBRBS eBRBS) {
		EBRBS = eBRBS;
	}
	
	
}
