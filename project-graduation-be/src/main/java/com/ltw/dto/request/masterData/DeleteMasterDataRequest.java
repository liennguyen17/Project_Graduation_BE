package com.ltw.dto.request.masterData;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class DeleteMasterDataRequest {
    @NotEmpty(message = "Vui lòng truyền id cần xóa!")
    private List<Integer> ids;
}
