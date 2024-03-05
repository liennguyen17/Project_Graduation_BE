package com.ltw.dto.request.topic;

import com.ltw.model.User;
import lombok.Data;

@Data
public class CreateTopicRequest {
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
