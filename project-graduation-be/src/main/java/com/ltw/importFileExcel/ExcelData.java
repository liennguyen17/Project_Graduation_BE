package com.ltw.importFileExcel;

import com.ltw.model.User;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExcelData {
    private int rowIndex;
    private User user;

    private List<ErrorDetail> errorDetailList = new ArrayList<>();
    private boolean isValid = true;

    @Data
    @Builder
    public static class ErrorDetail {
        private int columnIndex;
        private String errMsg;
    }
}
