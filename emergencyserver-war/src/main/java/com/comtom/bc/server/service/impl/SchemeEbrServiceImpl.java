package com.comtom.bc.server.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.result.ResultKey;
import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.exchange.commom.BroadcastStateEnum;
import com.comtom.bc.server.repository.dao.EbdDAO;
import com.comtom.bc.server.repository.dao.EbdResponseDAO;
import com.comtom.bc.server.repository.dao.EbmBrdRecordDAO;
import com.comtom.bc.server.repository.dao.EbmDAO;
import com.comtom.bc.server.repository.dao.EbmDispatchDAO;
import com.comtom.bc.server.repository.dao.EbmResBsDAO;
import com.comtom.bc.server.repository.dao.EbmResDAO;
import com.comtom.bc.server.repository.dao.EbrPlatformDAO;
import com.comtom.bc.server.repository.dao.RegionAreaDAO;
import com.comtom.bc.server.repository.dao.SchemeDAO;
import com.comtom.bc.server.repository.dao.SchemeEbrDAO;
import com.comtom.bc.server.repository.entity.EbdResponse;
import com.comtom.bc.server.repository.entity.Ebm;
import com.comtom.bc.server.repository.entity.EbmBrdRecord;
import com.comtom.bc.server.repository.entity.EbmDispatch;
import com.comtom.bc.server.repository.entity.EbmRes;
import com.comtom.bc.server.repository.entity.EbmResBs;
import com.comtom.bc.server.repository.entity.EbrPlatform;
import com.comtom.bc.server.repository.entity.RegionArea;
import com.comtom.bc.server.repository.entity.SchemeEbr;
import com.comtom.bc.server.service.SchemeEbrService;
import com.comtom.bc.server.service.base.BaseService;

/**
 * 调度方案-业务逻辑处理接口定义
 * 
 * @author zhucanhui
 *
 */
@Service("SchemeEbrService")
public class SchemeEbrServiceImpl extends BaseService implements SchemeEbrService {

	@Autowired
	private SchemeDAO schemeDAO;

	@Autowired
	private SchemeEbrDAO schemeEbrDAO;

	@Autowired
	private EbdDAO ebdDAO;

	@Autowired
	private EbdResponseDAO ebdResponseDAO;

	@Autowired
	private EbmDAO ebmDAO;

	@Autowired
	private EbmDispatchDAO ebmDispatchDAO;

	@Autowired
	private EbmBrdRecordDAO ebmBrdRecordDAO;

	@Autowired
	private EbmResDAO ebmResDAO;

	@Autowired
	private EbmResBsDAO ebmResBsDAO;

	@Autowired
	private EbrPlatformDAO ebrPlatformDAO;

	@Autowired
	private RegionAreaDAO regionAreaDAO;

	/**
	 * 根据方案Id计算统计关联资源情况
	 * 
	 * @param schemeId
	 * @return Map<String, Integer>
	 */
	public Map<String, Long> calcEbr(Integer schemeId) {

		Map<String, Long> ebrMap = new HashMap<String, Long>();
		Long ebrPsCount = schemeEbrDAO.count(getEbrSpec(schemeId, Constants.EBR_TYPE_PLATFORM));
		Long ebrBsCount = schemeEbrDAO.count(getEbrSpec(schemeId, Constants.EBR_TYPE_EWBS_BS));

		ebrMap.put(ResultKey.EBRPS_LIST_KEY, ebrPsCount);
		ebrMap.put(ResultKey.EBRBS_LIST_KEY, ebrBsCount);

		return ebrMap;
	}

