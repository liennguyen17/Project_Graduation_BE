package com.ltw.dto.request.topicChangeName;

import lombok.Data;

@Data
public class UpdateChangeNameTopicRequest {
    private Integer id;
    private String status;
    private String note;
    private Integer topic;
}
