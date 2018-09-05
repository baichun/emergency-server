package com.comtom.bc.server.repository.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <b>bc_ebr_worktime[bc_ebr_worktime]数据持久化对象</b>
 * <p>
 * 注意:此文件由AOS平台自动生成-禁止手工修改。
 * </p>
 * 
 * @author Kidsoul Qu
 * @date 2016-12-06 16:57:08
 */
@Entity
@Table(name = "bc_ebr_worktime")
public class EbrWorktime {
	/**
	 * id
	 */
	@Id
	private String id;
	
	/**
	 * 资源ID
	 */
	private String ebrId;
	
	/**
	 * 工作时间表ID
	 */
	private String worktimeId;
	

	/**
	 * id
	 * 
	 * @return id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * 资源ID
	 * 
	 * @return ebrId
	 */
	public String getEbrId() {
		return ebrId;
	}
	
	/**
	 * 工作时间表ID
	 * 
	 * @return worktimeId
	 */
	public String getWorktimeId() {
		return worktimeId;
	}
	

	/**
	 * id
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 资源ID
	 * 
	 * @param ebrId
	 */
	public void setEbrId(String ebrId) {
		this.ebrId = ebrId;
	}
	
	/**
	 * 工作时间表ID
	 * 
	 * @param worktimeId
	 */
	public void setWorktimeId(String worktimeId) {
		this.worktimeId = worktimeId;
	}
	

}