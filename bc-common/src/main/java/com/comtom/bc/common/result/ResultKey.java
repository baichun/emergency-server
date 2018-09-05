package com.comtom.bc.common.result;

/**
 * 返回参数或对象KEY标识
 * 
 * @author zhucanhui
 *
 */
public class ResultKey {

	/**
	 * 结果对象，结果码KEY
	 */
	public static final String RESULT_CODE_KEY = "resultCode";

	/**
	 * 结果对象，结果描述KEY
	 */
	public static final String RESULT_MSG_KEY = "resultMsg";

	/**
	 * 总记录数
	 */
	public static final String TOTAL_COUNT_KEY = "totalCount";

	/**
	 * 用户对象Key
	 */
	public static final String USER_INFO_KEY = "UserInfo";

	public static final String SYS_USER_FINGERPRINT_KEY = "SysUserFingerprint";


	public static final String ROLE_INFO_KEY = "RoleInfo";
	/**
	 * 文件信息对象Key
	 */
	public static final String FILE_INFO_KEY = "FileInfo";

	/**
	 * 节目信息对象Key
	 */
	public static final String PROGRAM_INFO_KEY = "ProgramInfo";

	public static final String PARAM_INFO_KEY = "ParamInfo";

	/**
	 * 调度方案
	 */
	public static final String SCHEME_INFO_KEY = "SchemeInfo";

	/**
	 * EBD数据包对象Key
	 */
	public static final String EBD_INFO_KEY = "EbdInfo";

	/**
	 * 调度流程信息对象Key
	 */
	public static final String FLOW_INFO_KEY = "FlowInfo";
	
	/**
	 * EBM消息对象Key
	 */
	public static final String EBM_INFO_KEY = "EbmInfo";

	/**
	 * 调度流程列表Key
	 */
	public static final String FLOW_LIST_KEY = "FlowList";

	/**
	 * 应急广播平台资源列表Key
	 */
	public static final String EBRPS_LIST_KEY = "EbrPsList";

	/**
	 * 适配器资源列表Key
	 */
	public static final String EBRAS_LIST_KEY = "EbrAsList";

	/**
	 * 播出系统资源列表Key
	 */
	public static final String EBRBS_LIST_KEY = "EbrBsList";

	/**
	 * 台站资源列表Key
	 */
	public static final String EBRSTA_LIST_KEY = "EbrStaList";

	/**
	 * 终端资源列表Key
	 */
	public static final String EBRTRM_LIST_KEY = "EbrTrmList";

	/**
	 * EBD数据包列表Key
	 */
	public static final String EBD_LIST_KEY = "EbdList";

	/**
	 * EBM数据包列表Key
	 */
	public static final String EBM_LIST_KEY = "EbmList";

	/**
	 * 行政区域列表Key
	 */
	public static final String AREA_LIST_KEY = "AreaList";

	/**
	 * 文件目录列表Key
	 */
	public static final String LIBRARY_LIST_KEY = "LibraryList";

    /**
     * 文件目录列表Key
     */
    public static final String LIBRARY_INFO_KEY = "LibraryInfo";

	/**
	 * 文件信息列表对象Key
	 */
	public static final String FILE_LIST_KEY = "FileInfoList";
	/**
	 * 媒体文件树形目录对象
	 */
	public static final String MEDIA_TREE_KEY = "MediaTreeList";

	/**
	 * 节目信息列表对象Key
	 */
	public static final String PROGRAM_LIST_KEY = "ProgramInfoList";

	/**
	 * 日志信息列表对象Key
	 */
	public static final String LOG_LIST_KEY = "LogInfoList";

	/**
	 * 事件分类信息列表对象Key
	 */
	public static final String EVENT_LIST_KEY = "EventTypeList";

	/**
	 * 调度方案信息列表对象Key
	 */
	public static final String SCHEME_LIST_KEY = "SchemeInfoList";
	
	/**
	 * 字典参数列表Key
	 */
	public static final String DIC_LIST_KEY = "DicList";

	/**
	 * 终端数量
	 */
	public static final String TERMINAL_TOTAL_KEY = "TerminalTotalCount";
	
	/**
	 * 平台下发目标
	 */
	public static final String PS_SEND_TARGET_COUNT = "PsSendTargetCount";

	/**
	 * 播出系统下发目标
	 */
	public static final String BS_SEND_TARGET_COUNT = "BsSendTargetCount";
	
	/**
	 * 平台下发完成
	 */
	public static final String PS_SEND_COMPLETE_COUNT = "PsSendCompleteCount";

