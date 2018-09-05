package com.comtom.bc.server.repository.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <b>bc_ebd_files[bc_ebd_files]数据持久化对象</b>
 * 
 * @author zhucanhui
 * @date 2016-12-20 17:03:37
 */
@Entity
@Table(name = "bc_ebd_files")
public class EbdFile {

	/**
	 * 业务数据包Id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer fileId;

	/**
	 * 关联数据包Id
	 */
	private String ebdId;

	/**
	 * 文件Url
	 */
	private String fileUrl;

	/**
	 * @return the fileId
	 */
	public Integer getFileId() {
		return fileId;
	}

	/**
	 * @param fileId
	 *            the fileId to set
	 */
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	/**
	 * @return the ebdId
	 */
	public String getEbdId() {
		return ebdId;
	}

	/**
	 * @param ebdId
	 *            the ebdId to set
	 */
	public void setEbdId(String ebdId) {
		this.ebdId = ebdId;
	}

	/**
	 * @return the fileUrl
	 */
	public String getFileUrl() {
		return fileUrl;
	}

	/**
	 * @param fileUrl
	 *            the fileUrl to set
	 */
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

}