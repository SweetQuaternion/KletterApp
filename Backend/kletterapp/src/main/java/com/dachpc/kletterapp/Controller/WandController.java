package com.dachpc.kletterapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dachpc.kletterapp.Dtos.WandCreateDTO;
import com.dachpc.kletterapp.Dtos.WandResponseDTO;
import com.dachpc.kletterapp.Services.WandService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/hallen/{hallenId}/waende")
public class WandController {

    @Autowired
    private WandService wandService; 

    @GetMapping(produces = "application/json")
    public List<WandResponseDTO> getWaendeByHallenId(@PathVariable int hallenId) {
        return wandService.getWändeByHallenId(hallenId);
    }

    @PostMapping(produces = "application/json")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public void addWände(@PathVariable int hallenId, @RequestBody WandCreateDTO[] dto) {
        for (WandCreateDTO wand : dto) {
            wand.setHallenId(hallenId);
            wandService.addWand(wand);
        }
    }

    @PatchMapping(produces = "application/json")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<WandResponseDTO> updateWände(@PathVariable int hallenId, @RequestBody List<WandResponseDTO> dtoList) {
        return wandService.updateWände(hallenId, dtoList);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWand(@PathVariable int hallenId, @RequestParam int id) {
        wandService.deleteWand(id);
    }
    
}
