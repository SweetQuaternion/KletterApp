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

    @Autowired
    private JWTService jwtService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public User register(User user) throws IllegalArgumentException {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request) throws UsernameNotFoundException, IllegalArgumentException {
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        if (!passwordEncoder.matches(request.getPassword(),user.get().getPassword())) {
            throw new IllegalArgumentException("Bad credentials");
        }
        return new AuthResponse(jwtService.generateToken(user.get().getEmail()));
    }
}
