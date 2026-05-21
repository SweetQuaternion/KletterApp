package com.dachpc.kletterapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dachpc.kletterapp.Services.FlachwitzData;
import com.dachpc.kletterapp.Services.FlachwitzService;


@RestController
@RequestMapping("/api/flachwitz")
public class FlachwitzController {

    @Autowired
    private FlachwitzService flachwitzService;
    
    @GetMapping(produces = "application/json")
    public FlachwitzData getFlachwitzOfTheDay() {
        return flachwitzService.getWitzOfTheDay();
    }
    
}