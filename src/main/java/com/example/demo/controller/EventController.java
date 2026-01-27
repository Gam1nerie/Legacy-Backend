package com.example.demo.controller;

import com.example.demo.entity.Event;
import com.example.demo.entity.User;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*")
public class EventController {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public EventController(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    // 1. Récupérer tous les événements à venir
    @GetMapping
    public List<Event> getAllEvents() {
        return eventRepository.findByDateEvenementAfterOrderByDateEvenementAsc(LocalDateTime.now());
    }

    // 2. Créer un événement (Réservé aux admins plus tard)
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        return ResponseEntity.ok(eventRepository.save(event));
    }
    // À ajouter dans ton EventController.java
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        return eventRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
}

    // 3. Rejoindre un événement
@PostMapping("/{eventId}/join")
public ResponseEntity<?> joinEvent(@PathVariable Long eventId, @RequestParam Long userId) {
    Optional<Event> eventOpt = eventRepository.findById(eventId);
    Optional<User> userOpt = userRepository.findById(userId);

    if (eventOpt.isPresent() && userOpt.isPresent()) {
        Event event = eventOpt.get();
        User user = userOpt.get();

        if (event.getParticipants().contains(user)) {
            return ResponseEntity.badRequest().body("Déjà inscrit");
        }

        if (event.getParticipants().size() >= event.getNbrmax()) {
            return ResponseEntity.badRequest().body("Complet");
        }

        event.getParticipants().add(user);
        eventRepository.save(event);
        return ResponseEntity.ok("Inscription réussie");
    }
    return ResponseEntity.notFound().build(); // <--- Vérifie le ; ici
} // <--- Vérifie la fermeture de la méthode

@PostMapping("/{eventId}/leave")
public ResponseEntity<?> leaveEvent(@PathVariable Long eventId, @RequestParam Long userId) {
    Optional<Event> eventOpt = eventRepository.findById(eventId);
    Optional<User> userOpt = userRepository.findById(userId);

    if (eventOpt.isPresent() && userOpt.isPresent()) {
        Event event = eventOpt.get();
        User user = userOpt.get();

        if (event.getParticipants().contains(user)) {
            event.getParticipants().remove(user); 
            eventRepository.save(event);
            return ResponseEntity.ok("Désinscription réussie");
        }
        return ResponseEntity.badRequest().body("Vous n'êtes pas inscrit");
    }
    return ResponseEntity.notFound().build();
}

@PostMapping("/{id}/add-guest")
public ResponseEntity<?> addGuest(@PathVariable Long id, @RequestParam String firstName, @RequestParam String lastName) {
    return eventRepository.findById(id).map(event -> {
        String fullName = firstName + " " + lastName;
        event.getGuests().add(fullName.trim());
        eventRepository.save(event);
        return ResponseEntity.ok().build();
    }).orElse(ResponseEntity.notFound().build());
}
}
