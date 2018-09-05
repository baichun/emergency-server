package com.comtom.bc.server.service.impl;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.common.utils.DateUtil;
import com.comtom.bc.common.utils.RegionUtil;
import com.comtom.bc.dispatch.FlowConstants;
import com.comtom.bc.exchange.commom.EBDType;
import com.comtom.bc.exchange.commom.SendFlag;
import com.comtom.bc.exchange.commom.TimeOutStatusEnum;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.commom.EBRBS;
import com.comtom.bc.exchange.model.ebd.commom.EBRPS;
import com.comtom.bc.exchange.model.ebd.details.other.EBDResponse;
import com.comtom.bc.exchange.model.ebd.details.other.EBM;
import com.comtom.bc.exchange.model.ebd.ebm.*;
import com.comtom.bc.exchange.util.*;
import com.comtom.bc.server.asset.WebCxt;
import com.comtom.bc.server.repository.dao.*;
import com.comtom.bc.server.repository.entity.*;
import com.comtom.bc.server.req.EbdQueryReq;
import com.comtom.bc.server.service.DispatchFlowService;
import com.comtom.bc.server.service.EbdService;
import com.comtom.bc.server.service.LogUserService;
import com.comtom.bc.server.service.base.BaseService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 数据包处理-业务逻辑处理接口定义
 * 
 * @author zhucanhui
 *
 */
@Service("EbdService")
public class EbdServiceImpl extends BaseService implements EbdService {

	/**
	 * 日志记录器
	 */
	private static final Logger logger = LoggerFactory.getLogger(EbdServiceImpl.class);

	@Autowired
	private LogUserService logUserService;

	@Autowired
	private DispatchFlowService dispatchFlowService;

	@Autowired
	private EbdDAO ebdDAO;

	@Autowired
	private EbdFileDAO ebdFileDAO;

	@Autowired
	private EbmDAO ebmDAO;

	@Autowired
	private SchemeDAO schemeDAO;

	@Autowired
	private EbmAuxiliaryDAO ebmAuxiliaryDAO;

	@Autowired
	private EbmDispatchDAO ebmDispatchDAO;

	@Autowired
	private EbrPlatformDAO ebrPlatformDAO;

	@Autowired
	private EbrBsDAO ebrBsDAO;

	@Autowired
	private EbdResponseDAO ebdResponseDAO;

    @Autowired
    private RegionAreaDAO regionAreaDAO;

	/**
	 * 获取EBD数据包文件的URL
	 * 
	 * @param sendFlag
	 * @param ebdName
	 * @return String
	 */
	public String getEbdFilePath(Integer sendFlag, String ebdName, String ebdType,String ebdDestEbrId) {
		String prefixPath = null;
		if (CommonUtil.isNotEmpty(ebdType)) {
			prefixPath = getSendEbdUrl(ebdType);
		} else {
			prefixPath = getSendEbdUrl();
		}

		String ebdFilePath = prefixPath + File.separator;
		if (CommonUtil.isNotEmpty(ebdDestEbrId)) {
			ebdFilePath += ebdDestEbrId + File.separator;
		}
		ebdFilePath += ebdName;
		return ebdFilePath;
	}

	/**
	 * 根据EBD类型和状态获取EBD数据
	 * 
	 * @param key
	 * @return List<Ebd>
	 */
	public List<Ebd> getEbd(String ebdType, Integer ebdState) {
		return ebdDAO.findAll(getEbdSpec(ebdType, 2, ebdState), getEbdSort());
	}

	/**
	 * 根据EBDID获取EBD数据
	 * 
	 * @param ebdId
	 * @return Ebd
	 */
	public Ebd findEbd(String ebdId) {
		return ebdDAO.findOne(ebdId);
	}

	/**
	 * 根据EBM Id获取EBD
	 * 
	 * @param ebmId
	 * @return List<Ebd>
	 */
	public List<Ebd> findByEbmId(String ebmId) {
		return ebdDAO.findByEbmId(ebmId);
	}

