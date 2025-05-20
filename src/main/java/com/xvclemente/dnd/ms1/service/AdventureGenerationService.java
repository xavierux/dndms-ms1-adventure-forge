package com.xvclemente.dnd.ms1.service;

import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Service;
import com.xvclemente.dnd.dtos.events.AventuraCreadaEvent; // dndms-event-dtos

@Service
public class AdventureGenerationService {
        private final Random random = new Random();
    private static final String[] CHALLENGE_TYPES = {"investigar", "recuperar", "proteger", "escoltar", "eliminar"};
    private static final String[] ENVIRONMENTS = {"ruina olvidada", "bosque encantado", "taberna sospechosa", "cueva oscura", "montaña nevada"};
    private static final String[] GOLD_TIERS = {"poor", "generous", "treasure"};

    public AventuraCreadaEvent generateRandomAdventure() {
        String adventureId = UUID.randomUUID().toString();
        String challengeType = CHALLENGE_TYPES[random.nextInt(CHALLENGE_TYPES.length)];
        String environment = ENVIRONMENTS[random.nextInt(ENVIRONMENTS.length)];
        int numEncounters = random.nextInt(3) + 1; // 1 a 3 encuentros
        String goldRewardTier = GOLD_TIERS[random.nextInt(GOLD_TIERS.length)];

        return new AventuraCreadaEvent(adventureId, challengeType, environment, numEncounters, goldRewardTier);
    }
}
