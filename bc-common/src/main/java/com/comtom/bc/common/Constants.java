package com.comtom.bc.common;

/**
 * 常量表
 * 
 * @author admin
 */
public interface Constants {

	public static final String CONTENT_TYPE = "application/json; charset=utf-8";
	public static final String CHARACTER_ENCODING = "utf-8";
	public static final String EBD_VERSION = "1.0";
	
	/**
	 * 中文语言代码
	 */
	public static final String LANGUAGE_CODE_ZH = "zho";

	/**
	 * 日期时间格式
	 */
	public static final String DATA = "yyyy-MM-dd";
	public static final String DATATIME = "yyyy-MM-dd HH:mm:ss";

	/**
	 * Json输出模式。格式化输出模式。
	 */
	public static final String JSON_FORMAT = "0";

	public static final String JSESSIONID = "jsessionid";
	
	/**
	 * 平台登录令牌
	 */
	public static final String ACCESS_TOKEN = "token";
	
	/**
	 * 平台登录账号
	 */
	public static final String ACCESS_ACCOUNT = "accessAccount";

	/**
	 * 默认分页参数
	 */
	public static final int DEFAULT_PAGE_NUM = 1;
	public static final int DEFAULT_LIMIT = 20;

	/**
	 * 客户端类型标识
	 */
	public static final Integer ADMIN_PORTAL = 1;
	public static final Integer BROADCAST_CLIENT = 2;
	public static final Integer DISPATCH_CLIENT = 3;

	/**
	 * 默认区域显示级别
	 */
	public static final String DEFAULT_LEVEL = "2";

	/**
	 * 允许重复登录
	 */
	public static final String ALLOW_LOGIN = "1";

	/**
	 * 分隔符号
	 */
	public static final char SUFFIX_SPLIT = '.';
	public static final char FILE_SPLIT = '/';
	public static final char COMMA_SPLIT = ',';
	public static final char BLANK_SPLIT = ' ';

	/**
	 * 公共参数Key
	 */
	public static final String USER_LOG = "USERLOG";
	public static final String ACCOUNT = "account";
	public static final String PORTAL_TYPE = "portalType";

	/**
	 * 文件目录类型
	 */
	public static final String DIR_AUDIO = "audio";
	public static final String DIR_RECORD = "record";
	public static final String DIR_TEXT = "text";
	public static final String DIR_VIDEO = "video";

	/**
	 * 参数【键值，字典】Key
	 */
	public static final String JSESSIONID_TIMEOUT = "session_timeout_";
	public static final String DEFAULT_LOAD_LEVEL = "default_load_level_";
	public static final String FILE_PATH = "file_path_";
	public static final String PLATFORM_AREA_CODE = "plat_area_code_";
	public static final String PLATFORM_AREA_NAME = "plat_area_name_";
	public static final String EBR_PLATFORM_ID = "ebr_platform_id_";
	public static final String PARENT_PLATFORM_URL = "parent_platform_url_";
	public static final String RECEIVE_FILE_PATH = "receive_file_path_";
	public static final String SEND_FILE_PATH = "send_file_path_";
	public static final String USER_LOG_EXPIRE_TIME = "user_log_expire_time_";
	public static final String SCHEME_AUDIT_FLAG = "scheme_audit_";
	public static final String USER_LOGIN = "user_login_";
	public static final String INFO_ACCESS_AUDIT_ = "info_access_audit_";
	public static final String FINGERPRINT_THRESHOLD = "fingerprint_threshold";



	/**
	 * 序列号Id
	 */
	public static final String EBMID = "EBMID";
	public static final String EBDID = "EBDID";

	/**
	 * 节目状态 【1:新建 2:提交 3:取消】
	 */
	public static final Integer PROGRAM_STATE_CREATE = 1;
	public static final Integer PROGRAM_STATE_SUBMIT = 2;
	public static final Integer PROGRAM_STATE_CANCEL = 3;

	/**
	 * 方案状态 【1:新建 2:取消】
	 */
	public static final Integer SCHEME_STATE_CREATE = 1;
	public static final Integer SCHEME_STATE_SUBMIT = 2;
	public static final Integer SCHEME_STATE_CANCEL = 3;

	/**
	 * EBM消息状态
	 */
	public static final Integer EBM_STATE_INIT = 0;
	public static final Integer EBM_STATE_CREATE = 1;
	public static final Integer EBM_STATE_SEND_SUCCESS = 2;
	public static final Integer EBM_STATE_SEND_FAILED = 3;
	public static final Integer EBM_STATE_CANCEL = 9;

	/**
	 * EBM调度状态
	 */
	public static final Integer DISPATCH_STATE_INIT = 0;
	public static final Integer DISPATCH_STATE_DONE = 1;

	/**
	 * EBD数据包状态
	 */
	public static final Integer EBD_STATE_CREATE = 1;
	public static final Integer EBD_STATE_SEND_SUCCESS = 2;
	public static final Integer EBD_STATE_SEND_FAILED = 3;
	public static final Integer EBD_STATE_CANCEL = 9;

	/**
	 * 是否需要审核标识
	 */
	public static final Integer AUDIT_YES = 1;
	public static final Integer AUDIT_NO = 2;

