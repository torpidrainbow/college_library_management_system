package com.librarymanagement.collegelibrarymanagementsystem.model.dto;

import com.librarymanagement.collegelibrarymanagementsystem.model.type.User_Type;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserDto {


    @NotEmpty
    private String name;

    @NotEmpty
    private String username;


    @NotEmpty
    private String password;


    private User_Type type;

    public UserDto(String name,String username, String password, User_Type type) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.type = type;

    }
}
