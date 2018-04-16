package io.vscale.uniservice.stomp.controllers;

import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.TextMessage;

/**
 * 09.04.2018
 *
 * @author Dias Arkharov
 * @version 1.0
 */
@Controller
public class MessageController {

    @MessageMapping("/event")
    @SendTo("/topic/chat")
    public TextMessage byeMessage(Message<?> message) {
        return new TextMessage("Новое мероприяте!");
    }


    @SubscribeMapping("/foo")
    public void handleWithError() {
        throw new IllegalArgumentException("Bad input");
    }

    @MessageExceptionHandler
    @SendToUser("/queue/error")
    public String handleException(IllegalArgumentException ex) {
        return "Got error: " + ex.getMessage();
    }
}
