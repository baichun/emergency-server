package com.comtom.bc.server.req;

import com.comtom.bc.server.repository.entity.ProgramArea;
import com.comtom.bc.server.repository.entity.ProgramFiles;
import com.comtom.bc.server.repository.entity.ProgramStrategy;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.List;

/**
 *参数配置更新请求体
 * WJ
 *
 */
public class ParamUpdateReq extends BaseReq {


	private String id;


	private String value;


	private String isOverwrite;


	private String overwriteField;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getIsOverwrite() {
		return isOverwrite;
	}

	public void setIsOverwrite(String isOverwrite) {
		this.isOverwrite = isOverwrite;
	}

	public String getOverwriteField() {
		return overwriteField;
	}

	public void setOverwriteField(String overwriteField) {
		this.overwriteField = overwriteField;
	}
}
