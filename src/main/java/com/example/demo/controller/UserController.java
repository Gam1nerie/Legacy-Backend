package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

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
@GetMapping("/check-email")
public ResponseEntity<?> checkEmail(@RequestParam String email) {
    boolean exists = userService.existsByEmail(email);
    // On renvoie un objet JSON : {"exists": true}
    return ResponseEntity.ok().body(java.util.Collections.singletonMap("exists", exists));
}

@GetMapping("/api/users/search")
public ResponseEntity<List<User>> searchUsers(@RequestParam String query) {
    // Cette méthode cherche les utilisateurs dont le nom ou prénom contient la "query"
    return ResponseEntity.ok(userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(query, query));
}
}
