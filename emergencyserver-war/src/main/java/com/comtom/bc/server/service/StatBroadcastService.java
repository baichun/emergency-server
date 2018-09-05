package com.comtom.bc.server.service;

import java.util.List;

import com.comtom.bc.server.repository.entity.StatBroadcast;
import com.comtom.bc.server.req.StatsicListReq;
import com.comtom.bc.server.req.StatsicQueryReq;

/**
 * 广播数据统计-业务逻辑处理接口定义
 * 
 * @author zhucanhui
 *
 */
public interface StatBroadcastService {
	/**
	 * 根据统计类型查询广播次数统计记录
	 * 
	 * @param searchReq
	 * @return
	 */
	public List<StatBroadcast> search(StatsicListReq searchReq);

	/**
	 * 根据给定条件实时查询广播统计数据
	 * 
	 * @param req
	 * @return
	 */
	public List<StatBroadcast> queryStatsic(StatsicQueryReq req);
}
