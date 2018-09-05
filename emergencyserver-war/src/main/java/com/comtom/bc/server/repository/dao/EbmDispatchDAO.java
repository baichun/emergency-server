package com.comtom.bc.server.repository.dao;

import java.util.List;

import com.comtom.bc.server.repository.entity.EbmDispatch;
import com.comtom.bc.server.repository.support.CustomRepository;

/**
 * EBM消息调用资源-数据访问层接口定义
 * 
 * @author zhucanhui
 *
 */
public interface EbmDispatchDAO extends CustomRepository<EbmDispatch, Integer> {

	/**
	 * 根据EbmId获取调度资源
	 * 
	 * @param ebmId
	 * @return List<EbmDispatch>
	 */
	public List<EbmDispatch> findByEbmId(String ebmId);

	/**
	 * 根据EbdId获取调度资源
	 * 
	 * @param ebdId
	 * @return EbmDispatch
	 */
	public EbmDispatch findByEbdId(String ebdId);

}
