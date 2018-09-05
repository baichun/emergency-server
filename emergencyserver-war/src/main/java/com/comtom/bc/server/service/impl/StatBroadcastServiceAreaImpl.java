package com.comtom.bc.server.service.impl;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.exchange.commom.EventSeverityEnum;
import com.comtom.bc.server.repository.dao.*;
import com.comtom.bc.server.repository.entity.*;
import com.comtom.bc.server.req.StatsicListReq;
import com.comtom.bc.server.req.StatsicQueryReq;
import com.comtom.bc.server.service.StatBroadcastAreaService;
import com.comtom.bc.server.service.StatBroadcastService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * 广播数据统计-业务逻辑处理接口定义
 * 
 * @author zhucanhui
 *
 */
@Service("StatBroadcastAreaService")
public class StatBroadcastServiceAreaImpl implements StatBroadcastAreaService {

	@Autowired
	private StatBoradcastAreaDAO statBoradcastAreaDAO;


	@Override
	public Object typeCount() {
		return statBoradcastAreaDAO.typeCount();
	}
}