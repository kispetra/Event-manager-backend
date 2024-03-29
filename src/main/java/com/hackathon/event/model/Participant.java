package com.hackathon.event.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="participants")
@Getter
@Setter
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "participant_sequence")
    @SequenceGenerator(name="participant_sequence", allocationSize = 1)
    @Column(name = "participant_id")
    private Long participantId;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="user_id")
    private AppUser appUser;
    @Column
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="registration_id")
    @JsonIgnore
    private Registration registration;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="event_id")
    @JsonIgnore
    private Event event;

    @OneToMany(mappedBy = "participant")
    @JsonManagedReference
    private List<Progress> progress;



}
