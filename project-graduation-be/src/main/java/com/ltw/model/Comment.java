package com.ltw.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "comment", schema = "db_graduation")
public class Comment {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "message")
    @Size(max = 5000)
    private String message;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "file")
    private String file;

    @Column(name = "description_file")
    private String descriptionFile;

    @Column(name = "create_at")
    private Timestamp createAt;

    @ManyToOne
    @JoinColumn(name = "Topic_id")
//    @JoinColumn(name = "Topic_id", nullable = false)
    private Topic topic;

}