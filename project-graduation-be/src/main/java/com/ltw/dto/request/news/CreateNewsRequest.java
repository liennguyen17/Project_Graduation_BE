package com.ltw.dto.request.news;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.Year;
@Data
public class CreateNewsRequest {
    @NotBlank(message = "Tiêu đề tin tức không được để trống!")
    @Size(min = 6, max = 255, message = "Tiêu đề tin tức phải có ít nhất 6, nhiều nhất 255 kí tự!")
    private String title;
    @NotBlank(message = "Nội dung không được để trống!")
    private String description;
    private String file;
    private Year year;
    private String subject;
}
