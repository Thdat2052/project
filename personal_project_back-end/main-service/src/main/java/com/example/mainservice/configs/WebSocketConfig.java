package com.example.mainservice.configs;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // Định nghĩa broker để publish messages tới các client
        config.setApplicationDestinationPrefixes("/app"); // Các tin nhắn từ client sẽ có prefix "/app"
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // Định nghĩa WebSocket endpoint
                .setAllowedOriginPatterns("*")
                .withSockJS(); // Kích hoạt SockJS fallback
    }
}