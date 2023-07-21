package com.cvcopilot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.Map;

@Service
public class ModificationService {

  private RedisTemplate<String, String> redisTemplate;

  private HashOperations<String, String, String> hashOperations;
  private ZSetOperations<String, String> zSetOperations;

  @Autowired
  public ModificationService(RedisTemplate<String, String> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @PostConstruct
  private void init() {
    hashOperations = redisTemplate.opsForHash();
    zSetOperations = redisTemplate.opsForZSet();
  }

  public void addOrUpdateModification(String userId, String modificationId, String state, String result) {
    String modificationKey = "modification:" + modificationId;
    long timestamp = System.currentTimeMillis();
    hashOperations.put(modificationKey, "state", state);
    hashOperations.put(modificationKey, "result", result);
    hashOperations.put(modificationKey, "userId", userId);
    hashOperations.put(modificationKey, "lastUpdate", String.valueOf(timestamp));
    zSetOperations.add("user:" + userId, modificationId, timestamp);
  }

  public Set<String> getAllModificationsForUser(String userId) {
    return zSetOperations.reverseRange("user:" + userId, 0, -1);
  }

  public Set<String> getTopKModificationsForUser(String userId, long k) {
    return zSetOperations.reverseRange("user:" + userId, 0, k - 1);
  }

  public Map<String, String> getModification(String modificationId) {
    return hashOperations.entries("modification:" + modificationId);
  }
}
