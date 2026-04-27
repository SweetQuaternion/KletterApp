package com.dachpc.kletterapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dachpc.kletterapp.Entities.UserRoutenStatus;
import com.dachpc.kletterapp.Services.UserRoutenStatusService;


@RestController
@RequestMapping("/api/userroutenstatus")
public class UserRoutenStatusController {

    @Autowired
    private UserRoutenStatusService userRoutenStatusService;


    @GetMapping(produces = "application/json")
    public UserRoutenStatus getUserRoutenStatus(@RequestParam String userId, @RequestParam Integer routenId) {
        return userRoutenStatusService.getStatus(userId, routenId);
    }

    @GetMapping(path = "/all", produces = "application/json")
    public List<UserRoutenStatus> getAllUserRoutenStatus(@RequestParam Integer[] routenIdList, @RequestParam String userId) {
        return userRoutenStatusService.getAllStatus(routenIdList, userId);
    }

    @PostMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public UserRoutenStatus createUserRoutenStatus(@RequestBody UserRoutenStatus userRoutenStatus) {
        return userRoutenStatusService.addStatus(userRoutenStatus);
    }
    
    @PatchMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public UserRoutenStatus updateUserRoutenStatus(@RequestBody UserRoutenStatus userRoutenStatus) {
        return userRoutenStatusService.updateStatus(userRoutenStatus);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserRoutenStatus(@RequestParam(required = false) String userId, @RequestParam(required = false) int routenId) {
        userRoutenStatusService.deleteStatus(userId, routenId);
    }
}