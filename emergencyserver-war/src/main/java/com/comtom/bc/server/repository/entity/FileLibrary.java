package com.comtom.bc.server.repository.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <b>bc_file_library[bc_file_library]数据持久化对象</b>
 * 
 * @author zhucanhui
 */
@Entity
@Table(name = "bc_file_library")
public class FileLibrary {

	/**
	 * 文件库ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long libId;

	/**
	 * 文件库名称
	 */
	private String libName;

	/**
	 * 文件库地址
	 */
	private String libURI;

	/**
	 * 创建人
	 */
	private String createUser;

	/**
	 * 文件库创建时间
	 */
	private Date createDate;

	/**
	 * 父文件库ID
	 */
	private Integer parentLibId;

	/**
	 * 文件库ID
	 * 
	 * @return libId
	 */
	public Long getLibId() {
		return libId;
	}

	/**
	 * 文件库名称
	 * 
	 * @return libName
	 */
	public String getLibName() {
		return libName;
	}

	/**
	 * 文件库地址
	 * 
	 * @return libURI
	 */
	public String getLibURI() {
		return libURI;
	}

	/**
	 * 创建人
	 * 
	 * @return createUser
	 */
	public String getCreateUser() {
		return createUser;
	}

	/**
	 * 文件库创建时间
	 * 
	 * @return createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * 父文件库ID
	 * 
	 * @return parentLibId
	 */
	public Integer getParentLibId() {
		return parentLibId;
	}

	/**
	 * 文件库ID
	 * 
	 * @param libId
	 */
	public void setLibId(Long libId) {
		this.libId = libId;
	}

	/**
	 * 文件库名称
	 * 
	 * @param libName
	 */
	public void setLibName(String libName) {
		this.libName = libName;
	}

	/**
	 * 文件库地址
	 * 
	 * @param libURI
	 */
	public void setLibURI(String libURI) {
		this.libURI = libURI;
	}

	/**
	 * 创建人
	 * 
	 * @param createUser
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * 文件库创建时间
	 * 
	 * @param createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 父文件库ID
	 * 
	 * @param parentLibId
	 */
	public void setParentLibId(Integer parentLibId) {
		this.parentLibId = parentLibId;
	}

}