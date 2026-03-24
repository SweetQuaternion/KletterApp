package com.dachpc.kletterapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dachpc.kletterapp.Entities.Route;
import com.dachpc.kletterapp.Repositories.RoutenRepository;

import jakarta.persistence.EntityNotFoundException;

 
@RestController
@RequestMapping("/api/hallen/{hallenId}/routen")
public class RoutenController {

    @Autowired
    private RoutenRepository routenRepository;

    @GetMapping
    public List<Route> getRoutenByHallenId(@PathVariable int hallenId) {
        return routenRepository.findByWand_Id_HallenId(hallenId);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Route addRoute(@PathVariable int hallenId, @RequestBody Route route) {
        routenRepository.save(route);
        return route;
    }

    @PatchMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Route updateRoute(@PathVariable int hallenId, @RequestBody Route route) {
        routenRepository.save(route);
        return route;
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoute(@PathVariable int hallenId, @RequestParam Integer id) {
        Route route = routenRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        routenRepository.delete(route);
    }

}
