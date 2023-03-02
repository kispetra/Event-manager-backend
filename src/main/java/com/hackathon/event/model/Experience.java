package com.hackathon.event.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hackathon.event.model.enumeration.Skills;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="experience")
@Getter
@Setter
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "experience_sequence")
    @SequenceGenerator(name="experience_sequence", allocationSize = 1)
    private Long experienceId;

    @OneToOne
    @JoinColumn(name="registrationid")
    @JsonIgnore
    private Registration registration;

    private Integer years;
    @OneToMany(mappedBy = "experience")
    private List<Skill> skills;
    @Column(name="repositoryurl")
    private String repositoryUrl;

    private String summary;
}
