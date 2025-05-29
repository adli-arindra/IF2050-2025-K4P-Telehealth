package org.drpl.telebe.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String loginRequest) {
        System.out.println("Login request received: " + loginRequest);
        return ResponseEntity.ok("Login successful (placeholder)");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody String registerRequest) {
        System.out.println("Register request received: " + registerRequest);
        return ResponseEntity.ok("Registration successful (placeholder)");
    }

    @GetMapping("/profile")
    public ResponseEntity<String> getUserProfile() {
        System.out.println("Get user profile request received");
        return ResponseEntity.ok("User profile data (placeholder)");
    }
}