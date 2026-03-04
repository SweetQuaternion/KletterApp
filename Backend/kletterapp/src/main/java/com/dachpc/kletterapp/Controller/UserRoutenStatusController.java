package com.dachpc.kletterapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dachpc.kletterapp.Entities.UserRoutenStatus;
import com.dachpc.kletterapp.Repositories.UserRoutenStatusRepository;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@CrossOrigin(origins = "http://localhost:5173") 
@RestController
@RequestMapping("/routen")
public class UserRoutenStatusController {

    @Autowired
    private UserRoutenStatusRepository userRoutenStatusRepository;

    @GetMapping
    public UserRoutenStatus getUserRoutenStatus(@RequestParam int userId, @RequestParam int routenId) {
        return userRoutenStatusRepository.findByUserIdAndRoutenId(userId, routenId);
    }

    @GetMapping("/user/{userId}")
    public List<UserRoutenStatus> getUserRoutenStatusByUserId(@PathVariable int userId) {
        return userRoutenStatusRepository.findByUserId(userId);
    }

    @GetMapping("/routen/{routenId}")
    public List<UserRoutenStatus> getUserRoutenStatusByRoutenId(@PathVariable int routenId) {
        return userRoutenStatusRepository.findByRoutenId(routenId);
    }

    @PutMapping
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