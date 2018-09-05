package com.comtom.bc.server.repository.dao;

import java.util.List;

import com.comtom.bc.server.repository.entity.EbrBroadcast;
import com.comtom.bc.server.repository.support.CustomRepository;

/**
 * 播出系统资源-数据访问层接口定义（支持分页查询）
 * 
 * @author zhucanhui
 *
 */
public interface EbrBsDAO extends CustomRepository<EbrBroadcast, String> {

	public EbrBroadcast findByBsName(String bsName);
	
	/**
	 * 根据关联的台站ID找出对应的播出系统列表， 一个台站对应多个播出系统
	 * 
	 * @param relatedStationId
	 * @return
	 */
	public List<EbrBroadcast> findByRelatedRsEbrId(String relatedStationId);

	/**
	 * 根据关联的平台ID找出对应的播出系统列表， 一个平台对应多个播出系统
	 * 
	 * @param relatedPlatformId
	 * @return
	 */
	public List<EbrBroadcast> findByRelatedPsEbrId(String relatedPlatformId);

	/**
	 * 根据关联的适配器ID找出对应的播出系统列表， 一个适配器对应多个播出系统
	 * 
	 * @param relatedAdaptorId
	 * @return
	 */
	public List<EbrBroadcast> findByRelatedAsEbrId(String relatedAdaptorId);

}