	/**
	 * 根据方案Id计算统计关联资源发送和响应情况情况
	 * 
	 * @return Map<String, Long>
	 */
	public Map<String, Long> calcEbrDispatch(Integer schemeId) {

		Map<String, Long> dispatchMap = new HashMap<String, Long>();
		List<Ebm> ebmList = ebmDAO.findBySchemeId(schemeId);

		// 下发目标
		Long psSendTargetCount = 0L;
		Long bsSendTargetCount = 0L;

		// 完成下发
		Long psSendCompleteCount = 0L;
		Long bsSendCompleteCount = 0L;

		// 响应目标
		Long psSendSuccessCount = 0L;
		Long bsSendSuccessCount = 0L;

		// 正在播发
		Long psInProcessCount = 0L;
		Long bsInProcessCount = 0L;

		// 播发完成
		Long psBrdSuccessCount = 0L;
		Long bsBrdSuccessCount = 0L;

		for (Ebm ebm : ebmList) {

			String ebmId = ebm.getEbmId();

			// 统计发送情况
			List<EbmDispatch> ebmDispatchList = ebmDispatchDAO.findByEbmId(ebmId);
			for (EbmDispatch ebmDispatch : ebmDispatchList) {

				Integer state = ebmDispatch.getState();
				String ebdId = ebmDispatch.getEbdId();
				EbdResponse ebdResponse = ebdResponseDAO.findOne(ebdId);

				String bsEbrId = ebmDispatch.getBsEbrId();

				if (CommonUtil.isNotEmpty(bsEbrId)) {
					bsSendTargetCount = bsSendTargetCount + 1;

					if (state.equals(Constants.DISPATCH_STATE_DONE)) {
						bsSendCompleteCount = bsSendCompleteCount + 1;
					}

				} else {

					if (state.equals(Constants.DISPATCH_STATE_DONE)) {
						psSendCompleteCount = psSendCompleteCount + 1;
					}

					psSendTargetCount = psSendTargetCount + 1;
				}

				if (ebdResponse != null) {
					if (CommonUtil.isNotEmpty(bsEbrId)) {
						bsSendSuccessCount = bsSendSuccessCount + 1;
					} else {
						psSendSuccessCount = psSendSuccessCount + 1;
					}
				}
			}

			// 设置下发目标、下发完成、目标响应
			dispatchMap.put(ResultKey.PS_SEND_TARGET_COUNT, psSendTargetCount);
			dispatchMap.put(ResultKey.BS_SEND_TARGET_COUNT, bsSendTargetCount);
			dispatchMap.put(ResultKey.PS_SEND_COMPLETE_COUNT, psSendCompleteCount);
			dispatchMap.put(ResultKey.BS_SEND_COMPLETE_COUNT, bsSendCompleteCount);
			dispatchMap.put(ResultKey.PS_SEND_SUCCESS_COUNT, psSendSuccessCount);
			dispatchMap.put(ResultKey.BS_SEND_SUCCESS_COUNT, bsSendSuccessCount);

			// 统计响应情况
			// 获取调度方案对应的播发情况
			List<EbmBrdRecord> brdRecordList = ebmBrdRecordDAO.findAll(getBrdSpec(ebm.getEbmId()));
			String localPsEbrId = this.getEbrPlatFormID();

			for (EbmBrdRecord ebmBrdRecord : brdRecordList) {
				String brdItemId = ebmBrdRecord.getBrdItemId();
				List<EbmRes> ebmResList = ebmResDAO.findByBrdItemId(brdItemId);

				for (EbmRes ebmRes : ebmResList) {

					String ebrId = ebmRes.getEbmResourceId();
					List<EbmResBs> ebmResBsList = ebmResBsDAO.findByEbmResourceId(ebrId);

					// 本级平台播出系统
					if (ebmRes.getEbrpsId().equals(localPsEbrId)) {

						for (EbmResBs ebmResBs : ebmResBsList) {
							Integer brdStateCode = ebmResBs.getBrdStateCode();
							if (brdStateCode.equals(BroadcastStateEnum.inprogress.getCode())) {
								bsInProcessCount = bsInProcessCount + 1;
							} else if (brdStateCode.equals(BroadcastStateEnum.succeeded.getCode())) {
								bsBrdSuccessCount = bsBrdSuccessCount + 1;
							}
						}
					} else { // 下级平台（只要有播出系统有播发中，认为启动广播）
						for (EbmResBs ebmResBs : ebmResBsList) {
							Integer brdStateCode = ebmResBs.getBrdStateCode();
							if (brdStateCode.equals(BroadcastStateEnum.inprogress.getCode())) {
								psInProcessCount = psInProcessCount + 1;
								break;
							} else if (brdStateCode.equals(BroadcastStateEnum.succeeded.getCode())) {
								psBrdSuccessCount = psBrdSuccessCount + 1;
								break;
							}
						}
					}
				}
			}

			dispatchMap.put(ResultKey.BS_INPROCESS_COUNT, bsInProcessCount);
			dispatchMap.put(ResultKey.PS_INPROCESS_COUNT, psInProcessCount);
			dispatchMap.put(ResultKey.BS_BRD_SUCCESS_COUNT, bsBrdSuccessCount);
			dispatchMap.put(ResultKey.PS_BRD_SUCCESS_COUNT, psBrdSuccessCount);
		}

		return dispatchMap;
	}

