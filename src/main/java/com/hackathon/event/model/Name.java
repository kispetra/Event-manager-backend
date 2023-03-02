package com.hackathon.event.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="name")
@Getter
@Setter
public class Name {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "name_sequence")
    @SequenceGenerator(name="name_sequence", allocationSize = 1)
    private Long nameId;

    @OneToOne
    @JoinColumn(name="personalid")
    @JsonIgnore
    private Personal personal;

    @Column(name="firstname")
    private String firstName;

    @Column(name="lastname")
    private String lastName;
}
