package com.comtom.bc.server.req;

import java.util.List;

public class UserFingerprintReq extends  BaseReq{

    private String regTemplate;

    private Integer fingerPosition;

    public Integer getFingerPosition() {
        return fingerPosition;
    }

    public void setFingerPosition(Integer fingerPosition) {
        this.fingerPosition = fingerPosition;
    }

    public String getRegTemplate() {
        return regTemplate;
    }

    public void setRegTemplate(String regTemplate) {
        this.regTemplate = regTemplate;
    }
}
