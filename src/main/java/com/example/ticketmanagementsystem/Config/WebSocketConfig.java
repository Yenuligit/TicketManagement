package com.example.ticketmanagementsystem.Config;

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
        // Enables a simple message broker for real-time messages
        config.enableSimpleBroker("/topic"); // Client subscribes to /topic/*
        config.setApplicationDestinationPrefixes("/app"); // Client sends messages to /app/*
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Register the WebSocket endpoint for clients
        registry.addEndpoint("/websocket")
                .setAllowedOrigins("http://localhost:3000") // Allow React on localhost:3000
                .withSockJS(); // Enable fallback for browsers that don't support WebSockets
    }
}
