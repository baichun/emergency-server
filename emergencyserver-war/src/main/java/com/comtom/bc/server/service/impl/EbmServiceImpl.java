package com.comtom.bc.server.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.comtom.bc.exchange.commom.EBDType;
import com.comtom.bc.exchange.commom.SendFlag;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.details.other.EBM;
import com.comtom.bc.exchange.model.ebd.ebm.Auxiliary;
import com.comtom.bc.exchange.model.ebd.ebm.MsgBasicInfo;
import com.comtom.bc.exchange.model.ebd.ebm.MsgContent;
import com.comtom.bc.exchange.model.signature.Signature;
import com.comtom.bc.exchange.util.EBDModelBuild;
import com.comtom.bc.exchange.util.FileUtil;
import com.comtom.bc.exchange.util.HttpRequestUtil;
import com.comtom.bc.exchange.util.TarFileUtil;
import com.comtom.bc.server.ebd.impl.EBMService;
import com.comtom.bc.server.req.EbmReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.server.repository.dao.EbdDAO;
import com.comtom.bc.server.repository.dao.EbdFileDAO;
import com.comtom.bc.server.repository.dao.EbmAuxiliaryDAO;
import com.comtom.bc.server.repository.dao.EbmDAO;
import com.comtom.bc.server.repository.dao.EbmDispatchDAO;
import com.comtom.bc.server.repository.dao.EbrBsDAO;
import com.comtom.bc.server.repository.dao.EbrPlatformDAO;
import com.comtom.bc.server.repository.dao.FileInfoDAO;
import com.comtom.bc.server.repository.dao.ProgramFilesDAO;
import com.comtom.bc.server.repository.dao.RegionAreaDAO;
import com.comtom.bc.server.repository.dao.SchemeDAO;
import com.comtom.bc.server.repository.dao.VEbmDispatchDAO;
import com.comtom.bc.server.repository.entity.EbdFile;
import com.comtom.bc.server.repository.entity.Ebm;
import com.comtom.bc.server.repository.entity.EbmDispatch;
import com.comtom.bc.server.repository.entity.EbrBroadcast;
import com.comtom.bc.server.repository.entity.EbrPlatform;
import com.comtom.bc.server.repository.entity.FileInfo;
import com.comtom.bc.server.repository.entity.ProgramFiles;
import com.comtom.bc.server.repository.entity.RegionArea;
import com.comtom.bc.server.repository.entity.SchemeInfo;
import com.comtom.bc.server.repository.entity.VEbmDispatch;
import com.comtom.bc.server.req.EbmQueryReq;
import com.comtom.bc.server.service.DispatchFlowService;
import com.comtom.bc.server.service.EbmService;
import com.comtom.bc.server.service.LogUserService;
import com.comtom.bc.server.service.base.BaseService;
import org.springframework.util.CollectionUtils;

/**
 * 预警消息和广播指令-业务逻辑处理接口定义<br>
 * 接收EBM-预警消息<br>
 * 发送EBM-广播指令<br>
 * 
 * @author zhucanhui
 *
 */
@Service("EbmService")
public class EbmServiceImpl extends BaseService implements EbmService {

	@Autowired
	private LogUserService logUserService;

	@Autowired
	private DispatchFlowService dispatchFlowService;

	@Autowired
	private EbdDAO ebdDAO;

	@Autowired
	private EbdFileDAO ebdFileDAO;

	@Autowired
	private ProgramFilesDAO programFilesDAO;

	@Autowired
	private FileInfoDAO fileInfoDAO;

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
	private RegionAreaDAO regionAreaDAO;

	@Autowired
	private VEbmDispatchDAO vEbmDispatchDAO;

	@Autowired
	private EBMService ebmService;

	/**
	 * 根据条件获取EBM数据-预警消息<br>
	 * 
	 * @param req
	 * @return Page<Ebm>
	 */
	public Page<Ebm> getAlarmEbm(EbmQueryReq req) {
		return ebmDAO.findAll(getEbmSpec(req, Constants.RECEIVE_FLAG),
				buildPageRequest(req.getPageNum(), req.getPageSize(), getEbmSort()));
	}

