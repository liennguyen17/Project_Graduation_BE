package com.ltw.dto.request.topic;

import com.ltw.constant.Constants;
import com.ltw.repository.topic.CustomTopicQuery;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class GetTopicRequest extends CustomTopicQuery.TopicFilterParam {
    @Min(value = 0, message = Constants.ErrorMessageTopicValidation.START_SIZE)
    private Integer start = 0;
    @Range(min = 5, max = 50, message = Constants.ErrorMessageTopicValidation.LIMIT_SIZE)
    private Integer limit = 10;
}
