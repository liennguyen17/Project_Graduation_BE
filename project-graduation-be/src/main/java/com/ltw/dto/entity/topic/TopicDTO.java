package com.ltw.dto.entity.topic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ltw.constant.Constants;
import com.ltw.model.User;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class TopicDTO {
    private User student;
    private User teacher;
    private String nameTopic;
    private String status;
    private Float instructor;
    private Float reviewer;
    private Float boardMembers1;
    private Float boardMembers2;
    private Float boardMembers3;
    @JsonFormat(pattern = Constants.DateTimeFormatConstant.DATE_FORMAT, timezone = Constants.DateTimeFormatConstant.TIME_ZONE)
    private Timestamp createAt;
    @JsonFormat(pattern = Constants.DateTimeFormatConstant.DATE_FORMAT, timezone = Constants.DateTimeFormatConstant.TIME_ZONE)
    private Timestamp updateAt;
}
