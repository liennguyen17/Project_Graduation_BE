package com.ltw.dto.entity.topic;

import com.ltw.dto.entity.users.UserDTO1;
import com.ltw.dto.entity.users.UserTeacherDTO;
import lombok.Data;

@Data
public class TopicTeacherDTO {
    private Integer id;
    private String nameTopic;
    private UserDTO1 student;
    private UserTeacherDTO teacher;
    private String status;
    private String note;
    private String semester;
    private String departmentManagement;
    private String nameInternshipFacility;
    private String menterInternshipFacility;
    private String phoneInstructorInternshipFacility;
    private Float scoresInternshipFacility;
    private Float instructor;
    private Float reviewer;
    private Float boardMembers1;
    private Float boardMembers2;
    private Float boardMembers3;
    private Float result;
    private String success;
}
