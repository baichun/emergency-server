package com.comtom.bc.server.repository.dao;

import com.comtom.bc.server.repository.entity.SysParam;
import com.comtom.bc.server.repository.support.CustomRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 
 * @author wj
 *
 */
public interface StatBoradcastAreaDAO extends CustomRepository<SysParam, Long> {


	@Query(value="SELECT SUM(commonNum)/SUM(totalNum)*100 AS commonRate,SUM(redNum)/SUM(totalNum)*100 AS redRate,SUM(orangeNum)/SUM(totalNum)*100 AS orangeRate,SUM(yellowNum)/SUM(totalNum)*100 AS yellowRate,SUM(blueNum)/SUM(totalNum)*100 AS blueRate FROM bc_stat_broadcast_area",nativeQuery = true)
	public Object typeCount();
}
