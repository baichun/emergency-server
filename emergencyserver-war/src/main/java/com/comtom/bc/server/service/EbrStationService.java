package com.comtom.bc.server.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.comtom.bc.server.repository.entity.EbrStation;
import com.comtom.bc.server.req.ResourceLoadReq;

/**
 * 资源-台站前端业务逻辑处理接口定义
 * 
 * @author kidsoul
 *
 */
public interface EbrStationService {
	/**
	 * 根据查询条件找出台站前端列表
	 * 
	 * @param searchReq
	 * @return
	 */
	public List<EbrStation> search(ResourceLoadReq searchReq);
	
	/**
	 * 根据查询条件找出台站前端列表，分页查询
	 * 
	 * @param searchReq
	 * @return
	 */
	public Page<EbrStation> searchPage(ResourceLoadReq searchReq);
	
	/**
	 * 根据最新更新时间查找台站前端列表
	 * 
	 * @param useRptTime
	 * @param rptStartTime
	 * @param rptEndTime
	 * @return
	 */
	public List<EbrStation> listStation(boolean useRptTime, Date rptStartTime, Date rptEndTime);
	
	/**
	 * 保存或修改台站
	 * 如果台站ID存在，则将其不为空的属性值更新到数据库；否则为新增保存
	 * 
	 * @param station
	 * @return
	 */
	public EbrStation saveOrUpdate(EbrStation station);
	
	/**
	 * 根据台站ID列表找出台站列表
	 * 
	 * @param ebrStationIds
	 * @return
	 */
	public List<EbrStation> findStationListByIds(List<String> ebrStationIds);
	
	/**
	 * 根据台站ID找出台站详情信息
	 * 
	 * @param stationId
	 * @return
	 */
	public EbrStation findOne(String stationId);

	/**
	 * 根据关联的平台ID列表找出对应的台站， 一个平台对应多个台站
	 * 
	 * @param relatedPlatformId
	 * @return
	 */
	public List<EbrStation> findByRelatedPsEbrId(String relatedPlatformId);
}
