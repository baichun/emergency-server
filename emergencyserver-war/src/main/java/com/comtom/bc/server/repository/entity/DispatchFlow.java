package com.comtom.bc.server.repository.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <b>bc_dispatch_flow[bc_dispatch_flow]数据持久化对象</b>
 * 
 * @author zhucanhui
 * @date 2016-12-18 17:38:24
 */
@Entity
@Table(name = "bc_dispatch_flow")
public class DispatchFlow {

	/**
	 * 流程（会话）编号
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long flowId;

	/**
	 * 流程阶段（1:预警触发2:预警响应3:预警处理4:预警完成）
	 */
	private Integer flowStage;

	/**
	 * 流程状态<br>
	 * 预警触发 11:节目制作 12:节目审核 13:审核通过 14:审核不通过<br>
	 * 预警触发 11:接收信息 12:信息检验 13:校验通过 14:校验不通过<br> 
	 * 预警响应 21:方案生成 22:方案优化 23:方案调整 24:方案制作 25方案审核 26:审核通过 27:审核不通过<br>
	 * 预警处理 31:消息下发 32:广播发布 <br>
	 * 预警完成 41:预警完成<br>
	 */
	private Integer flowState;

	/**
	 * 关联节目编号
	 */
	private Long relatedProgramId;

	/**
	 * 关联业务数据包编号
	 */
	private String relatedEbdId;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 流程（会话）编号
	 * 
	 * @return flowId
	 */
	public Long getFlowId() {
		return flowId;
	}

	/**
	 * 流程阶段（1:预警触发2:预警响应3:预警处理4:预警完成）
	 * 
	 * @return flowStage
	 */
	public Integer getFlowStage() {
		return flowStage;
	}

	/**
	 * 流程状态
	 * 
	 * @return flowState
	 */
	public Integer getFlowState() {
		return flowState;
	}

	/**
	 * 关联节目编号
	 * 
	 * @return relatedProgramId
	 */
	public Long getRelatedProgramId() {
		return relatedProgramId;
	}

	/**
	 * 关联业务数据包编号
	 * 
	 * @return relatedEbdId
	 */
	public String getRelatedEbdId() {
		return relatedEbdId;
	}

	/**
	 * 创建时间
	 * 
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 更新时间
	 * 
	 * @return updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 流程（会话）编号
	 * 
	 * @param flowId
	 */
	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}

	/**
	 * 流程阶段（1:预警触发2:预警响应3:预警处理4:预警完成）
	 * 
	 * @param flowStage
	 */
	public void setFlowStage(Integer flowStage) {
		this.flowStage = flowStage;
	}

	/**
	 * 流程状态
	 * 
	 * @param flowState
	 */
	public void setFlowState(Integer flowState) {
		this.flowState = flowState;
	}

	/**
	 * 关联节目编号
	 * 
	 * @param relatedProgramId
	 */
	public void setRelatedProgramId(Long relatedProgramId) {
		this.relatedProgramId = relatedProgramId;
	}

	/**
	 * 关联业务数据包编号
	 * 
	 * @param relatedEbdId
	 */
	public void setRelatedEbdId(String relatedEbdId) {
		this.relatedEbdId = relatedEbdId;
	}

	/**
	 * 创建时间
	 * 
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 更新时间
	 * 
	 * @param updateTime
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}