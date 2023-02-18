package com.librarymanagement.collegelibrarymanagementsystem.service;

import com.librarymanagement.collegelibrarymanagementsystem.model.dto.UserDto;
import com.librarymanagement.collegelibrarymanagementsystem.model.entity.Book;
import com.librarymanagement.collegelibrarymanagementsystem.model.entity.User;
import com.librarymanagement.collegelibrarymanagementsystem.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void register(UserDto userDto) throws Exception {
        try {
            User userEntity = new User();
            userEntity.setName(userDto.getName());
            userEntity.setUsername(userDto.getUser_name());
            userEntity.setPassword(userDto.getPassword());
            userEntity.setType(userDto.getType());
            System.out.println(userEntity);
            userRepository.save(userEntity);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception("Cannot register user");
        }
    }


}