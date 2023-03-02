package com.hackathon.event.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="comments")
@Getter
@Setter
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_sequence")
    @SequenceGenerator(name="comment_sequence", allocationSize = 1)
    private Long commentId;

    @OneToOne
    @JoinColumn(name="registrationid")
    @JsonIgnore
    private Registration registration;

    private String score;
    private String comment;


}
