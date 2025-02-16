package com.esolution.demo.graphql.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(@NonNull WebSocketHandlerRegistry registry) {
        registry.addHandler(graphqlWebSocketHandler(), "/graphql-ws")
                .addInterceptors(new HttpSessionHandshakeInterceptor()) // Optionnel pour la gestion des sessions
                .setAllowedOrigins("*");  // Définir les origines autorisées
    }

    public WebSocketHandler graphqlWebSocketHandler() {
        return new GraphQLWebSocketHandler();
    }
}
