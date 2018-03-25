package io.vscale.uniservice.websockets.handlers;

import io.vscale.uniservice.services.interfaces.events.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 18.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@Component
public class NewEventHandler extends TextWebSocketHandler {

    private Map<String, WebSocketSession> sessions;
    private final EventService eventService;

    @Autowired
    public NewEventHandler(EventService eventService) {
        this.sessions = new HashMap<>();
        this.eventService = eventService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session){
        this.sessions.put(session.getId(), session);
        StringBuilder sb = new StringBuilder();

        sb.append("Подключен клиент ").append(session.getRemoteAddress().getPort())
          .append(", id сессии ").append(session.getId());

        System.out.println(sb.toString());
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage textMessage){

        if(this.eventService.getNewEvents()){

            this.sessions.forEach((key, value) -> {
                try {
                    value.sendMessage(new TextMessage("Добавлены новые мероприятия! Спешите их увидеть!"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        }

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status){
        System.out.println("Клиент " + session.getId() + " был отключен");
    }

}
