package com.comtom.bc.server.job;

import com.comtom.bc.common.utils.DateUtil;
import com.comtom.bc.exchange.commom.EBDRespResultEnum;
import com.comtom.bc.exchange.model.ebd.EBD;
import com.comtom.bc.exchange.model.ebd.details.other.ConnectionCheck;
import com.comtom.bc.exchange.model.ebd.details.other.EBDResponse;
import com.comtom.bc.exchange.model.signature.Signature;
import com.comtom.bc.exchange.util.*;
import com.comtom.bc.server.service.impl.BaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * 心跳上报定时任务
 *
 */
@DisallowConcurrentExecution // 不允许并发执行,保证上一个任务执行完后，再去执行下一个任务
public class HeatBeatJob implements Job {
  
    private static Logger logger = LoggerFactory.getLogger(HeatBeatJob.class);

    @Autowired
    private BaseServiceImpl serviceImpl;


    public void execute(JobExecutionContext context) throws JobExecutionException {
       //  上级平台地址
        String parentUrl = serviceImpl.getParentPlatUrl();
        if (StringUtils.isEmpty(parentUrl)) {
            return;
        }
        // 平台地址
        String srcURL = serviceImpl.getPlatFormUrl();
        // ebd序列号
        String ebdIndex = "0000000000000000";
        // 平台ID
        String ebrId = serviceImpl.getEbrPlatFormID();

        // 接收文件保存路径
        String filePathReceive = serviceImpl.getReceiveEbdUrl();

        ConnectionCheck connectionCheck = new ConnectionCheck();
        Date date = new Date();
        String rptTime = DateTimeUtil.dateToString(date, DateStyle.YYYY_MM_DD_HH_MM_SS);
        connectionCheck.setRptTime(rptTime);

        EBD ebd = EBDModelBuild.buildConnectionCheck(ebrId, srcURL, ebdIndex, connectionCheck);

        // 发送文件保存路径（根据文件类型区分文件夹）
        String filePathSend = serviceImpl.getSendEbdUrl() + File.separator + ebd.getEBDType();

        File file = FileUtil.converFile(filePathSend, ebd);

        // 生成签名和签名文件
        Signature signature = EBDModelBuild.buildSignature(file, ebd.getEBDID());
        File signFile = FileUtil.converFile(filePathSend, signature);

        List<File> files = new ArrayList<File>();
        files.add(file);
        files.add(signFile);
        File tarfile = TarFileUtil.compressorsTar(ebd, files, filePathSend);
        EBD ebdReceive = HttpRequestUtil.sendFile(tarfile, parentUrl, filePathReceive);
        if (ebdReceive == null) {
            logger.error("发送心跳错误");
            return;
        }
        EBDResponse ebdResponse = ebdReceive.getEBDResponse();
        if (ebdResponse == null) {
            logger.error("发送心跳错误");
            return;
        }
        Integer resultCode = ebdResponse.getResultCode();

        if (!EBDRespResultEnum.receivevalid.getCode().equals(resultCode)) {
            logger.error("发送心跳错误");
            return;
        }
    }


}  
