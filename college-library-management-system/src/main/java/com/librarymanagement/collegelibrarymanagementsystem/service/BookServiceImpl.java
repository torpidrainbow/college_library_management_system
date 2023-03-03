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
import com.librarymanagement.collegelibrarymanagementsystem.model.type.User_Type;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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
    private RecordRespository recordRespository;
    @Autowired
    private UserRepository userRepository;


    public BookServiceImpl(ModelMapper mapper, BookRepository bookRepository, UserRepository userRepository) {
        this.mapper = mapper;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }


    @Override
    public List<BookDto> findAllBooks() {
        List<Book> bookList = bookRepository.findAll();
        if(bookList.isEmpty()){
            throw new LibraryException("Cannot find book with this keyword");
        }
        return bookList.stream().map(e -> mapper.map(e, BookDto.class)).collect(Collectors.toList());
    }


    @Override
    public String addBook(String username, BookDto book) throws Exception {
        try {
            User user = userRepository.findByUsername(username);
            if(user==null){
                throw new LibraryException("User not found");
            }
            if(!user.isActive()){
                throw new LibraryException("Cannot perform action for deactivated user");
            }
            if (user.getType()!= LIBRARIAN) {
                throw new LibraryException("Only librarian can add book");
            }
            Book bookEntity = new Book();
            bookEntity.setTitle(book.getTitle());
            bookEntity.setPublication(book.getPublication());
            bookEntity.setAuthor(book.getAuthor());
            bookEntity.setCategory(book.getCategory());
            bookRepository.save(bookEntity);
            return ("Book added");
        }
        catch (LibraryException e){
            throw e;
        }
        catch (ConstraintViolationException e){
            throw new LibraryException("Invalid details");
        }
        catch(Exception e){
            e.printStackTrace();
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
            if(!user.isActive()){
                throw new LibraryException("Cannot perform action for deactivated user");
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
        if(bookList.isEmpty()){
            throw new LibraryException("Cannot find book with this keyword");
        }
        return bookList.stream().map(e -> mapper.map(e, BookDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<BookDto> searchBooksByPublication(String publication) {
        List<Book> bookList = bookRepository.findByPublication(publication);
        if(bookList.isEmpty()){
            throw new LibraryException("Cannot find book with this keyword");
        }
        return bookList.stream().map(e -> mapper.map(e, BookDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<BookDto>searchBooksByAuthor(String author) throws Exception {
        try{
            List<Book> bookList = bookRepository.findByAuthor(author);
            if(bookList.isEmpty()){
                throw new LibraryException("Cannot find book with this author");
            }
            return bookList.stream().map(e -> mapper.map(e, BookDto.class)).collect(Collectors.toList());
        }
        catch (LibraryException e){
            throw e;
        }

    }
    @Override
    public List<BookDto> searchBooksByCategory(Book_category category){
        List<Book> bookList = bookRepository.findByCategory(category);

        if(bookList.isEmpty()){
            throw new LibraryException("Cannot find book with this keyword");
        }
        return bookList.stream().map(e -> mapper.map(e, BookDto.class)).collect(Collectors.toList());
    }

    @Override
    public String issueBook(String username, Long bookId) throws LibraryException{
        try {
            User user = userRepository.findByUsername(username);
            Book book = bookRepository.findById(bookId).orElseThrow(()-> new LibraryException("Book not found"));

            Record record = user.getRecord();

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
            Book book = bookRepository.findById(bookId).orElseThrow(()->new LibraryException("Book not found"));

            if(!user.isActive()){
                throw new LibraryException("Cannot perform action for deactivated user");
            }
            if (book.isAvailable()) {
                throw new LibraryException("Invalid book details");
            }
            if(!(user).equals(book.getBorrower())) {
                throw new LibraryException("User is not the borrower");
            }
            if(!book.getWaitingList().isEmpty()) {
                issueBook(book.getWaitingList().stream().findFirst().get().getUsername(), bookId);
                book.removeUser();
            }
            calculateFine(bookId);
            user.getRecord().setFine_amount(user.getRecord().getFine_amount() + calculateFine(bookId));
            user.getRecord().setBooks_issued(user.getRecord().getBooks_issued() - 1);

            book.setAvailable(true);
            book.setBorrower(null);
            book.setIssue_date(null);
            book.setDueDate(null);
            user.removeBook(book);
            bookRepository.save(book);
            userRepository.save(user);
            return "Book returned";
        }
        catch(LibraryException e){
            throw e;
        }
        catch (Exception e) {
            throw new LibraryException("Cannot return book");
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
            if (book.getWaitingList().contains(user)) {
                throw new LibraryException("User already added to waiting list");
            }
            book.addUser(user);
            bookRepository.save(book);
            return ("Added to waiting list");
        }
        catch(LibraryException e){
            throw e;
        }
        catch(Exception e){
            throw new LibraryException("Cannot reserve book");
        }
    }

    @Override
    public String renewBook(String username,Long bookId) throws Exception {
        try {
            User user = userRepository.findByUsername(username);

            Book book = bookRepository.findById(bookId).orElseThrow(() -> new LibraryException("Book not found"));
            if (book.isAvailable()) {
                throw new LibraryException("Book is not borrowed yet");
            }

            if(book.getBorrower() != user){
                throw new LibraryException("User is not the borrower");
            }

            if (book.getRenewalDate() != null) {
                throw new LibraryException("Book has already been renewed");
            }
            Date newDueDate = addDays(book.getDueDate(), user.getTime_period());
            book.setDueDate(newDueDate);
            bookRepository.save(book);
            return ("Book renewed");
        }
        catch (LibraryException e){
            throw e;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception("Cannot renew Book");
        }
    }

    @Override
    public List<Book> findOverdueBooks() {
        List<Book> books = bookRepository.findAll();
        List<Book> overdueBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.daysOverdue() > 0) {
                overdueBooks.add(book);
            }
        }
        return overdueBooks;
    }

    private Date addDays(Date date, int days) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        localDate = localDate.plusDays(days);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public int calculateFine(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new LibraryException("Book not found"));
        return Math.max(0, book.daysOverdue() * 5);
    }


}
