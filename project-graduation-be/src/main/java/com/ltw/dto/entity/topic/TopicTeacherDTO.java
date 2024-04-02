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
}