	/**
	 * 根据条件获取EBD数据<br>
	 * 接收上级的预警EBD数据包
	 * 
	 * @param key
	 * @return Page<Ebd>
	 */
	public Page<Ebd> getEbd(EbdQueryReq req) {
		return ebdDAO.findAll(getEbdSpec(req.getEbdType(), 1, null),
				buildPageRequest(req.getPageNum(), req.getPageSize(), getEbdSort()));
	}

	/**
	 * 根据Ebm状态（创建）获取待调度分发的Ebm消息
	 * 
	 * @param key
	 * @return List<Ebm>
	 */
	public List<Ebm> getEbm(Integer ebmState) {
		return ebmDAO.findAll(getEbmSpec(ebmState), getEbmSort());
	}

	/**
	 * 根据EBDID获取EBD数据包
	 * 
	 * @param ebdId
	 * @return Ebd
	 */
	public Ebd getEbdDetail(String ebdId) {

		// Step.1 获取EBD信息
		Ebd ebd = ebdDAO.findOne(ebdId);

		// Step.2 获取EBM信息
		Ebm ebm = null;
		if (CommonUtil.isNotEmpty(ebd.getEbmId())) {
			ebm = ebmDAO.findOne(ebd.getEbmId());
		}

		// Step.3 获取EBD关联文件
		List<EbdFile> ebdFileList = ebdFileDAO.findByEbdId(ebdId);

		// Step.4 获取其它内容

		ebd.setEbm(ebm);
		ebd.setEbdFileList(ebdFileList);

		return ebd;
	}

	/**
	 * 通过EBD分为发EBM消息
	 * 
	 * @return int
	 */
	public int dispatchEbdPack(Integer ebmState) {
		List<EBD> ebdPackList = getEbdPack(ebmState);
		if (ebdPackList == null || ebdPackList.isEmpty()) {
			// logger.info("EbdServiceImpl.dispatchEbmEbdPack get process ebd pack count is 0.");
		}
		for (EBD ebd : ebdPackList) {
			logger.info("~~~~~~~~~~~~ start 往大喇叭发送EBM消息 ~~~~~~~~~~~~~...");

			String requestUrl = null;

			EBM ebm = ebd.getEBM();
			List<Dispatch> dispatchList = ebm.getDispatchList();

			for (Dispatch dispatch : dispatchList) {
				EBRPS ebrPs = dispatch.getEBRPS();
				EBRBS ebrBs = dispatch.getEBRBS();

				if (ebrPs != null) {
					requestUrl = ebrPs.getURL();
					ebrPs.setURL(null);
				} else if (ebrBs != null) {
					requestUrl = ebrBs.getURL();
					ebrBs.setURL(null);
				}
			}
			
			//TODO 为了安徽联测，消息类型添加了 3: 模拟演练，4：实际演练； 大喇叭只支持请求播发和取消播发，在此特做处理，如果是3和4统一改为1,发往大喇叭
			if(ebm.getMsgBasicInfo().getMsgType() == 3 || ebm.getMsgBasicInfo().getMsgType() == 4){
				ebm.getMsgBasicInfo().setMsgType(1); // 设置为请求播发

			}
			if (StringUtils.isEmpty(ebm.getMsgContent().getAreaCode())) {
				logger.info(ebm.getEBMID() + " 该条消息的覆盖区域在系统中查找不到");
				savaOrUpdateDate(null,requestUrl,null,ebd);
				continue;
			}
			ebm.getMsgBasicInfo().setSeverity(5);  //不管什么级别都设为普通;都按时间播发
			Ebm ebms = ebmDAO.findOne(ebm.getEBMID());
			if(ebms != null && ebms.getTimeOut() == TimeOutStatusEnum.overtime.getValue()){
				ebm.getMsgBasicInfo().setSeverity(1); //如果超过开始时间；并且结束时间大于当前时间则设为紧急；马上播发一次
			}

			// EBD数据包文件
			List<File> fileList = new ArrayList<File>();

			// 根据EBD获取关联文件
			List<EbdFile> ebdFileList = ebdFileDAO.findByEbdId(ebd.getEBDID());
			for (EbdFile ebdFile : ebdFileList) {
				File file = new File(ebdFile.getFileUrl());
				fileList.add(file);
			}
			String filePath = this.getSendEbdUrl() + File.separator + ebd.getEBDType();
			filePath += File.separator + ebd.getDEST().getEBRID();
			// 根据EBD生成文件和签名文件
			File xmlFile = FileUtil.converFile(filePath, ebd);
			fileList.add(xmlFile);

			// 生成签名和签名文件
			//TODO 解决签名问题
			/*
			 * Signature signature = EBDModelBuild.buildSignature(xmlFile, ebd.getEBDID());
			File signFile = FileUtil.converFile(filePath, signature);
			fileList.add(signFile);
			*/

			// 生成联动TAR包
			File tarFile = TarFileUtil.compressorsTar(ebd, fileList, filePath);

			// 接收文件保存路径
			String filePathReceive = this.getReceiveEbdUrl();

			// 上传文件至对应平台或播出系统资源，并接收到通用反馈
			EBD ebdPack = HttpRequestUtil.sendFile(tarFile, requestUrl, filePathReceive);

			savaOrUpdateDate(ebdPack,requestUrl,xmlFile,ebd);

			logger.info("往大喇叭发送EBM消息结束...");
		}

		return ebdPackList.size();
	}

