package com.comtom.bc.server.req;

import java.util.Date;

/**
 * 文件信息参数对象
 * 
 * @author zhucanhui
 *
 */
public class FileQueryReq extends BaseReq {

	/**
	 * 文件Id
	 */
	private Long id;

	/**
	 * 文件目录Id
	 */
	private Long libraryId;

	/**
	 * 文件名
	 */
	private String fileName;

	/**
	 * 创建人员
	 */
	private String createUser;

	/**
	 * 开始日期
	 */
	private Date startDate;

	/**
	 * 结束日期
	 */
	private Date overDate;

	/**
	 * 文件类型
	 */
	private Integer fileType;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the libraryId
	 */
	public Long getLibraryId() {
		return libraryId;
	}

	/**
	 * @param libraryId
	 *            the libraryId to set
	 */
	public void setLibraryId(Long libraryId) {
		this.libraryId = libraryId;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the createUser
	 */
	public String getCreateUser() {
		return createUser;
	}

	/**
	 * @param createUser
	 *            the createUser to set
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * @return the fileType
	 */
	public Integer getFileType() {
		return fileType;
	}

	/**
	 * @param fileType
	 *            the fileType to set
	 */
	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the overDate
	 */
	public Date getOverDate() {
		return overDate;
	}

	/**
	 * @param overDate
	 *            the overDate to set
	 */
	public void setOverDate(Date overDate) {
		this.overDate = overDate;
	}

}
