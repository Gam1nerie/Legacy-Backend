package com.example.demo.repository;

import com.example.demo.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    // Pour afficher les événements à venir (triés par date)
    List<Event> findByDateEvenementAfterOrderByDateEvenementAsc(LocalDateTime dateTime);
    
    // Pour supprimer les vieux événements
    void deleteByDateEvenementBefore(LocalDateTime dateTime);
}