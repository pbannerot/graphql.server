package com.esolution.demo.graphql.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;

import com.esolution.demo.service.UserService;

import reactor.core.publisher.Flux;

//subscription s {
//newMessage
//}

@Controller
public class SubscriptionNewMessageController {
	private static final Logger logger = LoggerFactory.getLogger(SubscriptionNewMessageController.class);
	
	@Autowired
	private UserService userService;
	
	@SubscriptionMapping
	public Flux<String> newMessage() {
		logger.debug("Subscription received...");
		return userService.getMessageSink().asFlux()
				.doOnSubscribe(subscription -> logger.debug("Subscription started"))
				.doOnNext(message -> logger.debug("Recieved message: " + message))
	            .doOnTerminate(() -> logger.debug("Subscription terminated"));
	}
}
