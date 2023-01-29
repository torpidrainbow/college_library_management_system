package com.librarymanagement.collegelibrarymanagementsystem.controller;

import com.librarymanagement.collegelibrarymanagementsystem.model.dto.UserDto;
import com.librarymanagement.collegelibrarymanagementsystem.model.entity.User;
import com.librarymanagement.collegelibrarymanagementsystem.service.UserService;
import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Method;

@Controller
@RequestMapping("/user")

public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<String> register(@Valid @RequestBody UserDto userDto) throws Exception {
        userService.register(userDto);
        return new ResponseEntity<>("user registered",HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<String> login(@Valid @RequestBody UserDto userDto) throws Exception {

        userService.login(userDto.getUser_name(),userDto.pass());
        return new ResponseEntity<>("user registered",HttpStatus.OK);
    }
}
