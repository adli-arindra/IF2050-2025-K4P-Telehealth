package org.drpl.telebe.controller;

import org.drpl.telebe.model.User;
import org.drpl.telebe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        // Simple check if user with same id exists
        User existingUser = userRepository.getUserById(user.getId());
        if (existingUser != null) {
            return ResponseEntity.badRequest().body("User with this ID already exists");
        }
        userRepository.addUser(user);
        return ResponseEntity.ok("Registration successful");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String id) {
        User user = userRepository.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok("Login successful");
        }
        return ResponseEntity.status(401).body("Invalid user ID");
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<User> getUserProfile(@PathVariable String id) {
        User user = userRepository.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }
}
