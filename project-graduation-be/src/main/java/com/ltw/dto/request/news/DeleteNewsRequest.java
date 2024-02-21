package com.ltw.dto.request.news;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class DeleteNewsRequest {
    @NotEmpty(message = "Vui lòng truyền id tin tức cần xóa!")
    private List<Integer> ids;
}
