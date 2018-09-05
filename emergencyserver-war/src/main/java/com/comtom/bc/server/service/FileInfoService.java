package com.comtom.bc.server.service;

import java.util.List;

import com.comtom.bc.server.repository.entity.MediaTree;
import org.springframework.data.domain.Page;

import com.comtom.bc.server.repository.entity.FileInfo;
import com.comtom.bc.server.req.FileQueryReq;

/**
 * 键值参数-业务逻辑处理接口定义
 * 
 * @author zhucanhui
 *
 */
public interface FileInfoService {

	/**
	 * 根据条件查询文件信息
	 * 
	 * @return Page<FileInfo>
	 */
	public Page<FileInfo> getFileInfo(FileQueryReq req);
	
	/**
	 * 根据文件md5值获取对应的文件
	 * 
	 * @param md5
	 * @return FileInfo
	 */
	public FileInfo findFileByMd5(String md5);

	/**
	 * 根据主键Id获取文件信息
	 * 
	 * @param id
	 * @return
	 */
	public FileInfo findOne(Long id);

	/**
	 * 查询所有文件列表
	 * 
	 * @return List<FileInfo>
	 */
	public List<FileInfo> findAll();

	/**
	 * 根据目录Id获取对应的文件列表
	 * 
	 * @return List<FileInfo>
	 */
	public List<FileInfo> findByLibId(Long libId);

	/**
	 * 新增文件信息
	 * 
	 * @return FileInfo
	 */
	public FileInfo save(FileInfo fileInfo);

	/**
	 * 删除文件信息
	 */
	public void delete(Long id);

	/**
	 * 删除文件信息
	 */
	public void deleteFileInfo(Long id);

    public FileInfo getFileDetail(FileQueryReq req);

	public List<MediaTree> getMediaTree(FileQueryReq req);

	/**
	 * 修改文件信息
	 * @param req
	 * @return
	 */
	public FileInfo update(FileQueryReq req);
}