package com.cooltools.usernamewebservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cooltools.usernamewebservice.service.UsernameService;
import com.cooltools.usernamewebservice.model.dto.UsernameDTO;
import com.cooltools.usernamewebservice.exception.UsernameExistsException;
import com.cooltools.usernamewebservice.exception.UsernameNotFoundException;

@RestController
@RequestMapping("/usernames")
public class UsernameController {

    private final UsernameService usernameService;

    @Autowired
    public UsernameController(UsernameService usernameService) {
        this.usernameService = usernameService;
    }

    @PostMapping
    public ResponseEntity<UsernameDTO> addUsername(@RequestBody UsernameDTO usernameRequest) {
        try {
            UsernameDTO response = usernameService.addUsername(usernameRequest.getUsername());
            return ResponseEntity.ok(response);
        } catch (UsernameExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @GetMapping("/{token}")
    public ResponseEntity<UsernameDTO> getUsernameByToken(@PathVariable("token") String token) {
        try {
            UsernameDTO response = usernameService.getUsernameByToken(token);
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{token}")
    public ResponseEntity<Void> deleteUsername(@PathVariable("token") String token) {
        try {
            usernameService.deleteUsername(token);
            return ResponseEntity.noContent().build();
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

