package com.comtom.bc.dispatch.common;

public class BaseException extends RuntimeException {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;

	// 错误代号
	private int errorCode;

	// 错误描述
	private String errorDesc;

	// 原始异常
	private Exception originException;

	public BaseException(int errorCode) {
		super();
		this.errorCode = errorCode;
	}

	public BaseException(int errorCode, String errorDesc) {
		super(errorDesc);
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
	}

	public BaseException(int errorCode, String errorDesc, Exception originException) {
		super(errorDesc, originException);
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
		this.originException = originException;
	}

	/**
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode
	 *            the errorCode to set
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorDesc
	 */
	public String getErrorDesc() {
		return errorDesc;
	}

	/**
	 * @param errorDesc
	 *            the errorDesc to set
	 */
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	/**
	 * @return the originException
	 */
	public Exception getOriginException() {
		return originException;
	}

	/**
	 * @param originException
	 *            the originException to set
	 */
	public void setOriginException(Exception originException) {
		this.originException = originException;
	}

}
