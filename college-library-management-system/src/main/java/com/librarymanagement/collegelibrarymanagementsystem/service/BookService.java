package com.librarymanagement.collegelibrarymanagementsystem.service;

import com.librarymanagement.collegelibrarymanagementsystem.exception.LibraryException;
import com.librarymanagement.collegelibrarymanagementsystem.model.dto.BookDto;
import com.librarymanagement.collegelibrarymanagementsystem.model.entity.Book;
import com.librarymanagement.collegelibrarymanagementsystem.model.type.Book_category;

import java.util.List;

public interface BookService {

    List<BookDto> findAllBooks() throws LibraryException;

    public String addBook(String username,BookDto book) throws LibraryException;

    public String removeBook(String username,Long bookId) throws LibraryException;

    public List<BookDto> searchBooksByTitle(String title) throws LibraryException;

    public List<BookDto> searchBooksByPublication(String publication)throws LibraryException;

    public List<BookDto> searchBooksByAuthor(String author) throws LibraryException;

    List<BookDto> searchBooksByCategory(Book_category category) throws LibraryException;


    public String issueBook(String username, Long bookId) throws LibraryException;

    String returnBook(String username, Long bookId) throws LibraryException;

    String reserveBook(String username, Long bookId) throws LibraryException;

    String renewBook(String username, Long bookId) throws LibraryException;

    List<Book> findOverdueBooks();
}
