package com.comtom.bc.server.service;

import java.util.List;
import java.util.Map;

import com.comtom.bc.server.repository.entity.FileLibrary;

/**
 * 文件目录-业务逻辑处理接口定义
 * 
 * @author zhucanhui
 *
 */
public interface LibraryService {

	/**
	 * 查询所有文件目录信息
	 * 
	 * @return List<FileLibrary
	 */
	public List<FileLibrary> findAll();

	/**
	 * 创建文件目录
	 * 
	 * @return FileLibrary
	 */
	public FileLibrary save(FileLibrary fileLibrary);
	/**
	 * 更新文件目录
	 *
	 * @return FileLibrary
	 */
	public FileLibrary update(FileLibrary lib);
	/**
	 * 删除文件目录
	 *
	 * @return FileLibrary
	 */
	public Map<String,Object> deleteById(Long libId);
}