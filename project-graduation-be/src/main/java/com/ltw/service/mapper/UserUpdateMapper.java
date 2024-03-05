package com.ltw.service.mapper;

import com.ltw.constant.Constants;
import com.ltw.dto.entity.users.UserUpdateValueDto;
import com.ltw.model.User;
import com.ltw.utils.DateUtils;
import org.mapstruct.*;

import java.text.ParseException;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface UserUpdateMapper {
//    @Named("convertDateFromString")
//    default Date converDateFromString(String dateStr) throws ParseException {
//        return DateUtils.convertDateFromString(dateStr, Constants.DateTimeFormatConstant.DATE_FORMAT);
//    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(target = "dob", source = "dob", dateFormat = "dd/MM/yyyy"),
//            @Mapping(target = "roleId", ignore = true),
            @Mapping(target = "createAt", ignore = true),
            @Mapping(target = "updateAt", ignore = true),
//            @Mapping(target = "password", ignore = true)
            })
    void updateUserFromDto(UserUpdateValueDto dto, @MappingTarget User entity);
}
