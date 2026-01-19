package com.example.demo.service;

import com.example.demo.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class CleanupService {

    @Autowired
    private EventRepository eventRepository;

    // S'exécute tous les jours à minuit pour supprimer les événements passés
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void removeOldEvents() {
        eventRepository.deleteByDateEvenementBefore(LocalDateTime.now());
    }
}