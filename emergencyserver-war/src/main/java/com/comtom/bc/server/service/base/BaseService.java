package com.comtom.bc.server.service.base;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.utils.DateUtil;
import com.comtom.bc.exchange.util.EBDModelBuild;
import com.comtom.bc.server.repository.dao.EbrPlatformDAO;
import com.comtom.bc.server.repository.dao.SysOrgDAO;
import com.comtom.bc.server.repository.dao.SysParamDAO;
import com.comtom.bc.server.repository.dao.SysUserDAO;
import com.comtom.bc.server.repository.entity.EbrPlatform;
import com.comtom.bc.server.repository.entity.SysOrg;
import com.comtom.bc.server.repository.entity.SysUser;
import com.comtom.bc.server.service.SequenceService;

/**
 * 基础业务层组件
 * 
 * @author zhucanhui
 *
 */
public abstract class BaseService {

	@Autowired
	private SequenceService sequenceService;

	@Autowired
	private SysParamDAO sysParamDAO;

	@Autowired
	private SysOrgDAO sysOrgDAO;

	@Autowired
	private SysUserDAO sysUserDAO;

	@Autowired
	private EbrPlatformDAO ebrPlatformDAO;

	/**
	 * 获取键值参数
	 * 
	 * @param key
	 * @return String
	 */
	public String findValueByKey(String key) {
		return sysParamDAO.findValueByKey(key);
	}

	/**
	 * 获取EBD生成文件URL
	 * 
	 * @param key
	 * @return String
	 */
	public String getSendEbdUrl() {
		return findValueByKey(Constants.SEND_FILE_PATH);
	}

	/**
	 * 获取方案审核标识（1：需要审核 2：不需要审核）
	 * 
	 * @return String
	 */
	public String getSchemeAuditFlag() {
		return findValueByKey(Constants.SCHEME_AUDIT_FLAG);
	}

	/**
	 * 获取EBD生成文件URL
	 * 
	 * @param key
	 * @return String
	 */
	public String getSendEbdUrl(String ebdType) {
		return findValueByKey(Constants.SEND_FILE_PATH) + File.separator + ebdType;
	}

	/**
	 * 获取接收文件保存地址
	 * 
	 * @return
	 */
	public String getReceiveEbdUrl() {
		return findValueByKey(Constants.RECEIVE_FILE_PATH);
	}

	/**
	 * 获取平台地址网络连接地址
	 * 
	 * @return
	 */
	public String getPlatFormUrl() {
		return getEBRPSInfo().getPsUrl();
	}

	/**
	 * 获取上级平台的网络连接地址
	 * 
	 * @return
	 */
	public String getParentPlatUrl() {
		return findValueByKey(Constants.PARENT_PLATFORM_URL);
	}

	/**
	 * 获取本平台的资源ID编号
	 * 
	 * @return String
	 */
	public String getEbrPlatFormID() {
		return findValueByKey(Constants.EBR_PLATFORM_ID);
	}

	/**
	 * 获取本平台的区域编码
	 * 
	 * @return String
	 */
	public String getEbrPlatFormArea() {
		return findValueByKey(Constants.PLATFORM_AREA_CODE);
	}

	/**
	 * 生成ebd数据包序列号
	 * 
	 * @return String
	 */
	public String generateEbdIndex() {
		return sequenceService.createId(Constants.EBDID);
	}

	/**
	 * 生成ebm消息序列号
	 * 
	 * @return String
	 */
	public String generateEbmIndex() {
		return sequenceService.createId(Constants.EBMID);
	}

	/**
	 * 生成EBM消息Id<br>
	 * 格式：PLATFORMID+YYYYMMDD+0001<br>
	 * 例如：010143000000000001201612230001
	 * 
	 * @return
	 */
	public String getEbmId() {
		String ebmId = sequenceService.createId(Constants.EBMID);
		return findValueByKey(Constants.EBR_PLATFORM_ID)
				+ DateUtil.getDateTime(DateUtil.DATE_PATTERN.YYYYMMDD) + ebmId;
	}

	/**
	 * 生成非心跳EBD数据包Id<br>
	 * 格式(36位数字)：类型码(2位)+SRCEBRID(18位)+顺序码(16位)<br>
	 * 例如：100102430000000000070000000000000001
	 * 
	 * @return String
	 */
	public String getEbdId() {
		String ebdId = sequenceService.createId(Constants.EBDID);
		String ebrId = getEbrPlatFormID();
		return EBDModelBuild.EBDTYPE + ebrId + ebdId;
	}

	/**
	 * 构建PageRequest,设置对应的分页参数
	 * 
	 * @param pageNumber
	 * @param pagzSize
	 * @return PageRequest
	 */
	protected PageRequest buildPageRequest(int pageNumber, int pageSize) {
		return new PageRequest(pageNumber - 1, pageSize, null);
	}

	/**
	 * 构建PageRequest,设置对应的分页+排序参数
	 * 
	 * @param pageNumber
	 * @param pagzSize
	 * @return PageRequest
	 */
	protected PageRequest buildPageRequest(int pageNumber, int pageSize, Sort sort) {
		return new PageRequest(pageNumber - 1, pageSize, sort);
	}

	/**
	 * 根据用户帐号获取系统用户信息
	 * 
	 * @param account
	 * @return SysUser
	 */
	protected SysUser getSysUser(String account) {
		return sysUserDAO.findByAccountAndDeleteFlag(account, Constants.USER_NORMAL_FLAG);
	}

	/**
	 * 获取组织机构信息
	 * 
	 * @param cascadeId
	 * @return SysOrg
	 */
	protected SysOrg getUserOrg(String cascadeId) {
		return sysOrgDAO.findByCascadeId(cascadeId);
	}

	/**
	 * 获取平台信息
	 * 
	 * @return
	 */
	public EbrPlatform getEBRPSInfo() {
		String ebrPsId = getEbrPlatFormID();
		return ebrPlatformDAO.findOne(ebrPsId);
	}

}
