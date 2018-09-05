package com.comtom.bc.server.controller;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.result.JsonResult;
import com.comtom.bc.common.result.ResultCode;
import com.comtom.bc.common.result.ResultKey;
import com.comtom.bc.common.result.ResultMsg;
import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.common.utils.JsonUtil;
import com.comtom.bc.server.asset.RedisUtil;
import com.comtom.bc.server.asset.WebCxt;
import com.comtom.bc.server.repository.entity.*;
import com.comtom.bc.server.req.*;
import com.comtom.bc.server.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Api(value = "角色管理")
@RestController
@RequestMapping(value = "api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    RedisUtil redisUtil;


    @ApiOperation(value = "查询角色列表", notes = "角色分页列表")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResponseEntity<String> list(HttpServletRequest request,
                                       @ModelAttribute RoleQueryReq req, @RequestParam String jsessionid) {

        // 初始化返回结果对象JsonResult
        JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

        // 初始化业务数据Map对象
        Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);


        // 业务逻辑处理
        Page<SysRole> page = roleService.find(req);

        dataMap.put(ResultKey.ROLE_LIST_KEY, page.getContent());
        resultJson.setCurrPage(page.getNumber()+1);
        resultJson.setTotalPage(page.getTotalPages());
        resultJson.setTotalCount(page.getTotalElements());

        // 返回处理结果
        resultJson.setDataMap(dataMap);
        return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
                HttpStatus.OK);
    }


    @ApiOperation(value = "根据userId查询角色列表", notes = "根据userId查询角色列表")
    @RequestMapping(value = "findListByUserId", method = RequestMethod.POST)
    public ResponseEntity<String> findListByUserId(HttpServletRequest request,
                                       @RequestParam String userId, @RequestParam String jsessionid) {

        // 初始化返回结果对象JsonResult
        JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

        // 初始化业务数据Map对象
        Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);


        // 业务逻辑处理
        List<SysUserRole> userList = roleService.findListByUserId(userId);

        dataMap.put(ResultKey.ROLE_LIST_KEY, userList);


        // 返回处理结果
        resultJson.setDataMap(dataMap);
        return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
                HttpStatus.OK);
    }






    @ApiOperation(value = "新增角色", notes = "新增角色")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public ResponseEntity<String> saveSysUser(HttpServletRequest request,
                                              @RequestBody SysRoleSaveReq req, @RequestParam String jsessionid) {

        SysUser cursysUser = (SysUser) redisUtil.get(jsessionid);
        String account = cursysUser.getAccount();

        // 初始化返回结果对象JsonResult
        JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

        // 初始化业务数据Map对象
        Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

        SysRole sysRole=new SysRole();
        BeanUtils.copyProperties(req,sysRole);

        sysRole.setCreaterId(cursysUser.getId());
        sysRole.setCreaterOrgCascadeId(cursysUser.getOrgCascadeId());
        sysRole.setCreaterOrgId(cursysUser.getOrgid());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sysRole.setCreateTime(sdf.format(new Date()));

        SysRole response=roleService.saveSysRole(sysRole);

            // 用户操作日志
            request.setAttribute(Constants.ACCOUNT, account);
            request.setAttribute(Constants.PORTAL_TYPE, req.getPortalType());

            if (response != null) {

                // 设置用户操作日志参数和内容
                String logContent = "用户【" + account + "】创建角色：【" + response.getName()
                        + "】成功.";
                request.setAttribute(Constants.USER_LOG, logContent);
                dataMap.put(ResultKey.USER_INFO_KEY, response);
                resultJson.setDataMap(dataMap);
            } else {
                resultJson.setResultCode(ResultCode.FAILURE);
                resultJson.setResultMsg(ResultMsg.RESULT_FAILURE);
                resultJson.setDataMap(dataMap);
                return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
            }

        return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
    }


    @ApiOperation(value = "角色删除", notes = "角色删除")
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUserInfo(HttpServletRequest request,
                                                 @PathVariable("id") String id, @RequestParam String jsessionid) {

        SysUser cursysUser = (SysUser) redisUtil.get(jsessionid);
        String account = cursysUser.getAccount();
        // 初始化返回结果对象JsonResult
        JsonResult<String, Object> resultJson = new JsonResult<String, Object>();
        // 初始化业务数据Map对象
        Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);
        resultJson.setDataMap(dataMap);

        String operAccount = (String) request.getAttribute(Constants.ACCOUNT);

        SysRole sysRole = roleService.findById(id);

        if (sysRole != null) {
            roleService.delRoleInfo(sysRole);
            // 用户操作日志
            request.setAttribute(Constants.ACCOUNT, account);
            request.setAttribute(Constants.PORTAL_TYPE, 1);

            // 设置用户操作日志参数和内容
            String logContent = "用户【" + operAccount + "】删除角色信息【" + sysRole.getName() + "】成功.";
            request.setAttribute(Constants.USER_LOG, logContent);

        } else {
            resultJson.setResultCode(ResultCode.FAILURE);
            resultJson.setResultMsg(ResultMsg.RESULT_FAILURE);
        }

        return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
    }

    @ApiOperation(value = "角色查找", notes = "根据角色ID找角色")
    @RequestMapping(value = "get", method = RequestMethod.GET)
    public ResponseEntity<String> get(HttpServletRequest request,
                                      @RequestParam String id, @RequestParam String jsessionid) {
        SysUser cursysUser = (SysUser) redisUtil.get(jsessionid);
        String account = cursysUser.getAccount();
        // 初始化返回结果对象JsonResult
        JsonResult<String, Object> resultJson = new JsonResult<String, Object>();
        // 初始化业务数据Map对象
        Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);
        resultJson.setDataMap(dataMap);

        SysRole sysRole = roleService.findById(id);
        if (sysRole != null) {
            request.setAttribute(Constants.ACCOUNT, account);
            request.setAttribute(Constants.PORTAL_TYPE, 1);
            dataMap.put(ResultKey.ROLE_INFO_KEY, sysRole);
            resultJson.setDataMap(dataMap);
        } else {
            resultJson.setResultCode(ResultCode.FAILURE);
            resultJson.setResultMsg(ResultMsg.RESULT_FAILURE);
        }
        return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
    }

    @ApiOperation(value = "角色更新", notes = "角色更新")
    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public ResponseEntity<String> updateSysUser(HttpServletRequest request,
                                                @RequestBody SysRoleSaveReq req, @RequestParam String jsessionid) {

        SysUser cursysUser = (SysUser) redisUtil.get(jsessionid);
        String account = cursysUser.getAccount();

        // 初始化返回结果对象JsonResult
        JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

        // 初始化业务数据Map对象
        Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);


        SysRole sysRole = roleService.findById(req.getId());


        if (sysRole == null) {
            //直接返回，提示当前账号已经被注册
            resultJson.setResultCode(ResultCode.FAILURE);
            resultJson.setResultMsg("当前角色不存在");
            resultJson.setDataMap(dataMap);
            return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
        } else {
            sysRole.setName(req.getName());
            sysRole.setStatus(req.getStatus());
            sysRole=roleService.saveSysRole(sysRole);

            // 用户操作日志
            request.setAttribute(Constants.ACCOUNT, account);
            request.setAttribute(Constants.PORTAL_TYPE, req.getPortalType());

            if (sysRole != null) {

                // 设置用户操作日志参数和内容
                String logContent = "用户【" + account + "】修改角色信息：【" + sysRole.getName()
                        + "】成功.";
                request.setAttribute(Constants.USER_LOG, logContent);
                dataMap.put(ResultKey.ROLE_INFO_KEY, sysRole);
                resultJson.setDataMap(dataMap);
            } else {
                resultJson.setResultCode(ResultCode.FAILURE);
                resultJson.setResultMsg(ResultMsg.RESULT_FAILURE);
                resultJson.setDataMap(dataMap);
                return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
            }
        }
        return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
    }

    @ApiOperation(value = "查询角色对应的菜单", notes = "查询角色对应的菜单")
    @RequestMapping(value = "getResourceIdsByRoleId", method = RequestMethod.GET)
    public ResponseEntity<String> getResourceIdsByRoleId(HttpServletRequest request,
                                      @RequestParam String roleId, @RequestParam String jsessionid) {
        SysUser cursysUser = (SysUser) redisUtil.get(jsessionid);
        String account = cursysUser.getAccount();
        // 初始化返回结果对象JsonResult
        JsonResult<String, Object> resultJson = new JsonResult<String, Object>();
        // 初始化业务数据Map对象
        Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);
        resultJson.setDataMap(dataMap);

        SysModuleRole sysModuleRole=new SysModuleRole();
        sysModuleRole.setRoleId(roleId);
        List<SysModuleRole> list= roleService.getResourceIdsByRoleId(sysModuleRole);
        request.setAttribute(Constants.ACCOUNT, account);
        request.setAttribute(Constants.PORTAL_TYPE, 1);
        dataMap.put(ResultKey.MENU_LIST_KEY, list);
        resultJson.setDataMap(dataMap);
        return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
    }

    @ApiOperation(value = "角色授权", notes = "角色授权")
    @RequestMapping(value = "grant", method = RequestMethod.POST)
    public ResponseEntity<String> grant(HttpServletRequest request,
                                        @RequestBody SysRoleGrantReq req, @RequestParam String jsessionid) {

        SysUser cursysUser = (SysUser) redisUtil.get(jsessionid);
        String account = cursysUser.getAccount();

        // 初始化返回结果对象JsonResult
        JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

        // 初始化业务数据Map对象
        Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

            try{
                roleService.grant(req,cursysUser.getId());
                request.setAttribute(Constants.ACCOUNT, account);
                request.setAttribute(Constants.PORTAL_TYPE, req.getPortalType());
                String logContent = "用户【" + account + "】修改角色信息：【" + req.getRoleId()
                        + "】成功.";
                request.setAttribute(Constants.USER_LOG, logContent);
                resultJson.setDataMap(dataMap);
            }catch (Exception e){
                e.printStackTrace();
                resultJson.setResultCode(ResultCode.FAILURE);
                resultJson.setResultMsg(ResultMsg.RESULT_FAILURE);
                resultJson.setDataMap(dataMap);
                return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
            }
        return new ResponseEntity<String>(JsonUtil.toJson(resultJson), HttpStatus.OK);
    }


}
