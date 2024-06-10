package com.ltw.importFileExcel;

import com.ltw.dto.response.BaseItemResponse;
import com.ltw.dto.response.BaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/excel")
public class ExcelController {
    private final FileManager fileManager;

    public ExcelController(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @PostMapping("/import")
    public BaseItemResponse<?> importUserList(MultipartFile file) throws IOException, ExecutionException, InterruptedException {
        fileManager.importFromExcel(file);
        return BaseResponse.successData("Nhập dữ liệu thành công!");
    }
}
