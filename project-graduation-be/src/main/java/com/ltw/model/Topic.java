package com.ltw.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "topic", schema = "db_graduation")
public class Topic {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "teacher_id", nullable = false)
    private User teacher;

    @Column(name = "name_topic")
    private String nameTopic;
    @Column(name = "semester")
    private String semester;

    @Column(name = "status")
    private String status;

    @Column(name = "name_internship_facility")
    private String nameInternshipFacility;

    @Column(name = "menter_internship_facility")
    private String menterInternshipFacility;

    @Column(name = "phone_instructor_internship_facility")
    private String phoneInstructorInternshipFacility;

    @Column(name = "department_management")
    private String departmentManagement;

    @Column(name = "instructor")
    private Float instructor;

    @Column(name = "reviewer")
    private Float reviewer;

    @Column(name = "board_members_1")
    private Float boardMembers1;

    @Column(name = "board_members_2")
    private Float boardMembers2;

    @Column(name = "board_members_3")
    private Float boardMembers3;

    @Column(name = "scores_internship_facility")
    private Float scoresInternshipFacility;

    @Column(name = "result")
    private Float result;

    @Column(name = "note")
    private String note;

    @Column(name = "create_at")
    private Timestamp createAt;

    @Column(name = "update_at")
    private Timestamp updateAt;

    @OneToMany(mappedBy = "topic")
    private List<Comment> comments = new ArrayList<>();
}