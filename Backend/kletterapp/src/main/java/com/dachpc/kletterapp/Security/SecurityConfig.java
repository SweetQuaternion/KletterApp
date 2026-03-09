package com.dachpc.kletterapp.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JWTFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(customizer -> customizer.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // bitte leg keine Sessions an
            .authorizeHttpRequests(request -> request
                .requestMatchers("/api/users/login", "/api/users/register").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}



// Http Basic gibt uns ein Popup-Login
// Login form gibt uns eine Default-Login-Seite
// http.sessionManagement kann bestimmen, ob stateless (immer neue Session ID) oder was anderes

// Customizer<CsrfConfigurer<HttpSecurity>> custCstf = new Customizer<CsrfConfigurer<HttpSecurity>> {
//     @Override
//     public void customize(CsrfConfigurer<HttpSecurity> customizer) {
//         customizer.disable();
//     }
// };
// ist ein functional interface, also kann man einfach lambdas benutzen:
// http.csrf(customizer -> customizer.disable());

// @Bean // wir wollen uns selbst um UserDetailsService kümmern, damit wir unsere User aus der DB holen können
// public UserDetailsService userDetailsService() { // is an interface damn
//     UserDetails userDetails = User
//         .withUsername("test")
//         .password(passwordEncoder().encode("test"))
//         .roles("USER")
//         .build();
//     return new InMemoryUserDetailsManager(userDetails);
// }