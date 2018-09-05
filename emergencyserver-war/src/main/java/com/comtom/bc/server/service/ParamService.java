package com.comtom.bc.server.service;

import com.comtom.bc.server.repository.entity.FileInfo;
import com.comtom.bc.server.repository.entity.ParamInfo;
import com.comtom.bc.server.repository.entity.SysParam;
import com.comtom.bc.server.req.ParamQueryReq;
import com.comtom.bc.server.req.ParamUpdateReq;
import org.springframework.data.domain.Page;


/**
 * 键值参数-业务逻辑处理接口定义
 * 
 * @author zhucanhui
 *
 */
public interface ParamService {

	/**
	 * 根据键值参数Key获取对象
	 * 
	 * @param key
	 * @return String
	 */
	public String findValueByKey(String key);
	
	/**
	 * 根据文件类型获取对象的文件目录名称
	 * 
	 * @param fileType
	 * @return String 文件目录名称
	 */
	public String getFileDirName(Integer fileType);
	
	/**
	 * 根据文件信息获取文件绝对地址
	 * 
	 * @param fileInfo
	 * @return String
	 */
	public String getFilePath(FileInfo fileInfo);

    public Page<SysParam> list(ParamQueryReq req);

	public SysParam updateProgram(ParamUpdateReq req);
}