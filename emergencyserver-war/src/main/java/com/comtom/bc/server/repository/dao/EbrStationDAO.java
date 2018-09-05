package com.comtom.bc.server.repository.dao;

import java.util.List;

import com.comtom.bc.server.repository.entity.EbrStation;
import com.comtom.bc.server.repository.support.CustomRepository;

public interface EbrStationDAO extends CustomRepository<EbrStation, String> {

	/**
	 * 根据关联的平台ID找出对应的台站， 一个平台对应多个台站
	 * 
	 * @param relatedPlatformId
	 * @return
	 */
	List<EbrStation> findByRelatedPsEbrId(String relatedPlatformId);

}
