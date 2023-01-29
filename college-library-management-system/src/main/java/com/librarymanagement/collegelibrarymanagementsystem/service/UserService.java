package com.librarymanagement.collegelibrarymanagementsystem.service;

import com.librarymanagement.collegelibrarymanagementsystem.model.dto.UserDto;
import com.librarymanagement.collegelibrarymanagementsystem.model.entity.User;

public interface UserService {

    public void register(UserDto userDto) throws Exception;
}
