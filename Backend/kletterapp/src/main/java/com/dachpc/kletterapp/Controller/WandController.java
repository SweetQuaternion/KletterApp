package com.dachpc.kletterapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dachpc.kletterapp.Entities.Wand;
import com.dachpc.kletterapp.Entities.WandId;
import com.dachpc.kletterapp.Repositories.WandRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/hallen/{hallenId}/waende")
public class WandController {

    @Autowired
    private WandRepository wandRepository;

    @GetMapping(produces = "application/json")
    public List<Wand> getWaendeByHallenId(@PathVariable int hallenId) {
        // return wandRepository.findByIdHallenId(hallenId);
        return wandRepository.findByHallenIdWithRouten(hallenId);
    }

    @PostMapping(produces = "application/json")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Wand addWand(@PathVariable int hallenId, @RequestBody Wand wand) {
        wandRepository.save(wand);
        return wand;
    }

    @PatchMapping(produces = "application/json")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Wand updateWand(@PathVariable int hallenId, @RequestBody Wand wand) {
        wandRepository.save(wand);
        return wand;
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWand(@PathVariable int hallenId, @RequestParam WandId id) {
        Wand wand = wandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        wandRepository.delete(wand);
    }
    
}
