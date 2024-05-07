package com.ltw.dto.entity.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ltw.constant.Constants;
import com.ltw.dto.entity.topic.TopicDTO1;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class CommentDTO {
    private Integer id;
    private String message;
    private String createBy;
    private String file;
    private String descriptionFile;
    private TopicDTO1 topic;
    @JsonFormat(pattern = Constants.DateTimeFormatConstant.DATE_FORMAT, timezone = Constants.DateTimeFormatConstant.TIME_ZONE)
    private Timestamp createAt;
}
