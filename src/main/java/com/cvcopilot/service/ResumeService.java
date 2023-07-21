package com.cvcopilot.service;

import com.cvcopilot.models.userProfile.UserProfile;
import com.cvcopilot.repository.ProfileRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ResumeService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ModificationService modificationService;

    private static final Logger logger = LoggerFactory.getLogger(ResumeService.class);

    private final String TOPIC = "resume";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private void send(String message){
        logger.debug(String.format("$$ -> Producing message --> %s", message));
        this.kafkaTemplate.send(TOPIC, message);
    }

    public String messageToMQueue(Long userId, String jobDescription) {
        UserProfile userProfile = profileRepository.findById(userId).orElse(null);
        if (userProfile == null) {
            logger.error("user profile not found");
            return null;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String modificationId = UUID.randomUUID().toString();
            String userIdString = String.format("%019d", userId);
            StringBuilder sb = new StringBuilder();
            sb.append(userIdString);
            sb.append(modificationId);
            sb.append(":");
            sb.append("Job description: ");
            sb.append(jobDescription);
            sb.append("\n");
            sb.append("User profile: ");
            sb.append(objectMapper.writeValueAsString(userProfile));
            send(sb.toString());
            modificationService.addOrUpdateModification(userIdString, modificationId, "in_queue", "#");
            return modificationId;
        }
        catch(JsonProcessingException e){
            logger.error("JsonProcessingException: " + e.getMessage());
        }
        return null;
    }

    public String getStatus(String modificationId) {
        return modificationService.getModification(modificationId).get("state");
    }

    public String getResume(String modificationId) {
        return modificationService.getModification(modificationId).get("result");
    }

    public Map<String, String> getModification(String modificationId) {
        return modificationService.getModification(modificationId);
    }
}
