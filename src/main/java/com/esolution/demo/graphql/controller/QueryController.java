package com.esolution.demo.graphql.controller;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

//query q {
//	  hello
//	}

@Controller
public class QueryController {

    @QueryMapping
    public String hello() {
        return "Hello, world!";
    }
}
