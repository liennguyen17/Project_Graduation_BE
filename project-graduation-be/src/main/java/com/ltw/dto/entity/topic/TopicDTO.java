package com.ltw.dto.entity.topic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ltw.constant.Constants;
import com.ltw.dto.entity.users.UserDTO1;
import com.ltw.model.User;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class TopicDTO {
    private Integer id;
    private UserDTO1 student;
    private UserDTO1 teacher;
    private String nameTopic;
    private String status;
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
    @JsonFormat(pattern = Constants.DateTimeFormatConstant.DATE_FORMAT, timezone = Constants.DateTimeFormatConstant.TIME_ZONE)
    private Timestamp createAt;
    @JsonFormat(pattern = Constants.DateTimeFormatConstant.DATE_FORMAT, timezone = Constants.DateTimeFormatConstant.TIME_ZONE)
    private Timestamp updateAt;
}
