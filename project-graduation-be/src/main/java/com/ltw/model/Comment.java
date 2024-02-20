package com.ltw.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "comment", schema = "projectgraduation")
public class Comment {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "message")
    private String message;

    @Lob
    @Column(name = "create_by")
    private String createBy;

    @Lob
    @Column(name = "file")
    private String file;

    @Column(name = "create_at")
    private Instant createAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Topic_id", nullable = false)
    private Topic topic;

}