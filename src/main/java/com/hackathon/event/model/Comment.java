package com.hackathon.event.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="comments")
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_sequence")
    @SequenceGenerator(name="comment_sequence", allocationSize = 1)
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne
    @JoinColumn(name="registration_id")
    @JsonIgnore
    private Registration registration;

    @Column(name = "score")
    private String score;
    @Column(name = "comment")
    private String comment;


}
