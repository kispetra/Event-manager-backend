package com.hackathon.event.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Entity
@Table(name="events")
@Getter @Setter
@JsonInclude(NON_DEFAULT)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_sequence")
    @SequenceGenerator(name = "event_sequence", allocationSize = 1)
    @Column(name = "event_id")
    private Long eventId;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="user_id")
    private AppUser appUser;
    @Column
    private String name;
    @Column
    private String description;
    @Column(name = "maxparticipants")
    private Integer maxParticipants;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+01:00")
    @Column(name = "registrationsnotbefore")
    private Date registrationsNotBefore;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+01:00")
    @Column(name = "registrationsnotafter")
    private Date registrationsNotAfter;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+01:00")
    @Column(name = "confirmationnotafter")
    private Date confirmationNotAfter;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+01:00")
    @Column(name = "startdate")
    private Date startDate;
    @Column
    private int weeks;
    @Column
    @JsonManagedReference
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Skill> skills;
    @Column(name = "sent_invites")
    private Boolean invitesSent;
    @Column
    @JsonManagedReference
    @OneToMany(mappedBy = "event")
    private List<Registration> registrations;
    @Column
    @JsonManagedReference
    @OneToMany(mappedBy = "event")
    private List<Participant> participants;

}
