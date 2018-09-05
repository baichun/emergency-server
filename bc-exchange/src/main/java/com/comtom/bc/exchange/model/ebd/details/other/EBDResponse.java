package com.comtom.bc.exchange.model.ebd.details.other;

/**
 * @author nobody
 * 数据请求结果反馈
 */
public class EBDResponse {
	
	/**
	 * 执行结果代码：
	 *  0: 收到数据未处理 
	 *  1：接收解析及数据校验成功
	 *	2：接收解析失败 
	 *  3：数据内容缺失 
	 *  4: 签名验证失败 
	 *  5：其他错误 
	 *  对于执行结果代码为2-5的情况下,不会再进一步处理及发送相应的业务数据。
	 */
	private Integer resultCode;
	
	/**
	 * 执行结果描述 
	 */
	private String resultDesc;

	/**
	 * @return the 执行结果代码：0:收到数据未处理1：接收解析及数据校验成功2：接收解析失败3：数据内容缺失4:签名验证失败5：其他错误对于执行结果代码为2-5的情况下不会再进一步处理及发送相应的业务数据。
	 */
	public Integer getResultCode() {
		return resultCode;
	}

	/**
	 * @param 执行结果代码：0:收到数据未处理1：接收解析及数据校验成功2：接收解析失败3：数据内容缺失4:签名验证失败5：其他错误对于执行结果代码为2-5的情况下不会再进一步处理及发送相应的业务数据。 the resultCode to set
	 */
	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	/**
	 * @return the 执行结果描述
	 */
	public String getResultDesc() {
		return resultDesc;
	}

	/**
	 * @param 执行结果描述 the resultDesc to set
	 */
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	
}
