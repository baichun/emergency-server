package com.comtom.bc.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.comtom.bc.server.repository.dao.LibraryDAO;
import com.comtom.bc.server.repository.entity.FileLibrary;
import com.comtom.bc.server.repository.entity.MediaTree;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.server.repository.dao.FileInfoDAO;
import com.comtom.bc.server.repository.entity.FileInfo;
import com.comtom.bc.server.req.FileQueryReq;
import com.comtom.bc.server.service.FileInfoService;
import com.comtom.bc.server.service.base.BaseService;

/**
 * 文件信息-业务逻辑处理接口定义
 * 
 * @author zhucanhui
 *
 */
@Service("FileInfoService")
public class FileInfoServiceImpl extends BaseService implements FileInfoService {

	@Autowired
	private FileInfoDAO fileInfoDAO;

	@Autowired
	private LibraryDAO libraryDAO;

	/**
	 * 根据条件查询文件信息
	 * 
	 * @return Page<FileInfo>
	 */
	public Page<FileInfo> getFileInfo(FileQueryReq req) {
		Page<FileInfo> page = fileInfoDAO.findAll(getSpec(req),
				buildPageRequest(req.getPageNum(), req.getPageSize(), getSort()));
		List<FileInfo> fileInfoList = page.getContent();
		if(fileInfoList != null && fileInfoList.size() > 0 ){
			for(FileInfo f : fileInfoList){
				f.setDurationTxt(getDurationText(f.getSecondLength()));
				f.setByteSizeTxt(getByteSizeTxt(Double.valueOf(f.getByteSize())));
			}
		}
		return page;
	}

	/**
	 * 根据文件md5值获取对应的文件
	 * 
	 * @param md5
	 * @return FileInfo
	 */
	public FileInfo findFileByMd5(String md5) {
		return fileInfoDAO.findByMd5Code(md5);
	}

	/**
	 * 根据主键Id获取文件信息
	 * 
	 * @param id
	 * @return FileInfo
	 */
	public FileInfo findOne(Long id) {
		return fileInfoDAO.findOne(id);
	}

	/**
	 * 查询所有文件列表
	 * 
	 * @return List<FileInfo>
	 */
	public List<FileInfo> findAll() {
		return fileInfoDAO.findAll();
	}

	/**
	 * 根据目录Id获取对应的文件列表
	 * 
	 * @param libId
	 * 
	 * @return List<FileInfo>
	 */
	public List<FileInfo> findByLibId(Long libId) {
		return fileInfoDAO.findByLibId(libId);
	}

	/**
	 * 新增文件信息
	 * 
	 * @return FileInfo
	 */
	public FileInfo save(FileInfo fileInfo) {
		return fileInfoDAO.save(fileInfo);
	}

	/**
	 * 删除文件信息
	 */
	public void delete(Long id) {
		fileInfoDAO.delete(id);
	}

	/**
	 * 删除文件信息
	 */
	public void deleteFileInfo(Long id) {
		fileInfoDAO.deleteFileInfo(id);
	}

	@Override
	public FileInfo getFileDetail(FileQueryReq req) {
		Long fileId = req.getId();
		FileInfo fileInfo = fileInfoDAO.findOne(fileId);
		fileInfo.setDurationTxt(getDurationText(fileInfo.getSecondLength()));
		fileInfo.setByteSizeTxt(getByteSizeTxt(Double.valueOf(fileInfo.getByteSize())));
		if(fileInfo != null){
			FileLibrary library = libraryDAO.findOne(fileInfo.getLibId());
			fileInfo.setLibrary(library);
		}
		return fileInfo;
	}

	@Override
	public List<MediaTree> getMediaTree(FileQueryReq req) {
		Long libId = req.getLibraryId();
		List<FileLibrary> librarys = libraryDAO.findAll(getLibSpec(req));
		List<FileInfo> fileInfos = fileInfoDAO.findAll(getSpec(req));
		List<MediaTree> mediaTreeList = new ArrayList<>();
		if(librarys != null && librarys.size() >  0){
			for(FileLibrary f : librarys){
				MediaTree mt = new MediaTree();
				mt.setId(f.getLibId());
				mt.setName(f.getLibName());
				mt.setParent(true);
				mt.setPid(Long.valueOf(f.getParentLibId()));
				mt.setTitle(f.getLibName());
				mt.setUrl(f.getLibURI());
				mt.setLibraryId(f.getLibId());
				mediaTreeList.add(mt);
			}
		}
		if(fileInfos != null && fileInfos.size() >  0){
			for(FileInfo f : fileInfos){
				MediaTree mt = new MediaTree();
				mt.setId(f.getId());
				mt.setName(f.getOriginName());
				mt.setParent(false);
				mt.setPid(f.getLibId());
				mt.setTitle(f.getOriginName());
				mt.setUrl(null);
				mt.setLibraryId(f.getLibId());
				mediaTreeList.add(mt);
			}
		}

		return mediaTreeList;
	}

