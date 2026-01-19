package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@EnableScheduling
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@Service
public class CleanupService {
    @Autowired
    private EventRepository eventRepository;

    @Scheduled(cron = "0 0 0 * * *") // S'exécute tous les jours à minuit
    @Transactional
    public void removeOldEvents() {
        eventRepository.deleteByDateEvenementBefore(LocalDateTime.now());
    }
}

}
