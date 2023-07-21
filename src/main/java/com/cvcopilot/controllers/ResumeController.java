package com.cvcopilot.controllers;

import com.cvcopilot.payload.request.JobDescriptionRequest;
import com.cvcopilot.service.ResumeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/polishResume")
public class ResumeController {

    @Autowired
    ResumeService resumeService;

    @PostMapping("/")
    public ResponseEntity<?> polishResume(@AuthenticationPrincipal(expression = "id") Long UserId, @RequestBody JobDescriptionRequest jobDescriptionRequest){
        String modificationId = resumeService.messageToMQueue(UserId, jobDescriptionRequest.getJobDescription());
        if(modificationId == null){
            return new ResponseEntity<>("Error in sending resume", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(modificationId, HttpStatus.OK);
    }

    @GetMapping("/{modificationId}")
    public ResponseEntity<?> getResume(@PathVariable String modificationId){
        return new ResponseEntity<>(resumeService.getModification(modificationId), HttpStatus.OK);
    }
}
