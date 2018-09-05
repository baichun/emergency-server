package com.comtom.bc.server.repository.dao;

import java.util.List;

import com.comtom.bc.server.repository.entity.EbrPlatform;
import com.comtom.bc.server.repository.support.CustomRepository;

public interface EbrPlatformDAO extends CustomRepository<EbrPlatform, String> {
	
	public List<EbrPlatform> findByPsEbrNameLike(String psEbrName);
}
