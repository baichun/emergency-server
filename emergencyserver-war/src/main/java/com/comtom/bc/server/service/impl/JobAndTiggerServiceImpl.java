package com.comtom.bc.server.service.impl;

import com.comtom.bc.server.repository.dao.JobTiggerDao;
import com.comtom.bc.server.repository.entity.JobAndTrigger;
import com.comtom.bc.server.repository.entity.JobTrigger;
import com.comtom.bc.server.service.JobAndTiggerService;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * 定时调度实现类
 * 
 * @author wjd
 *
 */
@Service("JobAndTiggerService")
public class JobAndTiggerServiceImpl implements JobAndTiggerService {

	@Autowired
	private JobTiggerDao jobTiggerDao;

	@PersistenceContext
	private EntityManager em;

	public Page<JobTrigger> getJobAndTriggerDetails(int pageNum, int pageSize) {
		Pageable pageable = new PageRequest(pageNum - 1,pageSize);
		return jobTiggerDao.findAll(pageable);
	}

	public List<JobAndTrigger> getAllJob(){
		String sql="SELECT qrtz_job_details.JOB_NAME, qrtz_job_details.JOB_GROUP, qrtz_job_details.JOB_CLASS_NAME, qrtz_triggers.TRIGGER_NAME,qrtz_job_details.DESCRIPTION,\n qrtz_triggers.TRIGGER_GROUP, qrtz_cron_triggers.CRON_EXPRESSION, qrtz_cron_triggers.TIME_ZONE_ID, \n" +
				"   FROM_UNIXTIME(qrtz_triggers.PREV_FIRE_TIME/1000,'%Y-%m-%d %H:%i:%s') PREV_FIRE_TIME,\n" +
				" FROM_UNIXTIME(qrtz_triggers.NEXT_FIRE_TIME/1000,'%Y-%m-%d %H:%i:%s') NEXT_FIRE_TIME,\n"+
				"case  qrtz_triggers.TRIGGER_STATE "+
				" when 'ACQUIRED'  then '正在执行'  when 'WAITING' then  '等待'  when 'PAUSED'  then '暂停'  when 'BLOCKED' then  '阻塞'   when  'ERROR' then '错误'  end as TRIGGER_STATE \n"+
				" FROM qrtz_job_details JOIN qrtz_triggers JOIN qrtz_cron_triggers ON qrtz_job_details.JOB_NAME = qrtz_triggers.JOB_NAME AND qrtz_triggers.TRIGGER_NAME = qrtz_cron_triggers.TRIGGER_NAME\n" +
				" AND qrtz_triggers.TRIGGER_GROUP = qrtz_cron_triggers.TRIGGER_GROUP order by qrtz_triggers.START_TIME ";
      Query query=em.createNativeQuery(sql);
      query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
      List<JobAndTrigger> rows= query.getResultList();
      return rows;
		//return em.createNativeQuery(sql).getResultList();

	}
}