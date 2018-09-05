package com.comtom.bc.server.service.impl;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.server.repository.dao.FileInfoDAO;
import com.comtom.bc.server.repository.dao.LibraryDAO;
import com.comtom.bc.server.repository.entity.FileInfo;
import com.comtom.bc.server.repository.entity.FileLibrary;
import com.comtom.bc.server.repository.entity.ProgramFiles;
import com.comtom.bc.server.service.LibraryService;
import com.comtom.bc.server.service.ParamService;
import com.comtom.bc.server.service.ProgramService;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典参数-业务逻辑处理接口定义
 * 
 * @author zhucanhui
 *
 */
@Service("LibraryService")
public class LibraryServiceImpl implements LibraryService {

	@Autowired
	private LibraryDAO libraryDAO;

	@Autowired
	private FileInfoDAO fileInfoDAO;

	@Autowired
	private ParamService paramService;

	@Autowired
	private ProgramService programService;

	/**
	 * 查询所有文件目录信息
	 * 
	 * @return List<FileLibrary>
	 */
	public List<FileLibrary> findAll() {
		return libraryDAO.findAll();
	}

	/**
	 * 创建文件目录
	 * 
	 * @return FileLibrary
	 */
	public FileLibrary save(FileLibrary fileLibrary) {
		return libraryDAO.save(fileLibrary);
	}

	@Override
	public FileLibrary update(FileLibrary lib) {
		FileLibrary library = libraryDAO.findOne(lib.getLibId());
		library.setLibName(lib.getLibName());
		library.setParentLibId(lib.getParentLibId());
		if(StringUtils.isNotBlank(lib.getLibURI())){
			library.setLibURI(lib.getLibURI());
		}
		return libraryDAO.save(library);
	}

	@Override
	@Transactional
	public Map<String,Object> deleteById(Long libId) {
		Map<String,Object> resultMap = new HashMap<>();
		List<Long> libIds = new ArrayList<>();
		List<Long> fileIds = new ArrayList<>();
		List<Map<String,Object>> uploadNames = new ArrayList<>();
		libIds.add(libId);
		getAllChildrenIds(libId,libIds,fileIds,uploadNames);
		boolean allowDelete = true;
		if(fileIds != null && fileIds.size() > 0){
			for(Long fileId : fileIds){
				if(allowDelete){
					//校验该文件是否允许被删除，已经被使用的文件不允许删除。
					ProgramFiles programFile = new ProgramFiles();
					programFile.setFileId(fileId);
					List<ProgramFiles> list = programService.findProgramFile(programFile);
					if(list != null && list.size() > 0){
						allowDelete = false;
					}
				}
			}
		}
		if (allowDelete){
			//文件可以被删除
			libraryDAO.deleteByIdsBatch(libIds);
			fileInfoDAO.deleteByIdsBatch(fileIds);
			if(uploadNames!= null && uploadNames.size() > 0 ){
				// 获取文件存放路径
				String filePath = paramService.findValueByKey(Constants.FILE_PATH);
				for(Map<String,Object> map : uploadNames){
					// 根据文件类型匹配目录名称
					String dirName = paramService.getFileDirName(Integer.valueOf(map.get("fileType").toString()));
					// 删除实体文件
					try {
						FileUtils.forceDelete(new File(filePath + Constants.FILE_SPLIT + dirName
								+ Constants.FILE_SPLIT + map.get("uploadName").toString()));
					} catch (IOException e) {

					}
				}
			}
			resultMap.put("resultCode","0");
			resultMap.put("resultMsg","");

		}else{
			//文件已经被使用，不能被删除
			resultMap.put("resultCode","-1");
			resultMap.put("resultMsg","文件目录下存在已经被使用的文件，不能被删除");
		}
		return resultMap;
	}

	private void getAllChildrenIds(Long libId, List<Long> libIds, List<Long> fileIds,List<Map<String,Object>> uploadNames) {
		List<FileLibrary> librarys = libraryDAO.findAll(getLibSpec(libId));
		List<FileInfo> fileInfos = fileInfoDAO.findAll(getSpec(libId));
		if(fileInfos != null && fileInfos.size() > 0 ){
			for(FileInfo f : fileInfos){
				fileIds.add(f.getId());
				Map<String,Object> map = new HashMap<>();
				map.put("uploadName",f.getUploadedName());
				map.put("fileType",f.getFileType());
				uploadNames.add(map);
			}
		}

		//递归获取所有子文件夹
		if(librarys != null && librarys.size() > 0){
			for(FileLibrary fl : librarys){
				libIds.add(fl.getLibId());
				getAllChildrenIds(fl.getLibId(),libIds,fileIds,uploadNames);
			}
		}

	}
	private Specification<FileLibrary> getLibSpec(final Long libId) {
		return new Specification<FileLibrary>() {
			@Override
			public Predicate toPredicate(Root<FileLibrary> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();

				if (CommonUtil.isNotEmpty(libId)) {
					predicate.getExpressions().add(cb.equal(r.<Long> get("parentLibId"), libId));
				}
				return predicate;
			}
		};
	}

	private Specification<FileInfo> getSpec(final Long libId) {
		return new Specification<FileInfo>() {
			@Override
			public Predicate toPredicate(Root<FileInfo> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();

				if (CommonUtil.isNotEmpty(libId)) {
					predicate.getExpressions().add(cb.equal(r.<Long> get("libId"), libId));
				}
				return predicate;
			}
		};
	}
}