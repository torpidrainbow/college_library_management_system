package com.librarymanagement.collegelibrarymanagementsystem.model.dto;

import com.librarymanagement.collegelibrarymanagementsystem.model.type.User_Type;
import lombok.Data;

@Data
public class UserDto {


    private String name;

    private String username;

    private String password;

    private User_Type type;
}
