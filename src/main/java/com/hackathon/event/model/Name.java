package com.hackathon.event.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="names")
@Getter
@Setter
public class Name {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "name_sequence")
    @SequenceGenerator(name="name_sequence", allocationSize = 1)
    private Long nameId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="personal_id")
    @JsonIgnore
    private Personal personal;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;
}
