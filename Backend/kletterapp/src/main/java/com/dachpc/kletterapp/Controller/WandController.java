package com.dachpc.kletterapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dachpc.kletterapp.Entities.Wand;
import com.dachpc.kletterapp.Repositories.WandRepository;



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
}
