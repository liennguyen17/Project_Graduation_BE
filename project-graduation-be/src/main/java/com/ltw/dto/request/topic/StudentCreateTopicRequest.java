package com.ltw.dto.request.topic;

import lombok.Data;

@Data
public class StudentCreateTopicRequest {
    private Integer teacher;
    private String nameTopic;
    private String semester;
    private String status;
    private String departmentManagement;
    private String nameInternshipFacility;
    private String menterInternshipFacility;
    private String phoneInstructorInternshipFacility;
}
