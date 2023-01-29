package com.librarymanagement.collegelibrarymanagementsystem.model.dto;

import com.librarymanagement.collegelibrarymanagementsystem.model.type.User_Type;
import lombok.Data;

@Data
public class UserDto {


    private String name;

    private String user_name;

    private String password;

    private User_Type type;
}
