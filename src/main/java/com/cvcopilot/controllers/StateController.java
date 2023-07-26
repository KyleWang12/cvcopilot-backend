package com.cvcopilot.controllers;

import com.cvcopilot.payload.request.JobDescriptionRequest;
import com.cvcopilot.service.ResumeService;

import com.cvcopilot.service.StateService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/state")
public class StateController {

  @Autowired
  StateService stateService;

  @GetMapping("/{modificationId}")
  public ResponseEntity<?> getResume(@PathVariable String modificationId){
    Map<String, String> state = stateService.getState(modificationId);
    if(state.isEmpty()){
      return new ResponseEntity<>("Error getting the state", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(state, HttpStatus.OK);
  }
}
