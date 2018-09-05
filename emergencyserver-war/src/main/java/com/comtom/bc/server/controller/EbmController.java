package com.comtom.bc.server.controller;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.result.JsonResult;
import com.comtom.bc.common.utils.JsonUtil;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.server.asset.WebCxt;
import com.comtom.bc.server.repository.entity.Ebd;
import com.comtom.bc.server.req.EbmReq;
import com.comtom.bc.server.service.EbmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author wjd
 * @create 2018/6/27 0027
 * @desc ${DESCRIPTION}
 **/
@Api(value = "应急广播消息")
@RestController
public class EbmController {

    @Autowired
    private EbmService ebmService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @ApiOperation(value = "发送广播消息", notes = "发送广播消息")
    @RequestMapping(value = "/sendEbm", method = RequestMethod.POST)
    public ResponseEntity<String> sendEbm(HttpServletRequest request, @RequestBody EbmReq req) {
        // 初始化返回结果对象JsonResult
        JsonResult<String, Object> resultJson = new JsonResult<String, Object>();

        // 初始化业务数据Map对象
        Map<String, Object> dataMap = WebCxt.newJsonMap(resultJson);

        stringRedisTemplate.convertAndSend("chat","test : "+String.valueOf(Math.random()));

        // 业务逻辑处理
     //   EBD ebdResponse=ebmService.sendEbm(req);

//        if (ebdResponse == null) {
//            return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
//                    HttpStatus.BAD_REQUEST);
//        }

        // 返回处理结果
        resultJson.setDataMap(dataMap);
        return new ResponseEntity<String>(JsonUtil.toJson(resultJson, Constants.DATATIME),
                HttpStatus.OK);
    }

}
