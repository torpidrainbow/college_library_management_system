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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/book")
@PreAuthorize("isAuthenticated()")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;


    @GetMapping("/catalogue")
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(bookService.findAllBooks(), HttpStatus.OK);
    }

    @PostMapping("/addBook")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<String> addBook(@RequestBody BookDto bookDto,@AuthenticationPrincipal UserDetails userDetails) throws Exception {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth.isAuthenticated()) {
            return new ResponseEntity<>(bookService.addBook(userDetails.getUsername(),bookDto),HttpStatus.OK);
//        }
//
//        else{
//            return new ResponseEntity<>("Unauthorized access", HttpStatus.UNAUTHORIZED);
//        }
    }

    @DeleteMapping("/removeBook")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<String> removeBook(@AuthenticationPrincipal UserDetails userDetails,@RequestParam Long bookId) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.isAuthenticated()) {
            return new ResponseEntity<>(bookService.removeBook(userDetails.getUsername(), bookId), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Unauthorized access", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/title")
    public ResponseEntity<List<BookDto>> searchBooksByTitle(@RequestParam String title) throws Exception {
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

    @PutMapping("/borrow")
    public ResponseEntity<String> borrowBook(@AuthenticationPrincipal UserDetails userDetails,
                                               @RequestParam Long bookId) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.isAuthenticated()) {
            return new ResponseEntity<>(bookService.issueBook(userDetails.getUsername(), bookId), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Unauthorized access", HttpStatus.UNAUTHORIZED);
        }

    }

    @PutMapping("/{userId}/return/{bookId}")
    public ResponseEntity<String> returnBook(@AuthenticationPrincipal UserDetails userDetails,
                                            @RequestParam Long bookId) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            return new ResponseEntity<>(bookService.returnBook(userDetails.getUsername(), bookId), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Unauthorized access", HttpStatus.UNAUTHORIZED);
        }
    }
}






