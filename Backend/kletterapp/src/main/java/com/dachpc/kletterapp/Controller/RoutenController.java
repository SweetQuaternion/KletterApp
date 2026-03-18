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

import com.dachpc.kletterapp.Entities.Route;
import com.dachpc.kletterapp.Repositories.RoutenRepository;

import jakarta.persistence.EntityNotFoundException;


@CrossOrigin(origins = "http://localhost:5173") 
@RestController
@RequestMapping("/api/routen")
public class RoutenController {

    @Autowired
    private RoutenRepository routenRepository;

    // @GetMapping
    // public List<Route> filter(@RequestParam int hallenID, @RequestParam(required = false) Float minGrade, @RequestParam(required = false) Float maxGrade, @RequestParam(required = false) Boolean isToprope, @RequestParam(required = false) Boolean isVorstieg, @RequestParam(required = false) Boolean isActive) {
    //     System.out.println("Filter-Endpoint aufgerufen");
    //     return routenRepository.filter(hallenID, minGrade, maxGrade, isToprope, isVorstieg, isActive);
    // }

    @GetMapping
    public List<Route> getByHallenId(@RequestParam int hallenId) {
        return routenRepository.findByHallenId(hallenId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody Route route) {
        routenRepository.save(route);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody Route route) {
        routenRepository.save(route);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestParam Integer id) {
        Route route = routenRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        routenRepository.delete(route);
    }

}
