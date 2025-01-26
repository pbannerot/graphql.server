package com.esolution.demo.datafetcher;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.esolution.demo.model.dto.UserDTO;
import com.esolution.demo.service.UserService;

import graphql.schema.DataFetchingEnvironment;

@Controller
public class UsersDataFetcher {
	@Autowired
	private UserService userService;
	
//	{
//	  users {
//	    id
//	    firstName
//	    lastName
//	    location {
//	      city
//	      country
//	    }
//	  }
//	}

	@QueryMapping
    public List<UserDTO> users(DataFetchingEnvironment environment) {
        return userService.users();
    }
	
	@QueryMapping
    public UserDTO userById(@Argument UUID id) {
        return userService.getUser(id).orElseThrow();
    }
	
//	{
//	  usersByCountry(country: FR) {
//	    id
//	    firstName
//	    lastName
//	    location {
//	      city
//	      country
//	    }
//	  }

}
