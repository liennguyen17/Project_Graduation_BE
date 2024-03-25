package com.ltw.dto.request.comment;

import lombok.Data;

@Data
public class CreateCommentRequest {
    private String message;
//    private String createBy;
    private String file;
    private Integer topicId;
}
