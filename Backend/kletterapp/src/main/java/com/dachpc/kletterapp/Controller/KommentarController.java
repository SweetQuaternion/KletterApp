package com.dachpc.kletterapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dachpc.kletterapp.Entities.Kommentar;
import com.dachpc.kletterapp.Repositories.KommentarRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "http://localhost:5173") 
@RestController
@RequestMapping("/api/kommentare")
public class KommentarController {

    @Autowired
    private KommentarRepository kommentarRepository;

    
    // per Konvention hier eher requestparams nutzen, statt eine Baumstruktur
    // beide GetMappings müssen daher in eine Methode, nicht in zwei
    @GetMapping
    public List<Kommentar> getByRouteID(@RequestParam(required = false) Integer routeId, @RequestParam(required = false) Integer userId) {
        if (routeId != null) {
            return kommentarRepository.findByRoutenId(routeId);
        } else if (userId != null) {
            return kommentarRepository.findByUserId(userId);
        } else {
            return kommentarRepository.findAll();
        }
    }

    @PostMapping
    public ResponseEntity<Kommentar> newKommentar(@RequestBody Kommentar kommentar) {
        try {
            kommentarRepository.save(kommentar);
            return ResponseEntity.status(HttpStatus.CREATED).body(kommentar);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PatchMapping
    public ResponseEntity<String> update(@RequestParam int id, @RequestParam String newText) {
        Kommentar prevKommentar = kommentarRepository.findById(id).orElse(null);
        if (prevKommentar == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        prevKommentar.setText(newText);
        kommentarRepository.save(prevKommentar);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam int id) {
        if (!kommentarRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        kommentarRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
