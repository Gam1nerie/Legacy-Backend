package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Hache le mot de passe avant de sauvegarder l'utilisateur
     */
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Utilise BCrypt pour comparer le mot de passe reçu avec le hachage en base
     */
    public Optional<User> loginUser(String email, String password) {
        System.out.println("Tentative de connexion pour l'email : " + email);
        
        return userRepository.findByEmail(email).filter(user -> {
            boolean matches = passwordEncoder.matches(password, user.getPassword());
            if (matches) {
                System.out.println("SUCCÈS : Le mot de passe correspond via BCrypt.");
            } else {
                System.out.println("ÉCHEC : Le mot de passe ne correspond pas.");
            }
            return matches;
        });
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<User> searchUsers(String query) {
        return userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(query, query);
    }

    /**
     * Met à jour les infos et les préférences de jeux
     */
    public User updateUser(Long id, User details) {
        return userRepository.findById(id).map(user -> {
            // Infos perso
            if (details.getFirstName() != null) user.setFirstName(details.getFirstName());
            if (details.getLastName() != null) user.setLastName(details.getLastName());
            if (details.getPhone() != null) user.setPhone(details.getPhone());
            if (details.getPostalCode() != null) user.setPostalCode(details.getPostalCode());

            // Préférences de jeux
            user.setMagic(details.isMagic());
            user.setPokemon(details.isPokemon());
            user.setLorcana(details.isLorcana());
            user.setAltered(details.isAltered());
            user.setRiftbound(details.isRiftbound());
            user.setOnePiece(details.isOnePiece());
            user.setW40k(details.isW40k());
            user.setWaos(details.isWaos());
            user.setBoardgames(details.isBoardgames());
            user.setRpg(details.isRpg());

            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }
}