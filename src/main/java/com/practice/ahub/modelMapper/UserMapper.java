package com.practice.ahub.modelMapper;

import com.practice.ahub.model.User;
import com.practice.ahub.modelDto.UserDto;

public class UserMapper {
    public static UserDto toUserDto(User user){
        return UserDto.builder()
                .name(user.getName())
                .surName(user.getSurname())
                .build();
    }
}
