package com.dachpc.kletterapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dachpc.kletterapp.Repositories.AscentRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/ascents")
public class AscentController {
    
    @Autowired
    private AscentRepository ascentRepository;

    @GetMapping
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    
}
