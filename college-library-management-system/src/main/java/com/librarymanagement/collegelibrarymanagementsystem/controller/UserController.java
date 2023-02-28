package com.librarymanagement.collegelibrarymanagementsystem.controller;

import com.librarymanagement.collegelibrarymanagementsystem.model.dto.LoginDto;
import com.librarymanagement.collegelibrarymanagementsystem.model.dto.UserDto;
import com.librarymanagement.collegelibrarymanagementsystem.model.entity.User;
import com.librarymanagement.collegelibrarymanagementsystem.service.BookService;
import com.librarymanagement.collegelibrarymanagementsystem.service.BookServiceImpl;
import com.librarymanagement.collegelibrarymanagementsystem.service.UserDetailsServiceImpl;
import com.librarymanagement.collegelibrarymanagementsystem.service.UserService;
import org.apache.catalina.connector.Request;
import org.apache.catalina.security.SecurityClassLoad;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.SecureRandom;


@Controller
@RequestMapping("/user")

public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private BookServiceImpl bookService;
    @Autowired
    public AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto userDto) throws Exception {

        return new ResponseEntity<>(userService.register(userDto),HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto) throws Exception {
        try {
            SecurityContextHolder.clearContext();
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(),userDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(auth);

            return new ResponseEntity<>("User logged in successfully\nThe total fine is: Rs."
                    + userService.total_fine(userDto.getUsername())
                    + "\nThe total overdue books are " + bookService.findOverdueBooks(), HttpStatus.OK);
        }
        catch (BadCredentialsException e){
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        SecurityContextHolder.clearContext();
        return new ResponseEntity<>("User logged out successfully", HttpStatus.OK);
    }

}

