package com.comtom.bc.server.repository.entity;

import java.util.Date;

import javax.persistence.*;

/**
 * <b>bc_file_info[bc_file_info]数据持久化对象</b>
 * 
 * @author zhucanhui
 */
@Entity
@Table(name = "bc_file_info")
public class FileInfo {

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 原始文件名
	 */
	private String originName;

	/**
	 * 上传后的文件名
	 */
	private String uploadedName;

	/**
	 * 文件类型 1-媒体文件 2-文本文件
	 */
	private Integer fileType;

	/**
	 * 文件后缀名
	 */
	private String fileExt;

	/**
	 * 文件MD5值
	 */
	private String md5Code;

	/**
	 * 文件播放时长，单位毫秒
	 */
	private Integer secondLength;
	/**
	 * 文件播放时长，文本描述
	 */
	private String durationTxt;

	/**
	 * 文件大小，单位字节
	 */
	private Integer byteSize;

	/**
	 * 文件大小，文本描述
	 */
	private String byteSizeTxt;

	/**
	 * 文件夹ID
	 */
	private Long libId;

	/**
	 * 文件夹ID
	 */
	@Transient
	private FileLibrary library;
	/**
	 * 文本广播内容
	 */
	private String fileText;

	/**
	 * 文件描述
	 */
	private String fileDesc;

	/**
	 * 创建人
	 */
	private String createUser;

	/**
	 * 文件创建日期
	 */
	private Date createDate;

	/**
	 * 审核状态 0-未审核 1-审核通过 2-审核未通过
	 */
	private Integer auditState;

	/**
	 * 审核意见
	 */
	private String auditComment;

	/**
	 * 审核时间
	 */
	private Date auditDate;

	/**
	 * 审核人
	 */
	private String auditUser;

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
	 * @return the originName
	 */
	public String getOriginName() {
		return originName;
	}

	/**
	 * @param originName
	 *            the originName to set
	 */
	public void setOriginName(String originName) {
		this.originName = originName;
	}

	/**
	 * @return the uploadedName
	 */
	public String getUploadedName() {
		return uploadedName;
	}

	/**
	 * @param uploadedName
	 *            the uploadedName to set
	 */
	public void setUploadedName(String uploadedName) {
		this.uploadedName = uploadedName;
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
	 * @return the fileExt
	 */
	public String getFileExt() {
		return fileExt;
	}

	/**
	 * @param fileExt
	 *            the fileExt to set
	 */
	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	/**
	 * @return the md5Code
	 */
	public String getMd5Code() {
		return md5Code;
	}

	/**
	 * @param md5Code
	 *            the md5Code to set
	 */
	public void setMd5Code(String md5Code) {
		this.md5Code = md5Code;
	}

	/**
	 * @return the secondLength
	 */
	public Integer getSecondLength() {
		return secondLength;
	}

	/**
	 * @param secondLength
	 *            the secondLength to set
	 */
	public void setSecondLength(Integer secondLength) {
		this.secondLength = secondLength;
	}

	/**
	 * @return the byteSize
	 */
	public Integer getByteSize() {
		return byteSize;
	}

	/**
	 * @param byteSize
	 *            the byteSize to set
	 */
	public void setByteSize(Integer byteSize) {
		this.byteSize = byteSize;
	}

	/**
	 * @return the libId
	 */
	public Long getLibId() {
		return libId;
	}

	/**
	 * @param libId
	 *            the libId to set
	 */
	public void setLibId(Long libId) {
		this.libId = libId;
	}

	/**
	 * @return the fileText
	 */
	public String getFileText() {
		return fileText;
	}

	/**
	 * @param fileText
	 *            the fileText to set
	 */
	public void setFileText(String fileText) {
		this.fileText = fileText;
	}

	/**
	 * @return the fileDesc
	 */
	public String getFileDesc() {
		return fileDesc;
	}

	/**
	 * @param fileDesc
	 *            the fileDesc to set
	 */
	public void setFileDesc(String fileDesc) {
		this.fileDesc = fileDesc;
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
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the auditState
	 */
	public Integer getAuditState() {
		return auditState;
	}

	/**
	 * @param auditState
	 *            the auditState to set
	 */
	public void setAuditState(Integer auditState) {
		this.auditState = auditState;
	}

	/**
	 * @return the auditComment
	 */
	public String getAuditComment() {
		return auditComment;
	}

	/**
	 * @param auditComment
	 *            the auditComment to set
	 */
	public void setAuditComment(String auditComment) {
		this.auditComment = auditComment;
	}

	/**
	 * @return the auditDate
	 */
	public Date getAuditDate() {
		return auditDate;
	}

	/**
	 * @param auditDate
	 *            the auditDate to set
	 */
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	/**
	 * @return the auditUser
	 */
	public String getAuditUser() {
		return auditUser;
	}

	/**
	 * @param auditUser
	 *            the auditUser to set
	 */
	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}

	public FileLibrary getLibrary() {
		return library;
	}

	public void setLibrary(FileLibrary library) {
		this.library = library;
	}

	public String getDurationTxt() {
		return durationTxt;
	}

	public void setDurationTxt(String durationTxt) {
		this.durationTxt = durationTxt;
	}

	public String getByteSizeTxt() {
		return byteSizeTxt;
	}

	public void setByteSizeTxt(String byteSizeTxt) {
		this.byteSizeTxt = byteSizeTxt;
	}
}