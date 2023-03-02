package com.hackathon.event.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="registration")
@Getter
@Setter
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "registration_sequence")
    @SequenceGenerator(name="registration_sequence", allocationSize = 1)
    private Long registrationId;

    @OneToOne(mappedBy = "registration")
    private Personal personal;

    @OneToOne(mappedBy = "registration")
    private Experience experience;

    @OneToOne(mappedBy = "registration")
    private Comments comments;

    private Integer score;
    private String motivation;
    @Column(name="preferredos")
    private String preferredOs;
}
