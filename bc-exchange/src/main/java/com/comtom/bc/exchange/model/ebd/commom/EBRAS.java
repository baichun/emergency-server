package com.comtom.bc.exchange.model.ebd.commom;

import java.util.List;


/**
 * @author nobody
 * 接收设备
 */
public class EBRAS {
	
	/**
	 * 上报时间 YYYY--MM--DD HH:MI:SS
	 */
	private String rptTime;
	
	/**
	 * 数据操作类型
	 * Sync: 同步（新增、更新）Delete:删除(暂不使用) 
	 */
	private String rptType;
	
	/**
	 * 联应急广播平台，指所属的应急广播平台(可选)
	 */
	private RelatedEBRPS relatedEBRPS;

	/**
	 * 关联台站(可选)
	 */
	private RelatedEBRST relatedEBRST;
		
	/**
	 * 接收设备ID:18位资源编码
	 */
	private String EBRID;
	
	/**
	 * 接收设备名称
	 */
	private String EBRName;
	
	/**
	 * 经度 
	 */
	private String longitude;
	
	/**
	 * 纬度
	 */
	private String latitude;
	
	/**
	 * 网络地址(可选)
	 */
	private String URL;
	
	/**
	 * 扩展参数，自定义，平台联动可不解析(可选)
	 */
	private List<String> params;

	/**
	 * 状态代码
	 * 1:开机 ;2关机;3故障;4故障恢复
	 */
	private Integer stateCode;
	
	/**状态描述*/
	private String stateDesc;	
	
	/**
	 * @return the 上报时间YYYY--MM--DDHH:MI:SS
	 */
	public String getRptTime() {
		return rptTime;
	}

	/**
	 * @param 上报时间YYYY--MM--DDHH:MI:SS the rptTime to set
	 */
	public void setRptTime(String rptTime) {
		this.rptTime = rptTime;
	}

	/**
	 * @return the 数据操作类型Sync:同步（新增、更新）Delete:删除(暂不使用)
	 */
	public String getRptType() {
		return rptType;
	}

	/**
	 * @param 数据操作类型Sync:同步（新增、更新）Delete:删除(暂不使用) the rptType to set
	 */
	public void setRptType(String rptType) {
		this.rptType = rptType;
	}

	/**
	 * @return the 联应急广播平台，指所属的应急广播平台(可选)
	 */
	public RelatedEBRPS getRelatedEBRPS() {
		return relatedEBRPS;
	}

	/**
	 * @param 联应急广播平台，指所属的应急广播平台(可选) the relatedEBRPS to set
	 */
	public void setRelatedEBRPS(RelatedEBRPS relatedEBRPS) {
		this.relatedEBRPS = relatedEBRPS;
	}

	/**
	 * @return the 关联台站(可选)
	 */
	public RelatedEBRST getRelatedEBRST() {
		return relatedEBRST;
	}

	/**
	 * @param 关联台站(可选) the relatedEBRST to set
	 */
	public void setRelatedEBRST(RelatedEBRST relatedEBRST) {
		this.relatedEBRST = relatedEBRST;
	}

	/**
	 * @return the eBRID
	 */
	public String getEBRID() {
		return EBRID;
	}

	/**
	 * @param eBRID the eBRID to set
	 */
	public void setEBRID(String eBRID) {
		EBRID = eBRID;
	}

	/**
	 * @return the eBRName
	 */
	public String getEBRName() {
		return EBRName;
	}

	/**
	 * @param eBRName the eBRName to set
	 */
	public void setEBRName(String eBRName) {
		EBRName = eBRName;
	}

	/**
	 * @return the 经度
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @param 经度 the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the 纬度
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @param 纬度 the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the uRL
	 */
	public String getURL() {
		return URL;
	}

	/**
	 * @param uRL the uRL to set
	 */
	public void setURL(String uRL) {
		URL = uRL;
	}

	/**
	 * @return the 扩展参数，自定义，平台联动可不解析(可选)
	 */
	public List<String> getParams() {
		return params;
	}

	/**
	 * @param 扩展参数，自定义，平台联动可不解析(可选) the params to set
	 */
	public void setParams(List<String> params) {
		this.params = params;
	}

	/**
	 * @return the 状态代码1:开机;2关机;3故障;4故障恢复
	 */
	public Integer getStateCode() {
		return stateCode;
	}

	/**
	 * @param 状态代码1:开机;2关机;3故障;4故障恢复 the stateCode to set
	 */
	public void setStateCode(Integer stateCode) {
		this.stateCode = stateCode;
	}

	/**
	 * @return the 状态描述
	 */
	public String getStateDesc() {
		return stateDesc;
	}

	/**
	 * @param 状态描述 the stateDesc to set
	 */
	public void setStateDesc(String stateDesc) {
		this.stateDesc = stateDesc;
	}
	
}
