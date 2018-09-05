package com.comtom.bc.server.service;

import com.comtom.bc.server.repository.entity.JobAndTrigger;
import com.comtom.bc.server.repository.entity.JobTrigger;
import com.comtom.bc.server.repository.entity.RegionArea;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 定时调度服务类
 * 
 * @author wjd
 *
 */
public interface JobAndTiggerService {



	public Page<JobTrigger> getJobAndTriggerDetails(int pageNum, int pageSize);


	public List<JobAndTrigger> getAllJob();

}