	@Override
	public Page<Ebm> getEbm(EbmQueryReq req) {
		Page<Ebm> ebmPages = ebmDAO.findAll(getEbmSpec(req,null),
				buildPageRequest(req.getPageNum(), req.getPageSize(), getEbmSort()));

		for (Ebm ebm : ebmPages) {
			List<EbmDispatch> dispatchList = ebmDispatchDAO.findByEbmId(ebm.getEbmId());
			for (EbmDispatch ebmDispatch : dispatchList) {
				String bsEbrId = ebmDispatch.getBsEbrId();
				String psEbrId = ebmDispatch.getPsEbrId();

				if (CommonUtil.isNotEmpty(bsEbrId)) {
					EbrBroadcast ebrBroadcast = ebrBsDAO.findOne(bsEbrId);

					if (ebrBroadcast != null) {
						String areaCode = ebrBroadcast.getAreaCode();
						RegionArea regionArea = regionAreaDAO.getOne(areaCode);
						ebmDispatch.setBsEbrName(ebrBroadcast.getBsName());
						ebmDispatch.setAreaName(regionArea.getAreaName());
					}
				} else {
					EbrPlatform ebrPlatform = ebrPlatformDAO.findOne(psEbrId);

					if (ebrPlatform != null) {
						ebmDispatch.setPsEbrName(ebrPlatform.getPsEbrName());
						String areaCode = ebrPlatform.getAreaCode();
						RegionArea regionArea = regionAreaDAO.getOne(areaCode);
						ebmDispatch.setAreaName(regionArea.getAreaName());
					}
				}
			}

			ebm.setDispatchList(dispatchList);
		}
		return 	ebmPages;

	}
	/**
	 * 预警消息详情
	 * 
	 * @param req
	 * @return Ebm
	 */
	public Ebm getAlarmEbmDetail(EbmQueryReq req) {
		String ebmId = req.getEbmId();
		Ebm ebm = ebmDAO.findOne(ebmId);
		if(ebm==null) {
			return null;
		}

		String[] areaCodes = ebm.getAreaCode().split(String.valueOf(Constants.COMMA_SPLIT));

		StringBuffer bufArea = new StringBuffer();
		for (int i = 0; i < areaCodes.length; i++) {
			RegionArea regionArea = regionAreaDAO.findOne(areaCodes[i]);
			if(regionArea != null) {
				bufArea.append(regionArea.getAreaName());
				if (i < areaCodes.length - 1) {
					bufArea.append(Constants.COMMA_SPLIT);
				}
			}
		}

		// 根据消息获取调度方案信息
		SchemeInfo schemeInfo = null;
		if (CommonUtil.isNotEmpty(ebm)) {
			schemeInfo = schemeDAO.findOne(ebm.getSchemeId());
		}

		// 调度分发关联文件
		List<FileInfo> fileList = new ArrayList<FileInfo>();

		// 根据调度方案获取EBD
		if (CommonUtil.isNotEmpty(schemeInfo)) {
			String ebdId = schemeInfo.getEbdId();

			if (CommonUtil.isNotEmpty(ebdId)) {
				List<EbdFile> ebdFileList = ebdFileDAO.findByEbdId(ebdId);
				for (EbdFile ebdFile : ebdFileList) {
					FileInfo fileInfo = fileInfoDAO.findOne(ebdFile.getFileId().longValue());
					fileList.add(fileInfo);
				}
			}
		}

		ebm.setFileInfo(fileList);
		ebm.setAreaName(bufArea.toString());
		return ebm;
	}

	/**
	 * 根据条件获取EBM数据-广播指令消息<br>
	 * 
	 * @param req
	 * @return Page<VEbmDispatch>
	 */
	public Page<VEbmDispatch> getEbmDispatch(EbmQueryReq req) {
		return vEbmDispatchDAO.findAll(getDispatchSpec(req),
				buildPageRequest(req.getPageNum(), req.getPageSize()));
	}

