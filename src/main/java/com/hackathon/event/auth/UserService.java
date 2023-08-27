package com.hackathon.event.auth;


public interface UserService {
        String addUser(appUserDto userDto);

        LoginMessage loginUser(LoginDto loginDto);

}

