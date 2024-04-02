package com.ltw.dto.entity.notification;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ltw.constant.Constants;
import com.ltw.model.User;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class NotificationDTO {
    private Integer id;
    private User user;
    private String title;
    private String description;
    private String content;
    private String file;
    private String isRead;
    @JsonFormat(pattern = Constants.DateTimeFormatConstant.DATE_FORMAT, timezone = Constants.DateTimeFormatConstant.TIME_ZONE)
    private java.sql.Timestamp createAt;
    @JsonFormat(pattern = Constants.DateTimeFormatConstant.DATE_FORMAT, timezone = Constants.DateTimeFormatConstant.TIME_ZONE)
    private Timestamp updateAt;

}
