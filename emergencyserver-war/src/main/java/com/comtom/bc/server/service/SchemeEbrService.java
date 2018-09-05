package com.comtom.bc.server.service;

import java.util.List;
import java.util.Map;

import com.comtom.bc.server.repository.entity.EbmResBs;
import com.comtom.bc.server.repository.entity.SchemeEbr;

/**
 * 调度方案关联资源-业务逻辑处理接口定义
 * 
 * @author zhucanhui
 *
 */
public interface SchemeEbrService {

	/**
	 * 根据方案Id计算统计关联资源情况
	 * 
	 * @param schemeId
	 * @return Map<String, Integer>
	 */
	public Map<String, Long> calcEbr(Integer schemeId);

	/**
	 * 根据方案Id计算统计关联资源发送和响应情况情况
	 * 
	 * @return Map<String, Long>
	 */
	public Map<String, Long> calcEbrDispatch(Integer schemeId);

	/**
	 * 播发情况统计(根据调度方案)
	 * 
	 * @return Map<String, Long>
	 */
	public Map<String, List<EbmResBs>> brdStatByScheme(Integer schemeId);

	/**
	 * 根据调度方案查询调度方案关联资源
	 * 
	 * @param schemeId
	 * @return List<SchemeEbr>
	 */
	public List<SchemeEbr> findBySchemeId(Integer schemeId);
}