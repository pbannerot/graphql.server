package com.esolution.demo.graphql.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;

import com.esolution.demo.service.UserService;

import reactor.core.publisher.Flux;

@Controller
public class SubscriptionController {
	@Autowired
	private UserService userService;
	
	@SubscriptionMapping
	public Flux<String> newMessage() {
		return userService.getMessageSink().asFlux();
	}
}
