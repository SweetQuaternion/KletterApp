package com.dachpc.kletterapp;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dachpc.kletterapp.Entities.Route;
import com.dachpc.kletterapp.Repositories.RoutenRepository;

@RestController
public class JSONController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), template.formatted(name));
    }

    @GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "Fluff") String name) {
		return String.format("Hello %s", name);
	}

    @Autowired
    private RoutenRepository routenRepository;

    @GetMapping("/route")
    public List<Route> route(@RequestParam String grade) {
        return routenRepository.findBySchwierigkeit(grade);
    }
    
}

// @RestController
// Das hier gibt JSON zurück
// bzw einfach blank Text (aber kein HTML oder so, sondern nur den Text, den wir zurückgeben wollen)