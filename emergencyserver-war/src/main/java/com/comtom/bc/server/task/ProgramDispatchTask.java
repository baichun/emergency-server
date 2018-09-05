package com.comtom.bc.server.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.server.repository.entity.ProgramInfo;
import com.comtom.bc.server.repository.entity.SchemeInfo;
import com.comtom.bc.server.service.ProgramService;
import com.comtom.bc.server.service.SchemeService;

/**
 * 节目调度（根据节目策略）处理定时任务
 * 
 * @author zhucanhui
 *
 */
//@Component
//@EnableScheduling
public class ProgramDispatchTask {

	/**
	 * 日志记录器
	 */
	private static final Logger logger = LoggerFactory.getLogger(ProgramDispatchTask.class);

	@Autowired
	private ProgramService programService;

	@Autowired
	private SchemeService schemeService;

	/**
	 * 处理周期性节目调度<br>
	 * 调度策略:每天2点执行
	 */
	@Scheduled(cron = "${task.program-dispatch.cron}")
	public void executeProgramDispatch() {

		// 获取节目策略信息(有效期内的周期性节目)
		List<ProgramInfo> programList = programService.getDispatchProgram();

		// 根据节目获取对应的调度方案
		for (ProgramInfo programInfo : programList) {

			try {
				SchemeInfo schemeInfo = schemeService.findByProgramId(programInfo.getProgramId());

				if (CommonUtil.isNotEmpty(schemeInfo)) {
					schemeService.generateEbm(programInfo, schemeInfo);
				}
			} catch (RuntimeException e) {
				logger.error("Program dispatch exception. ", e);
				continue;
			}
		}
	}

}
