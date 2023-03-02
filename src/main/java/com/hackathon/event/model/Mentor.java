package com.hackathon.event.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="mentors")
@Getter
@Setter
public class Mentor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mentor_sequence")
    @SequenceGenerator(name="mentor_sequence", allocationSize = 1)
    @Column(name = "mentor_id")
    private Long mentorId;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
    @Column
    private String email;

}