	/**
	 * 根据条件获取EBM数据-广播指令消息<br>
	 * 
	 * @param req
	 * @return Page<VEbmDispatch>
	 */
	public VEbmDispatch getEbmDispatchDetail(EbmQueryReq req) {
		Integer dispatchId = req.getDispatchId();
		VEbmDispatch vEbmDispatch = vEbmDispatchDAO.findOne(dispatchId);

		String bsAreaCode = vEbmDispatch.getBsAreaCode();
		String psAreaCode = vEbmDispatch.getPsAreaCode();

		RegionArea regionArea = null;

		if (CommonUtil.isNotEmpty(bsAreaCode)) {
			regionArea = regionAreaDAO.findOne(bsAreaCode);
		} else {
			regionArea = regionAreaDAO.findOne(psAreaCode);
		}

		if (CommonUtil.isNotEmpty(regionArea)) {
			vEbmDispatch.setAreaName(regionArea.getAreaName());
		}

		// 获取调度EBM消息Id
		String ebmId = vEbmDispatch.getEbmId();
		Ebm ebm = null;
		if (CommonUtil.isNotEmpty(ebmId)) {
			ebm = ebmDAO.findOne(ebmId);
		}

		// 根据消息获取调度方案信息
		SchemeInfo schemeInfo = null;
		if (CommonUtil.isNotEmpty(ebm)) {
			schemeInfo = schemeDAO.findOne(ebm.getSchemeId());
		}

		// 调度分发关联文件
		List<FileInfo> fileList = new ArrayList<FileInfo>();

		// 根据调度方案获取EBD或节目
		if (CommonUtil.isNotEmpty(schemeInfo)) {
			String ebdId = schemeInfo.getEbdId();
			Long programId = schemeInfo.getProgramId();

			if (CommonUtil.isNotEmpty(programId)) {
				// 根据节目Id获取节目关联文件
				List<ProgramFiles> programFileList = programFilesDAO.findByProgramId(programId);
				for (ProgramFiles programFiles : programFileList) {
					FileInfo fileInfo = fileInfoDAO.findOne(programFiles.getFileId());
					fileList.add(fileInfo);
				}
			} else {
				List<EbdFile> ebdFileList = ebdFileDAO.findByEbdId(ebdId);
				for (EbdFile ebdFile : ebdFileList) {
					FileInfo fileInfo = fileInfoDAO.findOne(ebdFile.getFileId().longValue());
					fileList.add(fileInfo);
				}
			}
		}

		vEbmDispatch.setFileList(fileList);
		return vEbmDispatch;
	}

	@Override
	public List<EbmDispatch> getDispatch(String embId) {
		List<EbmDispatch> dispatchList = ebmDispatchDAO.findByEbmId(embId);
		if(CollectionUtils.isEmpty(dispatchList)){
			EbmDispatch ebmDispatch = new EbmDispatch();
			List<EbrBroadcast> ebrBroadcasts = ebrBsDAO.findAll();
			ebmDispatch.setState(0);
			ebmDispatch.setBsEbrId(ebrBroadcasts.get(0).getBsEbrId());
			ebmDispatch.setBsEbrName(ebrBroadcasts.get(0).getBsName());
			dispatchList.add(ebmDispatch);
			return dispatchList;
		}

			for (EbmDispatch ebmDispatch : dispatchList) {
			String bsEbrId = ebmDispatch.getBsEbrId();
			String psEbrId = ebmDispatch.getPsEbrId();

			if (CommonUtil.isNotEmpty(bsEbrId)) {
				EbrBroadcast ebrBroadcast = ebrBsDAO.findOne(bsEbrId);

				if (ebrBroadcast != null) {
					String areaCode = ebrBroadcast.getAreaCode();
					RegionArea regionArea = regionAreaDAO.getOne(areaCode);
					ebmDispatch.setBsEbrName(ebrBroadcast.getBsName());
					ebmDispatch.setAreaName(regionArea.getAreaName());
				}
			} else {
				EbrPlatform ebrPlatform = ebrPlatformDAO.findOne(psEbrId);

				if (ebrPlatform != null) {
					ebmDispatch.setPsEbrName(ebrPlatform.getPsEbrName());
					String areaCode = ebrPlatform.getAreaCode();
					RegionArea regionArea = regionAreaDAO.getOne(areaCode);
					ebmDispatch.setAreaName(regionArea.getAreaName());
				}
			}
		}
		return dispatchList;
	}