	/**
	 * 审核标识
	 */
	public static final Integer AUDIT_INIT = 0;
	public static final Integer AUDIT_PASS = 1;
	public static final Integer AUDIT_NO_PASS = 2;

	/**
	 * 区域级别 省 市 区/县 街道/镇 社区/村
	 */
	public static final Integer AREA_LEVEL1 = 1;
	public static final Integer AREA_LEVEL2 = 2;
	public static final Integer AREA_LEVEL3 = 3;
	public static final Integer AREA_LEVEL4 = 4;
	public static final Integer AREA_LEVEL5 = 5;

	/**
	 * 资源状态
	 */
	public static final Integer EBR_STATE_RUNNING = 1;
	public static final Integer EBR_STATE_STOP = 2;
	public static final Integer EBR_STATE_FAULT = 3;
	public static final Integer EBR_STATE_RECOVER = 4;

	/**
	 * EBM辅助数据类型
	 */
	public static final Integer EBM_AUXILIARY_MP3 = 2;
	public static final Integer EBM_AUXILIARY_WAV = 3;

	/**
	 * 不可用的字符串主键取值
	 */
	public static final String INVALID_STRING_ID = "-1-2-3-4-5-6-7-8-9-0";

	/**
	 * 发送，接收标识定义
	 */
	public static final int RECEIVE_FLAG = 1;
	public static final int SEND_FLAG = 2;

	/**
	 * 消息的类型
	 */
	public static final Integer REQUEST_MSG = 1;
	public static final Integer CANCEL_MSG = 2;

	/**
	 * 单次心跳维持资源活跃状态时长，单位秒
	 */
	public static final Integer EBR_KEEP_ALIVE_SECOND = 7200;

	/**
	 * 资源ID前缀，资源一级类型
	 */
//	public static final String EBR_TYPE_PLATFORM = "01";
//	public static final String EBR_TYPE_ADAPTOR = "04";
//	public static final String EBR_TYPE_BROADCAST = "05";
//	public static final String EBR_TYPE_STATION = "03";
//	public static final String EBR_TYPE_TERMINAL = "06";

	/**
	 * 资源类型码
 	 */
	public static final String EBR_TYPE_PLATFORM = "0103";
	public static final String EBR_TYPE_STATION = "03";



	/**
	 *  资源子类型码
	 */
	public static final String EBR_THIS_LEVEL = "01"; //本级
	public static final String EBR_TYPE_ADAPTOR = "02"; // 应急广播适配器
	public static final String EBR_TYPE_BROADCAST = "03"; // 传输覆盖播出设备
	public static final String EBR_TYPE_TERMINAL = "04"; // 终端

	public static final String EBR_TYPE_EWBS_BS = "031403";//应急广播大喇叭系统(资源类型码+资源子类型码)




	/**
	 * 资源ID从19位开始寻找子类型码。
	 */
	public static final Integer SUBTYPE_TOFFSET = 19;

	/**
	 * 资源ID从13位开始寻找资源类型码。
	 */
	public static final Integer RESTYPE_TOFFSET = 13;


	/**
	 * 节目策略
	 */
	public static final Integer DAY_STRATEGY = 2;
	public static final Integer WEEK_STRATEGY = 3;

	public static final String AUDIT_PASS_STR = "审核通过";
	public static final String AUDIT_NOPASS_STR = "审核不通过";
	public static final String AUDIT_USER = "admin";

	/**
	 * 统计报表类型
	 */
	public static final int REPORT_DAILY = 1;
	public static final int REPORT_WEEKLY = 2;
	public static final int REPORT_MONTHLY = 3;
	public static final int REPORT_SEASONLY = 4;
	public static final int REPORT_HALF_YEARLY = 5;
	public static final int REPORT_YEARLY = 6;

	/**
	 * 用户信息状态（正常、删除）
	 */
	public static final String USER_NORMAL_FLAG = "0";
	public static final String USER_DELETE_FLAG = "1";
	
	/**
	 * 是否进行签名验证
	 */
	public static final boolean VERIFY_SIGN = true;

	/**
	 * 信息接入来源 (1:上级平台 2:自制作应急信息 3:其他第三方预警部门)
	 */
	public static final Integer INFO_SOURCE_SUPERIORS = 1;
	public static final Integer INFO_SOURCE_SELF = 2;
	public static final Integer INFO_SOURCE_OTHER = 3;

	/**
	 * 备份的数据库参数
	 */
	public static final String BACKUP_USER_KEY = "backup_user_";
	public static final String BACKUP_FILE_PATH_KEY = "backup_file_path_";
	public static final String BACKUP_PASSWORD_KEY = "backup_password_";
	public static final String BACKUP_HOST_KEY = "backup_host_";
	public static final String BACKUP_CMD_PATH_KEY = "backup_cmd_path_";
	public static final String BACKUP_DATABASE_NAME_KEY = "backup_database_name_";
    


	/**
	 * 节目类型（1:应急 2:日常 3:演练）
	 */
	public static final Integer PROGRAM_TYPE_DRILL  = 3;
}
