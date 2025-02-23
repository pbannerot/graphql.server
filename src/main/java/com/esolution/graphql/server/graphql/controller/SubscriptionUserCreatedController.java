package com.esolution.graphql.server.graphql.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;

import com.esolution.graphql.server.model.dto.UserDTO;
import com.esolution.graphql.server.service.UserService;

import reactor.core.publisher.Flux;

@Controller
public class SubscriptionUserCreatedController {
	private static final Logger logger = LoggerFactory.getLogger(SubscriptionUserCreatedController.class);
	
	@Autowired
	private UserService userService;
	
	@SubscriptionMapping
	public Flux<UserDTO> userCreated() {
		logger.debug("Subscription received...");
		return userService.getUserCreatedSink().asFlux()
				.doOnSubscribe(subscription -> logger.debug("Subscription started"))
				.doOnNext(message -> logger.debug("Recieved message: " + message))
	            .doOnTerminate(() -> logger.debug("Subscription terminated"));
	}

}
