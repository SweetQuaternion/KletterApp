package com.dachpc.kletterapp.Security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/hallen", "/api/routen").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/users").permitAll()          // Daten von Usern können ungeschützt abgefragt werden, da wir nur die ID, Username und Profilbild zurückgeben und keine sensiblen Daten
                .requestMatchers(HttpMethod.POST, "/api/users").authenticated()     // User müssen sich synchronisieren können, wenn sie sich das erste Mal anmelden, also muss diese Route ungeschützt bleiben
                .requestMatchers(HttpMethod.PATCH, "/api/users").authenticated()    // User müssen ihre Daten ändern können, aber nur wenn sie eingeloggt sind (und idealerweise nur, wenn sie sich selbst ändern oder Admins)
                .requestMatchers(HttpMethod.DELETE, "/api/users").authenticated()   // User müssen ihre Daten löschen können, aber nur wenn sie eingeloggt sind (und idealerweise nur, wenn sie sich selbst löschen oder Admins)
                .anyRequest().authenticated()                                                   // der Rest - erstmal alles schützen
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // REST-API, also keine Sessions
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));  // Keycloak Login aktivieren
        
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return JwtDecoders.fromIssuerLocation("http://localhost:8180/realms/KletterApp");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}

