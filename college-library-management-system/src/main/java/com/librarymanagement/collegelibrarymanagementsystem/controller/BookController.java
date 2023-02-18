package com.librarymanagement.collegelibrarymanagementsystem.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.librarymanagement.collegelibrarymanagementsystem.exception.LibraryException;
import com.librarymanagement.collegelibrarymanagementsystem.model.dto.BookDto;
import com.librarymanagement.collegelibrarymanagementsystem.model.dto.UserDto;
import com.librarymanagement.collegelibrarymanagementsystem.model.entity.Book;
import com.librarymanagement.collegelibrarymanagementsystem.model.repository.BookRepository;
import com.librarymanagement.collegelibrarymanagementsystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;


    @GetMapping("/catalogue")
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(bookService.findAllBooks(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addBook(@RequestParam Long userId, @RequestBody BookDto bookDto) throws Exception {

        return new ResponseEntity<>(bookService.addBook(userId,bookDto),HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/removebook/{bookId}")
    public ResponseEntity<String> removeBook(@PathVariable Long userId,@PathVariable Long bookId) throws Exception {
        return new ResponseEntity<>(bookService.removeBook(userId,bookId), HttpStatus.OK);
    }

    @GetMapping("/title")
    public ResponseEntity<List<Book>> searchBooksByTitle(@RequestParam String title) throws Exception {
        return new ResponseEntity<>(bookService.searchBooksByTitle(title), HttpStatus.OK);
    }

    @GetMapping("/author")
    public ResponseEntity<List<Book>> searchBooksByAuthor(@RequestParam String author) throws Exception {
        return new ResponseEntity<>(bookService.searchBooksByAuthor(author),HttpStatus.OK) ;
    }

    @GetMapping("/publication")
    public ResponseEntity<List<Book>> searchBooksByPublication(@RequestParam String publication) {
        return new ResponseEntity<>(bookService.searchBooksByPublication(publication), HttpStatus.OK);
    }

    @GetMapping("/category")
    public List<Book> searchBooksByCategory(@RequestParam String category) {

            return (bookService.searchBooksByCategory(category));
    }

    @PutMapping("/{userId}/issue/{bookId}")
    public ResponseEntity<String> issueBook(@PathVariable(name = "userId") Long userId,
                                               @PathVariable(name = "bookId") Long bookId) throws Exception {
        return new ResponseEntity<>(bookService.issueBook(userId, bookId),HttpStatus.OK);

    }

    @PutMapping("/{userId}/return/{bookId}")
    public ResponseEntity<String> returnBook(@PathVariable(name = "userId") Long userId,
                                            @PathVariable(name = "bookId") Long bookId) throws Exception {
        return new ResponseEntity<>(bookService.returnBook(userId, bookId),HttpStatus.OK);

    }
    }






