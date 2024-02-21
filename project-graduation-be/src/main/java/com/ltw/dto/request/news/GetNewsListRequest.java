package com.ltw.dto.request.news;

import com.ltw.constant.Constants;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class GetNewsListRequest {
    @Min(value = 0, message = "Số trang bắt đầu từ 0!")
    private Integer start = 0;
    @Range(min = 5, max = 50, message = "Số lượng tin tức trong một trang là từ 5 đến 50 bài viết!")
    private Integer limit = 5;
    private Boolean getAll = false;
}
