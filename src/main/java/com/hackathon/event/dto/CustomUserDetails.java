package com.hackathon.event.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User {
    private final String firstName;
    private final String lastName;
    private final String address;


    public CustomUserDetails(String username, String password,
                             String firstName, String lastName, String address) {
        super(username, password,  new ArrayList<>());
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        // Inicijalizirajte ostale atribute
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }
    // Dodajte gettere za ostale atribute
}

