package com.ltw.dto.request.notification;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class DeleteNotificationRequest {
    @NotEmpty(message = "Vui lòng truyền id thông báo cần xóa!")
    private List<Integer> ids;
}
