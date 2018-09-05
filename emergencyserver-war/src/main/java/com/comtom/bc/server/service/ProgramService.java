package com.comtom.bc.server.service;

import java.util.List;

import com.comtom.bc.server.repository.entity.ProgramFiles;
import org.springframework.data.domain.Page;

import com.comtom.bc.server.repository.entity.ProgramInfo;
import com.comtom.bc.server.req.ProgramAuditReq;
import com.comtom.bc.server.req.ProgramQueryReq;
import com.comtom.bc.server.req.ProgramSaveReq;
import com.comtom.bc.server.req.ProgramUpdateReq;

/**
 * 节目信息-业务逻辑处理接口定义
 * 
 * @author zhucanhui
 *
 */
public interface ProgramService {

	/**
	 * 节目保存<br>
	 * 节目信息、节目区域、节目文件和节目策略
	 * 
	 * @param programReq
	 * @return ProgramInfo
	 */
	public ProgramInfo saveProgram(ProgramSaveReq programReq);

	/**
	 * 节目保存<br>
	 * 节目信息、节目区域、节目文件和节目策略
	 *
	 * @param programReq
	 * @return ProgramInfo
	 */
	public ProgramInfo saveProgram(ProgramInfo programReq);

	/**
	 * 提交节目，更新节目状态为提交审核状态
	 * 
	 * @param programId
	 * @return ProgramInfo
	 */
	public ProgramInfo submitProgram(Long programId);

	/**
	 * 节目信息更新
	 * 
	 * @param programReq
	 * @return ProgramInfo
	 */
	public ProgramInfo updateProgram(ProgramUpdateReq programReq) throws RuntimeException;

	/**
	 * 节目审核
	 * 
	 * @return
	 */
	public ProgramInfo auditProgram(ProgramAuditReq req);

	/**
	 * 根据节目Id取消节目
	 * 
	 * @param programId
	 * @return ProgramInfo
	 */
	public ProgramInfo cancelProgram(Long programId);

	/**
	 * 节目总记录数统计
	 * 
	 * @return long
	 */
	public long count();

	/**
	 * 查询节目基本信息列表<br>
	 * 提供多条件，日期区间查询
	 * 
	 * @param programReq
	 */
	public Page<ProgramInfo> getProgramInfo(ProgramQueryReq programReq);

	/**
	 * 节目详细信息查看
	 */
	public ProgramInfo getProgramDetail(Long programId);

	public ProgramInfo getProgramInfo(Long programId);


	/**
	 * 获取周期性节目待调度分发的节目列表
	 * 
	 * @return List<ProgramInfo>
	 */
	public List<ProgramInfo> getDispatchProgram();

	public List<ProgramFiles> findProgramFile(ProgramFiles programFile);
}