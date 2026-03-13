package com.dachpc.kletterapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import com.dachpc.kletterapp.Entities.UserRoutenStatus;
import com.dachpc.kletterapp.Repositories.UserRoutenStatusRepository;



@CrossOrigin(origins = "http://localhost:5173") 
@RestController
@RequestMapping("/api/userroutenstatus")
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUserRoutenStatus(@RequestBody UserRoutenStatus userRoutenStatus) {
        userRoutenStatusRepository.save(userRoutenStatus);
    }
    
    // braucht eigentlich immer eine id -> hier schwierig
    // müsste man nochmal drüber nachdenken
    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateUserRoutenStatus(@RequestBody UserRoutenStatus userRoutenStatus) {
        userRoutenStatusRepository.save(userRoutenStatus);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserRoutenStatus(@RequestParam (required = false) String userId, @RequestParam (required = false) int routenId) {
        if (userId == null || routenId == 0) {
            throw new IllegalArgumentException("UserId or RoutenId must be provided");
        }
        UserRoutenStatus existingStatus = userRoutenStatusRepository.findByIdUserIdAndIdRouteId(Integer.parseInt(userId), routenId);
        userRoutenStatusRepository.delete(existingStatus);
    }
}