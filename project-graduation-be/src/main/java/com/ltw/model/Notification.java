package com.ltw.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "notification", schema = "projectgraduation")
public class Notification {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "User_id", nullable = false)
    private User user;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "file")
    private String file;

    @Lob
    @Column(name = "is_read")
    private String isRead;

    @Column(name = "update_at")
    private Instant updateAt;

    @Column(name = "create_at")
    private Instant createAt;

}