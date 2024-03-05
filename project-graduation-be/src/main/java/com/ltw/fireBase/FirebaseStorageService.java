package com.ltw.fireBase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

@Service
public class FirebaseStorageService {
    public final Long EXPIRATION_TIME = System.currentTimeMillis() + 100L * 365 * 24 * 60 * 60 * 1000;
    @Value("${firebase.storage.bucket}")
    private String bucketName;

    @Value("${firebase.config.path}")
    private String fileBasePath;

    public String uploadFile(MultipartFile file) throws IOException {
        InputStream serviceAccount = getClass().getResourceAsStream("/e-test-3d981-firebase-adminsdk-u31s6-7328ee93ed.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        StorageOptions storageOptions = StorageOptions.newBuilder().setCredentials(credentials).build();
        Storage storage = storageOptions.getService();
        String originalFilename = file.getOriginalFilename();

        String filename = originalFilename;

        Blob blob = storage.create(BlobInfo.newBuilder(bucketName, filename)
                .setContentType(file.getContentType())
                .build(), file.getInputStream());

        // Ví dụ: Thời gian hết hạn là 100 năm
        long expirationTime = EXPIRATION_TIME;
        String downloadUrl = blob.signUrl(expirationTime, TimeUnit.MILLISECONDS).toString();

        return downloadUrl;
    }
}
