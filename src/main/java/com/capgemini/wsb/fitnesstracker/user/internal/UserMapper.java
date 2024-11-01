package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.stereotype.Component;

@Component
class UserMapper {

    UserDto toDto(User user) {
        return new UserDto(user.getId(),
                           user.getFirstName(),
                           user.getLastName(),
                           user.getBirthdate(),
                           user.getEmail());
    }

    User toEntity(UserDto userDto) {
        return new User(
                        userDto.firstName(),
                        userDto.lastName(),
                        userDto.birthdate(),
                        userDto.email());
    }

    User updateEntity(User user, UserDto userDto) {
        // Update only fields that are not null in the DTO
        if (userDto.firstName() != null) {
            user.setFirstName(userDto.firstName());
        }

        if (userDto.lastName() != null) {
            user.setLastName(userDto.lastName());
        }

        if (userDto.birthdate() != null) {
            user.setBirthdate(userDto.birthdate());
        }

        if (userDto.email() != null) {
            user.setEmail(userDto.email());
        }
        
        return user;
    }

}
