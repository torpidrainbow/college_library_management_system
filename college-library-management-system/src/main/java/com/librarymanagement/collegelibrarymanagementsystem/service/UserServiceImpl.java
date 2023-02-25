package com.librarymanagement.collegelibrarymanagementsystem.service;

import com.librarymanagement.collegelibrarymanagementsystem.exception.LibraryException;
import com.librarymanagement.collegelibrarymanagementsystem.model.dto.UserDto;
import com.librarymanagement.collegelibrarymanagementsystem.model.entity.Record;
import com.librarymanagement.collegelibrarymanagementsystem.model.entity.User;
import com.librarymanagement.collegelibrarymanagementsystem.model.repository.RecordRespository;
import com.librarymanagement.collegelibrarymanagementsystem.model.repository.UserRepository;
import com.librarymanagement.collegelibrarymanagementsystem.model.type.User_Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

import static com.librarymanagement.collegelibrarymanagementsystem.model.type.User_Type.LIBRARIAN;

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
            User userEntity = new User();
            Record record = new Record();
            record.setUser(userEntity);
            userEntity.setName(userDto.getName());
            userEntity.setUsername(userDto.getUsername());
            String encode = passwordEncoder.encode(userDto.getPassword());
            userEntity.setPassword(encode);
            userEntity.setType(userDto.getType());
            userEntity.setRecord(record);
            if(userDto.getType()== User_Type.STUDENT){
                userEntity.setTime_period(14);
            }
            else{
                userEntity.setTime_period(30);
            }
            userRepository.save(userEntity);
        }
        catch (DataIntegrityViolationException e){
            throw new LibraryException("Username should be unique and cannot be null");
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception("Cannot register user");
        }
    }


//    @Override
//    public User authenticate(String username, String password){
//
//        User user = userRepository.findByUsername(username);
//        System.out.println(user);
//        if(user==null){
//            return null;
//        }
//        boolean passwordMatch = passwordEncoder.matches(password, user.getPassword());
//        if(!passwordMatch){
//            return null;
//        }
//        return user;
//    }

    @Override
    public String deactivateUser(String username, Long userId) throws Exception {
        try {
            User loggedInUser = userRepository.findByUsername(username);
            User user = userRepository.findById(userId).orElseThrow(() -> new LibraryException("User not found"));

            if (loggedInUser.getType() == LIBRARIAN) {
                if (user.isActive()) {
                    user.setActive(false);
                    userRepository.save(user);
                    return "User deactivated";

                } else {
                    throw new LibraryException("User is not active");
                }
            } else {
                throw new LibraryException("Only librarian can deactivate user");
            }
        }
    catch(LibraryException e){
        throw e;
    }
        catch (Exception e){
            throw new Exception("Cannot deactivate user");
        }
    }
    }

//    @Override
//    public double totalFine(Long userId)


