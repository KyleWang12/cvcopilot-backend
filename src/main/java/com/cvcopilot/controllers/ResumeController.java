package com.cvcopilot.controllers;

import com.cvcopilot.payload.request.JobDescriptionRequest;
import com.cvcopilot.service.ResumeService;

import com.cvcopilot.service.StateService;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private StateService stateService;

    private static final Logger logger = LoggerFactory.getLogger(ResumeController.class);

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
        // check if there exists a modification to prevent penetration
        Map<String, String> state = stateService.getState(modificationId);
        if(state.isEmpty() || !state.containsKey("state") || !state.get("state").equals("finished")){
            return new ResponseEntity<>("Result is not available", HttpStatus.BAD_REQUEST);
        }

        // get the resume
        Map<String, String> resume = resumeService.getModification(modificationId);
        if(resume.isEmpty()){
            logger.error("Error when retrieving the resume");
            return new ResponseEntity<>("Error when retrieving the resume", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(resumeService.getModification(modificationId), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllResumes(@AuthenticationPrincipal(expression = "id") Long UserId, @RequestParam(defaultValue = "10") Integer limit){
        Set<String> modifications = resumeService.getAllModification(UserId, limit);
        if(modifications.isEmpty()) {
            return new ResponseEntity<>("No resume found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(modifications, HttpStatus.OK);
    }
}
