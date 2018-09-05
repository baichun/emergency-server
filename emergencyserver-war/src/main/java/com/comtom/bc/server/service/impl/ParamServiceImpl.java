package com.comtom.bc.server.service.impl;

import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.server.repository.entity.ParamInfo;
import com.comtom.bc.server.repository.entity.ProgramInfo;
import com.comtom.bc.server.repository.entity.SysParam;
import com.comtom.bc.server.req.ParamQueryReq;
import com.comtom.bc.server.req.ParamUpdateReq;
import com.comtom.bc.server.req.ProgramQueryReq;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.comtom.bc.common.Constants;
import com.comtom.bc.server.repository.dao.SysParamDAO;
import com.comtom.bc.server.repository.entity.FileInfo;
import com.comtom.bc.server.service.ParamService;
import com.comtom.bc.server.service.base.BaseService;
import zk.jni.JavaToBiokey;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Calendar;
import java.util.Date;

/**
 * 用户-用户处理业务逻辑处理
 * 
 * @author zhucanhui
 *
 */
@Service("ParamService")
public class ParamServiceImpl extends BaseService implements ParamService {

	@Autowired
	private SysParamDAO sysParamDAO;

	/**
	 * 根据键值参数Key获取对象
	 * 
	 * @param key
	 * @return String
	 */
	public String findValueByKey(String key) {
		return sysParamDAO.findValueByKey(key);
	}

	/**
	 * 根据文件类型获取对象的文件目录名称
	 * 
	 * @param fileType
	 * @return String 文件目录名称
	 */
	public String getFileDirName(Integer fileType) {

		String dirName = null;

		// 根据文件类型匹配目录名称
		switch (fileType) {
		case 1:
			dirName = Constants.DIR_AUDIO;
			break;
		case 2:
			dirName = Constants.DIR_RECORD;
			break;
		case 3:
			dirName = Constants.DIR_TEXT;
			break;
		case 4:
			dirName = Constants.DIR_VIDEO;
			break;
		default:
			dirName = Constants.DIR_AUDIO;
			break;
		}

		return dirName;
	}

	/**
	 * 根据文件信息获取文件绝对地址
	 * 
	 * @param fileInfo
	 * @return String
	 */
	public String getFilePath(FileInfo fileInfo) {

		// 获取文件类型
		Integer fileType = fileInfo.getFileType();

		// 获取文件存放路径
		String filePath = this.findValueByKey(Constants.FILE_PATH);

		// 根据文件类型匹配目录名称
		String dirName = getFileDirName(fileType);

		StringBuffer buf = new StringBuffer();
		buf.append(filePath);
		buf.append(Constants.FILE_SPLIT);
		buf.append(dirName);
		buf.append(Constants.FILE_SPLIT);
		buf.append(fileInfo.getUploadedName());

		return buf.toString();
	}

	@Override
	public Page<SysParam> list(ParamQueryReq req) {

		Page<SysParam> page = sysParamDAO.findAll(getSpec(req),
				buildPageRequest(req.getPageNum(), req.getPageSize(), null));

		return page;
	}

	private Specification<SysParam> getSpec(ParamQueryReq req) {

		// 查询参数
		final String key = req.getKey();
		final String catalogId=req.getCatalogId();

		return new Specification<SysParam>() {
			@Override
			public Predicate toPredicate(Root<SysParam> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (CommonUtil.isNotEmpty(key)) {
					predicate.getExpressions().add(
							cb.equal(r.<String> get("key"), key));
				}
				if (CommonUtil.isNotEmpty(catalogId)) {
					predicate.getExpressions().add(
							cb.equal(r.<Integer> get("catalogId"), catalogId));
				}

				return predicate;
			}
		};
	}

	@Override
	public SysParam updateProgram(ParamUpdateReq req) {
		String paramId = req.getId();

		if (CommonUtil.isEmpty(paramId)) {
			return null;
		}

		// 获取节目信息
		SysParam paramInfo = sysParamDAO.findOne(paramId);
		if(paramInfo==null){
			return paramInfo;
		}

		//增加对指纹参数做特殊处理 key:fingerprint_threshold
		if(paramInfo.getKey().equals(Constants.FINGERPRINT_THRESHOLD)){
			//调用设置指纹阈值设置接口
			try{
				JavaToBiokey.NativeToSetThreshold(Integer.parseInt(paramInfo.getValue()),Integer.parseInt(paramInfo.getValue()));
			}catch (Exception e){
				return null;
			}
		}

		String value=req.getValue();
		String isOverwrite=req.getIsOverwrite();
		String overwriteField=req.getOverwriteField();
		paramInfo.setValue(value);
		paramInfo.setIsOverwrite(isOverwrite);
		paramInfo.setOverwriteField(overwriteField);
		return sysParamDAO.save(paramInfo);
	}
}
