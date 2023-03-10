package com.hackathon.event.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="participants")
@Getter
@Setter
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "participant_sequence")
    @SequenceGenerator(name="participant_sequence", allocationSize = 1)
    @Column(name = "participant_id")
    private Long participantId;

    @Column
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="registration_id")
    @JsonIgnore
    private Registration registration;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "team_id")
    private Team team;

}
