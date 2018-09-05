package com.comtom.bc.server.service.impl;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.server.repository.dao.InfoAccessDAO;
import com.comtom.bc.server.repository.dao.ProgramDAO;
import com.comtom.bc.server.repository.dao.RegionAreaDAO;
import com.comtom.bc.server.repository.entity.*;
import com.comtom.bc.server.req.InfoAccessQueryReq;
import com.comtom.bc.server.service.InfoAccessService;
import com.comtom.bc.server.service.SchemeService;
import com.comtom.bc.server.service.base.BaseService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * @author wjd
 * @create 2018/7/26 0026
 * @desc ${DESCRIPTION}
 **/
@Service
public class InfoAccessServiceImpl extends BaseService implements InfoAccessService {

    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(InfoAccessServiceImpl.class);

    @Autowired
    private InfoAccessDAO infoAccessDAO;

    @Autowired
    private ProgramDAO programDAO;

    @Autowired
    private SchemeService schemeService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private RegionAreaDAO regionAreaDAO;


    @Override
    public Page<InfoAccess> list(InfoAccessQueryReq req) {
        Page<InfoAccess> infoAccess = infoAccessDAO.findAll(getSpec(req),
                buildPageRequest(req.getPageNum(), req.getPageSize(), getSort()));
        return infoAccess;
    }

    @Override
    public InfoAccess save(Ebm ebm,String auditor,Integer auditResult,String auditOpinion) {
        InfoAccess infoAccess = new InfoAccess();
        copyProperties(ebm, infoAccess);
        infoAccess.setRelatedEbmId(ebm.getEbmId());
        infoAccess.setMsgSource(Constants.INFO_SOURCE_SUPERIORS);
        if (auditResult == null) {
            infoAccess.setAuditResult(Constants.AUDIT_INIT);
        } else {
            infoAccess.setAuditResult(auditResult);
            infoAccess.setAuditTime(new Date());
        }
        if (StringUtils.isNotEmpty(auditor)) {
            infoAccess.setAuditor(auditor);
        }
        if (StringUtils.isNotEmpty(auditOpinion)) {
            infoAccess.setAuditOpinion(auditOpinion);
        }
        InfoAccess info=infoAccessDAO.save(infoAccess);
        if(info != null){
            infoRemind(info.getInfoId(),info.getMsgTitle());
        }
        return info;
    }

    private Specification<InfoAccess> getSpec(InfoAccessQueryReq req){
        final String msgTitle = req.getTitle();
        final String auditResult = req.getAuditResult();
        return new Specification<InfoAccess>() {
            @Override
            public Predicate toPredicate(Root<InfoAccess> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (CommonUtil.isNotEmpty(msgTitle)) {
                    predicate.getExpressions().add(
                            cb.like(r.<String> get("msgTitle"),
                                    "%" + StringUtils.trim(msgTitle) + "%"));
                }
                if (CommonUtil.isNotEmpty(auditResult)) {
                    predicate.getExpressions().add(
                            cb.equal(r.<String> get("auditResult"), auditResult));
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
    private Sort getSort() {
        Sort.Order orderState = new Sort.Order(Sort.Direction.ASC, "auditResult");
        Sort.Order orderTime = new Sort.Order(Sort.Direction.DESC, "createTime");
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        orders.add(0, orderState);
        orders.add(1, orderTime);
        Sort sort = new Sort(orders);
        return sort;
    }

    @Override
    public InfoAccess getInfo(long infoId) {
        InfoAccess info = infoAccessDAO.findOne(infoId);
        String areaCode=info.getAreaCode();
        StringBuffer bufArea = new StringBuffer();
        String[] areaCodes = areaCode.split(Constants.COMMA_SPLIT+"");
        for (int i = 0; i < areaCodes.length; i++) {
             RegionArea regionArea = regionAreaDAO.findOne(areaCodes[i]);
             bufArea.append(regionArea.getAreaName());

            if (i < areaCodes.length - 1) {
                bufArea.append(Constants.COMMA_SPLIT);
            }
        }
        info.setAreaName(bufArea.toString());
        return info;
    }

    @Override
    public InfoAccess update(InfoAccess infoAccess) {
        if (infoAccess.getInfoId() != null) {
            InfoAccess exist = infoAccessDAO.findOne(infoAccess.getInfoId());
            if (null != exist) {
                exist.setAuditTime(new Date());
                exist.setAuditResult(infoAccess.getAuditResult());
                exist.setAuditor(infoAccess.getAuditor());
                exist.setAuditOpinion(infoAccess.getAuditOpinion());
                InfoAccess info= infoAccessDAO.save(exist);
                synDispatch(info);
                return info;
            }
        }
        return null;
    }

    private void synDispatch(InfoAccess info){
        if(info.getMsgSource() == Constants.INFO_SOURCE_SELF){
            ProgramInfo programInfo = programDAO.getOne(Long.valueOf(info.getRelatedEbmId()));
            programInfo.setInfoId(info.getInfoId());
            // 审批通过 ,触发调度
            SchemeInfo schemeInfo = schemeService.synDispatch(programInfo);
            if (CommonUtil.isNotEmpty(schemeInfo)) {
                logger.info("InfoAccess.audit generate dispatch successfully.");
            }
        }else if(info.getMsgSource() == Constants.INFO_SOURCE_SUPERIORS){
            // 根据EBD触发调度方案
//            Future<SchemeInfo> result = schemeService.asynDispatch(ebd, dispatchFlow.getFlowId());
//
//            while (true) {
//                if (result.isDone()) {
//                    logger.info("EBMService.service generate dispatch scheme successfully.");
//                    break;
//                } else {
//                    try {
//                        synchronized (Thread.currentThread()) {
//                            Thread.currentThread().wait(100L);
//                        }
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    logger.debug("EBMService.service generate dispatch scheme is running.");
//                }
//            }
//            // 发送应急广播消息播发状态
//            eBMStateRequestService.sendResponse(ebmId, getParentPlatUrl(),null);
        }


    }


    public InfoAccess updateAuditState(long infoId, Integer auditResult,String auditOpinion,Integer auditType,String auditor) {
        InfoAccess exist = infoAccessDAO.getOne(infoId);
        if (exist != null) {
            exist.setAuditResult(auditResult);
            exist.setAuditTime(new Date());
            exist.setAuditType(auditType);
            exist.setAuditOpinion(auditOpinion);
            exist.setAuditor(auditor);
            return infoAccessDAO.save(exist);
        }
        return null;
    }

    @Override
    public void infoRemind(long id,String title) {
        String msg = "您好，您有一条消息标题为 %s 的应急消息，请注意查收及处理 ";
        messagingTemplate.convertAndSend("/topic/callback", String.format(msg,title));
    }



}
