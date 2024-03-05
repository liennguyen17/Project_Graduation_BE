package com.ltw.dto.request.news;

import com.ltw.constant.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.Year;

@Data
public class UpdateNewsRequest {
    @NotNull(message = Constants.ErrorMessageNewsValidation.ID_NOT_NULL)
    private Integer id;
    @NotBlank(message = Constants.ErrorMessageNewsValidation.TITLE_NOT_BLANK)
    @Size(min = 6, max = 255, message = Constants.ErrorMessageNewsValidation.TITLE_SIZE)
    private String title;
    @NotBlank(message = Constants.ErrorMessageNewsValidation.DESCRIPTION_NOT_BLANK)
    private String description;
    private String file;
    @NotNull(message = Constants.ErrorMessageNewsValidation.YEAR_NOT_NULL)
    private Year year;
    @NotBlank(message = Constants.ErrorMessageNewsValidation.SUBJECT_NOT_BLANK)
    private String subject;
}
