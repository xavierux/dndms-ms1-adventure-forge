package com.xvclemente.dnd.ms1.kafka.producer;

import com.xvclemente.dnd.dtos.events.AventuraCreadaEvent;  // dndms-event-dtos
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class AdventureEventProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdventureEventProducer.class);

    @Value("${app.kafka.topic.aventuras-creadas}") // Toma el nombre del topic de application.properties
    private String topicAventurasCreadas;

    private final KafkaTemplate<String, AventuraCreadaEvent> kafkaTemplate;

    @Autowired
    public AdventureEventProducer(KafkaTemplate<String, AventuraCreadaEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendAdventureCreatedEvent(AventuraCreadaEvent event) {
        LOGGER.info("Intentando enviar AventuraCreadaEvent: {}", event.getAdventureId());
        CompletableFuture<SendResult<String, AventuraCreadaEvent>> future = 
            kafkaTemplate.send(topicAventurasCreadas, event.getAdventureId(), event);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                LOGGER.info("AventuraCreadaEvent enviado con éxito [adventureId={}] a topic [{}] con offset [{}]",
                        event.getAdventureId(), topicAventurasCreadas, result.getRecordMetadata().offset());
            } else {
                LOGGER.error("Error al enviar AventuraCreadaEvent [adventureId={}] a topic [{}]: {}",
                        event.getAdventureId(), topicAventurasCreadas, ex.getMessage());
            }
        });
    }
}