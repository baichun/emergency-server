package com.comtom.bc.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.comtom.bc.server.repository.dao.EbmEventDAO;
import com.comtom.bc.server.repository.entity.EbmEventType;
import com.comtom.bc.server.service.EbmEventService;

/**
 * 字典参数-业务逻辑处理接口定义
 * 
 * @author zhucanhui
 *
 */
@Service("EbmEventService")
public class EbmEventServiceImpl implements EbmEventService {

	@Autowired
	private EbmEventDAO ebmEventDAO;

	/**
	 * 获取Ebm事件分类(一次性返回所有)
	 * 
	 * @return List<EbmEventType>
	 */
	public List<EbmEventType> findAll() {
		return ebmEventDAO.findAll(getSort());
	}

	/**
	 * 获取排序对象
	 * 
	 * @return Sort
	 */
	private Sort getSort() {
		Order order = new Order(Direction.ASC, "eventCode");
		List<Order> orders = new ArrayList<Order>();
		orders.add(order);
		Sort sort = new Sort(orders);
		return sort;
	}
}