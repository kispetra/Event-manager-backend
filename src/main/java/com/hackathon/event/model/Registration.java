package com.hackathon.event.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="registrations")
@Getter
@Setter
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "registration_sequence")
    @SequenceGenerator(name="registration_sequence", allocationSize = 1)
    @Column(name="registration_id")
    private Long registrationId;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="event_id")
    private Event event;

    @OneToOne(mappedBy = "registration", cascade = CascadeType.ALL)
    private Personal personal;

    @OneToOne(mappedBy = "registration", cascade = CascadeType.ALL)
    private Experience experience;

    @Column
    private Integer score;

    @Column
    private String motivation;

    @Column(name="preferredos")
    private String preferredOs;
}
