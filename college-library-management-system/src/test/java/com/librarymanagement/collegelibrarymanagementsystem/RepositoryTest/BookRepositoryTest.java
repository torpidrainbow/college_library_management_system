package com.librarymanagement.collegelibrarymanagementsystem.RepositoryTest;

import com.librarymanagement.collegelibrarymanagementsystem.model.entity.Book;
import com.librarymanagement.collegelibrarymanagementsystem.model.repository.BookRepository;

import com.librarymanagement.collegelibrarymanagementsystem.model.type.Book_category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;
import java.util.List;



@DataJpaTest
@Transactional
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;


    @BeforeEach
    void setUp(){
        Book book1 = new Book("dbms", "author1", "publisher1", Book_category.TECHNICAL);
        Book book2 = new Book("java", "author2", "publisher2", Book_category.JOURNALS);

        bookRepository.save(book1);
        bookRepository.save(book2);
    }

    @Test
    public void testFindAllBooks() {
        Assertions.assertEquals(2,bookRepository.findAll().size());

    }

    @Test
    public void searcBooksByTitle() {
        List<Book> bookList = bookRepository.findByTitle("dbms");

        Assertions.assertEquals(1,bookRepository.findByTitle("dbms").size());
        Assertions.assertEquals("dbms", bookList.stream().findFirst().get().getTitle());
    }

    @Test
    public void searcBooksByAuthor() {
        List<Book> bookList = bookRepository.findByAuthor("author1");

        Assertions.assertEquals(1,bookRepository.findByAuthor("author1").size());
        Assertions.assertEquals("author1", bookList.stream().findFirst().get().getAuthor());

    }

    @Test
    public void searchBookByPublisher() {
        List<Book> bookList = bookRepository.findByPublication("publisher1");

        Assertions.assertEquals(1,bookRepository.findByPublication("publisher1").size());
        Assertions.assertEquals(0,bookRepository.findByPublication("publisher5").size());
        Assertions.assertEquals("publisher1", bookList.stream().findFirst().get().getPublication());

    }

    @Test
    public void searchBookByCategory() {
        List<Book> bookList = bookRepository.findByCategory(Book_category.TECHNICAL);

        Assertions.assertEquals(1,bookRepository.findByCategory(Book_category.TECHNICAL).size());
        Assertions.assertEquals(Book_category.TECHNICAL, bookList.stream().findFirst().get().getCategory());

    }

}
