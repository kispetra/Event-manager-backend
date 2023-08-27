package com.hackathon.event.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
public class UserController {

        private final UserService userService;
        @PostMapping(path = "/event/save")
        public String saveUser(@RequestBody appUserDto userDto)
        {
            return  userService.addUser(userDto);
        }
        @PostMapping(path = "/event/login")
        public ResponseEntity<?> loginUser(@RequestBody LoginDto loginDto)
        {
            LoginMessage loginResponse = userService.loginUser(loginDto);
            return ResponseEntity.ok(loginResponse);
        }
}

