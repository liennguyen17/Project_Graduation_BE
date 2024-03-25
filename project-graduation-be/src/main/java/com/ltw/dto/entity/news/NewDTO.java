package com.ltw.dto.entity.news;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ltw.constant.Constants;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Year;

@Data
public class NewDTO {
    private Integer id;
    private String title;
    private String description;
    private String file;
    private String content;
    private String subject;
    private String image;
    private Year year;
    @JsonFormat(pattern = Constants.DateTimeFormatConstant.DATE_FORMAT, timezone = Constants.DateTimeFormatConstant.TIME_ZONE)
    private Timestamp createAt;
    @JsonFormat(pattern = Constants.DateTimeFormatConstant.DATE_FORMAT, timezone = Constants.DateTimeFormatConstant.TIME_ZONE)
    private Timestamp updateAt;

}