	/**
	 * 根据条件获取EBM数据-广播指令消息<br>
	 * 
	 * @param req
	 * @return Page<Ebm>
	 */
	public Page<Ebm> getDispatchEbm(EbmQueryReq req) {
		Page<Ebm> ebmPages = ebmDAO.findAll(getEbmSpec(req, Constants.SEND_FLAG),
				buildPageRequest(req.getPageNum(), req.getPageSize(), getEbmSort()));

		for (Ebm ebm : ebmPages) {
			List<EbmDispatch> dispatchList = ebmDispatchDAO.findByEbmId(ebm.getEbmId());
			for (EbmDispatch ebmDispatch : dispatchList) {
				String bsEbrId = ebmDispatch.getBsEbrId();
				String psEbrId = ebmDispatch.getPsEbrId();

				if (CommonUtil.isNotEmpty(bsEbrId)) {
					EbrBroadcast ebrBroadcast = ebrBsDAO.findOne(bsEbrId);

					if (ebrBroadcast != null) {
						String areaCode = ebrBroadcast.getAreaCode();
						RegionArea regionArea = regionAreaDAO.getOne(areaCode);
						ebmDispatch.setBsEbrName(ebrBroadcast.getBsName());
						ebmDispatch.setAreaName(regionArea.getAreaName());
					}
				} else {
					EbrPlatform ebrPlatform = ebrPlatformDAO.findOne(psEbrId);

					if (ebrPlatform != null) {
						ebmDispatch.setPsEbrName(ebrPlatform.getPsEbrName());
						String areaCode = ebrPlatform.getAreaCode();
						RegionArea regionArea = regionAreaDAO.getOne(areaCode);
						ebmDispatch.setAreaName(regionArea.getAreaName());
					}
				}
			}

			ebm.setDispatchList(dispatchList);
		}

		return ebmPages;
	}

	/**
	 * 实现多条件查询Specification
	 * 
	 * @param req
	 * @return Specification<Ebm>
	 */
	private Specification<VEbmDispatch> getDispatchSpec(final EbmQueryReq req) {
		final String msgTitle = req.getMsgTitle();
		final String msgDesc = req.getMsgDesc();
		final Date startDate = req.getStartDate();
		final Date endDate = req.getEndDate();
		final Integer severity = req.getSeverity();

		return new Specification<VEbmDispatch>() {
			@Override
			public Predicate toPredicate(Root<VEbmDispatch> r, CriteriaQuery<?> q,
					CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();

				if (CommonUtil.isNotEmpty(msgTitle)) {
					predicate.getExpressions().add(
							cb.like(r.<String> get("msgTitle"), "%" + msgTitle + "%"));
				}

				if (CommonUtil.isNotEmpty(msgDesc)) {
					predicate.getExpressions().add(
							cb.like(r.<String> get("msgDesc"), "%" + msgDesc + "%"));
				}

				if (CommonUtil.isNotEmpty(startDate)) {
					predicate.getExpressions().add(
							cb.greaterThanOrEqualTo(r.<Date> get("createTime"), startDate));
				}

				if (CommonUtil.isNotEmpty(endDate)) {
					predicate.getExpressions().add(
							cb.lessThanOrEqualTo(r.<Date> get("createTime"), endDate));
				}

				if (CommonUtil.isNotEmpty(severity)) {
					predicate.getExpressions().add(cb.equal(r.<Integer> get("severity"), severity));
				}

				return predicate;
			}
		};
	}

