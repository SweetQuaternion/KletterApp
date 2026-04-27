package com.dachpc.kletterapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dachpc.kletterapp.Dtos.AscentCreateDTO;
import com.dachpc.kletterapp.Dtos.AscentResponseDTO;
import com.dachpc.kletterapp.Services.AscentService;


@RestController
@RequestMapping("/api/ascents")
public class AscentController {

    @Autowired
    public AscentService ascentService;

    @GetMapping(produces = "application/json")
    public List<AscentResponseDTO> findAscents(@RequestParam String userId, @RequestParam(required = false) Integer routenId) {
        return ascentService.findAscents(userId, routenId);
    }

    @GetMapping(path = "/all", produces = "application/json")
    public List<AscentResponseDTO> findAllAscents(@RequestParam String userId, @RequestParam List<Integer> routenIdList) {
        return ascentService.findAllAscents(userId, routenIdList);
    }

    @PostMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN') or #ascent.userId == authentication.principal.subject")
    public AscentResponseDTO addAscent(@RequestBody AscentCreateDTO dto) {
        return ascentService.addAscent(dto);
    }

    @PatchMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN') or #ascent.userId == authentication.principal.subject")
    public AscentResponseDTO updateAscent(@RequestParam int id, @RequestBody AscentCreateDTO dto) {
        return ascentService.updateAscent(id, dto);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or #ascent.userId == authentication.principal.subject")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAscent(@RequestParam Integer id) {
        ascentService.deleteAscent(id);
    }
    
}