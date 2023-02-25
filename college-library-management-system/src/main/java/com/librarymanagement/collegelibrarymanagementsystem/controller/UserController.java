package com.librarymanagement.collegelibrarymanagementsystem.controller;

import com.librarymanagement.collegelibrarymanagementsystem.model.dto.LoginDto;
import com.librarymanagement.collegelibrarymanagementsystem.model.dto.UserDto;
import com.librarymanagement.collegelibrarymanagementsystem.model.entity.User;
import com.librarymanagement.collegelibrarymanagementsystem.model.repository.UserRepository;
import com.librarymanagement.collegelibrarymanagementsystem.model.type.User_Type;
import com.librarymanagement.collegelibrarymanagementsystem.service.BookService;
import com.librarymanagement.collegelibrarymanagementsystem.service.UserDetailsServiceImpl;
import com.librarymanagement.collegelibrarymanagementsystem.service.UserService;
import org.apache.catalina.connector.Request;
import org.apache.catalina.security.SecurityClassLoad;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    public AuthenticationManager authenticationManager;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto userDto) throws Exception {
        userService.register(userDto);
        return new ResponseEntity<>("user registered", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto) throws Exception {
        try {
            SecurityContextHolder.clearContext();
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(auth);

            return new ResponseEntity<>("User logged in successfully", HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() throws Exception {
        SecurityContextHolder.clearContext();
        return new ResponseEntity<>("User logged out successfully", HttpStatus.OK);
    }


    @PutMapping("/deactivate")
    public ResponseEntity<?> deactivateUser(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Long userId) throws Exception {
            return new ResponseEntity<>(userService.deactivateUser(userDetails.getUsername(), userId), HttpStatus.OK);
        }

    @GetMapping("/modifyperiod")
    public ResponseEntity<?> modifyTimePeriod(@AuthenticationPrincipal UserDetails userDetails,Long userId) throws Exception{

            return new ResponseEntity<>("Time period for the user is updated",HttpStatus.OK);
        }

}

