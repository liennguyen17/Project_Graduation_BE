package com.ltw.dto.entity.users;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ltw.constant.Constants;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class UserDTO {
    private Integer id;
    private String name;
    private String username;
    @JsonFormat(pattern = Constants.DateTimeFormatConstant.DATE_FORMAT, timezone = Constants.DateTimeFormatConstant.TIME_ZONE)
    private Date dob;
    private String address;
    private String email;
    private String phone;
    private String subject;
    @JsonFormat(pattern = Constants.DateTimeFormatConstant.DATE_FORMAT, timezone = Constants.DateTimeFormatConstant.TIME_ZONE)
    private Timestamp createAt;
    @JsonFormat(pattern = Constants.DateTimeFormatConstant.DATE_FORMAT, timezone = Constants.DateTimeFormatConstant.TIME_ZONE)
    private Timestamp updateAt;
    private Integer roleId;
}
