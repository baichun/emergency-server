package com.comtom.bc.server.service;

import java.util.Set;

import com.comtom.bc.server.repository.entity.SysDic;

/**
 * 键值参数-业务逻辑处理接口定义
 * 
 * @author zhucanhui
 *
 */
public interface DicService {
	public Set<SysDic> findCodeByKey(String key);
}