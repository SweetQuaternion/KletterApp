package com.dachpc.kletterapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dachpc.kletterapp.Entities.Kommentar;
import com.dachpc.kletterapp.Repositories.KommentarRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@CrossOrigin(origins = "http://localhost:5173") 
@RestController
@RequestMapping("/kommentare")
public class KommentarController {

    @Autowired
    private KommentarRepository kommentarRepository;

    @GetMapping("/route/{routeId}")
    public List<Kommentar> filter(@PathVariable int routeId) {
        return kommentarRepository.findByRoutenId(routeId);
    }

    @GetMapping("/user/{userId}")
    public List<Kommentar> getKommentareByUserId(@PathVariable int userId) {
        return kommentarRepository.findByUserId(userId);
    }

    @PostMapping
    public ResponseEntity<String> newKommentar(@RequestBody Kommentar kommentar) {
        try {
            kommentarRepository.save(kommentar);
            return ResponseEntity.status(HttpStatus.CREATED).body("Kommentar created successfully.");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong.");
        }
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<String> changeText(@PathVariable int id, @RequestBody String newText) {
        Kommentar prevKommentar = kommentarRepository.findById(id).orElse(null);
        if (prevKommentar == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kommentar not found.");
        }
        prevKommentar.setText(newText);
        kommentarRepository.save(prevKommentar);
        return ResponseEntity.ok("Kommentar updated successfully.");
    }
}
