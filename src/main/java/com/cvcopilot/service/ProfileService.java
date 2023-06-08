package com.cvcopilot.service;

import com.cvcopilot.models.userProfile.UserProfile;
import com.cvcopilot.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public UserProfile findByID(Long id) {
        return profileRepository.findById(id).orElse(null);
    }

    public UserProfile create(UserProfile userProfile){
        return profileRepository.save(userProfile);
    }

    public void delete(Long id){
        UserProfile userProfile = findByID(id);
        profileRepository.delete(userProfile);
    }

    public UserProfile update(Long id, UserProfile userProfile){
        UserProfile existingUserProfile = findByID(id);
        updateProperties(existingUserProfile, userProfile);
        return profileRepository.save(existingUserProfile);
    }

    private void updateProperties(UserProfile existingUserProfile, UserProfile newUserProfile) {
        if(newUserProfile.getFirstname() != null){
            existingUserProfile.setFirstname(newUserProfile.getFirstname());
        }
        if(newUserProfile.getLastname() != null){
            existingUserProfile.setLastname(newUserProfile.getLastname());
        }
        if(newUserProfile.getPhone() != null){
            existingUserProfile.setPhone(newUserProfile.getPhone());
        }
        if(newUserProfile.getAddress() != null){
            existingUserProfile.setAddress(newUserProfile.getAddress());
        }
        if(newUserProfile.getLinks() != null){
            existingUserProfile.setLinks(newUserProfile.getLinks());
        }
        if(newUserProfile.getSkills() != null){
            existingUserProfile.setSkills(newUserProfile.getSkills());
        }
        if(newUserProfile.getProjects() != null){
            existingUserProfile.setProjects(newUserProfile.getProjects());
        }
        if(newUserProfile.getWorkExperiences() != null){
            existingUserProfile.setWorkExperiences(newUserProfile.getWorkExperiences());
        }
        if(newUserProfile.getEducations() != null){
            existingUserProfile.setEducations(newUserProfile.getEducations());
        }

    }
}
