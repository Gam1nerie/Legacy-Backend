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

    // Injection par constructeur (recommandé)
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
        return ResponseEntity.ok().body(Collections.singletonMap("exists", exists));
    }

    // FIX 2 : Utilisation de userService au lieu de userRepository pour la cohérence
    @GetMapping("/search") // Note: le chemin est déjà /api/users via le RequestMapping
    public ResponseEntity<List<User>> searchUsers(@RequestParam String query) {
        // Cette méthode doit être créée dans ton UserService
        List<User> users = userService.searchUsers(query);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return userRepository.findById(id).map(user -> {
            // Mise à jour des infos personnelles
            if (userDetails.getFirstName() != null) user.setFirstName(userDetails.getFirstName());
            if (userDetails.getLastName() != null) user.setLastName(userDetails.getLastName());
            if (userDetails.getPhone() != null) user.setPhone(userDetails.getPhone());
            if (userDetails.getPostalCode() != null) user.setPostalCode(userDetails.getPostalCode());

            // Mise à jour massive des préférences de jeux
            user.setMagic(userDetails.isMagic());
            user.setPokemon(userDetails.isPokemon());
            user.setLorcana(userDetails.isLorcana());
            user.setAltered(userDetails.isAltered());
            user.setRiftbound(userDetails.isRiftbound());
            user.setOnePiece(userDetails.isOnePiece());
            user.setW40k(userDetails.isW40k());
            user.setWaos(userDetails.isWaos());
            user.setBoardgames(userDetails.isBoardgames());
            user.setRpg(userDetails.isRpg());

            User updated = userRepository.save(user);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }
}
