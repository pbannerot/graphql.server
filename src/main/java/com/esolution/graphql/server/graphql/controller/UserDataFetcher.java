package com.esolution.graphql.server.graphql.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.esolution.graphql.server.model.Country;
import com.esolution.graphql.server.model.dto.UserDTO;
import com.esolution.graphql.server.service.UserService;

import graphql.schema.DataFetchingEnvironment;

// http://localhost:9090/graphiql?path=/graphql&wsPath=/graphql
	
@Controller
public class UserDataFetcher {
	@Autowired
	private UserService userService;
	
	@QueryMapping
    public List<UserDTO> users(DataFetchingEnvironment environment) {
        return userService.users();
    }
	
	@QueryMapping
    public UserDTO userById(@Argument UUID id) {
        return userService.getUser(id).orElseThrow(() -> new NoSuchElementException(String.format("User not found with id: %s", id)));
    }

	@QueryMapping
	public List<UserDTO> usersByCountry(@Argument Country country) {
		return userService.getUsersByCountry(country);
	}
}
