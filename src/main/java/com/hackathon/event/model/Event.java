package com.hackathon.event.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="events")
@Getter @Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_sequence")
    @SequenceGenerator(name = "event_sequence", allocationSize = 1)
    @Column(name = "event_id")
    private Long eventId;
    @Column
    private String name;
    @OneToMany(mappedBy = "event")
    @JsonManagedReference
    private List<Team> teams;
    @Column(name = "maxparticipants")
    private Integer maxParticipants;
    @Column(name = "registrationsnotbefore")
    private Date registrationsNotBefore;
    @Column(name = "registrationsnotafter")
    private Date registrationsNotAfter;
    @Column(name = "confirmationnotafter")
    private Date confirmationNotAfter;
    @Column(name = "startdate")
    private Date startDate;
    @Column
    private int weeks;
    @Column(name = "sent_invites")
    private Boolean invitesSent;
    @Column
    @JsonManagedReference
    @OneToMany(mappedBy = "event")
    private List<Registration> registrations;

}
