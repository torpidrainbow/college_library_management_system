package com.librarymanagement.collegelibrarymanagementsystem.service;

import com.librarymanagement.collegelibrarymanagementsystem.exception.LibraryException;
import com.librarymanagement.collegelibrarymanagementsystem.model.dto.UserDto;
import com.librarymanagement.collegelibrarymanagementsystem.model.entity.Record;
import com.librarymanagement.collegelibrarymanagementsystem.model.entity.User;
import com.librarymanagement.collegelibrarymanagementsystem.model.repository.UserRepository;
import com.librarymanagement.collegelibrarymanagementsystem.model.type.User_Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.librarymanagement.collegelibrarymanagementsystem.model.type.User_Type.LIBRARIAN;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String register(UserDto userDto) throws LibraryException {
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
            return "User Registered";
        }
        catch (DataIntegrityViolationException e){
            throw new LibraryException("Username should be unique and cannot be null");
        }
        catch (Exception e){
            throw new LibraryException("Cannot register user");
        }
    }


    @Override
    public String deactivateUser(String username, Long userId) throws LibraryException {
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
            throw new LibraryException("Cannot deactivate user");
        }
    }

    @Override
    public String modifyTimePeriod(String username, Long userId, int days) throws LibraryException {
        try {
            User loggedInUser = userRepository.findByUsername(username);
            User user = userRepository.findById(userId).orElseThrow(() -> new LibraryException("User not found"));

            if (loggedInUser.getType() != LIBRARIAN) {
                throw new LibraryException("Only librarian can modify time period");
            }
            if (!user.isActive()) {
                throw new LibraryException("User is not active");
            }
            user.setTime_period(days);
            return ("Borrow period modified");
        } catch (LibraryException e) {
            throw e;
        } catch (Exception e) {
            throw new LibraryException("Cannot modify time period");
        }
    }

    @Override
    public double total_fine(String username) throws LibraryException{
        User loggedInUser = userRepository.findByUsername(username);

        return loggedInUser.getRecord().getFine_amount();
    }
}
