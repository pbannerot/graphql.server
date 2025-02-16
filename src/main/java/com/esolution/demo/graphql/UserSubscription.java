package com.esolution.demo.graphql;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;

import com.esolution.demo.model.dto.LocationDTOInput;
import com.esolution.demo.model.dto.UserDTO;
import com.esolution.demo.service.UserService;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Controller
public class UserSubscription {
	private final UserService userService;
    private final Sinks.Many<UserDTO> userCreatedSink;
    private final Sinks.Many<String> userDeletedSink;
    
    public UserSubscription(UserService userService) {
        this.userService = userService;
        this.userCreatedSink = Sinks.many().replay().all();
        this.userDeletedSink = Sinks.many().replay().all();
    }
    
    @SubscriptionMapping
    public Mono<UserDTO> userCreated() {
        return userCreatedSink.asFlux().next();
    }
    
    @SubscriptionMapping
    public Mono<String> userDeleted() {
        return userDeletedSink.asFlux().next();
    }
    
//    @MutationMapping
//    public UserDTO createUser(@Argument String firstName, @Argument String lastName, @Argument LocationDTOInput location) {
//        UserDTO user = userService.createUser(firstName, lastName, location);
//        
//        userCreatedSink.tryEmitNext(user);
//
//        return user;
//    }
//    
//    @MutationMapping
//    public String deleteUser(@Argument String id) {
//        String deletedUserId = userService.deleteUser(id);
//        
//        userDeletedSink.tryEmitNext(deletedUserId);
//
//        return deletedUserId;
//    }

}
