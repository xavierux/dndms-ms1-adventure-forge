package com.xvclemente.dnd.ms1.controller;

import com.xvclemente.dnd.dtos.events.AventuraCreadaEvent; // dndms-event-dtos
import com.xvclemente.dnd.ms1.service.AdventureGenerationService;
import com.xvclemente.dnd.ms1.kafka.producer.AdventureEventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/adventures")
public class AdventureController {

    private final AdventureGenerationService adventureGenerationService;
    private final AdventureEventProducer adventureEventProducer;

    @Autowired
    public AdventureController(AdventureGenerationService adventureGenerationService,
                               AdventureEventProducer adventureEventProducer) {
        this.adventureGenerationService = adventureGenerationService;
        this.adventureEventProducer = adventureEventProducer;
    }

    @PostMapping("/generate-random")
    public ResponseEntity<AventuraCreadaEvent> generateRandomAdventure() {
        AventuraCreadaEvent event = adventureGenerationService.generateRandomAdventure();
        adventureEventProducer.sendAdventureCreatedEvent(event);
        // Podrías devolver el evento o solo un mensaje de éxito
        return ResponseEntity.ok(event); 
    }
}