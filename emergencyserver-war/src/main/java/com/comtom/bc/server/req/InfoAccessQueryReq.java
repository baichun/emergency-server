package com.comtom.bc.server.req;

public class InfoAccessQueryReq extends BaseReq {

	/**
	 * 标题
	 */
	private String title;

	private String auditResult;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuditResult() {
		return auditResult;
	}

	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}
}
