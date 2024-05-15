package com.ltw.dto.request.comment;

import lombok.Data;

@Data
public class CreateCommentRequest {
    private String message;
    private String file;
    private String descriptionFile;
    private Integer topic;
}
