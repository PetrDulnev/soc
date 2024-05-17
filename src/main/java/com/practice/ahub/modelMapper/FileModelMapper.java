package com.practice.ahub.modelMapper;

import com.practice.ahub.model.FileModel;
import com.practice.ahub.modelDto.FileModelDto;

public class FileModelMapper {

    public static FileModelDto toDto(FileModel file, String url) {
        return FileModelDto.builder()
                .id(file.getId())
                .url(url)
                .uploadDate(file.getCreatedDate())
                .build();
    }


}
