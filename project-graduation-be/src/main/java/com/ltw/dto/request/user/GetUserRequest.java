package com.ltw.dto.request.user;

import com.ltw.constant.Constants;
import com.ltw.repository.users.CustomUserQuery;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
@Data
public class GetUserRequest extends CustomUserQuery.UserFilterParam {
    @Min(value = 0, message = Constants.ErrorMessageUserValidation.START_SIZE)
    private Integer start = 0;
    @Range(min = 5, max = 50, message = Constants.ErrorMessageUserValidation.LIMIT_SIZE)
    private Integer limit = 10;
}
