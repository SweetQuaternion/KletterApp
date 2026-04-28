package com.dachpc.kletterapp.Security;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
// import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${JWT_ISSUER_URI}")
    private String issuerUri;

    @Value("${JWT_JWK_SET_URI}")
    private String jwkSetUri;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "/api/hallen/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/waende/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/routen/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/kommentare/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/v3/*").permitAll() // Swagger UI und API-Dokumentation
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // REST-API, also keine Sessions
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));  // Keycloak Login aktivieren
        
        return http.build();
    }

    // @Bean
    // public JwtDecoder jwtDecoder() {
    //     return JwtDecoders.fromIssuerLocation(issuerUri);
    // }

    // Müssen wir deaktivieren, da wir keinen Nginx Reverse Proxy nutzen
    // Keycloak muss unter http://localhost:8080/ erreichbar sein, damit der Issuer-Check funktioniert
    // aber auch unter http://localhost:8180/ erreichbar sein, damit das Frontend happy ist
    // also deaktivieren und nur den JWK-Set-URI nutzen, damit die JWTs trotzdem validiert werden können
    @Bean
    public JwtDecoder jwtDecoder() {
        NimbusJwtDecoder decoder = NimbusJwtDecoder
            .withJwkSetUri(jwkSetUri)
            .build();
        return decoder; // kein Issuer-Check
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}

