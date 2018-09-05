package com.comtom.bc.server.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comtom.bc.server.repository.entity.EbmEventType;

/**
 * EBM事件分类信息-数据访问层接口定义
 * 
 * @author zhucanhui
 *
 */
public interface EbmEventDAO extends JpaRepository<EbmEventType, String> {

}
