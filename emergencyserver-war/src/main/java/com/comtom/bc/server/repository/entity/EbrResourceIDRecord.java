package com.comtom.bc.server.repository.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <b>bc_ebr_resourceid_record[bc_ebr_resourceid_record]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author Kidsoul Qu
 * @date 2016-12-14 19:32:41
 */
@Entity
@Table(name="bc_ebr_resourceid_record")
public class EbrResourceIDRecord {

	/**
	 * 资源类型(资源类型和地址编码合成)
	 */
	@Id
	private String resourceType;
	
	/**
	 * 资源ID标识
	 */
	private String idrecord;
	

	/**
	 * 资源类型(资源类型和地址编码合成)
	 * 
	 * @return resourceType
	 */
	public String getResourceType() {
		return resourceType;
	}
	
	/**
	 * 资源ID标识
	 * 
	 * @return idrecord
	 */
	public String getIdrecord() {
		return idrecord;
	}
	

	/**
	 * 资源类型(资源类型和地址编码合成)
	 * 
	 * @param resourceType
	 */
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	
	/**
	 * 资源ID标识
	 * 
	 * @param idrecord
	 */
	public void setIdrecord(String idrecord) {
		this.idrecord = idrecord;
	}
}
