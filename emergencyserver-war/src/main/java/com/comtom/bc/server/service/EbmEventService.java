package com.comtom.bc.server.service;

import java.util.List;

import com.comtom.bc.server.repository.entity.EbmEventType;

/**
 * EBM事件分类-业务逻辑处理接口定义
 * 
 * @author zhucanhui
 *
 */
public interface EbmEventService {

	/**
	 * 获取Ebm事件分类
	 * 
	 * @return List<EbmEventType>
	 */
	public List<EbmEventType> findAll();
}