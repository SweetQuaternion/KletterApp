package com.dachpc.kletterapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dachpc.kletterapp.Entities.Kommentar;
import com.dachpc.kletterapp.Repositories.KommentarRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/kommentare")
public class KommentarController {

    @Autowired
    private KommentarRepository kommentarRepository;

    
    // per Konvention hier eher requestparams nutzen, statt eine Baumstruktur
    // beide GetMappings müssen daher in eine Methode, nicht in zwei
    @GetMapping(produces = "application/json")
    public List<Kommentar> getKommentareByRouteID(@RequestParam(required = false) Integer routeId, @RequestParam(required = false) Integer userId) {
        if (routeId != null) {
            return kommentarRepository.findByRoute_id(routeId);
        } else if (userId != null) {
            return kommentarRepository.findByUser_keycloakId(userId);
        } else {
            return kommentarRepository.findAll();
        }
    }

    @PostMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Kommentar addKommentar(@RequestBody Kommentar kommentar) {
        kommentarRepository.save(kommentar);
        return kommentar;
    }

    @PatchMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Kommentar updateKommentar(@RequestParam int id, @RequestParam String newText) {
        Kommentar prevKommentar = kommentarRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        prevKommentar.setText(newText);
        kommentarRepository.save(prevKommentar);
        return prevKommentar;
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteKommentar(@RequestParam int id) {
        Kommentar kommentar = kommentarRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        kommentarRepository.delete(kommentar);;
    }

}
