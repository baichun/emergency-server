package com.comtom.bc.server.controller;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.result.JsonResult;
import com.comtom.bc.common.result.ResultCode;
import com.comtom.bc.common.result.ResultKey;
import com.comtom.bc.common.utils.GenUUId;
import com.comtom.bc.common.utils.JsonUtil;
import com.comtom.bc.server.asset.RedisUtil;
import com.comtom.bc.server.asset.WebCxt;
import com.comtom.bc.server.repository.entity.SysUser;
import com.comtom.bc.server.repository.entity.UserMenuView;
import com.comtom.bc.server.repository.entity.SysModule;
import com.comtom.bc.server.req.MenuReq;
import com.comtom.bc.server.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api(value = "菜单管理")
@RestController
@RequestMapping(value = "api/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value = "查询菜单列表分页", notes = "查询菜单列表")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public ResponseEntity<String> page(HttpServletRequest request,
                                       @RequestBody MenuReq req, @RequestParam String jsessionid) {

/*        SysUser sysUser = (SysUser) redisUtil.get(jsessionid);
        String account = sysUser.getAccount();
        req.setAccount(account);*/



        // 初始化返回结果对象JsonResult
        JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

        // 初始化业务数据Map对象
        Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

        // 业务逻辑处理
        Page<SysModule> page = menuService.page(req);


        dataMap.put(ResultKey.MENU_LIST_KEY, page.getContent());
        resultJson.setCurrPage(page.getNumber()+1);
        resultJson.setTotalPage(page.getTotalPages());
        resultJson.setTotalCount(page.getTotalElements());

        // 返回处理结果
        resultJson.setDataMap(dataMap);
        return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
                HttpStatus.OK);
    }

    @ApiOperation(value = "查询菜单列表", notes = "查询菜单列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity<String> list(HttpServletRequest request,
                                       @RequestBody MenuReq req, @RequestParam String jsessionid) {


        // 初始化返回结果对象JsonResult
        JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

        // 初始化业务数据Map对象
        Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

        // 业务逻辑处理
        List<SysModule> list = menuService.list(req);


        dataMap.put(ResultKey.MENU_LIST_KEY, list);
        resultJson.setTotalCount(list.size());

        // 返回处理结果
        resultJson.setDataMap(dataMap);
        return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
                HttpStatus.OK);
    }


    @ApiOperation(value = "查询用户已授权菜单", notes = "查询用户已授权菜单")
    @RequestMapping(value = "/grantedList", method = RequestMethod.POST)
    public ResponseEntity<String> grantedList(HttpServletRequest request,
                                       @RequestBody MenuReq req, @RequestParam String jsessionid) {


        // 初始化返回结果对象JsonResult
        JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

        // 初始化业务数据Map对象
        Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

        //为防止数据量过大，递归查询时造成内存溢出，要求必须传父菜单ID。
        if(StringUtils.isBlank(req.getParentId()) && StringUtils.isBlank(req.getId())){
            resultJson.setResultCode(ResultCode.FAILURE);
            resultJson.setResultMsg("菜单ID或者父菜单ID不能为都空！");
            resultJson.setDataMap(dataMap);
            return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
        }

        SysUser sysUser = (SysUser)redisUtil.get(jsessionid);
        req.setUserId(sysUser.getId());
        // 业务逻辑处理
        List<UserMenuView> list = menuService.grantedList(req);


        dataMap.put(ResultKey.MENU_LIST_KEY, list);
        resultJson.setTotalCount(list.size());

        // 返回处理结果
        resultJson.setDataMap(dataMap);
        return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
                HttpStatus.OK);
    }

    @ApiOperation(value = "查询菜单树", notes = "查询菜单树")
    @RequestMapping(value = "/treeList", method = RequestMethod.POST)
    public ResponseEntity<String> treeList(HttpServletRequest request,
                                       @RequestBody MenuReq req, @RequestParam String jsessionid) {


        // 初始化返回结果对象JsonResult
        JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

        // 初始化业务数据Map对象
        Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

        // 业务逻辑处理
        //为防止数据量过大，递归查询时造成内存溢出，要求必须传父菜单ID。
        if(StringUtils.isBlank(req.getParentId()) && StringUtils.isBlank(req.getId())){
            resultJson.setResultCode(ResultCode.FAILURE);
            resultJson.setResultMsg("菜单ID或者父菜单ID不能为都空！");
            resultJson.setDataMap(dataMap);
            return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
        }
        List<SysModule> list = menuService.treeList(req);

        dataMap.put(ResultKey.MENU_LIST_KEY, list);
        resultJson.setTotalCount(list.size());

        // 返回处理结果
        resultJson.setDataMap(dataMap);
        return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
                HttpStatus.OK);
    }

    @ApiOperation(value = "查询菜单详情", notes = "查询菜单详情")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> detail(HttpServletRequest request,
                                         @PathVariable("id") String id, @RequestParam String jsessionid) {


        // 初始化返回结果对象JsonResult
        JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

        // 初始化业务数据Map对象
        Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

        // 业务逻辑处理
        SysModule sysModule = menuService.findById(id);
        dataMap.put(ResultKey.MENU_INFO_KEY, sysModule);
        resultJson.setTotalCount(1);

        // 返回处理结果
        resultJson.setDataMap(dataMap);
        return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
                HttpStatus.OK);
    }

    @ApiOperation(value = "查询菜单详情", notes = "查询菜单详情")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<String> save(HttpServletRequest request,
                                         @RequestBody SysModule sysModule, @RequestParam String jsessionid) {


        // 初始化返回结果对象JsonResult
        JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

        // 初始化业务数据Map对象
        Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

        // 业务逻辑处理
        sysModule.setId(GenUUId.uuid());
        sysModule.setCascadeId("0.001.002.003");
        sysModule.setIsAutoExpand("0");
        sysModule.setStatus("1");
        SysModule module = menuService.save(sysModule);

        dataMap.put(ResultKey.MENU_INFO_KEY, module);
        resultJson.setTotalCount(1);

        // 返回处理结果
        resultJson.setDataMap(dataMap);
        return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
                HttpStatus.OK);
    }

    @ApiOperation(value = "查询菜单详情", notes = "查询菜单详情")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<String> update(HttpServletRequest request,
                                       @RequestBody SysModule sysModule, @RequestParam String jsessionid) {


        // 初始化返回结果对象JsonResult
        JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

        // 初始化业务数据Map对象
        Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

        // 业务逻辑处理
        SysModule module = menuService.findById(sysModule.getId());
        if(module == null){
            resultJson.setResultCode(ResultCode.FAILURE);
            resultJson.setResultMsg("编辑的菜单不存在");
            resultJson.setDataMap(dataMap);
            return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
        }
        if (sysModule.getParentId() != null) {
            module.setParentId(sysModule.getParentId());
            SysModule md = menuService.findById(sysModule.getParentId());
            if(md!= null){
                module.setParentName(md.getName());
            }else{
                resultJson.setResultCode(ResultCode.FAILURE);
                resultJson.setResultMsg("编辑菜单的父菜单不存在");
                resultJson.setDataMap(dataMap);
                return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
            }
        }
        if (sysModule.getName() != null) {
            module.setName(sysModule.getName());
        }
        if (sysModule.getSortNo() != null) {
            module.setSortNo(sysModule.getSortNo());
        }
        if (sysModule.getUrl() != null) {
            module.setUrl(sysModule.getUrl());
        }
        if (sysModule.getIconName() != null) {
            module.setIconName(sysModule.getIconName());
        }
        if (sysModule.getIsLeaf() != null) {
            module.setIsLeaf(sysModule.getIsLeaf());
        }
        module = menuService.save(module);
        if(module == null){
            resultJson.setResultCode(ResultCode.FAILURE);
            resultJson.setResultMsg("更新菜单时出现异常！");
            resultJson.setDataMap(dataMap);
            return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
        }
        dataMap.put(ResultKey.MENU_INFO_KEY, module);
        resultJson.setTotalCount(1);

        // 返回处理结果
        resultJson.setDataMap(dataMap);
        return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
                HttpStatus.OK);
    }

}
