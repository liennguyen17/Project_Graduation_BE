package com.ltw.dto.request.topic;

import com.ltw.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CreateTopicRequest {
    private Integer student;
    private Integer teacher;
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
