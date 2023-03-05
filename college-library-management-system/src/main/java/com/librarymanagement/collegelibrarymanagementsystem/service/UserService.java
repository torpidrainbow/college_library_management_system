package com.librarymanagement.collegelibrarymanagementsystem.service;

import com.librarymanagement.collegelibrarymanagementsystem.exception.LibraryException;
import com.librarymanagement.collegelibrarymanagementsystem.model.dto.UserDto;


public interface UserService {

    public String register(UserDto userDto) throws LibraryException;

    public String deactivateUser(String username, Long userId) throws LibraryException;

    String modifyTimePeriod(String username, Long userId, int days) throws LibraryException;

    double total_fine(String username) throws LibraryException;
}