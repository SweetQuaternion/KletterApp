package com.dachpc.kletterapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dachpc.kletterapp.Entities.Wand;
import com.dachpc.kletterapp.Repositories.WandRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@CrossOrigin(origins = "http://localhost:5173") 
@RestController
@RequestMapping("/api/waende")
public class WandController {

    @Autowired
    private WandRepository wandRepository;

    // oder hallen/wände/routen
    @GetMapping
    public List<Wand> getByHallenId(@RequestParam int hallenId) {
        return wandRepository.findByHallenId(hallenId);
    }

    @PostMapping
    public ResponseEntity<Wand> add(@RequestBody Wand wand) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(wandRepository.save(wand));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PatchMapping
    public ResponseEntity<Wand> update(@RequestBody Wand wand) {
        if (!wandRepository.existsById(wand.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(wandRepository.save(wand));
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam Integer id) {
        if (!wandRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        wandRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
    
}
