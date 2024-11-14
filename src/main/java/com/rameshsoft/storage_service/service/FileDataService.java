package com.rameshsoft.storage_service.service;

import com.rameshsoft.storage_service.entity.FileData;
import com.rameshsoft.storage_service.repository.FileDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class FileDataService {
    @Autowired
    private FileDataRepository fileDataRepository;

    private final String FOLDER_PATH = "C:\\Users\\Sreenivas Bandaru\\Pictures\\Screenshots\\";

    public String uploadImageToFileSystem(MultipartFile file) throws IOException {
        String filePath = FOLDER_PATH+file.getOriginalFilename();

        FileData fileData = fileDataRepository.save(FileData.builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .filePath(filePath)
                .build());
        file.transferTo(new File(filePath));

        if(fileData != null)
        {
            return "file uploaded successfully : " +filePath;
        }
        return "file not uploaded";
    }

    public byte[] downloadImageFromFileSystem(String fileName) throws IOException{
        Optional<FileData> fileData = fileDataRepository.findByName(fileName);
        String filePath = fileData.get().getFilePath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }
}
