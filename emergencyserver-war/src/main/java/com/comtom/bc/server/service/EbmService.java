package com.comtom.bc.server.service;

import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.details.other.EBM;
import com.comtom.bc.exchange.model.ebd.ebm.MsgBasicInfo;
import com.comtom.bc.exchange.model.ebd.ebm.MsgContent;
import com.comtom.bc.exchange.util.DateStyle;
import com.comtom.bc.exchange.util.DateTimeUtil;
import com.comtom.bc.exchange.util.EBDModelBuild;
import com.comtom.bc.server.repository.entity.EbmDispatch;
import com.comtom.bc.server.req.EbmReq;
import org.springframework.data.domain.Page;

import com.comtom.bc.server.repository.entity.Ebm;
import com.comtom.bc.server.repository.entity.VEbmDispatch;
import com.comtom.bc.server.req.EbmQueryReq;

import java.util.Date;
import java.util.List;

/**
 * 预警消息和广播指令-业务逻辑处理接口定义
 * 
 * @author zhucanhui
 *
 */
public interface EbmService {

	/**
	 * 根据条件获取EBM数据-预警消息<br>
	 * 
	 * @param key
	 * @return Page<Ebm>
	 */
	public Page<Ebm> getAlarmEbm(EbmQueryReq req);

	/**
	 * 预警消息详情
	 * 
	 * @param req
	 * @return Ebm
	 */
	public Ebm getAlarmEbmDetail(EbmQueryReq req);

	/**
	 * 根据条件获取EBM数据-广播指令消息<br>
	 * 
	 * @param req
	 * @return Page<Ebm>
	 */
	public Page<Ebm> getDispatchEbm(EbmQueryReq req);

	/**
	 * 根据条件获取EBM数据-广播指令消息<br>
	 * 
	 * @param req
	 * @return Page<VEbmDispatch>
	 */
	public Page<VEbmDispatch> getEbmDispatch(EbmQueryReq req);

	/**
	 * 根据条件获取EBM数据-广播指令消息详情<br>
	 * 
	 * @param req
	 * @return Page<VEbmDispatch>
	 */
	public VEbmDispatch getEbmDispatchDetail(EbmQueryReq req);


	public List<EbmDispatch> getDispatch(String embId);


	public EBD sendEbm(EbmReq ebmReq);

	public Page<Ebm> getEbm(EbmQueryReq req);


}