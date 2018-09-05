package com.comtom.bc.exchange.model.ebd.details.info;

import java.util.List;

import com.comtom.bc.exchange.model.ebd.commom.EBRBS;
import com.comtom.bc.exchange.model.ebd.commom.Params;

/**
 * @author nobody
 * 播出系统上报信息
 */
public class EBRBSInfo {
	
	/**
	 * 附加参数说明(可选)
	 */
	private Params params;
	
	/**
	 * 播出系统信息，可为多个
	 * (brdSysType,brdSysInfo,startTime,endTime,fileURL,
	 * brdStateCode,brdStateDesc,stateCode,stateDesc 不需要设置)
	 */
	private List<EBRBS> dataList;

	/**
	 * @return the 附加参数说明(可选)
	 */
	public Params getParams() {
		return params;
	}

	/**
	 * @param 附加参数说明(可选) the params to set
	 */
	public void setParams(Params params) {
		this.params = params;
	}

	/**
	 * @return the 播出系统信息，可为多个
	 * (brdSysType,brdSysInfo,startTime,endTime,fileURL,
	 * brdStateCode,brdStateDesc,stateCode,stateDesc 不需要设置)
	 */
	public List<EBRBS> getDataList() {
		return dataList;
	}

	/**
	 * @param 播出系统信息，可为多个 the dataList to set
	 * (brdSysType,brdSysInfo,startTime,endTime,fileURL,
	 * brdStateCode,brdStateDesc,stateCode,stateDesc 不需要设置)
	 */
	public void setDataList(List<EBRBS> dataList) {
		this.dataList = dataList;
	}
	
}
