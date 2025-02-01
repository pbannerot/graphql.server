package com.esolution.demo;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import com.esolution.demo.controller.UserControllerTest;
import com.esolution.demo.datafetcher.UserDataFetcher;
import com.esolution.demo.datafetcher.UserMutation;

@Suite
@SelectClasses({ // DemoApplicationTests.class, 
	UserControllerTest.class,
	UserDataFetcher.class, 
	UserMutation.class })
public class AllTests {

}
