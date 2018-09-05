package com.comtom.bc.dispatch.common;

/**
 * 业务错误码定义
 * 
 * @author zhucanhui
 *
 */
public enum ErrorEnum {

	scheme_add_error(101, "生成调度方案异常."), 
	ebr_match_error(102, "无调度资源可用."), 
	ebm_add_error(103,"调度消息生成失败."),
	ebm_cancel_error(104, "播发消息取消失败."),
	;
	

	/** 异常编码 */
	private Integer errorCode;

	/** 异常信息 */
	private String errorMsg;

	private ErrorEnum(Integer errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	};

	public Integer getErrorCode() {
		return this.errorCode;
	}

	public String getErrorMsg() {
		return this.errorMsg;
	}

}
