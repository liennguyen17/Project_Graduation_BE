package com.ltw.dto.request.notification;

import com.ltw.constant.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateNotificationRequest {
    @NotBlank(message = Constants.ErrorMessageNotificationValidation.TITLE_NOT_BLANK)
    @Size(min = 6, max = 255, message = Constants.ErrorMessageNotificationValidation.TITLE_SIZE)
    private String title;
    @NotBlank(message = Constants.ErrorMessageNotificationValidation.DESCRIPTION_NOT_BLANK)
    private String description;
    private String file;
    private String isRead;
    //    @NotNull(message = )
    private Integer userId;
}
