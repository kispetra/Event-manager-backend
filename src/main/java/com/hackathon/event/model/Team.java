package com.hackathon.event.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name="teams")
@Getter
@Setter
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_sequence")
    @SequenceGenerator(name="team_sequence", allocationSize = 1)
    @Column(name = "team_id")
    private Long teamId;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "event_id")
    private Event event;
    @Column
    private String name;

    @JsonManagedReference
    @OneToMany(mappedBy="team")
    private List<Mentor> mentors;


}
