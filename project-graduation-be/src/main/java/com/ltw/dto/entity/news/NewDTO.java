package com.ltw.dto.entity.news;

import lombok.Data;

import java.sql.Timestamp;
import java.time.Year;

@Data
public class NewDTO {
    private Integer id;
    private String title;
    private String description;
    private String file;
    private Timestamp createAt;
    private Timestamp updateAt;
    private Year year;
    private String subject;
}
