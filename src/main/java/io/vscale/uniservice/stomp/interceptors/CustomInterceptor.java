package io.vscale.uniservice.stomp.interceptors;

import io.vscale.uniservice.services.interfaces.events.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

@Component
public class CustomInterceptor extends ChannelInterceptorAdapter {

    @Autowired
    private EventService eventService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (eventService.getNewEvents()){
            System.out.println("Добавлены новые мероприятия! Спешите их увидеть!");
        }else if(accessor.getCommand().equals(StompCommand.ERROR)){
            System.out.println("Мероприятий нет");
        }
        return message;
    }
}
