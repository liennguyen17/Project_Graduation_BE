package com.ltw.importFileExcel;

import com.ltw.repository.users.UsersRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
public class FileManager  {
    private final UsersRepository userRepository;
    private final ExcelService excelService;

//    public FileManager(UserRepository userRepository, ExcelService excelService) {
//        this.userRepository = userRepository;
//        this.excelService = excelService;
//    }

    public void importFromExcel(MultipartFile file) throws IOException, ExecutionException, InterruptedException {
        userRepository.saveAllAndFlush(excelService.readUserFromExcel(file));
    }
}
