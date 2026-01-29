package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> loginUser(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(u -> u.getPassword().equals(password));
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

    // Logique de mise à jour déplacée ici
    public User updateUser(Long id, User details) {
        return userRepository.findById(id).map(user -> {
            // Infos perso
            if (details.getFirstName() != null) user.setFirstName(details.getFirstName());
            if (details.getLastName() != null) user.setLastName(details.getLastName());
            if (details.getPhone() != null) user.setPhone(details.getPhone());
            if (details.getPostalCode() != null) user.setPostalCode(details.getPostalCode());

            // Préférences (On met tout à jour car Android envoie l'objet complet)
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