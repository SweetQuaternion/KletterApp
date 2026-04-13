package com.dachpc.kletterapp.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dachpc.kletterapp.Dtos.UserCreateDTO;
import com.dachpc.kletterapp.Dtos.UserResponseDTO;
import com.dachpc.kletterapp.Entities.User;
import com.dachpc.kletterapp.Mappers.UserMapper;
import com.dachpc.kletterapp.Repositories.UserRepository;
import com.dachpc.kletterapp.Security.UserSyncRequest;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;
    

    public UserResponseDTO findUser(String id, String username) {
    if (id != null) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User mit id " + id + " nicht gefunden"));
        return userMapper.toDTO(user);
    }
    if (username != null) {
        User user = userRepository.findByName(username).orElseThrow(() -> new EntityNotFoundException("User mit username " + username + " nicht gefunden"));
        return userMapper.toDTO(user);
    }
    throw new IllegalArgumentException("need either id or username");
    }

    public UserResponseDTO syncUser(UserSyncRequest request) {
        User user = userRepository.findByKeycloakId(request.keycloakId()).orElse(null);
        if (user == null) {
            user = userRepository.save(new User(request.keycloakId(), request.name()));
        }
        return userMapper.toDTO(user);
    }

    public UserResponseDTO updateUser(UserCreateDTO updatedUser) {
        User prevUser = userRepository.findByKeycloakId(updatedUser.getKeycloakId()).orElseThrow(() -> new EntityNotFoundException());
        userMapper.updateEntity(updatedUser, prevUser);
        userRepository.save(prevUser);
        return userMapper.toDTO(prevUser);
    }

    public void deleteUser(String keycloakId) {
        User user = userRepository.findByKeycloakId(keycloakId).orElseThrow(() -> new EntityNotFoundException());
        userRepository.delete(user);
    }

    public void reward(String userId, int punkte) {
        User user = userRepository.findByKeycloakId(userId).orElseThrow(() -> new EntityNotFoundException());
        user.setPunkte(user.getPunkte() + punkte);
        user.setAscentCount(user.getAscentCount() + 1);
        userRepository.save(user);
    }

}
