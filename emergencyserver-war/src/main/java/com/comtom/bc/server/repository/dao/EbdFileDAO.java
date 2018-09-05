package com.comtom.bc.server.repository.dao;

import java.util.List;

import com.comtom.bc.server.repository.entity.EbdFile;
import com.comtom.bc.server.repository.support.CustomRepository;

/**
 * 数据包关联文件-数据访问层接口定义
 * 
 * @author zhucanhui
 *
 */
public interface EbdFileDAO extends CustomRepository<EbdFile, Integer> {

	/**
	 * 根据EBDID获取EBD关联文件信息
	 * 
	 * @param ebdId
	 * @return List<EbdFile>
	 */
	public List<EbdFile> findByEbdId(String ebdId);
}
