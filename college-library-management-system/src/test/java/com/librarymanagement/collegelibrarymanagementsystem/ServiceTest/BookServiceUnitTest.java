package com.librarymanagement.collegelibrarymanagementsystem.ServiceTest;

import com.librarymanagement.collegelibrarymanagementsystem.RepositoryTest.TestObjectFactory;
import com.librarymanagement.collegelibrarymanagementsystem.exception.LibraryException;
import com.librarymanagement.collegelibrarymanagementsystem.model.dto.BookDto;
import com.librarymanagement.collegelibrarymanagementsystem.model.entity.Book;
import com.librarymanagement.collegelibrarymanagementsystem.model.entity.Record;
import com.librarymanagement.collegelibrarymanagementsystem.model.entity.User;
import com.librarymanagement.collegelibrarymanagementsystem.model.repository.BookRepository;
import com.librarymanagement.collegelibrarymanagementsystem.model.repository.UserRepository;
import com.librarymanagement.collegelibrarymanagementsystem.model.type.Book_category;
import com.librarymanagement.collegelibrarymanagementsystem.model.type.User_Type;
import com.librarymanagement.collegelibrarymanagementsystem.service.BookServiceImpl;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;


import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.*;


import static com.librarymanagement.collegelibrarymanagementsystem.model.type.User_Type.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceUnitTest {

    @Mock
    ModelMapper mapper;
    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;


    @Test
    public void should_addBook() throws Exception {

        User user1 = TestObjectFactory.UserFactory.getUser("name","librarian","password1", LIBRARIAN);
        when(userRepository.findByUsername("librarian")).thenReturn(user1);
        user1.setType(LIBRARIAN);

        BookDto bookDto = new BookDto("dbms", "author1", "publisher", Book_category.TECHNICAL);

        System.out.println(user1.getUsername());
        assertEquals("Book added", bookService.addBook("librarian", bookDto));

        Assertions.assertThrows(LibraryException.class, () -> {
            bookService.addBook("user", bookDto);
        });

        Assertions.assertThrows(LibraryException.class, () -> {
            user1.setActive(false);
            bookService.addBook("librarian", bookDto);
        });

        Assertions.assertThrows(LibraryException.class, () -> {
            user1.setType(User_Type.STUDENT);
            bookService.addBook("librarian", bookDto);
        });
    }

    @Test
    public void should_removeBook() throws Exception {
        User user1 = TestObjectFactory.UserFactory.getUser("name","librarian1","password1", LIBRARIAN);
        user1.setType(LIBRARIAN);
        when(userRepository.findByUsername("librarian1")).thenReturn(user1);

        Book book1 = TestObjectFactory.BookFactory.getBook("dbms", "author1", "publisher1", Book_category.TECHNICAL);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));

        assertEquals("Book removed", bookService.removeBook("librarian1", 1L));

        Assertions.assertThrows(LibraryException.class, () -> {
            user1.setType(STUDENT);
            bookService.removeBook("librarian1", 1L);
        });

        Assertions.assertThrows(LibraryException.class, () -> {
            user1.setActive(false);
            bookService.removeBook("librarian1", 1L);
        });

        Assertions.assertThrows(LibraryException.class, () -> {
            bookService.removeBook("librarian1", 2L);
        });
    }

    @Test
    public void should_issueBook() throws Exception {
        User user1 = TestObjectFactory.UserFactory.getUser("name","student1","password1",User_Type.STUDENT);
        user1.setType(STUDENT);
        Record record = new Record();
        user1.setRecord(record);
        List<Book> list = new ArrayList<>();
        user1.setBookList(list);
        when(userRepository.findByUsername("student1")).thenReturn(user1);

        Book book1 = TestObjectFactory.BookFactory.getBook("dbms", "author1", "publisher1", Book_category.TECHNICAL);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));

        assertEquals("Book issued", bookService.issueBook("student1", 1L));

        Book book2 = TestObjectFactory.BookFactory.getBook("dbms", "author1", "publisher1", Book_category.RESEARCH_PAPERS);
        when(bookRepository.findById(2L)).thenReturn(Optional.of(book2));
        user1.setType(STAFF);
        assertEquals("Book issued", bookService.issueBook("student1", 2L));

        Assertions.assertThrows(LibraryException.class, () -> {
            book1.setCategory(Book_category.RESEARCH_PAPERS);
            bookService.removeBook("student1", 1L);
        });

        Assertions.assertThrows(LibraryException.class, () -> {
            user1.setType(STUDENT);
            user1.getRecord().setBooks_issued(3);
            book1.setCategory(Book_category.TECHNICAL);
            bookService.removeBook("student1", 1L);
        });

        Assertions.assertThrows(LibraryException.class, () -> {
            user1.getRecord().setBooks_issued(7);
            book1.setCategory(Book_category.TECHNICAL);
            bookService.removeBook("student1", 1L);
        });

        Assertions.assertThrows(LibraryException.class, () -> {
            user1.setActive(false);
            bookService.issueBook("student1", 1L);
        });

        Assertions.assertThrows(LibraryException.class, () -> {
            bookService.issueBook("student1", 1000L);
        });

    }

    @Test
    public void should_returnBook() throws Exception {
        User user1 = TestObjectFactory.UserFactory.getUser("name","student1","password1",User_Type.STUDENT);
        user1.setType(STUDENT);
        Record record = new Record();
        user1.setRecord(record);
        List<Book> list = new ArrayList<>();
        user1.setBookList(list);
        when(userRepository.findByUsername("student1")).thenReturn(user1);

        Book book1 = TestObjectFactory.BookFactory.getBook("dbms", "author1", "publisher1", Book_category.TECHNICAL);
        book1.setAvailable(false);
        book1.setBorrower(user1);
        book1.setWaitingList(new ArrayList<>());
        book1.setIssue_date(new Date());
        book1.setDueDate(new Date());
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));

        user1.addBook(book1);

        assertEquals("Book returned", bookService.returnBook("student1", 1L));

        Assertions.assertThrows(LibraryException.class, () -> {
            book1.setBorrower(null);
            bookService.returnBook("student1", 1L);
        });
        Assertions.assertThrows(LibraryException.class, () -> {
            user1.setActive(false);
            bookService.returnBook("student1", 1L);
        });

        Assertions.assertThrows(LibraryException.class, () -> {
            bookService.returnBook("student1", 1000L);
        });
    }

    @Test
    public void should_reserveBook() throws Exception {
        User user1 = TestObjectFactory.UserFactory.getUser("name","student1","password1",User_Type.STUDENT);
        user1.setType(STUDENT);
        Record record = new Record();
        user1.setRecord(record);
        List<Book> list = new ArrayList<>();
        user1.setBookList(list);
        when(userRepository.findByUsername("student1")).thenReturn(user1);

        Book book1 = TestObjectFactory.BookFactory.getBook("dbms", "author1", "publisher1", Book_category.TECHNICAL);
        book1.setAvailable(false);
        List<User> userList = new ArrayList<>();
        book1.setWaitingList(userList);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));

        assertEquals("Added to waiting list", bookService.reserveBook("student1", 1L));

        Assertions.assertThrows(LibraryException.class, () -> {
            book1.addUser(user1);
            bookService.reserveBook("student1", 1L);
        });

        Assertions.assertThrows(LibraryException.class, () -> {
            user1.setActive(false);
            bookService.reserveBook("student1", 1L);
        });

        Assertions.assertThrows(LibraryException.class, () -> {
            bookService.reserveBook("student1", 1000L);
        });
    }

    @Test
    public void should_renewBook() throws Exception {
        User user1 = TestObjectFactory.UserFactory.getUser("name","student1","password1",User_Type.STUDENT);
        user1.setType(STUDENT);
        Record record = new Record();
        user1.setRecord(record);
        List<Book> list = new ArrayList<>();
        user1.setBookList(list);
        when(userRepository.findByUsername("student1")).thenReturn(user1);

        User user2 = TestObjectFactory.UserFactory.getUser("name","student2","password1",User_Type.STUDENT);

        Book book1 = TestObjectFactory.BookFactory.getBook("dbms", "author1", "publisher1", Book_category.TECHNICAL);
        book1.setAvailable(false);
        book1.setBorrower(user1);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));

        assertEquals("Book renewed", bookService.renewBook("student1", 1L));

        Assertions.assertThrows(LibraryException.class, () -> {
            book1.setAvailable(true);
            bookService.renewBook("student1", 1000L);
        });

        Assertions.assertThrows(LibraryException.class, () -> {
            book1.setBorrower(user2);
            bookService.renewBook("student1", 1000L);
        });

        Assertions.assertThrows(LibraryException.class, () -> {
            book1.setRenewalDate(new Date());
            bookService.renewBook("student1", 1000L);
        });

        Assertions.assertThrows(LibraryException.class, () -> {
            user1.setActive(false);
            bookService.renewBook("student1", 1L);
        });

        Assertions.assertThrows(LibraryException.class, () -> {
            bookService.renewBook("student1", 1000L);
        });
    }

    @Test
    public void testSearchBooksByAuthor() throws Exception {
        Book book1 = new Book("Java Programming", "John", "12345", Book_category.TECHNICAL);
        Book book2 = new Book("Python Programming", "John", "67890", Book_category.TECHNICAL);
        List<Book> books = Arrays.asList(
                book1,book2);
        when(bookRepository.findByAuthor("John")).thenReturn(books);

        List<BookDto> bookDtos = bookService.searchBooksByAuthor("John");

        Assertions.assertEquals(2, bookDtos.size());

        Assertions.assertThrows(LibraryException.class, () -> {
            bookService.searchBooksByAuthor("student1");
        });
    }

    @Test
    public void testSearchBooksByTitle() throws Exception {
        Book book1 = new Book("Java", "John", "12345", Book_category.TECHNICAL);
        Book book2 = new Book("Java", "Smith", "67890", Book_category.TECHNICAL);
        List<Book> books = Arrays.asList(
                book1,book2);
        when(bookRepository.findByTitle("Java")).thenReturn(books);

        List<BookDto> bookDtos = bookService.searchBooksByTitle("Java");

        Assertions.assertEquals(2, bookDtos.size());

        Assertions.assertThrows(LibraryException.class, () -> {
            bookService.searchBooksByTitle("student1");
        });
    }

    @Test
    public void testSearchBooksByPublication() throws Exception {
        Book book1 = new Book("Java", "John", "12345", Book_category.TECHNICAL);
        Book book2 = new Book("Python", "Smith", "123435", Book_category.TECHNICAL);
        List<Book> books = Arrays.asList(
                book1,book2);
        when(bookRepository.findByPublication("Java")).thenReturn(books);

        List<BookDto> bookDtos = bookService.searchBooksByPublication("Java");

        Assertions.assertEquals(2, bookDtos.size());

        Assertions.assertThrows(LibraryException.class, () -> {
            bookService.searchBooksByPublication("student1");
        });
    }

    @Test
    public void testSearchBooksByCategory() throws Exception {
        Book book1 = new Book("Java", "John", "12345", Book_category.TECHNICAL);
        Book book2 = new Book("Python", "Smith", "123435", Book_category.TECHNICAL);
        List<Book> books = Arrays.asList(
                book1,book2);
        when(bookRepository.findByCategory(Book_category.TECHNICAL)).thenReturn(books);

        List<BookDto> bookDtos = bookService.searchBooksByCategory(Book_category.TECHNICAL);

        Assertions.assertEquals(2, bookDtos.size());

        Assertions.assertThrows(LibraryException.class, () -> {
            bookService.searchBooksByCategory(Book_category.JOURNALS);
        });
    }
}







