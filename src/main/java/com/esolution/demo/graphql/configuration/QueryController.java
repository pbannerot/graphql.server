package com.esolution.demo.graphql.configuration;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class QueryController {

    @QueryMapping
    public String hello() {
        return "Hello, world!";
    }
}
