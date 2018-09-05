package com.comtom.bc.server.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.comtom.bc.server.repository.entity.Ebd;
import com.comtom.bc.server.req.EbdQueryReq;

/**
 * 数据包处理-业务逻辑处理接口定义
 * 
 * @author zhucanhui
 *
 */
public interface EbdService {

	/**
	 * 根据EBD类型和状态获取EBD数据
	 * 
	 * @param key
	 * @return List<Ebd>
	 */
	public List<Ebd> getEbd(String ebdType, Integer ebdState);

	/**
	 * 获取EBD数据包文件的URL
	 * 
	 * @param sendFlag
	 * @param ebdName
	 * @return String
	 */
	public String getEbdFilePath(Integer sendFlag, String ebdName, String ebdType,String ebdDestEbrId);

	/**
	 * 根据条件获取EBD数据
	 * 
	 * @param key
	 * @return Page<Ebd>
	 */
	public Page<Ebd> getEbd(EbdQueryReq req);
	
	/**
	 * 根据EBDID获取EBD数据
	 * @param ebdId
	 * @return Ebd
	 */
	public Ebd findEbd(String ebdId);

	/**
	 * 根据EBM Id获取EBD
	 * 
	 * @param ebmId
	 * @return List<Ebd>
	 */
	public List<Ebd> findByEbmId(String ebmId);

	/**
	 * 根据EBDID获取EBD数据包
	 * 
	 * @param ebdId
	 * @return Ebd
	 */
	public Ebd getEbdDetail(String ebdId);

	/**
	 * 通过EBD分为发EBM消息
	 * 
	 * @param ebdType
	 * @param ebdState
	 */
	public int dispatchEbdPack(Integer ebmState);
}