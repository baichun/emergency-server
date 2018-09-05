package com.comtom.bc.server.service;

import java.util.Date;
import java.util.List;

import com.comtom.bc.server.repository.entity.EbmBrdRecord;

/**
 * 播发记录业务逻辑处理接口定义
 * 
 * @author kidsoul
 *
 */
public interface EbmBrdRecordService {
	/**
	 * 根据播发开始时间查找播发记录列表
	 * 
	 * @param useRptTime
	 * @param rptStartTime
	 * @param rptEndTime
	 * @return
	 */
    public List<EbmBrdRecord> listViaStartTime(boolean useRptTime, Date rptStartTime, Date rptEndTime);
    
	/**
	 * 根据播发结束时间查找播发记录列表
	 * 
	 * @param useRptTime
	 * @param rptStartTime
	 * @param rptEndTime
	 * @return
	 */
    public List<EbmBrdRecord> listViaEndTime(boolean useRptTime, Date rptStartTime, Date rptEndTime);
    
    /**
     * 根据播发开始时间或播发结束时间查找播发记录列表
     * 
     * @param useRptTime
     * @param rptStartTime
     * @param rptEndTime
     * @return
     */
    public List<EbmBrdRecord> listViaStartOrEndTime(boolean useRptTime, Date rptStartTime, Date rptEndTime);
}
