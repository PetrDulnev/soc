package com.practice.ahub.conroller;

import com.practice.ahub.service.FileModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class FileController {

//    private final FileModelService fileModelService;
//
//    @DeleteMapping("/{fileName}")
//    public void deleteFileByName(@PathVariable String fileName) {
//        fileModelService.safeDeleteImageWithName(fileName);
//    }

}
