package com.ltw.dto.request.notification;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateNotificationRequest {
    @NotNull
    private Integer id;
    @NotBlank(message = "Tiêu đề thông báo không được để trống!")
    @Size(min = 6, max = 255, message = "Tiêu đề thông báo phải có ít nhất 6, nhiều nhất 255 kí tự!")
    private String title;
    @NotBlank(message = "Nội dung không được để trống!")
    private String description;
    private String file;
    private String isRead;
}
