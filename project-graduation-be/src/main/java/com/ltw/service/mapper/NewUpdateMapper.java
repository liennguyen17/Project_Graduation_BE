package com.ltw.service.mapper;

import com.ltw.dto.entity.news.NewUpdateValueDto;
import com.ltw.model.News;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface NewUpdateMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(target = "createAt",ignore = true),
            @Mapping(target = "updateAt",ignore = true)
    })
    void updateNewFromDto(NewUpdateValueDto dto, @MappingTarget News entity);
}
