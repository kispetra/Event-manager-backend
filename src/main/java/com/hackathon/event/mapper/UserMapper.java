package com.hackathon.event.mapper;

import com.hackathon.event.dto.SignUpDto;
import com.hackathon.event.dto.UserDto;
import com.hackathon.event.model.AppUser;
import com.hackathon.event.model.Event;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {
    public UserDto toDtoUser(AppUser user){
            UserDto userDto=new UserDto();
            userDto.setUserid(user.getUserid());
            userDto.setFirstname(user.getFirstname());
            userDto.setLastname(user.getLastname());
            userDto.setLogin(user.getLogin());
            userDto.setEmail(user.getEmail());
            userDto.setAddress(user.getAddress());
            userDto.setHouseNumber(user.getHouseNumber());
            userDto.setCountry(user.getCountry());
            return  userDto;
    }
    @Mapping(target="password", ignore=true)
    public AppUser signUpToUser(SignUpDto signUpDto){
            AppUser appUser=new AppUser();
            appUser.setFirstname(signUpDto.getFirstname());
            appUser.setLastname(signUpDto.getLastname());
            appUser.setLogin(signUpDto.getLogin());
            appUser.setEmail(signUpDto.getEmail());

            appUser.setAddress(signUpDto.getAddress());
            appUser.setHouseNumber(signUpDto.getHouseNumber());
            appUser.setCountry(signUpDto.getCountry());
            return appUser;
    }

}
