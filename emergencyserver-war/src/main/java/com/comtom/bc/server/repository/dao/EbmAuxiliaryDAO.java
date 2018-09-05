package com.comtom.bc.server.repository.dao;

import java.util.List;

import com.comtom.bc.server.repository.entity.EbmAuxiliary;
import com.comtom.bc.server.repository.support.CustomRepository;

/**
 * EBM消息辅助数据-数据访问层接口定义
 * 
 * @author zhucanhui
 *
 */
public interface EbmAuxiliaryDAO extends CustomRepository<EbmAuxiliary, Integer> {

	/**
	 * 根据EbmId获取关联辅助数据
	 * 
	 * @param ebmId
	 * @return List<EbmAuxiliary>
	 */
	public List<EbmAuxiliary> findByEbmId(String ebmId);
}
