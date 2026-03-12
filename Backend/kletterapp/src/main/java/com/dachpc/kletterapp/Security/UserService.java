package com.dachpc.kletterapp.Security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dachpc.kletterapp.Entities.User;
import com.dachpc.kletterapp.Repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // public AuthResponse register(User user) throws IllegalArgumentException {
    //     if (userRepository.existsByEmail(user.getEmail())) {
    //         throw new IllegalArgumentException("Email already exists");
    //     }
    //     user.setPassword(passwordEncoder.encode(user.getPassword()));
    //     userRepository.save(user);
    //     return new AuthResponse(jwtService.generateToken(user.getEmail()), new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getBildUrl(), user.getRole().name()));
    // }

    // public AuthResponse login(LoginRequest request) throws UsernameNotFoundException, IllegalArgumentException {
    //     Optional<User> user = userRepository.findByEmail(request.email());
    //     if (user.isEmpty()) {
    //         throw new UsernameNotFoundException("User not found");
    //     }
    //     if (!passwordEncoder.matches(request.password(),user.get().getPassword())) {
    //         throw new IllegalArgumentException("Bad credentials");
    //     }
    //     return new AuthResponse(jwtService.generateToken(user.get().getEmail()), new UserResponse(user.get().getId(), user.get().getName(), user.get().getEmail(), user.get().getBildUrl(), user.get().getRole().name()));
    // }
}
