package com.fgc.email.controller;

import com.fgc.email.service.EmailService;
import com.fgc.email.controller.model.EmailRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class EmailController {

  private final EmailService emailService;

  public EmailController(EmailService emailService) {
    this.emailService = emailService;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> email(@RequestBody final EmailRequestDTO emailRequestDTO) {
    this.emailService.sendEmail(emailRequestDTO);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
