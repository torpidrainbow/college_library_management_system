package com.librarymanagement.collegelibrarymanagementsystem.service;

import com.librarymanagement.collegelibrarymanagementsystem.model.dto.UserDto;
import com.librarymanagement.collegelibrarymanagementsystem.model.entity.Record;
import com.librarymanagement.collegelibrarymanagementsystem.model.entity.User;
import com.librarymanagement.collegelibrarymanagementsystem.model.repository.RecordRespository;
import com.librarymanagement.collegelibrarymanagementsystem.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecordRespository recordRespository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void register(UserDto userDto) throws Exception {
        try {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10, new SecureRandom("your_fixed_salt".getBytes()));
            User userEntity = new User();
            Record record = new Record();
            record.setUser(userEntity);
            userEntity.setName(userDto.getName());
            userEntity.setUsername(userDto.getUsername());
            String encode = passwordEncoder.encode(userDto.getPassword());
            userEntity.setPassword(encode);
            userEntity.setType(userDto.getType());
            userEntity.setRecord(record);
            System.out.println(userEntity);
            userRepository.save(userEntity);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception("Cannot register user");
        }
    }

    @Override
    public User authenticate(String username, String password){

        User user = userRepository.findByUsername(username);
        System.out.println(user);
        if(user==null){
            return null;
        }
        boolean passwordMatch = passwordEncoder.matches(password, user.getPassword());
        if(!passwordMatch){
            return null;
        }
        return user;
    }


}