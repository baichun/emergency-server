package com.comtom.bc.server.req;

import java.util.List;

import com.comtom.bc.server.repository.entity.SchemeEbr;

/**
 * 调整方案请求参数对象
 * 
 * @author zhucanhui
 *
 */
public class SchemeUpdateReq extends BaseReq {

	/**
	 * 调度方案Id
	 */
	private Integer schemeId;

	/**
	 * 方案关联资源
	 */
	private List<SchemeEbr> schemeEbrList;

	/**
	 * @return the schemeId
	 */
	public Integer getSchemeId() {
		return schemeId;
	}

	/**
	 * @param schemeId
	 *            the schemeId to set
	 */
	public void setSchemeId(Integer schemeId) {
		this.schemeId = schemeId;
	}

	/**
	 * @return the schemeEbrList
	 */
	public List<SchemeEbr> getSchemeEbrList() {
		return schemeEbrList;
	}

	/**
	 * @param schemeEbrList
	 *            the schemeEbrList to set
	 */
	public void setSchemeEbrList(List<SchemeEbr> schemeEbrList) {
		this.schemeEbrList = schemeEbrList;
	}

}
