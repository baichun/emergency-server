package com.comtom.bc.server.service;

import com.comtom.bc.server.repository.entity.Ebm;
import com.comtom.bc.server.repository.entity.InfoAccess;
import com.comtom.bc.server.req.InfoAccessQueryReq;
import org.springframework.data.domain.Page;


/**
 * 信息接入-业务逻辑处理接口定义
 * 
 *
 */
public interface InfoAccessService {


	 Page<InfoAccess> list(InfoAccessQueryReq infoAccessQueryReq);

	 InfoAccess save(Ebm ebm,String auditor,Integer auditResult,String auditOpinion);

	/**
	 * 详细信息查看
	 */
	 InfoAccess getInfo(long infoId);

	 InfoAccess update(InfoAccess infoAccess);

	 InfoAccess updateAuditState(long infoId, Integer auditResult,String auditOpinion,Integer auditType,String auditor);

	 void infoRemind(long id,String title);

}