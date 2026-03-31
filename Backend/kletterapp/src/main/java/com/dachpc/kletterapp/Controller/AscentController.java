package com.dachpc.kletterapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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


@RestController
@RequestMapping("/api/ascents")
public class AscentController {
    
    @Autowired
    private AscentRepository ascentRepository;

    @GetMapping(produces = "application/json")
    public List<Ascent> findAscentsByUserId(@RequestParam String userId, @RequestParam(required = false) Integer routenId) {
        return ascentRepository.search(userId, routenId);
    }

    @PostMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN') or #ascent.userId == authentication.principal.subject")
    public Ascent addAscent(@RequestBody Ascent ascent) {
        ascentRepository.save(ascent);
        return ascent;
    }

    @PatchMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN') or #ascent.userId == authentication.principal.subject")
    public Ascent updateAscent(@RequestBody Ascent ascent) {
        ascentRepository.save(ascent);
        return ascent;
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or #ascent.userId == authentication.principal.subject")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAscent(@RequestParam Integer id) {
        ascentRepository.deleteById(id);
    }
    
}