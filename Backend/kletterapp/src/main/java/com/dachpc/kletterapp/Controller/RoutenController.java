package com.dachpc.kletterapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dachpc.kletterapp.Entities.Route;
import com.dachpc.kletterapp.Repositories.RoutenRepository;


@CrossOrigin(origins = "http://localhost:5173") 
@RestController
@RequestMapping("/api/routen")
public class RoutenController {

    @Autowired
    private RoutenRepository routenRepository;

    @GetMapping
    public List<Route> filter(@RequestParam int hallenID, @RequestParam(required = false) Float minGrade, @RequestParam(required = false) Float maxGrade, @RequestParam(required = false) Boolean isToprope, @RequestParam(required = false) Boolean isVorstieg, @RequestParam(required = false) Boolean isActive) {
        System.out.println("Filter-Endpoint aufgerufen");
        return routenRepository.filter(hallenID, minGrade, maxGrade, isToprope, isVorstieg, isActive);
    }

    @PostMapping
    public ResponseEntity<Route> add(@RequestBody Route route) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(routenRepository.save(route));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PatchMapping
    public ResponseEntity<Route> update(@RequestBody Route route) {
        if (!routenRepository.existsById(route.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(routenRepository.save(route));
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam Integer id) {
        if (!routenRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        routenRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    // @GetMapping("/grade/{grade}")
    // public List<Route> filterBySchwierigkeit(@RequestParam String grade) {
    //     return routenRepository.findBySchwierigkeit(grade);
    // }
    
    // @GetMapping("/halle/{halle}")
    // public List<Route> filterByHalle(@RequestParam String halle) {
    //     return routenRepository.findByHalle(halle);
    // }

    // @GetMapping("/sicherungsart/{sicherungsart}")
    // public List<Route> filterBySicherungsart(@RequestParam String sicherungsart) {
    //     return routenRepository.findBySicherungsart(sicherungsart);
    // }
}
