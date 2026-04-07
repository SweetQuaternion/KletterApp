package com.dachpc.kletterapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dachpc.kletterapp.Dtos.HalleCreateDTO;
import com.dachpc.kletterapp.Dtos.HalleResponseDTO;
import com.dachpc.kletterapp.Services.HalleService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/hallen")
public class HallenController {

    @Autowired
    private HalleService halleService;
    
    @GetMapping(produces = "application/json")
    public List<HalleResponseDTO> findHalle(@RequestParam(required = false) String name) {
        if (name == null || name.isEmpty()) {
            return halleService.findAll();
        }
        return halleService.findHalle(name);
    }

    @PostMapping(produces = "application/json")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public HalleResponseDTO addHalle(@RequestBody HalleCreateDTO dto) {
        return halleService.addHalle(dto);
    }
    
    @PatchMapping(produces = "application/json")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public HalleResponseDTO updateHalle(@RequestParam int id, @RequestBody HalleCreateDTO dto) {
        return halleService.updateHalle(id, dto);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHalle(@RequestParam Integer id) {
        halleService.deleteHalle(id);
    }
}