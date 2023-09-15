package com.hackathon.event.controller;


import com.hackathon.event.config.UserAuthProvider;
import com.hackathon.event.dto.*;
import com.hackathon.event.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final UserAuthProvider userAuthProvider;
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentialsDto){
        UserDto user=userService.login(credentialsDto);
        user.setToken(userAuthProvider.createToken(user));
        return ResponseEntity.ok(user);
    }
     @PostMapping("/register")
    public ResponseEntity<UserDto> register (@RequestBody SignUpDto signUpDto){
        UserDto user= userService.register(signUpDto);
        return ResponseEntity.created(URI.create("/users" + user.getUserid())).body(user);
     }
}

