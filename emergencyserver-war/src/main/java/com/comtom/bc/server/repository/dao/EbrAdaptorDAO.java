package com.comtom.bc.server.repository.dao;

import java.util.List;

import com.comtom.bc.server.repository.entity.EbrAdaptor;
import com.comtom.bc.server.repository.support.CustomRepository;

public interface EbrAdaptorDAO extends CustomRepository<EbrAdaptor, String> {
	/**
	 * 根据关联的台站ID找出对应的消息接收设备， 一个台站对应多个消息接收设备
	 * 
	 * @param relatedStationId
	 * @return
	 */
	public List<EbrAdaptor> findByRelatedRsEbrId(String relatedStationId);

	/**
	 * 根据关联的平台ID找出对应的消息接收设备， 一个平台对应多个消息接收设备
	 * 
	 * @param relatedPlatformId
	 * @return
	 */
	public List<EbrAdaptor> findByRelatedPsEbrId(String relatedPlatformId);
}