	/**
	 * 上传到播出系统后，保存日志，更新状态
	 * @param ebdPack
	 * @param requestUrl
	 * @param xmlFile
	 * @param ebd
	 */
	@Transactional
	private void savaOrUpdateDate(EBD ebdPack,String requestUrl,File xmlFile,EBD ebd){
		if (ebdPack != null) {
			String ebmId=ebd.getEBM().getEBMID();
			Ebm ebmInfo = ebmDAO.findOne(ebmId);
			SchemeInfo schemeInfo = schemeDAO.findOne(ebmInfo.getSchemeId());
			// 更新流程：消息下发
			dispatchFlowService.updateFlow(schemeInfo.getProgramId(), schemeInfo.getEbdId(),
					FlowConstants.STAGE_PROCESS, FlowConstants.STATE_MSG_SEND);
			// 记录操作日志
			// 日志内容
			StringBuffer buf = new StringBuffer();
			buf.append("调度分发【");
			buf.append(ebdPack.getEBDID());
			buf.append("】数据包至【");
			buf.append(requestUrl);
			buf.append("】发送成功.");

			LogUser logUser = new LogUser();
			logUser.setUserName("admin");
			logUser.setModule("调度控制");
			logUser.setOperation("调度分发");
			logUser.setLogTime(DateUtil.getDateTime(DateUtil.DATE_PATTERN.YYYY_MM_DD_HH_MM_SS));
			logUser.setContent(buf.toString());
			logUser.setClientIp(WebCxt.getClientIpAddr());
			logUser.setPortalType(Constants.DISPATCH_CLIENT);

			// 记录用户操作日志
			logUserService.save(logUser);

			// 更新EBD和EBM调度状态
			updateEbmState(ebmId, Constants.EBM_STATE_SEND_SUCCESS);
			// 更新EBM调度分发记录状态为：已发送
			updateEbmDispatchState(ebd.getEBDID(), Constants.DISPATCH_STATE_DONE);

			// 保存接收数据包信息
			recordEbd(ebdPack, xmlFile.getName(), SendFlag.receive);

			// 获取接收通用反馈信息
			EBDResponse response = ebdPack.getEBDResponse();
			if (response == null) {
				logger.error("获取接收通用反馈数据异常.");
			} else {
				// 保存接收通用反馈信息
				recordEbdResponse(ebd, response, SendFlag.receive);
			}

		} else {

			// 增加发送次数处理，达到尝试次数

			// 发送失败，记录
			// 记录操作日志
			// 日志内容
			StringBuffer buf = new StringBuffer();
			buf.append("调度分发【");
			buf.append(ebd.getEBDID());
			buf.append("】数据包至【");
			buf.append(requestUrl);
			buf.append("】发送失败.");

			LogUser logUser = new LogUser();
			logUser.setUserName("--");
			logUser.setModule("调度控制");
			logUser.setOperation("调度分发");
			logUser.setLogTime(DateUtil.getDateTime(DateUtil.DATE_PATTERN.YYYY_MM_DD_HH_MM_SS));
			logUser.setContent(buf.toString());
			logUser.setClientIp(WebCxt.getClientIpAddr());

			// 记录用户操作日志
			logUserService.save(logUser);

			// 更新EBD和EBM状态
			updateEbdState(ebd.getEBDID(), Constants.EBD_STATE_SEND_FAILED);
			updateEbmState(ebd.getEBM().getEBMID(), Constants.EBM_STATE_SEND_FAILED);
		}
	}

