package com.hackathon.event.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="registrations")
@Getter
@Setter
public class Registration {
    @Id
    @Column(name = "registration_uuid")
    private UUID registrationUUID;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="event_id")
    private Event event;

    @OneToOne(mappedBy = "registration", cascade = CascadeType.ALL)
    private Personal personal;

    @OneToOne(mappedBy = "registration", cascade = CascadeType.ALL)
    private Experience experience;


    @OneToOne(mappedBy = "registration", cascade = CascadeType.ALL)
    private Participant participant;

    @Column
    private Integer score;

    @OneToMany(mappedBy = "registration")
    private List<Comment> comment;

    @Column
    private String motivation;

    @Column(name="preferredos")
    private String preferredOs;
    @Column
    private Boolean participation;
    @Column
    private Boolean kickoff;
    @Column
    private String tshirt;
    @Column
    private String gitlab;
}
