package com.librarymanagement.collegelibrarymanagementsystem.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.librarymanagement.collegelibrarymanagementsystem.exception.LibraryException;
import com.librarymanagement.collegelibrarymanagementsystem.model.dto.BookDto;
import com.librarymanagement.collegelibrarymanagementsystem.model.entity.Book;
import com.librarymanagement.collegelibrarymanagementsystem.model.entity.Record;
import com.librarymanagement.collegelibrarymanagementsystem.model.entity.User;
import com.librarymanagement.collegelibrarymanagementsystem.model.repository.BookRepository;
import com.librarymanagement.collegelibrarymanagementsystem.model.repository.RecordRespository;
import com.librarymanagement.collegelibrarymanagementsystem.model.repository.UserRepository;
import com.librarymanagement.collegelibrarymanagementsystem.model.type.Book_category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.librarymanagement.collegelibrarymanagementsystem.model.type.User_Type.*;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecordRespository recordRespository;

    public BookServiceImpl(ModelMapper mapper, BookRepository bookRepository, UserRepository userRepository) {
        this.mapper = mapper;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }


    @Override
    public List<Book> findAllBooks() {
        List<Book> bookList = bookRepository.findAll();
        return bookList;
    }


    @Override
    public String addBook(String username, BookDto book) throws Exception {
        try {
            User user = userRepository.findByUsername(username);
            if(user==null){
                throw new LibraryException("User not found");
            }
            if (user.getType() == LIBRARIAN) {
                Book bookEntity = new Book();
                bookEntity.setTitle(book.getTitle());
                bookEntity.setPublication(book.getPublication());
                bookEntity.setAuthor(book.getAuthor());
                bookEntity.setCategory(book.getCategory());
                bookRepository.save(bookEntity);
                return ("Book added");
            }
            else{
                throw new LibraryException("Only librarian can add book");
            }
        }
        catch (LibraryException e){
            throw e;
        }
        catch (DataIntegrityViolationException e){
            throw new LibraryException("fields can't be empty");
        }
        catch(Exception e){
            throw new Exception("Cannot add book");
        }
    }

    @Override
    public String removeBook(String username,Long bookId) throws Exception {
        try {
            User user = userRepository.findByUsername(username);
            if(user==null){
                throw new LibraryException("Invalid user");
            }
            Book book = bookRepository.findById(bookId).orElse(null);
            if (book == null) {
                throw new LibraryException("book not found");
            }

            if (user.getType()!=LIBRARIAN) {
                throw new LibraryException("This user cannot remove book");
            }
            bookRepository.deleteById(bookId);
            return ("Book removed");
        }
        catch (LibraryException e){
            throw e;
        }
        catch (Exception e){
            throw new Exception("cannot remove book");
        }
    }
    @Override
    public List<BookDto> searchBooksByTitle(String title) throws Exception {
        List<Book> bookList = bookRepository.findByTitle(title);

        return bookList.stream().map(e -> {
            BookDto bookDto = mapper.map(e, BookDto.class);
            return bookDto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Book> searchBooksByPublication(String publication) {
        List<Book> bookList = bookRepository.findByPublication(publication);
        return bookList;
    }

    @Override
    public List<Book>searchBooksByAuthor(String author) throws Exception {
        try{
            List<Book> bookList = bookRepository.findByAuthor(author);
            return bookList;
        }
        catch (Exception e){
            throw new LibraryException("cannot find books with this author ");
        }

    }

    @Override
    public List<Book> searchBooksByCategory(String category){
        return bookRepository.findByCategory(category);
    }


    @Override
    public String issueBook(String username, Long bookId) throws LibraryException{
        try {
            User user = userRepository.findByUsername(username);
            Book book = bookRepository.findById(bookId).orElseThrow(()-> new LibraryException("Book not found"));

            Record record = user.getRecord();
            System.out.println(record.getBooks_issued());

            if(user.getType()==STUDENT && record.getBooks_issued() >= 2){

                throw new LibraryException("Cannot issue more than 2 books");
            }
            if(user.getType()==STAFF && record.getBooks_issued() >= 6){
                throw new LibraryException("Cannot issue more than 6 books");
            }


            if (!book.isAvailable()) {
                throw new LibraryException("Book is not available");
            }

            if((user.getType()==STUDENT || user.getType()==LIBRARIAN) && book.getCategory()== Book_category.RESEARCH_PAPERS){
                throw new LibraryException("Only staff can issue Research Papers");
            }

                user.addBook(book);
                user.getRecord().setBooks_issued(user.getRecord().getBooks_issued() + 1);
                book.setAvailable(false);
                book.setIssue_date(new Date());
                book.setBorrower(user);


                bookRepository.save(book);
                userRepository.save(user);

            return "Book issued";
        }
        catch(LibraryException e){
            throw e;
        }catch (Exception e) {
            e.printStackTrace();
            throw new LibraryException("Cannot issue book");
        }

    }

    @Override
    public String returnBook(String username, Long bookId) throws JsonProcessingException ,LibraryException{
        try {
            User user = userRepository.findByUsername(username);
            System.out.println(user);
            Book book = bookRepository.findById(bookId).orElse(null);
            if (user == null || book == null) {
                throw new LibraryException("Enter valid user and book details");
            }
            if (book.isAvailable()) {
                throw new LibraryException("Invalid book details");
            }

            user.getRecord().setBooks_issued(user.getRecord().getBooks_issued() - 1);

            book.setAvailable(true);
            book.setReturn_date(new Date());
            //TODO:add calculate fine here
            user.removeBook(book);
            bookRepository.save(book);
            userRepository.save(user);
            if(!book.getWaitingList().isEmpty()) {
                issueBook(book.getWaitingList().stream().findFirst().get().getUsername(), bookId);
                book.removeUser();
            }

            return "Book returned";
        }
        catch(LibraryException e){
            throw e;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new LibraryException("Cannot issue book");
        }
    }

    @Override
    public String reserveBook(String username, Long bookId) throws Exception {
        try {
            User user = userRepository.findByUsername(username);
            Book book = bookRepository.findById(bookId).orElse(null);

            if (user == null || book == null) {
                throw new LibraryException("Enter valid user and book details");
            }
            if (book.isAvailable()) {
                issueBook(user.getUsername(), bookId);
            }
            if (!book.isAvailable()) {
                book.addUser(user);
            }
            return ("Added to waiting list");
        }
        catch(LibraryException e){
            throw e;
        }
        catch(Exception e){
            throw new LibraryException("Cannot reserve book");
        }
    }


}
