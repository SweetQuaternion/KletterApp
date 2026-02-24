package com.dachpc.kletterapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dachpc.kletterapp.Entities.Halle;
import com.dachpc.kletterapp.Entities.Route;
import com.dachpc.kletterapp.Repositories.HallenRepository;
import com.dachpc.kletterapp.Repositories.RoutenRepository;

@RestController
public class JSONController {

    @Autowired
    private RoutenRepository routenRepository;

    @GetMapping("/route")
    public List<Route> route(@RequestParam String grade) {
        return routenRepository.findBySchwierigkeit(grade);
    }

    @Autowired
    private HallenRepository hallenRepository;
    
    @GetMapping("/halle")
    public List<Halle> halle(@RequestParam String name) {
        return hallenRepository.findByName(name);
    }
    
}

// @RestController
// Das hier gibt JSON zurück
// bzw einfach blank Text (aber kein HTML oder so, sondern nur den Text, den wir zurückgeben wollen)