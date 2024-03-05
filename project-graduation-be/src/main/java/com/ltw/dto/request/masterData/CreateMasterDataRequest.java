package com.ltw.dto.request.masterData;

import lombok.Data;

@Data
public class CreateMasterDataRequest {
    private String type;
    private String code;
    private String name;
}
