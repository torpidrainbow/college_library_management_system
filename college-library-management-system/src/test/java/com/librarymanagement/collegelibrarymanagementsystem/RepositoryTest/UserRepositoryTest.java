package com.librarymanagement.collegelibrarymanagementsystem.RepositoryTest;

import com.librarymanagement.collegelibrarymanagementsystem.exception.LibraryException;
import com.librarymanagement.collegelibrarymanagementsystem.model.entity.User;
import com.librarymanagement.collegelibrarymanagementsystem.model.repository.UserRepository;
import com.librarymanagement.collegelibrarymanagementsystem.model.type.User_Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import javax.transaction.Transactional;

@DataJpaTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        User user1 = new User("name1","username1","password", User_Type.STUDENT);
        User user2 = new User("name2","username2","password", User_Type.STAFF);

        userRepository.save(user1);
        userRepository.save(user2);
    }

    @Test
    public void should_registerUser() throws Exception{

        Assertions.assertEquals(2,userRepository.findAll().stream().count());
        Assertions.assertEquals("username1",userRepository.findByUsername("username1").getUsername());

        User user = new User(null,"username1","password", User_Type.STUDENT);
        Assertions.assertThrows(DataIntegrityViolationException.class, ()->{
                userRepository.save(user);
                });
    }

    @Test
    public void should_modifyTime() throws Exception{
        userRepository.findByUsername("username2").setTime_period(20);

        Assertions.assertEquals(20,userRepository.findByUsername("username2").getTime_period());

        Assertions.assertEquals("username1",userRepository.findById(1L).get().getUsername());

    }

    @Test
    public void should_deactivateUser() throws Exception{

        Assertions.assertEquals("username1",userRepository.findById(1L).get().getUsername());

        userRepository.findByUsername("username2").setActive(false);
        Assertions.assertEquals(false,userRepository.findByUsername("username2").isActive());

    }
}
