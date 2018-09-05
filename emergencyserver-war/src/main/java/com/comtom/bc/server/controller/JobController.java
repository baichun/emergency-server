package com.comtom.bc.server.controller;


import com.comtom.bc.server.repository.entity.JobAndTrigger;
import com.comtom.bc.server.service.JobAndTiggerService;
import io.swagger.annotations.Api;
import org.hibernate.service.spi.ServiceException;
import org.quartz.*;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 任务调度管理
 */
@Api(value = "定时任务")
@RestController
@RequestMapping(value="/job")
public class JobController
{
	@Autowired
	private JobAndTiggerService  jobAndTriggerService;

	@Autowired
	private Scheduler scheduler;

	private static Logger log = LoggerFactory.getLogger(JobController.class);


	@PostMapping(value="/addjob")
	public void addjob(
			@RequestParam(value="jobName")String jobName,
			@RequestParam(value="jobClassName")String jobClassName,
			@RequestParam(value="jobGroupName")String jobGroupName,
			@RequestParam(value="cronExpression")String cronExpression,
			@RequestParam(value="jobDescription")String jobDescription
			) throws Exception
	{
		jobName = URLDecoder.decode(jobName,"utf-8");
		jobGroupName = URLDecoder.decode(jobGroupName,"utf-8");
		jobDescription = URLDecoder.decode(jobDescription,"utf-8");
		addJob(jobClassName,jobName, jobGroupName, cronExpression,jobDescription);
	}

	public void addJob(String jobClassName,String jobName, String jobGroupName, String cronExpression,String jobDescription)throws Exception {

		//构建job信息
		JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass()).withIdentity(jobName, jobGroupName).withDescription(jobDescription).build();

		//表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroupName)
            .withSchedule(scheduleBuilder).build();

        try {
			scheduler.scheduleJob(jobDetail, trigger);
			// 启动调度器
			scheduler.start();
        } catch (SchedulerException e) {
            System.out.println("创建定时任务失败"+e);
            throw new Exception("创建定时任务失败");
        }
	}


	/**
	 * 暂停
	 * @param jobClassName
	 * @param jobGroupName
	 * @throws Exception
	 */
	@PostMapping(value="/pausejob")
	public void pausejob(@RequestParam(value="jobClassName")String jobClassName, @RequestParam(value="jobGroupName")String jobGroupName) throws Exception
	{
		jobClassName = URLDecoder.decode(jobClassName,"utf-8");
		jobGroupName = URLDecoder.decode(jobGroupName,"utf-8");
		jobPause(jobClassName, jobGroupName);
	}


	public void jobPause(String jobClassName, String jobGroupName) throws Exception
	{
		scheduler.pauseJob(JobKey.jobKey(jobClassName, jobGroupName));
	}

	/**
	 * 恢复任务
	 * @param jobClassName
	 * @param jobGroupName
	 * @throws Exception
	 */
	@PostMapping(value="/resumejob")
	public void resumejob(@RequestParam(value="jobClassName")String jobClassName, @RequestParam(value="jobGroupName")String jobGroupName) throws Exception
	{
		jobClassName = URLDecoder.decode(jobClassName,"utf-8");
		jobGroupName = URLDecoder.decode(jobGroupName,"utf-8");
		jobresume(jobClassName, jobGroupName);
	}

	public  void jobresume(String jobClassName, String jobGroupName) throws Exception
	{
		scheduler.resumeJob(JobKey.jobKey(jobClassName, jobGroupName));
	}


	@PostMapping(value="/reschedulejob")
	public void rescheduleJob(@RequestParam(value="jobClassName")String jobClassName,
			@RequestParam(value="jobGroupName")String jobGroupName,
			@RequestParam(value="cronExpression")String cronExpression) throws Exception
	{
		jobClassName = URLDecoder.decode(jobClassName,"utf-8");
		jobGroupName = URLDecoder.decode(jobGroupName,"utf-8");
		jobreschedule(jobClassName, jobGroupName, cronExpression);
	}

	public  void jobreschedule(String jobClassName, String jobGroupName, String cronExpression) throws Exception
	{
		try {
			TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName, jobGroupName);
			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		} catch (SchedulerException e) {
			System.out.println("更新定时任务失败"+e);
			throw new Exception("更新定时任务失败");
		}
	}


	/**
	 * 删除任务
	 * @param jobClassName
	 * @param jobGroupName
	 * @throws Exception
	 */
	@PostMapping(value="/deletejob")
	public void deletejob(@RequestParam(value="jobClassName")String jobClassName, @RequestParam(value="jobGroupName")String jobGroupName) throws Exception
	{
		jobClassName = URLDecoder.decode(jobClassName,"utf-8");
		jobGroupName = URLDecoder.decode(jobGroupName,"utf-8");
		jobdelete(jobClassName, jobGroupName);
	}

	public  void jobdelete(String jobClassName, String jobGroupName) throws Exception
	{
		// 通过SchedulerFactory获取一个调度器实例
//		SchedulerFactory sf = new StdSchedulerFactory();
//		Scheduler sched = sf.getScheduler();
		scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
		scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));
		scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));
	}

	/**
	 * 获取Cron表达式
	 * @param triggerName 触发器
	 * @param triggerGroup 工作组
	 */
	public CronTriggerImpl getCronTrigger(String triggerName, String triggerGroup) {
		try {
//			SchedulerFactory sf = new StdSchedulerFactory();
//			Scheduler sched = sf.getScheduler();
			return (CronTriggerImpl) scheduler.getTrigger(new TriggerKey(triggerName, triggerGroup));
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return null;
	}


	@GetMapping(value="/queryjob")
	public Map<String, Object> queryjob(@RequestParam(value="pageNum")Integer pageNum, @RequestParam(value="pageSize")Integer pageSize)
	{
	//	Page<JobTrigger> page=jobAndTriggerService.getJobAndTriggerDetails(pageNum,pageSize);
		List<JobAndTrigger> list = jobAndTriggerService.getAllJob();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("JobAndTrigger", list);
		map.put("number", 2);
		return map;
	}

	public Job getClass(String classname)
	{
		Class<?> class1 = null;
		try {
			class1 = Class.forName(classname);
			Job baseJob = (Job) class1.newInstance();
			return baseJob;
		} catch (Exception e) {
			throw new ServiceException("类名不存在");
		}
	}


}

