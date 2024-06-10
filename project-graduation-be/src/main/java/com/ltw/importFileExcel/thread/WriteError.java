package com.ltw.importFileExcel.thread;

import com.ltw.importFileExcel.ExcelData;
import com.ltw.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import java.util.concurrent.Callable;
@Data
@AllArgsConstructor
public class WriteError implements Callable<Void> {
    private Row row;
    private User user;
    private CellStyle cellStyle;
    private ExcelData excelData;
    @Override
    public Void call() throws Exception {
        row.createCell(0).setCellValue(user.getName());
        row.createCell(1).setCellValue(user.getUserCode());
        row.createCell(2).setCellValue(user.getUsername());
        row.createCell(3).setCellValue(user.getPassword());
        row.createCell(4).setCellValue(user.getRole());

        excelData.getErrorDetailList().forEach(errorDetail -> {
            Cell cell = row.getCell(errorDetail.getColumnIndex());
            cell.setCellStyle(cellStyle);
            cell.setCellValue(errorDetail.getErrMsg());
        });
        return null;
    }
}
