package com.comtom.bc.server.req;

/**
 * @author wjd
 * @create 2018/7/30 0030
 * @desc ${DESCRIPTION}
 **/
public class ResponseMessage {
    private String responseMessage;

    public ResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseMessage() {
        return responseMessage;
    }
}
