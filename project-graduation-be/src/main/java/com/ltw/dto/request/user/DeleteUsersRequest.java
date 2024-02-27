package com.ltw.dto.request.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class DeleteUsersRequest {
    @NotEmpty(message = "Vui lòng truyền id người dùng cần xóa!")
    private List<Integer> ids;
}