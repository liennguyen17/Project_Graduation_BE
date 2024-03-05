package com.ltw.dto.request.masterData;

import lombok.Data;

@Data
public class UpdateMasterDataRequest {
    private Integer id;
    private String type;
    private String code;
    private String name;
}
