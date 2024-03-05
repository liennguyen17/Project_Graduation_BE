package com.ltw.dto.request.notification;

import com.ltw.constant.Constants;
import com.ltw.repository.notification.CustomNotificationQuery;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
@Data
public class GetNotificationRequest extends CustomNotificationQuery.NotificationFilterParam {
    @Min(value = 0, message = Constants.ErrorMessageNotificationValidation.START_SIZE)
    private Integer start = 0;
    @Range(min = 5, max = 50, message = Constants.ErrorMessageNotificationValidation.LIMIT_SIZE)
    private Integer limit = 10;
}
