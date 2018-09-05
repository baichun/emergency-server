package com.comtom.bc.server.repository.dao;

import com.comtom.bc.server.repository.entity.EbrTerminal;
import com.comtom.bc.server.repository.support.CustomRepository;

import java.util.List;

public interface EbrTerminalDAO extends CustomRepository<EbrTerminal, String> {

	/**
	 * 根据关联的平台ID找出对应的终端， 一个平台对应多个终端
	 * 
	 * @param relatedPlatformId
	 * @return
	 */
	List<EbrTerminal> findByRelatedPsEbrId(String relatedPlatformId);


}
