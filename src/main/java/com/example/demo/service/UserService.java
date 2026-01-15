package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Import nécessaire
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder; // On déclare l'encodeur

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(); // On l'initialise
    }

    public User registerUser(User user) throws Exception {
        Optional<User> existing = userRepository.findByEmail(user.getEmail());
        if (existing.isPresent()) {
            throw new Exception("Email déjà utilisé");
        }
        
        // --- HACHAGE ICI ---
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        
        return userRepository.save(user);
    }

    public Optional<User> loginUser(String email, String passwordSaisi) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // --- VÉRIFICATION ICI ---
            // On ne peut pas faire .equals() car le hash change à chaque fois.
            // passwordEncoder.matches vérifie si le texte clair correspond au hash en base.
            if (passwordEncoder.matches(passwordSaisi, user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
    public boolean existsByEmail(String email) {
    return userRepository.findByEmail(email).isPresent();
}
}