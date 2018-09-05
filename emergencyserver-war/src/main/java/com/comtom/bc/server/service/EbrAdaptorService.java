package com.comtom.bc.server.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.comtom.bc.server.repository.entity.EbrAdaptor;
import com.comtom.bc.server.req.ResourceLoadReq;

/**
 * 资源-消息接收设备业务逻辑处理接口定义
 * 
 * @author kidsoul
 *
 */
public interface EbrAdaptorService {
	/**
	 * 根据消息接收设备查询条件找出消息接收设备列表，分页查询
	 * 
	 * @param searchReq
	 * @return
	 */
	public Page<EbrAdaptor> searchPage(ResourceLoadReq searchReq);
	
	/**
	 * 根据消息接收设备查询条件找出消息接收设备列表
	 * 
	 * @param searchReq
	 * @return
	 */
	public List<EbrAdaptor> search(ResourceLoadReq searchReq);

	/**
	 * 根据最近更新时间找出消息接收设备列表
	 * 
	 * @param useRptTime
	 * @param rptStartTime
	 * @param rptEndTime
	 * @return
	 */
	public List<EbrAdaptor> listAdaptor(boolean useRptTime, Date rptStartTime, Date rptEndTime);
	
	/**
	 * 保存或修改消息接收设备
	 * 如果消息接收设备ID存在，则将其不为空的属性值更新到数据库；否则为新增保存
	 * 
	 * @param adaptor
	 * @return
	 */
	public EbrAdaptor saveOrUpdate(EbrAdaptor adaptor);
	
	/**
	 * 根据消息接收设备ID列表查找消息接收设备列表
	 * 
	 * @param adaptorIds
	 * @return
	 */
	public List<EbrAdaptor> findAdaptorListByIds(List<String> adaptorIds);

	/**
	 * 根据关联的台站ID列表找出对应的消息接收设备， 一个台站对应多个消息接收设备
	 * 
	 * @param relatedStationId
	 * @return
	 */
	public List<EbrAdaptor> findByRelatedRsEbrId(String relatedStationId);

	/**
	 * 根据消息接收设备ID找出消息接收设备详情信息
	 * 
	 * @param relatedAsEbrId
	 * @return
	 */
	public EbrAdaptor findOne(String relatedAsEbrId);

	/**
	 * 根据关联的平台ID列表找出对应的消息接收设备， 一个平台对应多个消息接收设备
	 * 
	 * @param relatedPlatformId
	 * @return
	 */
	public List<EbrAdaptor> findByRelatedPsEbrId(String relatedPlatformId);
}
