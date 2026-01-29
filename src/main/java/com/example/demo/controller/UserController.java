package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List; 
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    // On n'injecte QUE le service ici
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User saved = userService.registerUser(user);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User login) {
        Optional<User> userOpt = userService.loginUser(login.getEmail(), login.getPassword());
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(userOpt.get());
        } else {
            return ResponseEntity.status(401).body("Email ou mot de passe incorrect");
        }
    }

    // Cette méthode manquait et est appelée par Android
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/check-email")
    public ResponseEntity<?> checkEmail(@RequestParam String email) {
        boolean exists = userService.existsByEmail(email);
        return ResponseEntity.ok().body(Collections.singletonMap("exists", exists));
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String query) {
        List<User> users = userService.searchUsers(query);
        return ResponseEntity.ok(users);
    }

    // CORRECTION : On utilise userService.updateUser au lieu de userRepository
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        try {
            User updatedUser = userService.updateUser(id, userDetails);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}