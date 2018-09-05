package com.comtom.bc.server.repository.dao;

import java.util.List;

import com.comtom.bc.server.repository.entity.Ebm;
import com.comtom.bc.server.repository.entity.EbmBrdRecord;
import com.comtom.bc.server.repository.support.CustomRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * EBM消息-数据访问层接口定义
 * 
 * @author zhucanhui
 *
 */
public interface EbmDAO extends CustomRepository<Ebm, String> {

	/**
	 * 根据调度方案Id获取EBM
	 * 
	 * @param schemeId
	 * @return Ebm
	 */
	public List<Ebm> findBySchemeId(Integer schemeId);

	/**
	 * 根据节目Id获取EBM
	 * 
	 * @param programNum
	 * @return Ebm
	 */
	public Ebm findByProgramNum(Integer programNum);


	/**
	 * 根据消息id集合查询EBM消息记录
	 * @param ebmIds
	 * @return
	 */
	@Query(nativeQuery = true, value = "select * from bc_ebm where ebmId in  (:ebmIds) ORDER by createTime desc")
	public List<Ebm> findEbmByEmbIds(@Param("ebmIds") List<String> ebmIds);

}
