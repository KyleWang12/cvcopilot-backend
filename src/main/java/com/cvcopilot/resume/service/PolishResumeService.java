package com.cvcopilot.resume.service;

import com.cvcopilot.models.userProfile.UserProfile;
import com.cvcopilot.repository.ProfileRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PolishResumeService {
    @Autowired
    ProfileRepository profileRepository;

//    @Value("${spring.kafka.consumer.group-id}")
//    private static String groupId;

    private static final Logger logger = LoggerFactory.getLogger(PolishResumeService.class);

    private final String TOPIC = "resume";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String message){
        logger.info(String.format("$$ -> Producing message --> %s", message));
        this.kafkaTemplate.send(TOPIC, message);
    }

    @KafkaListener(topics = TOPIC, groupId = "resumeConsumer", containerFactory = "kafkaListenerContainerFactory")
    public void consume(ConsumerRecord<String, String> record){

        System.out.println(record.value());
        logger.info(String.format("$$ -> Consumed Message -> %s", record.value()));
    }


    public void MessasgeToMQueue(Long userId, String jobDescription) {
        //TODO：Get userprofile from Redis
        UserProfile userProfile = profileRepository.findById(userId).orElse(null);
        if (userProfile == null) {
            logger.error("user profile not found");
            //TODO: Send no userprofile error message
            return;
        }
        else{
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                send(jobDescription + objectMapper.writeValueAsString(userProfile));
            }
            catch(JsonProcessingException e){
                logger.error("JsonProcessingException error");
            }
        }
        System.out.println("success");
    }



}
