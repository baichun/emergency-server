package com.comtom.bc.server.controller;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.annotation.NoRecordLog;
import com.comtom.bc.common.result.JsonResult;
import com.comtom.bc.common.result.ResultCode;
import com.comtom.bc.common.result.ResultKey;
import com.comtom.bc.common.utils.JsonUtil;
import com.comtom.bc.server.asset.RedisUtil;
import com.comtom.bc.server.asset.WebCxt;
import com.comtom.bc.server.repository.entity.FileLibrary;
import com.comtom.bc.server.repository.entity.SysUser;
import com.comtom.bc.server.service.LibraryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 文件目录控制器实现
 * 
 * @author zhucanhui
 *
 */
@Api(value = "文件目录")
@RestController
@RequestMapping(value = "api/library")
public class LibraryController {

	@Autowired
	private LibraryService libraryService;

	@Autowired
	private RedisUtil redisUtil;

	@NoRecordLog
	@ApiOperation(value = "文件目录", notes = "获取文件目录")
	@RequestMapping(value = "getLibrary", method = RequestMethod.POST)
	public ResponseEntity<String> getLibrary(@RequestParam String jsessionid) {

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		List<FileLibrary> libraryList = libraryService.findAll();
		dataMap.put(ResultKey.LIBRARY_LIST_KEY, libraryList);
		dataMap.put(ResultKey.TOTAL_COUNT_KEY, libraryList.size());
		// 返回处理结果
		resultJson.setDataMap(dataMap);
		resultJson.setTotalCount(libraryList.size());
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}

	@NoRecordLog
	@ApiOperation(value = "文件目录", notes = "创建文件目录")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public ResponseEntity<String> create(@RequestParam String jsessionid,@RequestBody FileLibrary lib) {

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		SysUser sysUser = (SysUser) redisUtil.get(jsessionid);
		String account = sysUser.getAccount();
		lib.setCreateDate(new Date());
		lib.setCreateUser(account);
		FileLibrary library = libraryService.save(lib);
		dataMap.put(ResultKey.LIBRARY_INFO_KEY,library);
		// 返回处理结果
		resultJson.setDataMap(dataMap);
		resultJson.setTotalCount(1);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}

	@NoRecordLog
	@ApiOperation(value = "文件目录", notes = "修改文件目录")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public ResponseEntity<String> update(@RequestParam String jsessionid,@RequestBody FileLibrary lib){

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		FileLibrary library = libraryService.update(lib);
		dataMap.put(ResultKey.LIBRARY_INFO_KEY,library);
		// 返回处理结果
		resultJson.setDataMap(dataMap);
		resultJson.setTotalCount(1);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}

	@NoRecordLog
	@ApiOperation(value = "文件目录", notes = "删除文件目录")
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public ResponseEntity<String> delete(@RequestParam String jsessionid,@RequestParam Long libId){

		// 初始化返回结果对象JsonResult
		JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

		// 初始化业务数据Map对象
		Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

		// 业务逻辑处理
		Map<String,Object> map = libraryService.deleteById(libId);
		if(!"0".equals(map.get("resultCode").toString())){
			resultJson.setResultCode(ResultCode.FAILURE);
			resultJson.setResultMsg(map.get("resultMsg").toString());
			return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
		}
		// 返回处理结果
		resultJson.setDataMap(dataMap);
		resultJson.setTotalCount(1);
		return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
				HttpStatus.OK);
	}

}
