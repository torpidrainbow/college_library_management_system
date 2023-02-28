package com.librarymanagement.collegelibrarymanagementsystem.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.librarymanagement.collegelibrarymanagementsystem.exception.LibraryException;
import com.librarymanagement.collegelibrarymanagementsystem.model.dto.BookDto;
import com.librarymanagement.collegelibrarymanagementsystem.model.dto.UserDto;
import com.librarymanagement.collegelibrarymanagementsystem.model.entity.Book;
import com.librarymanagement.collegelibrarymanagementsystem.model.type.Book_category;

import java.util.List;

public interface BookService {

    List<Book> findAllBooks();

    public String addBook(String username,BookDto book) throws Exception;

    public String removeBook(String username,Long bookId) throws Exception;

    public List<BookDto> searchBooksByTitle(String title) throws Exception;

    public List<BookDto> searchBooksByPublication(String publication);

    public List<BookDto> searchBooksByAuthor(String author) throws Exception;

    List<BookDto> searchBooksByCategory(Book_category category);

    //public void reserveBook(BookDto book) throws Exception;

    public String issueBook(String username, Long bookId) throws Exception;

    String returnBook(String username, Long bookId) throws JsonProcessingException , LibraryException;

    String reserveBook(String username, Long bookId) throws Exception;

    String renewBook(String username, Long bookId) throws Exception;

    List<Book> findOverdueBooks();
}
