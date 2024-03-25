package com.ltw.dto.entity.topic;

import com.ltw.dto.entity.users.UserDTO1;
import lombok.Data;

@Data
public class TopicDTO1 {
    private Integer id;
    private UserDTO1 student;
    private UserDTO1 teacher;
    private String status;
    private String semester;
    private String nameTopic;
    private String departmentManagement;
    private String nameInternshipFacility;
    private String menterInternshipFacility;
    private String phoneInstructorInternshipFacility;
}
