package com.dachpc.kletterapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dachpc.kletterapp.Entities.UserRoutenStatus;
import com.dachpc.kletterapp.Repositories.UserRoutenStatusRepository;



@CrossOrigin(origins = "http://localhost:5173") 
@RestController
@RequestMapping("/userroutenstatus")
public class UserRoutenStatusController {

    @Autowired
    private UserRoutenStatusRepository userRoutenStatusRepository;

    @GetMapping
    public UserRoutenStatus getStatus(@RequestParam int userId, @RequestParam int routenId) {
        return userRoutenStatusRepository.findByIdUserIdAndIdRouteId(userId, routenId);
    }

    // Wie würde das in einer Ordnerstruktur aussehen?
    @GetMapping("/all")
    public List<UserRoutenStatus> getAll(@PathVariable(required = false) Integer userId, @PathVariable(required = false) Integer routenId) {
        if (userId != null) {
            return userRoutenStatusRepository.findByIdUserId(userId);
        } else if (routenId != null) {
            return userRoutenStatusRepository.findByIdRouteId(routenId);
        } else {
            return userRoutenStatusRepository.findAll();
        }
    }
    
    // braucht eigentlich immer eine id -> hier schwierig
    // müsste man nochmal drüber nachdenken
    @PatchMapping
    public ResponseEntity<UserRoutenStatus> updateUserRoutenStatus(@RequestBody UserRoutenStatus userRoutenStatus) {
        try {
            userRoutenStatusRepository.save(userRoutenStatus);
            return ResponseEntity.status(HttpStatus.OK).body(userRoutenStatus);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}