package com.esolution.demo.graphql.configuration;

import org.springframework.lang.NonNull;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

public class GraphQLWebSocketHandler extends AbstractWebSocketHandler {

    @Override
    public void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) throws Exception {
        // Implémentez ici la logique de traitement des messages, par exemple,
        // la gestion des abonnements ou l'exécution des requêtes GraphQL
    }

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
        // Logique d'initialisation, par exemple, inscrire un utilisateur ou vérifier la session
    }
}