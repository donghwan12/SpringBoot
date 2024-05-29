package com.example.app.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String username;
    private String password;
    private String role;

    //OAUth2
    private String provider;
    private String providerId;


    public UserDto(UserDto userDto) {
    }
}
