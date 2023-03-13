package com.hackathon.event.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hackathon.event.model.enumeration.StatusType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="flow")
@Getter
@Setter
public class Flow {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flow_sequence")
    @SequenceGenerator(name="flow_sequence", allocationSize = 1)
    @Column(name="flow_id")
    private Long flow_id;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "progress_id")
    private Progress progress;
    @Column
    private StatusType status;
    @Column
    private String comment;
}
