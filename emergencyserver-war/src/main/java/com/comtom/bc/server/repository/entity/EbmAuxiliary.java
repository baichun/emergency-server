package com.comtom.bc.server.repository.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <b>bc_ebm_auxiliary[bc_ebm_auxiliary]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author Kidsoul Qu
 * @date 2016-12-23 13:40:26
 */
@Entity
@Table(name = "bc_ebm_auxiliary")
public class EbmAuxiliary {

	/**
	 * auxiliaryId
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer auxiliaryId;

	/**
	 * 辅助数据类型
	 */
	private Integer auxiliaryType;

	/**
	 * 辅助数据描述
	 */
	private String auxiliaryDesc;

	/**
	 * 辅助数据文件大小
	 */
	private Integer auxiliarySize;

	/**
	 * 辅助数据文件摘要
	 */
	private String auxiliaryDigest;

	/**
	 * 消息ID
	 */
	private String ebmId;

	/**
	 * auxiliaryId
	 * 
	 * @return auxiliaryId
	 */
	public Integer getAuxiliaryId() {
		return auxiliaryId;
	}

	/**
	 * 辅助数据类型
	 * 
	 * @return auxiliaryType
	 */
	public Integer getAuxiliaryType() {
		return auxiliaryType;
	}

	/**
	 * 辅助数据描述
	 * 
	 * @return auxiliaryDesc
	 */
	public String getAuxiliaryDesc() {
		return auxiliaryDesc;
	}

	/**
	 * 辅助数据文件大小
	 * 
	 * @return auxiliarySize
	 */
	public Integer getAuxiliarySize() {
		return auxiliarySize;
	}

	/**
	 * 辅助数据文件摘要
	 * 
	 * @return auxiliaryDigest
	 */
	public String getAuxiliaryDigest() {
		return auxiliaryDigest;
	}

	/**
	 * 消息ID
	 * 
	 * @return ebmId
	 */
	public String getEbmId() {
		return ebmId;
	}

	/**
	 * auxiliaryId
	 * 
	 * @param auxiliaryId
	 */
	public void setAuxiliaryId(Integer auxiliaryId) {
		this.auxiliaryId = auxiliaryId;
	}

	/**
	 * 辅助数据类型
	 * 
	 * @param auxiliaryType
	 */
	public void setAuxiliaryType(Integer auxiliaryType) {
		this.auxiliaryType = auxiliaryType;
	}

	/**
	 * 辅助数据描述
	 * 
	 * @param auxiliaryDesc
	 */
	public void setAuxiliaryDesc(String auxiliaryDesc) {
		this.auxiliaryDesc = auxiliaryDesc;
	}

	/**
	 * 辅助数据文件大小
	 * 
	 * @param auxiliarySize
	 */
	public void setAuxiliarySize(Integer auxiliarySize) {
		this.auxiliarySize = auxiliarySize;
	}

	/**
	 * 辅助数据文件摘要
	 * 
	 * @param auxiliaryDigest
	 */
	public void setAuxiliaryDigest(String auxiliaryDigest) {
		this.auxiliaryDigest = auxiliaryDigest;
	}

	/**
	 * 消息ID
	 * 
	 * @param ebmId
	 */
	public void setEbmId(String ebmId) {
		this.ebmId = ebmId;
	}

}