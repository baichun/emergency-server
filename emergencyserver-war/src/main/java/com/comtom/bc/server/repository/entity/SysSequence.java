package com.comtom.bc.server.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <b>aos_sys_sequenceID配置表[aos_sys_sequence]数据持久化对象</b>
 * 
 * @author zhucanhui
 * @date 2015-12-22 22:10:31
 */
@Entity
@Table(name = "aos_sys_sequence")
public class SysSequence {

	/**
	 * 流水号
	 */
	@Id
	@Column(name = "id_")
	private String id;

	/**
	 * 名称
	 */
	@Column(name = "name_")
	private String name;

	/**
	 * 类型
	 */
	@Column(name = "type_")
	private String type;

	/**
	 * 前缀
	 */
	@Column(name = "prefix_")
	private String prefix;

	/**
	 * 起始值
	 */
	@Column(name = "start_")
	private String start;

	/**
	 * 递增步长
	 */
	@Column(name = "step_")
	private String step;

	/**
	 * 当前值
	 */
	@Column(name = "cur_value_")
	private String curValue;

	/**
	 * 当前状态
	 */
	@Column(name = "status_")
	private String status;

	/**
	 * 连接符
	 */
	@Column(name = "connector_")
	private String connector;

	/**
	 * 后缀
	 */
	@Column(name = "suffix_")
	private String suffix;

	/**
	 * DBSequence名称
	 */
	@Column(name = "db_seq_name_")
	private String dbSeqName;

	/**
	 * 最大值
	 */
	@Column(name = "max_value_")
	private String maxValue;

	/**
	 * 是否循环
	 */
	@Column(name = "is_circul_")
	private String isCircul;

	/**
	 * 最小值
	 */
	@Column(name = "min_value_")
	private String minValue;

	/**
	 * 是否左补足
	 */
	@Column(name = "is_leftpad_")
	private String isLeftpad;

	/**
	 * 当前格式化值
	 */
	@Column(name = "format_value_")
	private String formatValue;

	/**
	 * 备注
	 */
	@Column(name = "remark_")
	private String remark;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @param prefix
	 *            the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * @return the start
	 */
	public String getStart() {
		return start;
	}

	/**
	 * @param start
	 *            the start to set
	 */
	public void setStart(String start) {
		this.start = start;
	}

	/**
	 * @return the step
	 */
	public String getStep() {
		return step;
	}

	/**
	 * @param step
	 *            the step to set
	 */
	public void setStep(String step) {
		this.step = step;
	}

	/**
	 * @return the curValue
	 */
	public String getCurValue() {
		return curValue;
	}

	/**
	 * @param curValue
	 *            the curValue to set
	 */
	public void setCurValue(String curValue) {
		this.curValue = curValue;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the connector
	 */
	public String getConnector() {
		return connector;
	}

	/**
	 * @param connector
	 *            the connector to set
	 */
	public void setConnector(String connector) {
		this.connector = connector;
	}

	/**
	 * @return the suffix
	 */
	public String getSuffix() {
		return suffix;
	}

	/**
	 * @param suffix
	 *            the suffix to set
	 */
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	/**
	 * @return the dbSeqName
	 */
	public String getDbSeqName() {
		return dbSeqName;
	}

	/**
	 * @param dbSeqName
	 *            the dbSeqName to set
	 */
	public void setDbSeqName(String dbSeqName) {
		this.dbSeqName = dbSeqName;
	}

	/**
	 * @return the maxValue
	 */
	public String getMaxValue() {
		return maxValue;
	}

	/**
	 * @param maxValue
	 *            the maxValue to set
	 */
	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}

	/**
	 * @return the isCircul
	 */
	public String getIsCircul() {
		return isCircul;
	}

	/**
	 * @param isCircul
	 *            the isCircul to set
	 */
	public void setIsCircul(String isCircul) {
		this.isCircul = isCircul;
	}

	/**
	 * @return the minValue
	 */
	public String getMinValue() {
		return minValue;
	}

	/**
	 * @param minValue
	 *            the minValue to set
	 */
	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}

	/**
	 * @return the isLeftpad
	 */
	public String getIsLeftpad() {
		return isLeftpad;
	}

	/**
	 * @param isLeftpad
	 *            the isLeftpad to set
	 */
	public void setIsLeftpad(String isLeftpad) {
		this.isLeftpad = isLeftpad;
	}

	/**
	 * @return the formatValue
	 */
	public String getFormatValue() {
		return formatValue;
	}

	/**
	 * @param formatValue
	 *            the formatValue to set
	 */
	public void setFormatValue(String formatValue) {
		this.formatValue = formatValue;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

}