	/**
	 * 处理完成，更新EBD状态
	 * 
	 * @return Ebd
	 */
	private Ebd updateEbdState(String ebdId, Integer ebdState) {
		Ebd ebd = ebdDAO.findOne(ebdId);

		if (ebd != null) {
			ebd.setEbdState(ebdState);
			return ebdDAO.save(ebd);
		}

		return null;
	}

	/**
	 * 处理完成，更新EBD状态
	 * 
	 * @param ebdId
	 * @param ebmState
	 * @return
	 */
	private EbmDispatch updateEbmDispatchState(String ebdId, Integer ebdState) {
		EbmDispatch ebmDispatch = ebmDispatchDAO.findByEbdId(ebdId);

		if (ebmDispatch != null) {
			ebmDispatch.setState(ebdState);
			ebmDispatch.setDispatchTime(new Date());
			return ebmDispatchDAO.save(ebmDispatch);
		}

		return null;
	}

	/**
	 * 处理完成，更新EBM状态
	 * 
	 * @param ebmId
	 * @param ebmState
	 * @return
	 */
	private Ebm updateEbmState(String ebmId, Integer ebmState) {
		Ebm ebm = ebmDAO.findOne(ebmId);

		if (ebm != null) {
			ebm.setEbmState(ebmState);
			ebm.setSendTime(new Date());
			return ebmDAO.save(ebm);
		}

		return null;
	}