	/**
	 * 播发情况统计(根据调度方案)<br>
	 * 根据方案Id获取播发的EBM，再获取EBM对应的播发记录BrdLog,根据播发记录对应资源状态进行统计；
	 * 
	 * @return Map<String, List<EbmResBs>>
	 */
	public Map<String, List<EbmResBs>> brdStatByScheme(Integer schemeId) {

		List<EbmResBs> ebmResBs0 = new ArrayList<EbmResBs>();
		List<EbmResBs> ebmResBs1 = new ArrayList<EbmResBs>();
		List<EbmResBs> ebmResBs2 = new ArrayList<EbmResBs>();
		List<EbmResBs> ebmResBs3 = new ArrayList<EbmResBs>();
		List<EbmResBs> ebmResBs4 = new ArrayList<EbmResBs>();
		List<EbmResBs> ebmResBs5 = new ArrayList<EbmResBs>();

		// 根据调度方案获取调度消息
		List<Ebm> ebmList = ebmDAO.findBySchemeId(schemeId);
		for (Ebm ebm : ebmList) {
			// 获取调度方案对应的播发情况
			List<EbmBrdRecord> brdRecordList = ebmBrdRecordDAO.findAll(getBrdSpec(ebm.getEbmId()));

			for (EbmBrdRecord ebmBrdRecord : brdRecordList) {
				String brdItemId = ebmBrdRecord.getBrdItemId();

				List<EbmRes> ebmResList = ebmResDAO.findByBrdItemId(brdItemId);

				for (EbmRes ebmRes : ebmResList) {
					String ebrId = ebmRes.getEbmResourceId();
					String psEbrId = ebmRes.getEbrpsId();
					List<EbmResBs> ebmResBsList = ebmResBsDAO.findByEbmResourceId(ebrId);

					EbrPlatform ebrPlatform = ebrPlatformDAO.findOne(psEbrId);
					String areaCode = ebrPlatform.getAreaCode();
					RegionArea regionArea = regionAreaDAO.findOne(areaCode);

					for (EbmResBs ebmResBs : ebmResBsList) {

						ebmResBs.setAreaCode(areaCode);

						if (CommonUtil.isNotEmpty(regionArea)) {
							ebmResBs.setAreaName(regionArea.getAreaName());
						}

						Integer brdStateCode = ebmResBs.getBrdStateCode();
						if (brdStateCode.equals(BroadcastStateEnum.noprogress.getCode())) {
							ebmResBs0.add(ebmResBs);
						} else if (brdStateCode.equals(BroadcastStateEnum.timetogo.getCode())) {
							ebmResBs1.add(ebmResBs);
						} else if (brdStateCode.equals(BroadcastStateEnum.inprogress.getCode())) {
							ebmResBs2.add(ebmResBs);
						} else if (brdStateCode.equals(BroadcastStateEnum.succeeded.getCode())) {
							ebmResBs3.add(ebmResBs);
						} else if (brdStateCode.equals(BroadcastStateEnum.failed.getCode())) {
							ebmResBs4.add(ebmResBs);
						} else if (brdStateCode.equals(BroadcastStateEnum.cancelled.getCode())) {
							ebmResBs5.add(ebmResBs);
						}
					}
				}
			}
		}

		// 播发记录播发状态统计
		// 响应结果
		Map<String, List<EbmResBs>> statMap = new HashMap<String, List<EbmResBs>>();
		statMap.put(BroadcastStateEnum.noprogress.name(), ebmResBs0);
		statMap.put(BroadcastStateEnum.timetogo.name(), ebmResBs1);
		statMap.put(BroadcastStateEnum.inprogress.name(), ebmResBs2);
		statMap.put(BroadcastStateEnum.succeeded.name(), ebmResBs3);
		statMap.put(BroadcastStateEnum.failed.name(), ebmResBs4);
		statMap.put(BroadcastStateEnum.cancelled.name(), ebmResBs5);

		return statMap;
	}

	/**
	 * 根据调度方案查询调度方案关联资源
	 * 
	 * @param schemeId
	 * @return List<SchemeEbr>
	 */
	public List<SchemeEbr> findBySchemeId(Integer schemeId) {
		return schemeEbrDAO.findBySchemeId(schemeId);
	}

	/**
	 * 实现多条件模糊和带日期区间查询Specification
	 * 
	 * @param req
	 * @return Specification<EbmBrdRecord>
	 */
	private Specification<EbmBrdRecord> getBrdSpec(final String ebmId) {

		// 查询参数
		return new Specification<EbmBrdRecord>() {
			@Override
			public Predicate toPredicate(Root<EbmBrdRecord> r, CriteriaQuery<?> q,
					CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();

				if (CommonUtil.isNotEmpty(ebmId)) {
					predicate.getExpressions().add(cb.equal(r.<String> get("ebmId"), ebmId));
				}

				return predicate;
			}
		};
	}

	/**
	 * 实现多条件模糊和带日期区间查询Specification
	 * 
	 * @param req
	 * @return Specification<SchemeEbr>
	 */
	private Specification<SchemeEbr> getEbrSpec(final Integer schemeId, final String ebrType) {

		// 查询参数

		return new Specification<SchemeEbr>() {
			@Override
			public Predicate toPredicate(Root<SchemeEbr> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();

				if (CommonUtil.isNotEmpty(schemeId)) {
					predicate.getExpressions().add(cb.equal(r.<Integer> get("schemeId"), schemeId));
				}

				if (CommonUtil.isNotEmpty(ebrType)) {
					predicate.getExpressions().add(cb.equal(r.<String> get("ebrType"), ebrType));
				}

				return predicate;
			}
		};
	}
}