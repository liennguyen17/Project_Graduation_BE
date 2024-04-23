package com.ltw.dto.request.topicChangeName;

import com.ltw.model.Topic;
import lombok.Data;

import java.sql.Timestamp;
@Data
public class CreateChangeTopicRequest {
    private String newNameTopic;
//    private String topicOld;
    private String status = "Chờ phê duyệt";
    private String reason;
//    private Timestamp createAt;
//    private Integer topic;
}
