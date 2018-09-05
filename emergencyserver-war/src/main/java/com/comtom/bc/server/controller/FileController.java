package com.comtom.bc.server.controller;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.annotation.NoRecordLog;
import com.comtom.bc.common.result.JsonResult;
import com.comtom.bc.common.result.ResultCode;
import com.comtom.bc.common.result.ResultKey;
import com.comtom.bc.common.result.ResultMsg;
import com.comtom.bc.common.utils.*;
import com.comtom.bc.server.asset.WebCxt;
import com.comtom.bc.server.repository.entity.FileInfo;
import com.comtom.bc.server.repository.entity.MediaTree;
import com.comtom.bc.server.repository.entity.ProgramFiles;
import com.comtom.bc.server.req.FileQueryReq;
import com.comtom.bc.server.service.FileInfoService;
import com.comtom.bc.server.service.ParamService;
import com.comtom.bc.server.service.ProgramService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Header;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 文件管理控制器实现
 * 
 * @author zhucanhui
 *
 */
@Api(value = "媒资管理")
@RestController
@RequestMapping(value = "api/file")
public class FileController {

	/**
	 * 日志记录器
	 */
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private ParamService paramService;

	@Autowired
	private FileInfoService fileInfoService;

	@Autowired
	private ProgramService programService;

	/**
	 * 媒资文件上传和文件信息保存处理
	 * 
	 * @param portalType
	 *            客户端类型
	 * @param fileType
	 *            文本类型
	 * @param file
	 *            媒资文件
	 * @return ResponseEntity<String>
	 */
	@ApiOperation(value = "媒资管理", notes = "媒资上传")
	@RequestMapping(value = "upload", method = RequestMethod.POST, consumes = "multipart/form-data")
	public ResponseEntity<String> uploadFile(HttpServletRequest request,
			@RequestParam Integer portalType, @RequestParam Integer fileType,
			@RequestParam Long libraryId, @RequestParam String jsessionid,
			@RequestParam(required = false) String originalFileName, @RequestParam(required = false) Integer fileDuration,
			@RequestParam MultipartFile file) {

		// 设置公用参数

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		String md5Code = getMd5Code(file);

		// 根据媒体文件MD5值过滤重复上传
		FileInfo md5FileInfo = fileInfoService.findFileByMd5(md5Code);

		// 不存在重复文件时，进行文件保存
		if (CommonUtil.isEmpty(md5FileInfo)) {

			// 获取文件存放路径
			String filePath = paramService.findValueByKey(Constants.FILE_PATH);

			if (logger.isDebugEnabled()) {
				logger.info("FileController.handleFileUpload file path={}", filePath);
			}
			if(CommonUtil.isEmpty(originalFileName)){
				originalFileName = file.getOriginalFilename();
			}

			// 获取文件扩展名
			String fileExtName = FileUtil.getFileExtName(originalFileName);
			String uuidFileName = GenUUId.uuid();

			// 根据文件类型匹配目录名称
			String dirName = paramService.getFileDirName(fileType);

			// 上传目标路径
			File targetPath = new File(filePath + Constants.FILE_SPLIT + dirName
					+ Constants.FILE_SPLIT);
			if (targetPath.exists() == false) {
				targetPath.mkdirs();
			}

			// 创建文件
			File uploadFile = new File(filePath + Constants.FILE_SPLIT + dirName
					+ Constants.FILE_SPLIT + uuidFileName + Constants.SUFFIX_SPLIT + fileExtName);

			// String md5Code = Md5Util.getMd5ByFile(uploadFile);

			// 保存文件
			if (!file.isEmpty() && !filePath.isEmpty()) {

				try {
					file.transferTo(uploadFile);
				} catch (RuntimeException e) {
					logger.error("FileController.handleFileUpload 上传文件失败.", e);

					// 返回处理结果
					resultJson.setDataMap(null);
					resultJson.setResultCode(ResultCode.FAILURE);
					resultJson.setResultMsg(ResultMsg.RESULT_FAILURE);
					return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);

				} catch (IOException e) {
					logger.error("FileController.handleFileUpload 上传文件发生异常.", e);

					// 返回处理结果
					resultJson.setDataMap(null);
					resultJson.setResultCode(ResultCode.FAILURE);
					resultJson.setResultMsg(ResultMsg.RESULT_FAILURE);
					return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
				}
			} else {

				logger.error("FileController.handleFileUpload 上传文件为空.");

				// 返回处理结果
				resultJson.setDataMap(null);
				resultJson.setResultCode(ResultCode.FAILURE);
				resultJson.setResultMsg(ResultMsg.RESULT_FAILURE);
				return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
			}

			// 文件上传成功，处理文件信息

			// 获取文件详细参数
			String account = (String) request.getAttribute(Constants.ACCOUNT);
			try {
				fileDuration=getAudioPlayTime(uploadFile);
			}catch (Exception e){

			}

			// 创建文件信息对象
			FileInfo fileInfo = new FileInfo();
			fileInfo.setOriginName(originalFileName);
			fileInfo.setUploadedName(uuidFileName + Constants.SUFFIX_SPLIT + fileExtName);
			fileInfo.setCreateDate(new Date());
			fileInfo.setByteSize(Long.valueOf(file.getSize()).intValue());
			fileInfo.setMd5Code(md5Code);
			fileInfo.setFileType(fileType);
			fileInfo.setCreateUser(account);
			fileInfo.setFileExt(fileExtName);
			fileInfo.setSecondLength(fileDuration);
			fileInfo.setLibId(libraryId);
			fileInfo.setFileDesc(originalFileName);

			// 新增文件信息记录
			FileInfo newFileInfo = fileInfoService.save(fileInfo);

			// 用户操作日志
			request.setAttribute(Constants.ACCOUNT, account);
			request.setAttribute(Constants.PORTAL_TYPE, portalType);

			// 设置用户操作日志参数和内容
			String logContent = "用户【" + account + "】新增和上传媒资文件【" + originalFileName + "】成功.";
			request.setAttribute(Constants.USER_LOG, logContent);

			// 设置返回文件信息对象
			dataMap.put(ResultKey.FILE_INFO_KEY, newFileInfo);
		} else {
			// 设置返回文件信息对象
			dataMap.put(ResultKey.FILE_INFO_KEY, md5FileInfo);
		}

