package com.practice.ahub.service;

import com.practice.ahub.model.FileModel;
import com.practice.ahub.properties.MinioProperties;
import io.minio.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class MinioService {
    private final MinioProperties minioProperties;
    private MinioClient minioClient;

    @SneakyThrows
    public String save(MultipartFile file) {
        String fileName = UUID.randomUUID().toString().concat(file.getOriginalFilename());
        minioClient.putObject(
                PutObjectArgs.builder()
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .object(fileName)
                        .bucket(checkBucket(file.getContentType().split("/")[0]))
                        .build()
        );
        log.info("save file name: {}", fileName);
        return fileName;
    }

    @SneakyThrows
    private String checkBucket(String bucketName) {
        log.info("try check bucket with name: {}", bucketName);
        if (!minioClient.bucketExists(BucketExistsArgs
                .builder().bucket(bucketName).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
            log.info("create bucket with name: {}", bucketName);
        }
        return bucketName;
    }

    @SneakyThrows
    public String getFileUrl(FileModel file) {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .bucket(file.getContentType())
                        .object(file.getFileName())
                        .expiry(1, TimeUnit.MINUTES)
                        .build()
        );
    }

    @SneakyThrows
    public void deleteUselessFiles(List<FileModel> files) {
        List<DeleteObject> errors = new ArrayList<>();
        for (FileModel file : files) {
            errors.add(new DeleteObject(file.getFileName()));
        }
        for (FileModel file : files) {
            minioClient.removeObjects(RemoveObjectsArgs.builder()
                    .objects(errors)
                    .bucket(file.getContentType().split("/")[0].toLowerCase())
                    .build());
        }
    }


    @PostConstruct
    public MinioClient init() {
        log.info("try connect to minio");
        log.info("2");
        try {
            return minioClient =
                    MinioClient.builder()
                            .endpoint(minioProperties.getUrl())
                            .credentials(minioProperties.getAccess_key(), minioProperties.getSecret_key())
                            .build();
        }catch (Exception e){
            log.debug("can not connect to minio");
            throw new RuntimeException();
        }
    }
}
