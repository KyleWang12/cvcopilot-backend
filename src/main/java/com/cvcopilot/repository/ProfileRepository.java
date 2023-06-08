package com.cvcopilot.repository;

import com.cvcopilot.models.userProfile.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfileRepository extends MongoRepository<UserProfile, Long> {
}