		// 返回处理结果
		resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}

	/**
	 * 媒资文件下载
	 * 
	 * @param id
	 * @return ResponseEntity<InputStreamResource>
	 * @throws IOException
	 */
//	@ApiOperation(value = "媒资管理", notes = "媒资文件下载")
//	@RequestMapping(value = "/download", method = RequestMethod.POST)
//	public ResponseEntity<InputStreamResource> downloadFile(HttpServletRequest request,
//			@RequestBody FileQueryReq req, @RequestParam String jsessionid) throws IOException {
//
//		// 设置公用参数
//		String account = (String) request.getAttribute(Constants.ACCOUNT);
//
//		// 根据媒资文件Id下载文件试听
//		Long id = req.getId();
//
//		if (id != null) {
//			FileInfo fileInfo = fileInfoService.findOne(id);
//
//			String filePath = paramService.getFilePath(fileInfo);
//
//			FileSystemResource file = new FileSystemResource(filePath);
//			HttpHeaders headers = new HttpHeaders();
//			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
//			headers.add("Content-Disposition",
//					String.format("attachment; filename=\"%s\"", file.getFilename()));
//			headers.add("Pragma", "no-cache");
//			headers.add("Expires", "0");
//
//			// 用户操作日志
//			request.setAttribute(Constants.ACCOUNT, account);
//			request.setAttribute(Constants.PORTAL_TYPE, req.getPortalType());
//
//			// 设置用户操作日志参数和内容
//			String logContent = "用户【" + account + "】下载媒资文件【" + fileInfo.getOriginName() + "】成功.";
//			request.setAttribute(Constants.USER_LOG, logContent);
//
//			return ResponseEntity.ok().headers(headers).contentLength(file.contentLength())
//					.contentType(MediaType.parseMediaType("application/octet-stream"))
//					.body(new InputStreamResource(file.getInputStream()));
//		} else {
//			return new ResponseEntity<InputStreamResource>(HttpStatus.NOT_FOUND);
//		}
//	}


	@ApiOperation(value = "媒资管理", notes = "媒资文件下载")
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> downloadFile(HttpServletRequest request,
															@RequestParam Long id, @RequestParam String jsessionid) throws IOException {

		// 设置公用参数
		String account = (String) request.getAttribute(Constants.ACCOUNT);

		// 根据媒资文件Id下载文件试听
	//	Long id = id;

		if (id != null) {
			FileInfo fileInfo = fileInfoService.findOne(id);

			String filePath = paramService.getFilePath(fileInfo);

			FileSystemResource file = new FileSystemResource(filePath);
			HttpHeaders headers = new HttpHeaders();
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			headers.add("Content-Disposition",
					String.format("attachment; filename=\"%s\"", file.getFilename()));
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");

			// 用户操作日志
			request.setAttribute(Constants.ACCOUNT, account);
			request.setAttribute(Constants.PORTAL_TYPE,1);

			// 设置用户操作日志参数和内容
			String logContent = "用户【" + account + "】下载媒资文件【" + fileInfo.getOriginName() + "】成功.";
			request.setAttribute(Constants.USER_LOG, logContent);

			return ResponseEntity.ok().headers(headers).contentLength(file.contentLength())
					.contentType(MediaType.parseMediaType("application/octet-stream"))
					.body(new InputStreamResource(file.getInputStream()));
		} else {
			return new ResponseEntity<InputStreamResource>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * 查询媒资文件信息
	 * 
	 * @param request
	 * @return ResponseEntity<String>
	 */
	@NoRecordLog
	@ApiOperation(value = "媒资管理", notes = "媒资文件查询")
	@RequestMapping(value = "get", method = RequestMethod.POST)
	public ResponseEntity<String> getFileInfo(HttpServletRequest request,
			@RequestBody FileQueryReq req, @RequestParam String jsessionid) {

		// 设置公用参数
		String account = (String) request.getAttribute(Constants.ACCOUNT);

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 查询文件信息列表
		Page<FileInfo> pages = fileInfoService.getFileInfo(req);
		dataMap.put(ResultKey.FILE_LIST_KEY, pages.getContent());
		dataMap.put(ResultKey.TOTAL_COUNT_KEY, pages.getSize());

		// 用户操作日志
		request.setAttribute(Constants.ACCOUNT, account);
		request.setAttribute(Constants.PORTAL_TYPE, req.getPortalType());

		// 返回处理结果
		resultJson.setTotalCount(pages.getTotalElements());
		resultJson.setCurrPage(pages.getNumber()+1);
		resultJson.setTotalPage(pages.getTotalPages());
		resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}

	/**
	 * 查询媒资文件信息
	 *
	 * @param request
	 * @return ResponseEntity<String>
	 */
	@NoRecordLog
	@ApiOperation(value = "媒资管理", notes = "文件详情查询")
	@RequestMapping(value = "detail", method = RequestMethod.POST)
	public ResponseEntity<String> getFileDetail(HttpServletRequest request,
											  @RequestBody FileQueryReq req, @RequestParam String jsessionid) {

		// 设置公用参数
		String account = (String) request.getAttribute(Constants.ACCOUNT);

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 查询文件信息列表
		FileInfo fileInfo = fileInfoService.getFileDetail(req);
		dataMap.put(ResultKey.FILE_INFO_KEY, fileInfo);

		// 用户操作日志
		request.setAttribute(Constants.ACCOUNT, account);
		request.setAttribute(Constants.PORTAL_TYPE, req.getPortalType());

		// 返回处理结果
		resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}
	/**
	 * 媒资文件树形目录查询
	 *
	 * @param request
	 * @return ResponseEntity<String>
	 */
	@NoRecordLog
	@ApiOperation(value = "媒资管理", notes = "媒资文件树形目录查询")
	@RequestMapping(value = "getMediaTree", method = RequestMethod.POST)
	public ResponseEntity<String> getMediaTree(HttpServletRequest request,
											  @RequestBody FileQueryReq req, @RequestParam String jsessionid) {

		// 设置公用参数
		String account = (String) request.getAttribute(Constants.ACCOUNT);

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 查询文件信息列表
		List<MediaTree> mediaTrees = fileInfoService.getMediaTree(req);
		dataMap.put(ResultKey.MEDIA_TREE_KEY, mediaTrees);

		// 用户操作日志
		request.setAttribute(Constants.ACCOUNT, account);
		request.setAttribute(Constants.PORTAL_TYPE, req.getPortalType());

		// 返回处理结果
		resultJson.setTotalCount(mediaTrees.size());
		resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}
	/**
	 * 媒资文件更新
	 *
	 * @param request
	 * @return ResponseEntity<String>
	 */
	@NoRecordLog
	@ApiOperation(value = "媒资管理", notes = "媒资文件更新")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public ResponseEntity<String> update(HttpServletRequest request,
											   @RequestBody FileQueryReq req, @RequestParam String jsessionid) {

		// 设置公用参数
		String account = (String) request.getAttribute(Constants.ACCOUNT);

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 查询文件信息列表
		FileInfo fileInfo = fileInfoService.update(req);
		dataMap.put(ResultKey.FILE_INFO_KEY, fileInfo);

		// 用户操作日志
		request.setAttribute(Constants.ACCOUNT, account);
		request.setAttribute(Constants.PORTAL_TYPE, req.getPortalType());

		// 返回处理结果
		resultJson.setTotalCount(1);
		resultJson.setDataMap(dataMap);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}

	/**
	 * 媒资文件删除
	 * 
	 * @param portalType
	 *            客户端类型
	 * @param fileType
	 *            文本类型
	 * @param file
	 *            媒资文件
	 * @return ResponseEntity<String>
	 */
	@ApiOperation(value = "媒资管理", notes = "媒资文件删除")
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public ResponseEntity<String> deleteFileInfo(HttpServletRequest request,
			@RequestBody FileQueryReq req, @RequestParam String jsessionid) {

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();
		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);
		resultJson.setDataMap(dataMap);

		String account = (String) request.getAttribute(Constants.ACCOUNT);

		FileInfo fileInfo = fileInfoService.findOne(req.getId());


		// 文件信息存在
		if (fileInfo != null) {

			//校验该文件是否允许被删除，已经被使用的文件不允许删除。
			ProgramFiles programFile = new ProgramFiles();
			programFile.setFileId(req.getId());
			List<ProgramFiles> list = programService.findProgramFile(programFile);
			if(list != null && list.size() > 0){
				resultJson.setResultCode(ResultCode.FAILURE);
				resultJson.setResultMsg("文件正在被使用，不能删除！");
				return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
			}


			String uploadedName = fileInfo.getUploadedName();

			// 删除文件信息
			fileInfoService.delete(req.getId());

			Integer fileType = fileInfo.getFileType();

			// 获取文件存放路径
			String filePath = paramService.findValueByKey(Constants.FILE_PATH);

			// 根据文件类型匹配目录名称
			String dirName = paramService.getFileDirName(fileType);

			// 删除实体文件
			try {
				FileUtils.forceDelete(new File(filePath + Constants.FILE_SPLIT + dirName
						+ Constants.FILE_SPLIT + uploadedName));
			} catch (IOException e) {
				logger.error("FileController.deleteFileInfo IOException.", e);
			}

			// 用户操作日志
			request.setAttribute(Constants.ACCOUNT, account);
			request.setAttribute(Constants.PORTAL_TYPE, req.getPortalType());

			// 设置用户操作日志参数和内容
			String logContent = "用户【" + account + "】删除媒资文件【" + fileInfo.getOriginName() + "】成功.";
			request.setAttribute(Constants.USER_LOG, logContent);

		} else {
			resultJson.setResultCode(ResultCode.FAILURE);
			resultJson.setResultMsg(ResultMsg.RESULT_FAILURE);
		}

		return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
	}

	/**
	 * 获取上传文件MD5值
	 * 
	 * @param upload
	 * @return
	 */
	public String getMd5Code(MultipartFile upload) {
		byte[] uploadBytes = null;
		try {
			uploadBytes = upload.getBytes();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return Md5Util.getMd5Hex(uploadBytes);
	}

	// 读取音频总时长
	public static int getAudioPlayTime(File mp3) throws IOException, BitstreamException
	{
		FileInputStream fis = null;
		try
		{
			fis = new FileInputStream(mp3);
			int b = fis.available();
			Bitstream bt = new Bitstream(fis);
			Header h = bt.readFrame();
			int time = (int) h.total_ms(b);
			return time;
		} catch (Exception e)
		{
			e.printStackTrace();
			return 0;
		} finally
		{
			if (fis != null)
			{
				fis.close();
			}
		}
	}

}
