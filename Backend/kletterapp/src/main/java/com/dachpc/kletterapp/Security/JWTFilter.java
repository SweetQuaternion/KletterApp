package com.dachpc.kletterapp.Security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter { // dieser Filter soll nur einmal pro Request ausgeführt werden

    @Autowired
    private JWTService jwtService;

    @Autowired
    private MyUserDetailService userDetailsService;

    // TODO nochmal drüber nachdenken später
    // MyUserDetailsService zu autowiren, würde eine Circular Dependency verursachen?
    // SecurityConfig braucht JWTFilter braucht MyUserDetailsService braucht UserRepository ne was wie hä?
    // Wir holen ihn unten später, wenn wir ihn brauchen
    // Spring hat dann MyUserDetailsService schon gebaut, wenn wir ihn haben wollen
    // @Autowired
    // private ApplicationContext context;
    // Aber es scheint auch so noch zu compilieren also egal ansonsten unten
    // UserDetails userDetails = context.getBean(MyUserDetailService.class).loadUserByUsername(email); // Wir bekommen alle UserDetails hier

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // wir bekommen "Bearer token" vom Client
        // wir müssen das Token extrahieren
        System.out.println("JWTFilter läuft!");
        String authHeader = request.getHeader("Authorization");    // checkt ob wir auth headers haben
        String token = null;
        String email = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {   // dann enthält der Header auch ein Token, was wir prüfen
            token = authHeader.substring(7);                            // wir schnibbeln den Header weg und extrahieren das Token selbst
            email = jwtService.extractUserName(token);                  // wir holen die Email des Users aus dem Token raus
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) { 
            // Token ist vorhanden, aber Nutzer ist noch nicht eingeloggt
            // 1. Token muss validiert werden
            // 2. UserDetails aus DB holen
            
            // Existiert der Nutzer wirklich in der DB?
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // Ist das Token auch gültig
            if (jwtService.validateToken(token, userDetails)) {
                // ja, also müssen wir den Nutzer jetzt als authentifiziert markieren, damit er Zugriff bekommt
                // wir geben ein Token an den nächsten Filter 
                // userDetails: enthält die Informationen über den Nutzer
                // credentials: null, weil wir das Passwort ja nicht haben, und es auch nicht brauchen, weil das Token ja schon validiert ist
                // authorities: die Rollen des Nutzers, damit Spring Security weiß, welche Rechte er hat
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // Wie bitte schön soll man das auswendig wissen?
                SecurityContextHolder.getContext().setAuthentication(authToken); // Hier loggen wir den Nutzer ein
                System.out.println("Authentication gesetzt: " + SecurityContextHolder.getContext().getAuthentication());
                System.out.println("Nutzer " + email + " erfolgreich authentifiziert!");
            }
        }
        filterChain.doFilter(request, response); // ab zum nächsten Filter mit dem Request
    } 
    
}
