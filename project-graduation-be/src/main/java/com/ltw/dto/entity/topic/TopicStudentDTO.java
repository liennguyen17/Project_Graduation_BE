package com.ltw.dto.entity.topic;

import com.ltw.dto.entity.users.UserDTO1;
import com.ltw.dto.entity.users.UserStudentDTO;
import com.ltw.dto.entity.users.UserTeacherDTO;
import lombok.Data;

@Data
public class TopicStudentDTO {
    private Integer id;
    private String nameTopic;
    private UserStudentDTO student;
    private UserTeacherDTO teacher;
}
