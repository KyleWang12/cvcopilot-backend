package com.cvcopilot.resume.controller;

import com.cvcopilot.models.userProfile.UserProfile;
import com.cvcopilot.resume.payload.JobDescriptionRequest;
import com.cvcopilot.resume.service.PolishResumeService;
import com.cvcopilot.security.services.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/polishResume")
public class PolishResumeController {

    @Autowired
    PolishResumeService polishResumeService;

    @PostMapping("/")
    public void polishResume(@AuthenticationPrincipal(expression = "id") Long UserId, @RequestBody JobDescriptionRequest jobDescriptionRequest){
        polishResumeService.MessasgeToMQueue(UserId, jobDescriptionRequest.getJobDescriptionRequest());
    }

    @GetMapping()
    public ResponseEntity<?> getPolishedResume(@AuthenticationPrincipal(expression = "id") Long UserId){
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
