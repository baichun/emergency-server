package com.comtom.bc.dispatch;

import com.comtom.bc.common.utils.DateUtil;

/**
 * 调度流程变量定义
 * 
 * @author zhucanhui
 *
 */
public class FlowConstants {

	/**
	 * 调度阶段定义
	 */
	public static final Integer STAGE_STARTING = 1;
	public static final Integer STAGE_RESPONSE = 2;
	public static final Integer STAGE_PROCESS = 3;
	public static final Integer STAGE_COMPLETE = 4;
	public static final Integer STAGE_CANCEL = 5;

	/**
	 * 调度状态定义
	 */
	// 预警触发 11:节目制作 12:节目审核 13:审核通过 14:审核不通过<br>
	public static final Integer STATE_INFO = 11;
	public static final Integer STATE_INFO_AUDIT = 12;
	public static final Integer STATE_INFO_AUDIT_YES = 13;
	public static final Integer STATE_INFO_AUDIT_NO = 14;

	// 预警响应
	public static final Integer STATE_SCHEME = 21;
	public static final Integer STATE_SCHEME_OPTIMIZE = 22;
	public static final Integer STATE_SCHEME_ADJUST = 23;
	public static final Integer STATE_SCHEME_MAKE = 24;
	public static final Integer STATE_SCHEME_AUDIT = 25;
	public static final Integer STATE_SCHEME_AUDIT_YES = 26;
	public static final Integer STATE_SCHEME_AUDIT_NO = 27;

	// 预警处理
	public static final Integer STATE_MSG_SEND = 31;
	public static final Integer STATE_MSG_RELEASE = 32;

	// 预警完成
	public static final Integer STATE_COMPLETE = 41;
	
	// 预警取消
	public static final Integer STATE_CANCEL = 51;

	/**
	 * 事件级别
	 */
	public static final Integer SEVERITY_0 = 0;
	public static final Integer SEVERITY_1 = 1;
	public static final Integer SEVERITY_2 = 2;
	public static final Integer SEVERITY_3 = 3;
	public static final Integer SEVERITY_4 = 4;
	public static final Integer SEVERITY_10 = 10;
	public static final Integer SEVERITY_15 = 15;

	public static final String SCHEME_NAME_PREFIX = "调度方案-";

	public static final String getSchemeName() {
		return FlowConstants.SCHEME_NAME_PREFIX
				+ DateUtil.getDateTime(DateUtil.DATE_PATTERN.YYYYMMDDHHMMSS);
	}

}
