package com.librarymanagement.collegelibrarymanagementsystem.model.repository;

import com.librarymanagement.collegelibrarymanagementsystem.model.entity.Book;
import com.librarymanagement.collegelibrarymanagementsystem.model.type.Book_category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    List<Book> findByTitle(String title);

    List<Book> findByCategory(Book_category category);

    List<Book> findByAuthor(String author);

    List<Book> findByPublication(String publication);


}
