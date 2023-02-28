package com.librarymanagement.collegelibrarymanagementsystem.ServiceTest;

import com.librarymanagement.collegelibrarymanagementsystem.RepositoryTest.TestObjectFactory;
import com.librarymanagement.collegelibrarymanagementsystem.exception.LibraryException;
import com.librarymanagement.collegelibrarymanagementsystem.model.dto.UserDto;
import com.librarymanagement.collegelibrarymanagementsystem.model.entity.User;
import com.librarymanagement.collegelibrarymanagementsystem.model.repository.BookRepository;
import com.librarymanagement.collegelibrarymanagementsystem.model.repository.UserRepository;
import com.librarymanagement.collegelibrarymanagementsystem.model.type.User_Type;
import com.librarymanagement.collegelibrarymanagementsystem.service.UserServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static com.librarymanagement.collegelibrarymanagementsystem.model.type.User_Type.LIBRARIAN;
import static com.librarymanagement.collegelibrarymanagementsystem.model.type.User_Type.STUDENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceUnitTest {

    @Mock
    ModelMapper mapper;
    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void should_registerUser() throws Exception {
        UserDto userDto = new UserDto("john","john","john@123",STUDENT);

        assertEquals("john",userDto.getUsername());

    }


    @Test
    public void should_deactivateUser() throws Exception {

        User user1 = TestObjectFactory.UserFactory.getUser("name","librarian","password1",User_Type.STUDENT);
        when(userRepository.findByUsername("librarian")).thenReturn(user1);
        user1.setType(LIBRARIAN);

        User user2 = TestObjectFactory.UserFactory.getUser("name","student","password1",User_Type.STUDENT);
        when(userRepository.findById(2L)).thenReturn(Optional.of(user2));
        user2.setType(STUDENT);
        System.out.println(user2.getType());


        assertEquals("User deactivated", userService.deactivateUser("librarian", 2L));

        Assertions.assertThrows(LibraryException.class, () -> {
            userService.deactivateUser("librarian", 100L);
        });

        Assertions.assertThrows(LibraryException.class, () -> {
            user2.setActive(false);
            userService.deactivateUser("librarian", 2L);
        });

        Assertions.assertThrows(LibraryException.class, () -> {
            user1.setType(User_Type.STUDENT);
            userService.deactivateUser("librarian", 2L);
        });
    }

    @Test
    public void should_modify() throws Exception {

        User user1 = TestObjectFactory.UserFactory.getUser("name","librarian","password1",User_Type.STUDENT);
        when(userRepository.findByUsername("librarian")).thenReturn(user1);
        user1.setType(LIBRARIAN);

        User user2 = TestObjectFactory.UserFactory.getUser("name","student","password1",User_Type.STUDENT);
        when(userRepository.findById(2L)).thenReturn(Optional.of(user2));
        user2.setType(STUDENT);
        user2.setTime_period(14);

        Assertions.assertEquals("Borrow period modified", userService.modifyTimePeriod("librarian", 2L, 15));

        userService.modifyTimePeriod("librarian", 2L, 15);
        Assertions.assertEquals(15,user2.getTime_period());
    }
}


