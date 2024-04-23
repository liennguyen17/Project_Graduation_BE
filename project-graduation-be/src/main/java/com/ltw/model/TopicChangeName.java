package com.ltw.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "topic_change_name", schema = "db_graduation")
public class TopicChangeName {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "old_name_topic")
    private String oldNameTopic;

    @Column(name = "name_topic_change")
    private String newNameTopic;

    @Column(name = "status")
    private String status;

    @Column(name = "note")
    private String note;

    @Column(name = "reason")
    private String reason;

    @Column(name = "create_at")
    private Timestamp createAt;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;
}
