package com.hackathon.event.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="skills")
@Getter
@Setter
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "skill_sequence")
    @SequenceGenerator(name="skill_sequence", allocationSize = 1)
    @Column(name = "skill_id")
    private Long skillId;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="event_id")
    private Event event;
    @ManyToOne
    @JoinColumn(name="experience_id")
    @JsonBackReference
    private Experience experience;

    @Column(name = "name")
    private String name;
    @Column(name="points")
    private Integer points;
}