	/**
	 * 播出系统下发完成
	 */
	public static final String BS_SEND_COMPLETE_COUNT = "BsSendCompleteCount";

	/**
	 * 平台分发成功
	 */
	public static final String PS_SEND_SUCCESS_COUNT = "PsSendSuccessCount";

	/**
	 * 播出系统分发成功
	 */
	public static final String BS_SEND_SUCCESS_COUNT = "BsSendSuccessCount";

	/**
	 * 平台分发失败
	 */
	public static final String PS_SEND_FAILED_COUNT = "PsSendFailedCount";

	/**
	 * 播出系统分发失败
	 */
	public static final String BS_SEND_FAILED_COUNT = "BsSendFailedCount";

	/**
	 * 平台响应未处理
	 */
	public static final String PS_UNPROCESS_COUNT = "PsUnProcessCount";

	/**
	 * 播出系统响应未处理
	 */
	public static final String BS_UNPROCESS_COUNT = "BsUnProcessCount";

	/**
	 * 平台响应等待处理
	 */
	public static final String PS_WAIT_COUNT = "PsWaitCount";

	/**
	 * 播出系统响应等待处理
	 */
	public static final String BS_WAIT_COUNT = "BsWaitCount";

	/**
	 * 平台响应处理中
	 */
	public static final String PS_INPROCESS_COUNT = "PsInProcessCount";

	/**
	 * 播出系统响应处理中
	 */
	public static final String BS_INPROCESS_COUNT = "BsInProcessCount";

	/**
	 * 平台响应播发成功
	 */
	public static final String PS_BRD_SUCCESS_COUNT = "PsBrdSuccessCount";

	/**
	 * 播出系统响应播发成功
	 */
	public static final String BS_BRD_SUCCESS_COUNT = "BsBrdSuccessCount";

	/**
	 * 平台响应播发失败
	 */
	public static final String PS_BRD_FAILED_COUNT = "PsBrdFailedCount";

	/**
	 * 播出系统响应播发失败
	 */
	public static final String BS_BRD_FAILED_COUNT = "BsBrdFailedCount";

	/**
	 * 平台响应播发取消
	 */
	public static final String PS_CANCEL_COUNT = "PsCancelCount";

	/**
	 * 播出系统响应播发取消
	 */
	public static final String BS_CANCEL_COUNT = "BsCancelCount";

	/**
	 * 方案资源数量统计
	 */
	public static final String EBR_COUNT_MAP = "EbrCountMap";

	/**
	 * 方案资源调度情况统计
	 */
	public static final String EBR_DISPATCH_COUNT_MAP = "EbrDispatchCountMap";

	/**
	 * 播发记录结果统计情况统计
	 */
	public static final String BRD_RESULT_COUNT_LIST = "BrdResultCountList";

	/**
	 * 调度流程状态统计
	 */
	public static final String FLOW_COUNT_MAP = "FlowCountMap";

	/**
	 * 预警响应阶段
	 */
	public static final String STAGE_RESPONSE_COUNT = "StageResponseCount";

	/**
	 * 预警处理阶段
	 */
	public static final String STAGE_PROCESS_COUNT = "StageProcessCount";

	/**
	 * 方案调整状态
	 */
	public static final String STATE_ADJUST_COUNT = "StateAdjustCount";

	/**
	 * 方案审核状态
	 */
	public static final String STATE_AUDIT_COUNT = "StateAuditCount";

	/**
	 * 信息接入
	 */
	public static final String INFO_ACCESS = "InfoAccess";

	/**
	 * 信息接入列表对象Key
	 */
	public static final String INFOACCESS_LIST_KEY = "InfoAccessList";

	/**
	 * 消息分发
	 */
	public static final String EBM_DISPATCH_KEY = "EbmDispatchList";

	/**
	 * 参数列表
	 */
	public static final String PARAM_LIST_KEY = "ParamList";

	/**
	 * 数据备份文件列表
	 */
	public static final String BACKUP_FILE_LIST_KEY = "BackupFileList";

	/**
	 * 数据备份文件信息
	 */
	public static final String BACKUP_FILE_KEY = "BackupFileInfo";

	/**
	 * 用户列表
	 */
	public static final String USER_LIST_KEY = "UserList";

	/**
	 * 角色列表
	 */
	public static final String ROLE_LIST_KEY = "RoleList";

	/**
	 * 菜单列表
	 */
	public static final String MENU_LIST_KEY = "MenuList";

	/**
	 * 菜单详情
	 */
	public static final String MENU_INFO_KEY = "MenuInfo";




}
