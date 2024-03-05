package com.ltw.dto.request.topic;

import lombok.Data;

@Data
public class UpdateTopicRequest {
    private Integer id;
    private Integer studentId;
    private Integer teacherId;
    private String nameTopic;
    private String status;
    private Float instructor;
    private Float reviewer;
    private Float boardMembers1;
    private Float boardMembers2;
    private Float boardMembers3;
}
