package com.dachpc.kletterapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dachpc.kletterapp.Entities.Halle;
import com.dachpc.kletterapp.Repositories.HallenRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/hallen")
public class HallenController {

    @Autowired
    private HallenRepository hallenRepository;
    
    @GetMapping
    public List<Halle> findHalle(@RequestParam(required = false) String name) {
        if (name == null || name.isEmpty()) {
            return hallenRepository.findAll();
        }
        return hallenRepository.search(name);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Halle addHalle(@RequestBody Halle halle) {
        hallenRepository.save(halle);
        return halle;
    }
    
    @PatchMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Halle updateHalle(@RequestBody Halle halle) {
        hallenRepository.save(halle);
        return halle;
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHalle(@RequestParam Integer id) {
        Halle halle = hallenRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        hallenRepository.delete(halle);
    }
}