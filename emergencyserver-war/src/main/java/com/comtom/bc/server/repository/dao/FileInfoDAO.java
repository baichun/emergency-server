package com.comtom.bc.server.repository.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.comtom.bc.server.repository.entity.FileInfo;
import com.comtom.bc.server.repository.support.CustomRepository;
import org.springframework.data.repository.query.Param;

/**
 * 文件信息-数据访问层接口定义
 * 
 * @author zhucanhui
 *
 */
public interface FileInfoDAO extends CustomRepository<FileInfo, Long> {

	/**
	 * 根据文件目录Id获取对应的文件
	 * 
	 * @param libId
	 *            文件目录Id
	 * @return List<FileInfo>
	 */
	public List<FileInfo> findByLibId(Long libId);
	
	/**
	 * 根据md5code获取对应的文件
	 * @param md5Code
	 * @return FileInfo
	 */
	public FileInfo findByMd5Code(String md5Code);

	/**
	 * 删除文件信息
	 * 
	 * @param id
	 */
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM FileInfo WHERE ID=:id")
	public void deleteFileInfo(Long id);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM bc_file_info WHERE ID in (:fileIds)",nativeQuery = true)
	public void deleteByIdsBatch(@Param("fileIds") List<Long> fileIds);
}
