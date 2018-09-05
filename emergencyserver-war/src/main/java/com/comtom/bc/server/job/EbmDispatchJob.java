package com.comtom.bc.server.job;

import com.comtom.bc.common.Constants;
import com.comtom.bc.server.service.EbdService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * EBM调度处理定时任务(向大喇叭系统发送消息)
 * 
 *
 */
@DisallowConcurrentExecution
public class EbmDispatchJob implements Job {

	@Autowired
	private EbdService ebdService;

	/**
	 * 日志记录器
	 */
	private static final Logger logger = LoggerFactory.getLogger(EbmDispatchJob.class);

	/**
	 * 处理EBM消息分发处理定时器
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException {

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
