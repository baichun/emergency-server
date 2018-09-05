package com.comtom.bc.server.controller;


import com.comtom.bc.common.Constants;
import com.comtom.bc.common.result.JsonResult;
import com.comtom.bc.common.utils.JsonUtil;
import com.comtom.bc.server.asset.WebCxt;
import com.comtom.bc.server.repository.entity.StatBroadcast;
import com.comtom.bc.server.req.StatsicListReq;
import com.comtom.bc.server.service.StatBroadcastAreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 广播数据分区域统计
 *
 * @author wj
 */

@Api(value = "广播数据分区域统计")
@RestController
@RequestMapping(value = "api/statBroadcastArea")
public class StatBroadcastAreaController {


    @Autowired
    private StatBroadcastAreaService statBroadcastAreaService;



    @ApiOperation(value = "广播类型统计", notes = "广播类型统计")
    @RequestMapping(value = "typeCount", method = RequestMethod.GET)
    public ResponseEntity<String> listHistory(HttpServletRequest request, @RequestParam String jsessionid) {

        // 初始化返回结果对象JsonResult
        JsonResult<String, Object> resultJson = new JsonResult<String, Object>();
        resultJson.setTotalCount(0);

        // 初始化业务数据Map对象
        Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

        // 业务逻辑处理
        Object statBoradcastTypeCount = statBroadcastAreaService.typeCount();
        if(null != statBoradcastTypeCount) {
            Object[] arr=(Object[])statBoradcastTypeCount;
            String[] nameArr={"日常广播","I级应急广播","II级应急广播","III级应急广播","IV级应急广播"};

            List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
            for(int i=0;i<arr.length;i++){
                Map<String,Object> map=new HashMap<String,Object>();
                map.put("name",nameArr[i]);
                map.put("y",arr[i]);
                list.add(map);
            }
            dataMap.put("statBoradcastTypeCount", list);
            resultJson.setTotalCount(arr.length);
        }

        // 返回处理结果
        resultJson.setDataMap(dataMap);
        return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME), HttpStatus.OK);
    }

}
