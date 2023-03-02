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
    @Column(name="personal_id")
    private Long personalId;

    @OneToOne
    @JoinColumn(name="registration_id")
    @JsonIgnore
    private Registration registration;

    @OneToOne(mappedBy = "personal")
    private Name name;
    @Column
    private String email;
    @Column
    private String phone;
    @OneToOne(mappedBy = "personal")
    private Education education;
    @Column
    private String summary;

}
