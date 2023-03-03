package com.hackathon.event.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="educations")
@Getter
@Setter
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "education_sequence")
    @SequenceGenerator(name="education_sequence", allocationSize = 1)
    private Long educationId;

    @OneToOne
    @JoinColumn(name="personal_id")
    @JsonIgnore
    private Personal personal;

    @Column(name = "faculty")
    private String faculty;

    @Column(name = "year")
    private Integer year;
}
