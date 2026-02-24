package com.dachpc.kletterapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class KletterappApplication {

	public static void main(String[] args) {
		SpringApplication.run(KletterappApplication.class, args);
	}

	@GetMapping("/")
    public String index() {
        return "index.html";
    }

}


// @RestController
// tells Spring that this code describes an endpoint that can be accessed via HTTP requests.
//
// @GetMapping("/hello")
// tells Spring that this method should be called when a GET request is made to the /hello endpoint.
//
// @RequestParam
// tells Spring that the name parameter should be taken from the query string of the request.
//
// @SpringBootApplication
// tells Spring that this is the main class of the application and that it should be run when the application starts.
// convenience annotation that adds @Configuration, @EnableAutoConfiguration, and @ComponentScan with their default attributes.
//
// @Configuration
// There are bean definitions in this class, and Spring should process the class to generate beans to be used in the application context.
//
// @EnableAutoConfiguration
// Tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings.
//
// @ComponentScan
// tells Spring to look for other components, configurations, and services in the com/dachpc/kletterapp package