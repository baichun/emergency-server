package com.comtom.bc.server.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.comtom.bc.common.utils.CommonUtil;
import com.comtom.bc.server.repository.dao.EbmBrdRecordDAO;
import com.comtom.bc.server.repository.entity.EbmBrdRecord;
import com.comtom.bc.server.service.EbmBrdRecordService;
import com.comtom.bc.server.service.base.BaseService;

/**
 * 播发记录业务逻辑处理
 * 
 * @author kidsoul
 *
 */
@Service("EbmBrdRecordService")
public class EbmBrdRecordServiceImpl extends BaseService implements EbmBrdRecordService {
	@Autowired
	private EbmBrdRecordDAO ebmBrdRecordDAO;

	private Sort getDefaultSort() {
		return new Sort(Direction.DESC, "startTime");
	}
	
	private Specification<EbmBrdRecord> getStartTimeWhereClause(final Date rptStartTime, final Date rptEndTime) {
		return new Specification<EbmBrdRecord>() {
			public Predicate toPredicate(Root<EbmBrdRecord> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
					if (CommonUtil.isNotEmpty(rptStartTime)) {
						predicate.getExpressions().add(
								cb.greaterThanOrEqualTo(r.<Date> get("startTime"), 
										rptStartTime));
					}

					if (CommonUtil.isNotEmpty(rptEndTime)) {
						predicate.getExpressions().add(
								cb.lessThanOrEqualTo(r.<Date> get("startTime"),
										rptEndTime));
					}

					return predicate;
				}
		};
	}

	public List<EbmBrdRecord> listViaStartTime(boolean useRptTime, Date rptStartTime, Date rptEndTime) {
        if(useRptTime == false) {
        	rptStartTime = null;
        	rptEndTime = null;
        }
        
		return ebmBrdRecordDAO.findAll(getStartTimeWhereClause(rptStartTime, rptEndTime), getDefaultSort());
	}

	private Specification<EbmBrdRecord> getEndTimeWhereClause(final Date rptStartTime, final Date rptEndTime) {
		return new Specification<EbmBrdRecord>() {
			public Predicate toPredicate(Root<EbmBrdRecord> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
					if (CommonUtil.isNotEmpty(rptStartTime)) {
						predicate.getExpressions().add(
								cb.greaterThanOrEqualTo(r.<Date> get("endTime"), 
										rptStartTime));
					}

					if (CommonUtil.isNotEmpty(rptEndTime)) {
						predicate.getExpressions().add(
								cb.lessThanOrEqualTo(r.<Date> get("endTime"),
										rptEndTime));
					}

					return predicate;
				}
		};
	}

	public List<EbmBrdRecord> listViaEndTime(boolean useRptTime, Date rptStartTime, Date rptEndTime) {
        if(useRptTime == false) {
        	rptStartTime = null;
        	rptEndTime = null;
        }
        
        return ebmBrdRecordDAO.findAll(getEndTimeWhereClause(rptStartTime, rptEndTime), getDefaultSort());
	}

	private List<EbmBrdRecord> distinctViaId(List<EbmBrdRecord> brdRecords1, List<EbmBrdRecord> brdRecords2) {
		if(null == brdRecords1 || brdRecords1.size() < 1) {
			return brdRecords2;
		}
		if(null == brdRecords2 || brdRecords2.size() < 1) {
			return brdRecords1;
		}
		
		List<String> rcdIds = new ArrayList<String>(brdRecords1.size() + brdRecords2.size());
		for(EbmBrdRecord rcd : brdRecords1) {
			rcdIds.add(rcd.getBrdItemId());
		}
		
		List<EbmBrdRecord> result = new ArrayList<EbmBrdRecord>(rcdIds.size());
		result.addAll(brdRecords1);
		
		for(EbmBrdRecord rcd : brdRecords2) {
			if(!rcdIds.contains(rcd.getBrdItemId())) {
				result.add(rcd);
			}
		}
		
		Collections.sort(result, new Comparator<EbmBrdRecord>(){
			public int compare(EbmBrdRecord o1, EbmBrdRecord o2) {
				return o1.getStartTime().compareTo(o2.getStartTime());
			}});
		
		return result;	
	}
	
	public List<EbmBrdRecord> listViaStartOrEndTime(boolean useRptTime, Date rptStartTime, Date rptEndTime) {
		List<EbmBrdRecord> brdRecordFound = listViaStartTime(useRptTime, rptStartTime, rptEndTime);
		if(useRptTime) {
			List<EbmBrdRecord> endTimeRecords = listViaEndTime(useRptTime, rptStartTime, rptEndTime);
			brdRecordFound = distinctViaId(brdRecordFound, endTimeRecords);
		}
		
		if(null == brdRecordFound) {
			brdRecordFound = new ArrayList<EbmBrdRecord>();
		}
		
		return brdRecordFound;
	}
	
}
