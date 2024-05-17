package com.practice.ahub.modelDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileModelDto {
    private Long id;
    private String url;
    private LocalDateTime uploadDate;
}
