package com.dachpc.kletterapp.Security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dachpc.kletterapp.Entities.User;
import com.dachpc.kletterapp.Repositories.UserRepository;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return new UserPrincipal(user.get());
        } else {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }
}


// Was ist der Sinn dieser Klasse?
// Sie ruft ja auch einfach nur das Repository auf.

// Antwort oder so: Spring Security weiß nicht, wie meine App aussieht.
// Es weiß nicht, dass ich eine User-Klasse habe, die in der DB liegt.
// Es weiß nicht, wie es einen User anhand einer Email finden soll.
// Es weiß nur, dass es eine Methode loadUserByUsername gibt, die einen UserDetails zurückgibt,
// und dass es eine UserDetails-Klasse gibt, die bestimmte Methoden hat (getUsername, getPassword, getAuthorities, etc).
// Keine Ahnung wo diese Methode aufgerufen wird, aber sie wird aufgerufen, wenn Spring Security einen User authentifizieren will.
// Teil von Spring Security Magic oder so.

// Also muss ich Spring Security eine Klasse geben, die UserDetailsService implementiert,
// damit es weiß, wie es einen User anhand einer Email finden soll.
// Es ist sozusagen die Brücke zwischen meiner User-Klasse und Spring Securitys UserDetails.