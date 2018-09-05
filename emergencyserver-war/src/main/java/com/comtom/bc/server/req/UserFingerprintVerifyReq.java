package com.comtom.bc.server.req;

public class UserFingerprintVerifyReq extends  BaseReq{

    private String fpTemplate;

    public String getFpTemplate() {
        return fpTemplate;
    }

    public void setFpTemplate(String fpTemplate) {
        this.fpTemplate = fpTemplate;
    }
}
