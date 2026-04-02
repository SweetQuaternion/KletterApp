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

import com.dachpc.kletterapp.Dtos.KommentarCreateDTO;
import com.dachpc.kletterapp.Dtos.KommentarResponseDTO;
import com.dachpc.kletterapp.Services.KommentarService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/kommentare")
public class KommentarController {

    @Autowired
    private KommentarService kommentarService;

    
    @GetMapping(produces = "application/json")
    public List<KommentarResponseDTO> getKommentareByRouteID(@RequestParam Integer routeId) {
        return kommentarService.findKommentarByRouteId(routeId);
    }

    @PostMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public KommentarResponseDTO addKommentar(@RequestBody KommentarCreateDTO dto) {
        KommentarResponseDTO kommentar = kommentarService.addKommentar(dto);
        return kommentar;
    }

    @PatchMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public KommentarResponseDTO updateKommentar(@RequestParam int id, @RequestParam String newText) {
        return kommentarService.updateKommentar(id, newText);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteKommentar(@RequestParam int id) {
        kommentarService.deleteKommentar(id);
    }

}
