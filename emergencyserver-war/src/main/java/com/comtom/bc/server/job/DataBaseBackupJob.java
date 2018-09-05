package com.comtom.bc.server.job;

import com.comtom.bc.common.Constants;
import com.comtom.bc.common.utils.DateUtil;
import com.comtom.bc.server.repository.entity.DBBackupFile;
import com.comtom.bc.server.service.DBBackupFileService;
import com.comtom.bc.server.service.DBBackupService;
import com.comtom.bc.server.service.EbdService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * EBM调度处理定时任务(向大喇叭系统发送消息)
 * 
 *
 */
@DisallowConcurrentExecution
public class DataBaseBackupJob implements Job {

	@Autowired
	private DBBackupFileService backupService;

	/**
	 * 日志记录器
	 */
	private static final Logger logger = LoggerFactory.getLogger(DataBaseBackupJob.class);

	/**
	 * 数据库系统自动备份定时器
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException {
		long startSecond = new Date().getTime();
		DBBackupFile file = new DBBackupFile();
		file.setFileName("数据库系统自动备份 "+DateUtil.getDateTime());
		file.setCreator("system");
		file.setCreateTime(new Date());
		file.setBackupType("1");
		file.setRemark("系统自动备份数据库于："+DateUtil.getDateTime());
		DBBackupFile backupFile = backupService.backup(file);
		long endSecond = new Date().getTime();
		if (backupFile != null) {
			logger.info("数据库系统自动备份成功！");
			logger.info("备份时间："+DateUtil.getDateTime());
			logger.info("备份文件名称："+backupFile.getFileName());
			logger.info("备份持续时长："+(endSecond - startSecond) + " 毫秒");

		}
	}
}
