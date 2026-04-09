package com.dachpc.kletterapp.Controller;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dachpc.kletterapp.Services.AvatarData;
import com.dachpc.kletterapp.Services.AvatarService;


@RestController
@RequestMapping("/api/avatar")
public class AvatarController {

    @Autowired
    public AvatarService avatarService;

    @GetMapping
    public ResponseEntity<byte[]> getAvatar(@RequestParam String userId) {
        AvatarData avatarData = avatarService.getAvatar(userId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(avatarData.contentType()))
                .body(avatarData.bytes());
    }
    
    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN') or #userId == authentication.principal.subject")
    public String uploadAvatar(@RequestParam String userId, @RequestPart MultipartFile file) {
        Path filePath = avatarService.storeAvatar(userId, file);
        System.out.println("Avatar stored at: " + filePath);
        return filePath.toString();
    }
    
    
}