	/**
	 * 实现多条件查询Specification
	 * 
	 * @param req
	 * @return Specification<Ebm>
	 */
	private Specification<Ebm> getEbmSpec(final EbmQueryReq req, final Integer sendFlag) {

		final String msgTitle = req.getMsgTitle();
		final String ebmId = req.getEbmId();
		final String msgDesc = req.getMsgDesc();
		final Date startDate = req.getStartDate();
		final Date endDate = req.getEndDate();
		final Integer severity = req.getSeverity();

		return new Specification<Ebm>() {
			@Override
			public Predicate toPredicate(Root<Ebm> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();

				if (CommonUtil.isNotEmpty(msgTitle)) {
					predicate.getExpressions().add(
							cb.like(r.<String> get("msgTitle"), "%" + msgTitle + "%"));
				}
				if (CommonUtil.isNotEmpty(ebmId)) {
					predicate.getExpressions().add(cb.equal(r.<String> get("ebmId"), ebmId));
				}

				if (CommonUtil.isNotEmpty(msgDesc)) {
					predicate.getExpressions().add(
							cb.like(r.<String> get("msgDesc"), "%" + msgDesc + "%"));
				}

				if (CommonUtil.isNotEmpty(startDate)) {
					predicate.getExpressions().add(
							cb.greaterThanOrEqualTo(r.<Date> get("createTime"), startDate));
				}

				if (CommonUtil.isNotEmpty(endDate)) {
					predicate.getExpressions().add(
							cb.lessThanOrEqualTo(r.<Date> get("createTime"), endDate));
				}

				if (CommonUtil.isNotEmpty(severity)) {
					predicate.getExpressions().add(cb.equal(r.<Integer> get("severity"), severity));
				}

				if (CommonUtil.isNotEmpty(sendFlag)) {
					predicate.getExpressions().add(cb.equal(r.<Integer> get("sendFlag"), sendFlag));
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
		Order orderTime = new Order(Direction.DESC, "createTime");

		List<Order> orders = new ArrayList<Order>();
		orders.add(orderTime);
		Sort sort = new Sort(orders);
		return sort;
	}

	@Override
	public EBD sendEbm(EbmReq ebmReq) {
		MsgContent msgContent = new MsgContent();
		msgContent.setAreaCode(ebmReq.getAreaCode());
		msgContent.setLanguageCode(ebmReq.getMsgLanguageCode());
		msgContent.setMsgTitle(ebmReq.getMsgTitle());
		msgContent.setMsgDesc(ebmReq.getMsgDesc());
		//	msgContent.setProgramNum(ebmReq.getp);


		MsgBasicInfo msgBasicInfo = new MsgBasicInfo();
		msgBasicInfo.setEventType(ebmReq.getEventType());
		msgBasicInfo.setSenderCode(ebmReq.getSenderCode());
		msgBasicInfo.setSenderName(ebmReq.getSendName());
		msgBasicInfo.setSeverity(ebmReq.getSeverity());
		msgBasicInfo.setStartTime(ebmReq.getStartTime());
		msgBasicInfo.setEndTime(ebmReq.getEndTime());
		msgBasicInfo.setSendTime(ebmReq.getSendTime());
		msgBasicInfo.setMsgType(ebmReq.getMsgType());


//		Auxiliary auxiliary = new Auxiliary();
//		auxiliary.setAuxiliaryType(Constants.EBM_AUXILIARY_MP3);
//		auxiliary.setAuxiliaryDesc("test.mp3");
//		auxiliary.setSize(1024);
//		List<Auxiliary> auxiliaryList = new ArrayList();
//		auxiliaryList.add(auxiliary);
//		msgContent.setAuxiliaryList(auxiliaryList);


		EBM eBM = new EBM();
		eBM.setEBMID(getEbmId());
		eBM.setEBMVersion("1.0");
		eBM.setMsgBasicInfo(msgBasicInfo);
		eBM.setMsgContent(msgContent);
		String ebdIndex = generateEbdIndex();
		EBD ebd = EBDModelBuild.buildEBM(getEbrPlatFormID(), getPlatFormUrl(), ebdIndex, eBM);

		// 发送文件的路径（根据文件类型区分文件夹）
		String sendFilePath = getSendEbdUrl() + File.separator + ebd.getEBDType();
		// 对象转换文件
		File file = FileUtil.converFile(sendFilePath, ebd);
		// 生成签名和签名文件
		Signature signature = EBDModelBuild.buildSignature(file, ebd.getEBDID());
		File signFile = FileUtil.converFile(sendFilePath, signature);

		List<File> files = new ArrayList<File>();
		files.add(file);
		files.add(signFile);
		if(!CollectionUtils.isEmpty(msgContent.getAuxiliaryList())){
			File map3File = new File("C:\\test.mp3");
			files.add(map3File);
		}

		// 文件压缩
		File tar = TarFileUtil.compressorsTar(ebd, files, sendFilePath);
		// 接收文件保存路径
		String receviceFilePath = getReceiveEbdUrl();
		// 文件发送
		EBD ebdResponse = HttpRequestUtil.sendFile(tar, ebmReq.getRequestUrl(), receviceFilePath);



		return ebdResponse;
	}



}