package com.ltw.dto.request.topic;

import lombok.Data;

@Data
public class UpdateTopicRequest {
    private Integer id;
    private Integer studentId;
    private Integer teacherId;
    private String nameTopic;
    private String semester;
    private String status;
    private String departmentManagement;
    private String nameInternshipFacility;
    private String menterInternshipFacility;
    private String phoneInstructorInternshipFacility;
    private Float instructor;
    private Float reviewer;
    private Float boardMembers1;
    private Float boardMembers2;
    private Float boardMembers3;
    private Float scoresInternshipFacility;
}
