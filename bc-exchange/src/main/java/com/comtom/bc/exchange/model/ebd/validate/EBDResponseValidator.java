package com.comtom.bc.exchange.model.ebd.validate;

import org.apache.commons.lang.StringUtils;

import com.comtom.bc.exchange.model.ebd.details.other.EBDResponse;

public class EBDResponseValidator implements Validator {
	
	public String validateEntity(Object entity) {
        if(!EBDResponse.class.equals(entity.getClass())) {
        	return null;
        }
        
        EBDResponse ebdRes = (EBDResponse) entity;
        if(null == ebdRes.getResultCode()) {
        	return "执行结果代码不能为空";
        } else {
        	if(!ValidatorHelper.validResultCode(ebdRes.getResultCode())) {
        		return "执行结果代码系统暂不支持";
        	}
        }
        
        if(StringUtils.isEmpty(ebdRes.getResultDesc())) {
        	return "执行结果描述不能为空";
        }
        
		return null;
	}

}
