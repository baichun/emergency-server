package com.comtom.bc.server.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.comtom.bc.server.repository.entity.EbrPlatform;
import com.comtom.bc.server.repository.entity.VEbrPlatform;
import com.comtom.bc.server.req.ResourceLoadReq;

/**
 * 资源-应急广播平台业务逻辑处理接口定义
 * 
 * @author kidsoul
 *
 */
public interface EbrPlatformService {

	/**
	 * 查询应急广播平台资源列表记录数
	 * 
	 * @return long
	 */
	public long count();

	/**
	 * 根据条件查询应急广播平台资源列表
	 * 
	 * @param ebrPlatform
	 * @return List<EbrPlatform>
	 */
	public List<EbrPlatform> list(EbrPlatform ebrPlatform);

	/**
	 * 查询应急广播平台资源列表
	 */
	public Page<VEbrPlatform> findAll(ResourceLoadReq req);

	/**
	 * 查询应急广播平台资源列表(分页查询)
	 * 
	 * @param ebrPlatform
	 *            查询条件对象，字符串属性支持模糊查询，数值属性仅支持
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<EbrPlatform> listPage(EbrPlatform ebrPlatform, int pageNumber, int pageSize);

	/**
	 * 根据应急广播平台ID查询应急广播平台
	 * 
	 * @param psId
	 * @return
	 */
	public EbrPlatform findByPsId(String psId);

	/**
	 * 根据应急广播平台名称查找应急广播平台资源列表(分页查询)
	 * 
	 * @param psEbrName
	 * @return
	 */
	public Page<EbrPlatform> findByPlatformName(String psEbrName);

	/**
	 * 根据应急广播平台查询条件找出应急广播平台列表，分页查询
	 * 
	 * @param searchReq
	 * @return
	 */
	public Page<EbrPlatform> searchPage(ResourceLoadReq searchReq);

	/**
	 * 根据应急广播平台查询条件找出应急广播平台列表
	 * 
	 * @param searchReq
	 * @return
	 */
	public List<EbrPlatform> search(ResourceLoadReq searchReq);

	/**
	 * 保存或修改应急广播平台 如果应急广播平台ID存在，则将其不为空的属性值更新到数据库；否则为新增保存
	 * 
	 * @param platform
	 * @return
	 */
	public EbrPlatform saveOrUpdate(EbrPlatform platform);

	/**
	 * 根据应急广播平台ID列表批量删除应急广播平台数据记录
	 * 
	 * @param platformIds
	 * @return
	 */
	public boolean batchDelete(List<String> platformIds);

	/**
	 * 新增应急广播平台
	 * 
	 * @param platform
	 * @return
	 */
	public EbrPlatform add(EbrPlatform platform);

	/**
	 * 修改应急广播平台
	 * 
	 * @param platform
	 * @return
	 */
	public EbrPlatform update(EbrPlatform platform);

	/**
	 * 根据最近更新时间找出应急广播平台列表
	 * 
	 * @param useRptTime
	 * @param rptStartTime
	 * @param rptEndTime
	 * @return
	 */
	public List<EbrPlatform> listPlatform(boolean useRptTime, Date rptStartTime, Date rptEndTime);


	/**
	 * 查询未同步的应急广播状态
	 * @param useRptTime
	 * @param rptStartTime
	 * @param rptEndTime
	 * @return
	 */
	public List<EbrPlatform> statusSyncPlatform(boolean useRptTime, Date rptStartTime, Date rptEndTime);



	/**
	 * 根据平台ID列表查询平台列表
	 * 
	 * @param platformIds
	 * @return
	 */
	public List<EbrPlatform> findPlatformListByIds(List<String> platformIds);

	/**
	 * 根据应急广播平台ID查询应急广播平台
	 * @param psId
	 * @return
	 */
	public EbrPlatform findOne(String platformErbId);
}
