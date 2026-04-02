package com.dachpc.kletterapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dachpc.kletterapp.Dtos.RouteCreateDTO;
import com.dachpc.kletterapp.Dtos.RouteResponseDTO;
import com.dachpc.kletterapp.Services.RoutenService;

 
@RestController
@RequestMapping("/api/hallen/{hallenId}/routen")
public class RoutenController {

    @Autowired
    private RoutenService routenService;

    @GetMapping(produces = "application/json")
    public List<RouteResponseDTO> getRoutenByHallenId(@PathVariable int hallenId) {
        return routenService.getRoutenByHallenId(hallenId);
    }    

    @PostMapping(produces = "application/json")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public RouteResponseDTO addRoute(@PathVariable int hallenId, @RequestBody RouteCreateDTO dto) {
        return routenService.addRoute(dto);
    }

    @PatchMapping(produces = "application/json")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public RouteResponseDTO updateRoute(@PathVariable int hallenId, @RequestBody RouteCreateDTO dto, @RequestParam int id) {
        return routenService.updateRoute(id, dto);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoute(@PathVariable int hallenId, @RequestParam Integer id) {
        routenService.deleteRoute(id);
    }

}
