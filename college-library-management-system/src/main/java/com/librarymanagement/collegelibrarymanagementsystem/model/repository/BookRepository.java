package com.librarymanagement.collegelibrarymanagementsystem.model.repository;

import com.librarymanagement.collegelibrarymanagementsystem.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    @Query(value = "Select * from books b where b.title=?", nativeQuery = true)
    public List<Book> findByTitle(@Param("title") String title);

    @Query(value = "Select * from books b where b.author=?", nativeQuery = true)
    public List<Book> findByAuthor(@Param("author") String author);

    @Query(value = "Select * from books b where b.publication=?", nativeQuery = true)
    public List<Book> findByPublication(@Param("publication") String publication);

    @Query(value = "Select * from books b where b.category=?", nativeQuery = true)
    public List<Book> findByCategory(@Param("category") String category);
}
