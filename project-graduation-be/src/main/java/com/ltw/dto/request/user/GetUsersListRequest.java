package com.ltw.dto.request.user;

import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
@Data
public class GetUsersListRequest {
    @Min(value = 0, message = "Số trang bắt đầu từ 0!")
    private Integer start = 0;
    @Range(min = 5, max = 50, message = "Số lượng người dùng trong một trang là từ 5 đến 50 người!")
    private Integer limit = 5;
    private Boolean getAll = false;
}
