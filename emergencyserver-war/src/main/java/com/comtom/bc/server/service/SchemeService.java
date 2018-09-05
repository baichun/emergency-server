package com.comtom.bc.server.service;

import java.util.concurrent.Future;

import org.springframework.data.domain.Page;

import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.server.repository.entity.ProgramInfo;
import com.comtom.bc.server.repository.entity.SchemeInfo;
import com.comtom.bc.server.repository.entity.VSchemeFlow;
import com.comtom.bc.server.req.SchemeAuditReq;
import com.comtom.bc.server.req.SchemeFlowReq;
import com.comtom.bc.server.req.SchemeQueryReq;
import com.comtom.bc.server.req.SchemeUpdateReq;

/**
 * 调度方案-业务逻辑处理接口定义
 * 
 * @author zhucanhui
 *
 */
public interface SchemeService {

	/**
	 * 生成调度方案、资源发现和EBM消息(本级制作节目处理)<br>
	 * 异步调度
	 * 
	 * @return Future<SchemeInfo>
	 */
	public Future<SchemeInfo> asynDispatch(ProgramInfo programInfo);

	/**
	 * 生成调度方案、资源发现和EBM消息(本级制作节目处理)<br>
	 * 同步调度
	 * 
	 * @return SchemeInfo
	 */
	public SchemeInfo synDispatch(ProgramInfo programInfo);

	/**
	 * 生成调度方案、资源发现和EBM消息(解析上级数据包处理) 异步调度
	 * 
	 * @return Future<SchemeInfo>
	 */
	public Future<SchemeInfo> asynDispatch(EBD ebd,Long flowId,Long infoId);

	/**
	 * 生成EBM消息(播发)
	 * 
	 * @return int
	 */
	public int generateEbm(ProgramInfo programInfo, SchemeInfo schemeInfo);

	/**
	 * 根据条件查询调度方案列表
	 * 
	 * @param req
	 * @return Page<SchemeInfo>
	 */
	public Page<SchemeInfo> getScheme(SchemeQueryReq req);

	/**
	 * 根据条件查询调度方案流程信息列表
	 * 
	 * @param req
	 * @return Page<VSchemeFlow>
	 */
	public Page<VSchemeFlow> getSchemeFlow(SchemeFlowReq req);

	/**
	 * 根据调度方案Id,获取调度方案详情
	 * 
	 * @param req
	 * @return SchemeInfo
	 */
	public SchemeInfo getSchemeDetail(SchemeQueryReq req);

	/**
	 * 调度方案调整
	 * 
	 * @param req
	 * @return SchemeInfo
	 */
	public SchemeInfo adjustScheme(SchemeUpdateReq req);

	/**
	 * 调度方案审核
	 * 
	 * @param req
	 * @return SchemeInfo
	 */
	public SchemeInfo auditScheme(SchemeAuditReq req);

	/**
	 * 根据方案Id获取方案信息
	 * 
	 * @param schemeId
	 * @return SchemeInfo
	 */
	public SchemeInfo findOne(Integer schemeId);

	/**
	 * 调度方案提交
	 * 
	 * @return SchemeInfo
	 */
	public SchemeInfo submitScheme(SchemeQueryReq req);

	/**
	 * 取消调度方案
	 * 
	 * @param programId
	 * @return SchemeInfo
	 */
	public SchemeInfo cancelScheme(Long programId);

	/**
	 * 取消EBD和EBM
	 * 
	 * @param schemeId
	 * @return Ebd
	 */
	public boolean cancelEbdEbm(Integer schemeId);

	/**
	 * 根据节目Id获取对应调度方案信息
	 * 
	 * @param programId
	 * @return SchemeInfo
	 */
	public SchemeInfo findByProgramId(Long programId);

}