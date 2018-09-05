package com.comtom.bc.server.repository.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * <b>bc_ebm_res[bc_ebm_res]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author Kidsoul Qu
 * @date 2016-12-27 21:17:16
 */
@Entity
@Table(name = "bc_ebm_res")
public class EbmRes {

	/**
	 * 消息资源ID
	 */
	@Id
	private String ebmResourceId;
	
	/**
	 * 关联记录ID
	 */
	private String brdItemId;
	
	/**
	 * 平台ID
	 */
	private String ebrpsId;
	
	/**
	 * 台站ID
	 */
	private String ebrstId;
	
	/**
	 * 接收设备ID
	 */
	private String ebrasId;
	

	/**
	 * 消息资源ID
	 * 
	 * @return ebmResourceId
	 */
	public String getEbmResourceId() {
		return ebmResourceId;
	}
		
	/**
	 * 平台ID
	 * 
	 * @return ebrpsId
	 */
	public String getEbrpsId() {
		return ebrpsId;
	}
	
	/**
	 * 台站ID
	 * 
	 * @return ebrstId
	 */
	public String getEbrstId() {
		return ebrstId;
	}
	
	/**
	 * 接收设备ID
	 * 
	 * @return ebrasId
	 */
	public String getEbrasId() {
		return ebrasId;
	}
	
	/**
	 * 消息资源ID
	 * 
	 * @param ebmResourceId
	 */
	public void setEbmResourceId(String ebmResourceId) {
		this.ebmResourceId = ebmResourceId;
	}
	
	/**
	 * @return the brdItemId
	 */
	public String getBrdItemId() {
		return brdItemId;
	}

	/**
	 * @param brdItemId the brdItemId to set
	 */
	public void setBrdItemId(String brdItemId) {
		this.brdItemId = brdItemId;
	}

	/**
	 * 平台ID
	 * 
	 * @param ebrpsId
	 */
	public void setEbrpsId(String ebrpsId) {
		this.ebrpsId = ebrpsId;
	}
	
	/**
	 * 台站ID
	 * 
	 * @param ebrstId
	 */
	public void setEbrstId(String ebrstId) {
		this.ebrstId = ebrstId;
	}
	
	/**
	 * 接收设备ID
	 * 
	 * @param ebrasId
	 */
	public void setEbrasId(String ebrasId) {
		this.ebrasId = ebrasId;
	}
	

}