package com.comtom.bc.server.repository.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.comtom.bc.server.repository.entity.EbrWorktime;
import com.comtom.bc.server.repository.support.CustomRepository;

/**
 * 运行图-数据访问层接口定义<br>
 * 
 * @author kidsoul
 *
 */
public interface EbrWorktimeDAO extends CustomRepository<EbrWorktime, String> {

	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM EbrWorktime WHERE ebrId=:ebrId")
	public void deleteByEbrId(@Param(value = "ebrId") String ebrId);
}
