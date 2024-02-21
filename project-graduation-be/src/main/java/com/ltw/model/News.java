package com.ltw.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Year;

@Getter
@Setter
@Entity
@Table(name = "news", schema = "projectgraduation")
public class News {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    @Size(max = 255)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "file")
    private String file;

    @Column(name = "create_at")
    private Timestamp createAt;

    @Column(name = "update_at")
    private Timestamp updateAt;

    @Column(name = "year")
    private Year year;

    @Column(name = "subject")
    private String subject;

}