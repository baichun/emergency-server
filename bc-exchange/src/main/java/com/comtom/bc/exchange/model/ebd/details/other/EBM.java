package com.comtom.bc.exchange.model.ebd.details.other;

import java.util.List;

import com.comtom.bc.exchange.model.ebd.ebm.Dispatch;
import com.comtom.bc.exchange.model.ebd.ebm.MsgBasicInfo;
import com.comtom.bc.exchange.model.ebd.ebm.MsgContent;
import com.comtom.bc.exchange.model.ebd.ebm.RelatedInfo;

/**
 * @author nobody
 * 应急广播消息
 */
public class EBM {
	
	/**
	 * 应急广播消息协议版本号 目前取值1
	 */
	private String EBMVersion;
	
	/**
	 * 应急广播消息ID
	 * 30位数字码，通过应急广播消息ID区别其他的应急广播消息。
	 * 编码规则：应急广播平台ID(18位)+日期（8位）+顺序码（4位），
	 * 日期格式为YYYYMMDD，YYYY表示年，MM表示月，DD表示日
	 */
	private String EBMID;
	
	
	/**
	 * 关联信息
	 */
	private RelatedInfo relatedInfo;
	
	/**
	 * 消息基本消息(可选)
	 */
	private MsgBasicInfo msgBasicInfo;
	
	/**
	 * 应急广播消息内容
	 */
	private MsgContent msgContent;

	/**
	 * 调度数据(可选)
	 */
	private List<Dispatch> dispatchList; 
	
	/**
	 * @return the eBMID
	 */
	public String getEBMID() {
		return EBMID;
	}

	/**
	 * @param eBMID the eBMID to set
	 */
	public void setEBMID(String eBMID) {
		EBMID = eBMID;
	}

	/**
	 * @return the 消息基本消息(可选)
	 */
	public MsgBasicInfo getMsgBasicInfo() {
		return msgBasicInfo;
	}

	/**
	 * @param 消息基本消息(可选) the msgBasicInfo to set
	 */
	public void setMsgBasicInfo(MsgBasicInfo msgBasicInfo) {
		this.msgBasicInfo = msgBasicInfo;
	}

	/**
	 * @return the 应急广播消息内容
	 */
	public MsgContent getMsgContent() {
		return msgContent;
	}

	/**
	 * @param 应急广播消息内容 the msgContent to set
	 */
	public void setMsgContent(MsgContent msgContent) {
		this.msgContent = msgContent;
	}

	/**
	 * @return the eBMVersion
	 */
	public String getEBMVersion() {
		return EBMVersion;
	}

	/**
	 * @param eBMVersion the eBMVersion to set
	 */
	public void setEBMVersion(String eBMVersion) {
		EBMVersion = eBMVersion;
	}

	/**
	 * @return the 关联信息
	 */
	public RelatedInfo getRelatedInfo() {
		return relatedInfo;
	}

	/**
	 * @param 关联信息 the relatedInfo to set
	 */
	public void setRelatedInfo(RelatedInfo relatedInfo) {
		this.relatedInfo = relatedInfo;
	}

	/**
	 * @return the 调度数据(可选)
	 */
	public List<Dispatch> getDispatchList() {
		return dispatchList;
	}

	/**
	 * @param 调度数据(可选) the dispathList to set
	 */
	public void setDispatchList(List<Dispatch> dispatchList) {
		this.dispatchList = dispatchList;
	}
	
}
