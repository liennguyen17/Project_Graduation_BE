package com.ltw.service.mapper;

import com.ltw.dto.entity.masterData.MasterDataUpdateValueDto;
import com.ltw.model.MasterData;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MasterDataUpdateMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
//            @Mapping(target = "type", ignore = true),
//            @Mapping(target = "code", ignore = true),
//            @Mapping(target = "name", ignore = true) // neu khi mình truyen gia trị moi của trường nay nó van set nguyen gia trị cu => vay nen cac gia tri cho vao day nen la cac gia tri khong duoc thay doi se hop ly hon
    })
    void updateMasterDataFromDto(MasterDataUpdateValueDto dto, @MappingTarget MasterData entity);
}
