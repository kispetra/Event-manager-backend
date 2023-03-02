package com.hackathon.event.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="personal")
@Getter
@Setter
public class Personal {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personal_sequence")
    @SequenceGenerator(name="personal_sequence", allocationSize = 1)
    private Long personalId;

    @OneToOne
    @JoinColumn(name="registrationid")
    @JsonIgnore
    private Registration registration;

    @OneToOne(mappedBy = "personal")
    private Name name;

    private String email;
    private String phone;

    @OneToOne(mappedBy = "personal")
    private Education education;
    private String summary;

}
