package com.ltw.fireBase;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FirebaseUploadResponse {
    private String downloadUrl;
}
