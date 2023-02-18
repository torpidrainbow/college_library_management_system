package com.librarymanagement.collegelibrarymanagementsystem.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.librarymanagement.collegelibrarymanagementsystem.exception.LibraryException;
import com.librarymanagement.collegelibrarymanagementsystem.model.dto.BookDto;
import com.librarymanagement.collegelibrarymanagementsystem.model.dto.UserDto;
import com.librarymanagement.collegelibrarymanagementsystem.model.entity.Book;

import java.util.List;

public interface BookService {

    List<Book> findAllBooks();

    public String addBook(Long userId,BookDto book) throws Exception;

    public String removeBook(Long userId,Long bookId) throws Exception;

    public List<Book> searchBooksByTitle(String title) throws Exception;

    public List<Book> searchBooksByPublication(String publication);

    public List<Book> searchBooksByAuthor(String author) throws Exception;

    List<Book> searchBooksByCategory(String category);

    //public void reserveBook(BookDto book) throws Exception;

    public String issueBook(Long userId, Long bookId) throws Exception;

    String returnBook(Long userId, Long bookId) throws JsonProcessingException , LibraryException;

    String reserveBook(Long userId, Long bookId) throws Exception;
}
