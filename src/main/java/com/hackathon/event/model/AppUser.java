package com.hackathon.event.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="appuser")
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appuser_sequence")
        @SequenceGenerator(name = "appuser_sequence", allocationSize = 1)
        @Column(name = "user_id")
        private Long userid;
        @Column(name="username")
        private String login;
        @Column(name="firstname")
        private String firstname;
        @Column(name="lastname")
        private String lastname;
        @Column(name="address")
        private String address;
        @Column(name="housenumber")
        private String houseNumber;
        @Column(name="Country")
        private String country;
        @Column(name="email", length = 255)
        private String email;
        @Column(name="password", length = 255)
        private String password;
        @Column
        @JsonManagedReference
        @OneToMany(mappedBy = "appUser")
        private List<Event> events;
        @Column
        @JsonManagedReference
        @OneToMany(mappedBy = "appUser")
        private List<Registration> registrations;
        @Column
        @JsonManagedReference
        @OneToMany(mappedBy = "appUser")
        private List<Participant> participants;


}
