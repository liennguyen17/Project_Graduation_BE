package com.ltw.dto.entity.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ltw.constant.Constants;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class CommentDTO {
    private String message;
    private String createBy;
    private String file;
    private Integer topicId;
    @JsonFormat(pattern = Constants.DateTimeFormatConstant.DATE_FORMAT, timezone = Constants.DateTimeFormatConstant.TIME_ZONE)
    private Timestamp createAt;
}
