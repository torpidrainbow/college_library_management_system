package com.librarymanagement.collegelibrarymanagementsystem.service;

import com.librarymanagement.collegelibrarymanagementsystem.model.dto.UserDto;
import com.librarymanagement.collegelibrarymanagementsystem.model.entity.Book;
import com.librarymanagement.collegelibrarymanagementsystem.model.entity.User;

import java.util.List;

public interface UserService {

    public void register(UserDto userDto) throws Exception;

    User authenticate(String username, String password);
}