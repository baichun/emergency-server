package com.comtom.bc.server.task.statsic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.comtom.bc.common.Constants;
import com.comtom.bc.server.repository.dao.LogUserDAO;
import com.comtom.bc.server.repository.dao.LogUserHisDAO;
import com.comtom.bc.server.repository.dao.SysParamDAO;
import com.comtom.bc.server.repository.entity.LogUser;
import com.comtom.bc.server.repository.entity.LogUserHis;

/**
 * 用户日志转移到历史表定时任务
 * 
 * @author kidsoul
 *
 */
//@Component
//@Configurable
//@EnableScheduling
public class UserLogExpireTask {
	@Autowired
	private SysParamDAO sysParamDAO;
	
	@Autowired
	private LogUserDAO logUserDAO;
	
	@Autowired
	private LogUserHisDAO logUserHisDAO;

	/**
	 * 日志记录器
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(UserLogExpireTask.class);

	/**
	 * 用户日志转移到历史表
	 */
	@Scheduled(cron = "${task.user-log-history.cron}")
	public void moveToHistory() {
		Integer logExpireDay = 90;
		String logExpireTimeStr = sysParamDAO.findValueByKey(Constants.USER_LOG_EXPIRE_TIME);
		if(null != logExpireTimeStr && logExpireTimeStr.trim().length() > 0) {
			logExpireDay = Integer.valueOf(logExpireTimeStr);
		}
		
        BigDecimal gap = new BigDecimal(86400000).multiply(new BigDecimal(logExpireDay));
        BigDecimal expiredTime = new BigDecimal(new Date().getTime()).subtract(gap);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(expiredTime.longValue());
        final Date cutoffDate = cal.getTime();
		
        List<LogUser> userLogs = logUserDAO.findAll(new Specification<LogUser>(){
			public Predicate toPredicate(Root<LogUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				predicate.getExpressions().add(
							cb.lessThanOrEqualTo(root.<Date> get("logTime"),
									cutoffDate));
				return predicate;
			}
        	
        });
        
        if(null == userLogs || userLogs.size() < 1) {
        	return;
        }
        
        List<LogUserHis> logHistory = new ArrayList<LogUserHis>();
        for(LogUser log : userLogs) {
        	LogUserHis history = new LogUserHis();
        	history.setClientIp(log.getClientIp());
        	history.setContent(log.getContent());
        	history.setLogId(log.getLogId().intValue());
        	history.setLogTime(log.getLogTime());
        	history.setModule(log.getModule());
        	history.setOperation(log.getOperation());
        	history.setPortalType(log.getPortalType());
        	history.setUserName(log.getUserName());
        	
        	logHistory.add(history);
        }
        
        logUserHisDAO.save(logHistory);
        logUserDAO.delete(userLogs);

		logger.info("用户日志转移到历史表已经完成");
	}

}
