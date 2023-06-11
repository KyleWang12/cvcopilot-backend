package com.cvcopilot.controllers;

import com.cvcopilot.models.userProfile.UserProfile;
import com.cvcopilot.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/profiles/me")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getUserProfile(@PathVariable Long id){
        UserProfile userProfile = profileService.findByID(id);
        if (userProfile == null) {
            return new ResponseEntity<>("UserProfile not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userProfile, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> createUserProfile(@RequestBody UserProfile userProfile){
        UserProfile createdUserProfile = profileService.create(userProfile);
        return new ResponseEntity<>(createdUserProfile, HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<?> updateUserProfile(@PathVariable Long id, @RequestBody UserProfile userProfile){
        UserProfile existingUserProfile = profileService.findByID(id);
        if(existingUserProfile == null){
            return new ResponseEntity<>("UserProfile not found", HttpStatus.NOT_FOUND);
        }
        UserProfile updatedUserProfile = profileService.update(id, userProfile);
        return new ResponseEntity<>(updatedUserProfile, HttpStatus.OK);
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteUserProfile(@PathVariable Long id){
        UserProfile userProfile = profileService.findByID(id);
        if(userProfile == null){
            return new ResponseEntity<>("UserProfile not found", HttpStatus.NOT_FOUND);
        }
        profileService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/")
    public ResponseEntity<?> modifyUserProfile(@PathVariable Long id, @RequestBody UserProfile userProfile){
        UserProfile existingUserProfile = profileService.findByID(id);
        if(existingUserProfile == null){
            return new ResponseEntity<>("UserProfile not found", HttpStatus.NOT_FOUND);
        }
        UserProfile updatedUserProfile = profileService.update(id, userProfile);
        return new ResponseEntity<>(updatedUserProfile, HttpStatus.OK);
    }
}
