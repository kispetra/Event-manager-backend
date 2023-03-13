package com.hackathon.event.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="progress")
@Getter
@Setter
public class Progress {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "progress_sequence")
    @SequenceGenerator(name="progress_sequence", allocationSize = 1)
    @Column(name="progress_id")
    private Long id;

    @Column
    private Integer week;

    @JsonManagedReference
    @OneToMany(mappedBy="progress")
    private List<Flow> flow;

    @ManyToOne
    @JoinColumn(name="participant_id")
    @JsonBackReference
    private Participant participant;




}
