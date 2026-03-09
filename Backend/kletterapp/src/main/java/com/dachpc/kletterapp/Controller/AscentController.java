package com.dachpc.kletterapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dachpc.kletterapp.Entities.Ascent;
import com.dachpc.kletterapp.Repositories.AscentRepository;



@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/ascents")
public class AscentController {
    
    @Autowired
    private AscentRepository ascentRepository;

    @GetMapping
    public List<Ascent> findByUserId(@RequestParam(required = false) Integer userId, @RequestParam(required = false) Integer routenId) {
        return ascentRepository.search(userId, routenId);
    }

    @PostMapping
    public ResponseEntity<String> add(@RequestBody Ascent ascent) {
        try {
            ascentRepository.save(ascent);
            return ResponseEntity.status(HttpStatus.CREATED).body("Ascent added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding ascent: " + e.getMessage());
        }
    }
}
