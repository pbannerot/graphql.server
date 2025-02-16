package com.esolution.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.esolution.demo.service.UserService;

@Configuration
public class GraphQLConfiguration {
	@Autowired
    private UserService userService; 
	
//	@Bean
//    public GraphQL graphQL() {
//        return GraphQL.newGraphQL(schema)
//            .subscription(userCreatedSubscription())
//            .subscription(userDeletedSubscription())
//            .build();
//    }
//
//    @Bean
//    public DataFetcher<?> userCreatedSubscription() {
//        return environment -> {
//            // Gestion de l'événement pour la création de l'utilisateur
//            return userCreatedEventPublisher;
//        };
//    }
//
//    @Bean
//    public DataFetcher<?> userDeletedSubscription() {
//        return environment -> {
//            // Gestion de l'événement pour la suppression de l'utilisateur
//            return userDeletedEventPublisher;
//        };
//    }
	
//	S

}
