package com.librarymanagement.collegelibrarymanagementsystem.service;

import com.librarymanagement.collegelibrarymanagementsystem.model.dto.UserDto;


public interface UserService {

    public String register(UserDto userDto) throws Exception;

    public String deactivateUser(String username, Long userId) throws Exception;

    String modifyTimePeriod(String username, Long userId, int days) throws Exception;

    double total_fine(String username) throws Exception;
}