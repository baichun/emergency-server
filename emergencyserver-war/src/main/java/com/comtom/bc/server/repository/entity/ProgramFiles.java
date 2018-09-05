package com.comtom.bc.server.repository.entity;

import javax.persistence.*;

/**
 * <b>bc_program_files[bc_program_files]数据持久化对象</b>
 * 
 * @author zhucanhui
 * @date 2016-12-17 17:08:43
 */
@Entity
@Table(name = "bc_program_files")
public class ProgramFiles {

	/**
	 * 标识id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 节目编号
	 */
	private Long programId;

	/**
	 * 关联文件编号
	 */
	private Long fileId;

	/**
	 * 文件名称（原文件名）
	 */
	private String fileName;

	/**
	 * 标识id
	 * 
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 节目编号
	 * 
	 * @return programId
	 */
	public Long getProgramId() {
		return programId;
	}

	/**
	 * 关联文件编号
	 * 
	 * @return fileId
	 */
	public Long getFileId() {
		return fileId;
	}

	/**
	 * 标识id
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 节目编号
	 * 
	 * @param programId
	 */
	public void setProgramId(Long programId) {
		this.programId = programId;
	}

	/**
	 * 关联文件编号
	 * 
	 * @param fileId
	 */
	public void setFileId(Long fileId) {
		this.fileId = fileId;
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

}