package io.vscale.uniservice.websockets.config;

import io.vscale.uniservice.services.interfaces.events.EventService;
import io.vscale.uniservice.websockets.handlers.NewEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * 18.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{

    private final EventService eventService;

    @Autowired
    public WebSocketConfig(EventService eventService) {
        this.eventService = eventService;
    }

    @Bean
    public WebSocketHandler newEventHandler(){
        return new NewEventHandler(this.eventService);
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(newEventHandler(), "/new_events").withSockJS();
    }
}
