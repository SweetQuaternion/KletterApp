package com.dachpc.kletterapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;

import com.dachpc.kletterapp.Dtos.UserDTO;
import com.dachpc.kletterapp.Security.UserSyncRequest;
import com.dachpc.kletterapp.Services.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping(produces = "application/json")
    public UserDTO getUser(@RequestParam(required = false) String id, @RequestParam(required = false) String username) {
        return userService.findUser(id, username);
    }
    
    @PostMapping(produces = "application/json")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #request.keycloakId == authentication.principal.subject")
    public UserDTO syncUser(@RequestBody UserSyncRequest request) { 
        return userService.syncUser(request);
    }

    @PatchMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN') or #request.keycloakId == authentication.principal.subject")
    public UserDTO changeUser(@RequestBody UserDTO updatedUser) {
        return userService.updateUser(updatedUser);
    }
    
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN') or #request.keycloakId == authentication.principal.subject")
    public void deleteUser(@RequestParam String keycloakId) {
        userService.deleteUser(keycloakId);
    }
    
}