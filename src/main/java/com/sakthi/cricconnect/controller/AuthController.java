package com.sakthi.cricconnect.controller;

import com.sakthi.cricconnect.dto.RegisterRequest;
import com.sakthi.cricconnect.model.User;
import com.sakthi.cricconnect.service.AuthService;
import com.sakthi.cricconnect.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            User savedUser = authService.register(request);

            // Generate token for the registered user
            String token = jwtService.generateToken(savedUser.getEmail(), savedUser.getRole().name());

            return ResponseEntity.ok(Map.of(
                    "user", savedUser,
                    "token", token
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        try {
            String email = loginRequest.get("email");
            String password = loginRequest.get("password");

            // login now returns JWT token
            String token = authService.login(email, password);

            User user = authService.getUserByEmail(email);

            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "email", user.getEmail(),
                    "role", user.getRole()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        }
    }
}
