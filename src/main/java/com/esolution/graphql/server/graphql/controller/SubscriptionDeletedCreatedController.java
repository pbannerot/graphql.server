package com.esolution.graphql.server.graphql.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;

import com.esolution.graphql.server.service.UserService;

import reactor.core.publisher.Flux;

@Controller
public class SubscriptionDeletedCreatedController {
	private static final Logger logger = LoggerFactory.getLogger(SubscriptionDeletedCreatedController.class);
	
	@Autowired
	private UserService userService;
	
	@SubscriptionMapping
	public Flux<UUID> userDeleted() {
		logger.debug("Subscription received...");
		return userService.getUserDeletedSink().asFlux()
				.doOnSubscribe(subscription -> logger.debug("Subscription started"))
				.doOnNext(message -> logger.debug("Recieved message: " + message))
	            .doOnTerminate(() -> logger.debug("Subscription terminated"));
	}
	
}