	@Override
	public FileInfo update(FileQueryReq req) {
		FileInfo fileInfo = fileInfoDAO.findOne(req.getId());
		fileInfo.setOriginName(req.getFileName());
		fileInfo.setLibId(req.getLibraryId());
		return fileInfoDAO.save(fileInfo);
	}

	private String getByteSizeTxt(Double byteSize) {
		String[] uints = new String[]{"B","KB","MB","TB","PB"};
		int index = 0;
		while(byteSize > 1024 && index < 5){
			byteSize = byteSize/1024;
			index ++;
		}
		BigDecimal bd = new BigDecimal(byteSize);
		return bd.setScale(2,BigDecimal.ROUND_HALF_UP).toString()+uints[index];
	}

	private String getDurationText(Integer secondLength) {
		String[] units = new String[]{"秒","分","小时","天"};
		Integer[] d = new Integer[units.length];
		int n = 0;
		String result = "";
		if(secondLength != null){
			secondLength = secondLength/1000;
			if(secondLength < 60){
				d[0] = secondLength;
			}
			if(secondLength > 60){
				d[0] = secondLength % 60;
				d[1] = secondLength / 60;
				n++;
				secondLength = secondLength/60;
			}
			if(secondLength > 60){
				d[1] = secondLength % 60;
				d[2] = secondLength / 60;
				n++;
				secondLength = secondLength/60;
			}
			if(secondLength > 24){
				d[2] = secondLength % 24;
				d[3] = secondLength / 24;
				n++;
				secondLength = secondLength/24;
			}
			for (int i = n ;i>= 0;i--){
				if(d[i] != null){
					result +=  d[i] + units[i];
				}
			}
			return result;
		}
		return null;
	}

	/**
	 * 实现多条件模糊和带日期区间查询Specification
	 * 
	 * @param req
	 * @return Specification<FileInfo>
	 */
	private Specification<FileInfo> getSpec(FileQueryReq req) {

		// 查询参数
		final Integer fileType = req.getFileType();
		final String createUser = req.getCreateUser();
		final Long libId = req.getLibraryId();
		final String fileName = req.getFileName();
		final Date startDate = req.getStartDate();
		final Date overDate = req.getOverDate();

		return new Specification<FileInfo>() {
			@Override
			public Predicate toPredicate(Root<FileInfo> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (CommonUtil.isNotEmpty(fileName)) {
					predicate.getExpressions().add(
							cb.like(r.<String> get("originName"), "%" + StringUtils.trim(fileName)
									+ "%"));
				}

				if (CommonUtil.isNotEmpty(fileType)) {
					predicate.getExpressions().add(cb.equal(r.<Integer> get("fileType"), fileType));
				}

				if (CommonUtil.isNotEmpty(libId)) {
					predicate.getExpressions().add(cb.equal(r.<Long> get("libId"), libId));
				}

				if (CommonUtil.isNotEmpty(createUser)) {
					predicate.getExpressions().add(
							cb.like(r.<String> get("createUser"),
									"%" + StringUtils.trim(createUser) + "%"));
				}

				if (CommonUtil.isNotEmpty(startDate)) {
					predicate.getExpressions().add(
							cb.greaterThanOrEqualTo(r.<Date> get("createDate"), startDate));
				}

				if (CommonUtil.isNotEmpty(overDate)) {
					predicate.getExpressions().add(
							cb.lessThanOrEqualTo(r.<Date> get("createDate"), overDate));
				}

				return predicate;
			}
		};
	}

	private Specification<FileLibrary> getLibSpec(FileQueryReq req) {

		// 查询参数
		final Long libId = req.getLibraryId();

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

	/**
	 * 获取排序对象Sort
	 * 
	 * @return Sort
	 */
	private Sort getSort() {
		Order orderTime = new Order(Direction.DESC, "createDate");
		List<Order> orders = new ArrayList<Order>();
		orders.add(orderTime);
		Sort sort = new Sort(orders);
		return sort;
	}

}