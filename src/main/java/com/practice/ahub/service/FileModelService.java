package com.practice.ahub.service;

import com.practice.ahub.exception.CustomException;
import com.practice.ahub.model.AccessFileExtension;
import com.practice.ahub.model.FileModel;
import com.practice.ahub.repository.FileModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileModelService {

    private final FileModelRepository fileModelRepository;
    private final MinioService minioService;

    public void isValidFile(MultipartFile file) {
        if (file != null && file.getOriginalFilename() != null && !file.getOriginalFilename().isEmpty()){
            try {
                AccessFileExtension.valueOf(file.getContentType().split("/")[1].toUpperCase());
            }catch (IllegalArgumentException e){
                throw new CustomException("unsupported file format");
            }
        }
    }

    public void safeDeleteImageWithName(String fileName) {
        FileModel file = fileModelRepository.findByFileName(fileName).orElseThrow(
                () -> new CustomException("file not found")
        );

        file.setDeleted(true);

        fileModelRepository.save(file);
    }

    public FileModel saveFileModel(FileModel fileModel) {
        return fileModelRepository.save(fileModel);
    }

    public void fileGC(){
        List<FileModel> listFiles = fileModelRepository.findByDeletedIsTrue();
        minioService.deleteUselessFiles(listFiles);
        fileModelRepository.deleteByDeletedIsTrue();
    }
}
