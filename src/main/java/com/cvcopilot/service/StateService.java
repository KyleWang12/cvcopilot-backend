package com.cvcopilot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.Map;

@Service
public class StateService {

  private RedisTemplate<String, String> redisTemplate;

  private HashOperations<String, String, String> hashOperations;
  private ZSetOperations<String, String> zSetOperations;

  private static final Logger logger = LoggerFactory.getLogger(StateService.class);

  @Autowired
  public StateService(RedisTemplate<String, String> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @PostConstruct
  private void init() {
    hashOperations = redisTemplate.opsForHash();
    zSetOperations = redisTemplate.opsForZSet();
  }

  public void addOrUpdateState(String userId, String modificationId, String state) {
    String modificationKey = "state:modification:" + modificationId;
    long timestamp = System.currentTimeMillis();
    hashOperations.put(modificationKey, "state", state);
    hashOperations.put(modificationKey, "userId", userId);
    hashOperations.put(modificationKey, "lastUpdate", String.valueOf(timestamp));
  }

  public Set<String> getAllModificationsForUser(String userId) {
    return zSetOperations.reverseRange("user:" + userId, 0, -1);
  }

  public Set<String> getTopKModificationsForUser(String userId, long k) {
    return zSetOperations.reverseRange("user:" + userId, 0, k - 1);
  }

  public Map<String, String> getState(String modificationId) {
    return hashOperations.entries("state:modification:" + modificationId);
  }
}
