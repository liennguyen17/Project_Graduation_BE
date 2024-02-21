package com.ltw.dto.request.news;

import com.ltw.constant.Constants;
import com.ltw.repository.news.CustomNewsQuery;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
@Data
public class GetNewRequest extends CustomNewsQuery.NewsFilterParam {
    @Min(value = 0, message = Constants.ErrorMessageNewsValidation.START_SIZE)
    private Integer start = 0;
    @Range(min = 5, max = 50, message = Constants.ErrorMessageNewsValidation.LIMIT_SIZE)
    private Integer limit = 10;
}
