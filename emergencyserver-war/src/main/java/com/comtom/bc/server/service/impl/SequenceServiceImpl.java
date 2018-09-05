package com.comtom.bc.server.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.server.repository.dao.SysSequenceDAO;
import com.comtom.bc.server.repository.entity.SysSequence;
import com.comtom.bc.server.service.SequenceService;

/**
 * 序列号处理-业务逻辑处理接口定义
 * 
 * @author zhucanhui
 *
 */
@Service("SequenceService")
public class SequenceServiceImpl implements SequenceService {

	@Autowired
	private SysSequenceDAO sysSequenceDAO;

	/**
	 * 根据序列号名称获取序列号信息
	 * 
	 * @param name
	 * @return SysSequence
	 */
	public SysSequence findByName(String name) {
		return sysSequenceDAO.findByName(name);
	}

	/**
	 * 生成ID
	 * 
	 * @param idname
	 * @return String
	 */
	public synchronized String createId(String idname) {

		String id = "";

		// 获取Id当前信息
		SysSequence sysSequence = findByName(idname);

		if (CommonUtil.isEmpty(sysSequence)) {
			return null;
		}

		// Id
		Long idLong = Long.valueOf(sysSequence.getCurValue());
		idLong = idLong + 1; // 将当前值+1做为本次ID值
		if (idLong > new Long(sysSequence.getMaxValue())) {
			// 号源用完
			if (sysSequence.getIsCircul().equals("1")) {
				// 从最小值开始循环
				idLong = new Long(sysSequence.getMinValue());
			} else {
				// 溢出
				return null;
			}
		}

		id = String.valueOf(idLong);

		// 补足
		if (StringUtils.equals(sysSequence.getIsLeftpad(), "1")) {
			id = StringUtils.leftPad(id, sysSequence.getMaxValue().length(), "0");
		}

		sysSequence.setCurValue(id);

		// 回写配置表
		sysSequence.setFormatValue(id);
		sysSequenceDAO.save(sysSequence);
		sysSequenceDAO.flush();
		return id;
	}
}