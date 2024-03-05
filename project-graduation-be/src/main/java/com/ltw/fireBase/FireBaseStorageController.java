package com.ltw.fireBase;

import com.ltw.dto.response.BaseItemResponse;
import com.ltw.dto.response.BaseResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FireBaseStorageController {
    private final FirebaseStorageService storageService;

    public FireBaseStorageController(FirebaseStorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/upload")
    public BaseItemResponse<FirebaseUploadResponse> uploadFile(@Valid @RequestPart("file") MultipartFile file) {
        try {
            String url = storageService.uploadFile(file);
            FirebaseUploadResponse response = new FirebaseUploadResponse(url);
            return BaseResponse.successData(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/upload1")
    public BaseItemResponse<FirebaseUploadResponse> uploadFile1(@Valid @RequestPart("file") MultipartFile file) {
        try {
            String url = storageService.uploadFile(file);
            FirebaseUploadResponse response = new FirebaseUploadResponse(url);
            return BaseResponse.successData(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
