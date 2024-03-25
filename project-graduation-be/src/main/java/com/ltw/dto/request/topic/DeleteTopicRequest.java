package com.ltw.dto.request.topic;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class DeleteTopicRequest {
    @NotEmpty(message = "Vui lòng truyền id thông báo cần xóa!")
    private List<Integer> ids;

}
