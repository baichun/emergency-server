package com.comtom.bc.server.controller;

import com.comtom.bc.server.req.RequestMessage;
import com.comtom.bc.server.req.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author wjd
 * @create 2018/7/30 0030
 * @desc ${DESCRIPTION}
 **/
@Controller
public class WsController {

    private final SimpMessagingTemplate messagingTemplate;


    @Autowired
    public WsController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/welcome")
    @SendTo("/topic/say")
    public ResponseMessage say(RequestMessage message) {
        System.out.println(message.getName());
        return new ResponseMessage("welcome," + message.getName() + " !");
    }

    /**
     * 定时推送消息
     */
//    @Scheduled(fixedRate = 5000)
//    public void callback() {
//        // 发现消息
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//      //  messagingTemplate.convertAndSend("/topic/callback", "定时推送消息时间: " + df.format(new Date()));
//        String msg = "您好，您有一条消息标题为 %s 的应急消息，请注意查收及处理 ";
//        messagingTemplate.convertAndSend("/topic/callback", String.format(msg,"测试"));
//    }
}
