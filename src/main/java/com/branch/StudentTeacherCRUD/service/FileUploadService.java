package com.branch.StudentTeacherCRUD.service;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileUploadService {

    public static String saveFile(MultipartFile photo) throws IOException {
        String uploadDir = System.getProperty("user.dir")+"\\src\\main\\resources\\webapp\\image\\";
        Files.createDirectories(Paths.get(uploadDir));
        String fileName = System.currentTimeMillis() + "_"+photo.getOriginalFilename();
        Path path = Paths.get(uploadDir+fileName);
        Files.write(path, photo.getBytes());
        return path.toAbsolutePath().toString();
    }

}
