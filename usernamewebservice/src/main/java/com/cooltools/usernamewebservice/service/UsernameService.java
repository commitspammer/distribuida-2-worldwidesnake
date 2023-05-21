package com.cooltools.usernamewebservice.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.cooltools.usernamewebservice.model.dto.UsernameDTO;
import com.cooltools.usernamewebservice.exception.UsernameExistsException;
import com.cooltools.usernamewebservice.exception.UsernameNotFoundException;

@Service
public class UsernameService {

    private final Map<String, String> usernames = new HashMap<>();

    public UsernameDTO addUsername(String username) throws UsernameExistsException {
        if (usernames.containsValue(username)) {
            throw new UsernameExistsException();
        }
        
        String token = generateToken();
        usernames.put(token, username);
        
        return new UsernameDTO(username, token);
    }

    public UsernameDTO getUsernameByToken(String token) throws UsernameNotFoundException {
        if (!usernames.containsKey(token)) {
            throw new UsernameNotFoundException();
        }
        
        String username = usernames.get(token);
        
        return new UsernameDTO(username, token);
    }

    public void deleteUsername(String token) throws UsernameNotFoundException {
        if (!usernames.containsKey(token)) {
            throw new UsernameNotFoundException();
        }
        
        usernames.remove(token);
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }
}