	/**
	 * 根据Ebd生成协议分发EBD包
	 * 
	 * @return List<EBD>
	 */
	public List<EBD> getEbdPack(Integer ebmState) {

		List<EBD> ebdPackList = new ArrayList<EBD>();

		// 根据待发送的EBM和EBMDipatch进行分发
		List<Ebm> ebmList = getEbm(ebmState);

		// 获取本级平台信息
		String localPsURL = this.getPlatFormUrl();
		String localPsEbrId = this.getEbrPlatFormID();

		for (Ebm ebm : ebmList) {

			// 获取Ebm调度分发记录
			List<EbmDispatch> dispatchList = ebmDispatchDAO.findByEbmId(ebm.getEbmId());

			for (EbmDispatch ebmDispatch : dispatchList) {
				SRC src = new SRC();
				src.setEBRID(localPsEbrId);
				src.setURL(localPsURL);

				DEST dest = new DEST();

				String psEbrId = ebmDispatch.getPsEbrId();
				String bsEbrId = ebmDispatch.getBsEbrId();

				List<Dispatch> ebmDispatchList = new ArrayList<Dispatch>();
				Dispatch dispatch = new Dispatch();

				// 如果调度分发为播出系统，则设置资源为播出系统，否则设置平台
				if (CommonUtil.isNotEmpty(bsEbrId)) {
					dest.setEBRID(bsEbrId);

					EbrBroadcast ebrBroadcast = ebrBsDAO.findOne(ebmDispatch.getBsEbrId());

					EBRBS ebrBs = new EBRBS();
					ebrBs.setEBRID(ebrBroadcast.getBsEbrId());
					ebrBs.setURL(ebrBroadcast.getBsUrl());
					dispatch.setEBRBS(ebrBs);

				} else {
					dest.setEBRID(psEbrId);

					EbrPlatform ebrPlatform = ebrPlatformDAO.findOne(psEbrId);

					EBRPS ebrPs = new EBRPS();
					ebrPs.setEBRID(ebrPlatform.getPsEbrId());
					ebrPs.setURL(ebrPlatform.getPsUrl());
					dispatch.setEBRPS(ebrPs);
				}

				ebmDispatchList.add(dispatch);

				EBD ebdPack = new EBD();
				ebdPack.setEBDID(ebmDispatch.getEbdId());
				ebdPack.setSRC(src);
				ebdPack.setDEST(dest);
				ebdPack.setEBDType(EBDType.EBM.name());
				ebdPack.setEBDTime(DateUtil.getDateTime(DateUtil.DATE_PATTERN.YYYY_MM_DD_HH_MM_SS));

				String ebmId = ebm.getEbmId();
				List<EbmAuxiliary> auxiliaryList = ebmAuxiliaryDAO.findByEbmId(ebmId);

				MsgBasicInfo mbInfo = new MsgBasicInfo();
				mbInfo.setMsgType(ebm.getMsgType());
				mbInfo.setEventType(ebm.getEventType());
				mbInfo.setSenderCode(ebm.getSenderCode());
				mbInfo.setSenderName(ebm.getSendName());
				mbInfo.setSeverity(ebm.getSeverity());
				mbInfo.setSendTime(DateUtil.getDateTime());
				mbInfo.setStartTime(DateUtil.format(ebm.getStartTime(),
						DateUtil.DATE_PATTERN.YYYY_MM_DD_HH_MM_SS));
				mbInfo.setEndTime(DateUtil.format(ebm.getEndTime(),
						DateUtil.DATE_PATTERN.YYYY_MM_DD_HH_MM_SS));

				List<Auxiliary> auxiList = new ArrayList<Auxiliary>();
				for (EbmAuxiliary ebmAuxiliary : auxiliaryList) {
					Auxiliary auxiliary = new Auxiliary();
					auxiliary.setAuxiliaryType(ebmAuxiliary.getAuxiliaryType());
					auxiliary.setAuxiliaryDesc(ebmAuxiliary.getAuxiliaryDesc());
					auxiliary.setDigest(ebmAuxiliary.getAuxiliaryDigest());
					auxiliary.setSize(ebmAuxiliary.getAuxiliarySize());
					auxiList.add(auxiliary);
				}
				
//				String areaCode = ebm.getAreaCode();
//				// 如果传入多个覆盖区域，则取最后一个区域进行播放
//				if(StringUtils.isNotEmpty(areaCode) &&  StringUtils.contains(areaCode , Constants.COMMA_SPLIT)){
//					areaCode = areaCode.substring(areaCode.lastIndexOf(Constants.COMMA_SPLIT)+1);
//				}

				MsgContent mContent = new MsgContent();
				mContent.setMsgTitle(ebm.getMsgTitle());
				mContent.setMsgDesc(ebm.getMsgDesc());
				mContent.setLanguageCode(ebm.getMsgLanguageCode());
				mContent.setProgramNum(ebm.getProgramNum());
				String areaCode = getAreaCode(ebm.getAreaCode());
				mContent.setAreaCode(areaCode);
				mContent.setAuxiliaryList(auxiList);

				RelatedInfo relatedInfo = new RelatedInfo();
				relatedInfo.setEBMID(ebm.getRelatedEbmId());

				EBM ebmPack = new EBM();
				ebmPack.setEBMID(ebm.getEbmId());
				ebmPack.setEBMVersion(ebm.getEbmVersion());
				if(StringUtils.isNotEmpty(relatedInfo.getEBMID())){
					ebmPack.setRelatedInfo(relatedInfo);
				}
				ebmPack.setMsgBasicInfo(mbInfo);
				ebmPack.setMsgContent(mContent);
				ebmPack.setDispatchList(ebmDispatchList);
				ebdPack.setEBM(ebmPack);

				ebdPackList.add(ebdPack);
			}
		}

		return ebdPackList;
	}

