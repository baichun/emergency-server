package com.comtom.bc.server.repository.dao;

import java.util.List;

import com.comtom.bc.server.repository.entity.Ebd;
import com.comtom.bc.server.repository.support.CustomRepository;

/**
 * 数据包-数据访问层接口定义
 * 
 * @author zhucanhui
 *
 */
public interface EbdDAO extends CustomRepository<Ebd, String> {

	/**
	 * 根据EBM Id获取EBD
	 * 
	 * @param ebmId
	 * @return List<Ebd>
	 */
	public List<Ebd> findByEbmId(String ebmId);
}
