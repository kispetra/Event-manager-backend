package com.hackathon.event.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="experiences")
@Getter
@Setter
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "experience_sequence")
    @SequenceGenerator(name="experience_sequence", allocationSize = 1)
    private Long experienceId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="registration_id")
    @JsonIgnore
    private Registration registration;

    @Column(name = "years")
    private Integer years;

    @OneToMany(mappedBy = "experience")
    private List<Skill> skills;

    @Column(name="repositoryurl")
    private String repositoryUrl;

    @Column(name = "summary")
    private String summary;
}
