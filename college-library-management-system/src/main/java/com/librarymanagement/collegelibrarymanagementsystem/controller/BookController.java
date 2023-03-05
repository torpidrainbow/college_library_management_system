package com.librarymanagement.collegelibrarymanagementsystem.controller;



import com.librarymanagement.collegelibrarymanagementsystem.model.dto.BookDto;
import com.librarymanagement.collegelibrarymanagementsystem.model.repository.BookRepository;
import com.librarymanagement.collegelibrarymanagementsystem.model.type.Book_category;
import com.librarymanagement.collegelibrarymanagementsystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;


    @GetMapping("/catalogue")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return new ResponseEntity<>(bookService.findAllBooks(), HttpStatus.OK);
    }

    @PostMapping("/addBook")
    public ResponseEntity<?> addBook(@Valid@RequestBody BookDto bookDto, @AuthenticationPrincipal UserDetails userDetails) {
        return new ResponseEntity<>(bookService.addBook(userDetails.getUsername(), bookDto), HttpStatus.OK);

    }

    @DeleteMapping("/removeBook")
    public ResponseEntity<?> removeBook(@AuthenticationPrincipal UserDetails userDetails,@RequestParam Long bookId) {
            return new ResponseEntity<>(bookService.removeBook(userDetails.getUsername(), bookId), HttpStatus.OK);
    }

    @GetMapping("/title")
    public ResponseEntity<List<BookDto>> searchBooksByTitle(@RequestParam String title){
        return new ResponseEntity<>(bookService.searchBooksByTitle(title), HttpStatus.OK);
    }

    @GetMapping("/author")
    public ResponseEntity<List<BookDto>> searchBooksByAuthor(@RequestParam String author) {
        return new ResponseEntity<>(bookService.searchBooksByAuthor(author),HttpStatus.OK) ;
    }

    @GetMapping("/publication")
    public ResponseEntity<List<BookDto>> searchBooksByPublication(@RequestParam String publication) {
        return new ResponseEntity<>(bookService.searchBooksByPublication(publication), HttpStatus.OK);
    }

    @GetMapping("/category")
    public ResponseEntity<List<BookDto>> searchBooksByCategory(@RequestParam Book_category category) {
            return new ResponseEntity<>(bookService.searchBooksByCategory(category), HttpStatus.OK);
    }

    @PutMapping("/borrow")
    public ResponseEntity<?> borrowBook(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Long bookId) {
            return new ResponseEntity<>(bookService.issueBook(userDetails.getUsername(), bookId), HttpStatus.OK);
    }

    @PutMapping("/return")
    public ResponseEntity<String> returnBook(@AuthenticationPrincipal UserDetails userDetails,
                                            @RequestParam Long bookId)  {
            return new ResponseEntity<>(bookService.returnBook(userDetails.getUsername(), bookId), HttpStatus.OK);
    }

    @PutMapping("/reserve")
    public ResponseEntity<String> reserveBook(@AuthenticationPrincipal UserDetails userDetails,
                                             @RequestParam Long bookId) {
            return new ResponseEntity<>(bookService.reserveBook(userDetails.getUsername(), bookId), HttpStatus.OK);
    }

    @PutMapping("/renew")
    public ResponseEntity<String> renewBook(@AuthenticationPrincipal UserDetails userDetails,
                                              @RequestParam Long bookId) {
            return new ResponseEntity<>(bookService.renewBook(userDetails.getUsername(), bookId), HttpStatus.OK);
        }

}






