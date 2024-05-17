package com.practice.ahub.modelDto;

import com.practice.ahub.model.FileModel;
import com.practice.ahub.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {
    private UserDto userDto;
    private Gender gender;
    private String link;
    private FileModelDto profileImage;
    private FileModelDto bannerImage;
    private List<FileModelDto> allImages;
}
