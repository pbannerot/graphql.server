package com.esolution.graphql.server;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import com.esolution.graphql.server.controller.UserControllerTest;
import com.esolution.graphql.server.graphql.controller.UserDataFetcher;
import com.esolution.graphql.server.graphql.controller.UserMutation;

@Suite
@SelectClasses({ // GraphqlServerApplicationTests.class, 
	UserControllerTest.class,
	UserDataFetcher.class, 
	UserMutation.class })
public class AllTests {

}
