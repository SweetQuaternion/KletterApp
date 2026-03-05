package com.dachpc.kletterapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dachpc.kletterapp.Entities.Route;
import com.dachpc.kletterapp.Repositories.RoutenRepository;


@CrossOrigin(origins = "http://localhost:5173") 
@RestController
@RequestMapping("/routen")
public class RoutenController {

    @Autowired
    private RoutenRepository routenRepository;

    @GetMapping
    public List<Route> filter(@RequestParam int hallenID, @RequestParam(required = false) float minGrade, @RequestParam(required = false) float maxGrade, @RequestParam(required = false) Boolean isToprope, @RequestParam(required = false) Boolean isVorstieg, @RequestParam(required = false) Boolean isActive) {
        return routenRepository.filter(hallenID, minGrade, maxGrade, isToprope, isVorstieg, isActive);
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
