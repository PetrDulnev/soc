package com.practice.ahub.modelMapper;

import com.practice.ahub.model.UserProfile;
import com.practice.ahub.modelDto.FileModelDto;
import com.practice.ahub.modelDto.UserDto;
import com.practice.ahub.modelDto.UserProfileDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserProfileMapper {
    public static UserProfileDto toDto(UserProfile userProfile,
                                       UserDto userDto,
                                       FileModelDto profileImage,
                                       List<FileModelDto> files,
                                       FileModelDto bannerImage) {
        return UserProfileDto.builder()
                .userDto(userDto)
                .link(userProfile.getLink())
                .profileImage(profileImage)
                .allImages(Optional.of(files).orElse(new ArrayList<>()))
                .bannerImage(Optional.of(bannerImage).orElse(null))
                .gender(Optional.of(userProfile.getGender()).orElse(null))
                .build();
    }
}
