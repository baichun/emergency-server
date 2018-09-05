package com.comtom.bc.server.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comtom.bc.server.repository.dao.SysDicDAO;
import com.comtom.bc.server.repository.entity.SysDic;
import com.comtom.bc.server.service.DicService;

/**
 * 字典参数-业务逻辑处理接口定义
 * 
 * @author zhucanhui
 *
 */
@Service("DicService")
public class DicServiceImpl implements DicService {

	@Autowired
	private SysDicDAO sysDicDAO;

	/**
	 * 根据字典参数索引KEY获取对应值（集合）
	 * 
	 * @param key
	 * @return
	 */
	public Set<SysDic> findCodeByKey(String key) {
		return sysDicDAO.findCodeByKey(key);
	}
}