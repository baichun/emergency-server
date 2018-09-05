package com.comtom.bc.server.repository.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * <b>bc_ebm_res_bs[bc_ebm_res_bs]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author Kidsoul Qu
 * @date 2016-12-27 21:17:16
 */
@Entity
@Table(name = "bc_ebm_res_bs")
public class EbmResBs {

	/**
	 * 记录ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	/**
	 * 消息关联资源ID
	 */
	private String ebmResourceId;

	/**
	 * rptTime
	 */
	private Date rptTime;

	/**
	 * brdSysType
	 */
	private String brdSysType;

	/**
	 * brdSysInfo
	 */
	private String brdSysInfo;

	/**
	 * startTime
	 */
	private Date startTime;

	/**
	 * endTime
	 */
	private Date endTime;

	/**
	 * fileURL
	 */
	private String fileURL;

	/**
	 * brdStateCode
	 */
	private Integer brdStateCode;

	/**
	 * brdStateDesc
	 */
	private String brdStateDesc;

	/**
	 * 资源覆盖区域
	 */
	@Transient
	private String areaCode;

	/**
	 * 资源覆盖区域名称
	 */
	@Transient
	private String areaName;

	/**
	 * 记录ID
	 * 
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 消息关联资源ID
	 * 
	 * @return ebmResourceId
	 */
	public String getEbmResourceId() {
		return ebmResourceId;
	}

	/**
	 * rptTime
	 * 
	 * @return rptTime
	 */
	public Date getRptTime() {
		return rptTime;
	}

	/**
	 * brdSysType
	 * 
	 * @return brdSysType
	 */
	public String getBrdSysType() {
		return brdSysType;
	}

	/**
	 * brdSysInfo
	 * 
	 * @return brdSysInfo
	 */
	public String getBrdSysInfo() {
		return brdSysInfo;
	}

	/**
	 * startTime
	 * 
	 * @return startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * endTime
	 * 
	 * @return endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * fileURL
	 * 
	 * @return fileURL
	 */
	public String getFileURL() {
		return fileURL;
	}

	/**
	 * brdStateCode
	 * 
	 * @return brdStateCode
	 */
	public Integer getBrdStateCode() {
		return brdStateCode;
	}

	/**
	 * brdStateDesc
	 * 
	 * @return brdStateDesc
	 */
	public String getBrdStateDesc() {
		return brdStateDesc;
	}

	/**
	 * 记录ID
	 * 
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 消息关联资源ID
	 * 
	 * @param ebmResourceId
	 */
	public void setEbmResourceId(String ebmResourceId) {
		this.ebmResourceId = ebmResourceId;
	}

	/**
	 * rptTime
	 * 
	 * @param rptTime
	 */
	public void setRptTime(Date rptTime) {
		this.rptTime = rptTime;
	}

	/**
	 * brdSysType
	 * 
	 * @param brdSysType
	 */
	public void setBrdSysType(String brdSysType) {
		this.brdSysType = brdSysType;
	}

	/**
	 * brdSysInfo
	 * 
	 * @param brdSysInfo
	 */
	public void setBrdSysInfo(String brdSysInfo) {
		this.brdSysInfo = brdSysInfo;
	}

	/**
	 * startTime
	 * 
	 * @param startTime
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * endTime
	 * 
	 * @param endTime
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * fileURL
	 * 
	 * @param fileURL
	 */
	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
	}

	/**
	 * brdStateCode
	 * 
	 * @param brdStateCode
	 */
	public void setBrdStateCode(Integer brdStateCode) {
		this.brdStateCode = brdStateCode;
	}

	/**
	 * brdStateDesc
	 * 
	 * @param brdStateDesc
	 */
	public void setBrdStateDesc(String brdStateDesc) {
		this.brdStateDesc = brdStateDesc;
	}

	/**
	 * @return the areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * @param areaCode
	 *            the areaCode to set
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * @return the areaName
	 */
	public String getAreaName() {
		return areaName;
	}

	/**
	 * @param areaName
	 *            the areaName to set
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

}