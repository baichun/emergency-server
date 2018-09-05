package com.comtom.bc.server.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.comtom.bc.common.Constants;
import com.comtom.bc.server.service.EbdService;

/**
 * EBM调度处理定时任务
 * 
 * @author zhucanhui
 *
 */
//@Component
//@Configurable
//@EnableScheduling
public class EbmDispatchTask {

	@Autowired
	private EbdService ebdService;

	/**
	 * 日志记录器
	 */
	private static final Logger logger = LoggerFactory.getLogger(EbmDispatchTask.class);

	/**
	 * 处理EBM消息分发处理定时器
	 */
	@Scheduled(cron = "${task.ebm-dispatch.cron}")
	public void executeEbdDispatch() {

		// Step.1 获取待分发处理的EBD数据包
		// Step.2 根据联动接口协议写入XML
		// Step.3 进行打包TAR处理
		// Step.4 发送至对应的平台或播出系统资源
		int ebdListSize = ebdService.dispatchEbdPack(Constants.EBM_STATE_CREATE);
		
		if (logger.isDebugEnabled()) {
			logger.info("EbmDispatchTask execute ebd dispatch successfully. dispatch ebd pack={}",
					ebdListSize);
		}
	}
}
