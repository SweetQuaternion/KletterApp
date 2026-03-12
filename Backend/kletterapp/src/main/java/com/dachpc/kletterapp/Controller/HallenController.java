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

import com.dachpc.kletterapp.Entities.Halle;
import com.dachpc.kletterapp.Repositories.HallenRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/hallen")
public class HallenController {

    @Autowired
    private HallenRepository hallenRepository;
    
    @GetMapping
    public List<Halle> find(@RequestParam(required = false) String name) {
        if (name == null || name.isEmpty()) {
            return hallenRepository.findAll();
        }
        return hallenRepository.search(name);
    }

    @PostMapping
    public ResponseEntity<String> add(@RequestBody Halle halle) {
        hallenRepository.save(halle);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
    
    @PatchMapping
    public ResponseEntity<String> update(@RequestBody Halle halle) {
        if (!hallenRepository.existsById(halle.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        hallenRepository.save(halle);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam Integer id) {
        if (!hallenRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        hallenRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}