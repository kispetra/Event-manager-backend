package com.hackathon.event.service;

import com.hackathon.event.dto.CredentialsDto;
import com.hackathon.event.dto.SignUpDto;
import com.hackathon.event.dto.UserDto;
import com.hackathon.event.exceptions.AppException;
import com.hackathon.event.mapper.UserMapper;
import com.hackathon.event.model.AppUser;
import com.hackathon.event.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.CharBuffer;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

   /* @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }*/
    public UserDto login(CredentialsDto credentialsDto) {
        AppUser user = userRepo.findByLogin(credentialsDto.getLogin())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())) {
            System.out.println("Password matches!");
            return userMapper.toDtoUser(user);
        } else {
            System.out.println("Password does not match!");
            throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
        }


    }
    public UserDto register(SignUpDto userDto) {
        Optional<AppUser> optionalUser = userRepo.findByLogin(userDto.getLogin());

        if (optionalUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        AppUser user = userMapper.signUpToUser(userDto);
        String encodedPassword = passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword()));
        System.out.println("Encoded Password: " + encodedPassword);
        user.setPassword(encodedPassword);


        AppUser savedUser = userRepo.save(user);
        Long userid = savedUser.getUserid();
        return userMapper.toDtoUser(savedUser);
    }

    public UserDto findByUsername(String login) {
        AppUser user = userRepo.findByLogin(login)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.toDtoUser(user);
    }

}
