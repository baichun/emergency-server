package com.comtom.bc.server.service;

import com.comtom.bc.server.repository.entity.EbrTerminal;
import com.comtom.bc.server.req.ResourceLoadReq;
import com.comtom.bc.server.req.TerminalStatReq;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

/**
 * 资源-终端业务逻辑处理接口定义
 * 
 * @author kidsoul
 *
 */
public interface EbrTerminalService {

	/**
	 * 根据条件统计终端数
	 * 
	 * @param req
	 * @return long
	 */
	public long count(TerminalStatReq req);

	/**
	 * 根据查询条件找出终端列表
	 * 
	 * @param searchReq
	 * @return
	 */
	public List<EbrTerminal> search(ResourceLoadReq searchReq);

	/**
	 * 根据查询条件找出终端列表，分页查询
	 * 
	 * @param searchReq
	 * @return
	 */
	public Page<EbrTerminal> searchPage(ResourceLoadReq searchReq);

	/**
	 * 根据最新更新时间查找终端列表
	 * 
	 * @param useRptTime
	 * @param rptStartTime
	 * @param rptEndTime
	 * @return
	 */
	public List<EbrTerminal> listTerminal(boolean useRptTime, Date rptStartTime, Date rptEndTime);
	
	/**
	 * 保存或修改终端
	 * 如果终端ID存在，则将其不为空的属性值更新到数据库；否则为新增保存
	 * 
	 * @param terminal
	 * @return
	 */
	public EbrTerminal saveOrUpdate(EbrTerminal terminal);
	
	/**
	 * 根据终端ID列表查找终端列表
	 * 
	 * @param terminalIds
	 * @return
	 */
	public List<EbrTerminal> findTerminalListByIds(List<String> terminalIds);

	/**
	 * 根据关联的平台ID列表找出对应的终端， 一个平台对应多个终端
	 * 
	 * @param relatedPlatformId
	 * @return
	 */
	public List<EbrTerminal> findByRelatedPsEbrId(String relatedPlatformId);
	
	
	/** 根据终端所在的区域编码查找终端列表
	 * @param areaCode 区域编码
	 * @return
	 */
	public List<EbrTerminal> findByAreaCode(String areaCode);
	
	/** 根据终端ID查找终端信息
	 * @param terminalId 终端ID
	 * @return
	 */
	public EbrTerminal findByPK(String terminalId);

	/**
	 * 根据最新更新时间查找终端状态列表
	 *
	 * @param useRptTime
	 * @param rptStartTime
	 * @param rptEndTime
	 * @return
	 */
	public List<EbrTerminal> listTerminalStatus(boolean useRptTime, Date rptStartTime, Date rptEndTime );


    public Page<EbrTerminal> listTerminalPage();

	void updateEbrTerminal(List<EbrTerminal> ebrTrmFound);



}