	/**
	 * 保存接收数发送据包记录
	 * 
	 * @param ebd
	 * @param ebdName
	 */
	protected void recordEbd(EBD ebd, String ebdName, SendFlag sendFlag) {
		// 必选
		String ebdVersion = ebd.getEBDVersion();
		// 必选
		String ebdId = ebd.getEBDID();
		// 必选
		String ebdType = ebd.getEBDType();
		// 必选
		String ebdTimeString = ebd.getEBDTime();
		Date ebdTime = DateTimeUtil.stringToDate(ebdTimeString,
				DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
		// 必选
		SRC src = ebd.getSRC();
		// 必选资源ID
		String ebdSrcEbrId = src.getEBRID();
		// 可选
		String ebdSrcUrl = src.getURL();
		// 可选
		DEST dest = ebd.getDEST();
		String ebdDestEbrId = null;
		if (dest != null) {
			// 目标对象的资源ID 必选
			ebdDestEbrId = dest.getEBRID();
		}
		// 可选
		RelatedEBD relatedEBD = ebd.getRelatedEBD();
		String relateEbdId = null;
		if (relatedEBD != null) {
			// 必选
			relateEbdId = relatedEBD.getEBDID();
		}

		Ebd arg0 = new Ebd();
		arg0.setEbdId(ebdId);
		arg0.setEbdVersion(ebdVersion);
		arg0.setEbdType(ebdType);
		arg0.setEbdName(FileNameUtil.generateTarFileName(ebdId));
		arg0.setEbdSrcEbrId(ebdSrcEbrId);
		arg0.setEbdDestEbrId(ebdDestEbrId);
		arg0.setEbdTime(ebdTime);
		arg0.setRelateEbdId(relateEbdId);
		arg0.setEbdSrcUrl(ebdSrcUrl);

		arg0.setSendFlag(sendFlag.getValue());
		arg0.setEbdState(null);

		if (SendFlag.receive.equals(sendFlag)) {
			arg0.setEbdRecvTime(new Date());
		} else if (SendFlag.send.equals(sendFlag)) {
			arg0.setEbdSendTime(new Date());
		}
		arg0.setFlowId(null);

		// 如果是EBM消息，设置EBD关联的EBM消息Id
		if (ebd.getEBM() != null) {
			arg0.setEbmId(ebd.getEBM().getEBMID());
		}
		ebdDAO.save(arg0);
	}

	/**
	 * 保存接收发送数据包响应数据
	 * 
	 * @param ebd
	 * @param ebdName
	 */
	protected void recordEbdResponse(EBD ebd, EBDResponse eBDResponse, SendFlag sendFlag) {

		String ebdVersion = ebd.getEBDVersion();
		String ebdSrcEbrId = ebd.getSRC().getEBRID();
		String relatedEbdId = null;
		if (ebd.getRelatedEBD() != null) {
			relatedEbdId = ebd.getRelatedEBD().getEBDID();
		}
		// 必选
		String ebdId = ebd.getEBDID();
		// 必选
		String ebdType = ebd.getEBDType();
		// 必选
		String ebdTimeString = ebd.getEBDTime();
		Date ebdTime = DateTimeUtil.stringToDate(ebdTimeString,
				DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
		Integer resultCode = eBDResponse.getResultCode();
		String resultDesc = eBDResponse.getResultDesc();

		EbdResponse ebdResponse = new EbdResponse();
		ebdResponse.setEbdId(ebdId);
		ebdResponse.setEbdTime(ebdTime);
		ebdResponse.setEbdType(ebdType);
		ebdResponse.setResultCode(resultCode);
		ebdResponse.setResultDesc(resultDesc);
		ebdResponse.setSendFlag(sendFlag.getValue());
		ebdResponse.setEbdVersion(ebdVersion);
		ebdResponse.setRelatedEbdId(relatedEbdId);
		ebdResponse.setEbdSrcEbrId(ebdSrcEbrId);
		ebdResponseDAO.save(ebdResponse);
	}

	/**
	 * 实现多条件模糊和带日期区间查询Specification
	 * 
	 * @param req
	 * @return Specification<SchemeInfo>
	 */
	private Specification<Ebd> getEbdSpec(final String ebdType, final Integer sendFlag,
			final Integer ebdState) {

		return new Specification<Ebd>() {
			@Override
			public Predicate toPredicate(Root<Ebd> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (CommonUtil.isNotEmpty(ebdType)) {
					predicate.getExpressions().add(
							cb.like(r.<String> get("ebdType"), "%" + StringUtils.trim(ebdType)
									+ "%"));
				}

				if (CommonUtil.isNotEmpty(sendFlag)) {
					predicate.getExpressions().add(cb.equal(r.<Integer> get("sendFlag"), sendFlag));
				}

				if (CommonUtil.isNotEmpty(ebdState)) {
					predicate.getExpressions().add(cb.equal(r.<Integer> get("ebdState"), ebdState));
				}

				return predicate;
			}
		};
	}

	/**
	 * 获取排序对象Sort
	 * 
	 * @return Sort
	 */
	private Sort getEbdSort() {
		Order orderTime = new Order(Direction.ASC, "ebdTime");
		Order ebdState = new Order(Direction.ASC, "ebdState");
		List<Order> orders = new ArrayList<Order>();
		orders.add(0, orderTime);
		orders.add(1, ebdState);
		Sort sort = new Sort(orders);
		return sort;
	}

	/**
	 * 实现多条件查询Specification
	 * 
	 * @param req
	 * @return Specification<Ebm>
	 */
	private Specification<Ebm> getEbmSpec(final Integer ebmState) {

		return new Specification<Ebm>() {
			@Override
			public Predicate toPredicate(Root<Ebm> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();

				if (CommonUtil.isNotEmpty(ebmState)) {
					predicate.getExpressions().add(cb.equal(r.<Integer> get("ebmState"), ebmState));
				}

				return predicate;
			}
		};
	}

	/**
	 * 获取排序对象Sort
	 * 
	 * @return Sort
	 */
	private Sort getEbmSort() {
		Order orderTime = new Order(Direction.DESC, "startTime");
		List<Order> orders = new ArrayList<Order>();
		orders.add(orderTime);
		Sort sort = new Sort(orders);
		return sort;
	}
	
	/**
	 * 下发到大喇叭平台的区域码处理；
	 * @param areaCode
	 * @return
	 */
//	private static String getAreaCode(String areaCode){
//		String[] areaCodes = areaCode.split(String.valueOf(Constants.COMMA_SPLIT));
//		StringBuffer codesStr = new StringBuffer();
//		for (int i = 0; i < areaCodes.length; i++) {
//			if(areaCodes[i].length() == 12){
//				codesStr.append(areaCodes[i]).append(Constants.COMMA_SPLIT);
//			}
//		}
//		if(StringUtils.isEmpty(codesStr.toString())){ //如果不存在村，则获取最后一个区域码进行播放
//		//	areaCode = areaCode.substring(areaCode.lastIndexOf(Constants.COMMA_SPLIT)+1);
//			return areaCode;
//		}
//		areaCode = codesStr.substring(0, codesStr.length() - 1);
//		return areaCode;
//	}

	/**
	 * 下发到大喇叭平台的区域码处理；
	 * @param areaCode
	 * @return
	 */
    private String getAreaCode(String areaCode){
        String[] areaCodes = areaCode.split(String.valueOf(Constants.COMMA_SPLIT));
        StringBuffer codesStr = new StringBuffer();
        StringBuffer xStr = new StringBuffer();
        for (int i = 0; i < areaCodes.length; i++) {
                RegionArea regionArea = regionAreaDAO.findOne(areaCodes[i].trim());
                if(regionArea == null){
                    continue;
                }
            if(areaCodes[i].length() == 12){
                codesStr.append(areaCodes[i].trim()).append(Constants.COMMA_SPLIT);
            }else{
                xStr.append(areaCodes[i].trim()).append(Constants.COMMA_SPLIT);
            }
        }
        if(StringUtils.isEmpty(codesStr.toString())){
            //	areaCode = areaCode.substring(areaCode.lastIndexOf(Constants.COMMA_SPLIT)+1);
			String str = xStr.length() != 0 ? xStr.substring(0, xStr.length() - 1) : StringUtils.EMPTY;
            return str;
        }
        areaCode = codesStr.substring(0, codesStr.length() - 1);
        return areaCode;
    }

}