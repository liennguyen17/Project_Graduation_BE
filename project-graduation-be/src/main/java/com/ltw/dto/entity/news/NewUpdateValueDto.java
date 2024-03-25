package com.ltw.dto.entity.news;

import lombok.Data;

import java.sql.Timestamp;
import java.time.Year;
@Data
public class NewUpdateValueDto {
    private Integer id;
    private String title;
    private String description;
    private String file;
    private Year year;
    private String subject;
    private String content;
    private String image;
}
