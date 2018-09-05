package com.comtom.bc.server.controller;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.result.JsonResult;
import com.comtom.bc.common.result.ResultKey;
import com.comtom.bc.common.utils.DateUtil;
import com.comtom.bc.common.utils.JsonUtil;
import com.comtom.bc.server.asset.RedisUtil;
import com.comtom.bc.server.asset.WebCxt;
import com.comtom.bc.server.repository.entity.DBBackupFile;
import com.comtom.bc.server.repository.entity.SysUser;
import com.comtom.bc.server.req.BackupReq;
import com.comtom.bc.server.service.DBBackupFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@Api(value = "数据库备份")
@RestController
@RequestMapping(value = "api/backup")
public class DBBackupFileController {

    @Autowired
    private DBBackupFileService dbBackupFileService;

    @Autowired
    private RedisUtil redisUtil;


    @ApiOperation(value = "数据备份文件查询", notes = "数据备份文件查询")
    @RequestMapping(value = "page", method = RequestMethod.POST)
    public ResponseEntity<String> page(@RequestBody BackupReq req, @RequestParam String jsessionid) {

        // 初始化返回结果对象JsonResult
        JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

        // 初始化业务数据Map对象
        Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

        // 业务逻辑处理
        Page<DBBackupFile> page = dbBackupFileService.page(req);

        dataMap.put(ResultKey.BACKUP_FILE_LIST_KEY, page.getContent());
        dataMap.put(ResultKey.TOTAL_COUNT_KEY, page.getSize());

        // 返回处理结果
        resultJson.setDataMap(dataMap);
        resultJson.setCurrPage(page.getNumber()+1);
        resultJson.setTotalPage(page.getTotalPages());
        resultJson.setTotalCount(page.getTotalElements());
        return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
                HttpStatus.OK);
    }

    @ApiOperation(value = "数据备份", notes = "数据备份新增")
    @RequestMapping(value = "backup", method = RequestMethod.POST)
    public ResponseEntity<String> backup(@RequestBody DBBackupFile file, @RequestParam String jsessionid) {


        // 初始化返回结果对象JsonResult
        JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

        // 初始化业务数据Map对象
        Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

        // 业务逻辑处理
        DBBackupFile dbBackupFile = dbBackupFileService.getByFileName(file.getFileName());
        if(dbBackupFile != null){
            resultJson.setResultMsg("数据库备份失败！文件名已存在");
            resultJson.setResultCode(-1);
            return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
                    HttpStatus.OK);
        }

        SysUser sysUser = (SysUser)redisUtil.get(jsessionid);
        String account = sysUser.getAccount();
        file.setCreateTime(new Date());
        file.setCreator(account);
        file.setBackupType("2");    //备份方式： 1 自动备份；2 手动备份
        file.setRemark("手动备份");
        file.setBackupFileName("dbBackup_"+DateUtil.getDateTime());
        DBBackupFile backupFile = dbBackupFileService.backup(file);
     //   BackUpMySQLUtil.backlinux();

        if(backupFile == null){
            resultJson.setResultMsg("数据库备份失败！");
            resultJson.setResultCode(-1);
            return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
                    HttpStatus.OK);
        }

        dataMap.put(ResultKey.BACKUP_FILE_KEY, backupFile);
        dataMap.put(ResultKey.TOTAL_COUNT_KEY, 1);

        // 返回处理结果
        resultJson.setDataMap(dataMap);
        resultJson.setTotalCount(1);
        return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
                HttpStatus.OK);
    }

    @ApiOperation(value = "数据还原", notes = "数据还原")
    @RequestMapping(value = "restore", method = RequestMethod.POST)
    public ResponseEntity<String> restore(@RequestBody DBBackupFile file, @RequestParam String jsessionid) {

        // 初始化返回结果对象JsonResult
        JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

        // 初始化业务数据Map对象
        Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

        // 业务逻辑处理
        boolean b = dbBackupFileService.restore(file);
        if (!b){
            resultJson.setResultMsg("数据库还原失败！");
            resultJson.setResultCode(-1);
            return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
                    HttpStatus.OK);
        }
        // 返回处理结果
        resultJson.setDataMap(dataMap);
        resultJson.setTotalCount(1);
        return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
                HttpStatus.OK);
    }
}
