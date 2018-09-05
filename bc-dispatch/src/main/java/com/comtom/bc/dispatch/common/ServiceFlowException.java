package com.comtom.bc.dispatch.common;

/**
 * 业务流程异常
 * 
 * @author zhucanhui
 *
 */
public class ServiceFlowException extends BaseException {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -8069815003364156391L;

	public ServiceFlowException(ErrorEnum errorEnum) {
		super(errorEnum.getErrorCode(), errorEnum.getErrorMsg());
	}

	public ServiceFlowException(ErrorEnum paramError, String message) {
		super(paramError.getErrorCode(), paramError.getErrorMsg() + ":" + message);
	}

	public ServiceFlowException(int errorCode) {
		super(errorCode);
	}

	public ServiceFlowException(int errorCode, String errorDesc, Exception originException) {
		super(errorCode, errorDesc, originException);
	}

	public ServiceFlowException(int errorCode, String errorDesc) {
		super(errorCode, errorDesc);
	}
}
