package com.ltw.dto.entity.notification;

import com.ltw.model.User;
import lombok.Data;

@Data
public class NotificationUpdateValueDto {
    private Integer id;
    private User user;
    private String title;
    private String description;
    private String content;
    private String file;
    private String isRead;
}
