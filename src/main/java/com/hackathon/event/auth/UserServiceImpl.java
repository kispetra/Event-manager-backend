package com.hackathon.event.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements  UserService{
        private final UserRepo userRepo;
        private final PasswordEncoder passwordEncoder;
        @Transactional
        @Override
        public String addUser(appUserDto userDto) {

            appUser appUser = new appUser(

                    userDto.getUserid(),
                    userDto.getUsername(),
                    userDto.getEmail(),

                    this.passwordEncoder.encode(userDto.getPassword())
            );

            try {
                userRepo.save(appUser);
            } catch (Exception e) {
                e.printStackTrace(); // Ili koristite logger za ispisivanje informacija o grešci.
            }

            return appUser.getUsername();
        }
        appUserDto appUserDto;

        @Override
        public LoginMessage  loginUser(LoginDto loginDto) {
            String msg = "";
            appUser user1 = userRepo.findByEmail(loginDto.getEmail());
            if (user1 != null) {
                String password = loginDto.getPassword();
                String encodedPassword = user1.getPassword();
                Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
                if (isPwdRight) {
                    Optional<appUser> employee = userRepo.findOneByEmailAndPassword(loginDto.getEmail(), encodedPassword);
                    if (employee.isPresent()) {
                        return new LoginMessage("Login Success", true);
                    } else {
                        return new LoginMessage("Login Failed", false);
                    }
                } else {

                    return new LoginMessage("password Not Match", false);
                }
            }else {
                return new LoginMessage("Email not exits", false);
            }


        }

}

