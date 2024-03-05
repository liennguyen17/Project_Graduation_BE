package com.ltw.service.mapper;

import com.ltw.dto.entity.notification.NotificationUpdateValueDto;
import com.ltw.model.Notification;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface NotificationUpdateMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "createAt", ignore = true),
            @Mapping(target = "updateAt", ignore = true)
    })
    void updateNotificationFromDto(NotificationUpdateValueDto dto, @MappingTarget Notification entity);
}
