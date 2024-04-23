package com.ltw.dto.entity.topicChangeName;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ltw.constant.Constants;
import com.ltw.dto.entity.topic.TopicDTO1;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class TopicChangeNameDTO {
    private Integer id;
    private TopicDTO1 topic;
    private String oldNameTopic;
    private String newNameTopic;
    private String status;
    private String note;
    private String reason;
    @JsonFormat(pattern = Constants.DateTimeFormatConstant.DATE_FORMAT, timezone = Constants.DateTimeFormatConstant.TIME_ZONE)
    private Timestamp createAt;
}
