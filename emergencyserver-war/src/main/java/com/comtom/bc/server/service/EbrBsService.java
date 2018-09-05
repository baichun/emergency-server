package com.comtom.bc.server.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.comtom.bc.server.repository.entity.EbrBroadcast;
import com.comtom.bc.server.req.ResourceLoadReq;

/**
 * 资源-播出系统业务逻辑处理接口定义
 * 
 * @author kidsoul
 *
 */
public interface EbrBsService {

	/**
	 * 查询播出系统资源列表记录数
	 * 
	 * @return long
	 */
	public long count();
	
	/**
	 * 根据条件查询播出系统资源列表
	 * @param ebrBroadcast
	 * @return List<EbrBroadcast>
	 */
	public List<EbrBroadcast> list(EbrBroadcast ebrBroadcast);
	
	/**
	 * 查询播出系统资源列表(分页查询)
	 */
	public List<EbrBroadcast> listPage(int pageNumber, int pageSize);
	
	/**
	 * 根据播出系统名称查询播出系统
	 * @param bsName
	 * @return
	 */
	public EbrBroadcast findByBsName(String bsName);
	
	/**
	 * 根据播出系统查询条件找出播出系统列表，分页查询
	 * 
	 * @param searchReq
	 * @return
	 */
	public Page<EbrBroadcast> searchPage(ResourceLoadReq searchReq);

	/**
	 * 根据播出系统查询条件找出播出系统列表
	 * 
	 * @param searchReq
	 * @return
	 */
	public List<EbrBroadcast> search(ResourceLoadReq searchReq);
	
	/**
	 * 根据最近更新时间找出播出系统列表
	 * 
	 * @param useRptTime
	 * @param rptStartTime
	 * @param rptEndTime
	 * @return
	 */
	public List<EbrBroadcast> listBroadcast(boolean useRptTime, Date rptStartTime, Date rptEndTime);


	/**
	 * 查询未同步的播出系统状态
	 * @param useRptTime
	 * @param rptStartTime
	 * @param rptEndTime
	 * @return
	 */
	public List<EbrBroadcast> broadcastStatus(boolean useRptTime, Date rptStartTime, Date rptEndTime);
	
	/**
	 * 根据播出系统ID列表找出播出系统列表
	 * 
	 * @param broadcastIds
	 * @return
	 */
	public List<EbrBroadcast> findBroadcastListByIds(List<String> broadcastIds);
	
	/**
	 * 根据关联的台站ID找出对应的播出系统列表， 一个台站对应多个播出系统
	 * 
	 * @param relatedStationId
	 * @return
	 */
	public List<EbrBroadcast> findByRelatedRsEbrId(String relatedStationId);

	/**
	 * 根据播出系统ID找出播出系统详情信息
	 * 
	 * @param broadcastErbId
	 * @return
	 */
	public EbrBroadcast findOne(String broadcastErbId);
	
	/**
	 * 根据关联的平台ID找出对应的播出系统列表， 一个平台对应多个播出系统
	 * 
	 * @param relatedPlatformId
	 * @return
	 */
	public List<EbrBroadcast> findByRelatedPsEbrId(String relatedPlatformId);

	/**
	 * 根据关联的适配器ID找出对应的播出系统列表， 一个适配器对应多个播出系统
	 * 
	 * @param relatedAdaptorId
	 * @return
	 */
	public List<EbrBroadcast> findByRelatedAsEbrId(String relatedAdaptorId);

	/**
	 * 保存或修改播出系统
	 * 如果播出系统ID存在，则将其不为空的属性值更新到数据库；否则为新增保存
	 * 
	 * @param ebrBroadcast
	 * @return
	 */
	public EbrBroadcast saveOrUpdate(EbrBroadcast ebrBroadcast);
}
