package com.dachpc.kletterapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    public List<Ascent> findAscentsByUserId(@RequestParam(required = false) Integer userId, @RequestParam(required = false) Integer routenId) {
        return ascentRepository.search(userId, routenId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Ascent addAscent(@RequestBody Ascent ascent) {
        ascentRepository.save(ascent);
        return ascent;
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public Ascent updateAscent(@RequestBody Ascent ascent) {
        ascentRepository.save(ascent);
        return ascent;
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAscent(@RequestParam Integer id) {
        ascentRepository.deleteById(id);
    }
    
}