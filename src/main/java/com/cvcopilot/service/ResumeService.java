package com.cvcopilot.service;

import com.cvcopilot.models.userProfile.UserProfile;
import com.cvcopilot.repository.ModificationRepository;
import com.cvcopilot.repository.ProfileRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ResumeService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ModificationRepository modificationRepository;

    @Autowired
    private StateService stateService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private HashOperations<String, String, String> hashOperations;
    private ZSetOperations<String, String> zSetOperations;

    private static final Logger logger = LoggerFactory.getLogger(ResumeService.class);

    private final String TOPIC = "resume";

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
        zSetOperations = redisTemplate.opsForZSet();
    }

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
            stateService.addOrUpdateState(userIdString, modificationId, "in_queue");
            return modificationId;
        }
        catch(JsonProcessingException e){
            logger.error("JsonProcessingException: " + e.getMessage());
        }
        return null;
    }

    public Map<String, String> getModification(String modificationId) {
        Map<String, String> modification = hashOperations.entries("result:modification:" + modificationId);
        if(modification.isEmpty()) {
            modificationRepository.findByModificationId(modificationId).ifPresent(m -> {
                modification.put("result", m.getResult());
                modification.put("userId", m.getUserId().toString());
                modification.put("lastUpdated", m.getLastUpdated().toString());
            });
            if (!modification.isEmpty()) {
                hashOperations.putAll("result:modification:" + modificationId, modification);
                // Setting the expiry time to 2 months (approximately 60 days)
                redisTemplate.expire("result:modification:" + modificationId, 60, TimeUnit.DAYS);
            }
        }
        return modification;
    }

    public Set<String> getAllModification(Long userId, Integer limit) {
        Set<String> records = zSetOperations.reverseRangeByScore(String.valueOf(userId), 0, System.currentTimeMillis(), 0, limit);
        if(records == null || records.isEmpty()) {
            Set<String> finalRecords = new HashSet<>();
            modificationRepository.findByUserId(userId).forEach(m -> {
                zSetOperations.add(String.valueOf(userId), m.toString(), m.getLastUpdated());
                redisTemplate.expire(String.valueOf(userId), 60, TimeUnit.DAYS);
                finalRecords.add(m.toString());
            });
            return finalRecords;
        }
        return records;
    }
}
