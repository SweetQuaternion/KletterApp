package com.dachpc.kletterapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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




@CrossOrigin(origins = "http://localhost:5173") 
@RestController
@RequestMapping("/api/waende")
public class WandController {

    @Autowired
    private WandRepository wandRepository;

    @GetMapping
    public List<Wand> getByHallenId(@RequestParam int hallenId) {
        return wandRepository.findByIdHallenId(hallenId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody Wand wand) {
        wandRepository.save(wand);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody Wand wand) {
        wandRepository.save(wand);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestParam WandId id) {
        Wand wand = wandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        wandRepository.delete(wand);
    }
    
}
