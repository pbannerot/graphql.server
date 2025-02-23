package com.esolution.demo.graphql.controller.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

public class GraphQLWebSocketHandler extends AbstractWebSocketHandler {
	private static final Logger logger = LoggerFactory.getLogger(GraphQLWebSocketHandler.class);

    @Override
    public void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) throws Exception {
        logger.debug("GraphQLWebSocketHandler::handleTextMessage: " + message);
    }

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
    	logger.debug("GraphQLWebSocketHandler::afterConnectionEstablished");
    }
}